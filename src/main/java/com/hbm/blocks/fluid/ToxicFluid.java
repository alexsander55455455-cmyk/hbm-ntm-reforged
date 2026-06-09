package com.hbm.blocks.fluid;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.Color;

public class ToxicFluid extends Fluid {

	public ToxicFluid(String name) {
		super(name,
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/toxic_still"),
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/toxic_flowing"),
				Color.WHITE);
	}
}