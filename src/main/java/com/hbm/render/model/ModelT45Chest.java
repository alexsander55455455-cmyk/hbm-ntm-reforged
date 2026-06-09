package com.hbm.render.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ModelT45Chest extends ModelBiped {

    ModelRenderer chest;
    ModelRenderer leftarm;
    ModelRenderer rightarm;

    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;
    ModelRenderer Shape16;
    ModelRenderer Shape17;
    ModelRenderer Shape18;
    ModelRenderer Shape19;

    public ModelT45Chest() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.chest = new ModelRenderer(this, 0, 0);
        this.leftarm = new ModelRenderer(this, 0, 0);
        this.rightarm = new ModelRenderer(this, 0, 0);

        this.Shape1 = new ModelRenderer(this, 0, 0);
        this.Shape1.addBox(0.0F, 0.0F, 0.0F, 8, 12, 4);
        this.Shape1.setRotationPoint(-4.0F, -0.03125F, -2.0F);
        this.Shape1.setTextureSize(128, 64);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape1);

        this.Shape2 = new ModelRenderer(this, 0, 16);
        this.Shape2.addBox(0.0F, 0.0F, 0.0F, 7, 5, 2);
        this.Shape2.setRotationPoint(-3.5F, 1.96875F, -3.5F);
        this.Shape2.setTextureSize(128, 64);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape2);

        this.Shape3 = new ModelRenderer(this, 0, 23);
        this.Shape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.Shape3.setRotationPoint(-2.5F, 6.96875F, -3.0F);
        this.Shape3.setTextureSize(128, 64);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape3);

        this.Shape4 = new ModelRenderer(this, 0, 25);
        this.Shape4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.Shape4.setRotationPoint(1.5F, 6.96875F, -3.0F);
        this.Shape4.setTextureSize(128, 64);
        this.Shape4.mirror = true;
        this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape4);

        this.Shape5 = new ModelRenderer(this, 0, 28);
        this.Shape5.addBox(0.0F, -2.0F, 0.0F, 7, 2, 2);
        this.Shape5.setRotationPoint(-3.5F, 1.96875F, -3.5F);
        this.Shape5.setTextureSize(128, 64);
        this.Shape5.mirror = true;
        this.setRotation(this.Shape5, -0.6108652F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape5);

        this.Shape6 = new ModelRenderer(this, 48, 0);
        this.Shape6.addBox(0.0F, 0.0F, 0.0F, 4, 12, 4);
        this.Shape6.setRotationPoint(-1.75F, -3.0F, -2.0F);
        this.Shape6.setTextureSize(128, 64);
        this.Shape6.mirror = true;
        this.setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.leftarm, this.Shape6);

        this.Shape7 = new ModelRenderer(this, 32, 0);
        this.Shape7.addBox(0.0F, 0.0F, 0.0F, 4, 12, 4);
        this.Shape7.setRotationPoint(-2.25F, -3.0F, -2.0F);
        this.Shape7.setTextureSize(128, 64);
        this.Shape7.mirror = true;
        this.setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.rightarm, this.Shape7);

        this.Shape8 = new ModelRenderer(this, 32, 16);
        this.Shape8.addBox(0.0F, 0.0F, 0.0F, 5, 6, 6);
        this.Shape8.setRotationPoint(-1.75F, 1.0F, -3.0F);
        this.Shape8.setTextureSize(128, 64);
        this.Shape8.mirror = true;
        this.setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.leftarm, this.Shape8);

        this.Shape9 = new ModelRenderer(this, 0, 34);
        this.Shape9.addBox(0.0F, 0.0F, 0.0F, 5, 6, 6);
        this.Shape9.setRotationPoint(-3.25F, 1.0F, -3.0F);
        this.Shape9.setTextureSize(128, 64);
        this.Shape9.mirror = true;
        this.setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.rightarm, this.Shape9);

        this.Shape10 = new ModelRenderer(this, 32, 30);
        this.Shape10.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
        this.Shape10.setRotationPoint(1.0F, 3.96875F, 2.0F);
        this.Shape10.setTextureSize(128, 64);
        this.Shape10.mirror = true;
        this.setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape10);

        this.Shape11 = new ModelRenderer(this, 42, 30);
        this.Shape11.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
        this.Shape11.setRotationPoint(-3.0F, 3.96875F, 2.0F);
        this.Shape11.setTextureSize(128, 64);
        this.Shape11.mirror = true;
        this.setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape11);

        this.Shape12 = new ModelRenderer(this, 26, 9);
        this.Shape12.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1);
        this.Shape12.setRotationPoint(1.5F, -2.03125F, 2.0F);
        this.Shape12.setTextureSize(128, 64);
        this.Shape12.mirror = true;
        this.setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape12);

        this.Shape13 = new ModelRenderer(this, 26, 0);
        this.Shape13.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1);
        this.Shape13.setRotationPoint(-2.5F, -2.03125F, 2.0F);
        this.Shape13.setTextureSize(128, 64);
        this.Shape13.mirror = true;
        this.setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape13);

        this.Shape14 = new ModelRenderer(this, 20, 18);
        this.Shape14.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
        this.Shape14.setRotationPoint(-1.0F, 0.96875F, 2.0F);
        this.Shape14.setTextureSize(128, 64);
        this.Shape14.mirror = true;
        this.setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.chest, this.Shape14);

        this.Shape15 = new ModelRenderer(this, 21, 23);
        this.Shape15.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 1);
        this.Shape15.setRotationPoint(0.0F, 1.96875F, 3.0F);
        this.Shape15.setTextureSize(128, 64);
        this.Shape15.mirror = true;
        this.setRotation(this.Shape15, 0.0F, 0.0F, 0.7853982F);
        this.convertToChild(this.chest, this.Shape15);

        this.Shape16 = new ModelRenderer(this, 0, 48);
        this.Shape16.addBox(0.0F, -1.0F, 0.0F, 3, 1, 4);
        this.Shape16.setRotationPoint(-2.25F, 9.0F, -2.0F);
        this.Shape16.setTextureSize(128, 64);
        this.Shape16.mirror = true;
        this.setRotation(this.Shape16, 0.0F, 0.0F, 0.5235988F);
        this.convertToChild(this.rightarm, this.Shape16);

        this.Shape17 = new ModelRenderer(this, 0, 55);
        this.Shape17.addBox(-3.0F, -1.0F, 0.0F, 3, 1, 4);
        this.Shape17.setRotationPoint(2.25F, 9.0F, -2.0F);
        this.Shape17.setTextureSize(128, 64);
        this.Shape17.mirror = true;
        this.setRotation(this.Shape17, 0.0F, 0.0F, -0.5235988F);
        this.convertToChild(this.leftarm, this.Shape17);

        this.Shape18 = new ModelRenderer(this, 90, 0);
        this.Shape18.addBox(0.0F, -3.0F, 0.0F, 5, 3, 6);
        this.Shape18.setRotationPoint(-1.75F, -3.0F, -3.0F);
        this.Shape18.setTextureSize(128, 64);
        this.Shape18.mirror = true;
        this.setRotation(this.Shape18, 0.0F, 0.0F, 0.2617994F);
        this.convertToChild(this.leftarm, this.Shape18);

        this.Shape19 = new ModelRenderer(this, 66, 0);
        this.Shape19.addBox(-5.0F, -3.0F, 0.0F, 5, 3, 6);
        this.Shape19.setRotationPoint(1.75F, -3.0F, -3.0F);
        this.Shape19.setTextureSize(128, 64);
        this.Shape19.mirror = true;
        this.setRotation(this.Shape19, 0.0F, 0.0F, -0.2617994F);
        this.convertToChild(this.rightarm, this.Shape19);
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

        this.chest.rotationPointX = this.bipedBody.rotationPointX;
        this.chest.rotationPointY = this.bipedBody.rotationPointY;
        this.chest.rotationPointZ = this.bipedBody.rotationPointZ;
        this.chest.rotateAngleX = this.bipedBody.rotateAngleX;
        this.chest.rotateAngleY = this.bipedBody.rotateAngleY;
        this.chest.rotateAngleZ = this.bipedBody.rotateAngleZ;

        this.leftarm.rotationPointX = this.bipedLeftArm.rotationPointX;
        this.leftarm.rotationPointY = this.bipedLeftArm.rotationPointY;
        this.leftarm.rotationPointZ = this.bipedLeftArm.rotationPointZ;
        this.leftarm.rotateAngleX = this.bipedLeftArm.rotateAngleX;
        this.leftarm.rotateAngleY = this.bipedLeftArm.rotateAngleY;
        this.leftarm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;

        this.rightarm.rotationPointX = this.bipedRightArm.rotationPointX;
        this.rightarm.rotationPointY = this.bipedRightArm.rotationPointY;
        this.rightarm.rotationPointZ = this.bipedRightArm.rotationPointZ;
        this.rightarm.rotateAngleX = this.bipedRightArm.rotateAngleX;
        this.rightarm.rotateAngleY = this.bipedRightArm.rotateAngleY;
        this.rightarm.rotateAngleZ = this.bipedRightArm.rotateAngleZ;

        if (entity instanceof EntityZombie) {
            EntityZombie zombie = (EntityZombie) entity;
            boolean armsRaised = zombie.isArmsRaised();
            this.leftarm.rotateAngleY = 0.13962634F;
            this.rightarm.rotateAngleY = -0.13962634F;
            if (armsRaised) {
                this.leftarm.rotateAngleX = -2.0943952F;
                this.rightarm.rotateAngleX = -2.0943952F;
            } else {
                this.leftarm.rotateAngleX = -1.3962634F;
                this.rightarm.rotateAngleX = -1.3962634F;
            }
        }

        if (this.isSneak) {
            this.chest.offsetY = 0.25F;
            this.rightarm.offsetY = 0.25F;
            this.leftarm.offsetY = 0.25F;
        } else {
            this.chest.offsetY = 0.0F;
            this.rightarm.offsetY = 0.0F;
            this.leftarm.offsetY = 0.0F;
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
        this.chest.render(scale);
        GL11.glPopMatrix();
        this.renderLeft(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.renderRight(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public void renderLeft(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GL11.glPushMatrix();
        GL11.glScalef(1.13F, 1.13F, 1.13F);
        if (this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
            GL11.glScalef(0.75F, 0.75F, 0.75F);
        }
        this.leftarm.render(scale);
        GL11.glPopMatrix();
    }

    public void renderRight(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GL11.glPushMatrix();
        GL11.glScalef(1.13F, 1.13F, 1.13F);
        if (this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
            GL11.glScalef(0.75F, 0.75F, 0.75F);
        }
        this.rightarm.render(scale);
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
