package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemCanister;
import com.hbm.main.CraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.fluid.Fluids.*;

/** EE ammo crafts on workbench (from NTM Extended CraftingManager). */
public class EEAmmoWorkbenchRecipes {

    public static void register() {
        registerBulletRecipes();
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 64), "SSS", "SRS", "SSS", 'S', STEEL.plate(), 'R', ModItems.rod_quad_uranium_fuel_depleted);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 32), " S ", "SRS", " S ", 'S', STEEL.plate(), 'R', ModItems.rod_dual_uranium_fuel_depleted);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), " S ", " R ", " S ", 'S', STEEL.plate(), 'R', ModItems.rod_uranium_fuel_depleted);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), "SRS", 'S', STEEL.plate(), 'R', ModItems.rod_uranium_fuel_depleted);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), " S ", " R ", " S ", 'S', STEEL.plate(), 'R', U238.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), "SRS", 'S', STEEL.plate(), 'R', U238.ingot());

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', COAL.dust(), 'P', P_RED.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ItemCanister.getStackFromFluid(DIESEL));
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ItemCanister.getStackFromFluid(KEROSENE));
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 24), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.canister_napalm);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 32), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ItemCanister.getStackFromFluid(NITAN));

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', KNO.dust(), 'P', Items.SNOWBALL);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.powder_ice);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_emp_ammo, 8), "IGI", "IPI", "IPI", 'G', GOLD.plate(), 'I', IRON.plate(), 'P', ModItems.powder_power);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_euthanasia_ammo, 12), "P", "S", "N", 'P', ModItems.powder_poison, 'N', KNO.dust(), 'S', ModItems.syringe_metal_empty);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_spark_ammo, 4), "PCP", "DDD", "PCP", 'P', PB.plate(), 'C', ModItems.coil_gold, 'D', ModItems.powder_power);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_hp_ammo, 8), " R ", "BSK", " Y ", 'S', STEEL.plate(), 'K', new ItemStack(Items.DYE, 1, 0), 'R', new ItemStack(Items.DYE, 1, 1), 'B', new ItemStack(Items.DYE, 1, 4), 'Y', new ItemStack(Items.DYE, 1, 11));
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_defabricator_ammo, 16), "PCP", "DDD", "PCP", 'P', STEEL.plate(), 'C', ModItems.coil_copper, 'D', LI.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b92_ammo, 1), "PSP", "ESE", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'E', ModItems.powder_spark_mix);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.turret_flamer_ammo, 1), "FSF", "FPF", "FPF", 'F', ModItems.gun_immolator_ammo, 'S', ModItems.pipes_steel, 'P', CU.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.turret_tau_ammo, 1), "AAA", "AUA", "AAA", 'A', ModItems.gun_xvl1456_ammo, 'U', U238.block());

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fuel, 1), " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, Mats.MAT_STEEL.id), 'D', ItemCanister.getStackFromFluid(DIESEL));
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fuel_napalm, 1), " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, Mats.MAT_STEEL.id), 'D', ModItems.canister_napalm);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fuel_phosphorus, 1), "CPC", "CDC", "CPC", 'C', COAL.dust(), 'P', P_WHITE.ingot(), 'D', ModItems.ammo_fuel);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fuel_gas, 1), "PDP", "BDB", "PDP", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, Mats.MAT_STEEL.id), 'D', ModItems.pellet_gas);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fuel_vaporizer, 1), "PSP", "SNS", "PSP", 'P', P_WHITE.ingot(), 'S', ModItems.crystal_sulfur, 'N', ModItems.ammo_fuel_napalm);
    }

    private static void registerBulletRecipes() {
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.pellet_flechette, 1), " L ", " L ", "LLL", 'L', PB.nugget());

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.primer_357, 1), "R", "P", 'P', IRON.plate(), 'R', REDSTONE.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.primer_44, 1), "P", "R", 'P', IRON.plate(), 'R', REDSTONE.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.primer_9, 1), "R", "P", 'P', AL.plate(), 'R', REDSTONE.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.primer_50, 1), "P", "R", 'P', AL.plate(), 'R', REDSTONE.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.primer_buckshot, 1), "R", "P", 'P', CU.plate(), 'R', REDSTONE.dust());

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_357, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_44, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_9, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_50, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_357, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_44, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_9, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_50, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_iron, 24), " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_iron, 24), " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_steel, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_steel, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', U235.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', PU239.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', ModItems.trinitite, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', ModItems.nuclear_waste_tiny, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_gold, 24), " I", "GC", " P", 'I', GOLD.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_schrabidium, 6), " I ", "GCN", " P ", 'I', SA326.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357, 'N', Items.NETHER_STAR);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_nightmare, 24), " I", "GC", " P", 'I', W.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_desh, 24), " I", "GC", " P", 'I', DESH.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_smg, 32), " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_556, 32), " I", "GC", " P", 'I', STEEL.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_uzi, 32), " I", "GC", " P", 'I', IRON.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_lacunae, 32), " I", "GC", " P", 'I', CU.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_nopip, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_44, 'P', ModItems.primer_44);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_calamity, 12), " I ", "GCG", " P ", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_actionexpress, 12), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_556_k, 32), "G", "C", "P", 'G', ANY_GUNPOWDER.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_12gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_20gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_slug, 12), " I ", "GCL", " P ", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_explosive, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_flechette, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 12), "I", "C", "P", 'I', ModItems.powder_power, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_rocket, 1), " T ", "GCG", " P ", 'T', Blocks.TNT, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_rocket, 2), " T ", "GCG", " P ", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade, 2), " T ", "GCI", 'T', ANY_HIGHEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'I', IRON.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_tracer, 2), " T ", "GCI", " P ", 'T', LAPIS.dust(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', IRON.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_he, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', ANY_PLASTICEXPLOSIVE.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_incendiary, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', P_RED.dust());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_toxic, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_poison);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_concussion, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', Items.GLOWSTONE_DUST);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_nuclear, 2), " P ", "GIG", " P ", 'G', ModItems.ammo_grenade, 'I', ModItems.neutron_reflector, 'P', PU239.nugget());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_finned, 1), "G", "R", 'G', Items.FEATHER, 'R', ModItems.ammo_grenade);
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_grenade_kampf, 2), "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_grenade);

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_slug, 12), " I ", "GCL", " P ", 'I', STEEL.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_flechette, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_explosive, 4), " I ", "GCL", " P ", 'I', Blocks.TNT, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_explosive, 6), " I ", "GCL", " P ", 'I', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_semtex, 4), " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());

        CraftingManager.addRecipeAuto(new ItemStack(ModItems.folly_shell, 1), "IPI", "IPI", "IMI", 'I', IRON.ingot(), 'P', IRON.plate(), 'M', ModItems.primer_50);
    }
}