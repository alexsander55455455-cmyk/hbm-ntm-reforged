param(
    [string]$PortFile = "src\main\java\com\hbm\inventory\recipes\SILEXRecipes.java",
    [string]$EeFile = ""
)

$root = Split-Path $PSScriptRoot -Parent
$portPath = Join-Path $root $PortFile

if ([string]::IsNullOrWhiteSpace($EeFile)) {
    $workspace = Split-Path $root -Parent
    $candidates = Get-ChildItem -Path $workspace -Directory -Recurse -ErrorAction SilentlyContinue |
        Where-Object { $_.Name -eq "NTM-Extended-GitHub" } |
        Select-Object -First 1
    if ($candidates) {
        $EeFile = Join-Path $candidates.FullName "src\main\java\com\hbm\inventory\SILEXRecipes.java"
    }
}

$eePath = if ($EeFile -and [System.IO.Path]::IsPathRooted($EeFile)) { $EeFile } elseif ($EeFile) { Join-Path $root $EeFile } else { $null }

function Get-SilexKeys($path) {
    $content = Get-Content $path -Raw -Encoding UTF8
    $keys = [ordered]@{}
    $pattern = 'recipes\.put\(([^,]+),'
    foreach ($m in [regex]::Matches($content, $pattern)) {
        $key = $m.Groups[1].Value.Trim()
        if ($key -notmatch 'ComparableStack|\.ingot\(\)|\.crystal\(\)|\.gem\(\)|Blocks\.|OreDictStack|fluid_icon') { continue }
        if (-not $keys.Contains($key)) { $keys[$key] = 0 }
        $keys[$key]++
    }
    return $keys
}

function Get-WavelengthCounts($path) {
    $content = Get-Content $path -Raw -Encoding UTF8
    $counts = @{}
    foreach ($wl in [regex]::Matches($content, 'EnumWavelengths\.([A-Z]+)')) {
        $name = $wl.Groups[1].Value
        if (-not $counts.ContainsKey($name)) { $counts[$name] = 0 }
        $counts[$name]++
    }
    return $counts
}

if (-not (Test-Path $portPath)) { Write-Error "Port file not found: $portPath"; exit 1 }
if (-not $eePath -or -not (Test-Path $eePath)) { Write-Error "EE file not found: $eePath"; exit 1 }

$portKeys = Get-SilexKeys $portPath
$eeKeys = Get-SilexKeys $eePath

$exclude = @(
    'new ComparableStack(ModBlocks.ore_tikite, 1)',
    'new ComparableStack(ModItems.crystal_trixite)',
    'new ComparableStack(ModItems.crystal_schraranium)',
    'new ComparableStack(ModItems.fluid_icon, 1, Fluids.DEATH.getID())',
    'new ComparableStack(ModItems.fluid_icon, 1, Fluids.VITRIOL.getID())',
    'new ComparableStack(ModItems.fluid_icon, 1, Fluids.REDMUD.getID())',
    'new ComparableStack(ModItems.fluid_icon, 1, Fluids.FULLERENE.getID())',
    'new ComparableStack(ModItems.rbmk_pellet_unobtainium, 1, i)',
    'new ComparableStack(ModItems.rbmk_pellet_unobtainium, 1, i + 5)',
    'new OreDictStack(SRN.crystal())'
)

Write-Host "=== SILEX recipe key diff (port vs EE) ===" -ForegroundColor Cyan
Write-Host "Port: $portPath"
Write-Host "EE:   $eePath"
Write-Host "Port recipes.put keys: $($portKeys.Count)"
Write-Host "EE recipes.put keys:   $($eeKeys.Count)"
Write-Host ""

$missing = @()
foreach ($k in $eeKeys.Keys) {
    if ($exclude -contains $k) { continue }
    if (-not $portKeys.Contains($k)) { $missing += $k }
}
$extra = @()
foreach ($k in $portKeys.Keys) {
    if (-not $eeKeys.Contains($k) -and $exclude -notcontains $k) { $extra += $k }
}

if ($missing.Count -eq 0) {
    Write-Host "Missing in port (EE-only, excl. scope): none" -ForegroundColor Green
} else {
    Write-Host "Missing in port ($($missing.Count)):" -ForegroundColor Yellow
    $missing | ForEach-Object { Write-Host "  $_" }
}

if ($extra.Count -gt 0) {
    Write-Host ""
    Write-Host "Port-only ($($extra.Count)):" -ForegroundColor DarkYellow
    $extra | Select-Object -First 15 | ForEach-Object { Write-Host "  $_" }
    if ($extra.Count -gt 15) { Write-Host "  ... and $($extra.Count - 15) more" }
}

Write-Host ""
Write-Host "=== Wavelength counts ===" -ForegroundColor Cyan
$portWl = Get-WavelengthCounts $portPath
$eeWl = Get-WavelengthCounts $eePath
$allWl = ($portWl.Keys + $eeWl.Keys) | Sort-Object -Unique
foreach ($wl in $allWl) {
    $p = if ($portWl.ContainsKey($wl)) { $portWl[$wl] } else { 0 }
    $e = if ($eeWl.ContainsKey($wl)) { $eeWl[$wl] } else { 0 }
    Write-Host ("  {0,-10} port={1,3}  ee={2,3}" -f $wl, $p, $e)
}