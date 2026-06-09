package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WasteEarth extends Block {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 6);

    public WasteEarth(Material materialIn, boolean tick, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        this.setCreativeTab(MainRegistry.controlTab);
        this.setTickRandomly(tick);
        this.setHarvestLevel("shovel", 0);

        ModBlocks.ALL_BLOCKS.add(this);
    }

    public WasteEarth(Material materialIn, SoundType type, boolean tick, String s) {
        this(materialIn, tick, s);
        setSoundType(type);
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public @NotNull Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        if (this == ModBlocks.frozen_grass) {
            return Items.SNOWBALL;
        }
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(@NotNull IBlockState state, int fortune, @NotNull Random random) {
        return 1;
    }

    @Override
    public void onEntityWalk(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (!(entity instanceof EntityLivingBase base)) return;
        if (this == ModBlocks.frozen_grass) {
            base.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2 * 60 * 20, 2));
        } else if (this == ModBlocks.waste_mycelium) {
            base.addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 29));
            base.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
        } else if (!ContaminationUtil.isRadImmune(base)) {
            if (this == ModBlocks.waste_earth) {
                base.addPotionEffect(new PotionEffect(HbmPotion.radiation, 15 * 20, 4));
            } else if (this == ModBlocks.waste_dirt) {
                base.addPotionEffect(new PotionEffect(HbmPotion.radiation, 15 * 20, 3));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@NotNull IBlockState stateIn, @NotNull World world, @NotNull BlockPos pos, @NotNull Random rand) {
        super.randomDisplayTick(stateIn, world, pos, rand);

        if (this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) {
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
        if (this == ModBlocks.burning_earth) {
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canEntitySpawn(@NotNull IBlockState state, @NotNull Entity entityIn) {
        return ContaminationUtil.isRadImmune(entityIn);
    }

    @Override
    public void updateTick(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (this == ModBlocks.waste_mycelium && GeneralConfig.enableMycelium) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        Block b0 = world.getBlockState(new BlockPos(x + i, y + j, z + k)).getBlock();
                        IBlockState b1 = world.getBlockState(new BlockPos(x + i, y + j + 1, z + k));
                        if (!b1.isOpaqueCube() && (b0 == Blocks.DIRT || b0 == Blocks.GRASS || b0 == Blocks.MYCELIUM || b0 == ModBlocks.waste_earth)) {
                            world.setBlockState(new BlockPos(x + i, y + j, z + k), ModBlocks.waste_mycelium.getDefaultState());
                        }
                    }
                }
            }
        }

        if (this == ModBlocks.waste_earth || this == ModBlocks.waste_dirt || this == ModBlocks.waste_mycelium) {
            if (GeneralConfig.enableAutoCleanup) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }

            Block above = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
            if (above instanceof BlockMushroom || above instanceof BlockHugeMushroom) {
                world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.mush.getDefaultState());
            }
        }
    }
}