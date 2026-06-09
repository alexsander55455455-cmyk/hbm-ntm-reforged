package com.hbm.blocks.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	public static Fluid toxic_fluid = new ToxicFluid("toxic_fluid").setDensity(2500).setViscosity(2000).setTemperature(70 + 273);
	public static Fluid mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(1773);
	public static Fluid acid_fluid = new FluidNTM("acid_fluid", "forgefluid/acid_still", "forgefluid/acid_flowing").setDensity(2500).setViscosity(1500).setLuminosity(5).setTemperature(2773);
	public static Fluid schrabidic = new SchrabidicFluid("schrabidic").setDensity(31200).setViscosity(500).setLuminosity(15);
	public static Fluid corium_fluid = new CoriumFluid().setDensity(31200).setViscosity(2000).setTemperature(3000 + 273);
	public static Fluid volcanic_lava_fluid = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300 + 273);
	public static Fluid radwater_fluid = new RadWaterFluid("radwater_fluid").setDensity(1000);
	public static Fluid rad_lava_fluid = new FluidNTM("rad_lava_fluid", "forgefluid/rad_lava_still", "forgefluid/rad_lava_flowing").setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300 + 273);
	public static Fluid sulfuric_acid_fluid = new FluidNTM("sulfuric_acid", "forgefluid/sulfuric_acid_still", "forgefluid/sulfuric_acid_flowing").setDensity(1840).setViscosity(1000).setTemperature(273);

	public static void init() {
		registerFluid(toxic_fluid);
		registerFluid(mud_fluid);
		registerFluid(acid_fluid);
		registerFluid(schrabidic);
		registerFluid(corium_fluid);
		registerFluid(volcanic_lava_fluid);
		registerFluid(radwater_fluid);
		registerFluid(rad_lava_fluid);
		registerFluid(sulfuric_acid_fluid);
	}

	private static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}

	public static void setFromRegistry() {
		toxic_fluid = FluidRegistry.getFluid("toxic_fluid");
		mud_fluid = FluidRegistry.getFluid("mud_fluid");
		acid_fluid = FluidRegistry.getFluid("acid_fluid");
		schrabidic = FluidRegistry.getFluid("schrabidic");
		corium_fluid = FluidRegistry.getFluid("corium_fluid");
		volcanic_lava_fluid = FluidRegistry.getFluid("volcanic_lava_fluid");
		radwater_fluid = FluidRegistry.getFluid("radwater_fluid");
		rad_lava_fluid = FluidRegistry.getFluid("rad_lava_fluid");
		sulfuric_acid_fluid = FluidRegistry.getFluid("sulfuric_acid");
	}
}