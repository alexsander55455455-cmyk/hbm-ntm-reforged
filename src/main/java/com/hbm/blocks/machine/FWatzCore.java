package com.hbm.blocks.machine;

import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.BlockContainerBakeableNormal;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.render.block.BlockBakeFrame;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FWatzCore extends BlockContainerBakeableNormal {

	public FWatzCore(Material materialIn, String s) {
		super(materialIn, s, BlockBakeFrame.column("fwatz_core", "fwatz_computer"));
		this.setHardness(5.0F).setResistance(10.0F).setLightLevel(15F).setCreativeTab(MainRegistry.machineTab);
	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileentity = world.getTileEntity(pos);
        if(tileentity instanceof TileEntityFWatzCore) {
            InventoryHelper.dropInventoryItems(world, pos, tileentity);
        }
        super.breakBlock(world, pos, state);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFWatzCore();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void bakeModel(ModelBakeEvent event) {
		try {
			IModel baseModel = ModelLoaderRegistry.getModel(blockFrame.getBaseModelLocation());
			ImmutableMap.Builder<String, String> textureMap = ImmutableMap.builder();
			blockFrame.putTextures(textureMap);
			IModel retexturedModel = baseModel.retexture(textureMap.build());
			IBakedModel bakedModel = retexturedModel.bake(
					ModelRotation.X0_Y0,
					DefaultVertexFormats.BLOCK,
					ModelLoader.defaultTextureGetter()
			);

			ModelResourceLocation worldLocation = new ModelResourceLocation(getRegistryName(), "normal");
			event.getModelRegistry().putObject(worldLocation, bakedModel);
			ModelResourceLocation inventoryLocation = new ModelResourceLocation(getRegistryName(), "inventory");
			event.getModelRegistry().putObject(inventoryLocation, bakedModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}