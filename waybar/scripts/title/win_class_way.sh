#!/usr/bin/env bash

app_name=$(hyprctl activewindow | grep -oP 'class: \K[^\s]+')

case "$app_name" in
"Code") echo "VSCode" ;;
"jetbrains-studio") echo "Android Studio" ;;
"waybar") echo "hyprland" ;;
"jetbrains-idea") echo "Intellij" ;;
"uget-gtk") echo "Uget" ;;
"appimagekit_d2192f48ebc43a9db26e1dfa2bc5097b-Kotatogram_Desktop") echo "Telegram" ;;
"org.gnome.Nautilus") echo "File Manager" ;;
"Google-Chrome") echo "Chrome" ;;
*) echo "${app_name^}" ;;
esac
