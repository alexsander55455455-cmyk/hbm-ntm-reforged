"""Re-cluster creative_tab_order.txt: revolver families (gun→ammo→clip) and melee ordering.

Shipped in gradle project tools/ — GOAL_CLUSTER_VERSION=creative-tab-grouping-4
"""
from __future__ import annotations

import os
import re
import sys
from collections import defaultdict
from pathlib import Path

PROJ = Path(__file__).resolve().parents[1]
TOOLS = Path(__file__).resolve().parent
ORDER = PROJ / "src/main/resources/assets/hbm/creative_tab_order.txt"
MOD_ITEMS = PROJ / "src/main/java/com/hbm/items/ModItems.java"
MOD_BLOCKS = PROJ / "src/main/java/com/hbm/blocks/ModBlocks.java"
SCRATCH = Path(os.environ.get("GOAL_SCRATCH", r"C:\Temp\grok-goal-3cb09aa72ab5\implementer"))
SCRATCH.mkdir(parents=True, exist_ok=True)

sys.path.insert(0, str(TOOLS))
from creative_tab_parse import collect_port_tab_entries  # noqa: E402

GOAL_CLUSTER_VERSION = "creative-tab-grouping-4"

EE_PORT_BLOCK_LINE = 3264

# CE / x5687 firearms declared before the EE port block in ModItems.java.
CE_FIREARM_ORDER = (
    "gun_b92",
    "gun_b93",
    "gun_supershotgun",
    "gun_jshotgun",
    "gun_vortex",
    "gun_egon",
    "gun_moist_nugget",
    "crucible",
    "gun_fatman",
)

CE_FIREARM_PATHS = frozenset(CE_FIREARM_ORDER)

# Extended Edition port-block firearms (line >= EE_PORT_BLOCK_LINE), EE reference order.
EE_FIREARM_ORDER = (
    "gun_deagle",
    "gun_flechette",
    "gun_ar15",
    "gun_uboinik",
    "gun_ks23",
    "gun_sauer",
    "gun_calamity",
    "gun_calamity_dual",
    "gun_minigun",
    "gun_avenger",
    "gun_lacunae",
    "gun_bolt_action",
    "gun_bolt_action_green",
    "gun_uzi",
    "gun_uzi_silencer",
    "gun_uzi_saturnite",
    "gun_uzi_saturnite_silencer",
    "gun_mp40",
    "gun_thompson",
    "gun_rpg",
    "gun_karl",
    "gun_panzerschreck",
    "gun_quadro",
    "gun_lever_action",
    "gun_lever_action_dark",
    "gun_hk69",
    "gun_spark",
    "gun_proto",
    "gun_mirv",
    "gun_bf",
    "gun_zomg",
    "gun_xvl1456",
    "gun_hp",
    "gun_defabricator",
    "cc_plasma_gun",
    "gun_euthanasia",
    "gun_stinger",
    "gun_skystinger",
    "gun_mp",
    "gun_bolter",
    "gun_cryolator",
    "gun_jack",
    "gun_immolator",
    "gun_flamer",
    "gun_osipr",
    "gun_emp",
    "gun_super_shotgun",
    "gun_revolver_inverted",
    "gun_lever_action_sonata",
    "gun_bolt_action_saturnite",
    "gun_folly",
    "gun_dampfmaschine",
    "gun_darter",
)

CONSUMABLE_PREFIX_GROUPS = [
    "gas_mask_filter_",
    "gas_mask_filter",
]

WEAPON_TAB_MELEE = frozenset({
    "mese_pickaxe",
    "mese_axe",
    "dnt_sword",
    "dwarven_pickaxe",
})

MELEE_HEAD_ORDER = ("hs_sword", "hf_sword", "shimmer_axe")
MELEE_CLUSTER_ORDER = ("mese_pickaxe", "mese_axe", "dnt_sword", "dwarven_pickaxe", "mese_gavel")

REVOLVER_FAMILY_PROBES = (
    ("gun_revolver", "gun_revolver_ammo", "clip_revolver"),
    ("gun_revolver_iron", "gun_revolver_iron_ammo", "clip_revolver_iron"),
    ("gun_revolver_schrabidium", "gun_revolver_schrabidium_ammo", "clip_revolver_schrabidium"),
)

WEAPON_TAB_SHIELDS = {
    "alloy_shield",
    "cmb_shield",
    "cobalt_shield",
    "desh_shield",
    "elec_shield",
    "schrabidium_shield",
    "starmetal_shield",
    "steel_shield",
    "titanium_shield",
}

