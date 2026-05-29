package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GunEuthanasia extends Item {

    private final Random rand = new Random();

    public GunEuthanasia(String name) {
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
        if (player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_euthanasia) {
            player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
        }

        World world = player.world;
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_euthanasia_ammo)) && count % 8 == 0) {
            EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntityBullet bullet = new EntityBullet(world, player, 3.0F, 2, 8, this.rand.nextInt(5) == 0, false, hand);
            bullet.setDamage(1 + this.rand.nextInt(3));
            bullet.antidote = true;
            world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.rifleShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
            if (infinite) {
                bullet.canBePickedUp = 2;
            } else {
                Library.consumeInventoryItem(player.inventory, ModItems.gun_euthanasia_ammo);
            }
            if (!world.isRemote) {
                world.spawnEntity(bullet);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Say hello to my little syringe gun!");
        tooltip.add("");
        tooltip.add("Ammo: \u00a75Syringe");
        tooltip.add("Damage: 1 - 4");
        tooltip.add("Syringes have a 20% chance to instakill the enemy.");
        tooltip.add("");
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.0D, 0));
        }
        return multimap;
    }
}
