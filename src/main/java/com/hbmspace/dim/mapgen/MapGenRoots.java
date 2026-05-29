package com.hbmspace.dim.mapgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import org.jetbrains.annotations.NotNull;

/**
 * Underground root-like tunnel generator.
 * Ported from X5687 1.7.10 {@code com.hbm.dim.mapgen.MapGenRoots}.
 *
 * 1.12.2 adaptations:
 * - {@code Block[]} flat array → {@code ChunkPrimer} (x,y,z) access
 * - {@code MathHelper.floor_double} → {@code MathHelper.floor}
 * - {@code BiomeGenBase} → {@code Biome}
 * - {@code worldObj} → {@code world}
 * - {@code biome.topBlock} / {@code biome.fillerBlock} are now {@code IBlockState}
 * - Index math {@code (x*16+z)*256+y} replaced by direct primer access
 *
 * Behavior: carves tunnels through stone and replaces carved blocks with
 * {@code Blocks.LOG} (wood roots) above y=10, or {@code Blocks.OBSIDIAN}
 * below y=10. If the surface was broken, sets the block above the floor
 * to the biome's top block (grass).
 */
public class MapGenRoots extends MapGenBase {

	protected void addRoom(long seed, int chunkX, int chunkZ, ChunkPrimer primer,
			double x, double y, double z) {
		this.addTunnel(seed, chunkX, chunkZ, primer, x, y, z,
				1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkPrimer primer,
			double x, double y, double z, float width, float yaw, float pitch,
			int currentStep, int totalSteps, double yScale) {
		double d4 = (double) (chunkX * 16 + 8);
		double d5 = (double) (chunkZ * 16 + 8);
		float f3 = 0.0F;
		float f4 = 0.0F;
		Random random = new Random(seed);

		if (totalSteps <= 0) {
			int j1 = this.range * 16 - 16;
			totalSteps = j1 - random.nextInt(j1 / 4);
		}

		boolean isRoom = false;

		if (currentStep == -1) {
			currentStep = totalSteps / 2;
			isRoom = true;
		}

		int k1 = random.nextInt(totalSteps / 2) + totalSteps / 4;

		for (boolean flag = random.nextInt(6) == 0; currentStep < totalSteps; ++currentStep) {
			double d6 = 1.5D + (double) (MathHelper.sin((float) currentStep * (float) Math.PI / (float) totalSteps)
					* width * 1.0F);
			double d7 = d6 * yScale;
			float f5 = MathHelper.cos(pitch);
			float f6 = MathHelper.sin(pitch);
			x += (double) (MathHelper.cos(yaw) * f5);
			y += (double) f6;
			z += (double) (MathHelper.sin(yaw) * f5);

			if (flag) {
				pitch *= 0.92F;
			} else {
				pitch *= 0.7F;
			}

			pitch += f4 * 0.1F;
			yaw += f3 * 0.1F;
			f4 *= 0.9F;
			f3 *= 0.75F;
			f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!isRoom && currentStep == k1 && width > 1.0F && totalSteps > 0) {
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y,
						z, random.nextFloat() * 0.25F + 0.5F, yaw - ((float) Math.PI / 2F),
						pitch / 3.0F, currentStep, totalSteps, 1.0D);
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y,
						z, random.nextFloat() * 0.25F + 0.5F, yaw + ((float) Math.PI / 2F),
						pitch / 3.0F, currentStep, totalSteps, 1.0D);
				return;
			}

			if (isRoom || random.nextInt(4) != 0) {
				double d8 = x - d4;
				double d9 = z - d5;
				double d10 = (double) (totalSteps - currentStep);
				double d11 = (double) (width + 2.0F + 16.0F);

				if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
					return;
				}

