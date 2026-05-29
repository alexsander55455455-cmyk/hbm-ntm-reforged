package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.misc.RenderScreenOverlay;

import java.util.ArrayList;

public class Gun22LRFactory {

    static float inaccuracy = 5.0F;

    public static GunConfiguration getUziConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 1;
        config.roundsPerCycle = 1;
        config.gunMode = GunConfiguration.MODE_NORMAL;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 32;
        config.reloadType = GunConfiguration.RELOAD_FULL;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CROSS;
        config.durability = 3000;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.uziShoot;
        config.reloadSoundEnd = false;
        config.name = "IMI Uzi";
        config.manufacturer = "Israel Military Industries";
        config.comment.add("Mom, where are my mittens?");

        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.LR22_NORMAL);
        config.config.add(BulletConfigSyncingUtil.LR22_AP);
        config.config.add(BulletConfigSyncingUtil.CHL_LR22);
        return config;
    }

    public static GunConfiguration getSaturniteConfig() {
        GunConfiguration config = getUziConfig();
        config.durability = 4500;
        config.name = "IMI Uzi D-25A";
        config.manufacturer = "IMI / Big MT";

        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.LR22_NORMAL_FIRE);
        config.config.add(BulletConfigSyncingUtil.LR22_AP_FIRE);
        config.config.add(BulletConfigSyncingUtil.CHL_LR22_FIRE);
        return config;
    }

    public static BulletConfiguration get22LRConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_22lr);
        config.spread *= inaccuracy;
        config.dmgMin = 2.0F;
        config.dmgMax = 4.0F;
        return config;
    }

    public static BulletConfiguration get22LRAPConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_22lr_ap);
        config.spread *= inaccuracy;
        config.dmgMin = 6.0F;
        config.dmgMax = 8.0F;
        config.leadChance = 10;
        config.wear = 15;
        return config;
    }
}
