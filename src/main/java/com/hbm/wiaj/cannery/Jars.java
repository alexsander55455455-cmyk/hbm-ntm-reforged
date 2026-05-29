package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.PlantEnums;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class Jars {

	public static HashMap<ComparableStack, CanneryBase> canneries = new HashMap<ComparableStack, CanneryBase>();
	
	public static void initJars() {
		putCannery(new ItemStack(ModBlocks.heater_firebox), new CanneryFirebox());
		putCannery(new ItemStack(ModBlocks.machine_gascent), new CanneryCentrifuge());
		putCannery(new ItemStack(ModBlocks.machine_fensu), new CanneryFEnSU());
		putCannery(new ItemStack(ModBlocks.machine_fel), new CannerySILEX());
		putCannery(new ItemStack(ModBlocks.machine_silex), new CannerySILEX());
		putCannery(new ItemStack(ModBlocks.hadron_core), new CanneryHadron());
		putCannery(new ItemStack(ModBlocks.hadron_diode), new CannerySchottky());
		putCannery(new ItemStack(ModBlocks.machine_stirling), new CanneryStirling());
		putCannery(new ItemStack(ModBlocks.machine_stirling_steel), new CanneryStirling());

		putCannery(OreDictManager.DictFrame.fromOne(ModItems.plant_item, ItemEnums.EnumPlantType.MUSTARDWILLOW), new CanneryWillow());
		putCannery(OreDictManager.DictFrame.fromOne(ModBlocks.plant_flower, PlantEnums.EnumFlowerPlantType.MUSTARD_WILLOW_0), new CanneryWillow());

	}

	private static void putCannery(ItemStack stack, CanneryBase cannery) {
		if (stack == null || stack.isEmpty() || Item.REGISTRY.getNameForObject(stack.getItem()) == null) return;
		canneries.put(new ComparableStack(stack), cannery);
	}
}
