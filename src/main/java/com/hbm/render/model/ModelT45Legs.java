package com.hbm.render.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ModelT45Legs extends ModelBiped {

    ModelRenderer leftleg;
    ModelRenderer rightleg;

    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;

    public ModelT45Legs() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.leftleg = new ModelRenderer(this, 0, 0);
        this.rightleg = new ModelRenderer(this, 0, 0);

        this.Shape1 = new ModelRenderer(this, 0, 0);
        this.Shape1.addBox(0.0F, 0.0F, 0.0F, 4, 12, 4);
        this.Shape1.setRotationPoint(-2.0F, -0.5F, -2.0F);
        this.Shape1.setTextureSize(64, 32);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.rightleg, this.Shape1);

        this.Shape2 = new ModelRenderer(this, 16, 0);
        this.Shape2.addBox(0.0F, 0.0F, 0.0F, 4, 12, 4);
        this.Shape2.setRotationPoint(-2.0F, -0.5F, -2.0F);
        this.Shape2.setTextureSize(64, 32);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.leftleg, this.Shape2);

        this.Shape3 = new ModelRenderer(this, 16, 16);
        this.Shape3.addBox(0.0F, -6.0F, 0.0F, 5, 6, 4);
        this.Shape3.setRotationPoint(-3.0F, 9.5F, -2.0F);
        this.Shape3.setTextureSize(64, 32);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.1745329F, 0.0F, 0.0F);
        this.convertToChild(this.rightleg, this.Shape3);

        this.Shape4 = new ModelRenderer(this, 18, 16);
        this.Shape4.addBox(0.0F, -6.0F, 0.0F, 5, 6, 4);
        this.Shape4.setRotationPoint(-2.0F, 9.5F, -2.0F);
        this.Shape4.setTextureSize(64, 32);
        this.Shape4.mirror = true;
        this.setRotation(this.Shape4, 0.1745329F, 0.0F, 0.0F);
        this.convertToChild(this.leftleg, this.Shape4);

        this.Shape5 = new ModelRenderer(this, 34, 0);
        this.Shape5.addBox(0.0F, 0.0F, 0.0F, 5, 2, 4);
        this.Shape5.setRotationPoint(-3.0F, 0.5F, -3.0F);
        this.Shape5.setTextureSize(64, 32);
        this.Shape5.mirror = true;
        this.setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.rightleg, this.Shape5);

        this.Shape6 = new ModelRenderer(this, 34, 8);
        this.Shape6.addBox(0.0F, 0.0F, 0.0F, 5, 2, 4);
        this.Shape6.setRotationPoint(-2.0F, 0.5F, -3.0F);
        this.Shape6.setTextureSize(64, 32);
        this.Shape6.mirror = true;
        this.setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.leftleg, this.Shape6);
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

        this.leftleg.rotationPointX = this.bipedLeftLeg.rotationPointX;
        this.leftleg.rotationPointY = this.bipedLeftLeg.rotationPointY - 1.5F;
        this.leftleg.rotationPointZ = this.bipedLeftLeg.rotationPointZ;
        this.leftleg.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
        this.leftleg.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
        this.leftleg.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;

        this.rightleg.rotationPointX = this.bipedRightLeg.rotationPointX;
        this.rightleg.rotationPointY = this.bipedRightLeg.rotationPointY - 1.5F;
        this.rightleg.rotationPointZ = this.bipedRightLeg.rotationPointZ;
        this.rightleg.rotateAngleX = this.bipedRightLeg.rotateAngleX;
        this.rightleg.rotateAngleY = this.bipedRightLeg.rotateAngleY;
        this.rightleg.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;

        if (this.isSneak) {
            this.rightleg.offsetZ = 0.25F;
            this.leftleg.offsetZ = 0.25F;
            this.rightleg.rotationPointY = 11.0F;
            this.leftleg.rotationPointY = 11.0F;
            this.rightleg.rotationPointZ = -0.0625F;
            this.leftleg.rotationPointZ = -0.0625F;
        } else {
            this.rightleg.offsetZ = 0.0F;
            this.leftleg.offsetZ = 0.0F;
        }
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GL11.glPushMatrix();
        GL11.glScalef(1.13F, 1.13F, 1.13F);
        if (this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
            GL11.glScalef(0.75F, 0.75F, 0.75F);
        }
        this.leftleg.render(scale);
        this.rightleg.render(scale);
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
