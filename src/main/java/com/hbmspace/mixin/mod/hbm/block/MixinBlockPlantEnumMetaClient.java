package com.hbmspace.mixin.mod.hbm.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPlantEnumMeta;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockPlantEnumMeta.class)
public abstract class MixinBlockPlantEnumMetaClient {

    @Unique
    private static final String[] HBM_SPACE$PLANT_FLOWER_ITEM_MODELS = {
            "plant_flower_foxglove",
            "plant_flower_hemp",
            "plant_flower_mustard_willow_0",
            "plant_flower_mustard_willow_1",
            "plant_flower_nightshade",
            "plant_flower_tobacco",
            "plant_flower_strawberry",
            "plant_flower_mint"
    };

    @Inject(method = "registerModel", at = @At("HEAD"), cancellable = true, remap = false)
    private void space$registerPlantFlowerItemModels(CallbackInfo ci) {
        Block block = (Block) (Object) this;
        if (block != ModBlocks.plant_flower) return;

        Item item = Item.getItemFromBlock(block);
        for (int meta = 0; meta < HBM_SPACE$PLANT_FLOWER_ITEM_MODELS.length; meta++) {
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    meta,
                    new ModelResourceLocation("hbm:" + HBM_SPACE$PLANT_FLOWER_ITEM_MODELS[meta], "inventory")
            );
        }
        ci.cancel();
    }
}
