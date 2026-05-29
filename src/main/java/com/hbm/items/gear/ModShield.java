package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.inventory.EntityEquipmentSlot;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.List;

public class ModShield extends ItemShield {
    public static String materialOd;

    public ModShield(String name, ToolMaterial material, String materialOd) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(com.hbm.main.MainRegistry.weaponTab);
        ModShield.materialOd = materialOd;
        ModItems.ALL_ITEMS.add(this);
    }

    public ModShield(String name, int maxDamage, String materialOd) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(maxDamage);
        this.setCreativeTab(com.hbm.main.MainRegistry.weaponTab);
        ModShield.materialOd = materialOd;
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return super.getIsRepairable(toRepair, repair);
    }

    public void handleImpact(Item shield, Entity attacker, float damage) {
        if (shield == ModItems.cobalt_shield) {
            if (attacker instanceof EntityLivingBase) {
                ((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) damage + 20, 1));
            }
        } else if (shield == ModItems.starmetal_shield) {
            if (attacker instanceof EntityLivingBase) {
                ((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) damage + 20, 1));
            }
        } else if (shield == ModItems.cmb_shield) {
            if (attacker instanceof EntityLivingBase) {
                ((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(MobEffects.POISON, (int) damage + 20, 1));
            }
        } else if (shield == ModItems.schrabidium_shield) {
            if (attacker instanceof EntityLivingBase) {
                ContaminationUtil.contaminate((EntityLivingBase) attacker, HazardType.RADIATION, ContaminationType.CREATIVE, damage);
            }
            attacker.setFire(2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Item item = stack.getItem();
        if (item == ModItems.cobalt_shield) {
            tooltip.add("\u00a7eReflects Damage as Hunger Duration");
        } else if (item == ModItems.starmetal_shield) {
            tooltip.add("\u00a7eReflects Damage as Slowness Duration");
        } else if (item == ModItems.cmb_shield) {
            tooltip.add("\u00a7eReflects Damage as Poison Duration");
        } else if (item == ModItems.schrabidium_shield) {
            tooltip.add("\u00a7eReflects Damage as Radiation");
            tooltip.add("\u00a7eSets Attacker on Fire");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

    public boolean isShield(ItemStack stack, EntityLivingBase entity) {
        return !stack.isEmpty() && stack.getItem() instanceof ModShield;
    }
}
