package com.hbmspace.handler.registires;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbmspace.blocks.ModBlocksSpace;
import com.hbmspace.blocks.generic.BlockOreFluid;
import com.hbmspace.enums.EnumAddonTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModBlocksReplaceHandler {

    public static void initReplacings(RegistryEvent.Register<Block> event) {
        Block oldOreOil = ModBlocks.ore_oil;
        Block oldOreOilEmpty = ModBlocks.ore_oil_empty;
        Block oldBedrockOil = ModBlocks.ore_bedrock_oil;
        ModBlocks.ALL_BLOCKS.remove(oldOreOil);
        ModBlocks.ALL_BLOCKS.remove(oldOreOilEmpty);
        ModBlocks.ALL_BLOCKS.remove(oldBedrockOil);

        Block ore_oil_empty_override = new BlockBase(Material.ROCK, "ore_oil_empty").setCreativeTab(MainRegistry.resourceTab).setHardness(5.0F).setResistance(10.0F);
        Block ore_oil_override = new BlockOreFluid("ore_oil", ore_oil_empty_override, BlockOreFluid.ReserveType.OIL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
        Block ore_bedrock_oil_override = new BlockOreFluid("ore_bedrock_oil", null, BlockOreFluid.ReserveType.OIL).setCreativeTab(MainRegistry.resourceTab).setBlockUnbreakable().setResistance(1_000_000);

        EnumAddonTypes.setInstanceField(IForgeRegistryEntry.Impl.class, "registryName", ore_oil_empty_override, null);
        EnumAddonTypes.setInstanceField(IForgeRegistryEntry.Impl.class, "registryName", ore_oil_override, null);
        EnumAddonTypes.setInstanceField(IForgeRegistryEntry.Impl.class, "registryName", ore_bedrock_oil_override, null);

        ore_oil_empty_override.setRegistryName("hbm", "ore_oil_empty");
        ore_oil_override.setRegistryName("hbm", "ore_oil");
        ore_bedrock_oil_override.setRegistryName("hbm", "ore_bedrock_oil");

        event.getRegistry().register(ore_oil_empty_override);
        event.getRegistry().register(ore_oil_override);
        event.getRegistry().register(ore_bedrock_oil_override);

        EnumAddonTypes.setStaticField(ModBlocks.class, "ore_oil_empty", ore_oil_empty_override);
        EnumAddonTypes.setStaticField(ModBlocks.class, "ore_oil", ore_oil_override);
        EnumAddonTypes.setStaticField(ModBlocks.class, "ore_bedrock_oil", ore_bedrock_oil_override);

        // Registered above; keep out of automatic registration loops to avoid duplicate registry IDs.
        ModBlocks.ALL_BLOCKS.remove(ore_oil_empty_override);
        ModBlocksSpace.ALL_BLOCKS.remove(ore_oil_override);
        ModBlocksSpace.ALL_BLOCKS.remove(ore_bedrock_oil_override);
    }
}