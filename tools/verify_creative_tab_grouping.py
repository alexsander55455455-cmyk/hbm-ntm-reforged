"""Goal-only verification for creative tab grouping (gradle project tools/)."""
from __future__ import annotations

import os
import sys
from pathlib import Path

PROJ = Path(__file__).resolve().parents[1]
TOOLS = Path(__file__).resolve().parent
SCRATCH = Path(os.environ.get("GOAL_SCRATCH", r"C:\Temp\grok-goal-3cb09aa72ab5\implementer"))
ORDER = PROJ / "src/main/resources/assets/hbm/creative_tab_order.txt"

sys.path.insert(0, str(TOOLS))
from cluster_creative_tab_order import (  # noqa: E402
    CE_FIREARM_ORDER,
    CE_FIREARM_PATHS,
    EE_FIREARM_ORDER,
    GOAL_CLUSTER_VERSION,
    MELEE_CLUSTER_ORDER,
    REVOLVER_FAMILY_PROBES,
    cluster_consumable_tab,
    cluster_parts_tab,
    cluster_weapon_tab,
    registry_path,
)


def load_weapon_tab_keys() -> list[str]:
    keys: list[str] = []
    in_weapon = False
    for raw in ORDER.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        if line.startswith("@weaponTab"):
            in_weapon = True
            continue
        if line.startswith("@") and in_weapon:
            break
        if in_weapon and "=" in line:
            keys.append(line.split("=", 1)[0])
    return keys


def verify_parts_tab_regeneration_parity() -> bool:
    on_disk = load_tab_keys("partsTab")
    if not on_disk:
        print("[GOAL] parts_tab_regeneration_parity=False (empty partsTab)")
        return False
    regenerated = cluster_parts_tab(list(on_disk))
    ok = regenerated == on_disk
    print(f"[GOAL] parts_tab_regeneration_parity={ok} entries={len(on_disk)} version={GOAL_CLUSTER_VERSION}")
    return ok


def verify_consumable_tab_regeneration_parity() -> bool:
    on_disk = load_tab_keys("consumableTab")
    if not on_disk:
        print("[GOAL] consumable_tab_regeneration_parity=False (empty consumableTab)")
        return False
    regenerated = cluster_consumable_tab(list(on_disk))
    ok = regenerated == on_disk
    print(f"[GOAL] consumable_tab_regeneration_parity={ok} entries={len(on_disk)} version={GOAL_CLUSTER_VERSION}")
    return ok


def verify_weapon_tab_regeneration_parity() -> bool:
    on_disk = load_weapon_tab_keys()
    if not on_disk:
        print("[GOAL] weapon_tab_regeneration_parity=False (empty weaponTab)")
        return False
    regenerated = cluster_weapon_tab(list(on_disk))
    ok = regenerated == on_disk
    print(f"[GOAL] weapon_tab_regeneration_parity={ok} entries={len(on_disk)} version={GOAL_CLUSTER_VERSION}")
    return ok


def load_tab_keys(tab_name: str) -> list[str]:
    keys: list[str] = []
    in_tab = False
    for raw in ORDER.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        if line == f"@{tab_name}":
            in_tab = True
            continue
        if line.startswith("@") and in_tab:
            break
        if in_tab and "=" in line:
            keys.append(line.split("=", 1)[0])
    return keys