				if (x >= d4 - 16.0D - d6 * 2.0D && z >= d5 - 16.0D - d6 * 2.0D
						&& x <= d4 + 16.0D + d6 * 2.0D && z <= d5 + 16.0D + d6 * 2.0D) {
					int minX = MathHelper.floor(x - d6) - chunkX * 16 - 1;
					int maxX = MathHelper.floor(x + d6) - chunkX * 16 + 1;
					int minY = MathHelper.floor(y - d7) - 1;
					int maxY = MathHelper.floor(y + d7) + 1;
					int minZ = MathHelper.floor(z - d6) - chunkZ * 16 - 1;
					int maxZ = MathHelper.floor(z + d6) - chunkZ * 16 + 1;

					if (minX < 0) minX = 0;
					if (maxX > 16) maxX = 16;
					if (minY < 1) minY = 1;
					if (maxY > 248) maxY = 248;
					if (minZ < 0) minZ = 0;
					if (maxZ > 16) maxZ = 16;

					// Check for ocean blocks (water) in the carve area
					boolean hasWater = false;
					for (int bx = minX; !hasWater && bx < maxX; ++bx) {
						for (int bz = minZ; !hasWater && bz < maxZ; ++bz) {
							for (int by = maxY + 1; !hasWater && by >= minY - 1; --by) {
								if (by >= 0 && by < 256) {
									if (isOceanBlock(primer, bx, by, bz, chunkX, chunkZ)) {
										hasWater = true;
									}

									if (by != minY - 1 && bx != minX && bx != maxX - 1 && bz != minZ && bz != maxZ - 1) {
										by = minY;
									}
								}
							}
						}
					}

					// Carve the tunnel
					for (int bx = minX; bx < maxX; ++bx) {
						double d13 = ((double) (bx + chunkX * 16) + 0.5D - x) / d6;

						for (int bz = minZ; bz < maxZ; ++bz) {
							double d14 = ((double) (bz + chunkZ * 16) + 0.5D - z) / d6;
							boolean foundTop = false;

							if (d13 * d13 + d14 * d14 < 1.0D) {
								for (int by = maxY - 1; by >= minY; --by) {
									double d12 = ((double) by + 0.5D - y) / d7;

									if (d12 > -0.7D && d13 * d13 + d12 * d12 + d14 * d14 < 1.0D) {
										if (isTopBlock(primer, bx, by, bz, chunkX, chunkZ)) {
											foundTop = true;
										}
										digBlock(primer, bx, by, bz, chunkX, chunkZ, foundTop);
									}
								}
							}
						}
					}

					if (isRoom) {
						break;
					}
				}
			}
		}
	}

	@Override
	protected void recursiveGenerate(@NotNull World worldIn, int chunkX, int chunkZ,
			int originalX, int originalZ, @NotNull ChunkPrimer primer) {
		int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);

		if (this.rand.nextInt(7) != 0) {
			i1 = 0;
		}

		for (int j1 = 0; j1 < i1; ++j1) {
			double d0 = (double) (chunkX * 16 + this.rand.nextInt(16));
			double d1 = (double) this.rand.nextInt(this.rand.nextInt(120) + 8);
			double d2 = (double) (chunkZ * 16 + this.rand.nextInt(16));
			int k1 = 1;

			if (this.rand.nextInt(4) == 0) {
				this.addRoom(this.rand.nextLong(), originalX, originalZ, primer, d0, d1, d2);
				k1 += this.rand.nextInt(4);
			}

			for (int l1 = 0; l1 < k1; ++l1) {
				float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 16.0F;
				float f2 = this.rand.nextFloat() * 0.5F + this.rand.nextFloat();

				if (this.rand.nextInt(10) == 0) {
					f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 2.5F + 1.0F;
				}

				this.addTunnel(this.rand.nextLong(), originalX, originalZ, primer, d0, d1, d2, f2, f, f1,
						0, 0, 2.25D);
			}
		}
	}

	protected boolean isOceanBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ) {
		IBlockState state = primer.getBlockState(x, y, z);
		return state.getBlock() == Blocks.FLOWING_WATER || state.getBlock() == Blocks.WATER;
	}

	// Determine if the block at the specified location is the top block for the
	// biome, we take into account
	// Vanilla bugs to make sure that we generate the map the same way vanilla does.
	private boolean isTopBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ) {
		Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, y, z + chunkZ * 16));
		IBlockState state = primer.getBlockState(x, y, z);
		return state == biome.topBlock;
	}

	/**
	 * Digs out the current block, default implementation removes stone, filler, and
	 * top block.
	 * Sets the block to obsidian if y is less then 10, and log (wood root) otherwise.
	 * If setting to log, it also checks to see if we've broken the surface and if
	 * so tries to make the floor the biome's top block.
	 *
	 * @param primer   ChunkPrimer data
	 * @param x        local X position (0-15)
	 * @param y        Y position
	 * @param z        local Z position (0-15)
	 * @param chunkX   Chunk X position
	 * @param chunkZ   Chunk Z position
	 * @param foundTop True if we've encountered the biome's top block. Ideally if
	 *                 we've broken the surface.
	 */
	protected void digBlock(ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, y, z + chunkZ * 16));
		IBlockState filler = biome.fillerBlock;
		IBlockState block = primer.getBlockState(x, y, z);

		if (block.getBlock() == Blocks.STONE) {
			if (y < 10) {
				primer.setBlockState(x, y, z, Blocks.OBSIDIAN.getDefaultState());
			} else {
				primer.setBlockState(x, y, z, Blocks.LOG.getDefaultState());

				if (foundTop && primer.getBlockState(x, y + 1, z) == filler) {
					primer.setBlockState(x, y + 1, z, Blocks.GRASS.getDefaultState());
				}
			}
		}
	}

}
