package com.hbm.render.tileentity;

import com.hbm.interfaces.AutoRegister;
import com.hbm.render.NTMRenderHelper;
import com.hbm.tileentity.machine.TileEntityPlasmaStruct;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.jetbrains.annotations.NotNull;

import static com.hbm.render.util.SmallBlockPronter.renderSimpleBlockAt;
import static com.hbm.render.util.SmallBlockPronter.startDrawing;

@AutoRegister
public class RenderPlasmaMultiblock extends TileEntitySpecialRenderer<TileEntityPlasmaStruct> {

    public static TextureAtlasSprite heaterSide;
    public static TextureAtlasSprite heaterTop;

    @Override
    public void render(@NotNull TileEntityPlasmaStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(heaterSide == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y - 1, z + 0.5F);

        switch(te.getBlockMetadata()) {
            case 2: GlStateManager.rotate(270, 0F, 1F, 0F); break;
            case 4: GlStateManager.rotate(0, 0F, 1F, 0F); break;
            case 3: GlStateManager.rotate(90, 0F, 1F, 0F); break;
            case 5: GlStateManager.rotate(180, 0F, 1F, 0F); break;
        }

        GlStateManager.translate(-1.5F, 0, -0.5F);

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);

        startDrawing();
        NTMRenderHelper.bindBlockTexture();
        NTMRenderHelper.startDrawingTexturedQuads();

        for(int iy = 1; iy < 6; iy++) {
            for(int ix = 0; ix < 10; ix++) {
                for(int iz = -1; iz < 2; iz++) {
                    if(iy == 5 && ix > 3)
                        break;
                    renderSimpleBlockAt(heaterSide, ix, iy, iz);
                }
            }
        }

        for(int i = 10; i <= 11; i++)
            for(int j = 2; j <= 3; j++)
                renderSimpleBlockAt(heaterSide, i, j, 0);

        NTMRenderHelper.draw();

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}