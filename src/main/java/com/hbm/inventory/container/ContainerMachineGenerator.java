package com.hbm.inventory.container;

import com.hbm.inventory.TransferStrategy;
import com.hbm.inventory.slot.SlotBattery;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineGenerator extends Container {

    private final TileEntityMachineGenerator generator;
    private final TransferStrategy transferStrategy;

    public ContainerMachineGenerator(InventoryPlayer invPlayer, TileEntityMachineGenerator te) {
        generator = te;
        transferStrategy = TransferStrategy.builder(14)
                .rule(0, 9, s -> s.getItem() instanceof ItemFuelRod)
                .rule(9, 10, s -> Library.isStackDrainableForTank(s, generator.tankWater))
                .rule(10, 11, s -> Library.isStackDrainableForTank(s, generator.tankCoolant))
                .rule(11, 12, Library::isChargeableBattery)
                .ruleDispatchMode(TransferStrategy.RuleDispatchMode.FALLTHROUGH_ON_FAILURE)
                .playerFallbackMode(TransferStrategy.PlayerFallbackMode.REBALANCE_SECTIONS)
                .build();

        addSlotToContainer(new SlotItemHandler(te.inventory, 0, 116, 36));
        addSlotToContainer(new SlotItemHandler(te.inventory, 1, 134, 36));
        addSlotToContainer(new SlotItemHandler(te.inventory, 2, 152, 36));
        addSlotToContainer(new SlotItemHandler(te.inventory, 3, 116, 54));
        addSlotToContainer(new SlotItemHandler(te.inventory, 4, 134, 54));
        addSlotToContainer(new SlotItemHandler(te.inventory, 5, 152, 54));
        addSlotToContainer(new SlotItemHandler(te.inventory, 6, 116, 72));
        addSlotToContainer(new SlotItemHandler(te.inventory, 7, 134, 72));
        addSlotToContainer(new SlotItemHandler(te.inventory, 8, 152, 72));
        addSlotToContainer(new SlotItemHandler(te.inventory, 9, 8, 90));
        addSlotToContainer(new SlotItemHandler(te.inventory, 10, 26, 90));
        addSlotToContainer(new SlotBattery(te.inventory, 11, 62, 90));
        addSlotToContainer(new SlotItemHandler(te.inventory, 12, 8, 108));
        addSlotToContainer(new SlotItemHandler(te.inventory, 13, 26, 108));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 198));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return InventoryUtil.transferStack(inventorySlots, index, transferStrategy, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return generator.isUseableByPlayer(player);
    }
}