package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.items.ModItems;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelGustav;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderRpg extends TEISRBase {

    private static final ResourceLocation GUSTAV_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelgustav.png");
    private static final ResourceLocation KARL_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelgustavyellow.png");

    private final ModelGustav model = new ModelGustav();

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.enableCull();
        Minecraft.getMinecraft().getTextureManager().bindTexture(stack.getItem() == ModItems.gun_karl ? KARL_TEXTURE : GUSTAV_TEXTURE);

        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
                GL11.glTranslated(-1.5D, 0.0D, 0.5D);
            case FIRST_PERSON_RIGHT_HAND:
                GL11.glScaled(0.75D, 0.75D, 0.75D);
                GL11.glTranslated(1.0D, -0.5D, 0.1D);
                GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                GL11.glRotated(20.0D, 0.0D, 0.0D, 1.0D);
                if (type == TransformType.FIRST_PERSON_LEFT_HAND) {
                    GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(40.0D, 0.0D, 0.0D, 1.0D);
                    GL11.glRotated(-10.0D, 0.0D, 1.0D, 0.0D);
                }
                GL11.glTranslatef(0.0F, -0.1F, -0.4F);
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case HEAD:
            case FIXED:
            case GROUND:
                GL11.glTranslated(0.0D, -0.25D, 1.0D);
                GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
                GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            default:
                break;
        }
    }
}
