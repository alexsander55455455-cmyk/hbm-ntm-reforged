package com.hbm.hazard.type;


import com.hbm.config.RadiationConfig;
import com.hbm.handler.ArmorUtil;
import com.hbm.hazard.helper.HazardHelper;
import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.lib.Library;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.hbm.hazard.helper.HazardHelper.applyPotionEffect;

public class HazardTypeToxic implements IHazardType {
	@Override
    public void onUpdate(final EntityLivingBase target, double level, final ItemStack stack) {

		if (RadiationConfig.disableToxic) return;

		level *= stack.getCount();
		boolean hasToxFilter = false;
		boolean hasHazmat = false;
		final boolean reacher = HazardHelper.isHoldingReacher(target);

		if (target instanceof EntityPlayer player) {
			if (player.capabilities.isCreativeMode) return;

			hasToxFilter = ArmorRegistry.hasProtection(player, EntityEquipmentSlot.HEAD, ArmorRegistry.HazardClass.NERVE_AGENT);

			if (hasToxFilter) {
				ArmorUtil.damageGasMaskFilter(player, hazardRate);
			}

			hasHazmat = ArmorUtil.checkForHazmat(player);
		}

		if (!hasToxFilter && !hasHazmat && !reacher) {
			if (level > 0) {
				applyPotionEffect(target, MobEffects.HUNGER, 110, Math.min(255, (int) level));
			}
			if (level > 10) {
				applyPotionEffect(target, MobEffects.WEAKNESS, 110, Math.min(255, (int) level / 10));
			}
			if (level > 100) {
				applyPotionEffect(target, MobEffects.SLOWNESS, 110, Math.min(4, (int) level / 100));
			}
			if (level > 1000) {
				if (level > 2000 || target.world.rand.nextInt((int) (2000 / level)) == 0) {
					applyPotionEffect(target, MobEffects.POISON, 110, Math.min(255, (int) level / 1000));
				}
			}
		}

		if (!(hasHazmat && hasToxFilter) && !reacher) {
			if (level > 2000) {
				applyPotionEffect(target, MobEffects.MINING_FATIGUE, 110, Math.min(255, (int) level / 2000));
			}
			if (level > 10000) {
				applyPotionEffect(target, MobEffects.INSTANT_DAMAGE, 110, Math.min(255, (int) level / 5000));
			}
		}
	}

    @Override
    public void updateEntity(final EntityItem item, final double level) {
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void addHazardInformation(final EntityPlayer player, final List<String> list, double level, final ItemStack stack, final List<IHazardModifier> modifiers) {
		level *= stack.getCount();
		final float rl = Library.roundFloat(level, 3);
		final String adjectiveKey;

		if (level > 10000) {
			adjectiveKey = "adjective.extreme";
		} else if (level > 1000) {
			adjectiveKey = "adjective.veryhigh";
		} else if (level > 100) {
			adjectiveKey = "adjective.high";
		} else if (level > 10) {
			adjectiveKey = "adjective.medium";
		} else if (level > 0) {
			adjectiveKey = "adjective.little";
		} else {
			return;
		}

		list.add(TextFormatting.GREEN + "[" + I18nUtil.resolveKey(adjectiveKey) + " " + I18nUtil.resolveKey("trait.toxic") + "] " + rl);
    }
}