CONTROL_TAB_PELLETS = {
    "pellet_mercury",
    "pellet_rtg_depleted_bismuth",
    "pellet_rtg_depleted_lead",
    "pellet_rtg_depleted_mercury",
    "pellet_rtg_depleted_neptunium",
    "pellet_rtg_depleted_zirconium",
}

PARTS_PREFIX_GROUPS = [
    "ingot_",
    "nugget_",
    "powder_",
    "billet_",
    "gem_",
    "crystal_",
    "plate_",
    "wire_",
    "bolt_",
    "part_",
    "pellet_",
    "dust_",
    "scrap_",
]


def registry_path(key: str) -> str:
    return key.split(":", 1)[-1] if ":" in key else key


def alpha_sort_keys(keys: list[str]) -> list[str]:
    return sorted(keys, key=registry_path)


def order_by_template(keys: list[str], template: tuple[str, ...]) -> list[str]:
    by_path = {registry_path(k): k for k in keys}
    out: list[str] = []
    placed: set[str] = set()
    for name in template:
        key = by_path.get(name)
        if key and name not in placed:
            out.append(key)
            placed.add(name)
    for key in alpha_sort_keys(keys):
        if registry_path(key) not in placed:
            out.append(key)
    return out


def load_weapon_gun_declaration_lines() -> dict[str, int]:
    from creative_tab_parse import extract_registry_path, field_declaration_chunk

    text = MOD_ITEMS.read_text(encoding="utf-8")
    lines: dict[str, int] = {}
    pat = r"public static final (?:Item|Item\w+)\s+(\w+)\s*=\s*"
    for m in re.finditer(pat, text):
        field = m.group(1)
        chunk = field_declaration_chunk(text, m.start())
        if "MainRegistry.weaponTab" not in chunk:
            continue
        path = extract_registry_path(chunk, field)
        if not path:
            continue
        line_no = text[: m.start()].count("\n") + 1
        lines.setdefault(path, line_no)
    return lines


def is_gun_ammo_path(path: str) -> bool:
    return path.startswith("gun_") and "_ammo" in path


def is_firearm_path(path: str) -> bool:
    if path.startswith("gun_revolver") and path != "gun_revolver_inverted":
        return False
    if is_gun_ammo_path(path):
        return False
    if path.startswith("gun_"):
        return True
    return path in {"cc_plasma_gun", "crucible", "drax", "drax_mk2", "drax_mk3"}


def split_ee_ce_firearms(guns: list[str], decl_lines: dict[str, int]) -> tuple[list[str], list[str]]:
    """EE port-block guns first (EE reference order), then CE early-declared guns."""
    ee_pool = [k for k in guns if decl_lines.get(registry_path(k), 0) >= EE_PORT_BLOCK_LINE]
    ce_pool = [k for k in guns if decl_lines.get(registry_path(k), 10_000) < EE_PORT_BLOCK_LINE]
    ee_block = order_by_template(ee_pool, EE_FIREARM_ORDER)
    ce_block = order_by_template(ce_pool, CE_FIREARM_ORDER)
    return ee_block, ce_block


def revolver_suffix(gun_path: str) -> str:
    if gun_path == "gun_revolver":
        return ""
    return gun_path[len("gun_revolver") :]


def matching_revolver_ammo_path(suffix: str) -> str:
    if suffix == "":
        return "gun_revolver_ammo"
    return f"gun_revolver{suffix}_ammo"


def matching_revolver_clip_path(suffix: str) -> str:
    if suffix == "":
        return "clip_revolver"
    return f"clip_revolver{suffix}"


def build_revolver_families(
    revolvers: list[str],
    gun_ammo: list[str],
    clips: list[str],
) -> tuple[list[str], list[str], list[str]]:
    gun_ammo_by_path = {registry_path(k): k for k in gun_ammo}
    clip_by_path = {registry_path(k): k for k in clips}
    used: set[str] = set()
    families: list[str] = []

    for gun_key in sorted(revolvers, key=registry_path):
        gun_path = registry_path(gun_key)
        suffix = revolver_suffix(gun_path)
        families.append(gun_key)
        used.add(gun_key)

        ammo_path = matching_revolver_ammo_path(suffix)
        ammo_key = gun_ammo_by_path.get(ammo_path)
        if ammo_key and ammo_key not in used:
            families.append(ammo_key)
            used.add(ammo_key)

        clip_path = matching_revolver_clip_path(suffix)
        clip_key = clip_by_path.get(clip_path)
        if clip_key and clip_key not in used:
            families.append(clip_key)
            used.add(clip_key)

    remaining_gun_ammo = [k for k in gun_ammo if k not in used]
    remaining_clips = [k for k in clips if k not in used]
    return families, remaining_gun_ammo, remaining_clips


