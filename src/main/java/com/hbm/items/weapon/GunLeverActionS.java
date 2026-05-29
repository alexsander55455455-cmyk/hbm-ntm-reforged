package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import java.util.List;

import net.minecraft.client.resources.I18n;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GunLeverActionS extends Item {

	public int dmgMin = 8;
	public int dmgMax = 16;

	public GunLeverActionS(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(500);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;
		int charge = this.getMaxItemUseDuration(stack) - timeLeft;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, charge, Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return;
		}

		charge = event.getCharge();
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if(!infinite && !Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge)) {
			return;
		}

		if(charge < 10) {
			return;
		}

		Vec3d vec = player.getLookVec();
		vec = new Vec3d(vec.x * -1.0D, vec.y * -1.0D, vec.z * -1.0D).normalize();
		player.motionX += vec.x * 0.75D;
		player.motionY += vec.y * 0.75D;
		player.motionZ += vec.z * 0.75D;

		Library.consumeInventoryItem(player.inventory, ModItems.ammo_12gauge);
		stack.damageItem(1, player);
		player.attackEntityFrom(ModDamageSource.suicide, 10000.0F);
		if(!player.capabilities.isCreativeMode) {
			player.setHealth(0.0F);
		}

		worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.revolverShootAlt, SoundCategory.PLAYERS, 5.0F, 0.75F);
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
		ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, ModItems.ammo_12gauge));
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

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(MainRegistry.polaroidID == 11) {
			return ("" + I18n.format(this.getTranslationKey() + "_2.name")).trim();
		}
		return ("" + I18n.format(this.getTranslationKey() + ".name")).trim();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(MainRegistry.polaroidID == 11) {
			tooltip.add("Vee guilt-tripped me into this.");
		} else {
			tooltip.add("I hate your guts, Vee.");
		}
		tooltip.add("");
		tooltip.add("Ammo: \u00a7e12x74 Buckshot");
		tooltip.add("Damage: Infinite");
		tooltip.add("");
		tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.5D, 0));
		}
		return multimap;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
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
}
