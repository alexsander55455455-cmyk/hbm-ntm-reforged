package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelMP40;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderMP40 extends TEISRBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelmp40.png");

    private final ModelMP40 model = new ModelMP40();

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.enableCull();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);

        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
                    GL11.glTranslated(-0.2D, 1.1D, 0.8D);
                    GL11.glRotated(0.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(-25.0D, 0.0D, 0.0D, 1.0D);
                    GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                } else {
                    GL11.glTranslated(1.8D, 1.1D, 1.2D);
                    GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                    GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(25.0D, 0.0D, 0.0D, 1.0D);
                }
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case GROUND:
            case FIXED:
            case HEAD:
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslated(1.0D, 1.0D, 0.7D);
                GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            default:
                break;
        }
    }
}
