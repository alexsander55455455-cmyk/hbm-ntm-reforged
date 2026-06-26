import re

EE_PATH = r"C:\Users\alex\Desktop\hbmport_1.12.2\мод hbmntm\NTM-Extended-GitHub\src\main\java\com\hbm\main\CraftingManager.java"
OUT_PATH = r"C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\inventory\recipes\EEAmmoPressRecipes.java"

with open(EE_PATH, encoding="utf-8") as f:
    content = f.read()

start = content.find("addRecipeAuto(new ItemStack(ModItems.gun_bf_ammo")
end = content.find("public static void addBioCrafting")
ammo_section = content[start:end]

recipes = []
ALLOWED = re.compile(
    r"ModItems\.(ammo_[a-z0-9_]+|folly_bullet_nuclear|gun_[a-z0-9_]+_ammo|pellet_flechette)"
)

for m in re.finditer(r"addRecipeAuto\(([^;]+);", ammo_section):
    call = m.group(1).strip()
    if call.endswith(")"):
        call = call[:-1].strip()
    if not ALLOWED.search(call):
        continue
    if "ammo_shell" in call and "ammo_shell_" not in call:
        continue
    recipes.append(call)


def split_top_level(s):
    parts = []
    cur = ""
    depth = 0
    in_str = False
    esc = False
    for ch in s:
        if esc:
            cur += ch
            esc = False
            continue
        if ch == "\\":
            cur += ch
            esc = True
            continue
        if ch == '"':
            in_str = not in_str
            cur += ch
            continue
        if not in_str:
            if ch == "(":
                depth += 1
            elif ch == ")":
                depth -= 1
            elif ch == "," and depth == 0:
                parts.append(cur.strip())
                cur = ""
                continue
        cur += ch
    if cur.strip():
        parts.append(cur.strip())
    return parts


def to_astack(expr):
    expr = expr.strip()
    if expr.startswith("new ItemStack("):
        inner = expr[len("new ItemStack(") : -1]
        return f"new ComparableStack(new ItemStack({inner}))"
    if (
        re.match(r"[A-Z][A-Z0-9_]*\.[a-zA-Z_]+\(\)", expr)
        or expr.startswith("ANY_")
        or expr.startswith("OreDictManager.")
    ):
        return f"new OreDictStack({expr})"
    if expr.startswith("Blocks.") or expr.startswith("Items."):
        return f"new ComparableStack({expr})"
    if expr.startswith("ModBlocks.") or expr.startswith("ModItems."):
        return f"new ComparableStack({expr})"
    if expr.startswith("DictFrame."):
        return f"new ComparableStack({expr})"
    if expr.startswith("Item.getItemFromBlock("):
        return f"new ComparableStack({expr})"
    if expr.startswith("new IngredientNBT2("):
        return None  # skip fluid canister recipes for ammo press
    return f"new ComparableStack({expr})"


def parse_recipe(call):
    parts = split_top_level(call)
    output = parts[0]
    pattern_rows = []
    ingredients = {}
    i = 1
    while i < len(parts):
        p = parts[i]
        if p.startswith('"'):
            pattern_rows.append(p.strip('"'))
            i += 1
        elif p.startswith("'") and len(p) == 3:
            ing = parts[i + 1].strip()
            if ing.endswith(")") and "(" not in ing:
                ing = ing[:-1].strip()
            ingredients[p[1]] = ing
            i += 2
        else:
            i += 1

    grid = []
    for row in pattern_rows:
        row = row.strip()
        if len(row) < 3:
            pad = (3 - len(row)) // 2
            row = (" " * pad) + row + (" " * (3 - len(row) - pad))
        row = row[:3]
        for c in row:
            grid.append(c if c != " " else None)
    while len(grid) < 9:
        grid.append(None)
    grid = grid[:9]

    slots = []
    for c in grid:
        if c is None:
            slots.append("null")
        else:
            ing = ingredients.get(c)
            if ing is None:
                return None
            slot = to_astack(ing)
            if slot is None:
                return None
            slots.append(slot)
    return output, slots


header = """package com.hbm.inventory.recipes;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.AmmoPressRecipes.AmmoPressRecipe;
import com.hbm.items.ItemEnums.EnumCircuitType;
import com.hbm.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

/** EE ammunition recipes ported to Ammo Press (from NTM Extended CraftingManager). */
public class EEAmmoPressRecipes {

    public static void register(List<AmmoPressRecipe> recipes) {
"""

footer = """
    }
}
"""

lines = [header]
seen = set()
skipped = 0
for call in recipes:
    parsed = parse_recipe(call)
    if parsed is None:
        skipped += 1
        continue
    output, slots = parsed
    key = output + "|" + ",".join(slots)
    if key in seen:
        continue
    seen.add(key)

    if output.startswith("new ItemStack("):
        out_expr = output
    else:
        out_expr = f"new ItemStack({output})"

    slot_str = ", ".join(slots)
    lines.append(f"        recipes.add(new AmmoPressRecipe({out_expr}, {slot_str}));\n")

# post-process MAT_STEEL -> Mats.MAT_STEEL for CE port
text = "".join(lines)
text = text.replace("MAT_STEEL.id", "Mats.MAT_STEEL.id")
text = text.replace("OreDictManager.getReflector()", "getReflector()")
if "Mats.MAT_STEEL" in text and "import com.hbm.inventory.material.Mats;" not in text:
    text = text.replace(
        "import com.hbm.items.ModItems;",
        "import com.hbm.inventory.material.Mats;\nimport com.hbm.items.ModItems;",
    )
lines = [text]

lines.append(footer)

with open(OUT_PATH, "w", encoding="utf-8", newline="\n") as f:
    f.writelines(lines)

print(f"Wrote {len(seen)} recipes, skipped {skipped}")