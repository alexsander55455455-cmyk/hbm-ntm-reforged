package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import java.util.ArrayList;

public class GunOSIPRFactory {

	private static final float INACCURACY = 5.0F;

	public static GunConfiguration getOSIPRConfig() {
		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.durability = 10000;
		config.reloadSound = HBMSoundHandler.osiprReload;
		config.firingSound = HBMSoundHandler.osiprShoot;
		config.reloadSoundEnd = false;
		config.name = "Overwatch Standard Issue Pulse Rifle";
		config.manufacturer = "The Universal Union";

		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR);
		return config;
	}

	public static GunConfiguration getAltConfig() {
		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.firingSound = HBMSoundHandler.singFlyby;

		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED);
		return config;
	}

	public static BulletConfiguration getPulseConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.gun_osipr_ammo);
		bullet.spread *= INACCURACY;
		bullet.dmgMin = 3.0F;
		bullet.dmgMax = 5.0F;
		bullet.trail = BulletConfiguration.BOLT_LASER;
		return bullet;
	}

	public static BulletConfiguration getPulseChargedConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.gun_osipr_ammo2);
		return bullet;
	}
}
