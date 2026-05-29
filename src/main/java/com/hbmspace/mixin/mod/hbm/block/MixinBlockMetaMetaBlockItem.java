package com.hbmspace.mixin.mod.hbm.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMeta;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockMeta.MetaBlockItem.class, remap = false)
public abstract class MixinBlockMetaMetaBlockItem extends ItemBlock {

    public MixinBlockMetaMetaBlockItem(Block block) {
        super(block);
    }

    @Inject(method = {"getSubItems", "func_150895_a"}, at = @At("HEAD"), cancellable = true, remap = false)
    private void space$getSubItems(CreativeTabs tab, NonNullList<ItemStack> items, CallbackInfo ci) {
        if(this.getBlock() != ModBlocks.plant_flower) return;

        Item item = (Item)(Object)this;
        if(this.isInCreativeTab(tab)) {
            for(int meta = 0; meta < 8; meta++) {
                items.add(new ItemStack(item, 1, meta));
            }
        }
        ci.cancel();
    }
}
