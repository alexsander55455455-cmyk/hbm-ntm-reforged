package com.hbm.render.item.weapon;

import com.hbm.Tags;
import com.hbm.items.ModItems;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.BakedModelTransforms;
import com.hbm.render.model.ModelUzi;
import com.hbm.render.model.ModelUziBarrel;
import com.hbm.render.model.ModelUziSilencer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRenderUzi extends TEISRBase {

    private static final ResourceLocation UZI_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modeluzi.png");
    private static final ResourceLocation SATURNITE_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modeluzisaturnite.png");
    private static final ResourceLocation BARREL_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modeluzibarrel.png");
    private static final ResourceLocation SILENCER_TEXTURE = new ResourceLocation(Tags.MODID, "textures/models/weapons/modeluzisilencer.png");

    private final ModelUzi uzi = new ModelUzi();
    private final ModelUziBarrel barrel = new ModelUziBarrel();
    private final ModelUziSilencer silencer = new ModelUziSilencer();

    @Override
    public ModelBinding createModelBinding(Item item) {
        return ModelBinding.inventoryWithGuiModel(item, BakedModelTransforms.defaultItemTransforms());
    }

    @Override
    public void renderByItem(ItemStack stack) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableCull();
        bindMainTexture(stack.getItem());

        try {
            switch (type) {
                case FIRST_PERSON_LEFT_HAND:
                case FIRST_PERSON_RIGHT_HAND:
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
                        GL11.glTranslated(-0.2D, 1.1D, 0.4D);
                        GL11.glRotated(0.0D, 0.0D, 1.0D, 0.0D);
                        GL11.glRotated(-25.0D, 0.0D, 0.0D, 1.0D);
                        GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                    } else {
                        GL11.glTranslated(1.8D, 1.1D, 0.8D);
                        GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                        GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
                        GL11.glRotated(25.0D, 0.0D, 0.0D, 1.0D);
                    }
                    renderParts(stack.getItem());
                    break;
                case THIRD_PERSON_LEFT_HAND:
                case THIRD_PERSON_RIGHT_HAND:
                case GROUND:
                case FIXED:
                case HEAD:
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    GL11.glTranslated(1.0D, 0.9D, 1.3D);
                    GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
                    GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                    renderParts(stack.getItem());
                    break;
                default:
                    break;
            }
        } finally {
            GlStateManager.enableCull();
        }
    }

    private void bindMainTexture(Item item) {
        if (item == ModItems.gun_uzi_saturnite || item == ModItems.gun_uzi_saturnite_silencer) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SATURNITE_TEXTURE);
        } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(UZI_TEXTURE);
        }
    }

    private void renderParts(Item item) {
        uzi.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        if (item == ModItems.gun_uzi_silencer || item == ModItems.gun_uzi_saturnite_silencer) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SILENCER_TEXTURE);
            silencer.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            return;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(BARREL_TEXTURE);
        barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
    }
}
