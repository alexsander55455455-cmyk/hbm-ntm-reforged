package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.AutoRegister;
import com.hbm.render.NTMRenderHelper;
import com.hbm.render.util.IconUtil;
import com.hbm.tileentity.machine.TileEntityITERStruct;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.jetbrains.annotations.NotNull;

import static com.hbm.render.util.SmallBlockPronter.renderSimpleBlockAt;

@AutoRegister
public class RenderITERMultiblock extends TileEntitySpecialRenderer<TileEntityITERStruct> {

    public static TextureAtlasSprite magnetSprite;
    public static TextureAtlasSprite solenoidSprite;
    public static TextureAtlasSprite motorSprite;

    @Override
    public boolean isGlobalRenderer(TileEntityITERStruct te) {
        return true;
    }

    @Override
    public void render(@NotNull TileEntityITERStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (magnetSprite == null || solenoidSprite == null || motorSprite == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x - 1, (float) y - 1, (float) z);

        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(false);

        TextureAtlasSprite glass = IconUtil.getTextureFromBlock(ModBlocks.reinforced_glass);
        TextureAtlasSprite active = magnetSprite;

        NTMRenderHelper.bindBlockTexture();
        NTMRenderHelper.startDrawingTexturedQuads();

        int[][][] layout = TileEntityITERStruct.layout;

        for (int iy = -2; iy <= 2; iy++) {
            int iny = 2 - Math.abs(iy);

            for (int ix = 0; ix < layout[0].length; ix++) {
                for (int iz = 0; iz < layout[0][0].length; iz++) {
                    int block = layout[iny][ix][iz];

                    switch (block) {
                        case 0 -> { continue; }
                        case 1 -> active = magnetSprite;
                        case 2 -> active = solenoidSprite;
                        case 3 -> active = motorSprite;
                        case 4 -> active = glass;
                    }

                    renderSimpleBlockAt(active, ix - 6F, iy + 3, iz - 7F);
                }
            }
        }

        NTMRenderHelper.draw();

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}