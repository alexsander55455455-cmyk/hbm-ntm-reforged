"""Unit tests for cluster_weapon_tab (shipped in gradle project tools/)."""
from __future__ import annotations

import sys
from pathlib import Path

TOOLS = Path(__file__).resolve().parent
sys.path.insert(0, str(TOOLS))

from cluster_creative_tab_order import (  # noqa: E402
    CE_FIREARM_ORDER,
    CE_FIREARM_PATHS,
    EE_FIREARM_ORDER,
    GOAL_CLUSTER_VERSION,
    cluster_weapon_tab,
    load_weapon_gun_declaration_lines,
    registry_path,
)


def weapon_slice(keys: list[str], start: str, count: int) -> list[str]:
    idx = keys.index(start)
    return keys[idx : idx + count]


def test_revolver_schrabidium_family_adjacent() -> None:
    keys = [
        "gun_revolver_iron",
        "gun_revolver_schrabidium",
        "gun_revolver_schrabidium_ammo",
        "clip_revolver_schrabidium",
        "gun_b92",
        "gun_revolver_iron_ammo",
        "clip_revolver_iron",
    ]
    out = cluster_weapon_tab(keys)
    family = weapon_slice(out, "gun_revolver_schrabidium", 3)
    assert family == [
        "gun_revolver_schrabidium",
        "gun_revolver_schrabidium_ammo",
        "clip_revolver_schrabidium",
    ], family


def test_melee_immediately_after_shimmer_before_meteorite() -> None:
    keys = [
        "hs_sword",
        "hf_sword",
        "shimmer_axe",
        "meteorite_sword",
        "meteorite_sword_seared",
        "mese_pickaxe",
        "mese_axe",
        "dnt_sword",
        "dwarven_pickaxe",
        "mese_gavel",
        "gun_deagle",
    ]
    out = cluster_weapon_tab(keys)
    shimmer = out.index("shimmer_axe")
    assert out[shimmer + 1] == "mese_pickaxe"
    block = out[shimmer + 1 : shimmer + 6]
    assert block == [
        "mese_pickaxe",
        "mese_axe",
        "dnt_sword",
        "dwarven_pickaxe",
        "mese_gavel",
    ], block
    assert out[out.index("mese_gavel") + 1] == "meteorite_sword"
    assert out.index("meteorite_sword_seared") > out.index("meteorite_sword")


def test_ee_firearms_then_ce_firearms() -> None:
    decl = load_weapon_gun_declaration_lines()
    keys = [
        "gun_revolver",
        "gun_b92",
        "gun_fatman",
        "gun_deagle",
        "gun_darter",
        "gun_ar15",
        "crucible",
        "gun_b93",
        "gun_osipr_ammo",
    ]
    out = cluster_weapon_tab(keys)
    paths = [registry_path(k) for k in out]
    ee_sub = [p for p in paths if p in EE_FIREARM_ORDER]
    ce_sub = [p for p in paths if p in CE_FIREARM_PATHS]
    assert ee_sub == [n for n in EE_FIREARM_ORDER if n in paths], (paths, ee_sub)
    assert ce_sub == [n for n in CE_FIREARM_ORDER if n in paths], (paths, ce_sub)
    assert paths.index("gun_darter") < paths.index("gun_b92"), paths
    assert paths.index("gun_b92") == paths.index("gun_darter") + 1, paths
    assert paths.index("gun_osipr_ammo") > paths.index("gun_fatman"), paths
    assert "gun_osipr_ammo2" not in paths[paths.index("gun_darter") : paths.index("gun_fatman") + 1], paths
    assert decl["gun_b92"] < decl["gun_deagle"]


def test_shields_sorted_together() -> None:
    keys = ["desh_shield", "hs_sword", "elec_shield", "cobalt_shield", "steel_shield"]
    out = cluster_weapon_tab(keys)
    shield_idx = [out.index(k) for k in ("cobalt_shield", "desh_shield", "elec_shield", "steel_shield")]
    assert shield_idx == sorted(shield_idx), out
    assert out.index("hs_sword") < shield_idx[0], out


def main() -> int:
    assert GOAL_CLUSTER_VERSION == "creative-tab-grouping-4"
    test_revolver_schrabidium_family_adjacent()
    test_melee_immediately_after_shimmer_before_meteorite()
    test_ee_firearms_then_ce_firearms()
    test_shields_sorted_together()
    print(f"test_cluster_weapon_tab PASS version={GOAL_CLUSTER_VERSION}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())