package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelLacunae;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderMinigun extends TEISRBase {

	private static final ResourceLocation MINIGUN_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modellacunae.png");
	private static final ResourceLocation AVENGER_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modellacunaeavenger.png");
	private static final ResourceLocation LACUNAE_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modellacunaereal.png");

	private final ModelLacunae lacunae = new ModelLacunae();

	@Override
	public ModelBinding createModelBinding(Item item) {
		return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
	}

	@Override
	public void renderByItem(ItemStack stack) {
		float rotation = ItemGunBase.readNBT(stack, "rot");

		if(stack.getItem() == ModItems.gun_minigun) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(MINIGUN_TEXTURE);
		} else if(stack.getItem() == ModItems.gun_avenger) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(AVENGER_TEXTURE);
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(LACUNAE_TEXTURE);
		}

		GlStateManager.enableCull();
		switch(this.type) {
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(-0.25D, 0.0D, 0.0D);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			GL11.glTranslated(1.3D, 0.8D, 1.3D);
			if(this.type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glRotated(-10.0D, 0.0D, 1.0D, 0.0D);
				GL11.glRotated(-40.0D, 0.0D, 0.0D, 1.0D);
				GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
			} else {
				GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
				GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
				GL11.glRotated(40.0D, 0.0D, 0.0D, 1.0D);
			}
			GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.5F, 0.3F, -0.2F);
			this.lacunae.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, rotation);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
			GL11.glTranslated(0.4D, 0.5D, -0.3D);
			GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
			GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
			GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(185.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.5F, 0.6F, 0.2F);
			this.lacunae.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, rotation);
			break;
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.5D, 0.5D, 0.5D);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			this.lacunae.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, rotation);
			break;
		default:
			break;
		}
	}
}
