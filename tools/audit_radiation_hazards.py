#!/usr/bin/env python3
"""Compare EE vs port RADIATION hazard registrations."""
import re
import csv
from pathlib import Path

ROOT = Path(r"C:\Users\alex\Desktop\hbmport_1.12.2")
EE = ROOT / "мод hbmntm" / "NTM-Extended-GitHub" / "src" / "main" / "java" / "com" / "hbm" / "hazard" / "HazardRegistry.java"
PORT = ROOT / "hbm-x5687-1.12.2" / "src" / "main" / "java" / "com" / "hbm" / "hazard" / "HazardRegistry.java"
MOD_ITEMS = ROOT / "hbm-x5687-1.12.2" / "src" / "main" / "java" / "com" / "hbm" / "items" / "ModItems.java"
MOD_BLOCKS = ROOT / "hbm-x5687-1.12.2" / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "ModBlocks.java"
OUT = ROOT / "hbm-x5687-1.12.2" / "tools" / "radiation_hazard_audit.csv"

CONSTANTS = {
    "wst", "wstv", "trn", "bf", "fo", "yc", "u", "u233", "u235", "u238", "uf", "uzh",
    "pu", "pu238", "pu239", "pu241", "puf", "purg", "th232", "thf", "np237", "npf",
    "mox", "sa326", "sa327", "saf", "mes", "les", "hes", "amf", "amrg", "am241", "am242",
    "po210", "ra226", "ac227", "co60", "sr90", "au198", "pb209", "pobe", "rabe", "pube",
    "zfb_bi", "zfb_pu241", "zfb_am_mix", "ingot", "nugget", "billet", "block", "powder",
    "powder_tiny", "powder_mult", "rod", "rod_dual", "rod_quad", "rod_rbmk", "rtg", "ore",
    "crystal", "trx", "ts", "unof", "ferro", "tcalloy", "magt", "sb", "gh336", "radspice",
    "gen_S", "gen_H",
}

def port_items():
    text = MOD_ITEMS.read_text(encoding="utf-8", errors="replace")
    items = set(re.findall(r"public static final \w+ (\w+)", text))
    text2 = MOD_BLOCKS.read_text(encoding="utf-8", errors="replace")
    blocks = set(re.findall(r"public static final Block(?:\w+)? (\w+)", text2))
    return items | blocks

def normalize_item(raw):
    raw = raw.strip()
    raw = re.sub(r"^new ItemStack\((\w+).*$", r"\1", raw)
    raw = raw.replace("ModItems.", "").replace("ModBlocks.", "")
    raw = raw.replace("Items.", "").replace("Blocks.", "")
    return raw

def eval_expr(expr):
    expr = expr.strip().rstrip("F")
    expr = re.sub(r"(\d)_(\d)", r"\1\2", expr)  # 10_000 -> 10000
    # replace known constants with numeric values from port file header
    # For comparison, keep symbolic normalized form
    return expr

def parse_rads(path):
    text = path.read_text(encoding="utf-8", errors="replace")
    result = {}
    for line in text.splitlines():
        m = re.search(r"registerHazItem\(([^,]+),\s*([^,\)]+)", line)
        if m:
            item = normalize_item(m.group(1))
            rads = m.group(2).strip()
            if re.match(r"^[\d.]+F?$", rads) or re.match(r"^[\d_]+F?$", rads) or any(c in rads for c in CONSTANTS):
                result[item] = rads
            continue
        m = re.search(r"HazardSystem\.register\(([^,]+),\s*makeData\(RADIATION,\s*([^)]+)\)", line)
        if m:
            item = normalize_item(m.group(1))
            result[item] = m.group(2).strip()
            continue
        m = re.search(r"HazardSystem\.register\(([^,]+),\s*makeData\(\)\.addEntry\(RADIATION,\s*([^)]+)\)", line)
        if m:
            item = normalize_item(m.group(1))
            result[item] = m.group(2).strip()
            continue
        m = re.search(r"addEntry\(RADIATION,\s*([^)]+)\)", line)
        if m and "HazardSystem.register" in line:
            # multi-entry on same register line - skip, handled above
            pass
    return result

def main():
    port_set = port_items()
    ee = parse_rads(EE)
    port = parse_rads(PORT)
    rows = []
    for item in sorted(ee.keys()):
        in_port = item in port_set
        ee_val = ee[item]
        port_val = port.get(item)
        if not in_port:
            status = "SKIP_NOT_IN_PORT"
        elif port_val is None:
            status = "MISSING"
        elif port_val.replace(" ", "") != ee_val.replace(" ", ""):
            status = "MISMATCH"
        else:
            status = "OK"
        rows.append((item, ee_val, port_val or "", status))

    OUT.parent.mkdir(parents=True, exist_ok=True)
    with OUT.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["Item", "EE_Rads", "Port_Rads", "Status"])
        w.writerows(rows)

    missing = [r for r in rows if r[3] == "MISSING"]
    mismatch = [r for r in rows if r[3] == "MISMATCH"]
    print(f"Written: {OUT}")
    print(f"MISSING: {len(missing)}")
    print(f"MISMATCH: {len(mismatch)}")
    print(f"OK: {sum(1 for r in rows if r[3] == 'OK')}")
    print("\n--- MISSING (in port) ---")
    for r in missing:
        print(f"  {r[0]}: EE={r[1]}")
    print("\n--- MISMATCH ---")
    for r in mismatch:
        print(f"  {r[0]}: EE={r[1]}  port={r[2]}")

if __name__ == "__main__":
    main()