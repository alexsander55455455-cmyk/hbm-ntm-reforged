package com.hbm.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class HBMCreativeTab extends CreativeTabs {

	private final String tabKey;

	protected HBMCreativeTab(int index, String label, String tabKey) {
		super(index, label);
		this.tabKey = tabKey;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		collectItems(list);
		sortStacks(list);
		appendTabExtras(list);
	}

	@SideOnly(Side.CLIENT)
	protected void collectItems(NonNullList<ItemStack> list) {
		super.displayAllRelevantItems(list);
	}

	@SideOnly(Side.CLIENT)
	protected void appendTabExtras(NonNullList<ItemStack> list) {
	}

	@SideOnly(Side.CLIENT)
	protected void sortStacks(NonNullList<ItemStack> list) {
		CreativeTabSortHelper.sortStacks(list, tabKey);
	}
}