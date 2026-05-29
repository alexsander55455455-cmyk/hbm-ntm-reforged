/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 */
package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelZOMG
extends ModelBase {
    ModelRenderer Body;
    ModelRenderer BodyFront;
    ModelRenderer BodyFrontPlate;
    ModelRenderer PipeLeft;
    ModelRenderer PipeRight;
    ModelRenderer PipeBottom;
    ModelRenderer Head;
    ModelRenderer HeadBottom;
    ModelRenderer HeadBottomPlate;
    ModelRenderer BodyTop;
    ModelRenderer BodyCenter;
    ModelRenderer BodyBack;
    ModelRenderer BodyBackPlate;
    ModelRenderer StockTop;
    ModelRenderer StockBack;
    ModelRenderer StockBackPlate;
    ModelRenderer Handle;
    ModelRenderer PistonBack;
    ModelRenderer PistonFront;
    ModelRenderer BarrelVertical;
    ModelRenderer BarrelHorizontal;
    ModelRenderer BarrelCenter;
    ModelRenderer HandleBack;
    ModelRenderer BarrelPipe;
    ModelRenderer PistonPivot;
    ModelRenderer Scope;
    ModelRenderer ScopePivot;

    public ModelZOMG() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Body = new ModelRenderer((ModelBase)this, 0, 0);
        this.Body.addBox(0.0f, 0.0f, 0.0f, 12, 5, 4);
        this.Body.setRotationPoint(-6.0f, 0.0f, 0.0f);
        this.Body.setTextureSize(128, 64);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
        this.BodyFront = new ModelRenderer((ModelBase)this, 32, 14);
        this.BodyFront.addBox(0.0f, 0.0f, 0.0f, 1, 4, 4);
        this.BodyFront.setRotationPoint(-7.0f, 0.0f, 0.0f);
        this.BodyFront.setTextureSize(128, 64);
        this.BodyFront.mirror = true;
        this.setRotation(this.BodyFront, 0.0f, 0.0f, 0.0f);
        this.BodyFrontPlate = new ModelRenderer((ModelBase)this, 46, 6);
        this.BodyFrontPlate.addBox(-2.0f, -1.0f, 0.0f, 2, 1, 4);
        this.BodyFrontPlate.setRotationPoint(-6.0f, 5.0f, 0.0f);
        this.BodyFrontPlate.setTextureSize(128, 64);
        this.BodyFrontPlate.mirror = true;
        this.setRotation(this.BodyFrontPlate, 0.0f, 0.0f, 0.6981317f);
        this.PipeLeft = new ModelRenderer((ModelBase)this, 0, 29);
        this.PipeLeft.addBox(0.0f, 0.0f, 0.0f, 6, 1, 1);
        this.PipeLeft.setRotationPoint(-13.0f, 0.5f, 0.5f);
        this.PipeLeft.setTextureSize(128, 64);
        this.PipeLeft.mirror = true;
        this.setRotation(this.PipeLeft, 0.0f, 0.0f, 0.0f);
        this.PipeRight = new ModelRenderer((ModelBase)this, 14, 29);
        this.PipeRight.addBox(0.0f, 0.0f, 0.0f, 6, 1, 1);
        this.PipeRight.setRotationPoint(-13.0f, 0.5f, 2.5f);
        this.PipeRight.setTextureSize(128, 64);
        this.PipeRight.mirror = true;
        this.setRotation(this.PipeRight, 0.0f, 0.0f, 0.0f);
        this.PipeBottom = new ModelRenderer((ModelBase)this, 14, 25);
        this.PipeBottom.addBox(0.0f, 0.0f, 0.0f, 6, 2, 2);
        this.PipeBottom.setRotationPoint(-13.0f, 2.0f, 1.0f);
        this.PipeBottom.setTextureSize(128, 64);
        this.PipeBottom.mirror = true;
        this.setRotation(this.PipeBottom, 0.0f, 0.0f, 0.0f);
        this.Head = new ModelRenderer((ModelBase)this, 32, 0);
        this.Head.addBox(0.0f, 0.0f, 0.0f, 3, 4, 4);
        this.Head.setRotationPoint(-16.0f, 0.0f, 0.0f);
        this.Head.setTextureSize(128, 64);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
        this.HeadBottom = new ModelRenderer((ModelBase)this, 46, 0);
        this.HeadBottom.addBox(0.0f, 0.0f, 0.0f, 2, 2, 4);
        this.HeadBottom.setRotationPoint(-15.0f, 4.0f, 0.0f);
        this.HeadBottom.setTextureSize(128, 64);
        this.HeadBottom.mirror = true;
        this.setRotation(this.HeadBottom, 0.0f, 0.0f, 0.0f);
        this.HeadBottomPlate = new ModelRenderer((ModelBase)this, 32, 8);
        this.HeadBottomPlate.addBox(0.0f, 0.0f, 0.0f, 1, 2, 4);
        this.HeadBottomPlate.setRotationPoint(-16.0f, 4.0f, 0.0f);
        this.HeadBottomPlate.setTextureSize(128, 64);
        this.HeadBottomPlate.mirror = true;
        this.setRotation(this.HeadBottomPlate, 0.0f, 0.0f, -0.4363323f);
        this.BodyTop = new ModelRenderer((ModelBase)this, 0, 17);
        this.BodyTop.addBox(0.0f, 0.0f, 0.0f, 10, 1, 3);
        this.BodyTop.setRotationPoint(-5.0f, -0.5f, 0.5f);
        this.BodyTop.setTextureSize(128, 64);
        this.BodyTop.mirror = true;
        this.setRotation(this.BodyTop, 0.0f, 0.0f, 0.0f);
        this.BodyCenter = new ModelRenderer((ModelBase)this, 0, 9);
        this.BodyCenter.addBox(0.0f, 0.0f, 0.0f, 11, 3, 5);
        this.BodyCenter.setRotationPoint(-5.5f, 1.0f, -0.5f);
        this.BodyCenter.setTextureSize(128, 64);
        this.BodyCenter.mirror = true;
        this.setRotation(this.BodyCenter, 0.0f, 0.0f, 0.0f);
        this.BodyBack = new ModelRenderer((ModelBase)this, 42, 11);
        this.BodyBack.addBox(0.0f, 0.0f, 0.0f, 1, 3, 4);
        this.BodyBack.setRotationPoint(6.0f, 2.0f, 0.0f);
        this.BodyBack.setTextureSize(128, 64);
        this.BodyBack.mirror = true;
        this.setRotation(this.BodyBack, 0.0f, 0.0f, 0.0f);
        this.BodyBackPlate = new ModelRenderer((ModelBase)this, 58, 0);
        this.BodyBackPlate.addBox(-1.0f, -3.0f, 0.0f, 1, 4, 2);
        this.BodyBackPlate.setRotationPoint(7.0f, 2.0f, 1.0f);
        this.BodyBackPlate.setTextureSize(128, 64);
        this.BodyBackPlate.mirror = true;
        this.setRotation(this.BodyBackPlate, 0.0f, 0.0f, -0.4363323f);
        this.StockTop = new ModelRenderer((ModelBase)this, 0, 21);
        this.StockTop.addBox(0.0f, 0.0f, 0.0f, 8, 2, 2);
        this.StockTop.setRotationPoint(7.0f, 3.0f, 1.0f);
        this.StockTop.setTextureSize(128, 64);
        this.StockTop.mirror = true;
        this.setRotation(this.StockTop, 0.0f, 0.0f, 0.0f);
        this.StockBack = new ModelRenderer((ModelBase)this, 20, 21);
        this.StockBack.addBox(0.0f, 0.0f, 0.0f, 4, 2, 2);
        this.StockBack.setRotationPoint(11.0f, 5.0f, 1.0f);
        this.StockBack.setTextureSize(128, 64);
        this.StockBack.mirror = true;
        this.setRotation(this.StockBack, 0.0f, 0.0f, 0.0f);
        this.StockBackPlate = new ModelRenderer((ModelBase)this, 0, 25);
        this.StockBackPlate.addBox(-5.0f, -2.0f, 0.0f, 5, 2, 2);
        this.StockBackPlate.setRotationPoint(11.0f, 7.0f, 1.0f);
        this.StockBackPlate.setTextureSize(128, 64);
        this.StockBackPlate.mirror = true;
        this.setRotation(this.StockBackPlate, 0.0f, 0.0f, 0.418879f);
        this.Handle = new ModelRenderer((ModelBase)this, 64, 8);
        this.Handle.addBox(0.0f, 0.0f, 0.0f, 2, 4, 2);
        this.Handle.setRotationPoint(-4.0f, 5.0f, 1.0f);
        this.Handle.setTextureSize(128, 64);
        this.Handle.mirror = true;
        this.setRotation(this.Handle, 0.0f, 0.0f, 0.0f);
        this.PistonBack = new ModelRenderer((ModelBase)this, 30, 26);
        this.PistonBack.addBox(0.0f, -4.0f, 0.0f, 2, 4, 2);
        this.PistonBack.setRotationPoint(1.0f, 4.0f, -0.5f);
        this.PistonBack.setTextureSize(128, 64);
        this.PistonBack.mirror = true;
        this.setRotation(this.PistonBack, 0.7853982f, 0.0f, 0.0f);
        this.PistonFront = new ModelRenderer((ModelBase)this, 52, 11);
        this.PistonFront.addBox(0.0f, -4.0f, 0.0f, 2, 4, 2);
        this.PistonFront.setRotationPoint(-3.0f, 4.0f, -0.5f);
        this.PistonFront.setTextureSize(128, 64);
        this.PistonFront.mirror = true;
        this.setRotation(this.PistonFront, 0.7853982f, 0.0f, 0.0f);
        this.BarrelVertical = new ModelRenderer((ModelBase)this, 38, 22);
        this.BarrelVertical.addBox(0.0f, 0.0f, 0.0f, 8, 3, 2);
        this.BarrelVertical.setRotationPoint(-4.0f, 1.0f, 5.0f);
        this.BarrelVertical.setTextureSize(128, 64);
        this.BarrelVertical.mirror = true;
        this.setRotation(this.BarrelVertical, 0.0f, 0.0f, 0.0f);
        this.BarrelHorizontal = new ModelRenderer((ModelBase)this, 38, 27);
        this.BarrelHorizontal.addBox(0.0f, 0.0f, 0.0f, 8, 2, 3);
        this.BarrelHorizontal.setRotationPoint(-4.0f, 1.5f, 4.5f);
        this.BarrelHorizontal.setTextureSize(128, 64);
        this.BarrelHorizontal.mirror = true;
        this.setRotation(this.BarrelHorizontal, 0.0f, 0.0f, 0.0f);
        this.BarrelCenter = new ModelRenderer((ModelBase)this, 64, 0);
        this.BarrelCenter.addBox(0.0f, 0.0f, 0.0f, 10, 2, 2);
        this.BarrelCenter.setRotationPoint(-5.0f, 1.5f, 5.0f);
        this.BarrelCenter.setTextureSize(128, 64);
        this.BarrelCenter.mirror = true;
        this.setRotation(this.BarrelCenter, 0.0f, 0.0f, 0.0f);
        this.HandleBack = new ModelRenderer((ModelBase)this, 60, 8);
        this.HandleBack.addBox(0.0f, 0.0f, 0.0f, 1, 4, 1);
        this.HandleBack.setRotationPoint(-2.5f, 5.0f, 1.5f);
        this.HandleBack.setTextureSize(128, 64);
        this.HandleBack.mirror = true;
        this.setRotation(this.HandleBack, 0.0f, 0.0f, 0.0f);
        this.BarrelPipe = new ModelRenderer((ModelBase)this, 64, 4);
        this.BarrelPipe.addBox(-10.0f, 0.0f, -1.0f, 10, 1, 1);
        this.BarrelPipe.setRotationPoint(-5.0f, 2.0f, 6.5f);
        this.BarrelPipe.setTextureSize(128, 64);
        this.BarrelPipe.mirror = true;
        this.setRotation(this.BarrelPipe, 0.0f, -0.2602503f, 0.0f);
        this.PistonPivot = new ModelRenderer((ModelBase)this, 60, 14);
        this.PistonPivot.addBox(0.0f, -2.0f, 0.5f, 7, 2, 1);
        this.PistonPivot.setRotationPoint(-3.5f, 4.0f, -0.5f);
        this.PistonPivot.setTextureSize(128, 64);
        this.PistonPivot.mirror = true;
        this.setRotation(this.PistonPivot, 0.7853982f, 0.0f, 0.0f);
        this.Scope = new ModelRenderer((ModelBase)this, 48, 18);
        this.Scope.addBox(0.0f, 0.0f, 0.0f, 6, 2, 2);
        this.Scope.setRotationPoint(-3.0f, -3.0f, 1.0f);
        this.Scope.setTextureSize(128, 64);
        this.Scope.mirror = true;
        this.setRotation(this.Scope, 0.0f, 0.0f, 0.0f);
        this.ScopePivot = new ModelRenderer((ModelBase)this, 58, 6);
        this.ScopePivot.addBox(0.0f, 0.0f, 0.0f, 4, 1, 1);
        this.ScopePivot.setRotationPoint(-2.0f, -1.0f, 1.5f);
        this.ScopePivot.setTextureSize(128, 64);
        this.ScopePivot.mirror = true;
        this.setRotation(this.ScopePivot, 0.0f, 0.0f, 0.0f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
        this.BodyFront.render(f5);
        this.BodyFrontPlate.render(f5);
        this.PipeLeft.render(f5);
        this.PipeRight.render(f5);
        this.PipeBottom.render(f5);
        this.Head.render(f5);
        this.HeadBottom.render(f5);
        this.HeadBottomPlate.render(f5);
        this.BodyTop.render(f5);
        this.BodyCenter.render(f5);
        this.BodyBack.render(f5);
        this.BodyBackPlate.render(f5);
        this.StockTop.render(f5);
        this.StockBack.render(f5);
        this.StockBackPlate.render(f5);
        this.Handle.render(f5);
        this.PistonBack.render(f5);
        this.PistonFront.render(f5);
        this.BarrelVertical.render(f5);
        this.BarrelHorizontal.render(f5);
        this.BarrelCenter.render(f5);
        this.HandleBack.render(f5);
        this.BarrelPipe.render(f5);
        this.PistonPivot.render(f5);
        this.Scope.render(f5);
        this.ScopePivot.render(f5);
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
