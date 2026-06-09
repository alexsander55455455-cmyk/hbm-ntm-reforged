package com.hbm.blocks.fluid;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

public class ToxicBlock extends BlockFluidClassic implements IFluidFog {

	public final int color;

	public ToxicBlock(Fluid fluid, Material material, String s, int color) {
		super(fluid, material);
		this.color = color;
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		this.setQuantaPerBlock(4);
		this.displacements.put(this, false);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setInWeb();

		if (entityIn instanceof EntityLivingBase livingBase) {
			ContaminationUtil.contaminate(livingBase, HazardType.RADIATION, ContaminationType.CREATIVE, 50.0F);
		} else if (entityIn instanceof EntityFallingBlock) {
			entityIn.setDead();
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if (reactToBlocks(world, pos.east())) {
			world.setBlockState(pos.east(), getRandomSellafite(world));
		}
		if (reactToBlocks(world, pos.west())) {
			world.setBlockState(pos.west(), getRandomSellafite(world));
		}
		if (reactToBlocks(world, pos.up())) {
			world.setBlockState(pos, getRandomSellafite(world));
		}
		if (reactToBlocks(world, pos.down())) {
			world.setBlockState(pos.down(), getRandomSellafite(world));
		}
		if (reactToBlocks(world, pos.south())) {
			world.setBlockState(pos.south(), getRandomSellafite(world));
		}
		if (reactToBlocks(world, pos.north())) {
			world.setBlockState(pos.north(), getRandomSellafite(world));
		}

		if (!world.isRemote && rand.nextInt(15) == 0) {
			ChunkRadiationManager.proxy.incrementRad(world, pos, 300F, 3000F);
		}
	}

	private IBlockState getRandomSellafite(World world) {
		int n = world.rand.nextInt(100);
		if (n < 20) return ModBlocks.sellafield_4.getStateFromMeta(world.rand.nextInt(4));
		if (n < 60) return ModBlocks.sellafield_3.getStateFromMeta(world.rand.nextInt(4));
		return ModBlocks.sellafield_2.getStateFromMeta(world.rand.nextInt(4));
	}

	public boolean reactToBlocks(World world, BlockPos pos) {
		if (!world.isBlockLoaded(pos)) return false;
		if (world.getBlockState(pos).getMaterial() != Material.WATER) {
			IBlockState state = world.getBlockState(pos);
			if (state.getMaterial().isLiquid()) return true;
			if (state.getBlock() instanceof BlockStone) return true;
			return state.getBlock() == ModBlocks.sellafield_slaked
					|| state.getBlock() == ModBlocks.sellafield_0
					|| state.getBlock() == ModBlocks.sellafield_1;
		}
		return false;
	}

	@Override
	public int tickRate(World world) {
		return 15;
	}

	@Override
	public float getFogDensity() {
		return 2.0F;
	}

	@Override
	public int getFogColor() {
		return color;
	}
}