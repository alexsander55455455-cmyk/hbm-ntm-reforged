package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityCharger;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class MachineTransformer extends BlockContainer {

	public final long maxThroughput;
	public final boolean pointingUp;

	public MachineTransformer(Material mat, String s, long max, boolean pointingUp) {
		super(mat);
		this.maxThroughput = max / 20L;
		this.pointingUp = pointingUp;
		this.setTranslationKey(s);
		this.setRegistryName(s);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCharger();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}