package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.misc.RenderScreenOverlay;

import java.util.ArrayList;
import java.util.HashMap;

public class Gun9mmFactory {
    static float inaccuracy = 5.0F;

    public static GunConfiguration getMP40Config() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 2;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 1;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 32;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_SPLIT;
        config.durability = 2500;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.rifleShoot;
        config.reloadSoundEnd = false;
        config.name = "Maschinenpistole 40";
        config.manufacturer = "Erfurter Maschinenfabrik Geipel";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
        config.config.add(BulletConfigSyncingUtil.P9_AP);
        config.config.add(BulletConfigSyncingUtil.P9_DU);
        config.config.add(BulletConfigSyncingUtil.CHL_P9);
        config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
        return config;
    }

    public static GunConfiguration getThompsonConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 2;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 1;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 30;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_SPLIT;
        config.durability = 2500;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.rifleShoot;
        config.reloadSoundEnd = false;

        config.animations = new HashMap<HbmAnimations.AnimType, BusAnimation>();
        BusAnimation anim = new BusAnimation();
        BusAnimationSequence seq = new BusAnimationSequence();
        seq.addKeyframe(new BusAnimationKeyframe(0.0D, 1.0D, -5.0D, 20));
        seq.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 20));
        anim.addBus("RECOIL", seq);
        config.animations.put(HbmAnimations.AnimType.CYCLE, anim);

        config.name = "M1A1 Submachine Gun 9mm Mod";
        config.manufacturer = "Auto-Ordnance Corporation";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
        config.config.add(BulletConfigSyncingUtil.P9_AP);
        config.config.add(BulletConfigSyncingUtil.P9_DU);
        config.config.add(BulletConfigSyncingUtil.CHL_P9);
        config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
        return config;
    }

    public static BulletConfiguration get9mmConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_9mm);
        config.spread *= inaccuracy;
        config.dmgMin = 2.0F;
        config.dmgMax = 4.0F;
        return config;
    }

    public static BulletConfiguration get9mmAPConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_9mm_ap);
        config.spread *= inaccuracy;
        config.dmgMin = 6.0F;
        config.dmgMax = 8.0F;
        config.leadChance = 10;
        config.wear = 15;
        return config;
    }

    public static BulletConfiguration get9mmDUConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_9mm_du);
        config.spread *= inaccuracy;
        config.dmgMin = 6.0F;
        config.dmgMax = 8.0F;
        config.leadChance = 50;
        config.wear = 25;
        return config;
    }

    public static BulletConfiguration get9mmRocketConfig() {
        BulletConfiguration config = BulletConfigFactory.standardRocketConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_9mm_rocket);
        config.velocity = 5.0F;
        config.explosive = 7.5F;
        config.trail = 5;
        return config;
    }
}