def meteorite_sort_key(key: str) -> tuple[int, str]:
    path = registry_path(key)
    if path == "meteorite_sword":
        return (0, path)
    return (1, path)


def order_melee_section(swords: list[str], melee_tools: list[str]) -> list[str]:
    by_path = {registry_path(k): k for k in swords + melee_tools}
    head = [by_path[name] for name in MELEE_HEAD_ORDER if name in by_path]
    cluster = [by_path[name] for name in MELEE_CLUSTER_ORDER if name in by_path]
    meteorite_block = sorted(
        (k for k in swords if registry_path(k) == "meteorite_sword" or registry_path(k).startswith("meteorite_sword_")),
        key=meteorite_sort_key,
    )
    placed = {registry_path(k) for k in head + cluster + meteorite_block}
    other = [k for k in swords if registry_path(k) not in placed]
    return head + cluster + meteorite_block + other


def cluster_weapon_tab(keys: list[str]) -> list[str]:
    """Reorder weaponTab: revolvers, EE firearms, CE firearms, ammo, melee, shields."""
    revolvers: list[str] = []
    guns: list[str] = []
    gun_ammo: list[str] = []
    clips: list[str] = []
    ammo: list[str] = []
    swords: list[str] = []
    gavels: list[str] = []
    shields: list[str] = []
    melee_tools: list[str] = []
    grenades: list[str] = []
    turrets: list[str] = []
    other: list[str] = []

    for key in keys:
        path = registry_path(key)
        if path.startswith("gun_revolver") and not path.endswith("_ammo") and path != "gun_revolver_inverted":
            revolvers.append(key)
        elif path in WEAPON_TAB_MELEE or path == "mese_gavel":
            melee_tools.append(key)
        elif path.endswith("_shield") or path == "alloy_shield":
            shields.append(key)
        elif path.endswith("_gavel") or path == "schrabidium_hammer":
            gavels.append(key)
        elif is_gun_ammo_path(path):
            gun_ammo.append(key)
        elif is_firearm_path(path):
            guns.append(key)
        elif path.startswith("clip_revolver"):
            clips.append(key)
        elif path.startswith("ammo_") or path.startswith("clip_"):
            ammo.append(key)
        elif path.endswith("_sword") or path.endswith("_pickaxe") or path.endswith("_axe") or path.startswith("meteorite_sword"):
            swords.append(key)
        elif path.startswith("grenade_") or path.startswith("weaponized_"):
            grenades.append(key)
        elif path.startswith("turret_") or path.startswith("disperser_") or path.startswith("glyphid_"):
            turrets.append(key)
        else:
            other.append(key)

    revolver_block, gun_ammo, clips = build_revolver_families(revolvers, gun_ammo, clips)
    decl_lines = load_weapon_gun_declaration_lines()
    ee_firearms, ce_firearms = split_ee_ce_firearms(guns, decl_lines)
    melee_block = order_melee_section(swords, melee_tools)

    return (
        revolver_block
        + ee_firearms
        + ce_firearms
        + gun_ammo
        + ammo
        + clips
        + melee_block
        + gavels
        + alpha_sort_keys(shields)
        + grenades
        + turrets
        + other
    )


def load_tabs(path: Path) -> dict[str, list[str]]:
    tabs: dict[str, list[str]] = {}
    current = None
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        if line.startswith("@"):
            current = line[1:]
            tabs[current] = []
            continue
        key = line.split("=", 1)[0].strip()
        if current:
            tabs[current].append(key)
    return tabs


def write_tabs(path: Path, tabs: dict[str, list[str]]) -> None:
    lines: list[str] = [
        f"# {GOAL_CLUSTER_VERSION} shipped cluster_creative_tab_order.py (hbm-x5687-1.12.2/tools)",
    ]
    for tab in sorted(tabs.keys()):
        lines.append(f"@{tab}")
        for i, key in enumerate(tabs[tab]):
            lines.append(f"{key}={i}")
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def cluster_by_prefix(keys: list[str], prefix_groups: list[str]) -> list[str]:
    buckets: dict[str, list[str]] = {p: [] for p in prefix_groups}
    buckets["__other__"] = []
    for key in keys:
        path = registry_path(key)
        placed = False
        for prefix in prefix_groups:
            if path.startswith(prefix):
                buckets[prefix].append(key)
                placed = True
                break
        if not placed:
            buckets["__other__"].append(key)

    out: list[str] = []
    for prefix in prefix_groups:
        out.extend(alpha_sort_keys(buckets[prefix]))
    out.extend(buckets["__other__"])
    return out


