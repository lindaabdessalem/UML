$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
$imageDir = Join-Path $root "images"

Get-ChildItem $imageDir -Filter "0[1-6]_*.svg" |
    Sort-Object Name |
    ForEach-Object {
        $png = [System.IO.Path]::ChangeExtension($_.FullName, ".png")
        npx --yes svgexport $_.FullName $png
    }
