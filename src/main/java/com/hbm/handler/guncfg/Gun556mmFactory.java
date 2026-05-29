package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.inventory.RecipesCommon;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;

public class Gun556mmFactory {

    static float inaccuracy = 2.5F;

    public static GunConfiguration getEuphieConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 2;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 1;
        config.hasSights = false;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 40;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CROSS;
        config.durability = 10000;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.hksShoot;
        config.reloadSoundEnd = false;
        config.name = "Britannian Standard Issue Assault Rifle";
        config.manufacturer = "BAE Systems plc";
        config.comment.add("Why is this gun so sticky?");
        config.config = new ArrayList<>();
        config.config.add(BulletConfigSyncingUtil.R556_GOLD);
        config.config.add(BulletConfigSyncingUtil.R556_NORMAL);
        config.config.add(BulletConfigSyncingUtil.R556_TRACER);
        config.config.add(BulletConfigSyncingUtil.R556_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.R556_AP);
        config.config.add(BulletConfigSyncingUtil.R556_DU);
        config.config.add(BulletConfigSyncingUtil.R556_STAR);
        config.config.add(BulletConfigSyncingUtil.CHL_R556);
        config.config.add(BulletConfigSyncingUtil.R556_SLEEK);
        config.config.add(BulletConfigSyncingUtil.R556_K);
        return config;
    }

    public static GunConfiguration getSPIWConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 3;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 1;
        config.hasSights = true;
        config.reloadDuration = 25;
        config.firingDuration = 0;
        config.ammoCap = 30;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_BOX;
        config.durability = 7000;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.hksShoot;
        config.reloadSoundEnd = false;
        config.animations.put(HbmAnimations.AnimType.CYCLE, new BusAnimation().addBus("RECOIL", new BusAnimationSequence()
                .addKeyframe(new BusAnimationKeyframe(0.5D, 0.0D, 0.0D, 25))
                .addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 75))));
        config.name = "H&R SPIW";
        config.manufacturer = "Harrington & Richardson";
        config.comment.add("Launch some flechettes in the breeze");
        config.comment.add("Find his arms nailed to the trees");
        config.comment.add("Napalm sticks to kids");
        config.config = new ArrayList<>();
        config.config.add(BulletConfigSyncingUtil.R556_FLECHETTE);
        config.config.add(BulletConfigSyncingUtil.R556_FLECHETTE_INCENDIARY);
        config.config.add(BulletConfigSyncingUtil.R556_FLECHETTE_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.R556_FLECHETTE_DU);
        config.config.add(BulletConfigSyncingUtil.CHL_R556_FLECHETTE);
        config.config.add(BulletConfigSyncingUtil.R556_FLECHETTE_SLEEK);
        config.config.add(BulletConfigSyncingUtil.R556_K);
        return config;
    }

    public static GunConfiguration getGLauncherConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 60;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.hasSights = true;
        config.reloadDuration = 40;
        config.firingDuration = 0;
        config.ammoCap = 0;
        config.reloadType = 0;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CIRCUMFLEX;
        config.firingSound = HBMSoundHandler.glauncher;
        config.reloadSound = GunConfiguration.RSOUND_GRENADE;
        config.reloadSoundEnd = false;
        config.config = new ArrayList<>();
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
        return config;
    }

    public static BulletConfiguration get556Config() {
        BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556);
        bullet.spread *= inaccuracy;
        bullet.dmgMin = 2.0F;
        bullet.dmgMax = 4.0F;
        return bullet;
    }

    public static BulletConfiguration get556GoldConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_566_gold);
        bullet.spread = 0.0F;
        return bullet;
    }

    public static BulletConfiguration get556PhosphorusConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_phosphorus);
        bullet.wear = 15;
        bullet.incendiary = 5;
        bullet.doesPenetrate = false;

        PotionEffect effect = new PotionEffect(HbmPotion.phosphorus, 400, 0, true, false);
        effect.getCurativeItems().clear();

        bullet.effects = new ArrayList<>();
        bullet.effects.add(new PotionEffect(effect));
        bullet.bImpact = getFlameImpactBehavior();
        return bullet;
    }

    public static BulletConfiguration get556APConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_ap);
        bullet.dmgMin = 4.0F;
        bullet.dmgMax = 6.0F;
        bullet.wear = 15;
        bullet.leadChance = 10;
        return bullet;
    }

    public static BulletConfiguration get556DUConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_du);
        bullet.dmgMin = 8.0F;
        bullet.dmgMax = 10.0F;
        bullet.wear = 25;
        bullet.leadChance = 50;
        return bullet;
    }

    public static BulletConfiguration get556StarConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_star);
        bullet.dmgMin = 15.0F;
        bullet.dmgMax = 20.0F;
        bullet.wear = 25;
        bullet.leadChance = 100;
        return bullet;
    }

    public static BulletConfiguration get556TracerConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_tracer);
        bullet.vPFX = "reddust";
        return bullet;
    }

    public static BulletConfiguration get556SleekConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_sleek);
        bullet.dmgMin = 15.0F;
        bullet.dmgMax = 20.0F;
        bullet.wear = 10;
        bullet.leadChance = 100;
        bullet.doesPenetrate = false;
        bullet.bHit = getSleekHitBehavior();
        bullet.bImpact = getSleekImpactBehavior();
        return bullet;
    }

    public static BulletConfiguration get556FlechetteSleekConfig() {
        BulletConfiguration bullet = get556FlechetteConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_flechette_sleek);
        bullet.dmgMin = 12.0F;
        bullet.dmgMax = 16.0F;
        bullet.wear = 10;
        bullet.leadChance = 50;
        bullet.doesPenetrate = false;
        bullet.bHit = getSleekHitBehavior();
        bullet.bImpact = getSleekImpactBehavior();
        return bullet;
    }

    public static BulletConfiguration get556FlechetteConfig() {
        BulletConfiguration bullet = get556Config();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_flechette);
        bullet.dmgMin = 6.0F;
        bullet.dmgMax = 8.0F;
        bullet.HBRC = 2;
        bullet.LBRC = 95;
        bullet.wear = 15;
        bullet.style = 1;
        bullet.doesPenetrate = false;
        return bullet;
    }

    public static BulletConfiguration get556FlechetteIncendiaryConfig() {
        BulletConfiguration bullet = get556FlechetteConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_flechette_incendiary);
        bullet.incendiary = 5;
        return bullet;
    }

    public static BulletConfiguration get556FlechettePhosphorusConfig() {
        BulletConfiguration bullet = get556FlechetteConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_flechette_phosphorus);
        bullet.incendiary = 5;

        PotionEffect effect = new PotionEffect(HbmPotion.phosphorus, 400, 0, true, false);
        effect.getCurativeItems().clear();

        bullet.effects = new ArrayList<>();
        bullet.effects.add(new PotionEffect(effect));
        bullet.bImpact = getFlameImpactBehavior();
        return bullet;
    }

    public static BulletConfiguration get556FlechetteDUConfig() {
        BulletConfiguration bullet = get556FlechetteConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_flechette_du);
        bullet.dmgMin = 12.0F;
        bullet.dmgMax = 16.0F;
        bullet.wear = 25;
        bullet.leadChance = 50;
        bullet.doesPenetrate = true;
        return bullet;
    }

    public static BulletConfiguration get556KConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_556_k);
        bullet.dmgMin = 0.0F;
        bullet.dmgMax = 0.0F;
        bullet.maxAge = 0;
        return bullet;
    }

    private static IBulletImpactBehavior getFlameImpactBehavior() {
        return new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("type", "vanillaburst");
                nbt.setString("mode", "flame");
                nbt.setInteger("count", 15);
                nbt.setDouble("motion", 0.05D);
                PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(nbt, bullet.posX, bullet.posY, bullet.posZ),
                        new NetworkRegistry.TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50.0D));
            }
        };
    }

    private static IBulletHitBehavior getSleekHitBehavior() {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity entity) {
                if (bullet.world.isRemote) return;
                EntityBulletBase meteor = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.MASKMAN_METEOR);
                meteor.setPosition(entity.posX, entity.posY + 30.0D + bullet.world.rand.nextInt(10), entity.posZ);
                meteor.motionY = -1.0D;
                meteor.shooter = bullet.shooter;
                bullet.world.spawnEntity(meteor);
            }
        };
    }

    private static IBulletImpactBehavior getSleekImpactBehavior() {
        return new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if (bullet.world.isRemote) return;
                if (y == -1) return;
                EntityBulletBase meteor = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.MASKMAN_METEOR);
                meteor.setPosition(bullet.posX, bullet.posY + 30.0D + bullet.world.rand.nextInt(10), bullet.posZ);
                meteor.motionY = -1.0D;
                meteor.shooter = bullet.shooter;
                bullet.world.spawnEntity(meteor);
            }
        };
    }
}
