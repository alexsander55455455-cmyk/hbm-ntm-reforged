package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderWeaponSauer extends TEISRBase {

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms(), new ResourceLocation(Tags.MODID, "items/gun_uboinik"));
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.sauer_tex);

        try {
            switch (type) {
                case FIRST_PERSON_LEFT_HAND:
                case FIRST_PERSON_RIGHT_HAND:
                    renderFirstPerson();
                    break;
                case FIXED:
                case THIRD_PERSON_LEFT_HAND:
                case THIRD_PERSON_RIGHT_HAND:
                case HEAD:
                case GROUND:
                    GL11.glTranslated(0.5D, 0.0D, 0.7D);
                    if (type == TransformType.FIXED) {
                        GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
                    }
                    GL11.glScaled(0.4D, 0.4D, 0.4D);
                    ResourceManager.sauer.renderPart("Lever");
                    break;
                case GUI:
                    GlStateManager.enableLighting();
                    GL11.glScaled(0.16D, 0.16D, 0.16D);
                    GL11.glTranslatef(3.6F, 2.1F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);
                    ResourceManager.sauer.renderPart("Lever");
                    break;
                default:
                    break;
            }

            ResourceManager.sauer.renderPart("Gun");
        } finally {
            GlStateManager.shadeModel(GL11.GL_FLAT);
            GlStateManager.popMatrix();
            GlStateManager.enableLighting();
        }
    }

    private void renderFirstPerson() {
        EnumHand hand = type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        double[] recoil = nonNull(HbmAnimations.getRelevantTransformation("SAUER_RECOIL", hand));
        double[] tilt = nonNull(HbmAnimations.getRelevantTransformation("SAUER_TILT", hand));
        double[] cock = nonNull(HbmAnimations.getRelevantTransformation("SAUER_COCK", hand));
        double[] eject = nonNull(HbmAnimations.getRelevantTransformation("SAUER_SHELL_EJECT", hand));

        if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
            GL11.glTranslated(0.8D, -1.0D, 0.0D);
            if (entity != null && entity.isSneaking()) {
                GL11.glTranslated(-0.5D, 0.1D, -0.53D);
                GL11.glRotated(7.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(4.0D, 1.0D, 0.0D, 0.0D);
            }
            GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(20.0D, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(-10.0D, 0.0D, 1.0D, 0.0D);
        } else {
            GL11.glTranslated(0.2D, -1.0D, 0.0D);
            GL11.glRotated(-75.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(22.0D, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(-10.0D, 0.0D, 1.0D, 0.0D);
        }

        GL11.glTranslated(0.0D, 0.0D, recoil[0]);

        if (entity != null && entity.isSneaking() && type == TransformType.FIRST_PERSON_RIGHT_HAND) {
            GL11.glRotatef(-3.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(3.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(-2.0F, 0.5F, 0.3F);
        }

        GL11.glTranslated(0.0D, -5.0D, 0.0D);
        GL11.glRotated(tilt[2] * -0.5D, 1.0D, 0.0D, 0.0D);
        GL11.glTranslated(0.0D, 5.0D, 0.0D);
        GL11.glRotated(tilt[0], 0.0D, 0.0D, 1.0D);

        GL11.glTranslated(0.0D, 0.0D, cock[0] * 2.0D);
        ResourceManager.sauer.renderPart("Lever");
        GL11.glTranslated(0.0D, 0.0D, -cock[0] * 2.0D);

        GL11.glTranslated(eject[2] * 10.0D, -eject[2], 0.0D);
        GL11.glRotated(eject[2] * 90.0D, -1.0D, 0.0D, 0.0D);
        ResourceManager.sauer.renderPart("Shell");
        GL11.glRotated(eject[2] * 90.0D, 1.0D, 0.0D, 0.0D);
        GL11.glTranslated(-eject[2] * 10.0D, eject[2], 0.0D);
    }

    private double[] nonNull(double[] value) {
        return value != null ? value : new double[3];
    }
}
