package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
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

public class GunSpark extends Item {

    private final Random rand = new Random();
    public int dmgMin = 12;
    public int dmgMax = 24;

    public GunSpark(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(MainRegistry.weaponTab);
        this.setMaxStackSize(1);
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        int charge = this.getMaxItemUseDuration(stack) - timeLeft;
        if (entityLiving instanceof EntityPlayer) {
            ArrowLooseEvent event = new ArrowLooseEvent((EntityPlayer) entityLiving, stack, world, charge, true);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()) {
                return;
            }
            charge = event.getCharge();
        }

        boolean creative = entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).capabilities.isCreativeMode;
        boolean hasAmmo = entityLiving instanceof EntityPlayer && Library.hasInventoryItem(((EntityPlayer) entityLiving).inventory, ModItems.gun_spark_ammo);
        boolean infinite = creative || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if (infinite || hasAmmo) {
            if (charge < 10) {
                return;
            }

            stack.damageItem(1, entityLiving);
            world.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, HBMSoundHandler.sparkShoot, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
            if (!infinite && entityLiving instanceof EntityPlayer) {
                Library.consumeInventoryItem(((EntityPlayer) entityLiving).inventory, ModItems.gun_spark_ammo);
            }

            EnumHand hand = entityLiving.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            EntitySparkBeam beam = new EntitySparkBeam(world, entityLiving, 3.0F, hand);
            beam.setDamage(this.dmgMin + this.rand.nextInt(this.dmgMax - this.dmgMin));
            if (!world.isRemote) {
                world.spawnEntity(beam);
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
        ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, true);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return event.getAction();
        }
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("'magic does not compute'");
        tooltip.add("'aeiou'");
        tooltip.add("");
        tooltip.add("Ammo: \u00a7bElectromagnetic Cartridge");
        tooltip.add("Projectiles explode on impact.");
        tooltip.add("");
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
    }
}
