$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$html = Resolve-Path (Join-Path $root "report/final-report.html")
$pdf = Join-Path (Resolve-Path (Join-Path $root "report")).Path "final-report.pdf"
$chrome = "C:\Program Files\Google\Chrome\Application\chrome.exe"

if (-not (Test-Path -LiteralPath $chrome)) {
    throw "Google Chrome was not found at $chrome"
}

& $chrome `
    --headless `
    --disable-gpu `
    --no-sandbox `
    --no-pdf-header-footer `
    --print-to-pdf="$pdf" `
    "file:///$($html.Path.Replace('\','/'))"

Write-Host "Built report/final-report.pdf without browser headers or footers"
