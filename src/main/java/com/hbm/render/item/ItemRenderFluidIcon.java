package com.hbm.render.item;

import com.hbm.Tags;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.render.NTMRenderHelper;
import com.hbm.render.model.BakedModelTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ItemRenderFluidIcon extends TEISRBase {

	@Override
	public ModelBinding createModelBinding(Item item) {
		return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms(), new ResourceLocation(Tags.MODID, "items/fluid_icon"));
	}

	@Override
	public boolean useRegistryPerspective(Item item) {
		return true;
	}

	private static final double HALF_A_PIXEL = 0.03125;
	private static final double PIX = 0.0625;

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

		Fluid fluid = ItemFluidIcon.getFluid(stack);
		TextureAtlasSprite sprite = null;
		if (fluid != null) {
			sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
		}

		if (sprite != null) {
			NTMRenderHelper.setColor(fluid.getColor(new FluidStack(fluid, 1000)));
			GlStateManager.disableLighting();

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

			drawRect(buf, sprite, 5, 2, 11, 9);
			drawRect(buf, sprite, 6, 1, 10, 2);
			drawRect(buf, sprite, 4, 3, 5, 7);
			drawRect(buf, sprite, 11, 3, 12, 7);
			drawRect(buf, sprite, 6, 9, 10, 12);
			drawRect(buf, sprite, 7, 12, 9, 15);

			tes.draw();
			GlStateManager.enableLighting();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}

	private void drawRect(BufferBuilder buf, TextureAtlasSprite texture, int x1, int y1, int x2, int y2) {
		float maxU = texture.getInterpolatedU(x2);
		float minU = texture.getInterpolatedU(x1);
		float maxV = texture.getInterpolatedV(y2);
		float minV = texture.getInterpolatedV(y1);

		buf.pos(x1 * PIX, y1 * PIX, 0).tex(minU, minV).endVertex();
		buf.pos(x2 * PIX, y1 * PIX, 0).tex(maxU, minV).endVertex();
		buf.pos(x2 * PIX, y2 * PIX, 0).tex(maxU, maxV).endVertex();
		buf.pos(x1 * PIX, y2 * PIX, 0).tex(minU, maxV).endVertex();

		buf.pos(x2 * PIX, y1 * PIX, -PIX).tex(maxU, minV).endVertex();
		buf.pos(x1 * PIX, y1 * PIX, -PIX).tex(minU, minV).endVertex();
		buf.pos(x1 * PIX, y2 * PIX, -PIX).tex(minU, maxV).endVertex();
		buf.pos(x2 * PIX, y2 * PIX, -PIX).tex(maxU, maxV).endVertex();
	}
}