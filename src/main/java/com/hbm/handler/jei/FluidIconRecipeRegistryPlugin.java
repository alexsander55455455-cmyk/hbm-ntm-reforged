package com.hbm.handler.jei;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeRegistryPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FluidIconRecipeRegistryPlugin implements IRecipeRegistryPlugin {
    @Nullable
    private IRecipeRegistry recipeRegistry;
    @Nullable
    private IRecipeRegistryPlugin internalRecipePlugin;

    public void setRecipeRegistry(@Nullable IRecipeRegistry recipeRegistry) {
        this.recipeRegistry = recipeRegistry;
        this.internalRecipePlugin = findInternalRecipePlugin(recipeRegistry);
    }

    @Override
    public <V> List<String> getRecipeCategoryUids(IFocus<V> focus) {
        IFocus<FluidStack> fluidFocus = createFluidFocus(focus);
        if (fluidFocus == null) {
            return Collections.emptyList();
        }

        IRecipeRegistryPlugin plugin = this.internalRecipePlugin;
        if (plugin == null) {
            return Collections.emptyList();
        }

        List<String> categoryUids = plugin.getRecipeCategoryUids(fluidFocus);
        if (categoryUids.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> uids = new ArrayList<>(categoryUids.size());
        for (String uid : categoryUids) {
            if (!uids.contains(uid)) {
                uids.add(uid);
            }
        }
        return uids;
    }

    @Override
    public <T extends IRecipeWrapper, V> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
        IFocus<FluidStack> fluidFocus = createFluidFocus(focus);
        if (fluidFocus == null) {
            return Collections.emptyList();
        }

        IRecipeRegistryPlugin plugin = this.internalRecipePlugin;
        if (plugin == null) {
            return Collections.emptyList();
        }

        return plugin.getRecipeWrappers(recipeCategory, fluidFocus);
    }

    @Override
    public <T extends IRecipeWrapper> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory) {
        return Collections.emptyList();
    }

    @Nullable
    private <V> IFocus<FluidStack> createFluidFocus(IFocus<V> focus) {
        IRecipeRegistry registry = this.recipeRegistry;
        if (registry == null) {
            return null;
        }

        V value = focus.getValue();
        if (!(value instanceof ItemStack stack) || stack.isEmpty() || stack.getItem() != ModItems.fluid_icon) {
            return null;
        }

        FluidType fluidType = ItemFluidIcon.getFluidType(stack);
        if (fluidType == null) {
            return null;
        }

        Fluid forgeFluid = fluidType.getFF();
        if (forgeFluid == null) {
            return null;
        }

        int amount = ItemFluidIcon.getQuantity(stack);
        if (amount <= 0) {
            amount = Fluid.BUCKET_VOLUME;
        }

        return registry.createFocus(focus.getMode(), new FluidStack(forgeFluid, amount));
    }

    @Nullable
    private static IRecipeRegistryPlugin findInternalRecipePlugin(@Nullable IRecipeRegistry recipeRegistry) {
        if (recipeRegistry == null) {
            return null;
        }

        try {
            // JEI's safe plugin wrapper is not re-entrant, so skip back to its internal lookup plugin directly.
            Field pluginsField = recipeRegistry.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            Object pluginsObject = pluginsField.get(recipeRegistry);
            if (!(pluginsObject instanceof List<?> plugins)) {
                return null;
            }

            for (Object wrapper : plugins) {
                IRecipeRegistryPlugin plugin = unwrapRecipePlugin(wrapper);
                if (plugin != null && "mezz.jei.recipes.InternalRecipeRegistryPlugin".equals(plugin.getClass().getName())) {
                    return plugin;
                }
            }
        } catch (ReflectiveOperationException | SecurityException ignored) {
        }

        return null;
    }

    @Nullable
    private static IRecipeRegistryPlugin unwrapRecipePlugin(Object wrapper) throws ReflectiveOperationException {
        if (wrapper instanceof IRecipeRegistryPlugin plugin && "mezz.jei.recipes.InternalRecipeRegistryPlugin".equals(wrapper.getClass().getName())) {
            return plugin;
        }

        Field pluginField = wrapper.getClass().getDeclaredField("plugin");
        pluginField.setAccessible(true);
        Object plugin = pluginField.get(wrapper);
        return plugin instanceof IRecipeRegistryPlugin recipePlugin ? recipePlugin : null;
    }
}
