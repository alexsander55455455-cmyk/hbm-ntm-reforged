package com.hbm.creativetabs;

import com.hbm.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConsumableTab extends HBMCreativeTab {

	public ConsumableTab(int index, String label) {
		super(index, label, "consumableTab");
	}

	@Override
    @SideOnly(Side.CLIENT)
	public ItemStack createIcon() {
		if(ModItems.bottle_nuka != null){
			return new ItemStack(ModItems.bottle_nuka);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
