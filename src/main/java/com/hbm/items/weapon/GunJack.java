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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import java.util.List;
import java.util.Random;

public class GunJack extends Item {

	private final Random rand = new Random();
	public int dmgMin = 12;
	public int dmgMax = 24;

	public GunJack(String name) {
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
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_jack) {
			player.getHeldItemOffhand().getItem().onPlayerStoppedUsing(player.getHeldItemOffhand(), worldIn, entityLiving, timeLeft);
		}

		int charge = this.getMaxItemUseDuration(stack) - timeLeft;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, charge, Library.hasInventoryItem(player.inventory, ModItems.gun_jack_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return;
		}

		charge = event.getCharge();
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if(!infinite && !Library.hasInventoryItem(player.inventory, ModItems.gun_jack_ammo)) {
			return;
		}

		if(charge < 10) {
			return;
		}

		stack.damageItem(1, player);
		worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.shotgunShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);

		if(!infinite) {
			Library.consumeInventoryItem(player.inventory, ModItems.gun_jack_ammo);
		}

		EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		int projectiles = this.rand.nextInt(25) + 24;
		for(int i = 0; i < projectiles; i++) {
			EntityBullet bullet = new EntityBullet(worldIn, player, 3.0F, hand);
			bullet.setDamage(this.dmgMin + this.rand.nextInt(this.dmgMax - this.dmgMin));
			if(!worldIn.isRemote) {
				worldIn.spawnEntity(bullet);
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, ModItems.gun_jack_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			return event.getAction();
		}

		if(playerIn.capabilities.isCreativeMode || Library.hasInventoryItem(playerIn.inventory, ModItems.gun_jack_ammo)) {
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
		tooltip.add("One barrel? Boring.");
		tooltip.add("Two barrels? Nah.");
		tooltip.add("Four barrels? Heck yes!");
		tooltip.add("");
		tooltip.add("Ammo: \u00a7eQuadruple Shotgun Shells");
		tooltip.add("Damage: 12 - 24");
		tooltip.add("Projectiles: 24 - 48");
		tooltip.add("");
		tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.5D, 0));
		}
		return multimap;
	}
}
