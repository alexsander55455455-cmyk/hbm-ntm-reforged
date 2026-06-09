package com.hbm.handler.jei;

import com.hbm.Tags;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.recipes.CrucibleRecipes;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrucibleSmeltingHandler implements IRecipeCategory<CrucibleSmeltingHandler.Wrapper> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Tags.MODID, "textures/gui/jei/gui_nei_crucible_smelting.png");

    private final IDrawable background;

    public CrucibleSmeltingHandler(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(GUI_TEXTURE, 16, 16, 126, 54);
    }

    @Override
    public @NotNull String getUid() {
        return JEIConfig.CRUCIBLE_SMELT;
    }

    @Override
    public @NotNull String getTitle() {
        return I18nUtil.resolveKey("container.foundrySmelting");
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

        stacks.init(0, true, 18, 18);
        stacks.init(1, false, 72, 0);
        stacks.init(2, false, 90, 0);
        stacks.init(3, false, 108, 0);
        stacks.init(4, false, 72, 18);
        stacks.init(5, false, 90, 18);
        stacks.init(6, false, 108, 18);
        stacks.init(7, false, 70, 36);
        stacks.init(8, false, 90, 36);
        stacks.init(9, false, 108, 36);

        stacks.set(ingredients);
    }

    public static class Wrapper implements IRecipeWrapper {
        final List<ItemStack> inputs;
        final List<ItemStack> outputs;

        public Wrapper(RecipesCommon.AStack input, List<ItemStack> outputs) {
            this.inputs = new ArrayList<>();
            for (ItemStack s : input.extractForJEI()) this.inputs.add(s.copy());
            this.outputs = new ArrayList<>(outputs.size());
            for (ItemStack out : outputs) this.outputs.add(out.copy());
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
            ingredients.setOutputs(VanillaTypes.ITEM, outputs);
        }
    }

    public static List<Wrapper> getRecipes() {
        List<Wrapper> list = new ArrayList<>();
        HashMap<RecipesCommon.AStack, List<ItemStack>> smelting = CrucibleRecipes.getSmeltingRecipes();
        for (Map.Entry<RecipesCommon.AStack, List<ItemStack>> e : smelting.entrySet()) {
            list.add(new Wrapper(e.getKey(), e.getValue()));
        }
        return list;
    }
}