def cluster_parts_tab(keys: list[str]) -> list[str]:
    """Cluster partsTab: prefix families (ingot_, nugget_, powder_, ...) alpha-sorted within each bucket."""
    return cluster_by_prefix(keys, PARTS_PREFIX_GROUPS)


def cluster_consumable_tab(keys: list[str]) -> list[str]:
    filter_keys = [k for k in keys if registry_path(k).startswith("gas_mask_filter")]
    if not filter_keys:
        return keys
    filters = alpha_sort_keys(filter_keys)
    out: list[str] = []
    inserted = False
    for key in keys:
        if registry_path(key).startswith("gas_mask_filter"):
            if not inserted:
                out.extend(filters)
                inserted = True
            continue
        out.append(key)
    return out


def load_hidden_registry_paths() -> set[str]:
    from creative_tab_parse import extract_registry_path, field_declaration_chunk

    hidden: set[str] = set()
    text = MOD_ITEMS.read_text(encoding="utf-8")
    pat = r"public static final (?:Item|Item\w+)\s+(\w+)\s*=\s*"
    for m in re.finditer(pat, text):
        field = m.group(1)
        chunk = field_declaration_chunk(text, m.start())
        if not re.search(r"\.setCreativeTab\(\s*null\s*\)", chunk):
            continue
        path = extract_registry_path(chunk, field)
        if path:
            hidden.add(path)
    return hidden


def purge_hidden_items(tabs: dict[str, list[str]], hidden: set[str]) -> list[str]:
    removed: list[str] = []
    for tab, keys in list(tabs.items()):
        kept: list[str] = []
        for key in keys:
            if registry_path(key) in hidden:
                removed.append(key)
            else:
                kept.append(key)
        tabs[tab] = kept
    return removed


def load_declared_tabs() -> dict[str, str]:
    declared: dict[str, str] = {}
    port_tabs = collect_port_tab_entries(MOD_ITEMS, MOD_BLOCKS)
    for tab, entries in port_tabs.items():
        for registry_key, path in entries:
            key = registry_path(registry_key)
            declared[key] = tab
    return declared


def sync_tab_membership(tabs: dict[str, list[str]], declared: dict[str, str]) -> list[tuple[str, str]]:
    inserted: list[tuple[str, str]] = []
    for key, tab in declared.items():
        if tab not in tabs:
            tabs[tab] = []
        present = set(tabs[tab])
        if key not in present and f"hbm:{key}" not in present:
            tabs[tab].append(key)
            inserted.append((tab, key))
    return inserted


def reconcile_tabs_with_declarations(tabs: dict[str, list[str]], declared: dict[str, str]) -> None:
    misplaced: dict[str, list[str]] = defaultdict(list)
    for tab, keys in list(tabs.items()):
        keep: list[str] = []
        for key in keys:
            path = registry_path(key)
            want = declared.get(path) or declared.get(key)
            if want and want != tab:
                misplaced[want].append(key)
            else:
                keep.append(key)
        tabs[tab] = keep
    for tab, keys in misplaced.items():
        if tab not in tabs:
            tabs[tab] = []
        for key in keys:
            if key not in tabs[tab] and registry_path(key) not in {registry_path(k) for k in tabs[tab]}:
                tabs[tab].append(key)


def apply_cross_tab_moves(tabs: dict[str, list[str]]) -> None:
    parts = tabs.get("partsTab", [])
    weapon = tabs.get("weaponTab", [])
    control = tabs.get("controlTab", [])

    moved_melee = [k for k in parts if registry_path(k) in WEAPON_TAB_MELEE]
    for k in moved_melee:
        parts.remove(k)
        if k not in weapon:
            weapon.append(k)

    moved_shields = [k for k in parts if k in WEAPON_TAB_SHIELDS]
    for k in moved_shields:
        parts.remove(k)
        if k not in weapon:
            weapon.append(k)

    moved_pellets_parts = [k for k in parts if k in CONTROL_TAB_PELLETS]
    for k in moved_pellets_parts:
        parts.remove(k)
        if k not in control:
            control.append(k)

    moved_pellets_weapon = [k for k in weapon if k in CONTROL_TAB_PELLETS]
    for k in moved_pellets_weapon:
        weapon.remove(k)
        if k not in control:
            control.append(k)

    tabs["partsTab"] = parts
    tabs["weaponTab"] = weapon
    tabs["controlTab"] = control


