package com.hbm.creativetabs;

import com.hbm.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlTab extends HBMCreativeTab {

	public ControlTab(int index, String label) {
		super(index, label, "controlTab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon() {
		if (ModItems.pellet_rtg != null) {
			return new ItemStack(ModItems.pellet_rtg);
		}
		return new ItemStack(Items.IRON_PICKAXE, 1);
	}
}