package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
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

public class GunDefabricator extends Item {

    private final Random rand = new Random();

    public GunDefabricator(String name) {
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
        if (player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_defabricator) {
            player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
        }

        World world = player.world;
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_defabricator_ammo)) && count % 2 == 0) {
            EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntityBullet bullet = new EntityBullet(world, player, 3.0F, 40, 120, false, "tauDay", hand);
            bullet.setDamage(40 + this.rand.nextInt(80));
            world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.defabShoot, SoundCategory.PLAYERS, 1.0F, 0.9F + this.rand.nextFloat() * 0.2F);
            if (count == this.getMaxItemUseDuration(stack)) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.defabSpinup, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (count % 20 == 0 && !infinite) {
                Library.consumeInventoryItem(player.inventory, ModItems.gun_defabricator_ammo);
            }
            if (!world.isRemote) {
                world.spawnEntity(bullet);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (MainRegistry.polaroidID == 11) {
            tooltip.add("Did you set your alarm for volcano day?");
        } else {
            tooltip.add("\u00a74\u00a7lBAD WOLF");
        }
        tooltip.add("");
        tooltip.add("Ammo: \u00a7cDefabricator Energy Cell");
        tooltip.add("Damage: 40 - 120");
        tooltip.add("");
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.5D, 0));
        }
        return multimap;
    }
}
