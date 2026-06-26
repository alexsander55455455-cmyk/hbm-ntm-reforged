package com.hbm.crafting;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.CraftingManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.OreDictManager.*;

/**
 * Standalone rod item recipes ported from Extended Edition addRodCrafting().
 */
public class LegacyRodRecipes {

    public static void register() {
        legacyRod(ModItems.rod_th232, TH232.nugget());
        legacyRod(ModItems.rod_uranium, U.nugget());
        legacyRod(ModItems.rod_u233, U233.nugget());
        legacyRod(ModItems.rod_u235, U235.nugget());
        legacyRod(ModItems.rod_u238, U238.nugget());
        legacyRod(ModItems.rod_plutonium, PU.nugget());
        legacyRod(ModItems.rod_pu238, PU238.nugget());
        legacyRod(ModItems.rod_pu239, PU239.nugget());
        legacyRod(ModItems.rod_pu240, PU240.nugget());
        legacyRod(ModItems.rod_neptunium, NP237.nugget());
        legacyRod(ModItems.rod_polonium, PO210.nugget());
        legacyRod(ModItems.rod_lead, PB.nugget());
        legacyRod(ModItems.rod_schrabidium, SA326.nugget());
        legacyRod(ModItems.rod_solinium, SA327.nugget());
        legacyRod(ModItems.rod_uranium_fuel, ModItems.nugget_uranium_fuel);
        legacyRod(ModItems.rod_thorium_fuel, ModItems.nugget_thorium_fuel);
        legacyRod(ModItems.rod_plutonium_fuel, ModItems.nugget_plutonium_fuel);
        legacyRod(ModItems.rod_mox_fuel, ModItems.nugget_mox_fuel);
        legacyRod(ModItems.rod_schrabidium_fuel, ModItems.nugget_schrabidium_fuel);
        legacyRod(ModItems.rod_euphemium, EUPH.nugget());
        legacyRod(ModItems.rod_australium, AUSTRALIUM.nugget());
        legacyRod(ModItems.rod_weidanium, ModItems.nugget_weidanium);
        legacyRod(ModItems.rod_reiium, ModItems.nugget_reiium);
        legacyRod(ModItems.rod_unobtainium, ModItems.nugget_unobtainium);
        legacyRod(ModItems.rod_daffergon, ModItems.nugget_daffergon);
        legacyRod(ModItems.rod_verticium, ModItems.nugget_verticium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_balefire, 1), ModItems.rod_empty, ModItems.egg_balefire_shard);
        legacyRod(ModItems.rod_ac227, AC227.nugget());
        legacyRod(ModItems.rod_cobalt, CO.nugget());
        legacyRod(ModItems.rod_co60, CO60.nugget());
        legacyRod(ModItems.rod_ra226, RA226.nugget());
        legacyRod(ModItems.rod_rgp, PURG.nugget());