def verify_parts_prefix_probes() -> bool:
    parts = load_tab_keys("partsTab")
    ok = True
    for prefix in ("ingot_", "nugget_", "powder_"):
        hits = [i for i, k in enumerate(parts) if registry_path(k).startswith(prefix)]
        if len(hits) > 1 and hits[-1] - hits[0] + 1 != len(hits):
            ok = False
            print(f"[GOAL] partsTab {prefix} not contiguous span={hits[-1]-hits[0]+1} count={len(hits)}")
    probes = {
        name: parts.index(name) if name in parts else -1
        for name in (
            "ingot_ac227",
            "ingot_neodymium",
            "ingot_radspice",
            "ingot_strontium",
            "nugget_ac227",
            "nugget_radspice",
            "powder_ac227",
            "powder_radspice",
            "inf_water_mk4",
        )
    }
    ingot_hits = [i for i, k in enumerate(parts) if registry_path(k).startswith("ingot_")]
    nugget_hits = [i for i, k in enumerate(parts) if registry_path(k).startswith("nugget_")]
    powder_hits = [i for i, k in enumerate(parts) if registry_path(k).startswith("powder_")]
    for name in ("ingot_ac227", "ingot_neodymium", "ingot_radspice", "ingot_strontium"):
        idx = probes[name]
        if idx < 0 or idx < ingot_hits[0] or idx > ingot_hits[-1]:
            ok = False
    for name in ("nugget_ac227", "nugget_radspice"):
        idx = probes.get(name, -1)
        if idx < 0 or idx < nugget_hits[0] or idx > nugget_hits[-1]:
            ok = False
    for name in ("powder_ac227", "powder_radspice"):
        idx = probes.get(name, -1)
        if idx < 0 or idx < powder_hits[0] or idx > powder_hits[-1]:
            ok = False
    if probes["inf_water_mk4"] in ingot_hits:
        ok = False
    if ingot_hits:
        ingot_paths = [registry_path(parts[i]) for i in ingot_hits]
        if ingot_paths != sorted(ingot_paths):
            ok = False
            print(f"[GOAL] ingot_block_not_alpha_sorted sample={ingot_paths[:5]}..{ingot_paths[-3:]}")
    if probes["inf_water_mk4"] >= 0 and ingot_hits and probes["inf_water_mk4"] <= ingot_hits[-1]:
        ok = False
    print(f"[GOAL] parts_probes={probes} ok={ok}")
    return ok


def verify_consumable_filters() -> bool:
    consumable = load_tab_keys("consumableTab")
    hits = [i for i, k in enumerate(consumable) if registry_path(k).startswith("gas_mask_filter")]
    ok = len(hits) > 0 and hits[-1] - hits[0] + 1 == len(hits)
    radon = consumable.index("gas_mask_filter_radon") if "gas_mask_filter_radon" in consumable else -1
    ok = ok and radon >= 0 and radon in hits
    print(f"[GOAL] gas_mask_filter_contiguous={ok} radon={radon} block={hits[:3]}..{hits[-3:]}")
    return ok


def is_weapon_firearm_path(path: str) -> bool:
    if path.startswith("gun_revolver") and path != "gun_revolver_inverted":
        return False
    if path.startswith("gun_") and not path.endswith("_ammo"):
        return True
    return path in {"cc_plasma_gun", "crucible", "drax", "drax_mk2", "drax_mk3"}


def verify_ee_ce_firearm_boundary() -> bool:
    weapon = load_weapon_tab_keys()
    paths = [registry_path(k) for k in weapon]
    firearm_indices = [i for i, p in enumerate(paths) if is_weapon_firearm_path(p)]
    if not firearm_indices:
        print("[GOAL] ee_ce_boundary=False no firearms on weaponTab")
        return False

    ee_indices = [i for i, p in enumerate(paths) if p in EE_FIREARM_ORDER]
    ce_indices = [i for i, p in enumerate(paths) if p in CE_FIREARM_PATHS]
    if not ee_indices:
        print("[GOAL] ee_ce_boundary=False missing EE firearms")
        return False
    if not ce_indices:
        print("[GOAL] ee_ce_boundary=False missing CE firearms")
        return False

    last_ee = max(ee_indices)
    first_ce = min(ce_indices)
    boundary_ok = first_ce == last_ee + 1
    between = paths[last_ee + 1 : first_ce]
    between_ok = not between

    ee_sub = [p for p in paths if p in EE_FIREARM_ORDER]
    ee_order_ok = ee_sub == [n for n in EE_FIREARM_ORDER if n in paths]
    ce_sub = [p for p in paths if p in CE_FIREARM_PATHS]
    ce_order_ok = ce_sub == [n for n in CE_FIREARM_ORDER if n in paths]

    ok = boundary_ok and between_ok and ee_order_ok and ce_order_ok
    print(
        f"[GOAL] ee_ce_boundary last_ee={last_ee}({paths[last_ee]}) "
        f"first_ce={first_ce}({paths[first_ce]}) boundary_ok={boundary_ok} "
        f"ee_order_ok={ee_order_ok} ce_order_ok={ce_order_ok} ce_count={len(ce_sub)}"
    )
    return ok


