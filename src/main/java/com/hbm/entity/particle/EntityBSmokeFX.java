package com.hbm.entity.particle;

import com.hbm.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityBSmokeFX extends Particle {

    private static final Item[] TEXTURE_ITEMS = new Item[]{
            ModItems.b_smoke1, ModItems.b_smoke2, ModItems.b_smoke3, ModItems.b_smoke4,
            ModItems.b_smoke5, ModItems.b_smoke6, ModItems.b_smoke7, ModItems.b_smoke8
    };

    private int lastStage = -1;

    public EntityBSmokeFX(World world) {
        this(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public EntityBSmokeFX(World world, double x, double y, double z, double mx, double my, double mz) {
        this(world, x, y, z, mx, my, mz, 1.0F);
    }

    public EntityBSmokeFX(World world, double x, double y, double z, double mx, double my, double mz, float scale) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);

        this.motionX *= 0.1D;
        this.motionY *= 0.1D;
        this.motionZ *= 0.1D;
        this.motionX += mx;
        this.motionY += my;
        this.motionZ += mz;

        float color = (float) (Math.random() * 0.3D);
        this.particleRed = this.particleGreen = this.particleBlue = color;
        this.setSize(0.2F, 0.2F);
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F * 0.75F * scale;

        this.particleMaxAge = this.rand.nextInt(21) + 65;

        this.canCollide = false;

        updateSprite();
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.particleAge++;
        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
            return;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        this.move(this.motionX, this.motionY, this.motionZ);

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }

        updateSprite();
    }

    private void updateSprite() {
        if (this.particleMaxAge <= 0) return;
        int stage = this.particleAge * 8 / this.particleMaxAge;
        if (stage < 0) stage = 0;
        if (stage > 7) stage = 7;

        if (stage != this.lastStage) {
            Item item = TEXTURE_ITEMS[stage];
            TextureAtlasSprite tas = Minecraft.getMinecraft()
                    .getRenderItem()
                    .getItemModelWithOverrides(new ItemStack(item, 1, 0), null, null)
                    .getParticleTexture();
            this.setParticleTexture(tas);
            this.lastStage = stage;
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}
