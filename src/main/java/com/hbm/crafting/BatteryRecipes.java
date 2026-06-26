package com.hbm.crafting;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.main.CraftingManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static com.hbm.inventory.OreDictManager.*;

public class BatteryRecipes {

	public static void register() {
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_generic), " A ", "PRP", "PRP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'R', REDSTONE.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), " A ", "PSP", "PLP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_MINGRADE.id), 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), " A ", "PLP", "PSP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_MINGRADE.id), 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), "A A", "PSP", "PLP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_GOLD.id), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), "A A", "PLP", "PSP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_GOLD.id), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), " A ", "PNP", "PSP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), " A ", "PSP", "PNP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust());
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_trixite), " A ", "PSP", "PTP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite);
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_trixite), " A ", "PTP", "PSP", 'A', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite);

		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), "BBB", "WPW", "BBB", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), "BWB", "WPW", "BWB", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), "WPW", "BBB", "WPW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), "BWB", "WPW", "BWB", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), "WPW", "BBB", "WPW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2));

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_spark), "P", "S", "S", 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix);
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), "BW", "PW", "BW", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MAGTUNG.id), 'P', ModItems.powder_spark_mix, 'B', new ItemStack(ModItems.battery_spark));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), "W W", "SCS", "PSP", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MAGTUNG.id), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), "W W", "BPB", "SSS", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_MAGTUNG.id), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), "PCP", "CSC", "PCP", 'S', ModItems.singularity_spark, 'P', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100));
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), "SCS", "CVC", "SCS", 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'S', ModItems.powder_spark_mix);
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), "OPO", "VSV", "OPO", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'O', ModItems.ingot_osmiridium, 'P', ModItems.plate_dineutronium);
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), "YPY", "CSC", "YPY", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), 'Y', ModItems.billet_yharonite, 'P', ModItems.plate_dineutronium);

		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), "P", "R", "C", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), "P", "C", "R", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "CPC", "RPR", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "RPR", "CPC", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_ALUMINIUM.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "CPC", "RPR", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_COPPER.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "RPR", "CPC", 'W', new ItemStack(ModItems.wire, 1, Mats.MAT_COPPER.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());

		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam), "PMP", "ISI", "PCP", 'P', CU.plate(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.WATER.getID()), 'I', ANY_RUBBER.ingot());
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), "MPM", "ISI", "CPC", 'P', CU.plateWelded(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.WATER.getID()), 'I', ANY_PLASTIC.ingot());

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 1), new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE));
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 2), new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE));
	}
}