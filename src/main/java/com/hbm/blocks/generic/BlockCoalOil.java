package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import com.hbm.items.tool.ItemToolAbility;

import java.util.Random;

public class BlockCoalOil extends BlockOre {

    public BlockCoalOil(String s) {
        super();
        this.setTranslationKey(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        for (EnumFacing dir : EnumFacing.VALUES) {
            IBlockState nS = world.getBlockState(pos.offset(dir));
            Block n = nS.getBlock();

            if (n == ModBlocks.ore_coal_oil_burning || n == ModBlocks.balefire || n == Blocks.FIRE || nS.getMaterial() == Material.LAVA) {
                world.scheduleUpdate(pos, this, world.rand.nextInt(20) + 2);
            }
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, net.minecraft.tileentity.TileEntity te, ItemStack tool) {
        if (player.getHeldItemMainhand().isEmpty())
            return;

        Item held = player.getHeldItemMainhand().getItem();
        if (!(held instanceof ItemTool || held instanceof ItemToolAbility))
            return;

        if (!((ItemTool) held).getToolMaterialName().equals(Item.ToolMaterial.WOOD.toString())) {
            if (world.rand.nextInt(10) == 0)
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        }
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        world.setBlockState(pos, ModBlocks.ore_coal_oil_burning.getDefaultState());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.COAL;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 2 + rand.nextInt(2);
    }
}