package com.hbm.inventory.container;

import com.hbm.inventory.TransferStrategy;
import com.hbm.tileentity.machine.TileEntityFWatzCore;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFWatzCore extends Container {
	
	private TileEntityFWatzCore diFurnace;
	private final TransferStrategy transferStrategy = TransferStrategy.builder(7).genericMachineRange(0).build();
	
	public ContainerFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		
		diFurnace = tedf;
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 130, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 80, 45));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 8, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 152, 90));
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 8, 108) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 152, 108) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return InventoryUtil.transferStack(this.inventorySlots, index, this.transferStrategy, player);
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
}