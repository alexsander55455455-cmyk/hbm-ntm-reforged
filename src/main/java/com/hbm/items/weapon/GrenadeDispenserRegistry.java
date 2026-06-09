package com.hbm.items.weapon;

import com.hbm.entity.grenade.*;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemFertilizer;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrenadeDispenserRegistry {

    public static void registerDispenserBehaviors() {
        registerGrenade(ModItems.grenade_generic, EntityGrenadeGeneric.class);
        registerGrenade(ModItems.grenade_strong, EntityGrenadeStrong.class);
        registerGrenade(ModItems.grenade_frag, EntityGrenadeFrag.class);
        registerGrenade(ModItems.grenade_fire, EntityGrenadeFire.class);
        registerGrenade(ModItems.grenade_cluster, EntityGrenadeCluster.class);
        registerGrenade(ModItems.grenade_flare, EntityGrenadeFlare.class);
        registerGrenade(ModItems.grenade_electric, EntityGrenadeElectric.class);
        registerGrenade(ModItems.grenade_poison, EntityGrenadePoison.class);
        registerGrenade(ModItems.grenade_gas, EntityGrenadeGas.class);
        registerGrenade(ModItems.grenade_schrabidium, EntityGrenadeSchrabidium.class);
        registerGrenade(ModItems.grenade_nuke, EntityGrenadeNuke.class);
        registerGrenade(ModItems.grenade_nuclear, EntityGrenadeNuclear.class);
        registerGrenade(ModItems.grenade_pulse, EntityGrenadePulse.class);
        registerGrenade(ModItems.grenade_plasma, EntityGrenadePlasma.class);
        registerGrenade(ModItems.grenade_tau, EntityGrenadeTau.class);
        registerGrenade(ModItems.grenade_lemon, EntityGrenadeLemon.class);
        registerGrenade(ModItems.grenade_mk2, EntityGrenadeMk2.class);
        registerGrenade(ModItems.grenade_aschrab, EntityGrenadeASchrab.class);
        registerGrenade(ModItems.grenade_zomg, EntityGrenadeZOMG.class);
        registerGrenade(ModItems.grenade_solinium, EntityGrenadeSolinium.class);
        registerGrenade(ModItems.grenade_shrapnel, EntityGrenadeShrapnel.class);
        registerGrenade(ModItems.grenade_black_hole, EntityGrenadeBlackHole.class);
        registerGrenade(ModItems.grenade_gascan, EntityGrenadeGascan.class);
        registerGrenade(ModItems.grenade_cloud, EntityGrenadeCloud.class);
        registerGrenade(ModItems.grenade_pink_cloud, EntityGrenadePC.class);
        registerGrenade(ModItems.grenade_smart, EntityGrenadeSmart.class);
        registerGrenade(ModItems.grenade_mirv, EntityGrenadeMIRV.class);
        registerGrenade(ModItems.grenade_breach, EntityGrenadeBreach.class);
        registerGrenade(ModItems.grenade_burst, EntityGrenadeBurst.class);
        registerGrenade(ModItems.grenade_if_generic, EntityGrenadeIFGeneric.class);
        registerGrenade(ModItems.grenade_if_he, EntityGrenadeIFHE.class);
        registerGrenade(ModItems.grenade_if_bouncy, EntityGrenadeIFBouncy.class);
        registerGrenade(ModItems.grenade_if_sticky, EntityGrenadeIFSticky.class);
        registerGrenade(ModItems.grenade_if_impact, EntityGrenadeIFImpact.class);
        registerGrenade(ModItems.grenade_if_incendiary, EntityGrenadeIFIncendiary.class);
        registerGrenade(ModItems.grenade_if_toxic, EntityGrenadeIFToxic.class);
        registerGrenade(ModItems.grenade_if_concussion, EntityGrenadeIFConcussion.class);
        registerGrenade(ModItems.grenade_if_brimstone, EntityGrenadeIFBrimstone.class);
        registerGrenade(ModItems.grenade_if_mystery, EntityGrenadeIFMystery.class);
        registerGrenade(ModItems.grenade_if_spark, EntityGrenadeIFSpark.class);
        registerGrenade(ModItems.grenade_if_hopwire, EntityGrenadeIFHopwire.class);
        registerGrenade(ModItems.grenade_if_null, EntityGrenadeIFNull.class);
    }

    private static <T extends IProjectile> void registerGrenade(net.minecraft.item.Item item, Class<T> clazz) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack stack) {
                try {
                    return clazz.getConstructor(World.class, double.class, double.class, double.class)
                            .newInstance(world, pos.getX(), pos.getY(), pos.getZ());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create grenade entity " + clazz.getSimpleName(), e);
                }
            }
        });
    }

    public static void registerDispenserBehaviorFertilizer() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.powder_fertilizer, new BehaviorDefaultDispenseItem() {

            private boolean dispenseSound = true;

            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World world = source.getWorld();
                EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
                BlockPos targetPos = source.getBlockPos().offset(facing);
                this.dispenseSound = ItemFertilizer.useFertillizer(stack, world, targetPos.getX(), targetPos.getY(), targetPos.getZ());
                return stack;
            }

            @Override
            protected void playDispenseSound(IBlockSource source) {
                World world = source.getWorld();
                BlockPos pos = source.getBlockPos();
                if (this.dispenseSound) {
                    world.playEvent(1000, pos, 0);
                } else {
                    world.playEvent(1001, pos, 0);
                }
            }
        });
    }
}