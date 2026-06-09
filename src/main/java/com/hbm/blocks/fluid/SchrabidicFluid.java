package com.hbm.blocks.fluid;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.Color;

public class SchrabidicFluid extends Fluid {

	public SchrabidicFluid(String name) {
		super(name,
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/schrabidic_acid_still"),
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/schrabidic_acid_flowing"),
				Color.WHITE);
	}
}