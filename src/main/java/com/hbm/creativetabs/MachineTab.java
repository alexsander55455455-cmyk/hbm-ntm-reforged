package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class MachineTab extends HBMCreativeTab {

	public MachineTab(int index, String label) {
		super(index, label, "machineTab");
	}

	@NotNull
	@Override
    @SideOnly(Side.CLIENT)
	public ItemStack createIcon() {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.pwr_controller));
    }
}
