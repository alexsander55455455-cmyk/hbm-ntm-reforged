package com.hbm.handler.jei;

import com.hbm.Tags;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrucibleCastingHandler implements IRecipeCategory<CrucibleCastingHandler.Wrapper> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Tags.MODID, "textures/gui/jei/gui_nei_crucible_casting.png");

    private final IDrawable background;

    public CrucibleCastingHandler(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(GUI_TEXTURE, 16, 16, 126, 54);
    }

    @Override
    public @NotNull String getUid() {
        return JEIConfig.CRUCIBLE_CAST;
    }

    @Override
    public @NotNull String getTitle() {
        return I18nUtil.resolveKey("container.foundryCasting");
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

        stacks.init(0, true, 54, 32);
        stacks.init(1, true, 54, 4);
        stacks.init(2, true, 18, 18);
        stacks.init(3, false, 90, 18);

        stacks.set(ingredients);
    }

    public static class Wrapper implements IRecipeWrapper {
        private final List<ItemStack> inputs;
        private final ItemStack output;

        public Wrapper(ItemStack basin, ItemStack mold, ItemStack input, ItemStack output) {
            this.inputs = new ArrayList<>();
            this.inputs.add(basin.copy());
            this.inputs.add(mold.copy());
            this.inputs.add(input.copy());
            this.output = output.copy();
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
            ingredients.setOutput(VanillaTypes.ITEM, output);
        }
    }

    public List<Wrapper> getRecipes() {
        List<Wrapper> list = new ArrayList<>();
        for (ItemStack[] r : CrucibleRecipes.getMoldRecipes()) {
            list.add(new Wrapper(r[2], r[1], r[0], r[3]));
        }
        return list;
    }

    public static List<Wrapper> buildRecipes() {
        List<Wrapper> list = new ArrayList<>();
        for (com.hbm.inventory.material.NTMMaterial material : Mats.orderedList) {
            if (material.smeltable != com.hbm.inventory.material.NTMMaterial.SmeltingBehavior.SMELTABLE) continue;
            for (ItemMold.Mold mold : ItemMold.molds) {
                ItemStack out = mold.getOutput(material);
                if (!out.isEmpty()) {
                    ItemStack scrap = ItemScraps.create(new Mats.MaterialStack(material, mold.getCost()), false);
                    ItemStack moldStack = new ItemStack(ModItems.mold, 1, mold.id);
                    ItemStack basin = new ItemStack(mold.size == 0 ? ModBlocks.foundry_mold : mold.size == 1 ? ModBlocks.foundry_basin : Blocks.FIRE);
                    list.add(new Wrapper(basin, moldStack, scrap, out));
                }
            }
        }
        return list;
    }
}