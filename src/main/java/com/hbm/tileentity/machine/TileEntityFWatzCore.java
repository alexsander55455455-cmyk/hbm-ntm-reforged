package com.hbm.tileentity.machine;

import com.hbm.api.energymk2.IEnergyProviderMK2;
import com.hbm.api.fluid.IFluidStandardTransceiver;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.interfaces.AutoRegister;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.SAFERecipes;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.inventory.gui.GUIFWatzCore;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFWatzCore;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.world.FWatz;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AutoRegister
public class TileEntityFWatzCore extends TileEntityMachineBase implements IControlReceiver, ITickable, IEnergyProviderMK2, IFluidStandardTransceiver, IGUIProvider, IFluidCopiable {

	public long power;
	public final static long maxPower = 1000000000000L;
	public boolean cooldown = false;

	public FluidTankNTM[] tanks;
	public boolean needsUpdate = true;
	public boolean isOn = false;
    public boolean isOk = true;
    public boolean isDoingSomething = false;

	public TileEntityFWatzCore() {
		super(7, true, true);
		tanks = new FluidTankNTM[3];
		tanks[0] = new FluidTankNTM(Fluids.COOLANT, 128000).withOwner(this);
		tanks[1] = new FluidTankNTM(Fluids.AMAT, 64000).withOwner(this);
		tanks[2] = new FluidTankNTM(Fluids.ASCHRAB, 64000).withOwner(this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player){
		return true;
	}
	
	@Override
	public void receiveControl(NBTTagCompound data){
		this.isOn = !this.isOn;
		this.markDirty();
		this.dataChanged();
	}

	@Override
	public String getDefaultName() {
		return "container.fusionaryWatzPlant";
	}

	public int getSingularityType(){
		Item item = inventory.getStackInSlot(2).getItem();
		if(item instanceof ItemFWatzCore core){
			return core.type;
		}
		return 0;
	}

    public int getType(){
        Item item = inventory.getStackInSlot(2).getItem();
        if(item instanceof ItemFWatzCore core){
            return core.type * (core.isBaby ? -1 : 1);
        }
        return 0;
    }

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		isOn = compound.getBoolean("isOn");
		tanks[0].readFromNBT(compound, "t0");
		tanks[1].readFromNBT(compound, "t1");
		tanks[2].readFromNBT(compound, "t2");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setBoolean("isOn", isOn);
		tanks[0].writeToNBT(compound, "t0");
		tanks[1].writeToNBT(compound, "t1");
		tanks[2].writeToNBT(compound, "t2");
		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
        if(inventory.getStackInSlot(2).getItem() instanceof ItemFWatzCore core) {
            doGravityStuff(world, isOk ? 6 : 30, 1, pos.getX() + 0.5F, pos.getY() + 2.5F, pos.getZ() + 0.5F, (core.type / 2D + 2) * (core.isBaby ? 0.2 : 1));
        }
		if(!world.isRemote){
            if(this.isStructureValid(this.world)) {
                isOk = true;
                sendSAFEPower();

                if(isDoingSomething){
                    doElse();
                }

                if(inventory.getStackInSlot(2).getItem() instanceof ItemFWatzCore itemCore && this.isOn) {
                    if(cooldown) {

                        tanks[0].setFill(Math.min(tanks[0].getMaxFill(), tanks[0].getFill() + itemCore.coolantRefill));

                        if (tanks[0].getFill() >= tanks[0].getMaxFill()) {
                            cooldown = false;
                        }
                        isDoingSomething = false;

                    } else {

                        if (tanks[1].getFill() > itemCore.amatDrain && tanks[2].getFill() > itemCore.aschrabDrain) {
                            tanks[0].setFill(tanks[0].getFill() - itemCore.coolantDrain);
                            tanks[1].setFill(tanks[1].getFill() - itemCore.amatDrain);
                            tanks[2].setFill(tanks[2].getFill() - itemCore.aschrabDrain);
                            needsUpdate = true;
                            power += itemCore.powerOutput;
                            isDoingSomething = true;
                            if (world.rand.nextInt(2048) == 0)
                                tryGrowCore();
                        }

                        if (power > maxPower)
                            power = maxPower;

                        if (tanks[0].getFill() <= 0) {
                            cooldown = true;
                        }
                    }
                } else {
                    isDoingSomething = false;
                }

                if(power > maxPower)
                    power = maxPower;

                power = Library.chargeItemsFromTE(inventory, 0, power, maxPower);

                if(this.inputValidForTank(1, 3))
                    if(tanks[1].loadTank(3, 5, inventory))
                        needsUpdate = true;
                if(this.inputValidForTank(2, 4))
                    if(tanks[2].loadTank(4, 6, inventory))
                        needsUpdate = true;

                if(needsUpdate) {
                    needsUpdate = false;
                    this.markDirty();
                    this.dataChanged();
                }
            } else {
                isOk = false;
                this.dataChanged();
            }

            this.networkPackNT(50);
        }
	}

