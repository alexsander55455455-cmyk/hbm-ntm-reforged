#!/usr/bin/env python3
"""Compare block blast resistance in port ModBlocks vs EE reference and optionally patch."""

import argparse
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EE_BLOCKS = ROOT.parent / "мод hbmntm" / "NTM-Extended-GitHub" / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "ModBlocks.java"
PORT_BLOCKS = ROOT / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "ModBlocks.java"

DECL_RE = re.compile(
    r"public\s+static\s+final\s+Block\s+(\w+)\s*=\s*new\s+[^;]+?\.setResistance\(([^)]+)\)",
    re.DOTALL,
)


def parse_resistances(path: Path) -> dict[str, str]:
    text = path.read_text(encoding="utf-8")
    out = {}
    for name, raw in DECL_RE.findall(text):
        out[name] = raw.strip()
    return out


def patch_port(ee: dict[str, str], port_text: str) -> tuple[str, list[tuple[str, str, str]]]:
    changes = []
    for name, ee_val in sorted(ee.items()):
        pattern = re.compile(
            rf"(public\s+static\s+final\s+Block\s+{re.escape(name)}\s*=\s*new\s+[^;]+?\.setResistance\()([^)]+)(\))",
            re.DOTALL,
        )
        match = pattern.search(port_text)
        if not match:
            continue
        port_val = match.group(2).strip()
        if port_val == ee_val:
            continue
        port_text = port_text[: match.start(2)] + ee_val + port_text[match.end(2) :]
        changes.append((name, port_val, ee_val))
    return port_text, changes


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apply", action="store_true", help="Write EE resistance values into port ModBlocks.java")
    args = parser.parse_args()

    if not EE_BLOCKS.is_file() or not PORT_BLOCKS.is_file():
        print("Missing ModBlocks.java reference or port file", file=sys.stderr)
        return 1

    ee = parse_resistances(EE_BLOCKS)
    port = parse_resistances(PORT_BLOCKS)

    diffs = []
    for name in sorted(set(ee) & set(port)):
        if ee[name] != port[name]:
            diffs.append((name, port[name], ee[name]))

    print(f"EE blocks with resistance: {len(ee)}")
    print(f"Port blocks with resistance: {len(port)}")
    print(f"Mismatches in shared blocks: {len(diffs)}")
    for name, old, new in diffs[:40]:
        print(f"  {name}: {old} -> {new}")
    if len(diffs) > 40:
        print(f"  ... and {len(diffs) - 40} more")

    if args.apply and diffs:
        text = PORT_BLOCKS.read_text(encoding="utf-8")
        patched, changes = patch_port(ee, text)
        PORT_BLOCKS.write_text(patched, encoding="utf-8")
        print(f"Applied {len(changes)} resistance updates to {PORT_BLOCKS}")

    return 0


if __name__ == "__main__":
    raise SystemExit(main())