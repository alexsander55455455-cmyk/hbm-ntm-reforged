package com.hbm.blocks.fluid;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.Color;

public class VolcanicFluid extends Fluid {

	public VolcanicFluid() {
		super("volcanic_lava_fluid",
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/volcanic_lava_still"),
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/volcanic_lava_flowing"),
				Color.WHITE);
	}
}