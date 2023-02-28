#!/usr/bin/env bash

arg=$1

brightnessctl s $arg

# Get the current brightness value
brightness=$(brightnessctl get)

# Calculate the brightness percentage
brightness_percent=$(echo "scale=2; $brightness / 255 * 100" | bc)
brightness_percent=${brightness_percent%.*}

if [ "$brightness_percent" == 100 ]; then
    dunstify -h string:x-dunst-stack-tag:brightness "Full Brightness" --icon=$HOME/.config/hypr/scripts/assets/brightness.svg
    exit
elif [ "$brightness_percent" == 0 ]; then
    dunstify -h string:x-dunst-stack-tag:brightness "Into the darkness"
    exit
else
    dunstify -h string:x-dunst-stack-tag:brightness -h int:value:$brightness_percent "Brightness : $brightness_percent" --icon=$HOME/.config/hypr/scripts/assets/brightness.svg
    exit
fi
