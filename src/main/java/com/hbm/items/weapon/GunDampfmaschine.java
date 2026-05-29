package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
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

public class GunDampfmaschine extends Item {

	private final Random rand = new Random();

	public GunDampfmaschine(String name) {
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
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		World world = player.world;

		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_dampfmaschine) {
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}

		EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

		if(!player.isSneaking()) {
			EntityRocket rocket = new EntityRocket(world, player, 3.0F, hand);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.crateBreak, SoundCategory.PLAYERS, 10.0F, 0.9F + this.rand.nextFloat() * 0.2F);

			if(count == this.getMaxItemUseDuration(stack)) {
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.alarmAutopilot, SoundCategory.PLAYERS, 100.0F, 1.0F);
			}

			if(!world.isRemote) {
				world.spawnEntity(rocket);
			}
		} else {
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_NOTE_HARP, SoundCategory.PLAYERS, 10.0F, 0.9F + this.rand.nextFloat() * 0.2F);

			if(count % 10 == 0) {
				EntityBombletSelena bomb = new EntityBombletSelena(world);
				bomb.posX = player.posX;
				bomb.posY = player.posY + player.getEyeHeight();
				bomb.posZ = player.posZ;
				bomb.motionX = player.getLookVec().x * 5.0D;
				bomb.motionY = player.getLookVec().y * 5.0D;
				bomb.motionZ = player.getLookVec().z * 5.0D;

				if(count == this.getMaxItemUseDuration(stack)) {
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.chopperDrop, SoundCategory.PLAYERS, 10.0F, 1.0F);
				}

				if(!world.isRemote) {
					world.spawnEntity(bomb);
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Sometimes, to do what's right,");
		tooltip.add("you have to become the villain of");
		tooltip.add("the pi-I mean me too, thanks.");
		tooltip.add("");
		tooltip.add("oh sorry how did this get here i'm not good with computer can somebody tell me how i can get out of here oh fiddlesticks this is not good oh no please can anybody hear me i am afraid please for the love of god somebody get me out of here");
		tooltip.add("");
		tooltip.add("Ammo: \u00a76orang");
		tooltip.add("Damage: aaaaaaaaa");
		tooltip.add("");
		tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", -2.0D, 0));
		}
		return multimap;
	}
}
