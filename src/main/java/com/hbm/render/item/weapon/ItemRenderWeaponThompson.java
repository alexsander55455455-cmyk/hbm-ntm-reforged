package com.hbm.render.item.weapon;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.GL11;

public class ItemRenderWeaponThompson extends TEISRBase {

    @Override
    public void renderByItem(ItemStack stack) {
        GL11.glTranslated(0.5D, 0.5D, 0.5D);
        GlStateManager.enableRescaleNormal();
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.thompson_tex);

        switch (type) {
            case FIRST_PERSON_RIGHT_HAND:
            case FIRST_PERSON_LEFT_HAND:
                if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
                    double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL",
                            type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    GL11.glTranslated(0.0D + recoil[1] * 0.20000000298023224D, -0.4D - recoil[1] * 0.10000000149011612D, 0.4D);
                    GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(23.0D, 0.0D, 0.0D, 1.0D);
                } else {
                    GL11.glTranslated(0.0D, -0.8D, 0.5D);
                    GL11.glRotated(-80.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(20.0D, 1.0D, 0.0D, 0.0D);
                }
                GL11.glScaled(0.25D, 0.25D, 0.25D);
                break;

            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case GROUND:
            case FIXED:
            case HEAD:
                GL11.glTranslated(0.0D, -1.35D, 0.05D);
                GL11.glScaled(0.3D, 0.3D, 0.3D);
                break;

            case GUI:
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glTranslated(-0.25D, -0.25D, 0.0D);
                GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(40.0D, 1.0D, 0.0D, 0.0D);
                GL11.glScaled(0.12D, 0.12D, 0.12D);
                break;

            default:
                break;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.thompson_tex);
        GlStateManager.enableCull();
        ResourceManager.thompson.renderAll();
        GlStateManager.disableCull();
        GlStateManager.enableLighting();
    }
}
