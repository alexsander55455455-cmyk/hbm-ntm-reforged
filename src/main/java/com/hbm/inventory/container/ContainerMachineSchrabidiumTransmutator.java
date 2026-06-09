package com.hbm.inventory.container;

import com.hbm.inventory.slot.SlotBattery;
import com.hbm.inventory.slot.SlotFiltered;
import com.hbm.tileentity.machine.TileEntityMachineSchrabidiumTransmutator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineSchrabidiumTransmutator extends Container {

    private final TileEntityMachineSchrabidiumTransmutator transmutator;

    public ContainerMachineSchrabidiumTransmutator(InventoryPlayer invPlayer, TileEntityMachineSchrabidiumTransmutator te) {
        transmutator = te;

        this.addSlotToContainer(new SlotItemHandler(te.inventory, 0, 44, 63));
        this.addSlotToContainer(SlotFiltered.takeOnly(te.inventory, 1, 134, 63));
        this.addSlotToContainer(new SlotItemHandler(te.inventory, 2, 26, 18));
        this.addSlotToContainer(new SlotBattery(te.inventory, 3, 8, 108));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, transmutator.process);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            original = stack.copy();

            if (index <= 3) {
                if (!this.mergeItemStack(stack, 4, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(stack, 0, 1, false)) {
                if (!this.mergeItemStack(stack, 3, 4, false)) {
                    if (!this.mergeItemStack(stack, 2, 3, false))
                        return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return original;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return transmutator.isUseableByPlayer(player);
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0)
            transmutator.process = data;
    }
}