package com.hbm.tileentity.machine;

import com.hbm.api.energymk2.IEnergyReceiverMK2;
import com.hbm.interfaces.AutoRegister;
import com.hbm.inventory.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.inventory.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.inventory.recipes.NuclearTransmutationRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCapacitor;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@AutoRegister
public class TileEntityMachineSchrabidiumTransmutator extends TileEntityMachineBase implements ITickable, IEnergyReceiverMK2, IGUIProvider {

    public long power;
    public int process;
    public static final long maxPower = 50_000_000L;
    public static final int processSpeed = 600;

    private AudioWrapper audio;

    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{1, 2};
    private static final int[] slotsSide = new int[]{3, 2};

    public TileEntityMachineSchrabidiumTransmutator() {
        super(4, false, true);
    }

    @Override
    public String getDefaultName() {
        return "container.machine_schrabidium_transmutator";
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        if (stack.isEmpty())
            return false;
        switch (i) {
            case 0:
                return NuclearTransmutationRecipes.getOutput(stack) != null;
            case 2:
                return stack.getItem() == ModItems.redcoil_capacitor || stack.getItem() == ModItems.euphemium_capacitor;
            case 3:
                return Library.isBattery(stack);
            default:
                return false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing side) {
        if (side == EnumFacing.DOWN)
            return slotsBottom;
        if (side == EnumFacing.UP)
            return slotsTop;
        return slotsSide;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int amount) {
        if (slot == 2 && stack.getItem() == ModItems.redcoil_capacitor && ItemCapacitor.getDura(stack) <= 0)
            return true;
        if (slot == 1)
            return true;
        return slot == 3 && Library.isDischargeableBattery(stack);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        power = compound.getLong("power");
        process = compound.getInteger("process");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("power", power);
        compound.setInteger("process", process);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
                this.trySubscribe(world, pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ, dir);

            power = Library.chargeTEFromItems(inventory, 3, power, maxPower);

            if (canProcess()) {
                process();
            } else {
                process = 0;
            }

            networkPackNT(50);
        } else if (process > 0) {
            if (audio == null) {
                audio = MainRegistry.proxy.getLoopedSound(HBMSoundHandler.tauChargeLoop, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), 1.0F, 1.0F);
                audio.startSound();
            }
        } else if (audio != null) {
            audio.stopSound();
            audio = null;
        }
    }

    @Override
    public void onChunkUnload() {
        if (audio != null) {
            audio.stopSound();
            audio = null;
        }
        super.onChunkUnload();
    }

    @Override
    public void invalidate() {
        if (audio != null) {
            audio.stopSound();
            audio = null;
        }
        super.invalidate();
    }

    @Override
    public void serialize(ByteBuf buf) {
        buf.writeLong(power);
        buf.writeInt(process);
    }

    @Override
    public void deserialize(ByteBuf buf) {
        power = buf.readLong();
        process = buf.readInt();
    }

    public long getPowerScaled(long i) {
        return (power * i) / maxPower;
    }

    public int getProgressScaled(int i) {
        return (process * i) / processSpeed;
    }

    public boolean hasCoil() {
        ItemStack coil = inventory.getStackInSlot(2);
        if (coil.isEmpty())
            return false;
        if (coil.getItem() == ModItems.redcoil_capacitor)
            return ItemCapacitor.getDura(coil) > 0;
        return coil.getItem() == ModItems.euphemium_capacitor;
    }

    public boolean canProcess() {
        if (!hasCoil())
            return false;

        ItemStack input = inventory.getStackInSlot(0);
        if (input.isEmpty())
            return false;

        long recipePower = NuclearTransmutationRecipes.getEnergy(input);
        if (recipePower < 0 || recipePower > power)
            return false;

        ItemStack outputItem = NuclearTransmutationRecipes.getOutput(input);
        if (outputItem == null)
            return false;

        ItemStack outputSlot = inventory.getStackInSlot(1);
        return outputSlot.isEmpty()
                || (outputSlot.getItem() == outputItem.getItem() && outputSlot.getCount() < outputSlot.getMaxStackSize());
    }

    public boolean isProcessing() {
        return process > 0;
    }

    public void process() {
        process++;

        if (process >= processSpeed) {
            ItemStack input = inventory.getStackInSlot(0);
            long recipePower = NuclearTransmutationRecipes.getEnergy(input);
            power -= recipePower;
            if (power < 0)
                power = 0;
            process = 0;

            ItemStack recipeOutput = NuclearTransmutationRecipes.getOutput(input);
            ItemStack outputSlot = inventory.getStackInSlot(1);
            if (outputSlot.isEmpty()) {
                inventory.setStackInSlot(1, recipeOutput.copy());
            } else {
                outputSlot.grow(1);
            }

            ItemStack coil = inventory.getStackInSlot(2);
            if (!coil.isEmpty() && coil.getItem() == ModItems.redcoil_capacitor)
                ItemCapacitor.setDura(coil, ItemCapacitor.getDura(coil) - 1);

            input.shrink(1);
            if (input.isEmpty())
                inventory.setStackInSlot(0, ItemStack.EMPTY);

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.BLOCKS, 10000.0F, 0.8F + world.rand.nextFloat() * 0.2F);
            markDirty();
        }
    }

    @Override
    public void setPower(long i) {
        power = i;
    }

    @Override
    public long getPower() {
        return power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public Container provideContainer(int id, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineSchrabidiumTransmutator(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int id, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineSchrabidiumTransmutator(player.inventory, this);
    }
}