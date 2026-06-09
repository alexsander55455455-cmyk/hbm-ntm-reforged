package com.hbm.handler.radiation;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.WasteLeaves;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.handler.radiation.RadiationSystemNT.RadPocket;
import com.hbm.main.MainRegistry;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Collection;

class RadiationWorldHandler {
    private static final IBlockState AIR_DEFAULT_STATE = Blocks.AIR.getDefaultState();
    private static final float WORLD_RAD_THRESHOLD = 5.0F;

    static void handleWorldDestruction(World world) {
        if (!(world instanceof WorldServer)) return;
        if (!RadiationConfig.worldRadEffects || !GeneralConfig.enableRads || !GeneralConfig.advancedRadiation) return;

        if (GeneralConfig.enableDebugMode) {
            MainRegistry.logger.info("[Debug] Starting world destruction processing");
        }

        Collection<RadPocket> activePockets = RadiationSystemNT.getActiveCollection(world);
        if (activePockets.isEmpty()) return;

        int randIdx = world.rand.nextInt(activePockets.size());
        int itr = 0;
        for (RadPocket p : activePockets) {
            if (itr == randIdx) {
                if (p.radiation < WORLD_RAD_THRESHOLD) return;
                BlockPos startPos = p.getSubChunkPos();
                RadPocket[] pocketsByBlock = p.parent.pocketsByBlock;
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 16; k++) {
                            if (world.rand.nextInt(3) != 0) continue;
                            if (pocketsByBlock != null && pocketsByBlock[i * 16 * 16 + j * 16 + k] != p) continue;
                            BlockPos pos = startPos.add(i, j, k);
                            IBlockState state = world.getBlockState(pos);
                            if (!world.isAirBlock(pos)) {
                                decayBlock(world, pos, state);
                            }
                        }
                    }
                }
                break;
            }
            itr++;
        }

        if (GeneralConfig.enableDebugMode) {
            MainRegistry.logger.info("[Debug] Finished world destruction processing");
        }
    }

    static void decayBlock(World world, BlockPos pos, IBlockState state) {
        Block block = state.getBlock();
        if (block.getRegistryName() == null) return;

        if (block instanceof BlockDoublePlant) {
            BlockDoublePlant.EnumBlockHalf half;
            try {
                half = state.getValue(BlockDoublePlant.HALF);
            } catch (Exception _) { return; }
            BlockPos lowerPos = (half == BlockDoublePlant.EnumBlockHalf.LOWER) ? pos : pos.down();
            BlockPos upperPos = (half == BlockDoublePlant.EnumBlockHalf.LOWER) ? pos.up() : pos;
            world.setBlockState(upperPos, AIR_DEFAULT_STATE, 2);
            world.setBlockState(lowerPos, ModBlocks.waste_grass_tall.getDefaultState(), 2);
            return;
        }

        ResourceLocation registryName = block.getRegistryName();
        String namespace = registryName.getNamespace();
        String path = registryName.getPath();

        if ("hbm".equals(namespace) && "waste_leaves".equals(path)) {
            if (world.rand.nextInt(8) == 0) {
                world.setBlockToAir(pos);
            }
            return;
        }
        if (!"minecraft".equals(namespace)) return;

        if (block == Blocks.GRASS) {
            world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.DIRT || block == Blocks.FARMLAND) {
            world.setBlockState(pos, ModBlocks.waste_dirt.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.SANDSTONE) {
            world.setBlockState(pos, ModBlocks.waste_sandstone.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.RED_SANDSTONE) {
            world.setBlockState(pos, ModBlocks.waste_sandstone_red.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY) {
            world.setBlockState(pos, ModBlocks.waste_terracotta.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.SAND) {
            BlockSand.EnumType meta = state.getValue(BlockSand.VARIANT);
            world.setBlockState(pos, meta == BlockSand.EnumType.SAND
                    ? ModBlocks.waste_sand.getDefaultState()
                    : ModBlocks.waste_sand_red.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.GRAVEL) {
            world.setBlockState(pos, ModBlocks.waste_gravel.getDefaultState(), 2);
            return;
        }
        if (block == Blocks.MYCELIUM) {
            world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState(), 2);
            return;
        }
        if (block instanceof BlockSnow) {
            world.setBlockState(pos, ModBlocks.waste_snow.getDefaultState(), 2);
            return;
        }
        if (block instanceof BlockSnowBlock) {
            world.setBlockState(pos, ModBlocks.waste_snow_block.getDefaultState(), 2);
            return;
        }
        if (block instanceof BlockIce) {
            world.setBlockState(pos, ModBlocks.waste_ice.getDefaultState(), 2);
            return;
        }
        if (block instanceof BlockBush) {
            world.setBlockState(pos, ModBlocks.waste_grass_tall.getDefaultState(), 2);
            return;
        }
        if (block instanceof BlockLeaves && !(block instanceof WasteLeaves)) {
            BlockLeaves leaf = (BlockLeaves) block;
            BlockPlanks.EnumType type = null;
            try {
                type = leaf.getWoodType(leaf.getMetaFromState(state));
            } catch (UnsupportedOperationException ignored) {
            }
            if (type == null) type = BlockPlanks.EnumType.OAK;
            world.setBlockState(pos, ModBlocks.waste_leaves.getDefaultState().withProperty(WasteLeaves.VARIANT, type), 2);
        }
    }

    @Deprecated
    static void decayBlock(World world, BlockPos pos, IBlockState state, boolean isLegacy) {
        decayBlock(world, pos, state);
    }
}