package com.hbm.items.machine;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.compress.utils.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@SideOnly(Side.CLIENT)
public class ScrapAliasSprite extends TextureAtlasSprite {

    private final ResourceLocation sourceTexture;

    public ScrapAliasSprite(String atlasName, ResourceLocation sourceTexture) {
        super(atlasName);
        this.sourceTexture = sourceTexture;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        ResourceLocation file = new ResourceLocation(
                sourceTexture.getNamespace(),
                "textures/" + sourceTexture.getPath() + ".png"
        );

        IResource resource = null;
        try {
            resource = manager.getResource(file);
            PngSizeInfo sizeInfo = PngSizeInfo.makeFromResource(resource);
            boolean hasAnimation = resource.getMetadata("animation") != null;
            this.loadSprite(sizeInfo, hasAnimation);
            resource = manager.getResource(file);
            loadSpriteFrames(resource, manager);
            return false;
        } catch (RuntimeException | IOException e) {
            net.minecraftforge.fml.client.FMLClientHandler.instance().trackBrokenTexture(file, e.getMessage());
            return true;
        } finally {
            IOUtils.closeQuietly(resource);
        }
    }

    private void loadSpriteFrames(IResource resource, IResourceManager manager) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());
        AnimationMetadataSection animationMetadataSection = resource.getMetadata("animation");
        int mipmapLevels = Minecraft.getMinecraft().getTextureMapBlocks().getMipmapLevels() + 1;
        int[][] frameData = new int[mipmapLevels][];
        frameData[0] = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), frameData[0], 0, bufferedImage.getWidth());

        if (animationMetadataSection == null) {
            this.framesTextureData.add(frameData);
        } else {
            int frameCount = bufferedImage.getHeight() / this.width;
            if (animationMetadataSection.getFrameCount() > 0) {
                for (int frameIndex : animationMetadataSection.getFrameIndexSet()) {
                    allocateFrameTextureData(frameIndex);
                    this.framesTextureData.set(frameIndex, getFrameTextureData(frameData, this.width, this.width, frameIndex));
                }
                this.animationMetadata = animationMetadataSection;
            } else {
                List<AnimationFrame> frames = Lists.newArrayList();
                for (int i = 0; i < frameCount; ++i) {
                    this.framesTextureData.add(getFrameTextureData(frameData, this.width, this.width, i));
                    frames.add(new AnimationFrame(i, -1));
                }
                this.animationMetadata = new AnimationMetadataSection(frames, this.width, this.height, animationMetadataSection.getFrameTime(), animationMetadataSection.isInterpolate());
            }
        }
    }

    private static int[][] getFrameTextureData(int[][] data, int width, int height, int frame) {
        int[][] result = new int[data.length][];
        for (int i = 0; i < data.length; ++i) {
            int[] pixels = data[i];
            if (pixels != null) {
                result[i] = new int[(width >> i) * (height >> i)];
                System.arraycopy(pixels, frame * result[i].length, result[i], 0, result[i].length);
            }
        }
        return result;
    }

    private void allocateFrameTextureData(int index) {
        while (this.framesTextureData.size() <= index) {
            this.framesTextureData.add(null);
        }
    }
}