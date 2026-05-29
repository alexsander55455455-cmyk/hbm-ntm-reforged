package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunBoltAction extends Item {

	private final Random rand = new Random();
	public int dmgMin = 24;
	public int dmgMax = 36;

	public GunBoltAction(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(2500);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_bolt_action_saturnite) {
			player.getHeldItemOffhand().getItem().onPlayerStoppedUsing(player.getHeldItemOffhand(), worldIn, entityLiving, timeLeft);
		}

		int charge = this.getMaxItemUseDuration(stack) - timeLeft;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, charge, Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge_slug));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return;
		}

		charge = event.getCharge();
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if(!infinite && !Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge_slug)) {
			return;
		}

		if(charge < 10) {
			return;
		}

		EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		EntityBullet bullet = new EntityBullet(worldIn, player, 3.0F, this.dmgMin, this.dmgMax, false, false, hand);
		bullet.setDamage(this.dmgMin + this.rand.nextInt(this.dmgMax - this.dmgMin));
		bullet.setImmuneToFire();

		stack.damageItem(1, player);
		worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.revolverShoot, SoundCategory.PLAYERS, 5.0F, 0.75F);

		if(!infinite) {
			Library.consumeInventoryItem(player.inventory, ModItems.ammo_20gauge_slug);
		}

		if(!worldIn.isRemote) {
			worldIn.spawnEntity(bullet);
		}

		setAnim(stack, 1);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		int anim = getAnim(stack);
		if(anim > 0) {
			if(anim < 30) {
				setAnim(stack, anim + 1);
			} else {
				setAnim(stack, 0);
			}

			if(anim == 15) {
				worldIn.playSound(null, entityIn.posX, entityIn.posY, entityIn.posZ, HBMSoundHandler.leverActionReload, SoundCategory.PLAYERS, 2.0F, 0.85F);
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, ModItems.ammo_20gauge_slug));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return event.getAction();
		}

		if(getAnim(stack) == 0) {
			playerIn.setActiveHand(handIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Shiny shooter made from D-25A alloy.");
		tooltip.add("");
		tooltip.add("Ammo: \u00a7e20 Gauge Brenneke Slug");
		tooltip.add("Damage: 24 - 36");
		tooltip.add("Sets enemy on fire.");
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.5D, 0));
		}
		return multimap;
	}

	private static int getAnim(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		return stack.getTagCompound().getInteger("animation");
	}

	private static void setAnim(ItemStack stack, int anim) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("animation", anim);
	}

	public static float getRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F * 7.5F;
		int anim = getAnim(stack);
		if(anim < 10) {
			return 0.0F;
		}
		anim -= 10;
		if(anim < 10) {
			return rad * anim;
		}
		return rad * 10.0F - rad * (anim - 10);
	}

	public static float getLevRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F * 10.0F;
		int anim = getAnim(stack);
		if(anim < 10) {
			return 0.0F;
		}
		anim -= 10;
		if(anim < 6) {
			return rad * anim;
		}
		if(anim > 14) {
			return rad * (5 - (anim - 15));
		}
		return rad * 5.0F;
	}

	public static float getOffsetFromAnim(ItemStack stack) {
		float anim = getAnim(stack);
		if(anim < 10.0F) {
			return 0.0F;
		}
		anim -= 10.0F;
		if(anim < 10.0F) {
			return anim / 10.0F;
		}
		return 2.0F - anim / 10.0F;
	}

	public static float getTransFromAnim(ItemStack stack) {
		float anim = getAnim(stack);
		if(anim < 10.0F) {
			return 0.0F;
		}
		anim -= 10.0F;
		if(anim > 4.0F && anim < 10.0F) {
			return (anim - 5.0F) * 0.1F;
		}
		if(anim > 9.0F && anim < 15.0F) {
			return 1.0F - (anim - 5.0F) * 0.1F;
		}
		return 0.0F;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
}
