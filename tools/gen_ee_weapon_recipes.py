import re

EE_PATH = r"C:\Users\alex\Desktop\hbmport_1.12.2\мод hbmntm\NTM-Extended-GitHub\src\main\java\com\hbm\main\CraftingManager.java"
PORT_PATH = r"C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\crafting\WeaponRecipes.java"
OUT_PATH = r"C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\crafting\EEWeaponRecipes.java"

with open(EE_PATH, encoding="utf-8") as f:
    ee = f.read()
with open(PORT_PATH, encoding="utf-8") as f:
    port = f.read()

port_guns = set(re.findall(r"ModItems\.(gun_[a-z0-9_]+)", port))

lines_out = []
for m in re.finditer(r"(addRecipeAuto\((new ItemStack\(ModItems\.gun_[^;]+;)\s*)", ee):
    call = m.group(1).strip()
    gun = re.search(r"ModItems\.(gun_[a-z0-9_]+)", call).group(1)
    if gun in port_guns:
        continue
    # skip commented
    if call.strip().startswith("//"):
        continue
    lines_out.append(f"        CraftingManager.{call}")

header = """package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemEnums.EnumCircuitType;
import com.hbm.items.ModItems;
import com.hbm.main.CraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.OreDictManager.*;

/** EE weapon crafts missing from CE WeaponRecipes. */
public class EEWeaponRecipes {

    public static void register() {
"""

footer = """
    }
}
"""

with open(OUT_PATH, "w", encoding="utf-8", newline="\n") as f:
    f.write(header)
    for line in lines_out:
        f.write(line + "\n")
    f.write(footer)

print(f"Wrote {len(lines_out)} weapon recipes")