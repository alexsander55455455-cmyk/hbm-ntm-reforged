package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gun4GaugeFactory {

    private static GunConfiguration getShotgunConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 15;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = 0;
        config.reloadDuration = 10;
        config.firingDuration = 0;
        config.ammoCap = 4;
        config.reloadType = 2;
        config.allowsInfinity = true;
        config.hasSights = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CIRCLE;
        config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
        return config;
    }

    public static GunConfiguration getKS23Config() {
        GunConfiguration config = getShotgunConfig();
        config.durability = 3000;
        config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
        config.firingSound = HBMSoundHandler.revolverShootAlt;
        config.firingPitch = 0.65f;
        config.name = "KS-23";
        config.manufacturer = "Tulsky Oruzheiny Zavod";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.G4_NORMAL);
        config.config.add(BulletConfigSyncingUtil.G4_SLUG);
        config.config.add(BulletConfigSyncingUtil.G4_FLECHETTE);
        config.config.add(BulletConfigSyncingUtil.G4_FLECHETTE_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.G4_EXPLOSIVE);
        config.config.add(BulletConfigSyncingUtil.G4_SEMTEX);
        config.config.add(BulletConfigSyncingUtil.G4_BALEFIRE);
        config.config.add(BulletConfigSyncingUtil.G4_KAMPF);
        config.config.add(BulletConfigSyncingUtil.G4_CANISTER);
        config.config.add(BulletConfigSyncingUtil.G4_SLEEK);
        config.config.add(BulletConfigSyncingUtil.G4_CLAW);
        config.config.add(BulletConfigSyncingUtil.G4_VAMPIRE);
        config.config.add(BulletConfigSyncingUtil.G4_VOID);
        return config;
    }

    public static GunConfiguration getSauerConfig() {
        GunConfiguration config = getShotgunConfig();
        config.rateOfFire = 20;
        config.ammoCap = 0;
        config.reloadType = 0;
        config.firingMode = 1;
        config.durability = 3000;
        config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
        config.firingSound = HBMSoundHandler.sauerGun;
        config.firingPitch = 1.0f;
        config.name = "Sauer Shotgun";
        config.manufacturer = "Cube 2: Sauerbraten";

        config.animations = new HashMap<HbmAnimations.AnimType, BusAnimation>();
        BusAnimation anim = new BusAnimation();

        BusAnimationSequence seqRecoil = new BusAnimationSequence();
        seqRecoil.addKeyframe(new BusAnimationKeyframe(0.5D, 0.0D, 0.0D, 50));
        seqRecoil.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 50));
        anim.addBus("SAUER_RECOIL", seqRecoil);

        BusAnimationSequence seqTilt = new BusAnimationSequence();
        seqTilt.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 200));
        seqTilt.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 30.0D, 150));
        seqTilt.addKeyframe(new BusAnimationKeyframe(45.0D, 0.0D, 30.0D, 150));
        seqTilt.addKeyframe(new BusAnimationKeyframe(45.0D, 0.0D, 30.0D, 200));
        seqTilt.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 30.0D, 150));
        seqTilt.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 150));
        anim.addBus("SAUER_TILT", seqTilt);

        BusAnimationSequence seqCock = new BusAnimationSequence();
        seqCock.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 500));
        seqCock.addKeyframe(new BusAnimationKeyframe(1.0D, 0.0D, 0.0D, 100));
        seqCock.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 100));
        anim.addBus("SAUER_COCK", seqCock);

        BusAnimationSequence seqEject = new BusAnimationSequence();
        seqEject.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 0.0D, 500));
        seqEject.addKeyframe(new BusAnimationKeyframe(0.0D, 0.0D, 1.0D, 500));
        anim.addBus("SAUER_SHELL_EJECT", seqEject);

        config.animations.put(HbmAnimations.AnimType.CYCLE, anim);

        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.G4_NORMAL);
        config.config.add(BulletConfigSyncingUtil.G4_SLUG);
        config.config.add(BulletConfigSyncingUtil.G4_FLECHETTE);
        config.config.add(BulletConfigSyncingUtil.G4_FLECHETTE_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.G4_EXPLOSIVE);
        config.config.add(BulletConfigSyncingUtil.G4_SEMTEX);
        config.config.add(BulletConfigSyncingUtil.G4_BALEFIRE);
        config.config.add(BulletConfigSyncingUtil.G4_KAMPF);
        config.config.add(BulletConfigSyncingUtil.G4_CANISTER);
        config.config.add(BulletConfigSyncingUtil.G4_SLEEK);
        config.config.add(BulletConfigSyncingUtil.G4_CLAW);
        config.config.add(BulletConfigSyncingUtil.G4_VAMPIRE);
        config.config.add(BulletConfigSyncingUtil.G4_VOID);
        return config;
    }

    public static BulletConfiguration get4GaugeConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBuckshotConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge);
        config.dmgMin = 3.0f;
        config.dmgMax = 6.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        return config;
    }

    public static BulletConfiguration get4GaugeSlugConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_slug);
        config.dmgMin = 15.0f;
        config.dmgMax = 20.0f;
        config.wear = 7;
        config.style = 0;
        return config;
    }

    public static BulletConfiguration get4GaugeExplosiveConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_explosive);
        config.velocity *= 2.0f;
        config.gravity *= 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 25;
        config.trail = 1;
        return config;
    }

    public static BulletConfiguration get4GaugeSleekConfig() {
        BulletConfiguration config = BulletConfigFactory.standardAirstrikeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_sleek);
        return config;
    }

    public static BulletConfiguration get4GaugeFlechetteConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBuckshotConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_flechette);
        config.dmgMin = 5.0f;
        config.dmgMax = 8.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        config.wear = 15;
        config.style = 1;
        config.HBRC = 2;
        config.LBRC = 95;
        return config;
    }

    public static BulletConfiguration get4GaugeFlechettePhosphorusConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBuckshotConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_flechette_phosphorus);
        config.dmgMin = 5.0f;
        config.dmgMax = 8.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        config.wear = 15;
        config.style = 1;
        config.HBRC = 2;
        config.LBRC = 95;
        config.incendiary = 5;

        PotionEffect effect = new PotionEffect(HbmPotion.phosphorus, 400, 0, true, false);
        effect.getCurativeItems().clear();
        config.effects = new ArrayList<PotionEffect>();
        config.effects.add(new PotionEffect(effect));

        config.bImpact = new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("type", "vanillaburst");
                tag.setString("mode", "flame");
                tag.setInteger("count", 15);
                tag.setDouble("motion", 0.05d);
                PacketDispatcher.wrapper.sendToAllAround(
                    new AuxParticlePacketNT(tag, bullet.posX, bullet.posY, bullet.posZ),
                    new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50.0D)
                );
            }
        };
        return config;
    }

    public static BulletConfiguration get4GaugeMiningConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_semtex);
        config.velocity *= 2.0f;
        config.gravity *= 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 25;
        config.trail = 1;
        config.explosive = 0.0f;
        config.bImpact = new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if (bullet.world.isRemote) return;
                ExplosionNT explosion = new ExplosionNT(bullet.world, null, bullet.posX, bullet.posY, bullet.posZ, 4.0f);
                explosion.atttributes.add(ExplosionNT.ExAttrib.ALLDROP);
                explosion.atttributes.add(ExplosionNT.ExAttrib.NOHURT);
                explosion.explode();
                ExplosionLarge.spawnParticles(bullet.world, bullet.posX, bullet.posY, bullet.posZ, 15);
            }
        };
        return config;
    }

    public static BulletConfiguration get4GaugeBalefireConfig() {
        BulletConfiguration config = BulletConfigFactory.standardGrenadeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_balefire);
        config.velocity *= 2.0f;
        config.gravity *= 2.0f;
        config.dmgMin = 10.0f;
        config.dmgMax = 15.0f;
        config.wear = 25;
        config.trail = 1;
        config.explosive = 0.0f;
        config.bImpact = new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if (bullet.world.isRemote) return;
                ExplosionNT explosion = new ExplosionNT(bullet.world, null, bullet.posX, bullet.posY, bullet.posZ, 6.0f);
                explosion.atttributes.add(ExplosionNT.ExAttrib.BALEFIRE);
                explosion.explode();
                ExplosionLarge.spawnParticles(bullet.world, bullet.posX, bullet.posY, bullet.posZ, 30);
            }
        };
        return config;
    }

    public static BulletConfiguration getGrenadeKampfConfig() {
        BulletConfiguration config = BulletConfigFactory.standardRocketConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_kampf);
        config.spread = 0.0f;
        config.gravity = 0.0f;
        config.wear = 15;
        config.explosive = 3.5f;
        config.style = 9;
        config.trail = 4;
        config.vPFX = "smoke";
        return config;
    }

    public static BulletConfiguration getGrenadeCanisterConfig() {
        BulletConfiguration config = BulletConfigFactory.standardRocketConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_canister);
        config.spread = 0.0f;
        config.gravity = 0.0f;
        config.wear = 15;
        config.explosive = 1.0f;
        config.style = 9;
        config.trail = 4;
        config.vPFX = "smoke";
        config.bUpdate = new IBulletUpdateBehavior() {
            @Override
            public void behaveUpdate(EntityBulletBase bullet) {
                if (!bullet.world.isRemote && bullet.ticksExisted > 10) {
                    bullet.setDead();
                    for (int i = 0; i < 50; i++) {
                        EntityBulletBase sub = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.M44_AP);
                        sub.setPosition(bullet.posX, bullet.posY, bullet.posZ);
                        sub.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 0.25f, 0.1f);
                        bullet.world.spawnEntity(sub);
                    }
                }
            }
        };
        return config;
    }

    public static BulletConfiguration get4GaugeClawConfig() {
        BulletConfiguration config = get4GaugeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_claw);
        config.dmgMin = 6.0f;
        config.dmgMax = 9.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        config.leadChance = 100;
        config.bHurt = new IBulletHurtBehavior() {
            @Override
            public void behaveEntityHurt(EntityBulletBase bullet, Entity entity) {
                if (bullet.world.isRemote) return;
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase living = (EntityLivingBase) entity;
                    float hp = living.getHealth();
                    hp = Math.max(0.0f, hp - 2.0f);
                    living.setHealth(hp);
                    if (hp == 0.0f) {
                        living.onDeath(ModDamageSource.lead);
                    }
                }
            }
        };
        return config;
    }

    public static BulletConfiguration get4GaugeVampireConfig() {
        BulletConfiguration config = get4GaugeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_vampire);
        config.dmgMin = 6.0f;
        config.dmgMax = 9.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        config.leadChance = 100;
        config.style = 1;
        config.bHurt = new IBulletHurtBehavior() {
            @Override
            public void behaveEntityHurt(EntityBulletBase bullet, Entity entity) {
                // does nothing or empty stub
            }
        };
        return config;
    }

    public static BulletConfiguration get4GaugeVoidConfig() {
        BulletConfiguration config = get4GaugeConfig();
        config.ammo = new com.hbm.inventory.RecipesCommon.ComparableStack(ModItems.ammo_4gauge_void);
        config.dmgMin = 6.0f;
        config.dmgMax = 9.0f;
        config.bulletsMin *= 2;
        config.bulletsMax *= 2;
        config.leadChance = 0;
        config.bHurt = new IBulletHurtBehavior() {
            @Override
            public void behaveEntityHurt(EntityBulletBase bullet, Entity entity) {
                if (bullet.world.isRemote) return;
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    player.inventory.dropAllItems();
                }
            }
        };
        return config;
    }
}
