package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityRocketHoming;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import java.util.List;

public class GunStinger extends Item {

	public GunStinger(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage("gun_skystinger".equals(name) ? 1000 : 500);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer)entityLiving;
		if(player.getHeldItemMainhand() == stack && (player.getHeldItemOffhand().getItem() == ModItems.gun_stinger || player.getHeldItemOffhand().getItem() == ModItems.gun_skystinger)) {
			player.getHeldItemOffhand().getItem().onPlayerStoppedUsing(player.getHeldItemOffhand(), worldIn, entityLiving, timeLeft);
		}

		int charge = this.getMaxItemUseDuration(stack) - timeLeft;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, charge, Library.hasInventoryItem(player.inventory, ModItems.gun_stinger_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return;
		}

		charge = event.getCharge();
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if(!infinite && !Library.hasInventoryItem(player.inventory, ModItems.gun_stinger_ammo)) {
			return;
		}

		if(charge < 25) {
			return;
		}

		stack.damageItem(1, player);
		if(this == ModItems.gun_stinger) {
			worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.rpgShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
		if(this == ModItems.gun_skystinger) {
			worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.rpgShoot, SoundCategory.PLAYERS, 1.0F, 0.5F);
		}

		Library.consumeInventoryItem(player.inventory, ModItems.gun_stinger_ammo);

		if(!worldIn.isRemote) {
			EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

			if(this == ModItems.gun_stinger) {
				EntityRocketHoming rocket = new EntityRocketHoming(worldIn, player, 1.0F, hand);
				if(player.isSneaking()) {
					rocket.homingRadius = 0;
				}
				worldIn.spawnEntity(rocket);
			}

			if(this == ModItems.gun_skystinger) {
				if(player.isSneaking()) {
					EntityRocketHoming rocket = new EntityRocketHoming(worldIn, player, 1.5F, hand);
					EntityRocketHoming rocket2 = new EntityRocketHoming(worldIn, player, 1.5F, hand);
					rocket.homingMod = 12;
					rocket2.homingMod = 12;
					rocket.motionX += worldIn.rand.nextGaussian() * 0.2D;
					rocket.motionY += worldIn.rand.nextGaussian() * 0.2D;
					rocket.motionZ += worldIn.rand.nextGaussian() * 0.2D;
					rocket2.motionX += worldIn.rand.nextGaussian() * 0.2D;
					rocket2.motionY += worldIn.rand.nextGaussian() * 0.2D;
					rocket2.motionZ += worldIn.rand.nextGaussian() * 0.2D;
					rocket.setIsCritical(true);
					rocket2.setIsCritical(true);
					worldIn.spawnEntity(rocket);
					worldIn.spawnEntity(rocket2);
				} else {
					EntityRocketHoming rocket = new EntityRocketHoming(worldIn, player, 2.0F, hand);
					rocket.homingMod = 8;
					rocket.homingRadius *= 50;
					rocket.setIsCritical(true);
					worldIn.spawnEntity(rocket);
				}
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.gun_stinger) {
			tooltip.add("Woosh, beep-beep-beep!");
			tooltip.add("");
			tooltip.add("Ammo: \u00a7eStinger Rockets");
			tooltip.add(" Projectiles target entities.");
			tooltip.add(" Projectiles explode on impact.");
			tooltip.add(" Alt-fire disables homing effect.");
		}

		if(this == ModItems.gun_skystinger) {
			tooltip.add("Oh, I get it, because of the...nyeees!");
			tooltip.add("It all makes sense now!");
			tooltip.add("");
			tooltip.add("Ammo: \u00a7eStinger Rockets");
			tooltip.add(" Projectiles target entities.");
			tooltip.add(" Projectiles explode on impact.");
			tooltip.add(" Alt-fire fires a second rocket for free.");
			tooltip.add("");
			tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, ModItems.gun_stinger_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return event.getAction();
		}

		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.0D, 0));
		}
		return multimap;
	}
}
