package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArmorAsbestosFSB extends ArmorFSB {

    private static final float DAMAGE_THRESHOLD = 2F;

    public ArmorAsbestosFSB(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, String texture, String name) {
        super(material, renderIndex, slot, texture, name);
    }

    public static boolean hasFullSet(EntityLivingBase entity) {
        return isEquipped(entity, EntityEquipmentSlot.HEAD, ModItems.asbestos_helmet)
                && isEquipped(entity, EntityEquipmentSlot.CHEST, ModItems.asbestos_plate)
                && isEquipped(entity, EntityEquipmentSlot.LEGS, ModItems.asbestos_legs)
                && isEquipped(entity, EntityEquipmentSlot.FEET, ModItems.asbestos_boots);
    }

    private static boolean isEquipped(EntityLivingBase entity, EntityEquipmentSlot slot, Item item) {
        ItemStack stack = entity.getItemStackFromSlot(slot);
        return !stack.isEmpty() && stack.getItem() == item && stack.getItem() instanceof ArmorFSB armor && armor.isArmorEnabled(stack);
    }

    public static void handleAsbestosAttack(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!hasFullSet(entity)) {
            return;
        }

        DamageSource source = event.getSource();
        if (source.isFireDamage()) {
            entity.extinguish();
            event.setCanceled(true);
            return;
        }

        if (!source.isUnblockable() && DAMAGE_THRESHOLD >= event.getAmount()) {
            event.setCanceled(true);
        }
    }

    public static void handleAsbestosHurt(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!hasFullSet(entity)) {
            return;
        }

        DamageSource source = event.getSource();
        if (source.isFireDamage()) {
            entity.extinguish();
            event.setAmount(0F);
            return;
        }

        if (!source.isUnblockable()) {
            event.setAmount(Math.max(0F, event.getAmount() - DAMAGE_THRESHOLD));
        }
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot slot, Entity entity) {
        if (stack.getItem() == ModItems.asbestos_helmet) {
            return slot == EntityEquipmentSlot.HEAD;
        }
        if (stack.getItem() == ModItems.asbestos_plate) {
            return slot == EntityEquipmentSlot.CHEST;
        }
        if (stack.getItem() == ModItems.asbestos_legs) {
            return slot == EntityEquipmentSlot.LEGS;
        }
        if (stack.getItem() == ModItems.asbestos_boots) {
            return slot == EntityEquipmentSlot.FEET;
        }
        return super.isValidArmor(stack, slot, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, World worldIn, @NotNull List<String> list, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, list, flagIn);
        list.add(TextFormatting.GOLD + I18nUtil.resolveKey("armor.fullSetBonus"));
        list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.threshold", DAMAGE_THRESHOLD));
        list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.fireproof"));
    }
}
