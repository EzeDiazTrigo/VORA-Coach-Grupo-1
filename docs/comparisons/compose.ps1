# Arma una comparación Figma vs app lado a lado, a la misma escala (390 dp de ancho).
#
#   compose.ps1 -Figma figma.png -App app_top.png[,app_bottom.png] -Out salida.jpg -Title "Home / 01 — Dashboard"
#
# - Figma: export del frame a 2x (790 px con el borde del frame; se recortan 6 px por lado).
# - App: capturas del emulador de referencia (1170 × 2532 px = 390 × 844 dp a 3x). Con dos capturas
#   (arriba y final del scroll) se compara contra el inicio y el final del frame de Figma.
param(
    [Parameter(Mandatory)] [string]$Figma,
    [Parameter(Mandatory)] [string[]]$App,
    [Parameter(Mandatory)] [string]$Out,
    [string]$Title = ""
)
Add-Type -AssemblyName System.Drawing

$ColumnWidth = 540          # px por columna en la imagen final
$Gap = 24
$Header = 64
$ViewportDp = 844
$Background = [System.Drawing.Color]::FromArgb(68, 68, 68)
$TextBrush = [System.Drawing.Brushes]::White
$Font = New-Object System.Drawing.Font("Segoe UI", 18, [System.Drawing.FontStyle]::Bold)
$SmallFont = New-Object System.Drawing.Font("Segoe UI", 14)

$figmaRaw = [System.Drawing.Image]::FromFile((Resolve-Path $Figma))
$border = 6
$figmaRect = New-Object System.Drawing.Rectangle $border, $border, ($figmaRaw.Width - 2 * $border), ($figmaRaw.Height - 2 * $border)
$figmaPxPerDp = $figmaRect.Width / 390.0
$viewportFigmaPx = [int]($ViewportDp * $figmaPxPerDp)

# Fragmentos de Figma: el primer viewport y, si hay segunda captura, el último.
$figmaSlices = @(New-Object System.Drawing.Rectangle $figmaRect.X, $figmaRect.Y, $figmaRect.Width, ([Math]::Min($viewportFigmaPx, $figmaRect.Height)))
if ($App.Count -gt 1) {
    $figmaSlices += New-Object System.Drawing.Rectangle $figmaRect.X, ($figmaRect.Bottom - $viewportFigmaPx), $figmaRect.Width, $viewportFigmaPx
}

$appImages = $App | ForEach-Object { [System.Drawing.Image]::FromFile((Resolve-Path $_)) }
$rowHeight = [int]($ColumnWidth * $appImages[0].Height / $appImages[0].Width)
$width = 2 * $ColumnWidth + 3 * $Gap
$height = $Header + $appImages.Count * ($rowHeight + $Gap) + $Gap

$bmp = New-Object System.Drawing.Bitmap $width, $height
$g = [System.Drawing.Graphics]::FromImage($bmp)
$g.Clear($Background)
$g.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
$g.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::AntiAliasGridFit
$g.DrawString($Title, $Font, $TextBrush, $Gap, 8)
$g.DrawString("Figma", $SmallFont, $TextBrush, $Gap, 38)
$g.DrawString("App", $SmallFont, $TextBrush, (2 * $Gap + $ColumnWidth), 38)

for ($i = 0; $i -lt $appImages.Count; $i++) {
    $y = $Header + $i * ($rowHeight + $Gap)
    $slice = $figmaSlices[$i]
    $sliceHeight = [int]($ColumnWidth * $slice.Height / $slice.Width)
    $g.DrawImage($figmaRaw, (New-Object System.Drawing.Rectangle $Gap, $y, $ColumnWidth, $sliceHeight), $slice, [System.Drawing.GraphicsUnit]::Pixel)
    $g.DrawImage($appImages[$i], (2 * $Gap + $ColumnWidth), $y, $ColumnWidth, $rowHeight)
}

# JPEG al 85 % para que cada comparación pese ~300 KB en el repo.
$codec = [System.Drawing.Imaging.ImageCodecInfo]::GetImageEncoders() | Where-Object { $_.MimeType -eq 'image/jpeg' }
$params = New-Object System.Drawing.Imaging.EncoderParameters 1
$params.Param[0] = New-Object System.Drawing.Imaging.EncoderParameter ([System.Drawing.Imaging.Encoder]::Quality), 85L
$bmp.Save((Join-Path (Resolve-Path (Split-Path $Out -Parent)) (Split-Path $Out -Leaf)), $codec, $params)
$g.Dispose(); $bmp.Dispose(); $figmaRaw.Dispose(); $appImages | ForEach-Object { $_.Dispose() }
