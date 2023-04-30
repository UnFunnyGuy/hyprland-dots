#!/usr/bin/env bash

# Define the mapping
declare -A PKGS=(
  ["Code"]="VsCode"
  ["Alacritty"]="Alacritty"
  ["jetbrains-studio"]="Android Studio"
  ["jetbrains-idea"]="Idea"
  ["uget-gtk"]="Uget"
  ["org.gnome.Nautilus"]="File Manager"
  ["thunar"]="File Manager"
  ["org.telegram.desktop"]="Telegram"
  ["Google-Chrome"]="Chrome"
  ["firefox"]="Firefox"
)

app_name=$(hyprctl activewindow | grep -oP 'class: \K[^\s]+')


 # Check if the app_name is a valid key in the PKGS array
if [[ ${PKGS["$app_name"]+abc} ]]; then
  pkg=${PKGS["$app_name"]}
else
  pkg=""
fi

# Echo the symbol if it's not empty
if [ -n "$pkg" ]; then
  echo "$pkg"
else
  echo "$app_name"
fi
