package com.hbm.items.gear;

import com.hbm.Tags;
import com.hbm.api.item.IGasMask;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.render.NTMRenderHelper;
import com.hbm.render.model.ModelM65;
import com.hbm.util.ArmorRegistry.HazardClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ArmorHazmatMask extends ArmorHazmat implements IGasMask {

	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;
	private final ResourceLocation goggleBlur0 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_0.png");
	private final ResourceLocation goggleBlur1 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_1.png");
	private final ResourceLocation goggleBlur2 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_2.png");
	private final ResourceLocation goggleBlur3 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_3.png");
	private final ResourceLocation goggleBlur4 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_4.png");
	private final ResourceLocation goggleBlur5 = new ResourceLocation(Tags.MODID + ":textures/misc/overlay_goggles_5.png");

	public ArmorHazmatMask(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn, s);
	}

	@Override
	public boolean isValidArmor(@NotNull ItemStack stack, @NotNull EntityEquipmentSlot armorType, @NotNull Entity entity) {
		return armorType == EntityEquipmentSlot.HEAD;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(@NotNull EntityLivingBase entityLiving, @NotNull ItemStack itemStack, @NotNull EntityEquipmentSlot armorSlot, @NotNull ModelBiped _default) {
		if ((this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey || this == ModItems.hazmat_paa_helmet) && armorSlot == EntityEquipmentSlot.HEAD) {
			if (this.modelM65 == null) {
				this.modelM65 = new ModelM65();
			}
			return this.modelM65;
		}

		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ModItems.hazmat_helmet) {
			return Tags.MODID + ":textures/armor/hazmat_1.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_red) {
			return Tags.MODID + ":textures/armor/modelhazred.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_grey) {
			return Tags.MODID + ":textures/armor/modelhazgrey.png";
		}
		if (stack.getItem() == ModItems.hazmat_paa_helmet) {
			return Tags.MODID + ":textures/armor/modelhazpaa.png";
		}

		return super.getArmorTexture(stack, entity, slot, type);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHelmetOverlay(@NotNull ItemStack stack, @NotNull EntityPlayer player, @NotNull ScaledResolution resolution, float partialTicks) {
		if (this == ModItems.hazmat_helmet || this == ModItems.hazmat_paa_helmet) {
			super.renderHelmetOverlay(stack, player, resolution, partialTicks);
			return;
		}

		if (this != ModItems.hazmat_helmet_red && this != ModItems.hazmat_helmet_grey) {
			return;
		}

		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();

		switch((int)((double)stack.getItemDamage() / (double)stack.getMaxDamage() * 6D)) {
		case 0:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur0); break;
		case 1:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur1); break;
		case 2:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur2); break;
		case 3:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur3); break;
		case 4:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur4); break;
		default:
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur5); break;
		}

		NTMRenderHelper.startDrawingTexturedQuads();
		NTMRenderHelper.addVertexWithUV(0F, resolution.getScaledHeight(), -90F, 0F, 1F);
		NTMRenderHelper.addVertexWithUV(resolution.getScaledWidth(), resolution.getScaledHeight(), -90F, 1F, 1F);
		NTMRenderHelper.addVertexWithUV(resolution.getScaledWidth(), 0F, -90F, 1F, 0F);
		NTMRenderHelper.addVertexWithUV(0F, 0F, -90F, 0F, 0F);
		NTMRenderHelper.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addInformation(@NotNull ItemStack stack, World worldIn, @NotNull List<String> list, @NotNull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		ArmorUtil.addGasMaskTooltip(stack, worldIn, list, flagIn);
	}

	@Override
	public List<HazardClass> getBlacklist(ItemStack stack) {
		return Collections.emptyList();
	}

	@Override
	public @NotNull ItemStack getFilter(ItemStack stack) {
		return ArmorUtil.getGasMaskFilter(stack);
	}

	@Override
	public boolean isFilterApplicable(ItemStack stack, ItemStack filter) {
		return true;
	}

	@Override
	public void installFilter(ItemStack stack, ItemStack filter) {
		ArmorUtil.installGasMaskFilter(stack, filter);
	}

	@Override
	public void damageFilter(ItemStack stack, int damage) {
		ArmorUtil.damageGasMaskFilter(stack, damage);
	}

	@Override
	public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand hand) {
		if (player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			ItemStack filter = this.getFilter(stack);

			if (!filter.isEmpty()) {
				ArmorUtil.removeFilter(stack);

				if (!player.inventory.addItemStackToInventory(filter)) {
					player.dropItem(filter, true, false);
				}
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
}
