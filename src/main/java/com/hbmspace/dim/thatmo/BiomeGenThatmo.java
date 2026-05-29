package com.hbmspace.dim.thatmo;

import com.hbm.blocks.ModBlocks;
import com.hbmspace.dim.BiomeGenBaseCelestial;
import com.hbmspace.entity.mob.EntityMoonCow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeGenThatmo extends BiomeGenBaseCelestial {

    public static final BiomeGenThatmo biome = new BiomeGenThatmo(
            new Biome.BiomeProperties("Thatmo").setBaseHeight(0.125F).setHeightVariation(0.05F).setRainDisabled().setTemperature(0.0F)
    );

    private BiomeGenThatmo(BiomeProperties properties) {
        super(properties);
        properties.setBaseBiome("Thatmo");
        properties.setRainDisabled();

        this.creatures.add(new SpawnListEntry(EntityMoonCow.class, 10, 1, 1));
        this.decorator.generateFalls = false;
        this.topBlock = Blocks.SNOW.getDefaultState();
        this.fillerBlock = Blocks.SNOW.getDefaultState();
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int x, int z, double noise) {
        IBlockState topState = this.topBlock;
        IBlockState fillerState = this.fillerBlock;
        int layerDepthRemaining = -1;
        int surfaceDepth = (int) (noise / 6.0D + 6.0D + rand.nextDouble() * 0.85D);
        int localX = x & 15;
        int localZ = z & 15;

        for(int y = world.getActualHeight() - 1; y >= 0; --y) {
            if(y <= rand.nextInt(5)) {
                primer.setBlockState(localX, y, localZ, Blocks.BEDROCK.getDefaultState());
                continue;
            }

            IBlockState state = primer.getBlockState(localX, y, localZ);

            if(state.getMaterial() == Material.AIR) {
                layerDepthRemaining = -1;
                continue;
            }

            if(state.getBlock() != ModBlocks.basalt) {
                continue;
            }

            if(layerDepthRemaining == -1) {
                if(surfaceDepth <= 0) {
                    topState = Blocks.AIR.getDefaultState();
                    fillerState = ModBlocks.basalt.getDefaultState();
                } else if(y >= 59 && y <= 64) {
                    topState = this.topBlock;
                    fillerState = this.fillerBlock;
                }

                if(y < 63 && topState.getMaterial() == Material.AIR) {
                    topState = this.topBlock;
                }

                layerDepthRemaining = surfaceDepth;

                if(y >= 62) {
                    primer.setBlockState(localX, y, localZ, topState);
                } else if(y < 62) {
                    topState = Blocks.AIR.getDefaultState();
                    fillerState = ModBlocks.basalt.getDefaultState();
                    primer.setBlockState(localX, y, localZ,
                            rand.nextFloat() > 0.4F ? ModBlocks.basalt.getDefaultState() : ModBlocks.sellafield_slaked.getDefaultState());
                } else {
                    primer.setBlockState(localX, y, localZ, fillerState);
                }
            } else if(layerDepthRemaining > 0) {
                --layerDepthRemaining;
                primer.setBlockState(localX, y, localZ, fillerState);
            }
        }
    }
}
