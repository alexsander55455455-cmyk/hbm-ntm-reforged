package com.hbmspace.dim.thatmo;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.gen.nbt.JigsawPiece;
import com.hbm.world.gen.nbt.JigsawPool;
import com.hbm.world.gen.nbt.NBTStructure;
import com.hbm.world.gen.nbt.SpawnCondition;
import com.hbmspace.config.SpaceConfig;
import com.hbmspace.main.StructureManagerSpace;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Random;

public class WorldGeneratorThatmo implements IWorldGenerator {

    private NBTStructure.GenStructure nbtGen = new NBTStructure.GenStructure();
    private boolean hasPopulationEvent = false;

    public WorldGeneratorThatmo() {
        NBTStructure.registerStructure(SpaceConfig.thatmoDimension, new SpawnCondition("thatmotest") {{
            structure = new JigsawPiece("thatmotest", StructureManagerSpace.THATMOTESTMO, -1);
            canSpawn = biome -> biome.getHeightVariation() < 0.1F;
        }});

        NBTStructure.registerStructure(SpaceConfig.thatmoDimension, new SpawnCondition("thatmo2") {{
            structure = new JigsawPiece("thatmotest2", StructureManagerSpace.thatmo2, 1);
            canSpawn = biome -> biome.getHeightVariation() < 0.1F;
        }});

        NBTStructure.registerStructure(SpaceConfig.thatmoDimension, new SpawnCondition("trenches") {{
            JigsawPiece rupture = new JigsawPiece("trenches", StructureManagerSpace.trenches, -1);
            rupture.conformToTerrain = true;
            rupture.blockTable = new HashMap<Block, BlockSelector>() {{
                put(ModBlocks.brick_concrete_cracked, new ThatmoConcreteBricks());
            }};

            structure = rupture;
            spawnWeight = 2;
            canSpawn = biome -> biome.getHeightVariation() < 0.1F;
        }});

        NBTStructure.registerStructure(SpaceConfig.thatmoDimension, new SpawnCondition("city") {{
            sizeLimit = 128;
            canSpawn = biome -> biome.getHeightVariation() < 0.1F;
            startPool = "default";
            pools = new HashMap<String, JigsawPool>() {{
                put("default", new JigsawPool() {{
                    add(new JigsawPiece("intersection", StructureManagerSpace.intersection) {{ alignToTerrain = true; }}, 1);
                    add(new JigsawPiece("road_1", StructureManagerSpace.road) {{ conformToTerrain = true; }}, 1);
                    add(new JigsawPiece("curve_1", StructureManagerSpace.curve) {{ conformToTerrain = true; }}, 1);
                    add(new JigsawPiece("tshape", StructureManagerSpace.tshape) {{ conformToTerrain = true; }}, 1);
                    add(new JigsawPiece("block1", StructureManagerSpace.block1) {{ alignToTerrain = true; }}, 1);
                    add(new JigsawPiece("block2", StructureManagerSpace.block2) {{ alignToTerrain = true; }}, 1);
                    add(new JigsawPiece("pfmfac", StructureManagerSpace.pfmfac) {{ alignToTerrain = true; }}, 1);
                }});
                put("roadsonly", new JigsawPool() {{
                    add(new JigsawPiece("road_2", StructureManagerSpace.road) {{ conformToTerrain = true; }}, 1);
                    add(new JigsawPiece("curve_2", StructureManagerSpace.curve) {{ conformToTerrain = true; }}, 1);
                }});
            }};
        }});

        NBTStructure.registerNullWeight(SpaceConfig.thatmoDimension, 16);
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {
        nbtGen = (NBTStructure.GenStructure) TerrainGen.getModdedMapGen(new NBTStructure.GenStructure(), InitMapGenEvent.EventType.CUSTOM);
        hasPopulationEvent = false;
    }

    @SubscribeEvent
    public void generateStructures(PopulateChunkEvent.Pre event) {
        hasPopulationEvent = true;

        if(event.getWorld().provider.getDimension() == SpaceConfig.thatmoDimension) {
            nbtGen.generateStructures(event.getWorld(), event.getRand(), event.getWorld().getChunkProvider(), event.getChunkX(), event.getChunkZ());
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == SpaceConfig.thatmoDimension && !hasPopulationEvent) {
            nbtGen.generateStructures(world, random, chunkProvider, chunkX, chunkZ);
        }
    }

    private static class ThatmoConcreteBricks extends BlockSelector {

        @Override
        public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
            float chance = rand.nextFloat();

            if(chance < 0.4F) {
                this.blockstate = ModBlocks.brick_concrete.getDefaultState();
            } else if(chance < 0.7F) {
                this.blockstate = ModBlocks.brick_concrete_mossy.getDefaultState();
            } else if(chance < 0.9F) {
                this.blockstate = ModBlocks.brick_concrete_cracked.getDefaultState();
            } else {
                this.blockstate = ModBlocks.brick_concrete_broken.getDefaultState();
            }
        }
    }
}