def check_creative_grouping_goal() -> bool:
    weapon = load_weapon_tab_keys()
    ok = True
    for family in REVOLVER_FAMILY_PROBES:
        gun_idx = weapon.index(family[0]) if family[0] in weapon else -1
        if gun_idx < 0:
            ok = False
            print(f"[GOAL] revolver_family missing {family[0]}")
            continue
        expected = gun_idx + 1
        for part in family[1:]:
            part_idx = weapon.index(part) if part in weapon else -1
            if part_idx != expected:
                ok = False
                print(f"[GOAL] revolver_family broken {family[0]} {part} at={part_idx} expected={expected}")
            expected += 1
    probes = {name: weapon.index(name) if name in weapon else -1 for name in (
        "hs_sword", "shimmer_axe", "mese_pickaxe", "mese_gavel", "meteorite_sword", "meteorite_sword_seared"
    )}
    melee_ok = all(v >= 0 for v in probes.values())
    if melee_ok:
        melee_ok = probes["shimmer_axe"] + 1 == probes["mese_pickaxe"]
        expected = probes["mese_pickaxe"]
        for name in MELEE_CLUSTER_ORDER:
            if weapon.index(name) != expected:
                melee_ok = False
                break
            expected += 1
        melee_ok = melee_ok and probes["meteorite_sword"] == probes["mese_gavel"] + 1
        melee_ok = melee_ok and probes["meteorite_sword_seared"] > probes["meteorite_sword"]
    print(f"[GOAL] melee_probes={probes} ok={melee_ok}")
    assembly_hidden = "assembly_template" not in weapon
    for tab_section in ORDER.read_text(encoding="utf-8").split("@"):
        if "assembly_template=" in tab_section:
            assembly_hidden = False
            break
    print(f"[GOAL] assembly_template_hidden={assembly_hidden}")
    shield_hits = [i for i, k in enumerate(weapon) if registry_path(k).endswith("_shield")]
    shield_ok = len(shield_hits) > 0 and shield_hits[-1] - shield_hits[0] + 1 == len(shield_hits)
    print(f"[GOAL] shield_block_contiguous={shield_ok} count={len(shield_hits)}")
    parity_ok = (
        verify_parts_tab_regeneration_parity()
        and verify_consumable_tab_regeneration_parity()
        and verify_weapon_tab_regeneration_parity()
    )
    ok = (
        ok
        and melee_ok
        and assembly_hidden
        and parity_ok
        and shield_ok
        and verify_parts_prefix_probes()
        and verify_consumable_filters()
        and verify_ee_ce_firearm_boundary()
    )
    print(f"[GOAL] creative_grouping_pass={ok}")
    return ok


def main() -> int:
    SCRATCH.mkdir(parents=True, exist_ok=True)
    ok = check_creative_grouping_goal()
    (SCRATCH / "verify-goal-result.txt").write_text(
        f"verify_creative_tab_grouping.py version={GOAL_CLUSTER_VERSION}\n"
        f"creative_grouping_pass={ok}\n",
        encoding="utf-8",
    )
    return 0 if ok else 1


if __name__ == "__main__":
    raise SystemExit(main())