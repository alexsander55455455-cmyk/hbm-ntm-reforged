package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import java.util.List;

public class GunSuicide extends Item {

    private Item ammo;

    public GunSuicide(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(500);
        this.ammo = ModItems.gun_revolver_ammo;
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if(!(entityLiving instanceof EntityPlayer)) {
            return;
        }

        if(this.ammo == null) {
            this.ammo = ModItems.gun_revolver_ammo;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        int charge = this.getMaxItemUseDuration(stack) - timeLeft;
        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, world, charge, Library.hasInventoryItem(player.inventory, this.ammo));
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled()) {
            return;
        }

        charge = event.getCharge();
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

        if(!infinite && !Library.hasInventoryItem(player.inventory, this.ammo)) {
            return;
        }

        if(charge < 10) {
            return;
        }

        stack.damageItem(1, player);
        world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.revolverShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if(!infinite) {
            Library.consumeInventoryItem(player.inventory, this.ammo);
        }

        if(!world.isRemote) {
            player.attackEntityFrom(ModDamageSource.suicide, 10000.0F);
            if(!player.capabilities.isCreativeMode) {
                player.setHealth(0.0F);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if(this.ammo == null) {
            this.ammo = ModItems.gun_revolver_ammo;
        }

        ItemStack stack = player.getHeldItem(hand);
        ArrowNockEvent event = new ArrowNockEvent(player, stack, hand, world, Library.hasInventoryItem(player.inventory, this.ammo));
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled()) {
            return event.getAction();
        }

        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add("I've seen things...");
        tooltip.add("...I shouldn't have seen.");
        tooltip.add("");
        tooltip.add("Ammo: \u00a7eLead Bullets");
        tooltip.add("Damage: Infinite");
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2.5D, 0));
        }
        return multimap;
    }
}
