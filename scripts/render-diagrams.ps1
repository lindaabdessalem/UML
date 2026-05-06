$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$imageDir = Join-Path $root "images"
$diagramDir = Join-Path $root "diagrams"
$config = Join-Path $diagramDir "mermaid-config.json"

& (Join-Path $PSScriptRoot "render-uxf.ps1")
& (Join-Path $PSScriptRoot "render-uxf-png.ps1")

Get-ChildItem $diagramDir -Filter "*.mmd" |
    Sort-Object Name |
    ForEach-Object {
        $svg = Join-Path $imageDir ($_.BaseName + ".svg")
        $png = Join-Path $imageDir ($_.BaseName + ".png")
        npx --yes @mermaid-js/mermaid-cli -i $_.FullName -o $svg -c $config -b white
        npx --yes @mermaid-js/mermaid-cli -i $_.FullName -o $png -c $config -b white
    }
