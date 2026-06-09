# Compare EE vs port RADIATION hazard registrations
$eePath = "C:\Users\alex\Desktop\hbmport_1.12.2\мод hbmntm\NTM-Extended-GitHub\src\main\java\com\hbm\hazard\HazardRegistry.java"
$portPath = "C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\hazard\HazardRegistry.java"
$modItemsPath = "C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\items\ModItems.java"
$modBlocksPath = "C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\src\main\java\com\hbm\blocks\ModBlocks.java"

function Get-PortItems {
    $text = Get-Content $modItemsPath -Raw
    $items = [regex]::Matches($text, 'public static final \w+ (\w+)') | ForEach-Object { $_.Groups[1].Value }
    $text2 = Get-Content $modBlocksPath -Raw
    $blocks = [regex]::Matches($text2, 'public static final Block(?:\w+)? (\w+)') | ForEach-Object { $_.Groups[1].Value }
    return ($items + $blocks | Sort-Object -Unique)
}

function Parse-RadsFromFile($path) {
    $lines = Get-Content $path
    $map = @{}
    foreach ($line in $lines) {
        if ($line -match 'registerHazItem\(([^,]+),\s*([^,\)]+)') {
            $item = $Matches[1].Trim() -replace 'new ItemStack\((\w+).*', '$1' -replace 'ModItems\.', '' -replace 'ModBlocks\.', ''
            $rads = $Matches[2].Trim()
            if ($rads -match '^[\d.]+F?$') {
                $map[$item] = $rads
            }
        }
        if ($line -match 'HazardSystem\.register\(([^,]+),\s*makeData\(RADIATION,\s*([^)]+)\)') {
            $item = $Matches[1].Trim() -replace 'new ItemStack\((\w+).*', '$1'
            $map[$item] = $Matches[2].Trim()
        }
        if ($line -match 'HazardSystem\.register\(([^,]+),\s*makeData\(\)\.addEntry\(RADIATION,\s*([^)]+)\)') {
            $item = $Matches[1].Trim() -replace 'new ItemStack\((\w+).*', '$1'
            $map[$item] = $Matches[2].Trim()
        }
    }
    return $map
}

$portItems = Get-PortItems
$eeRads = Parse-RadsFromFile $eePath
$portRads = Parse-RadsFromFile $portPath

$results = @()
foreach ($key in ($eeRads.Keys | Sort-Object)) {
    if ($key -match '^(Items|Blocks)\.') { continue }
    $inPort = $portItems -contains $key
    $eeVal = $eeRads[$key]
    $portVal = $portRads[$key]
    if (-not $inPort) {
        $status = "SKIP_NOT_IN_PORT"
    } elseif (-not $portVal) {
        $status = "MISSING"
    } elseif ($portVal -ne $eeVal) {
        $status = "MISMATCH"
    } else {
        $status = "OK"
    }
    $results += [PSCustomObject]@{ Item = $key; EE_Rads = $eeVal; Port_Rads = $portVal; Status = $status }
}

$outFile = "C:\Users\alex\Desktop\hbmport_1.12.2\hbm-x5687-1.12.2\tools\radiation_hazard_audit.csv"
$results | Export-Csv $outFile -NoTypeInformation
Write-Host "Audit written to $outFile"
Write-Host "MISSING: $(($results | Where-Object Status -eq 'MISSING').Count)"
Write-Host "MISMATCH: $(($results | Where-Object Status -eq 'MISMATCH').Count)"
Write-Host "OK: $(($results | Where-Object Status -eq 'OK').Count)"
$results | Where-Object { $_.Status -eq 'MISSING' } | Select-Object -First 40 | Format-Table -AutoSize