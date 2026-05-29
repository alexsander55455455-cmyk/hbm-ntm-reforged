package com.hbmspace.dim.thatmo;

import com.hbm.blocks.ModBlocks;
import com.hbmspace.dim.ChunkProviderCelestial;
import com.hbmspace.dim.mapgen.MapGenCrater;
import com.hbmspace.dim.mapgen.MapGenGreg;
import com.hbmspace.dim.mapgen.MapgenRavineButBased;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ChunkProviderThatmo extends ChunkProviderCelestial {

    private final MapGenGreg caveGenV3 = new MapGenGreg();
    private final MapgenRavineButBased ravineGen = new MapgenRavineButBased();
    private final MapGenCrater smallCrater = new MapGenCrater(6);

    public ChunkProviderThatmo(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);

        caveGenV3.stoneBlock = ModBlocks.sellafield_slaked;
        ravineGen.stoneBlock = ModBlocks.sellafield_slaked;

        smallCrater.setSize(8, 32);
        smallCrater.regolith = ModBlocks.sellafield_slaked;
        smallCrater.rock = ModBlocks.sellafield_slaked;

        stoneBlock = ModBlocks.basalt;
        seaBlock = ModBlocks.basalt;
        seaLevel = 64;
    }

    @Override
    public ChunkPrimer getChunkPrimer(int x, int z) {
        ChunkPrimer primer = super.getChunkPrimer(x, z);

        caveGenV3.generate(worldObj, x, z, primer);
        ravineGen.generate(worldObj, x, z, primer);
        smallCrater.generate(worldObj, x, z, primer);

        return primer;
    }

    @Override
    public boolean generateStructures(@NotNull Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    @Nullable
    public BlockPos getNearestStructurePos(@NotNull World worldIn, @NotNull String structureName, @NotNull BlockPos position, boolean findUnexplored) {
        return null;
    }
}