        CraftingManager.addRodBilletUnload(ModItems.billet_uranium, ModItems.rod_uranium);
        CraftingManager.addRodBilletUnload(ModItems.billet_u233, ModItems.rod_u233);
        CraftingManager.addRodBilletUnload(ModItems.billet_u235, ModItems.rod_u235);
        CraftingManager.addRodBilletUnload(ModItems.billet_u238, ModItems.rod_u238);
        CraftingManager.addRodBilletUnload(ModItems.billet_th232, ModItems.rod_th232);
        CraftingManager.addRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_plutonium);
        CraftingManager.addRodBilletUnload(ModItems.billet_pu238, ModItems.rod_pu238);
        CraftingManager.addRodBilletUnload(ModItems.billet_pu239, ModItems.rod_pu239);
        CraftingManager.addRodBilletUnload(ModItems.billet_pu240, ModItems.rod_pu240);
        CraftingManager.addRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_neptunium);
        CraftingManager.addRodBilletUnload(ModItems.billet_polonium, ModItems.rod_polonium);
        CraftingManager.addRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_schrabidium);
        CraftingManager.addRodBilletUnload(ModItems.billet_solinium, ModItems.rod_solinium);
        CraftingManager.addRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_uranium_fuel);
        CraftingManager.addRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_thorium_fuel);
        CraftingManager.addRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_plutonium_fuel);
        CraftingManager.addRodBillet(ModItems.billet_mox_fuel, ModItems.rod_mox_fuel);
        CraftingManager.addRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_schrabidium_fuel);
        CraftingManager.addRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_waste);
        CraftingManager.addRodBilletUnload(ModItems.billet_ac227, ModItems.rod_ac227);
        CraftingManager.addRodBilletUnload(ModItems.billet_ra226, ModItems.rod_ra226);
        CraftingManager.addRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_rgp);
        CraftingManager.addRodBilletUnload(ModItems.billet_co60, ModItems.rod_co60);

        legacyDualRod(ModItems.rod_dual_th232, TH232.ingot(), TH232.nugget());
        legacyDualRod(ModItems.rod_dual_uranium, U.ingot(), U.nugget());
        legacyDualRod(ModItems.rod_dual_u233, U233.ingot(), U233.nugget());
        legacyDualRod(ModItems.rod_dual_u235, U235.ingot(), U235.nugget());
        legacyDualRod(ModItems.rod_dual_u238, U238.ingot(), U238.nugget());
        legacyDualRod(ModItems.rod_dual_plutonium, PU.ingot(), PU.nugget());
        legacyDualRod(ModItems.rod_dual_pu238, PU238.ingot(), PU238.nugget());
        legacyDualRod(ModItems.rod_dual_pu239, PU239.ingot(), PU239.nugget());
        legacyDualRod(ModItems.rod_dual_pu240, PU240.ingot(), PU240.nugget());
        legacyDualRod(ModItems.rod_dual_neptunium, NP237.ingot(), NP237.nugget());
        legacyDualRod(ModItems.rod_dual_polonium, PO210.ingot(), PO210.nugget());
        legacyDualRod(ModItems.rod_dual_lead, PB.ingot(), PB.nugget());
        legacyDualRod(ModItems.rod_dual_schrabidium, SA326.ingot(), SA326.nugget());
        legacyDualRod(ModItems.rod_dual_solinium, SA327.ingot(), SA327.nugget());
        legacyDualRod(ModItems.rod_dual_uranium_fuel, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel);
        legacyDualRod(ModItems.rod_dual_thorium_fuel, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel);
        legacyDualRod(ModItems.rod_dual_plutonium_fuel, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel);
        legacyDualRod(ModItems.rod_dual_mox_fuel, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel);
        legacyDualRod(ModItems.rod_dual_schrabidium_fuel, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel);
        legacyDualRod(ModItems.rod_dual_balefire, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_euphemium, 1), ModItems.rod_quad_empty, EUPH.nugget());
        legacyDualRod(ModItems.rod_dual_ac227, AC227.ingot(), AC227.nugget());
        legacyDualRod(ModItems.rod_dual_cobalt, CO.ingot(), CO.nugget());
        legacyDualRod(ModItems.rod_dual_co60, CO60.ingot(), CO60.nugget());
        legacyDualRod(ModItems.rod_dual_ra226, RA226.ingot(), RA226.nugget());
        legacyDualRod(ModItems.rod_dual_rgp, PURG.ingot(), PURG.nugget());

        CraftingManager.addDualRodBilletUnload(ModItems.billet_uranium, ModItems.rod_dual_uranium);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_u233, ModItems.rod_dual_u233);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_u235, ModItems.rod_dual_u235);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_u238, ModItems.rod_dual_u238);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_th232, ModItems.rod_dual_th232);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_dual_plutonium);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_pu238, ModItems.rod_dual_pu238);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_pu239, ModItems.rod_dual_pu239);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_pu240, ModItems.rod_dual_pu240);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_dual_neptunium);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_polonium, ModItems.rod_dual_polonium);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_dual_schrabidium);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_solinium, ModItems.rod_dual_solinium);
        CraftingManager.addDualRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_dual_uranium_fuel);
        CraftingManager.addDualRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_dual_thorium_fuel);
        CraftingManager.addDualRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_dual_plutonium_fuel);
        CraftingManager.addDualRodBillet(ModItems.billet_mox_fuel, ModItems.rod_dual_mox_fuel);
        CraftingManager.addDualRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_dual_schrabidium_fuel);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_dual_waste);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_ac227, ModItems.rod_dual_ac227);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_ra226, ModItems.rod_dual_ra226);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_dual_rgp);
        CraftingManager.addDualRodBilletUnload(ModItems.billet_co60, ModItems.rod_dual_co60);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_lithium, 1), ModItems.rod_empty, LI.ingot());
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_lithium, 1), ModItems.rod_dual_empty, LI.ingot(), LI.ingot());
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_lithium, 1), ModItems.rod_quad_empty, LI.ingot(), LI.ingot(), LI.ingot(), LI.ingot());
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell, 1, Fluids.TRITIUM.getID()), ModItems.rod_tritium, new ItemStack(ModItems.cell));
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell, 2, Fluids.TRITIUM.getID()), ModItems.rod_dual_tritium, new ItemStack(ModItems.cell), new ItemStack(ModItems.cell));
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell, 4, Fluids.TRITIUM.getID()), ModItems.rod_quad_tritium, new ItemStack(ModItems.cell), new ItemStack(ModItems.cell), new ItemStack(ModItems.cell), new ItemStack(ModItems.cell));

        legacyQuadRod(ModItems.rod_quad_th232, TH232.ingot(), TH232.nugget());
        legacyQuadRod(ModItems.rod_quad_uranium, U.ingot(), U.nugget());
        legacyQuadRod(ModItems.rod_quad_u233, U233.ingot(), U233.nugget());
        legacyQuadRod(ModItems.rod_quad_u235, U235.ingot(), U235.nugget());
        legacyQuadRod(ModItems.rod_quad_u238, U238.ingot(), U238.nugget());
        legacyQuadRod(ModItems.rod_quad_plutonium, PU.ingot(), PU.nugget());
        legacyQuadRod(ModItems.rod_quad_pu238, PU238.ingot(), PU238.nugget());
        legacyQuadRod(ModItems.rod_quad_pu239, PU239.ingot(), PU239.nugget());
        legacyQuadRod(ModItems.rod_quad_pu240, PU240.ingot(), PU240.nugget());
        legacyQuadRod(ModItems.rod_quad_neptunium, NP237.ingot(), NP237.nugget());
        legacyQuadRod(ModItems.rod_quad_polonium, PO210.ingot(), PO210.nugget());
        legacyQuadRod(ModItems.rod_quad_lead, PB.ingot(), PB.nugget());
        legacyQuadRod(ModItems.rod_quad_schrabidium, SA326.ingot(), SA326.nugget());
        legacyQuadRod(ModItems.rod_quad_solinium, SA327.ingot(), SA327.nugget());
        legacyQuadRod(ModItems.rod_quad_uranium_fuel, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel);
        legacyQuadRod(ModItems.rod_quad_thorium_fuel, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel);
        legacyQuadRod(ModItems.rod_quad_plutonium_fuel, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel);
        legacyQuadRod(ModItems.rod_quad_mox_fuel, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel);
        legacyQuadRod(ModItems.rod_quad_schrabidium_fuel, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_balefire, 1), ModItems.rod_quad_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard);
        legacyQuadRod(ModItems.rod_quad_ac227, AC227.ingot(), AC227.nugget());
        legacyQuadRod(ModItems.rod_quad_cobalt, CO.ingot(), CO.nugget());
        legacyQuadRod(ModItems.rod_quad_co60, CO60.ingot(), CO60.nugget());
        legacyQuadRod(ModItems.rod_quad_ra226, RA226.ingot(), RA226.nugget());
        legacyQuadRod(ModItems.rod_quad_rgp, PURG.ingot(), PURG.nugget());

        CraftingManager.addQuadRodBilletUnload(ModItems.billet_uranium, ModItems.rod_quad_uranium);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_u233, ModItems.rod_quad_u233);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_u235, ModItems.rod_quad_u235);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_u238, ModItems.rod_quad_u238);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_th232, ModItems.rod_quad_th232);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_quad_plutonium);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_pu238, ModItems.rod_quad_pu238);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_pu239, ModItems.rod_quad_pu239);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_pu240, ModItems.rod_quad_pu240);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_quad_neptunium);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_polonium, ModItems.rod_quad_polonium);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_quad_schrabidium);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_solinium, ModItems.rod_quad_solinium);
        CraftingManager.addQuadRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_quad_uranium_fuel);
        CraftingManager.addQuadRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_quad_thorium_fuel);
        CraftingManager.addQuadRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_quad_plutonium_fuel);
        CraftingManager.addQuadRodBillet(ModItems.billet_mox_fuel, ModItems.rod_quad_mox_fuel);
        CraftingManager.addQuadRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_quad_schrabidium_fuel);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_quad_waste);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_ac227, ModItems.rod_quad_ac227);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_ra226, ModItems.rod_quad_ra226);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_quad_rgp);
        CraftingManager.addQuadRodBilletUnload(ModItems.billet_co60, ModItems.rod_quad_co60);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_water, 1), ModItems.rod_empty, Items.WATER_BUCKET);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_water, 1), ModItems.rod_dual_empty, Items.WATER_BUCKET, Items.WATER_BUCKET);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_water, 1), ModItems.rod_quad_empty, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 6), ModItems.rod_lead);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 1), ModItems.rod_lithium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 6), ModItems.rod_cobalt);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_australium, 6), ModItems.rod_australium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_weidanium, 6), ModItems.rod_weidanium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_reiium, 6), ModItems.rod_reiium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_unobtainium, 6), ModItems.rod_unobtainium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_daffergon, 6), ModItems.rod_daffergon);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_verticium, 6), ModItems.rod_verticium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_euphemium, 6), ModItems.rod_euphemium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), ModItems.rod_balefire);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), ModItems.rod_balefire_blazing);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 12), ModItems.rod_dual_lead);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 2), ModItems.rod_dual_lithium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 12), ModItems.rod_dual_cobalt);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), ModItems.rod_dual_balefire);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), ModItems.rod_dual_balefire_blazing);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 24), ModItems.rod_quad_lead);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 4), ModItems.rod_quad_lithium);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 24), ModItems.rod_quad_cobalt);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), ModItems.rod_quad_balefire);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), ModItems.rod_quad_balefire_blazing);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 1), ModItems.rod_uranium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 2), ModItems.rod_dual_uranium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 4), ModItems.rod_quad_uranium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 1), ModItems.rod_thorium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 2), ModItems.rod_dual_thorium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 4), ModItems.rod_quad_thorium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 1), ModItems.rod_plutonium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 2), ModItems.rod_dual_plutonium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 4), ModItems.rod_quad_plutonium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 1), ModItems.rod_mox_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 2), ModItems.rod_dual_mox_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 4), ModItems.rod_quad_mox_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 1), ModItems.rod_schrabidium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 2), ModItems.rod_dual_schrabidium_fuel_depleted);
        CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 4), ModItems.rod_quad_schrabidium_fuel_depleted);

        CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_euphemium, 1), ModItems.rod_quad_euphemium);
    }

    private static void legacyRod(Item out, Object nugget) {
        CraftingManager.addShapelessAuto(new ItemStack(out), ModItems.rod_empty, nugget, nugget, nugget, nugget, nugget, nugget);
    }

    private static void legacyDualRod(Item out, Object ingot, Object nugget) {
        CraftingManager.addShapelessAuto(new ItemStack(out), ModItems.rod_dual_empty, ingot, nugget, nugget, nugget);
    }

    private static void legacyQuadRod(Item out, Object ingot, Object nugget) {
        CraftingManager.addShapelessAuto(new ItemStack(out), ModItems.rod_quad_empty, ingot, ingot, nugget, nugget, nugget, nugget, nugget, nugget);
    }
}