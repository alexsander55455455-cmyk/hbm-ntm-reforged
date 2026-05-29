package com.hbm.handler.jei;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class PWRRecipeHandler extends JEIUniversalHandler {

    public PWRRecipeHandler(IGuiHelper helper) {
        super(helper, JEIConfig.PWR, ModBlocks.pwr_controller.getTranslationKey(),
                new ItemStack[] { new ItemStack(ModBlocks.pwr_controller) }, getFuelRecipes());
    }

    public static HashMap<Object, Object> getFuelRecipes() {
        HashMap<Object, Object> map = new HashMap<>();

        for (EnumPWRFuel fuel : EnumPWRFuel.VALUES) {
            map.put(new ItemStack(ModItems.pwr_fuel, 1, fuel.ordinal()),
                    new ItemStack(ModItems.pwr_fuel_hot, 1, fuel.ordinal()));
        }

        return map;
    }
}
