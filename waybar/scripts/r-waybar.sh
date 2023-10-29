#!/usr/bin/env bash

CONFIG="$HOME/.config/waybar/configs/config"
STYLE="$HOME/..config/waybar/styling/style.css"

if [[ $(pgrep -x "waybar") = "waybar" ]];
then
killall waybar
else
if [[ $(pgrep -x "waybar") = "" ]];
then
waybar -c $CONFIG -s $STYLE > /dev/null 2>&1 &
fi
fi