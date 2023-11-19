#!/usr/bin/env bash
#

# Define the mapping between app names and symbols
declare -A SYMBOLS=(
  ["Code"]="󪥢"
  ["Alacritty"]="󪥌"
  ["jetbrains-studio"]="󪤍"
  ["jetbrains-idea"]="󪤍"
  ["uget-gtk"]="󪤐"
  ["org.gnome.Nautilus"]="󪤈"
  ["thunar"]="󪤈"
  ["org.telegram.desktop"]="󪥣"
  ["Google-Chrome"]="󪤕"
  ["firefox"]="󪤕"
  ["Spotify"]="󪥵"
  ["GitKraken"]="󪥨"
)

# Get the app name
app_name=$(hyprctl activewindow | grep -oP 'class: \K[^\s]+')

# Check if app_name is null
if [ -z "$app_name" ]; then
  echo ""
else
  # Retrieve the symbol from the mapping
  symbol=${SYMBOLS[$app_name]}

  # Echo the symbol if it's not empty
  if [ -n "$symbol" ]; then
    echo "$symbol"
  fi
fi

