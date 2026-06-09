package com.hbm.inventory.gui;

import com.hbm.Tags;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemCrucibleTemplate;
import com.hbm.items.machine.ItemFluidIDMulti;
import com.hbm.packet.ItemFolderPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.I18nUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIScreenTemplateFolder extends GuiScreen {

    protected static final ResourceLocation texture = new ResourceLocation(Tags.MODID, "textures/gui/gui_planner.png");
    protected int xSize = 176;
    protected int ySize = 229;
    protected int guiLeft;
    protected int guiTop;
    int currentPage = 0;
    List<ItemStack> stacks = new ArrayList<>();
    List<FolderButton> buttons = new ArrayList<>();
    private final EntityPlayer player;
    private final List<ItemStack> allStacks;
    private GuiTextField search;

    private void search(String sub) {
        stacks.clear();
        this.currentPage = 0;

        if (sub == null || sub.isEmpty()) {
            stacks.addAll(allStacks);
            updateButtons();
            return;
        }

        sub = sub.toLowerCase();

        for (ItemStack stack : allStacks) {
            if (stack.getDisplayName().toLowerCase().contains(sub)) {
                stacks.add(stack);
            } else if (stack.getItem() instanceof ItemFluidIDMulti) {
                FluidType fluid = ItemFluidIDMulti.getType(stack, true);
                if (I18nUtil.resolveKey(fluid.getTranslationKey()).toLowerCase().contains(sub)) {
                    stacks.add(stack);
                }
            } else if (stack.getItem() instanceof ItemCassette) {
                ItemCassette.TrackType track = ItemCassette.getType(stack);
                if (track.getTrackTitle().toLowerCase().contains(sub)) {
                    stacks.add(stack);
                }
            }
        }

        updateButtons();
    }

    public GUIScreenTemplateFolder(EntityPlayer player) {
        this.player = player;
        this.allStacks = new ArrayList<>();

        Item[] plateStamps = {
                ModItems.stamp_stone_plate, ModItems.stamp_iron_plate, ModItems.stamp_steel_plate,
                ModItems.stamp_titanium_plate, ModItems.stamp_obsidian_plate, ModItems.stamp_desh_plate,
                ModItems.stamp_schrabidium_plate
        };
        Item[] wireStamps = {
                ModItems.stamp_stone_wire, ModItems.stamp_iron_wire, ModItems.stamp_steel_wire,
                ModItems.stamp_titanium_wire, ModItems.stamp_obsidian_wire, ModItems.stamp_desh_wire,
                ModItems.stamp_schrabidium_wire
        };
        Item[] circuitStamps = {
                ModItems.stamp_stone_circuit, ModItems.stamp_iron_circuit, ModItems.stamp_steel_circuit,
                ModItems.stamp_titanium_circuit, ModItems.stamp_obsidian_circuit, ModItems.stamp_desh_circuit,
                ModItems.stamp_schrabidium_circuit
        };

        for (Item stamp : plateStamps) allStacks.add(new ItemStack(stamp));
        for (Item stamp : wireStamps) allStacks.add(new ItemStack(stamp));
        for (Item stamp : circuitStamps) allStacks.add(new ItemStack(stamp));

        for (ItemCassette.TrackType track : ItemCassette.TrackType.VALUES.values()) {
            if (track != ItemCassette.TrackType.NULL) {
                allStacks.add(new ItemStack(ModItems.siren_track, 1, track.getId()));
            }
        }

        for (FluidType fluid : Fluids.getInNiceOrder()) {
            if (!fluid.hasNoID()) {
                ItemStack id = new ItemStack(ModItems.fluid_identifier_multi, 1, fluid.getID());
                ItemFluidIDMulti.setType(id, fluid, true);
                allStacks.add(id);
            }
        }

        for (int i = 0; i < CrucibleRecipes.INSTANCE.recipeOrderedList.size(); i++) {
            allStacks.add(new ItemStack(ModItems.crucible_template, 1, i));
        }

        search(null);
    }

    int getPageCount() {
        return (stacks.size() - 1) / (5 * 7);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.enableLighting();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        updateButtons();

        Keyboard.enableRepeatEvents(true);
        this.search = new GuiTextField(0, this.fontRenderer, guiLeft + 61, guiTop + 213, 48, 12);
        this.search.setTextColor(0xffffff);
        this.search.setDisabledTextColour(0xffffff);
        this.search.setEnableBackgroundDrawing(false);
        this.search.setMaxStringLength(100);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void updateButtons() {
        buttons.clear();

        for (int i = currentPage * 35; i < Math.min(currentPage * 35 + 35, stacks.size()); i++) {
            buttons.add(new FolderButton(
                    guiLeft + 25 + (27 * (i % 5)),
                    guiTop + 26 + (27 * (int) Math.floor((i / 5D))) - currentPage * 27 * 7,
                    stacks.get(i)
            ));
        }

        if (currentPage != 0) {
            buttons.add(new FolderButton(guiLeft + 25 - 18, guiTop + 26 + (27 * 3), 1, "Previous"));
        }
        if (currentPage != getPageCount()) {
            buttons.add(new FolderButton(guiLeft + 25 + (27 * 4) + 18, guiTop + 26 + (27 * 3), 2, "Next"));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws java.io.IOException {
        this.search.mouseClicked(mouseX, mouseY, mouseButton);
        if (search.isFocused()) {
            return;
        }

        try {
            for (FolderButton b : buttons) {
                if (b.isMouseOnButton(mouseX, mouseY)) {
                    b.executeAction();
                }
            }
        } catch (Exception ex) {
            updateButtons();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String page = (currentPage + 1) + "/" + (getPageCount() + 1);
        this.fontRenderer.drawString(page,
                guiLeft + this.xSize / 2 - this.fontRenderer.getStringWidth(page) / 2,
                guiTop + 10, 4210752);

        for (FolderButton b : buttons) {
            if (b.isMouseOnButton(mouseX, mouseY)) {
                b.drawString(mouseX, mouseY);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (search.isFocused()) {
            drawTexturedModalRect(guiLeft + 45, guiTop + 211, 176, 54, 72, 12);
        }

        for (FolderButton b : buttons) {
            b.drawButton(b.isMouseOnButton(mouseX, mouseY));
        }
        for (FolderButton b : buttons) {
            b.drawIcon(b.isMouseOnButton(mouseX, mouseY));
        }

        search.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws java.io.IOException {
        if (this.search.textboxKeyTyped(typedChar, keyCode)) {
            this.search(this.search.getText());
            return;
        }

        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.player.closeScreen();
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    class FolderButton {

        int xPos;
        int yPos;
        int type;
        String info;
        ItemStack stack;

        FolderButton(int x, int y, int t, String i) {
            xPos = x;
            yPos = y;
            type = t;
            info = i;
        }

        FolderButton(int x, int y, ItemStack stack) {
            xPos = x;
            yPos = y;
            type = 0;
            info = stack.getDisplayName();
            this.stack = stack.copy();
        }

        boolean isMouseOnButton(int mouseX, int mouseY) {
            return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
        }

        void drawButton(boolean hovered) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            drawTexturedModalRect(xPos, yPos, hovered ? 176 + 18 : 176, type == 1 ? 18 : (type == 2 ? 36 : 0), 18, 18);
        }

        void drawIcon(boolean hovered) {
            try {
                RenderHelper.enableGUIStandardItemLighting();
                if (stack != null) {
                    if (stack.getItem() == ModItems.crucible_template) {
                        ItemStack icon = ItemCrucibleTemplate.getIcon(stack);
                        if (!icon.isEmpty()) {
                            itemRender.renderItemAndEffectIntoGUI(player, icon, xPos + 1, yPos + 1);
                        } else {
                            itemRender.renderItemAndEffectIntoGUI(player, stack, xPos + 1, yPos + 1);
                        }
                    } else {
                        itemRender.renderItemAndEffectIntoGUI(player, stack, xPos + 1, yPos + 1);
                    }
                }
                RenderHelper.disableStandardItemLighting();
            } catch (Exception ignored) {
            }
        }

        void drawString(int x, int y) {
            if (info == null || info.isEmpty()) {
                return;
            }

            String s = info;
            if (stack != null) {
                if (stack.getItem() instanceof ItemFluidIDMulti) {
                    s += ": " + I18n.format(ItemFluidIDMulti.getType(stack, true).getTranslationKey());
                } else if (stack.getItem() instanceof ItemCassette) {
                    s = ItemCassette.getType(stack).getTrackTitle();
                }
            }

            drawHoveringText(Arrays.asList(s), x, y);
        }

        void executeAction() {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (type == 0) {
                PacketDispatcher.wrapper.sendToServer(new ItemFolderPacket(stack.copy()));
            } else if (type == 1) {
                if (currentPage > 0) {
                    currentPage--;
                }
                updateButtons();
            } else if (type == 2) {
                if (currentPage < getPageCount()) {
                    currentPage++;
                }
                updateButtons();
            }
        }
    }
}