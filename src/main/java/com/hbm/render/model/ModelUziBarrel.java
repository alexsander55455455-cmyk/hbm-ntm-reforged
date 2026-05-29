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

public class ModelUziBarrel
extends ModelBase {
    ModelRenderer Barrel;

    public ModelUziBarrel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.Barrel = new ModelRenderer((ModelBase)this, 0, 0);
        this.Barrel.addBox(0.0f, 0.0f, 0.0f, 12, 2, 2);
        this.Barrel.setRotationPoint(-40.0f, 2.0f, 1.0f);
        this.Barrel.setTextureSize(32, 32);
        this.Barrel.mirror = true;
        this.setRotation(this.Barrel, 0.0f, 0.0f, 0.0f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Barrel.render(f5);
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
