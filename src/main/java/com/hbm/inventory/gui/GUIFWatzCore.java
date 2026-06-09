package com.hbm.inventory.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.Tags;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIFWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(Tags.MODID + ":textures/gui/gui_fwatz_multiblock.png");
	private TileEntityFWatzCore fwatz;

	public GUIFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		super(new ContainerFWatzCore(invPlayer, tedf));
		fwatz = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		fwatz.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 85, 70, 18);
		fwatz.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 17, 18, 72);
		fwatz.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 17, 18, 72);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 29, guiTop + 107, 118, 18, fwatz.power, TileEntityFWatzCore.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.fwatz.hasCustomName() ? this.fwatz.getName() : I18n.format(this.fwatz.getDefaultName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) throws IOException {
		super.mouseClicked(mouseX, mouseY, i);
		if(guiLeft + 29 <= mouseX && guiLeft + 29 + 18 > mouseX && guiTop + 89 < mouseY && guiTop + 89 + 18 >= mouseY) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketThreading.createSendToServerThreadedPacket(new NBTControlPacket(new NBTTagCompound(), fwatz.getPos().getX(), fwatz.getPos().getY(), fwatz.getPos().getZ()));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int)fwatz.getPowerScaled(116);
		drawTexturedModalRect(guiLeft + 30, guiTop + 108, 0, 222, k, 16);
		
		if(fwatz.isDoingSomething)
			drawTexturedModalRect(guiLeft + 64, guiTop + 29, 176, 24, 48, 48);

		if(fwatz.isOn)
			drawTexturedModalRect(guiLeft + 29, guiTop + 89, 192, 0, 18, 18);
		
		int m = fwatz.getSingularityType();
		drawTexturedModalRect(guiLeft + 80, guiTop + 20, 176, 4 * m, 16, 4);
		
		fwatz.tanks[0].renderTank(guiLeft + 54, guiTop + 130, zLevel, 68, 16);
		fwatz.tanks[1].renderTank(guiLeft + 8, guiTop + 116, zLevel, 16, 70);
		fwatz.tanks[2].renderTank(guiLeft + 152, guiTop + 116, zLevel, 16, 70);
	}
}