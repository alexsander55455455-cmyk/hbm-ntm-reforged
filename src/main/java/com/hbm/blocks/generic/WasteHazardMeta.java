package com.hbm.blocks.generic;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class WasteHazardMeta extends BlockHazard {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 6);

    public WasteHazardMeta(Material mat, String s) {
        super(mat, s);
    }

    public WasteHazardMeta(Material mat, SoundType type, String s) {
        super(mat, type, s);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }
}