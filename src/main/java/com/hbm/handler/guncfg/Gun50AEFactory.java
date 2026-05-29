package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.misc.RenderScreenOverlay;

import java.util.ArrayList;

public class Gun50AEFactory {
    static float inaccuracy = 5.0E-4F;

    public static GunConfiguration getBaseConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 10;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.reloadDuration = 10;
        config.firingDuration = 0;
        config.ammoCap = 7;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CLASSIC;
        config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
        config.firingSound = HBMSoundHandler.deagleShoot;
        config.reloadSoundEnd = false;
        return config;
    }

    public static GunConfiguration getDeagleConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 2500;
        config.name = "IMI Desert Eagle";
        config.manufacturer = "Magnum Research / Israel Military Industries";
        config.hasSights = true;
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.AE50_NORMAL);
        config.config.add(BulletConfigSyncingUtil.AE50_AP);
        config.config.add(BulletConfigSyncingUtil.AE50_DU);
        config.config.add(BulletConfigSyncingUtil.CHL_AE50);
        config.config.add(BulletConfigSyncingUtil.AE50_STAR);
        return config;
    }

    public static BulletConfiguration get50AEConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_50ae);
        config.spread *= inaccuracy;
        config.dmgMin = 15.0F;
        config.dmgMax = 18.0F;
        return config;
    }

    public static BulletConfiguration get50APConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_50ae_ap);
        config.spread *= inaccuracy;
        config.dmgMin = 20.0F;
        config.dmgMax = 22.0F;
        config.leadChance = 10;
        config.wear = 15;
        return config;
    }

    public static BulletConfiguration get50DUConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_50ae_du);
        config.spread *= inaccuracy;
        config.dmgMin = 24.0F;
        config.dmgMax = 28.0F;
        config.leadChance = 50;
        config.wear = 25;
        return config;
    }

    public static BulletConfiguration get50StarConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_50ae_star);
        config.spread *= inaccuracy;
        config.dmgMin = 48.0F;
        config.dmgMax = 56.0F;
        config.leadChance = 100;
        config.wear = 25;
        return config;
    }
}
