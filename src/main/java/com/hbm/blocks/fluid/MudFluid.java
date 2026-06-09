package com.hbm.blocks.fluid;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class MudFluid extends Fluid {

	public MudFluid() {
		super("mud_fluid",
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/mud_still"),
				new ResourceLocation(Tags.MODID, "blocks/forgefluid/mud_flowing"));
	}
}