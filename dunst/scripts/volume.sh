#!/usr/bin/env bash

arg=$1

if [ "$1" == "I" ]; then
    pamixer -i 3
elif [ "$1" == "D" ]; then
    pamixer -d 3
else
    notify-send "Please use correct args" --icon=$HOME/.config/hypr/scripts/assets/alert.svg
    exit
fi

volume=$(pamixer --get-volume)

if [ "$volume" == 100 ]; then
    dunstify -h string:x-dunst-stack-tag:volume "Full Volume" --icon=$HOME/.config/hypr/scripts/assets/volume-max.svg
    exit
elif [ "$volume" == 0 ]; then
    dunstify -h string:x-dunst-stack-tag:volume "No Volume" --icon=$HOME/.config/hypr/scripts/assets/muted.svg
    exit
else
    dunstify -h string:x-dunst-stack-tag:volume -h int:value:$volume "Volume : $volume" --icon=$HOME/.config/hypr/scripts/assets/volume-max.svg
    exit
fi

"Volume"
exit
