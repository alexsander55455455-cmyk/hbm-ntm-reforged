package com.hbm.tileentity.machine;

import com.hbm.api.energymk2.IBatteryItem;
import com.hbm.api.energymk2.IEnergyProviderMK2;
import com.hbm.api.fluidmk2.IFluidStandardReceiverMK2;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.AutoRegister;
import com.hbm.inventory.container.ContainerMachineGenerator;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.inventory.gui.GUIMachineGenerator;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@AutoRegister
public class TileEntityMachineGenerator extends TileEntityMachineBase implements ITickable, IEnergyProviderMK2, IFluidStandardReceiverMK2, IGUIProvider, IFluidCopiable {

    public int heat;
    public final int heatMax = 100000;
    public long power;
    public final long maxPower = 100000;
    public boolean isLoaded;

    public FluidTankNTM tankWater;
    public FluidTankNTM tankCoolant;

    private static final Map<Item, Item> DEPLETED_MAP = new HashMap<>();
    private static final Map<Item, Long> POWER_MAP = new HashMap<>();

    static {
        registerFuel(ModItems.rod_uranium_fuel, ModItems.rod_uranium_fuel_depleted, 100);
        registerFuel(ModItems.rod_dual_uranium_fuel, ModItems.rod_dual_uranium_fuel_depleted, 100);
        registerFuel(ModItems.rod_quad_uranium_fuel, ModItems.rod_quad_uranium_fuel_depleted, 100);
        registerFuel(ModItems.rod_plutonium_fuel, ModItems.rod_plutonium_fuel_depleted, 150);
        registerFuel(ModItems.rod_dual_plutonium_fuel, ModItems.rod_dual_plutonium_fuel_depleted, 150);
        registerFuel(ModItems.rod_quad_plutonium_fuel, ModItems.rod_quad_plutonium_fuel_depleted, 150);
        registerFuel(ModItems.rod_mox_fuel, ModItems.rod_mox_fuel_depleted, 50);
        registerFuel(ModItems.rod_dual_mox_fuel, ModItems.rod_dual_mox_fuel_depleted, 50);
        registerFuel(ModItems.rod_quad_mox_fuel, ModItems.rod_quad_mox_fuel_depleted, 50);
        registerFuel(ModItems.rod_schrabidium_fuel, ModItems.rod_schrabidium_fuel_depleted, 25000);
        registerFuel(ModItems.rod_dual_schrabidium_fuel, ModItems.rod_dual_schrabidium_fuel_depleted, 25000);
        registerFuel(ModItems.rod_quad_schrabidium_fuel, ModItems.rod_quad_schrabidium_fuel_depleted, 25000);
    }

    private static void registerFuel(Item fuel, Item depleted, long powerPerTick) {
        DEPLETED_MAP.put(fuel, depleted);
        POWER_MAP.put(fuel, powerPerTick);
    }

    private AxisAlignedBB bb;

    public TileEntityMachineGenerator() {
        super(14, 1, true, true);
        tankWater = new FluidTankNTM(Fluids.WATER, 32000).withOwner(this);
        tankCoolant = new FluidTankNTM(Fluids.COOLANT, 16000).withOwner(this);
    }

    @Override
    public String getDefaultName() {
        return "container.generator";
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (slot >= 0 && slot <= 8) return stack.getItem() instanceof ItemFuelRod;
        if (slot == 9) return Library.isStackDrainableForTank(stack, tankWater);
        if (slot == 10) return Library.isStackDrainableForTank(stack, tankCoolant);
        if (slot == 11) return stack.getItem() instanceof IBatteryItem;
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 12 || slot == 13;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                this.tryProvide(world, pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ, dir);
            }

            tankWater.setType(9, 12, inventory);
            tankCoolant.setType(10, 13, inventory);
            tankWater.loadTank(9, 12, inventory);
            tankCoolant.loadTank(10, 13, inventory);

