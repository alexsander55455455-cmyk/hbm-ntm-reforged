package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelOSIPR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderOSIPR extends TEISRBase {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelosipr.png");

	private final ModelOSIPR osipr = new ModelOSIPR();

	@Override
	public ModelBinding createModelBinding(Item item) {
		return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
	}

	@Override
	public void renderByItem(ItemStack stack) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		GlStateManager.enableCull();

		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0.0D, 0.0D, -0.2D);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			GL11.glTranslated(1.0D, 1.1D, 1.5D);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glRotated(10.0D, 0.0D, 1.0D, 0.0D);
				GL11.glRotated(-50.0D, 0.0D, 0.0D, 1.0D);
				GL11.glRotated(190.0D, 1.0D, 0.0D, 0.0D);
			} else {
				GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
				GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
				GL11.glRotated(50.0D, 0.0D, 0.0D, 1.0D);
			}
			osipr.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			if(type == TransformType.THIRD_PERSON_LEFT_HAND) {
				GL11.glTranslated(0.1D, 0.0D, 0.0D);
			}
			if(type == TransformType.GROUND) {
				GL11.glTranslated(0.05D, 0.1D, 0.0D);
			}
			GL11.glTranslated(0.4D, 0.6D, 0.7D);
			GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
			GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
			osipr.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case GUI:
			GL11.glTranslated(0.56D, 0.37D, 0.56D);
			GL11.glRotated(-45.0D, 0.0D, 1.0D, 0.0D);
			GL11.glRotated(25.0D, 1.0D, 0.0D, 0.0D);
			GL11.glScaled(0.72D, 0.72D, 0.72D);
			osipr.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
