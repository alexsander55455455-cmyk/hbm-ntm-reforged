package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.misc.RenderScreenOverlay;

import java.util.ArrayList;

public class Gun5mmFactory {

	private static final float INACCURACY = 10.0F;

	public static GunConfiguration getMinigunConfig() {
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.roundsPerCycle = 5;
		config.gunMode = 0;
		config.firingMode = 1;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = 0;
		config.allowsInfinity = true;
		config.crosshair = RenderScreenOverlay.Crosshair.L_CIRCLE;
		config.durability = 10000;
		config.firingSound = HBMSoundHandler.lacunaeShoot;
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.R5_DU);
		config.config.add(BulletConfigSyncingUtil.R5_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_R5);
		return config;
	}

	public static GunConfiguration get53Config() {
		GunConfiguration config = getMinigunConfig();
		config.name = "CZ53 Personal Minigun";
		config.manufacturer = "Rockwell International Corporation";
		return config;
	}

	public static GunConfiguration get57Config() {
		GunConfiguration config = getMinigunConfig();
		config.durability = 15000;
		config.name = "CZ57 Avenger Minigun";
		config.manufacturer = "Rockwell International Corporation";
		return config;
	}

	public static GunConfiguration getLacunaeConfig() {
		GunConfiguration config = getMinigunConfig();
		config.durability = 25000;
		config.name = "Auntie Lacunae";
		config.manufacturer = "Rockwell International Corporation?";
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_DU_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_STAR_BOLT);
		config.config.add(BulletConfigSyncingUtil.CHL_R5_BOLT);
		return config;
	}

	public static BulletConfiguration get5mmConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_5mm);
		bullet.spread *= INACCURACY;
		bullet.dmgMin = 3.0F;
		bullet.dmgMax = 5.0F;
		return bullet;
	}

	public static BulletConfiguration get5mmExplosiveConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_5mm_explosive);
		bullet.spread *= INACCURACY;
		bullet.dmgMin = 4.0F;
		bullet.dmgMax = 7.0F;
		bullet.explosive = 1.0F;
		bullet.wear = 25;
		return bullet;
	}

	public static BulletConfiguration get5mmDUConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_5mm_du);
		bullet.spread *= INACCURACY;
		bullet.dmgMin = 6.0F;
		bullet.dmgMax = 10.0F;
		bullet.wear = 25;
		bullet.leadChance = 50;
		return bullet;
	}

	public static BulletConfiguration get5mmStarConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_5mm_star);
		bullet.spread *= INACCURACY;
		bullet.dmgMin = 12.0F;
		bullet.dmgMax = 20.0F;
		bullet.wear = 25;
		bullet.leadChance = 100;
		return bullet;
	}
}
