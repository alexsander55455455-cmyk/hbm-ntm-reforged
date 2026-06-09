package com.hbm.items.machine;

import com.hbm.Tags;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.CrucibleRecipe;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemCrucibleTemplate extends Item {

    public static final ModelResourceLocation cruciModel = new ModelResourceLocation(Tags.MODID + ":crucible_template", "inventory");

    public ItemCrucibleTemplate(String s) {
        this.setTranslationKey(s);
        this.setRegistryName(s);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        ModItems.ALL_ITEMS.add(this);
    }

    public static CrucibleRecipe getRecipe(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta >= 0 && meta < CrucibleRecipes.INSTANCE.recipeOrderedList.size()) {
            return CrucibleRecipes.INSTANCE.recipeOrderedList.get(meta);
        }
        return null;
    }

    public static ItemStack getIcon(ItemStack stack) {
        CrucibleRecipe recipe = getRecipe(stack);
        if (recipe != null) {
            ItemStack icon = recipe.getIcon();
            if (icon != null && !icon.isEmpty()) return icon.copy();
        }
        return ItemStack.EMPTY;
    }

    public static int getMeta(CrucibleRecipe recipe) {
        return CrucibleRecipes.INSTANCE.recipeOrderedList.indexOf(recipe);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String s = ("" + I18nUtil.resolveKey(this.getTranslationKey() + ".name")).trim();
        CrucibleRecipe recipe = getRecipe(stack);
        if (recipe != null) {
            s = s + " " + recipe.getLocalizedName();
        }
        return s;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
            for (int i = 0; i < CrucibleRecipes.INSTANCE.recipeOrderedList.size(); i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
        CrucibleRecipe recipe = getRecipe(stack);
        if (recipe == null) return;

        list.add("§l" + I18nUtil.resolveKey("info.template_out_p"));
        for (MaterialStack out : recipe.output) {
            list.add(" §a" + I18nUtil.resolveKey(out.material.getTranslationKey()) + ": " + Mats.formatAmount(out.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
        }

        list.add("§l" + I18nUtil.resolveKey("info.template_in_p"));
        for (MaterialStack in : recipe.input) {
            list.add(" §c" + I18nUtil.resolveKey(in.material.getTranslationKey()) + ": " + Mats.formatAmount(in.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
        }
    }
}