package com.hbm.crafting;

import com.hbm.items.ModItems;
import com.hbm.main.CraftingManager;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.OreDictManager.AL;
import static com.hbm.inventory.OreDictManager.STEEL;

public class NukeRecipes {

    public static void register() {
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.early_explosive_lenses, 1),
                "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', AL.plate());
        CraftingManager.addRecipeAuto(new ItemStack(ModItems.explosive_lenses, 1),
                "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', STEEL.plate());
    }
}