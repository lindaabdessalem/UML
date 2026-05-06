$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$reportDir = Join-Path $root "report"
$markdown = Join-Path $reportDir "report.md"
$body = Join-Path $reportDir ".report-body.html"
$html = Join-Path $reportDir "final-report.html"

npx --yes marked -i $markdown -o $body

$bodyContent = Get-Content -LiteralPath $body -Raw
$template = @"
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>OMU Mini Project Final Report</title>
  <style>
    @page {
      size: A4;
      margin: 18mm 16mm;
    }

    body {
      margin: 0;
      background: #f3f4f6;
      color: #111827;
      font-family: Arial, Helvetica, sans-serif;
      font-size: 11pt;
      line-height: 1.45;
    }

    main {
      width: 210mm;
      min-height: 297mm;
      box-sizing: border-box;
      margin: 0 auto;
      padding: 18mm 16mm;
      background: white;
    }

    .cover-page {
      min-height: 248mm;
      display: flex;
      flex-direction: column;
      justify-content: center;
      gap: 16px;
      border: 2px solid #1f2937;
      padding: 22mm 18mm;
      box-sizing: border-box;
      background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
    }

    .cover-page h1 {
      margin: 0;
      text-align: center;
      font-size: 25pt;
      line-height: 1.15;
    }

    .cover-page h2 {
      margin: 4px 0 18px;
      padding: 0;
      border: 0;
      text-align: center;
      font-size: 17pt;
      line-height: 1.25;
      color: #374151;
    }

    .cover-kicker {
      margin: 0;
      text-align: center;
      text-transform: uppercase;
      letter-spacing: 0.12em;
      font-size: 10pt;
      font-weight: 700;
      color: #4b5563;
    }

    .cover-note {
      margin: 14px auto 0;
      max-width: 145mm;
      text-align: center;
      color: #374151;
      font-size: 10.5pt;
    }

    .page-break {
      break-after: page;
      page-break-after: always;
      height: 0;
    }

    h1 {
      margin: 0 0 20px;
      text-align: center;
      font-size: 24pt;
      letter-spacing: 0;
    }

    h2 {
      margin: 28px 0 10px;
      padding-bottom: 4px;
      border-bottom: 1px solid #d1d5db;
      font-size: 17pt;
    }

    h3 {
      margin: 20px 0 8px;
      font-size: 13.5pt;
    }

    h4 {
      margin: 16px 0 6px;
      font-size: 12pt;
    }

    p {
      margin: 0 0 10px;
      text-align: justify;
    }

    ul, ol {
      margin: 6px 0 12px 22px;
      padding: 0;
    }

    li {
      margin: 4px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin: 10px 0 18px;
      font-size: 10pt;
    }

    th, td {
      border: 1px solid #9ca3af;
      padding: 7px 8px;
      vertical-align: top;
    }

    th {
      background: #eef2f7;
      text-align: left;
    }

    tr:nth-child(even) td {
      background: #fbfdff;
    }

    img {
      display: block;
      max-width: 100%;
      max-height: 210mm;
      object-fit: contain;
      margin: 12px auto 8px;
      border: 1px solid #e5e7eb;
    }

    .caption {
      margin: 0 0 18px;
      text-align: center;
      font-size: 9.5pt;
      color: #4b5563;
      font-style: italic;
    }

    pre {
      background: #f9fafb;
      border: 1px solid #d1d5db;
      padding: 10px;
      overflow-x: auto;
      font-size: 9.5pt;
    }

    code {
      font-family: Consolas, "Courier New", monospace;
      font-size: 0.95em;
    }

    strong {
      font-weight: 700;
    }

    @media print {
      body {
        background: white;
      }

      main {
        width: auto;
        min-height: auto;
        margin: 0;
        padding: 0;
      }

      h2 {
        break-after: avoid;
      }

      img, table, pre {
        break-inside: avoid;
      }

      .cover-page {
        min-height: 260mm;
      }
    }
  </style>
</head>
<body>
  <main>
$bodyContent
  </main>
</body>
</html>
"@

Set-Content -LiteralPath $html -Value $template -Encoding UTF8
Remove-Item -LiteralPath $body -Force

Write-Host "Built report/final-report.html"
