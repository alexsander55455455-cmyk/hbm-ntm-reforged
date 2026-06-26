package com.hbm.inventory.gui;

import com.hbm.Tags;
import com.hbm.inventory.container.ContainerMachineGenerator;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.util.I18nUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGenerator extends GuiInfoContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MODID + ":textures/gui/gui_rtg.png");
    private final TileEntityMachineGenerator generator;

    public GUIMachineGenerator(InventoryPlayer invPlayer, TileEntityMachineGenerator te) {
        super(new ContainerMachineGenerator(invPlayer, te));
        generator = te;
        xSize = 176;
        ySize = 222;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        generator.tankWater.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 36, 16, 52);
        generator.tankCoolant.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 36, 16, 52);
        this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 36, 16, 52, generator.power, generator.maxPower);

        if (generator.tankWater.getFill() <= 0) {
            String[] text = I18nUtil.resolveKeyArray("desc.guimachinegen1");
            this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 52, text);
        }
        if (generator.tankCoolant.getFill() <= 0) {
            String[] text = I18nUtil.resolveKeyArray("desc.guimachinegen2");
            this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 52, 16, 16, guiLeft - 8, guiTop + 68, text);
        }
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = generator.hasCustomName() ? generator.getName() : I18n.format(generator.getDefaultName());
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 176);

        if (generator.hasPower()) {
            int i = (int) generator.getPowerScaled(52);
            drawTexturedModalRect(guiLeft + 62, guiTop + 88 - i, 192, 52 - i, 16, i);
        }
        if (generator.hasHeat()) {
            int i = generator.getHeatScaled(52);
            drawTexturedModalRect(guiLeft + 98, guiTop + 88 - i, 176, 52 - i, 16, i);
        }
        if (generator.tankWater.getFill() <= 0) {
            drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 6);
        }
        if (generator.tankCoolant.getFill() <= 0) {
            drawInfoPanel(guiLeft - 16, guiTop + 52, 16, 16, 7);
        }

        generator.tankWater.renderTank(guiLeft + 8, guiTop + 88, zLevel, 16, 52);
        generator.tankCoolant.renderTank(guiLeft + 26, guiTop + 88, zLevel, 16, 52);
    }
}