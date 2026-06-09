package com.hbm.forgefluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.FluidCombustionRecipes;
import com.hbm.inventory.recipes.HeatRecipes;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class FFUtils {

    public static void addFluidInfo(Fluid fluid, List<String> texts, boolean isAdvanced) {
        addFluidInfo(fluid, texts, isAdvanced, "");
    }

    public static void addFluidInfo(Fluid fluid, List<String> texts, boolean isAdvanced, String indent) {
        boolean isKeyPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        boolean hasInfo = false;

        int temp = fluid.getTemperature() - 273;
        if (temp != 27) {
            String tempColor;
            if (temp < -130) {
                tempColor = "§3";
            } else if (temp < 0) {
                tempColor = "§b";
            } else if (temp < 100) {
                tempColor = "§e";
            } else if (temp < 300) {
                tempColor = "§6";
            } else if (temp < 1000) {
                tempColor = "§c";
            } else if (temp < 3000) {
                tempColor = "§4";
            } else if (temp < 20000) {
                tempColor = "§5";
            } else {
                tempColor = "§d";
            }
            texts.add(indent + String.format("%s%d°C", tempColor, temp));
        }

        if (isKeyPressed && isAdvanced) {
            texts.add(indent + "§bFluid Key: §3" + FluidRegistry.getDefaultFluidName(fluid));
        }

        if (FluidTypeHandler.isAntimatter(fluid)) {
            if (isKeyPressed) {
                texts.add(indent + "§4[" + I18n.format("trait.antimatter") + "]");
            }
            hasInfo = true;
        }

        if (FluidTypeHandler.isCorrosivePlastic(fluid)) {
            if (FluidTypeHandler.isCorrosiveIron(fluid)) {
                if (isKeyPressed) {
                    texts.add(indent + "§2[" + I18n.format("trait.corrosiveIron") + "]");
                }
            } else if (isKeyPressed) {
                texts.add(indent + "§a[" + I18n.format("trait.corrosivePlastic") + "]");
            }
            hasInfo = true;
        }

        FluidType type = fromForgeFluid(fluid);
        if (FluidCombustionRecipes.hasFuelRecipe(type)) {
            if (isKeyPressed) {
                texts.add(indent + "§6[" + I18n.format("trait.flammable") + "]");
                texts.add(indent + " " + I18n.format("trait.flammable.desc", Library.getShortNumber(FluidCombustionRecipes.getFlameEnergy(type) * 1000L)));
            }
            hasInfo = true;
        }

        if (HeatRecipes.hasCoolRecipe(type)) {
            if (isKeyPressed) {
                HeatRecipes.HeatRecipe cool = HeatRecipes.getCoolRecipe(type);
                String heat = Library.getShortNumber(cool.heat * 1000L / cool.input.fill);
                texts.add(indent + "§4[" + I18n.format("trait.coolable") + "]");
                texts.add(indent + " " + I18n.format("trait.coolable.desc", heat));
            }
            hasInfo = true;
        }

        if (HeatRecipes.hasBoilRecipe(type)) {
            if (isKeyPressed) {
                HeatRecipes.HeatRecipe boil = HeatRecipes.getBoilRecipe(type);
                String heat = Library.getShortNumber(boil.heat * 1000L / boil.input.fill);
                texts.add(indent + "§3[" + I18n.format("trait.boilable") + "]");
                texts.add(indent + " " + I18n.format("trait.boilable.desc", heat));
            }
            hasInfo = true;
        }

        float dfcEff = FluidTypeHandler.getDFCEfficiency(fluid);
        if (dfcEff >= 1) {
            if (isKeyPressed) {
                texts.add(indent + "§5[" + I18n.format("trait.dfcFuel") + "]");
                dfcEff = (dfcEff - 1F);
                texts.add(indent + " " + I18n.format("trait.dfcFuel.desc", dfcEff >= 0 ? "+" + Library.getPercentage(dfcEff) : Library.getPercentage(dfcEff)));
            }
            hasInfo = true;
        }

        if (hasInfo && !isKeyPressed) {
            texts.add(indent + I18nUtil.resolveKey("desc.tooltip.hold", "LSHIFT"));
        }
    }

    private static FluidType fromForgeFluid(Fluid fluid) {
        if (fluid == null) return Fluids.NONE;
        for (FluidType type : Fluids.metaOrder) {
            if (type.getFF() == fluid) return type;
        }
        return Fluids.fromNameCompat(fluid.getName());
    }

    public static NBTTagList serializeTankArray(FluidTank[] tanks) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < tanks.length; i++) {
            if (tanks[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("tank", (byte) i);
                tanks[i].writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        return list;
    }

    public static void deserializeTankArray(NBTTagList tankList, FluidTank[] tanks) {
        for (int i = 0; i < tankList.tagCount(); i++) {
            NBTTagCompound tag = tankList.getCompoundTagAt(i);
            byte b0 = tag.getByte("tank");
            if (b0 >= 0 && b0 < tanks.length) {
                tanks[b0].readFromNBT(tag);
            }
        }
    }

}
