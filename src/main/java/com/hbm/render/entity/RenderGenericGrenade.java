package com.hbm.render.entity;

import com.hbm.entity.grenade.IGenericGrenade;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGenericGrenade;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class RenderGenericGrenade<T extends Entity & IGenericGrenade> extends RenderSnowball<T> {

    public RenderGenericGrenade(RenderManager renderManagerIn, RenderItem itemRendererIn) {
        super(renderManagerIn, ModItems.stick_dynamite, itemRendererIn);
    }

    @Override
    public ItemStack getStackToRender(T entityIn) {
        ItemGenericGrenade grenade = entityIn.getGrenade();
        if (grenade != null) {
            return new ItemStack(grenade);
        }
        return super.getStackToRender(entityIn);
    }
}