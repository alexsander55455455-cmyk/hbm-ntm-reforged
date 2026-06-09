package com.hbm.blocks.fluid;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.Color;

public class CoriumFluid extends Fluid {

	public CoriumFluid() {
		super("corium_fluid",
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/corium_still"),
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/corium_flowing"),
				Color.WHITE);
	}
}