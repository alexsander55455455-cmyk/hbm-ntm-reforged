package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;

public class Gun50BMGFactory {

    private static final float INACCURACY = 2.5F;

    public static GunConfiguration getCalamityConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 6;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 50;
        config.reloadType = GunConfiguration.RELOAD_FULL;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_BOX;
        config.durability = 2000;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.calShoot;
        config.reloadSoundEnd = false;
        config.name = "Maxim gun";
        config.manufacturer = "Hiram Maxim";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
        config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
        config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
        config.config.add(BulletConfigSyncingUtil.BMG50_AP);
        config.config.add(BulletConfigSyncingUtil.BMG50_DU);
        config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
        config.config.add(BulletConfigSyncingUtil.CHL_BMG50);
        config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
        return config;
    }

    public static GunConfiguration getSaddleConfig() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 3;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.reloadDuration = 30;
        config.firingDuration = 0;
        config.ammoCap = 100;
        config.reloadType = GunConfiguration.RELOAD_FULL;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_BOX;
        config.durability = 3500;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.calShoot;
        config.name = "Double Maxim gun";
        config.manufacturer = "???";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
        config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
        config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
        config.config.add(BulletConfigSyncingUtil.BMG50_AP);
        config.config.add(BulletConfigSyncingUtil.BMG50_DU);
        config.config.add(BulletConfigSyncingUtil.CHL_BMG50);
        config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
        config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
        return config;
    }

    public static GunConfiguration getAR15Config() {
        GunConfiguration config = new GunConfiguration();
        config.rateOfFire = 1;
        config.roundsPerCycle = 1;
        config.gunMode = 0;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.reloadDuration = 20;
        config.firingDuration = 0;
        config.ammoCap = 50;
        config.reloadType = GunConfiguration.RELOAD_FULL;
        config.allowsInfinity = true;
        config.crosshair = RenderScreenOverlay.Crosshair.L_CROSS;
        config.durability = 100000;
        config.reloadSound = GunConfiguration.RSOUND_MAG;
        config.firingSound = HBMSoundHandler.howard_fire;
        config.name = "AR-15 .50 BMG Mod";
        config.manufacturer = "Armalite";
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_AM);
        config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_PO);
        config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_NORMAL);
        config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
        config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
        config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
        config.config.add(BulletConfigSyncingUtil.BMG50_AP);
        config.config.add(BulletConfigSyncingUtil.BMG50_DU);
        config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
        config.config.add(BulletConfigSyncingUtil.CHL_BMG50);
        config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
        return config;
    }

    public static BulletConfiguration get50BMGConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg);
        config.spread *= INACCURACY;
        config.dmgMin = 15.0F;
        config.dmgMax = 18.0F;
        return config;
    }

    public static BulletConfiguration get50BMGFireConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_incendiary);
        config.spread *= INACCURACY;
        config.dmgMin = 15.0F;
        config.dmgMax = 18.0F;
        config.wear = 15;
        config.incendiary = 5;
        return config;
    }

    public static BulletConfiguration get50BMGExplosiveConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_explosive);
        config.spread *= INACCURACY;
        config.dmgMin = 20.0F;
        config.dmgMax = 25.0F;
        config.wear = 25;
        config.explosive = 1.0F;
        return config;
    }

    public static BulletConfiguration get50BMGDUConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_du);
        config.spread *= INACCURACY;
        config.dmgMin = 40.0F;
        config.dmgMax = 45.0F;
        config.wear = 25;
        config.leadChance = 50;
        return config;
    }

    public static BulletConfiguration get50BMGStarConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_star);
        config.spread *= INACCURACY;
        config.dmgMin = 50.0F;
        config.dmgMax = 70.0F;
        config.wear = 25;
        config.leadChance = 100;
        return config;
    }

    public static BulletConfiguration get50BMGPhosphorusConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_phosphorus);
        config.spread *= INACCURACY;
        config.dmgMin = 15.0F;
        config.dmgMax = 18.0F;
        config.wear = 15;
        config.incendiary = 5;
        config.doesPenetrate = false;

        PotionEffect effect = new PotionEffect(HbmPotion.phosphorus, 400, 0, true, false);
        effect.getCurativeItems().clear();
        config.effects = new ArrayList<PotionEffect>();
        config.effects.add(new PotionEffect(effect));
        config.bImpact = flameBurst();
        return config;
    }

    public static BulletConfiguration get50BMGAPConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_ap);
        config.spread *= INACCURACY;
        config.dmgMin = 30.0F;
        config.dmgMax = 25.0F;
        config.wear = 15;
        config.leadChance = 10;
        return config;
    }

    public static BulletConfiguration get50BMGSleekConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_sleek);
        config.spread *= INACCURACY;
        config.dmgMin = 50.0F;
        config.dmgMax = 70.0F;
        config.wear = 10;
        config.leadChance = 100;
        config.doesPenetrate = false;
        config.bHit = dropMeteorOnHit();
        config.bImpact = dropMeteorOnImpact();
        return config;
    }

    public static BulletConfiguration get50BMGFlechetteConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_flechette);
        config.spread *= INACCURACY;
        config.dmgMin = 20.0F;
        config.dmgMax = 25.0F;
        config.style = BulletConfiguration.STYLE_FLECHETTE;
        return config;
    }

    public static BulletConfiguration get50BMGFlechetteAMConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_flechette_am);
        config.spread *= INACCURACY;
        config.dmgMin = 50.0F;
        config.dmgMax = 65.0F;
        config.style = BulletConfiguration.STYLE_FLECHETTE;
        config.bHit = contaminateOnHit(100.0F);
        return config;
    }

    public static BulletConfiguration get50BMGFlechettePOConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_50bmg_flechette_po);
        config.spread *= INACCURACY;
        config.dmgMin = 30.0F;
        config.dmgMax = 40.0F;
        config.style = BulletConfiguration.STYLE_FLECHETTE;
        config.bHit = contaminateOnHit(50.0F);
        return config;
    }

    private static IBulletImpactBehavior flameBurst() {
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

    private static IBulletHitBehavior dropMeteorOnHit() {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
                if(bullet.world.isRemote) return;
                EntityBulletBase meteor = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.MASKMAN_METEOR);
                meteor.setPosition(hit.posX, hit.posY + 30.0D + meteor.world.rand.nextInt(10), hit.posZ);
                meteor.motionY = -1.0D;
                meteor.shooter = bullet.shooter;
                bullet.world.spawnEntity(meteor);
            }
        };
    }

    private static IBulletImpactBehavior dropMeteorOnImpact() {
        return new IBulletImpactBehavior() {
            @Override
            public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
                if(bullet.world.isRemote) return;
                if(y == -1) return;
                EntityBulletBase meteor = new EntityBulletBase(bullet.world, BulletConfigSyncingUtil.MASKMAN_METEOR);
                meteor.setPosition(bullet.posX, bullet.posY + 30.0D + meteor.world.rand.nextInt(10), bullet.posZ);
                meteor.motionY = -1.0D;
                meteor.shooter = bullet.shooter;
                bullet.world.spawnEntity(meteor);
            }
        };
    }

    private static IBulletHitBehavior contaminateOnHit(final float amount) {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
                if(bullet.world.isRemote) return;
                if(hit instanceof EntityLivingBase) {
                    ContaminationUtil.contaminate((EntityLivingBase) hit, HazardType.RADIATION, ContaminationType.RAD_BYPASS, amount);
                }
            }
        };
    }
}
