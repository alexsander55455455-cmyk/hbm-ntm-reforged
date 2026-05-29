package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class GunBrimstone extends Item {

    public GunBrimstone(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (player.getHeldItemMainhand() == stack) {
            ItemStack off = player.getHeldItemOffhand();
            if (off.getItem() == ModItems.gun_brimstone) {
                off.getItem().onUsingTick(off, player, count);
            }
        }

        if (!(player instanceof EntityPlayer)) return;
        EntityPlayer entityPlayer = (EntityPlayer) player;
        World world = entityPlayer.world;

        boolean infinite = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

        if (infinite || Library.hasInventoryItem(entityPlayer.inventory, ModItems.ammo_566_gold)) {
            if (count % 1 == 0) {
                EnumHand hand = (entityPlayer.getHeldItemMainhand() == stack) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                EntityLaser laser = new EntityLaser(world, entityPlayer, hand);
                if (!infinite) {
                    Library.consumeInventoryItem(entityPlayer.inventory, ModItems.gun_dash_ammo);
                }
                if (!world.isRemote) {
                    world.spawnEntity(laser);
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 5.0D, 0));
        }
        return multimap;
    }
}
