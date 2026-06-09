# HBM NTM Reforged - 0.8.9-alpha

**Minecraft:** 1.12.2 · **Forge:** 14.23.5.2859+  
**File:** `HBM-NTM-Reforged-0.8.9-alpha.jar`

A major stability and gameplay patch. Below is everything that was broken, unfinished, or missing - and what works now.

---

## Oil & Extraction

- **Oil derrick and pumpjack pump oil again** - machines detect oil deposits (`ore_oil`), no longer blindly replace them with pipe, and actually transfer fluid into the tank.
- **Fixed black-and-purple blocks** when draining a deposit - empty oil and depleted pockets no longer turn into missing textures or vanish after rejoining the world.
- **Restored empty oil deposits** - correct textures in hand, JEI, and creative (`ore_oil_empty`).
- **Bedrock oil is back** - world generation and extraction with the hydraulic fracking tower.
- **Oil detector** - finds deposits that are actively being pumped, including bedrock oil; works correctly on test deposits placed from creative.
- **Correct oil type on Earth** - overworld deposits yield regular crude oil, not desulfurized crude.

---

## Weapons & Tools

### Gluon Gun
- Centered inventory icon and a proper first-person 3D model.
- Beam travels **from the barrel to the crosshair**, not from your head into the sky; one stable beam without flickering strips when turning the camera.

### Meteorite Swords
- Fixed textures for Demonic, Warped, and other variants - shimmer animation, correct look in inventory, JEI, and in hand.
- Swords are grouped together in creative/JEI instead of scattered through the list.

### Other Swords & Guns
- **HF Sword / HS Sword** - 3D inventory icons again (no flat unfolded texture).
- **Firearms** - can no longer melee-hit mobs; fixed the bug where after a punch the gun kept breaking blocks while LMB was held.
- **Tau Cannon** - automatic fire in short bursts while holding the button.
- **Assembly Machine grenades** - restored missing recipes.
- **Dynamite** - correct thrown projectile texture (not the fishing dynamite variant).

### Pickaxes & Abilities
- Steel and advanced pickaxes gained missing passive traits: **vein miner**, **silk touch**, **autosmelt**, and other modes.
- Removed glitchy mode icons near the crosshair; the active mode is shown in the **tooltip** (yellow arrow when enabled), as intended.

---

## Items, Recipes & Machines

- **Self-charging batteries** - added missing recipes (americium, uranium, schrabidium, etc.); removed useless non-craftable duplicates.
- **Schrabidium Transmutator** - block is back in-game with recipe and JEI entry.
- **Coal dust** - applies the coal dust hazard on items/blocks again.
- **Lead and schrabidium apples** - removed broken duplicates; working versions can be eaten and grant effects.
- **Crucible** - three modes: smelting, alloying, casting; fixed templates, textures, JEI, and alloying recipes (stainless steel, etc.).
- **CMB ingot** - crafted via crucible instead of the broken “alloy furnace”.
- **Metal scraps** - proper textures in JEI and GUI, not a “liquid square”.
- **Decorative metal blocks** - 1 block = 1 ingot; rusty steel decor: 2 blocks = 1 steel ingot.

---

## Radiation - Full Overhaul

This is the biggest change in the patch.

### Blocks & Environment
- Dozens of radioactive blocks now have **correct emission strength** (sellafield, waste grass, dead grass, glowing mushroom, ancient scrap, corium, tiered sellafield, and more).
- Blocks **contaminate the world around them again** - not tooltip-only; real chunk radiation spread.
- **Chunk spread tuned** - contamination from an ancient scrap cube reaches ~4 chunks from center (previously stretched to 5-6).
- **Block transformation from radiation:**
  - grass → waste grass / dead grass;
  - sand → contaminated sand (not trinitite ore);
  - mushrooms → glowing mushroom under heavy exposure;
  - dirt and leaves contaminate after explosions.

### Explosions & Fallout
- **Nuclear grenades and bombs** - after detonation, **tiered sellafield blocks** appear (weak tiers up to core at hundreds of RAD/s), not only weak slaked sellafield.
- **Underground fallout** - stone under the crater is contaminated by distance from ground zero.
- **Fallout rain** - radioactive rain/dust after large blasts (Fat Man and similar); visible thin surface layer.
- **Trees** after a nuclear blast are **fully charred**, not half-burned.
- Added **baleonitite** block (for balefire explosions).

### Fluids
- **Sellafine** - dedicated fluid with correct texture, radiation (~700 RAD/s in a pool), spawns in the crater **and underground** after a Nuka grenade; spreads without instantly spawning sellafield around it.
- **WATZ mud** - green WATZ reactor fluid: correct color in bucket, canisters, drums, and fluid identifier; hot (~1500°C), pyrophoric.
- **Volcanic lava, corium, radwater** - updated textures, tooltips (temperature in red), pour behavior.
- **Hazardous Material Tank** - container canisters **do not** apply radiation, fire, or other debuffs while carried (they hold hazardous material; they are not the hazard itself).
- Fixed **crash when stacking 64 large canisters** of dangerous fluid.
- Negative fluid effects **do not apply in creative** while testing.

### Inventory Item Contamination
- Clean items (stack size = 1) **build up neutron activation** in strong fields: nuclear blast epicenter, radioactive pools, standing near hot blocks.
- Inventory slots, **armor**, and offhand are affected; the selected hotbar slot is skipped.
- Rate depends on dose and **armor resistance** (star metal slows it down).
- In **creative**, items can still be contaminated (easier to test); player HP remains protected from radiation in creative.
- Option **`7.01_itemContamination`** is **enabled by default** on fresh install; old configs with `false` migrate automatically on first launch.

### Debug
- **`/radvis`** - visualizes radiation “pockets” per chunk (for testing and reading zones).
- **`/hbm rad set`** - manually set radiation in a chunk.

---

## Hazards

- Asbestos, fire, toxicity, hot/cold blocks - verified and working on blocks and items.
- Sellafield blocks emit radiation on **contact** (walking, standing inside the block).
- Mobs on contaminated sand receive the contamination effect (green particles).