def audit_contiguity(keys: list[str], prefix: str) -> list[tuple[str, int]]:
    hits = [(i, k) for i, k in enumerate(keys) if registry_path(k).startswith(prefix)]
    if len(hits) <= 1:
        return []
    gaps = []
    for j in range(1, len(hits)):
        prev_i, prev_k = hits[j - 1]
        cur_i, cur_k = hits[j]
        if cur_i != prev_i + 1:
            gaps.append((prev_k, cur_i - prev_i - 1))
    return gaps


def main() -> None:
    declared = load_declared_tabs()
    tabs = load_tabs(ORDER)
    inserted = sync_tab_membership(tabs, declared)
    reconcile_tabs_with_declarations(tabs, declared)
    removed = purge_hidden_items(tabs, load_hidden_registry_paths())
    apply_cross_tab_moves(tabs)

    tabs["partsTab"] = cluster_parts_tab(tabs["partsTab"])
    tabs["consumableTab"] = cluster_consumable_tab(tabs.get("consumableTab", []))
    tabs["weaponTab"] = cluster_weapon_tab(tabs["weaponTab"])

    control = tabs.get("controlTab", [])
    rtg = [k for k in control if k.startswith("pellet_rtg") or k in CONTROL_TAB_PELLETS]
    non_rtg = [k for k in control if k not in rtg]
    tabs["controlTab"] = rtg + non_rtg

    write_tabs(ORDER, tabs)

    report_lines = [f"cluster_creative_tab_order.py audit version={GOAL_CLUSTER_VERSION}\n"]
    if inserted:
        report_lines.append(f"sync_tab_membership inserted={len(inserted)}")
        for tab, key in inserted[:20]:
            report_lines.append(f"  +{tab} {key}")
    else:
        report_lines.append("sync_tab_membership inserted=0")
    if removed:
        report_lines.append(f"purge_hidden removed={len(removed)}")
        for key in removed[:10]:
            report_lines.append(f"  -{key}")
    for family in ("gun_revolver_schrabidium", "gun_revolver_iron"):
        weapon = tabs.get("weaponTab", [])
        if family in weapon:
            idx = weapon.index(family)
            report_lines.append(f"  revolver_family_{family}={weapon[idx:idx + 4]}")
    for tab, keys in sorted(tabs.items()):
        for prefix in ("ingot_", "powder_", "nugget_", "gun_revolver", "gas_mask_filter"):
            gaps = audit_contiguity(keys, prefix)
            report_lines.append(f"{tab} {prefix} gaps={len(gaps)}")
            if gaps:
                report_lines.append(f"  sample_gap_after={gaps[0]}")
        if tab == "partsTab":
            for probe in ("ingot_ac227", "ingot_neodymium", "ingot_radspice", "ingot_strontium", "inf_water_mk4"):
                if probe in keys:
                    report_lines.append(f"  partsTab {probe} index={keys.index(probe)}")
        if tab == "weaponTab":
            paths = [registry_path(k) for k in keys]
            ee_idx = [i for i, p in enumerate(paths) if p in EE_FIREARM_ORDER]
            ce_idx = [i for i, p in enumerate(paths) if p in CE_FIREARM_PATHS]
            if ee_idx and ce_idx:
                last_ee = max(ee_idx)
                first_ce = min(ce_idx)
                report_lines.append(
                    f"  ee_ce_boundary last_ee={last_ee}({paths[last_ee]}) "
                    f"first_ce={first_ce}({paths[first_ce]}) contiguous={first_ce == last_ee + 1}"
                )
                report_lines.append(f"  ce_firearms={[paths[i] for i in ce_idx]}")
            shield_hits = [(i, k) for i, k in enumerate(keys) if registry_path(k).endswith("_shield")]
            if shield_hits:
                report_lines.append(
                    f"  weapon_shields count={len(shield_hits)} first={shield_hits[0][0]} last={shield_hits[-1][0]}"
                )
            for name in sorted(WEAPON_TAB_SHIELDS):
                if name in keys:
                    report_lines.append(f"  {name} index={keys.index(name)}")
    out = SCRATCH / "sort-audit.txt"
    out.write_text("\n".join(report_lines) + "\n", encoding="utf-8")
    print(out.read_text(encoding="utf-8"))


if __name__ == "__main__":
    main()