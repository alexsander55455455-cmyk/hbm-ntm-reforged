#!/usr/bin/env python3
"""Report vanilla block H/R targets for VanillaBlockPatcher (EE jar has no vanilla bytecode patches)."""

import csv
import re
import sys
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
PATCHER = ROOT / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "VanillaBlockPatcher.java"
EE_JAR = ROOT.parent / "мод hbmntm" / "_NTM-Extended-1.12.2-3.0.3.jar"
REPORT = ROOT / "tools" / "vanilla_block_stats_report.csv"

PATCH_RE = re.compile(r"patch\(Blocks\.(\w+),\s*([^,]+),\s*([^)]+)\)")


def jar_has_vanilla_patches() -> bool:
    if not EE_JAR.is_file():
        return False
    with zipfile.ZipFile(EE_JAR) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            data = zf.read(name)
            if b"Blocks" in data and b"setResistance" in data and b"OBSIDIAN" in data:
                return True
    return False


def main() -> int:
    if not PATCHER.is_file():
        print("Missing VanillaBlockPatcher.java", file=sys.stderr)
        return 1

    text = PATCHER.read_text(encoding="utf-8")
    rows = PATCH_RE.findall(text)

    REPORT.parent.mkdir(parents=True, exist_ok=True)
    with REPORT.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["block", "hardness", "resistance", "source"])
        for block, h, r in rows:
            w.writerow([block, h.strip(), r.strip(), "VanillaBlockPatcher / EE in-game reference"])

    print(f"EE jar vanilla bytecode patches found: {jar_has_vanilla_patches()}")
    print(f"Vanilla targets in patcher: {len(rows)}")
    print(f"Report: {REPORT}")
    for row in rows:
        print(f"  {row[0]}: H={row[1].strip()}, R={row[2].strip()}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())