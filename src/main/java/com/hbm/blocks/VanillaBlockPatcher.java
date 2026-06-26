package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * EE jar (_NTM-Extended-1.12.2-3.0.3.jar) contains no bytecode that patches vanilla block stats.
 * Values below match in-game EE blast resistance for vanilla blocks (JE wiki / EE smoke-test reference).
 */
public final class VanillaBlockPatcher {

    private VanillaBlockPatcher() {
    }

    public static void apply() {
        patch(Blocks.OBSIDIAN, 50.0F, 1200.0F);
        patch(Blocks.ANVIL, 5.0F, 1200.0F);
        patch(Blocks.ENCHANTING_TABLE, 5.0F, 1200.0F);
        patch(Blocks.ENDER_CHEST, 22.5F, 1200.0F);
        patch(Blocks.MOB_SPAWNER, 5.0F, 25.0F);
        patch(Blocks.BEDROCK, -1.0F, 3600000.0F);
        patch(Blocks.BARRIER, -1.0F, 3600000.0F);
        patch(Blocks.COMMAND_BLOCK, -1.0F, 3600000.0F);
        patch(Blocks.CHAIN_COMMAND_BLOCK, -1.0F, 3600000.0F);
        patch(Blocks.REPEATING_COMMAND_BLOCK, -1.0F, 3600000.0F);
        patch(Blocks.STRUCTURE_BLOCK, -1.0F, 3600000.0F);
        patch(Blocks.END_PORTAL_FRAME, -1.0F, 18000000.0F);
        patch(Blocks.END_PORTAL, -1.0F, 18000000.0F);
    }

    private static void patch(Block block, float hardness, float resistance) {
        if (hardness >= 0.0F) {
            block.setHardness(hardness);
        }
        block.setResistance(resistance);
    }
}