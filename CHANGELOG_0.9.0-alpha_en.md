# HBM NTM Reforged - 0.9.0-alpha

**Minecraft:** 1.12.2 · **Forge:** 14.23.5.2859+  
**File:** `HBM-NTM-Reforged-0.9.0-alpha.jar`

This update focuses on restoring crucial endgame multiblock logic, improving compatibility, rebalancing blast resistances, and fixing many hidden bugs in the recipe and hazard systems.

---

## Machines & Multiblocks
- **ITER Fusion Reactor fixed:** The multiblock structure can now be successfully built again. Placing the conductors, center, motor, and glass in the correct shape now properly forms the ITER core. Breaking the core also returns the correct components.
- **Plasma Heater expanded:** Added support for **XM Plasma** (Xenon + Mercury) and **PUT Plasma** (PuF6 + Tritium). Fixed an issue where breaking the plasma heater wouldn't return its components.
- **Sliding Blast Doors migrated:** Old or corrupted legacy blast door tile entities now automatically migrate to the new, stable system upon world load, preventing invisible blocks and crashes.

## Blocks & World
- **Massive Blast Resistance Rebalance:** Significantly buffed the hardness and blast resistance of almost all reinforced building blocks. Reinforced Stone, Concrete, Ducrete, CMB Bricks, and their slab/stair variants can now properly withstand explosions as intended.
- **Ores & Minerals:** Rare drops from Bedrock Ores have been restored! (e.g. Lead drops Cadmium, Gold drops Bismuth, Uranium drops Radium-226, Thorium drops Technetium).
- **Hazard System fixes:** 
  - Legacy fuel rods and fuel variants now correctly emit radiation.
  - Uranium and Thorium ores are no longer blacklisted and will emit radiation in the world.
  - Trixite crystals and frozen blocks now correctly emit COLD hazards.

## Fluids & Compatibility
- **New Fluid:** Added **PUT Plasma** (PuF6 + Tritium).
- **ForgeFluid Rendering fixed:** Addressed a major bug where Forge-compatible fluids appeared as missing textures. Custom fluid textures now correctly resolve, and Plasma/Gas fluids now have the correct gaseous properties for other mods.

## Addons & Recipes (Space / Tweaks)
- **Recipe Tweakers Active:** Fixed a critical issue where custom recipe tweakers were not being initialized. All recipe modifications (including custom Anvil, Assembly, and SILEX tweaks) now apply correctly on game start.
- **SILEX Wavelength fix:** Updated SILEX laser recipes to use the proper X-RAY wavelength enum, fixing compatibility issues.
- **Centrifuge fix:** Centrifuging MINSOL now correctly yields cleaned crystals alongside iron powder.
- **Entity Drops fixed:** Nuclear Creepers and Tier 0 Missiles now drop the correct, updated items instead of legacy ammo.
