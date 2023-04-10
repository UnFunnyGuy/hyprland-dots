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
)

app_name=$(hyprctl activewindow | grep -oP 'class: \K[^\s]+')

# Check if app_name is null
if [ -z "$app_name" ]; then
  echo "${app_name}"
else

 # Retrieve the PKS from the mapping
  pkg=${PKGS[$app_name]}

  # Echo the symbol if it's not empty
  if [ -n "$pkg" ]; then
    echo "$pkg"
  fi
fi
