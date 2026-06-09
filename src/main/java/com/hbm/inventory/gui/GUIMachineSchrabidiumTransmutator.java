package com.hbm.inventory.gui;

import com.hbm.Tags;
import com.hbm.inventory.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.tileentity.machine.TileEntityMachineSchrabidiumTransmutator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineSchrabidiumTransmutator extends GuiInfoContainer {

    private static final ResourceLocation texture = new ResourceLocation(Tags.MODID + ":textures/gui/gui_transmutator.png");
    private final TileEntityMachineSchrabidiumTransmutator transmutator;

    public GUIMachineSchrabidiumTransmutator(InventoryPlayer invPlayer, TileEntityMachineSchrabidiumTransmutator te) {
        super(new ContainerMachineSchrabidiumTransmutator(invPlayer, te));
        transmutator = te;
        this.xSize = 176;
        this.ySize = 222;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 106 - 88, 16, 88, transmutator.power, TileEntityMachineSchrabidiumTransmutator.maxPower);
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = transmutator.hasCustomName() ? transmutator.getName() : I18n.format(transmutator.getName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        String powerText = transmutator.getPower() + " HE";
        this.fontRenderer.drawString(powerText, this.xSize / 2 - this.fontRenderer.getStringWidth(powerText) / 2, 16, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (transmutator.getPower() > 0) {
            int i = (int) transmutator.getPowerScaled(88);
            drawTexturedModalRect(guiLeft + 8, guiTop + 106 - i, 176, 88 - i, 16, i);
        }

        if (transmutator.isProcessing()) {
            int j = transmutator.getProgressScaled(66);
            drawTexturedModalRect(guiLeft + 64, guiTop + 55, 176, 88, j, 66);
        }
    }
}