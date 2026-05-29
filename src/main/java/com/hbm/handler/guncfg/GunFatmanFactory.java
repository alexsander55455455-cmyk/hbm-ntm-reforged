package com.hbm.handler.guncfg;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.render.misc.RenderScreenOverlay;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GunFatmanFactory {
    public static GunConfiguration getFatmanConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 20;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.reloadDuration = 120;
        config.firingDuration = 0;
        config.ammoCap = 1;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CIRCUMFLEX;
        config.firingSound = HBMSoundHandler.fatmanShoot;
        config.reloadSound = GunConfiguration.RSOUND_FATMAN;
        config.reloadSoundEnd = false;
        config.name = "M-42 Tactical Nuclear Catapult";
        config.manufacturer = "Fort Strong";
        config.config = new ArrayList();
        config.config.add(BulletConfigSyncingUtil.NUKE_NORMAL);
        config.config.add(BulletConfigSyncingUtil.NUKE_LOW);
        config.config.add(BulletConfigSyncingUtil.NUKE_HIGH);
        config.config.add(BulletConfigSyncingUtil.NUKE_TOTS);
        config.config.add(BulletConfigSyncingUtil.NUKE_SAFE);
        config.config.add(BulletConfigSyncingUtil.NUKE_PUMPKIN);
        config.durability = 1000;
        return config;
    }

    public static GunConfiguration getMIRVConfig() {
        GunConfiguration config = GunFatmanFactory.getFatmanConfig();
        config.name = "M-42 Experimental MIRV";
        config.manufacturer = "Fort Strong";
        config.config = new ArrayList();
        config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL);
        config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_LOW);
        config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_HIGH);
        config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SAFE);
        config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
        config.durability = 1000;
        return config;
    }

    public static GunConfiguration getBELConfig() {
        GunConfiguration config = GunFatmanFactory.getFatmanConfig();
        config.name = "Balefire Egg Launcher";
        config.manufacturer = "Fort Strong";
        config.config = new ArrayList();
        config.config.add(BulletConfigSyncingUtil.NUKE_AMAT);
        return config;
    }

    public static GunConfiguration getProtoConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 20;
        config.roundsPerCycle = 8;
        config.gunMode = 0;
        config.firingMode = 0;
        config.reloadDuration = 120;
        config.firingDuration = 0;
        config.ammoCap = 8;
        config.reloadType = 1;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CIRCUMFLEX;
        config.firingSound = HBMSoundHandler.fatmanShoot;
        config.reloadSound = GunConfiguration.RSOUND_FATMAN;
        config.reloadSoundEnd = false;
        config.name = "M-42 Tactical Nuclear Catapult";
        config.manufacturer = "Fort Strong";
        config.config = new ArrayList();
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_NORMAL);
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_LOW);
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_HIGH);
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_TOTS);
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_SAFE);
        config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_PUMPKIN);
        config.durability = 1000;
        return config;
    }

    public static BulletConfiguration getNukeConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke);
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                BulletConfigFactory.nuclearExplosion((EntityBulletBase)bullet, (int)x, (int)y, (int)z, (int)35);
            }
        };
        return bullet;
    }

    public static BulletConfiguration getNukeLowConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke_low);
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                BulletConfigFactory.nuclearExplosion((EntityBulletBase)bullet, (int)x, (int)y, (int)z, (int)20);
            }
        };
        return bullet;
    }

    public static BulletConfiguration getNukeHighConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke_high);
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                BulletConfigFactory.nuclearExplosion((EntityBulletBase)bullet, (int)x, (int)y, (int)z, (int)50);
            }
        };
        return bullet;
    }

    public static BulletConfiguration getNukeTotsConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke_tots);
        bullet.bulletsMin = 8;
        bullet.bulletsMax = 8;
        bullet.spread = 0.1f;
        bullet.style = 9;
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                BulletConfigFactory.nuclearExplosion((EntityBulletBase)bullet, (int)x, (int)y, (int)z, (int)10);
            }
        };
        return bullet;
    }

    public static BulletConfiguration getNukeSafeConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke_safe);
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                BulletConfigFactory.nuclearExplosion((EntityBulletBase)bullet, (int)x, (int)y, (int)z, (int)0);
            }
        };
        return bullet;
    }

    public static BulletConfiguration getNukePumpkinConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_nuke_pumpkin);
        bullet.explosive = 10.0f;
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if (bullet.world.isRemote) {
                    double posX = bullet.posX;
                    double posY = bullet.posY + 0.5;
                    double posZ = bullet.posZ;
                    if (y >= 0) {
                        posX = (double)x + 0.5;
                        posY = (double)y + 1.5;
                        posZ = (double)z + 0.5;
                    }
                    ExplosionLarge.spawnParticles((World)bullet.world, (double)posX, (double)posY, (double)posZ, (int)45);
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getMirvConfig() {
        BulletConfiguration bullet = GunFatmanFactory.getNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_mirv);
        bullet.style = 8;
        bullet.velocity *= 3.0f;
        bullet.bUpdate = new IBulletUpdateBehavior(){

            public void behaveUpdate(EntityBulletBase bullet) {
                if (bullet.world.isRemote) {
                    return;
                }
                if (bullet.ticksExisted == 15) {
                    bullet.setDead();
                    for (int i = 0; i < 6; ++i) {
                        EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_NORMAL);
                        nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        double mod = 0.1;
                        nuke.motionX = bullet.world.rand.nextGaussian() * mod;
                        nuke.motionY = -0.1;
                        nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
                        bullet.world.spawnEntity((Entity)nuke);
                    }
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getMirvLowConfig() {
        BulletConfiguration bullet = GunFatmanFactory.getNukeLowConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_mirv_low);
        bullet.style = 8;
        bullet.velocity *= 3.0f;
        bullet.bUpdate = new IBulletUpdateBehavior(){

            public void behaveUpdate(EntityBulletBase bullet) {
                if (bullet.world.isRemote) {
                    return;
                }
                if (bullet.ticksExisted == 15) {
                    bullet.setDead();
                    for (int i = 0; i < 6; ++i) {
                        EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_LOW);
                        nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        double mod = 0.1;
                        nuke.motionX = bullet.world.rand.nextGaussian() * mod;
                        nuke.motionY = -0.1;
                        nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
                        bullet.world.spawnEntity((Entity)nuke);
                    }
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getMirvHighConfig() {
        BulletConfiguration bullet = GunFatmanFactory.getNukeHighConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_mirv_high);
        bullet.style = 8;
        bullet.velocity *= 3.0f;
        bullet.bUpdate = new IBulletUpdateBehavior(){

            public void behaveUpdate(EntityBulletBase bullet) {
                if (bullet.world.isRemote) {
                    return;
                }
                if (bullet.ticksExisted == 15) {
                    bullet.setDead();
                    for (int i = 0; i < 6; ++i) {
                        EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_HIGH);
                        nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        double mod = 0.1;
                        nuke.motionX = bullet.world.rand.nextGaussian() * mod;
                        nuke.motionY = -0.1;
                        nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
                        bullet.world.spawnEntity((Entity)nuke);
                    }
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getMirvSafeConfig() {
        BulletConfiguration bullet = GunFatmanFactory.getNukeSafeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_mirv_safe);
        bullet.style = 8;
        bullet.velocity *= 3.0f;
        bullet.bUpdate = new IBulletUpdateBehavior(){

            public void behaveUpdate(EntityBulletBase bullet) {
                if (bullet.world.isRemote) {
                    return;
                }
                if (bullet.ticksExisted == 15) {
                    bullet.setDead();
                    for (int i = 0; i < 6; ++i) {
                        EntityBulletBase nuke = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_SAFE);
                        nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        double mod = 0.1;
                        nuke.motionX = bullet.world.rand.nextGaussian() * mod;
                        nuke.motionY = -0.1;
                        nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
                        bullet.world.spawnEntity((Entity)nuke);
                    }
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getMirvSpecialConfig() {
        BulletConfiguration bullet = GunFatmanFactory.getNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_mirv_special);
        bullet.style = 8;
        bullet.velocity *= 3.0f;
        bullet.bUpdate = new IBulletUpdateBehavior(){

            public void behaveUpdate(EntityBulletBase bullet) {
                if (bullet.world.isRemote) {
                    return;
                }
                if (bullet.ticksExisted == 15) {
                    bullet.setDead();
                    for (int i = 0; i < 24; ++i) {
                        EntityBulletBase nuke = null;
                        nuke = i < 6 ? new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_LOW) : (i < 12 ? new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_TOTS) : (i < 18 ? new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_NORMAL) : new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.NUKE_AMAT)));
                        nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        double mod = 0.25;
                        nuke.motionX = bullet.world.rand.nextGaussian() * mod;
                        nuke.motionY = -0.1;
                        nuke.motionZ = bullet.world.rand.nextGaussian() * mod;
                        bullet.world.spawnEntity((Entity)nuke);
                    }
                }
            }
        };
        return bullet;
    }

    public static BulletConfiguration getBalefireConfig() {
        BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
        bullet.ammo = new RecipesCommon.ComparableStack(ModItems.gun_bf_ammo);
        bullet.style = 10;
        bullet.bImpact = new IBulletImpactBehavior(){

            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if (!bullet.world.isRemote) {
                    double posX = bullet.posX;
                    double posY = bullet.posY + 0.5;
                    double posZ = bullet.posZ;
                    if (y >= 0) {
                        posX = (double)x + 0.5;
                        posY = (double)y + 1.5;
                        posZ = (double)z + 0.5;
                    }
                    bullet.world.playSound(null, (double)x, (double)y, (double)z, HBMSoundHandler.mukeExplosion, SoundCategory.HOSTILE, 15.0f, 1.0f);
                    ExplosionLarge.spawnShrapnels((World)bullet.world, (double)posX, (double)posY, (double)posZ, (int)25);
                    ExplosionNT exp = new ExplosionNT(bullet.world, null, posX, posY, posZ, 15.0f).addAttrib(ExplosionNT.ExAttrib.BALEFIRE).addAttrib(ExplosionNT.ExAttrib.NOPARTICLE).addAttrib(ExplosionNT.ExAttrib.NOSOUND).addAttrib(ExplosionNT.ExAttrib.NODROP).addAttrib(ExplosionNT.ExAttrib.NOHURT).overrideResolution(64);
                    exp.explode();
                    if (BombConfig.enableNukeClouds) {
                        EntityNukeTorex.statFacBale((World)bullet.world, (double)posX, (double)posY, (double)posZ, (float)15.0f);
                    }
                }
            }
        };
        return bullet;
    }
}
