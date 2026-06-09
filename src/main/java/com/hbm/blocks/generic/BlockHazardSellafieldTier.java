package com.hbm.blocks.generic;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockHazardSellafieldTier extends BlockHazard {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);
    private static final int VARIANT_COUNT = 4;

    public BlockHazardSellafieldTier(Material mat, SoundType type, String s) {
        super(mat, type, s);
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{META});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta % VARIANT_COUNT);
    }
}