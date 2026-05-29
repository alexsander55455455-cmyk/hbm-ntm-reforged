package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityPlasmaBeam;
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

public class GunHP extends Item {

    private final Random rand = new Random();

    public GunHP(String name) {
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
        if (player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_hp) {
            player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
        }

        World world = player.world;
        boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if (player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_hp_ammo)) {
            EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntityPlasmaBeam[] beams = new EntityPlasmaBeam[5];
            for (int i = 0; i < beams.length; i++) {
                beams[i] = new EntityPlasmaBeam(world, player, 1.0F, hand);
                if (i > 0) {
                    beams[i].motionX *= 0.75D + this.rand.nextDouble() * 0.5D;
                    beams[i].motionY *= 0.75D + this.rand.nextDouble() * 0.5D;
                    beams[i].motionZ *= 0.75D + this.rand.nextDouble() * 0.5D;
                }
            }

            if (infinite) {
                beams[0].canBePickedUp = 2;
            } else if (count % 20 == 0) {
                Library.consumeInventoryItem(player.inventory, ModItems.gun_hp_ammo);
            }
            if (count == this.getMaxItemUseDuration(stack)) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorIgnite, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (count % 10 == 0) {
                world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            if (!world.isRemote) {
                for (EntityPlasmaBeam beam : beams) {
                    world.spawnEntity(beam);
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Rrrrt - rrrrt - rrrrt, weeee!");
        tooltip.add("");
        tooltip.add("Ammo: \u00a7aInk Cartridge");
        tooltip.add("Damage: 25 - 45");
        tooltip.add("");
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
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
