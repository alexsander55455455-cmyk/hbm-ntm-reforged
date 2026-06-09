package com.hbm.packet;

import com.hbm.inventory.OreDictManager;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCrucibleTemplate;
import com.hbm.items.machine.ItemFluidIDMulti;
import com.hbm.lib.Library;
import com.hbm.util.InventoryUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class ItemFolderPacket implements IMessage {

    ItemStack stack;
    PacketBuffer buffer;

    public ItemFolderPacket() {
    }

    public ItemFolderPacket(ItemStack stack) {
        buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeCompoundTag(stack.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (buffer == null) {
            buffer = new PacketBuffer(Unpooled.buffer());
        }
        buffer.writeBytes(buf);
        try {
            stack = new ItemStack(buffer.readCompoundTag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (buffer == null) {
            buffer = new PacketBuffer(Unpooled.buffer());
        }
        buf.writeBytes(buffer);
    }

    public static class Handler implements IMessageHandler<ItemFolderPacket, IMessage> {

        @Override
        public IMessage onMessage(ItemFolderPacket message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            if (message.stack == null || message.stack.isEmpty()) {
                return null;
            }

            player.getServer().addScheduledTask(() -> handle(player, message.stack.copy()));
            return null;
        }

        private static void handle(EntityPlayer player, ItemStack stack) {
            if (player.getHeldItemMainhand().getItem() != ModItems.template_folder
                    && player.getHeldItemOffhand().getItem() != ModItems.template_folder) {
                return;
            }

            if (player.capabilities.isCreativeMode) {
                if (!player.inventory.addItemStackToInventory(stack)) {
                    player.dropItem(stack, true);
                }
                return;
            }

            if (stack.getItem() instanceof ItemFluidIDMulti) {
                if (Library.hasInventoryOreDict(player.inventory, OreDictManager.IRON.plate())
                        && Library.hasInventoryOreDict(player.inventory, "dye")) {
                    InventoryUtil.consumeOreDictMatches(player, OreDictManager.IRON.plate(), 1);
                    InventoryUtil.consumeOreDictMatches(player, "dye", 1);
                    giveStack(player, stack);
                }
                return;
            }

            if (stack.getItem() instanceof ItemCrucibleTemplate) {
                if (Library.hasInventoryItem(player.inventory, Items.PAPER) && Library.hasInventoryOreDict(player.inventory, "dye")) {
                    Library.consumeInventoryItem(player.inventory, Items.PAPER);
                    InventoryUtil.consumeOreDictMatches(player, "dye", 1);
                    giveStack(player, stack);
                }
                return;
            }

            if (stack.getItem() == ModItems.siren_track) {
                if (Library.hasInventoryItem(player.inventory, ModItems.plate_polymer)
                        && Library.hasInventoryOreDict(player.inventory, OreDictManager.STEEL.plate())) {
                    Library.consumeInventoryItem(player.inventory, ModItems.plate_polymer);
                    InventoryUtil.consumeOreDictMatches(player, OreDictManager.STEEL.plate(), 1);
                    giveStack(player, stack);
                }
                return;
            }

            if (consumeFlatStamp(player, stack, ModItems.stamp_stone_flat, ModItems.stamp_stone_plate, ModItems.stamp_stone_wire, ModItems.stamp_stone_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_iron_flat, ModItems.stamp_iron_plate, ModItems.stamp_iron_wire, ModItems.stamp_iron_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_steel_flat, ModItems.stamp_steel_plate, ModItems.stamp_steel_wire, ModItems.stamp_steel_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_titanium_flat, ModItems.stamp_titanium_plate, ModItems.stamp_titanium_wire, ModItems.stamp_titanium_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_obsidian_flat, ModItems.stamp_obsidian_plate, ModItems.stamp_obsidian_wire, ModItems.stamp_obsidian_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_desh_flat, ModItems.stamp_desh_plate, ModItems.stamp_desh_wire, ModItems.stamp_desh_circuit)) return;
            if (consumeFlatStamp(player, stack, ModItems.stamp_schrabidium_flat, ModItems.stamp_schrabidium_plate, ModItems.stamp_schrabidium_wire, ModItems.stamp_schrabidium_circuit)) return;
        }

        private static boolean consumeFlatStamp(EntityPlayer player, ItemStack target, net.minecraft.item.Item flat,
                                                net.minecraft.item.Item plate, net.minecraft.item.Item wire, net.minecraft.item.Item circuit) {
            if (target.getItem() == plate || target.getItem() == wire || target.getItem() == circuit) {
                if (Library.hasInventoryItem(player.inventory, flat)) {
                    Library.consumeInventoryItem(player.inventory, flat);
                    giveStack(player, target);
                    return true;
                }
            }
            return false;
        }

        private static void giveStack(EntityPlayer player, ItemStack stack) {
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, true);
            }
        }
    }
}