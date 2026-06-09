package com.hbm.render.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ModelT45Helmet extends ModelBiped {

    ModelRenderer helmet;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;

    public ModelT45Helmet() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.helmet = new ModelRenderer(this, 0, 0);

        this.Shape1 = new ModelRenderer(this, 0, 0);
        this.Shape1.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8);
        this.Shape1.setRotationPoint(-4.0F, -7.96875F, -4.0F);
        this.Shape1.setTextureSize(64, 32);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape1);

        this.Shape2 = new ModelRenderer(this, 32, 0);
        this.Shape2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
        this.Shape2.setRotationPoint(1.0F, -5.96875F, -5.0F);
        this.Shape2.setTextureSize(64, 32);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape2);

        this.Shape3 = new ModelRenderer(this, 40, 6);
        this.Shape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4);
        this.Shape3.setRotationPoint(-5.0F, -6.96875F, -5.466667F);
        this.Shape3.setTextureSize(64, 32);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape3);

        this.Shape4 = new ModelRenderer(this, 40, 0);
        this.Shape4.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2);
        this.Shape4.setRotationPoint(-2.0F, -2.96875F, -4.0F);
        this.Shape4.setTextureSize(64, 32);
        this.Shape4.mirror = true;
        this.setRotation(this.Shape4, -0.7853982F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape4);

        this.Shape5 = new ModelRenderer(this, 54, 0);
        this.Shape5.addBox(0.0F, 2.0F, 0.0F, 2, 1, 2);
        this.Shape5.setRotationPoint(-1.0F, -2.96875F, -4.0F);
        this.Shape5.setTextureSize(64, 32);
        this.Shape5.mirror = true;
        this.setRotation(this.Shape5, -0.7853982F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape5);

        this.Shape6 = new ModelRenderer(this, 0, 16);
        this.Shape6.addBox(0.0F, 0.0F, 0.0F, 10, 1, 9);
        this.Shape6.setRotationPoint(-5.0F, -1.96875F, -4.5F);
        this.Shape6.setTextureSize(64, 32);
        this.Shape6.mirror = true;
        this.setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape6);

        this.Shape7 = new ModelRenderer(this, 32, 7);
        this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.Shape7.setRotationPoint(-1.5F, -2.96875F, -4.5F);
        this.Shape7.setTextureSize(64, 32);
        this.Shape7.mirror = true;
        this.setRotation(this.Shape7, -0.7853982F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape7);

        this.Shape8 = new ModelRenderer(this, 32, 5);
        this.Shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.Shape8.setRotationPoint(0.5F, -2.96875F, -4.5F);
        this.Shape8.setTextureSize(64, 32);
        this.Shape8.mirror = true;
        this.setRotation(this.Shape8, -0.7853982F, 0.0F, 0.0F);
        this.convertToChild(this.helmet, this.Shape8);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.isSneak = ((EntityPlayer) entity).isSneaking();
        }
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.helmet.rotationPointX = this.bipedHead.rotationPointX;
        this.helmet.rotationPointY = this.bipedHead.rotationPointY;
        this.helmet.rotationPointZ = this.bipedHead.rotationPointZ;
        this.helmet.rotateAngleY = this.bipedHead.rotateAngleY;
        this.helmet.rotateAngleX = this.bipedHead.rotateAngleX;
        this.helmet.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isSneak) {
            this.helmet.rotationPointY = 3.7F;
        }
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GL11.glPushMatrix();
        GL11.glScalef(1.13F, 1.13F, 1.13F);
        GL11.glScalef(1.0625F, 1.0625F, 1.0625F);
        if (this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
        }
        this.helmet.render(scale);
        GL11.glPopMatrix();
    }

    protected void convertToChild(ModelRenderer parent, ModelRenderer child) {
        child.rotationPointX -= parent.rotationPointX;
        child.rotationPointY -= parent.rotationPointY;
        child.rotationPointZ -= parent.rotationPointZ;
        child.rotateAngleX -= parent.rotateAngleX;
        child.rotateAngleY -= parent.rotateAngleY;
        child.rotateAngleZ -= parent.rotateAngleZ;
        parent.addChild(child);
    }
}
