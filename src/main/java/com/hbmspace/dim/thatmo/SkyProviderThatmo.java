package com.hbmspace.dim.thatmo;

import com.hbmspace.dim.SkyProviderCelestial;
import com.hbmspace.dim.WorldProviderCelestial;
import com.hbmspace.dim.WorldProviderCelestial.Meteor;
import com.hbmspace.dim.WorldProviderCelestial.MeteorType;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkyProviderThatmo extends SkyProviderCelestial {

    private static final ResourceLocation texture = new ResourceLocation("hbm:textures/particle/shockwave.png");
    private static final ResourceLocation flash = new ResourceLocation("hbm:textures/misc/space/flare.png");
    private static final ResourceLocation flare = new ResourceLocation("hbm:textures/particle/flare.png");
    private static final ResourceLocation particleBase = new ResourceLocation("hbm:textures/particle/particle_base.png");

    @Override
    protected void renderSpecialEffects(float partialTicks, WorldClient world, Minecraft mc) {
        WorldProviderThatmo thatmoProvider = null;
        if (world.provider instanceof WorldProviderThatmo) {
            thatmoProvider = (WorldProviderThatmo) world.provider;
        } else {
            return;
        }

        float flashDistance = thatmoProvider.getFlashDistance();
        float alpha = flashDistance <= 0 ? 0.0F : 1.0F - Math.min(1.0F, flashDistance / 100);

        GlStateManager.pushMatrix();
        float var14 = flashDistance * 2 + partialTicks;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.rotate(180.0F, 0.0F, 5.0F, 0.0F);
        GlStateManager.rotate(90.0F, -12.0F, 7.3F, -4.0F);

        mc.getTextureManager().bindTexture(texture);

        GlStateManager.color(1, 1, 1, alpha);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-var14, 100.0D, -var14).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos(var14, 100.0D, -var14).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(var14, 100.0D, var14).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(-var14, 100.0D, var14).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.rotate(180.0F, 0.0F, 5.0F, 0.0F);
        GlStateManager.rotate(90.0F, -12.0F, 7.3F, -4.0F);
        var14 = var14 * 0.5F;
        mc.getTextureManager().bindTexture(flash);

        GlStateManager.color(1, 1, 1, alpha);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-var14, 100.0D, -var14).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos(var14, 100.0D, -var14).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(var14, 100.0D, var14).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(-var14, 100.0D, var14).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();

        // Note: the battlefield shield and beam effects are skipped/deferred because they rely on state missing from 1.12.2 WorldProviderThatmo

        for (int i = 0; i < WorldProviderCelestial.meteors.size(); i++) {
            Meteor meteor = WorldProviderCelestial.meteors.get(i);
            
            GlStateManager.pushMatrix();
            GlStateManager.disableFog();
            GlStateManager.enableTexture2D();
            
            double dx = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks;
            double dy = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks;
            double dz = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks;
            
            Vec3d vec = new Vec3d(meteor.posX - dx, meteor.posY - dy, meteor.posZ - dz);
            Vec3d vec2 = new Vec3d(meteor.posX - dx, meteor.posY - dy, meteor.posZ - dz);
            
            double l = Math.min(mc.gameSettings.renderDistanceChunks * 16, vec.length());
            vec = vec.normalize();
            Vec3d vecd = new Vec3d(vec.x * l, vec.y * l, vec.z * l);
            
            GlStateManager.translate(vecd.x, vecd.y, vecd.z);
            
            if (meteor.type == MeteorType.SMOKE) {
                double descent = 2017d - meteor.posY;
                double quadratic = (-1 * Math.pow(descent, 2) + (1517 * descent)) / 82;
                float scalar = (float) (quadratic / vec2.length());
                
                GlStateManager.color(1, 0, 0, 1);
                GlStateManager.scale(scalar, scalar, scalar);
                renderSmoke(particleBase, meteor.age, mc);
            } else {
                double descent = 2017d - meteor.posY;
                double quadratic = (-1 * Math.pow(descent, 2) + (1517 * descent)) / 41;
                float scalar = (float) (quadratic / vec2.length());
                
                GlStateManager.scale(scalar, scalar, scalar);
                renderGlow(flare, meteor.posX, meteor.posY, meteor.posZ, partialTicks, mc, vec2);
            }
            
            GlStateManager.disableTexture2D();
            GlStateManager.enableFog();
            GlStateManager.popMatrix();
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(16.5, 28.5, 100);
        GlStateManager.scale(10, 10, 10);
        GlStateManager.rotate(-63.5F, 0.0F, 0.0F, 1.0F);
        
        BeamPronter.prontBeam(new Vec3d(0, flashDistance * 0.5, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x202060, 0x202060, 0, 1, 0F, 6, (float)0.2 * 0.2F);
        BeamPronter.prontBeam(new Vec3d(0, flashDistance * 0.5, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x202060, 0x202060, 0, 1, 0F, 6, (float)0.2 * 0.6F);
        BeamPronter.prontBeam(new Vec3d(0, flashDistance * 0.5, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, (int)(world.getTotalWorldTime() / 5) % 1000, 25, 0.2F, 6, (float)0.2 * 0.1F);
        
        GlStateManager.rotate(27F, 0.0F, 80.0F, 0.0F);
        GlStateManager.color(1, 1, 1, alpha);
        GlStateManager.popMatrix();

        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
    }

    private void renderSmoke(ResourceLocation loc1, long age, Minecraft mc) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        float dark = 1f - Math.min(((float)(age) / (float)(100f * 0.35F)), 1f);
        
        RenderManager rm = mc.getRenderManager();
        GlStateManager.rotate(180.0F - rm.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(rm.options.thirdPersonView == 2 ? -1 : 1) * -rm.playerViewX, 1.0F, 0.0F, 0.0F);
        
        GlStateManager.color((float)(0.6*dark+0.0), (float)(0.6*dark+0.0), (float)(1*dark+0.0), 1.0F);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        mc.getTextureManager().bindTexture(loc1);
        
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buffer.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(1, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(f4 - f5, 0.0F - f6, 0.0D).tex(0, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(f4 - f5, f4 - f6, 0.0D).tex(0, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(0.0F - f5, f4 - f6, 0.0D).tex(1, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
        tess.draw();
        
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void renderGlow(ResourceLocation loc1, double x, double y, double z, float partialTicks, Minecraft mc, Vec3d vec2) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        
        RenderManager rm = mc.getRenderManager();
        GlStateManager.rotate(180.0F - rm.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(rm.options.thirdPersonView == 2 ? -1 : 1) * -rm.playerViewX, 1.0F, 0.0F, 0.0F);
        
        double near = 0.51d * (Math.min(40000d, Math.max(0d, y - 35000d)) / 40000d);
        double entry = near * (1d - mc.player.world.rainingStrength) + (1d - (Math.min(200d, Math.max(0d, x - 2017d)) / 200f));
        
        GlStateManager.color((float)entry, (float)entry, (float)entry, (float)entry);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        mc.getTextureManager().bindTexture(loc1);
        
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buffer.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(1, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(f4 - f5, 0.0F - f6, 0.0D).tex(0, 0).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(f4 - f5, f4 - f6, 0.0D).tex(0, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(0.0F - f5, f4 - f6, 0.0D).tex(1, 1).normal(0.0F, 1.0F, 0.0F).endVertex();
        tess.draw();
        
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
