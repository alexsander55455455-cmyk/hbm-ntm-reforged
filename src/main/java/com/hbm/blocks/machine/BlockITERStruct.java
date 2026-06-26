package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityITERStruct;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockITERStruct extends BlockStruct {

	public BlockITERStruct(Material materialIn, String s, Class<? extends TileEntity> tileEntityClass) {
		super(materialIn, s, tileEntityClass);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityITERStruct();
	}
}