package com.hbm.render.item.weapon;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ItemRenderUziLegacy extends TEISRBase {

    private final boolean saturnite;
    private final boolean silenced;

    public ItemRenderUziLegacy(boolean saturnite, boolean silenced) {
        this.saturnite = saturnite;
        this.silenced = silenced;
    }

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.enableRescaleNormal();
        Minecraft.getMinecraft().getTextureManager().bindTexture(saturnite ? ResourceManager.uzi_saturnite_tex : ResourceManager.uzi_tex);

        switch (type) {
            case FIRST_PERSON_LEFT_HAND:
                GL11.glTranslated(0.35D, -0.15D, 0.35D);
                GL11.glRotated(95.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(20.0D, 0.0D, 0.0D, 1.0D);
                GL11.glScaled(0.25D, 0.25D, 0.25D);
                break;
            case FIRST_PERSON_RIGHT_HAND:
                GL11.glTranslated(-0.35D, -0.15D, 0.35D);
                GL11.glRotated(265.0D, 0.0D, 1.0D, 0.0D);
                GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
                GL11.glScaled(0.25D, 0.25D, 0.25D);
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case GROUND:
            case FIXED:
            case HEAD:
                GL11.glTranslated(0.0D, 0.15D, -0.65D);
                GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                GL11.glScaled(0.32D, 0.32D, 0.32D);
                break;
            case GUI:
                GlStateManager.enableLighting();
                GL11.glScaled(0.35D, 0.35D, 0.35D);
                GL11.glTranslatef(0.0F, 0.9F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                if (silenced) {
                    GL11.glTranslated(0.0D, 0.0D, -3.0D);
                }
                break;
            default:
                break;
        }

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        ResourceManager.uzi.renderPart("Gun");
        ResourceManager.uzi.renderPart("StockBack");
        ResourceManager.uzi.renderPart("StockFront");
        ResourceManager.uzi.renderPart("Slide");
        ResourceManager.uzi.renderPart("Magazine");
        if (silenced) {
            ResourceManager.uzi.renderPart("Silencer");
        }
        GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.enableLighting();
    }
}
