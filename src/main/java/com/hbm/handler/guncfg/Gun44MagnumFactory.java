package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityDuchessGambit;
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
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;

public class Gun44MagnumFactory {

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
        config.firingSound = HBMSoundHandler.revolverShootAlt;
        config.reloadSoundEnd = false;
        return config;
    }

    public static GunConfiguration getNovacConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 2500;
        config.name = "IF-18 Horseshoe";
        config.manufacturer = "Ironshod Firearms";
        config.comment.add("Fallout New Vegas wasn't THAT good.");
        config.config = new ArrayList<Integer>();
        addCommon44(config);
        return config;
    }

    public static GunConfiguration getMacintoshConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.name = "IF-18 Horseshoe Scoped";
        config.manufacturer = "Ironshod Firearms";
        config.comment.add("Poppin' mentats like tic tacs");
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.M44_PIP);
        addCommon44(config);
        return config;
    }

    public static GunConfiguration getBlackjackConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.ammoCap = 5;
        config.name = "IF-18 Horseshoe Vanity";
        config.manufacturer = "Ironshod Firearms";
        config.comment.add("Alcoholism is cool!");
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.M44_BJ);
        addCommon44(config);
        return config;
    }

    public static GunConfiguration getSilverConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.ammoCap = 6;
        config.name = "IF-18 Horseshoe Silver Storm";
        config.manufacturer = "Ironshod Firearms";
        config.comment.add("Our friendship is based on abusive behaviour");
        config.comment.add("and mutual hate. It's not that complicated.");
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.M44_SILVER);
        addCommon44(config);
        return config;
    }

    public static GunConfiguration getRedConfig() {
        GunConfiguration config = getBaseConfig();
        config.durability = 4000;
        config.ammoCap = 64;
        config.name = "IF-18 Horseshoe Bottomless Pit";
        config.manufacturer = "Ironshod Firearms R&D";
        config.comment.add("Explore the other side");
        config.comment.add("...from afar!");
        config.config = new ArrayList<Integer>();
        config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
        config.config.add(BulletConfigSyncingUtil.M44_AP);
        config.config.add(BulletConfigSyncingUtil.M44_DU);
        config.config.add(BulletConfigSyncingUtil.M44_STAR);
        config.config.add(BulletConfigSyncingUtil.CHL_M44);
        config.config.add(BulletConfigSyncingUtil.M44_PIP);
        config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.M44_BJ);
        config.config.add(BulletConfigSyncingUtil.M44_SILVER);
        config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
        return config;
    }

    private static void addCommon44(GunConfiguration config) {
        config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
        config.config.add(BulletConfigSyncingUtil.M44_AP);
        config.config.add(BulletConfigSyncingUtil.M44_DU);
        config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
        config.config.add(BulletConfigSyncingUtil.M44_STAR);
        config.config.add(BulletConfigSyncingUtil.CHL_M44);
        config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
    }

    public static BulletConfiguration getNoPipConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44);
        config.dmgMin = 5.0F;
        config.dmgMax = 7.0F;
        return config;
    }

    public static BulletConfiguration getNoPipAPConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_ap);
        config.dmgMin = 7.0F;
        config.dmgMax = 10.0F;
        config.wear = 15;
        config.leadChance = 10;
        return config;
    }

    public static BulletConfiguration getNoPipDUConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_du);
        config.dmgMin = 7.0F;
        config.dmgMax = 10.0F;
        config.wear = 25;
        config.leadChance = 50;
        return config;
    }

    public static BulletConfiguration getNoPipStarConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_star);
        config.dmgMin = 14.0F;
        config.dmgMax = 20.0F;
        config.wear = 25;
        config.leadChance = 100;
        return config;
    }

    public static BulletConfiguration getPipConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_pip);
        config.dmgMin = 4.0F;
        config.dmgMax = 5.0F;
        config.wear = 25;
        config.doesPenetrate = false;
        config.bHit = dropBoxcar();
        return config;
    }

    public static BulletConfiguration getBJConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_bj);
        config.dmgMin = 4.0F;
        config.dmgMax = 5.0F;
        config.wear = 25;
        config.doesPenetrate = false;
        config.bHit = dropDuchessGambit();
        return config;
    }

    public static BulletConfiguration getSilverStormConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_silver);
        config.dmgMin = 4.0F;
        config.dmgMax = 5.0F;
        config.wear = 25;
        config.doesPenetrate = false;
        config.bHit = dropBuilding();
        return config;
    }

    public static BulletConfiguration getRocketConfig() {
        BulletConfiguration config = BulletConfigFactory.standardRocketConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_rocket);
        config.velocity = 5.0F;
        config.explosive = 15.0F;
        config.trail = 1;
        return config;
    }

    public static BulletConfiguration getPhosphorusConfig() {
        BulletConfiguration config = BulletConfigFactory.standardBulletConfig();
        config.ammo = new RecipesCommon.ComparableStack(ModItems.ammo_44_phosphorus);
        config.dmgMin = 5.0F;
        config.dmgMax = 7.0F;
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

    private static IBulletHitBehavior dropBoxcar() {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
                if (bullet.world.isRemote) return;
                EntityBoxcar entity = new EntityBoxcar(bullet.world);
                dropEntity(bullet, hit, entity, HBMSoundHandler.trainHorn, 50, 4.0D, 12.0D, 4.0D);
            }
        };
    }

    private static IBulletHitBehavior dropDuchessGambit() {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
                if (bullet.world.isRemote) return;
                EntityDuchessGambit entity = new EntityDuchessGambit(bullet.world);
                dropEntity(bullet, hit, entity, HBMSoundHandler.boatWeapon, 150, 7.0D, 8.0D, 18.0D);
            }
        };
    }

    private static IBulletHitBehavior dropBuilding() {
        return new IBulletHitBehavior() {
            @Override
            public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
                if (bullet.world.isRemote) return;
                EntityBuilding entity = new EntityBuilding(bullet.world);
                dropEntity(bullet, hit, entity, HBMSoundHandler.blockDebris, 150, 15.0D, 15.0D, 15.0D);
            }
        };
    }

    private static void dropEntity(EntityBulletBase bullet, Entity hit, Entity entity, SoundEvent sound, int smokeCount, double spreadX, double spreadY, double spreadZ) {
        entity.setPosition(hit.posX, hit.posY + 50.0D, hit.posZ);
        bullet.world.spawnEntity(entity);
        smokeBurst(bullet, entity.posX, entity.posY, entity.posZ, smokeCount, spreadX, spreadY, spreadZ);
        bullet.world.playSound(null, entity.posX, entity.posY + 50.0D, entity.posZ, sound, SoundCategory.PLAYERS, 100.0F, 1.0F);
    }

    private static void smokeBurst(EntityBulletBase bullet, double x, double y, double z, int count, double spreadX, double spreadY, double spreadZ) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type", "vanillaburst");
        nbt.setString("mode", "cloud");
        nbt.setInteger("count", count);
        nbt.setDouble("motion", 0.05D);
        nbt.setDouble("spreadX", spreadX);
        nbt.setDouble("spreadY", spreadY);
        nbt.setDouble("spreadZ", spreadZ);
        PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(nbt, x, y, z),
                new NetworkRegistry.TargetPoint(bullet.dimension, x, y, z, 50.0D));
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
}