            power = Library.chargeItemsFromTE(inventory, 11, power, maxPower);

            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack.isEmpty() || !(stack.getItem() instanceof ItemFuelRod)) continue;

                ItemFuelRod rod = (ItemFuelRod) stack.getItem();
                ItemFuelRod.incrementTime(stack, 1);
                attemptHeat(rod.getHeatPerTick());
                attemptPower(POWER_MAP.getOrDefault(stack.getItem(), 0L));

                if (ItemFuelRod.getLifeTime(stack) >= rod.getMaxLifeTime()) {
                    Item depleted = DEPLETED_MAP.get(stack.getItem());
                    inventory.setStackInSlot(i, depleted != null ? new ItemStack(depleted) : ItemStack.EMPTY);
                }
            }

            if (power > maxPower) power = maxPower;
            if (heat > heatMax) explode();

            coolWhenIdle();
            this.networkPackNT(25);
        }
    }

    private void coolWhenIdle() {
        boolean onlyFirstRod = true;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (i == 0) {
                if (!stack.isEmpty() && !(stack.getItem() instanceof ItemFuelRod)) onlyFirstRod = false;
            } else if (!stack.isEmpty()) {
                onlyFirstRod = false;
            }
        }

        isLoaded = !onlyFirstRod;

        if (!onlyFirstRod) return;

        if (heat - 10 >= 0 && tankCoolant.getFill() - 2 >= 0) {
            heat -= 10;
            tankCoolant.setFill(tankCoolant.getFill() - 2);
        } else if (heat < 10 && heat != 0 && tankCoolant.getFill() != 0) {
            heat--;
            tankCoolant.setFill(tankCoolant.getFill() - 1);
        } else if (heat != 0 && tankCoolant.getFill() == 0) {
            heat--;
        }
    }

    private void attemptPower(long amount) {
        if (amount <= 0) return;
        int drain = (int) Math.ceil(amount / 100D);
        if (tankWater.getFill() - drain >= 0) {
            power += amount;
            if (drain > tankWater.getMaxFill() / 25) drain = tankWater.getMaxFill() / 25;
            tankWater.setFill(tankWater.getFill() - drain);
        }
    }

    private void attemptHeat(int amount) {
        Random rand = new Random();
        int drain = rand.nextInt(amount + 1);
        if (tankCoolant.getFill() - drain >= 0) {
            tankCoolant.setFill(tankCoolant.getFill() - drain);
        } else {
            heat += amount;
        }
    }

    private void explode() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 18.0F, true);
        ExplosionNukeGeneric.waste(world, pos.getX(), pos.getY(), pos.getZ(), 35);
        world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        power = compound.getLong("power");
        heat = compound.getInteger("heat");
        tankWater.readFromNBT(compound, "tankWater");
        tankCoolant.readFromNBT(compound, "tankCoolant");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("power", power);
        compound.setInteger("heat", heat);
        tankWater.writeToNBT(compound, "tankWater");
        tankCoolant.writeToNBT(compound, "tankCoolant");
        return super.writeToNBT(compound);
    }

    @Override
    public void serialize(ByteBuf buf) {
        super.serialize(buf);
        buf.writeLong(power);
        buf.writeInt(heat);
        tankWater.serialize(buf);
        tankCoolant.serialize(buf);
    }

    @Override
    public void deserialize(ByteBuf buf) {
        super.deserialize(buf);
        power = buf.readLong();
        heat = buf.readInt();
        tankWater.deserialize(buf);
        tankCoolant.deserialize(buf);
    }

    public long getPowerScaled(long scale) {
        return (power * scale) / maxPower;
    }

    public int getHeatScaled(int scale) {
        return (heat * scale) / heatMax;
    }

    public boolean hasPower() {
        return power > 0;
    }

    public boolean hasHeat() {
        return heat > 0;
    }

    @Override
    public long getPower() {
        return power;
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public FluidTankNTM[] getReceivingTanks() {
        return new FluidTankNTM[] { tankWater, tankCoolant };
    }

    @Override
    public FluidTankNTM[] getAllTanks() {
        return new FluidTankNTM[] { tankWater, tankCoolant };
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineGenerator(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineGenerator(player.inventory, this);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (bb == null) bb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        return bb;
    }
}