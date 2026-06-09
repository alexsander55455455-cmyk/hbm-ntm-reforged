package com.hbm.handler.jei;

import com.hbm.Tags;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.CrucibleRecipe;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCrucibleTemplate;
import com.hbm.items.machine.ItemScraps;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrucibleAlloyingHandler implements IRecipeCategory<CrucibleAlloyingHandler.Wrapper> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Tags.MODID, "textures/gui/jei/gui_nei_crucible_mixing.png");

    private final IDrawable background;

    public CrucibleAlloyingHandler(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(GUI_TEXTURE, 16, 16, 108, 36);
    }

    @Override
    public @NotNull String getUid() {
        return JEIConfig.CRUCIBLE_ALLOY;
    }

    @Override
    public @NotNull String getTitle() {
        return I18nUtil.resolveKey("container.foundryMixing");
    }

    @Override
    public @NotNull String getModName() {
        return Tags.MODID;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull Wrapper wrapper, @NotNull IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

        stacks.init(0, true, 45, 0);
        stacks.init(1, true, 0, 0);
        stacks.init(2, true, 18, 0);
        stacks.init(3, true, 0, 18);
        stacks.init(4, true, 18, 18);
        stacks.init(5, false, 72, 0);
        stacks.init(6, false, 90, 0);
        stacks.init(7, false, 72, 18);
        stacks.init(8, false, 90, 18);

        stacks.set(ingredients);
    }

    public static class Wrapper implements IRecipeWrapper {
        final List<ItemStack> inputs;
        final List<ItemStack> outputs;

        public Wrapper(CrucibleRecipe recipe) {
            this.inputs = new ArrayList<>();
            this.outputs = Mats.matsToScrap(recipe.output, false);
            ItemStack template = new ItemStack(ModItems.crucible_template, 1, ItemCrucibleTemplate.getMeta(recipe));
            this.inputs.add(template);
            for (Mats.MaterialStack s : recipe.input) this.inputs.add(ItemScraps.create(s, false));
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
            ingredients.setOutputs(VanillaTypes.ITEM, outputs);
        }
    }

    public static List<Wrapper> getRecipes() {
        List<Wrapper> list = new ArrayList<>();
        for (CrucibleRecipe r : CrucibleRecipes.INSTANCE.recipeOrderedList) {
            list.add(new Wrapper(r));
        }
        return list;
    }
}