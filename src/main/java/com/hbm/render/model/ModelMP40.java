/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.GL11
 */
package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelMP40
extends ModelBase {
    ModelRenderer Body;
    ModelRenderer Barrel;
    ModelRenderer Scope;
    ModelRenderer ClipPivot;
    ModelRenderer ClipPivotBack;
    ModelRenderer Clip;
    ModelRenderer BarrelBottom;
    ModelRenderer BodyStock;
    ModelRenderer BodyStockBottom;
    ModelRenderer BodyBack;
    ModelRenderer Handle;
    ModelRenderer TriggerFrame;
    ModelRenderer Trigger;
    ModelRenderer Bar;
    ModelRenderer BarFront;

    public ModelMP40() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Body = new ModelRenderer((ModelBase)this, 0, 0);
        this.Body.addBox(0.0f, 0.0f, 0.0f, 40, 4, 4);
        this.Body.setRotationPoint(-10.0f, 0.0f, -2.0f);
        this.Body.setTextureSize(128, 64);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
        this.Barrel = new ModelRenderer((ModelBase)this, 88, 0);
        this.Barrel.addBox(0.0f, 0.0f, 0.0f, 17, 2, 2);
        this.Barrel.setRotationPoint(-27.0f, 1.0f, -1.0f);
        this.Barrel.setTextureSize(128, 64);
        this.Barrel.mirror = true;
        this.setRotation(this.Barrel, 0.0f, 0.0f, 0.0f);
        this.Scope = new ModelRenderer((ModelBase)this, 88, 4);
        this.Scope.addBox(0.0f, 0.0f, 0.0f, 2, 3, 2);
        this.Scope.setRotationPoint(-25.0f, -2.0f, -1.0f);
        this.Scope.setTextureSize(128, 64);
        this.Scope.mirror = true;
        this.setRotation(this.Scope, 0.0f, 0.0f, 0.0f);
        this.ClipPivot = new ModelRenderer((ModelBase)this, 0, 17);
        this.ClipPivot.addBox(0.0f, 0.0f, 0.0f, 4, 5, 3);
        this.ClipPivot.setRotationPoint(-3.0f, 4.0f, -1.5f);
        this.ClipPivot.setTextureSize(128, 64);
        this.ClipPivot.mirror = true;
        this.setRotation(this.ClipPivot, 0.0f, 0.0f, 0.0f);
        this.ClipPivotBack = new ModelRenderer((ModelBase)this, 14, 17);
        this.ClipPivotBack.addBox(0.0f, 0.0f, 0.0f, 3, 3, 3);
        this.ClipPivotBack.setRotationPoint(1.0f, 4.0f, -1.5f);
        this.ClipPivotBack.setTextureSize(128, 64);
        this.ClipPivotBack.mirror = true;
        this.setRotation(this.ClipPivotBack, 0.0f, 0.0f, 0.0f);
        this.Clip = new ModelRenderer((ModelBase)this, 0, 25);
        this.Clip.addBox(0.0f, 0.0f, 0.0f, 3, 18, 2);
        this.Clip.setRotationPoint(-2.5f, 9.0f, -1.0f);
        this.Clip.setTextureSize(128, 64);
        this.Clip.mirror = true;
        this.setRotation(this.Clip, 0.0f, 0.0f, 0.0f);
        this.BarrelBottom = new ModelRenderer((ModelBase)this, 96, 4);
        this.BarrelBottom.addBox(0.0f, 0.0f, 0.0f, 14, 1, 1);
        this.BarrelBottom.setRotationPoint(-24.0f, 2.5f, -0.5f);
        this.BarrelBottom.setTextureSize(128, 64);
        this.BarrelBottom.mirror = true;
        this.setRotation(this.BarrelBottom, 0.0f, 0.0f, 0.0f);
        this.BodyStock = new ModelRenderer((ModelBase)this, 0, 8);
        this.BodyStock.addBox(0.0f, 0.0f, 0.0f, 26, 4, 5);
        this.BodyStock.setRotationPoint(4.0f, 3.0f, -2.5f);
        this.BodyStock.setTextureSize(128, 64);
        this.BodyStock.mirror = true;
        this.setRotation(this.BodyStock, 0.0f, 0.0f, 0.0f);
        this.BodyStockBottom = new ModelRenderer((ModelBase)this, 62, 11);
        this.BodyStockBottom.addBox(0.0f, 0.0f, 0.0f, 26, 3, 3);
        this.BodyStockBottom.setRotationPoint(4.0f, 7.0f, -1.5f);
        this.BodyStockBottom.setTextureSize(128, 64);
        this.BodyStockBottom.mirror = true;
        this.setRotation(this.BodyStockBottom, 0.0f, 0.0f, 0.0f);
        this.BodyBack = new ModelRenderer((ModelBase)this, 10, 25);
        this.BodyBack.addBox(0.0f, 0.0f, 0.0f, 7, 7, 3);
        this.BodyBack.setRotationPoint(30.0f, 0.0f, -1.5f);
        this.BodyBack.setTextureSize(128, 64);
        this.BodyBack.mirror = true;
        this.setRotation(this.BodyBack, 0.0f, 0.0f, 0.7853982f);
        this.Handle = new ModelRenderer((ModelBase)this, 30, 17);
        this.Handle.addBox(0.0f, 0.0f, 0.0f, 4, 10, 3);
        this.Handle.setRotationPoint(27.0f, 10.0f, -1.5f);
        this.Handle.setTextureSize(128, 64);
        this.Handle.mirror = true;
        this.setRotation(this.Handle, 0.0f, 0.0f, -0.4363323f);
        this.TriggerFrame = new ModelRenderer((ModelBase)this, 44, 17);
        this.TriggerFrame.addBox(0.0f, 0.0f, 0.0f, 6, 4, 2);
        this.TriggerFrame.setRotationPoint(23.0f, 10.0f, -1.0f);
        this.TriggerFrame.setTextureSize(128, 64);
        this.TriggerFrame.mirror = true;
        this.setRotation(this.TriggerFrame, 0.0f, 0.0f, 0.0f);
        this.Trigger = new ModelRenderer((ModelBase)this, 26, 17);
        this.Trigger.addBox(-1.0f, 0.0f, 0.0f, 1, 3, 1);
        this.Trigger.setRotationPoint(27.0f, 10.0f, -0.5f);
        this.Trigger.setTextureSize(128, 64);
        this.Trigger.mirror = true;
        this.setRotation(this.Trigger, 0.0f, 0.0f, 0.4363323f);
        this.Bar = new ModelRenderer((ModelBase)this, 60, 17);
        this.Bar.addBox(0.0f, 0.0f, 0.0f, 23, 1, 1);
        this.Bar.setRotationPoint(7.0f, 7.5f, -3.0f);
        this.Bar.setTextureSize(128, 64);
        this.Bar.mirror = true;
        this.setRotation(this.Bar, 0.0f, 0.0f, 0.0f);
        this.BarFront = new ModelRenderer((ModelBase)this, 0, 45);
        this.BarFront.addBox(-2.5f, -0.5f, 0.0f, 5, 1, 1);
        this.BarFront.setRotationPoint(7.0f, 8.0f, -3.0f);
        this.BarFront.setTextureSize(128, 64);
        this.BarFront.mirror = true;
        this.setRotation(this.BarFront, 0.0f, 0.0f, 0.7853982f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
        this.Barrel.render(f5);
        GL11.glDisable((int)2884);
        this.Scope.render(f5);
        GL11.glEnable((int)2884);
        this.ClipPivot.render(f5);
        this.ClipPivotBack.render(f5);
        this.Clip.render(f5);
        this.BarrelBottom.render(f5);
        this.BodyStock.render(f5);
        this.BodyStockBottom.render(f5);
        this.BodyBack.render(f5);
        this.Handle.render(f5);
        GL11.glDisable((int)2884);
        this.TriggerFrame.render(f5);
        GL11.glEnable((int)2884);
        this.Trigger.render(f5);
        this.Bar.render(f5);
        this.BarFront.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
