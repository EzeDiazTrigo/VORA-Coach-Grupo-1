#!/usr/bin/env bash
# Arma una comparación Figma vs app lado a lado, a la misma escala (390 dp de ancho). Es la versión para Linux/macOS
# de compose.ps1 y necesita ImageMagick 7 (`magick`).
#
#   ./compose.sh -f figma.png -a app_top.png [-a app_bottom.png] -o salida.jpg -t "Home / 01 — Dashboard (A)"
#
# - Figma: export del frame a 2x (se recortan 6 px por lado, el borde del frame).
# - App: capturas del emulador de referencia (390 × 844 dp). Con dos capturas (arriba y final del scroll) se compara
#   contra el inicio y el final del frame de Figma.
set -euo pipefail

COLUMN=540; GAP=24; HEADER=64; VIEWPORT_DP=844; BORDER=6
figma=""; out=""; title=""; apps=()
while getopts "f:a:o:t:" opt; do
  case $opt in
    f) figma=$OPTARG ;;
    a) apps+=("$OPTARG") ;;
    o) out=$OPTARG ;;
    t) title=$OPTARG ;;
    *) echo "uso: $0 -f figma.png -a app.png [-a app2.png] -o salida.jpg -t título" >&2; exit 1 ;;
  esac
done
[[ -n $figma && -n $out && ${#apps[@]} -gt 0 ]] || { echo "faltan -f, -a u -o" >&2; exit 1; }

read -r fw fh < <(magick identify -format "%w %h\n" "$figma")
cw=$((fw - 2 * BORDER)); ch=$((fh - 2 * BORDER))
vp=$(( (VIEWPORT_DP * cw + 195) / 390 ))            # alto de un viewport en píxeles de Figma
(( vp > ch )) && vp=$ch
read -r aw ah < <(magick identify -format "%w %h\n" "${apps[0]}")
row=$(( (COLUMN * ah + aw / 2) / aw ))
rows=${#apps[@]}
width=$((2 * COLUMN + 3 * GAP))
height=$((HEADER + rows * (row + GAP) + GAP))

args=(-size "${width}x${height}" xc:'#444444')
for i in $(seq 0 $((rows - 1))); do
  y=$((HEADER + i * (row + GAP)))
  if (( i == 0 )); then top=$BORDER; else top=$((BORDER + ch - vp)); fi
  args+=( \( "$figma" -crop "${cw}x${vp}+${BORDER}+${top}" +repage -resize "${COLUMN}x" \) -geometry "+${GAP}+${y}" -composite )
  args+=( \( "${apps[$i]}" -resize "${COLUMN}x${row}!" \) -geometry "+$((2 * GAP + COLUMN))+${y}" -composite )
done
args+=(-font DejaVu-Sans-Bold -pointsize 24 -fill white -annotate +${GAP}+32 "$title"
       -font DejaVu-Sans -pointsize 19 -annotate +${GAP}+57 Figma -annotate +$((2 * GAP + COLUMN))+57 App)
magick "${args[@]}" -quality 85 "$out"
