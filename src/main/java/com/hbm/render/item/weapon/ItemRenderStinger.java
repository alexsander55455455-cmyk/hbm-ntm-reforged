package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.items.ModItems;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelStinger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderStinger extends TEISRBase {

	private static final ResourceLocation STINGER_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelstinger.png");
	private static final ResourceLocation SKYSTINGER_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelskystinger.png");

	private final ModelStinger stinger = new ModelStinger();

	@Override
	public ModelBinding createModelBinding(Item item) {
		return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
	}

	@Override
	public void renderByItem(ItemStack stack) {
		GlStateManager.enableCull();
		Minecraft.getMinecraft().getTextureManager().bindTexture(stack.getItem() == ModItems.gun_skystinger ? SKYSTINGER_TEXTURE : STINGER_TEXTURE);

		switch(this.type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			GL11.glTranslated(0.5D, 0.5D, 0.5D);
			if(this.type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(1.2D, -0.1D, 0.8D);
				GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
				GL11.glRotated(45.0D, 0.0D, 0.0D, 1.0D);
			} else {
				GL11.glTranslated(-0.2D, -0.15D, 1.0D);
				GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
				GL11.glRotated(50.0D, 0.0D, 0.0D, 1.0D);
			}
			this.stinger.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(0.65D, 0.6D, 1.0D);
			GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
			GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
			this.stinger.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
