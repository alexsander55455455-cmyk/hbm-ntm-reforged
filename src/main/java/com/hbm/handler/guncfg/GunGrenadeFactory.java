package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import java.util.ArrayList;

public class GunGrenadeFactory {

    public static GunConfiguration getHK69Config() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 30;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.hasSights = true;
        config.reloadDuration = 40;
        config.firingDuration = 0;
        config.ammoCap = 1;
        config.reloadType = 2;
        config.allowsInfinity = true;
        config.crosshair = com.hbm.render.misc.RenderScreenOverlay.Crosshair.L_CIRCUMFLEX;
        config.firingSound = HBMSoundHandler.hkShoot;
        config.reloadSound = GunConfiguration.RSOUND_GRENADE;
        config.reloadSoundEnd = false;
        config.name = "Granatpistole HK69";
        config.manufacturer = "Heckler & Koch";
        
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.GRENADE_NORMAL);
        config.config.add(BulletConfigSyncingUtil.GRENADE_HE);
        config.config.add(BulletConfigSyncingUtil.GRENADE_INCENDIARY);
        config.config.add(BulletConfigSyncingUtil.GRENADE_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.GRENADE_CHEMICAL);
        config.config.add(BulletConfigSyncingUtil.GRENADE_CONCUSSION);
        config.config.add(BulletConfigSyncingUtil.GRENADE_FINNED);
        config.config.add(BulletConfigSyncingUtil.GRENADE_SLEEK);
        config.config.add(BulletConfigSyncingUtil.GRENADE_NUCLEAR);
        config.config.add(BulletConfigSyncingUtil.GRENADE_TRACER);
        config.config.add(BulletConfigSyncingUtil.GRENADE_KAMPF);
        
        config.durability = 300;
        return config;
    }

    public static BulletConfiguration getGrenadeConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade);
        config.velocity = 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 10;
        config.trail = 0;
        return config;
    }

    public static BulletConfiguration getGrenadeHEConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_he);
        config.velocity = 2.0f;
        config.dmgMin = 20.0f;
        config.dmgMax = 15.0f;
        config.wear = 15;
        config.explosive = 5.0f;
        config.trail = 1;
        return config;
    }

    public static BulletConfiguration getGrenadeIncendirayConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_incendiary);
        config.velocity = 2.0f;
        config.dmgMin = 15.0f;
        config.dmgMax = 15.0f;
        config.wear = 15;
        config.trail = 0;
        config.incendiary = 2;
        return config;
    }

    public static BulletConfiguration getGrenadeChlorineConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_toxic);
        config.velocity = 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 10;
        config.trail = 3;
        config.explosive = 0.0f;
        config.chlorine = 50;
        return config;
    }

    public static BulletConfiguration getGrenadeSleekConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_sleek);
        config.velocity = 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 10;
        config.trail = 4;
        config.explosive = 7.5f;
        config.jolt = 6.5d;
        return config;
    }

    public static BulletConfiguration getGrenadeConcussionConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_concussion);
        config.velocity = 2.0f;
        config.dmgMin = 15.0f;
        config.dmgMax = 20.0f;
        config.blockDamage = false;
        config.explosive = 10.0f;
        config.trail = 3;
        return config;
    }

    public static BulletConfiguration getGrenadeFinnedConfig() {
        BulletConfiguration config = getGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_finned);
        config.gravity = 0.02f;
        config.explosive = 1.5f;
        config.trail = 5;
        return config;
    }

    public static BulletConfiguration getGrenadeNuclearConfig() {
        BulletConfiguration config = getGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_nuclear);
        config.velocity = 4.0f;
        config.explosive = 0.0f;
        config.nuke = 15;
        return config;
    }

    public static BulletConfiguration getGrenadePhosphorusConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_phosphorus);
        config.velocity = 2.0f;
        config.dmgMin = 15.0f;
        config.dmgMax = 15.0f;
        config.wear = 15;
        config.trail = 0;
        config.incendiary = 2;
        config.bImpact = BulletConfigFactory.getPhosphorousEffect(10, 1200, 100, 0.5d, 1.0f);
        return config;
    }

    public static BulletConfiguration getGrenadeTracerConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_tracer);
        config.velocity = 2.0f;
        config.wear = 10;
        config.explosive = 0.0f;
        config.trail = 5;
        config.vPFX = "bluedust";
        return config;
    }

    public static BulletConfiguration getGrenadeKampfConfig() {
        BulletConfiguration config = BulletConfigFactory.standardRocketConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_grenade_kampf);
        config.spread = 0.0f;
        config.gravity = 0.0f;
        config.wear = 15;
        config.explosive = 3.5f;
        config.style = 9;
        config.trail = 4;
        config.vPFX = "smoke";
        return config;
    }
}
