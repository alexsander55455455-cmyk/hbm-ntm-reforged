package com.hbm.forgefluid;

import com.hbm.render.misc.EnumSymbol;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidTypeHandler {

	private static Map<String, FluidProperties> fluidProperties = new HashMap<String, FluidProperties>();
	public static final FluidProperties NONE = new FluidProperties(0, 0, 0, EnumSymbol.NONE);

	public static void registerFluidProperties() {
		fluidProperties.put("toxic_fluid", new FluidProperties(3, 0, 4, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put("radwater_fluid", new FluidProperties(2, 0, 0, EnumSymbol.RADIATION));
		fluidProperties.put("mud_fluid", new FluidProperties(4, 0, 1, EnumSymbol.ACID, FluidTrait.CORROSIVE_2));
		fluidProperties.put("corium_fluid", new FluidProperties(4, 0, 2, EnumSymbol.RADIATION, FluidTrait.CORROSIVE_2));
		fluidProperties.put("volcanic_lava_fluid", new FluidProperties(4, 1, 1, EnumSymbol.NOWATER));
	}
	
	public static FluidProperties getProperties(Fluid f){
		if(f == null)
			return NONE;
		FluidProperties p = fluidProperties.get(f.getName());
		return p != null ? p : NONE;
	}

	public static float getDFCEfficiency(Fluid f){
		FluidProperties prop = getProperties(f);
		return prop.dfcFuel;
	}
	
	public static boolean isAntimatter(Fluid f){
		return containsTrait(f, FluidTrait.AMAT);
	}
	
	public static boolean isCorrosivePlastic(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE) || containsTrait(f, FluidTrait.CORROSIVE_2);
	}
	
	public static boolean isCorrosiveIron(Fluid f){
		return containsTrait(f, FluidTrait.CORROSIVE_2);
	}

	public static boolean containsTrait(Fluid f, FluidTrait t){
		if(f == null)
			return false;
		FluidProperties p = fluidProperties.get(f.getName());
		if(p == null)
			return false;
		return p.traits.contains(t);
	}

	public static class FluidProperties {
		
		public final int poison;
		public final int flammability;
		public final int reactivity;
		public final float dfcFuel;
		public final EnumSymbol symbol;
		public final List<FluidTrait> traits = new ArrayList<>();

		public FluidProperties(int p, int f, int r, EnumSymbol symbol, FluidTrait... traits) {
			this(p, f, r, 0, symbol, traits);
		}
		
		public FluidProperties(int p, int f, int r, float dfc, EnumSymbol symbol, FluidTrait... traits) {
			this.poison = p;
			this.flammability = f;
			this.reactivity = r;
			this.dfcFuel = dfc;
			this.symbol = symbol;
			for(FluidTrait trait : traits)
				this.traits.add(trait);
		}
	}
	
	public static enum FluidTrait {
		AMAT,
		CORROSIVE,
		CORROSIVE_2,
		NO_CONTAINER,
		NO_ID;
	}
}