    public void doElse(){
        ItemStack stack = inventory.getStackInSlot(2);
        if(stack.getItem() == ModItems.meteorite_sword_baleful){
            inventory.setStackInSlot(2, new ItemStack(ModItems.meteorite_sword_warped));
            this.dataChanged();
        } else if(stack.hasTagCompound()){
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.getBoolean("ntmContagion")) nbt.removeTag("ntmContagion");
            if(nbt.isEmpty()) stack.setTagCompound(null);
        }
    }

	private void sendSAFEPower(){
		this.tryProvide(world, pos.add(7, 1, 0), Library.POS_X);
		this.tryProvide(world, pos.add(-7, 1, 0), Library.NEG_X);
		this.tryProvide(world, pos.add(0, 1, 7), Library.POS_Z);
		this.tryProvide(world, pos.add(0, 1, -7), Library.NEG_Z);
        this.tryProvide(world, pos.add(7, -3, 0), Library.POS_X);
        this.tryProvide(world, pos.add(-7, -3, 0), Library.NEG_X);
        this.tryProvide(world, pos.add(0, -3, 7), Library.POS_Z);
        this.tryProvide(world, pos.add(0, -3, -7), Library.NEG_Z);
	}

	private void tryGrowCore(){
		ItemStack output = SAFERecipes.getOutput(inventory.getStackInSlot(2));
		if(output != null){
			inventory.setStackInSlot(2, output.copy());
			this.dataChanged();
		}
	}

	public boolean isStructureValid(World world) {
		return FWatz.checkHull(world, pos);
	}

	public long getPowerScaled(long i) {
		return (power / 100 * i) / (maxPower / 100);
	}

	protected boolean inputValidForTank(int tank, int slot) {
		if(tanks[tank] != null) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if(stack.isEmpty()) return false;
            return stack.getItem() == ModItems.fluid_barrel_infinite || FluidContainerRegistry.getFluidContent(stack, tanks[tank].getTankType()) > 0;
		}
		return false;
	}

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return new int[] {0, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        if(stack.getItem() instanceof ItemFWatzCore){
            return i == 2;
        }
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
        if(slot == 2 && itemStack.getItem() instanceof ItemFWatzCore core && !core.isBaby) return true;
        return slot == 5 || slot == 6;
    }
	
	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

    AxisAlignedBB bb = null;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(bb == null) {
            bb = new AxisAlignedBB(pos.getX() + 0.5 - 8, pos.getY() + 0.5 - 3, pos.getZ() + 0.5 - 8, pos.getX() + 0.5 + 8, pos.getY() + 0.5 + 3, pos.getZ() + 0.5 + 8);
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    public static void doGravityStuff(World world, float range, float deathRadius, float posX, float posY, float posZ, double strength){
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));

        for(Entity e : entities) {
            if(e instanceof EntityPlayer player && player.capabilities.isCreativeMode)
                continue;

            Vec3 vec = Vec3.createVectorHelper(posX - e.posX, posY - e.posY, posZ - e.posZ);

            double dist = vec.length();

            if(dist > range)
                continue;

            vec = vec.normalize();

            if(!(e instanceof EntityItem))
                vec.rotateAroundY((float)Math.toRadians(15));
            double r2 = Math.max(dist * dist, 1);
            e.motionX += vec.xCoord * strength / r2;
            e.motionY += vec.yCoord * strength * 2 / r2;
            e.motionZ += vec.zCoord * strength / r2;

            if(e instanceof EntityBlackHole)
                continue;

            if(dist < deathRadius) {
                e.attackEntityFrom(ModDamageSource.blackhole, 1000);

                if(!(e instanceof EntityLivingBase))
                    e.setDead();
            }
        }
    }

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeBoolean(isOn);
		buf.writeBoolean(isOk);
		buf.writeBoolean(isDoingSomething);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		tanks[2].serialize(buf);
		BufferUtil.writeItemStack(buf, inventory.getStackInSlot(2));
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		isOn = buf.readBoolean();
		isOk = buf.readBoolean();
		isDoingSomething = buf.readBoolean();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		tanks[2].deserialize(buf);
		inventory.setStackInSlot(2, BufferUtil.readItemStack(buf));
	}

	@Override
	public FluidTankNTM[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTankNTM[] getSendingTanks() {
		return new FluidTankNTM[0];
	}

	@Override
	public FluidTankNTM[] getReceivingTanks() {
		return new FluidTankNTM[] { tanks[1], tanks[2] };
	}

	@Override
	public FluidTankNTM getTankToPaste() {
		return null;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFWatzCore(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFWatzCore(player.inventory, this);
	}
}