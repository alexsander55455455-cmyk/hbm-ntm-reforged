package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelEMPRay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderEMPRay extends TEISRBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modelempray.png");

    private final ModelEMPRay emp = new ModelEMPRay();

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
    }

    @Override
    public void renderByItem(ItemStack stack) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.enableCull();

        float spin = 0.0F;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            spin = player.getActiveItemStack().getItemUseAction() == EnumAction.BOW ? 0.15F : 0.0F;
            if (spin == 0.15F && player.getHeldItemMainhand().getItem() == stack.getItem() && player.getHeldItemOffhand().getItem() == stack.getItem()) {
                spin = 0.075F;
            }
        }

        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
                GL11.glTranslated(0.0D, 0.0D, -0.2D);
            case FIRST_PERSON_RIGHT_HAND:
                GL11.glScaled(0.25D, 0.25D, 0.25D);
                GL11.glTranslated(2.0D, 2.0D, 2.5D);
                if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
                    GL11.glRotated(0.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(-40.0D, 0.0D, 0.0D, 1.0D);
                    GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                    GL11.glRotated(-10.0D, 0.0D, 0.0D, 1.0D);
                } else {
                    GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                    GL11.glRotated(35.0D, 0.0D, 0.0D, 1.0D);
                }
                emp.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, spin);
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case HEAD:
            case FIXED:
            case GROUND:
                GL11.glScaled(0.5D, 0.5D, 0.5D);
                if (type == TransformType.THIRD_PERSON_LEFT_HAND) {
                    GL11.glTranslated(0.4D, 0.0D, 0.0D);
                }
                if (type == TransformType.GROUND) {
                    GL11.glTranslated(0.05D, 0.0D, 0.0D);
                }
                GL11.glTranslated(0.8D, 1.1D, 0.5D);
                GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                emp.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, spin);
                break;
            default:
                break;
        }
    }
}
