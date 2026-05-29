package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.potion.HbmPotion;
import com.hbm.render.misc.RenderScreenOverlay;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class Gun357MagnumFactory {

    public static GunConfiguration getBaseConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 10;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.reloadDuration = 10;
        config.firingDuration = 0;
        config.ammoCap = 6;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CLASSIC;
        config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
        config.firingSound = HBMSoundHandler.revolverShoot;
        config.reloadSoundEnd = false;
        return config;
    }

    public static GunConfiguration getRevolverSchrabidiumConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 7500;
        config.firingSound = HBMSoundHandler.schrabidiumShoot;
        config.name = "FFI Viper Ultra";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.SCHRABIDIUM_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverIronConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 2000;
        config.name = "FFI Viper";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 3500;
        config.name = "FFI Viper Inox";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverSaturniteConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 3500;
        config.name = "FFI Viper D-25A";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.SATURNITE_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverLeadConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 2000;
        config.name = "FFI Viper Lead";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverGoldConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 2500;
        config.name = "FFI Viper Bling";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.GOLD_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverCursedConfig() {
        GunConfiguration config = getBaseConfig();
        config.rateOfFire = 7;
        config.ammoCap = 17;
        config.durability = 5000;
        config.firingSound = HBMSoundHandler.heavyShoot;
        config.name = "Britannia Standard Issue Motorized Handgun";
        config.manufacturer = "BAE Systems plc";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.CURSED_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverNightmareConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.firingSound = HBMSoundHandler.schrabidiumShoot;
        config.name = "FFI Viper N1";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.NIGHT_REVOLVER);
        config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
        return config;
    }

    public static GunConfiguration getRevolverNightmare2Config() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.firingSound = HBMSoundHandler.schrabidiumShoot;
        config.crosshair = RenderScreenOverlay.Crosshair.NONE;
        config.name = "FFI Viper N2";
        config.manufacturer = "FlimFlam Industries";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.NIGHT2_REVOLVER);
        return config;
    }

    public static BulletConfiguration getRevIronConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_iron_ammo);
        config.dmgMin = 2.0F;
        config.dmgMax = 4.0F;
        return config;
    }

    public static BulletConfiguration getRevSteelConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_ammo);
        config.dmgMin = 3.0F;
        config.dmgMax = 5.0F;
        return config;
    }

    public static BulletConfiguration getRevLeadConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_lead_ammo);
        config.dmgMin = 2.0F;
        config.dmgMax = 3.0F;
        config.effects = new ArrayList<PotionEffect>();
        config.effects.add(new PotionEffect(HbmPotion.radiation, 200, 4));
        return config;
    }

    public static BulletConfiguration getRevGoldConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_gold_ammo);
        config.dmgMin = 10.0F;
        config.dmgMax = 15.0F;
        return config;
    }

    public static BulletConfiguration getRevDeshConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_357_desh);
        config.dmgMin = 15.0F;
        config.dmgMax = 17.0F;
        return config;
    }

    public static BulletConfiguration getRevSchrabidiumConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_schrabidium_ammo);
        config.dmgMin = 1000000.0F;
        config.dmgMax = 1.0E7F;
        return config;
    }

    public static BulletConfiguration getRevCursedConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_cursed_ammo);
        config.dmgMin = 12.0F;
        config.dmgMax = 15.0F;
        return config;
    }

    public static BulletConfiguration getRevNightmareConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_nightmare_ammo);
        config.dmgMin = 1.0F;
        config.dmgMax = 50.0F;
        return config;
    }

    public static BulletConfiguration getRevNightmare2Config() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.gun_revolver_nightmare2_ammo);
        config.spread *= 10.0F;
        config.bulletsMin = 4;
        config.bulletsMax = 6;
        config.dmgMin = 50.0F;
        config.dmgMax = 150.0F;
        config.destroysBlocks = true;
        config.style = 3;
        config.trail = 1;
        return config;
    }
}
