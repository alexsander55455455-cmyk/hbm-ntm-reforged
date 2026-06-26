package com.hbm.handler.jei;

import com.hbm.Tags;
import com.hbm.handler.jei.JeiRecipes.FusionRecipe;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.recipes.FusionRecipesLegacy;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ITERFusionByproductHandler implements IRecipeCategory<FusionRecipe> {

    public static final ResourceLocation GUI_RL = new ResourceLocation(Tags.MODID, "textures/gui/jei/gui_nei_fusion.png");

    protected final IDrawable background;
    private final List<FusionRecipe> recipes;

    public ITERFusionByproductHandler(IGuiHelper helper) {
        background = helper.createDrawable(GUI_RL, 33, 33, 109, 19);
        recipes = buildRecipes();
    }

    private static List<FusionRecipe> buildRecipes() {
        List<FusionRecipe> list = new ArrayList<>();
        for (Map.Entry<FluidType, ItemStack> entry : FusionRecipesLegacy.byproducts.entrySet()) {
            ItemStack out = FusionRecipesLegacy.getByproduct(entry.getKey());
            if (out != null) {
                list.add(new FusionRecipe(new FluidStack(entry.getKey(), 1000), out));
            }
        }
        return list;
    }

    public List<FusionRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public String getUid() {
        return JEIConfig.ITER_BYPRODUCT;
    }

    @Override
    public String getTitle() {
        return I18nUtil.resolveKey("jei.iter_byproduct");
    }

    @Override
    public String getModName() {
        return Tags.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FusionRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 1, 1);
        guiItemStacks.init(1, false, 91, 1);
        guiItemStacks.set(ingredients);
    }
}