package com.hbmspace.inventory.recipes.tweakers;

public class RecipeTweakerManager {

    private static boolean appliedAfterLoad;

    public static void initRecipeTweakers() {
        // Applied in SpaceMain.postInit after SerializableRecipe.initialize().
    }

    public static void applyAllTweakers() {
        if (appliedAfterLoad) {
            return;
        }
        appliedAfterLoad = true;
        applyTweaker("AnvilRecipes");
        applyTweaker("ArcWelderRecipes");
        applyTweaker("AssemblyMachineRecipes");
        applyTweaker("BlastFurnaceRecipes");
        applyTweaker("CentrifugeRecipes");
        applyTweaker("ChemicalPlantRecipes");
        applyTweaker("CrackingRecipes");
        applyTweaker("CrucibleRecipes");
        applyTweaker("CrystallizerRecipes");
        applyTweaker("CyclotronRecipes");
        applyTweaker("ElectrolyserFluidRecipes");
        applyTweaker("FractionRecipes");
        applyTweaker("HadronRecipes");
        applyTweaker("HydrotreatingRecipes");
        applyTweaker("LiquefactionRecipes");
        applyTweaker("MagicRecipes");
        applyTweaker("MixerRecipes");
        applyTweaker("OutgasserRecipes");
        applyTweaker("PUREXRecipes");
        applyTweaker("PressRecipes");
        applyTweaker("PyroOvenRecipes");
        applyTweaker("RefineryRecipes");
        applyTweaker("ReformingRecipes");
        applyTweaker("RotaryFurnaceRecipes");
        applyTweaker("SILEXRecipes");
        applyTweaker("ShredderRecipes");
        applyTweaker("SolderingRecipes");
        applyTweaker("SolidificationRecipes");
    }

    private static void applyTweaker(String recipeHandlerName) {
        switch (recipeHandlerName) {
            case "AnvilRecipes": AnvilRecipeTweaker.init(); AnvilSmithingRecipeTweaker.init(); break;
            case "ArcWelderRecipes": ArcWelderRecipesTweaker.init(); break;
            case "AssemblyMachineRecipes": AssemblyRecipesTweaker.init(); break;
            case "BlastFurnaceRecipes": BlastFurnaceRecipesTweaker.init(); break;
            case "CentrifugeRecipes": CentrifugeRecipesTweaker.init(); break;
            case "ChemicalPlantRecipes": ChemicalPlantRecipesTweaker.init(); break;
            case "CrackingRecipes": CrackingRecipesTweaker.init(); break;
            case "CrucibleRecipes": CrucibleRecipesTweaker.init(); break;
            case "CrystallizerRecipes": CrystallizerRecipesTweaker.init(); break;
            case "CyclotronRecipes": CyclotronRecipesTweaker.init(); break;
            case "ElectrolyserFluidRecipes": ElectrolyserFluidRecipesTweaker.init(); break;
            case "FractionRecipes": FractionRecipesTweaker.init(); break;
            case "HadronRecipes": HadronRecipesTweaker.init(); break;
            case "HydrotreatingRecipes": HydrotreatingRecipesTweaker.init(); break;
            case "LiquefactionRecipes": LiquefactionRecipesTweaker.init(); break;
            case "MagicRecipes": MagicRecipesTweaker.init(); break;
            case "MixerRecipes": MixerRecipesTweaker.init(); break;
            case "OutgasserRecipes": OutgasserRecipesTweaker.init(); break;
            case "PUREXRecipes": PUREXRecipesTweaker.init(); break;
            case "PressRecipes": PressRecipesTweaker.init(); break;
            case "PyroOvenRecipes": PyroOvenRecipesTweaker.init(); break;
            case "RefineryRecipes": RefineryRecipesTweaker.init(); break;
            case "ReformingRecipes": ReformingRecipesTweaker.init(); break;
            case "RotaryFurnaceRecipes": RotaryFurnaceRecipesTweaker.init(); break;
            case "SILEXRecipes": SILEXRecipesTweaker.init(); break;
            case "ShredderRecipes": ShredderRecipesTweaker.init(); break;
            case "SolderingRecipes": SolderingRecipesTweaker.init(); break;
            case "SolidificationRecipes": SolidificationRecipesTweaker.init(); break;
        }
    }
}
