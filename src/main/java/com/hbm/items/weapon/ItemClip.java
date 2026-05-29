package com.hbm.items.weapon;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSimpleConsumable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ItemClip extends Item {

    private final Supplier<ItemStack> content;

    public ItemClip(String name, Supplier<ItemStack> content) {
        this.content = content;
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(1);
        this.setMaxStackSize(32);
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            ItemStack reward = content.get();
            if (!reward.isEmpty()) {
                ItemSimpleConsumable.tryAddItem(player, reward.copy());
            }
        }

        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
}
