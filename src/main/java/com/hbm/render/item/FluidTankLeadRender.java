package com.hbm.render.item;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.NTMRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class FluidTankLeadRender extends TileEntityItemStackRenderer {

	public static final FluidTankLeadRender INSTANCE = new FluidTankLeadRender();

	public TransformType type;
	public IBakedModel itemModel;

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		NTMRenderHelper.bindBlockTexture();

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0.5);
		if (itemModel != null) {
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		}
		GL11.glPopMatrix();

		final double halfPixel = 0.03125;
		final double pix = 0.0625;
		Fluid fluid = resolveFluid(stack);
		TextureAtlasSprite sprite = null;
		if (fluid != null) {
			sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
		}

		if (sprite != null) {
			NTMRenderHelper.setColor(fluid.getColor(new FluidStack(fluid, 1000)));
			GlStateManager.disableLighting();

			float maxU = sprite.getInterpolatedU(9);
			float minU = sprite.getInterpolatedU(7);
			float maxV = sprite.getInterpolatedV(10);
			float minV = sprite.getInterpolatedV(4);

			GL11.glTranslated(0, 0, 0.5 + halfPixel);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(7 * pix, 4 * pix, 0).tex(minU, minV).endVertex();
			buf.pos(9 * pix, 4 * pix, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * pix, 10 * pix, 0).tex(maxU, maxV).endVertex();
			buf.pos(7 * pix, 10 * pix, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * pix, 4 * pix, -pix).tex(maxU, minV).endVertex();
			buf.pos(7 * pix, 4 * pix, -pix).tex(minU, minV).endVertex();
			buf.pos(7 * pix, 10 * pix, -pix).tex(minU, maxV).endVertex();
			buf.pos(9 * pix, 10 * pix, -pix).tex(maxU, maxV).endVertex();

			tes.draw();
			GlStateManager.enableLighting();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}

	private static Fluid resolveFluid(ItemStack stack) {
		FluidType type = Fluids.fromID(stack.getMetadata());
		if (type == null || type.getID() <= 0) {
			return null;
		}
		return type.getFF();
	}
}