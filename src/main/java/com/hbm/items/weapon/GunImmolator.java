package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GunImmolator extends Item {

    private final Random rand = new Random();

    public GunImmolator(String name) {
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
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        if (player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_immolator) {
            player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
        }

        if (player.isSneaking()) {
            shootPlasma(stack, player, count);
        } else {
            shootFire(stack, player, count);
        }
    }

    private void shootFire(ItemStack stack, EntityPlayer player, int count) {
        World world = player.world;
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if (player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_immolator_ammo)) {
            EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntityFire fire = new EntityFire(world, player, 3.0F, hand);
            fire.setDamage(6 + this.rand.nextInt(5));
            if (infinite) {
                fire.canBePickedUp = 2;
            } else if (count % 10 == 0) {
                Library.consumeInventoryItem(player.inventory, ModItems.gun_immolator_ammo);
            }
            if (count == this.getMaxItemUseDuration(stack)) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerIgnite, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (count % 5 == 0) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (!world.isRemote) {
                world.spawnEntity(fire);
            }
        }
    }

    private void shootPlasma(ItemStack stack, EntityPlayer player, int count) {
        World world = player.world;
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if (player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_immolator_ammo)) {
            EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, player, 1.0F, hand);
            if (infinite) {
                plasma.canBePickedUp = 2;
            } else if (count % 4 == 0) {
                Library.consumeInventoryItem(player.inventory, ModItems.gun_immolator_ammo);
            }
            if (count == this.getMaxItemUseDuration(stack)) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorIgnite, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (count % 10 == 0) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (!world.isRemote) {
                world.spawnEntity(plasma);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Hold right mouse button");
        tooltip.add("to shoot fire,");
        tooltip.add("sneak to shoot");
        tooltip.add("plasma beams!");
        tooltip.add("");
        tooltip.add("Ammo: \u00a7cImmolator Fuel");
        tooltip.add("Damage: 5");
        tooltip.add("Secondary Damage: 25 - 45");
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.0D, 0));
        }
        return multimap;
    }
}
