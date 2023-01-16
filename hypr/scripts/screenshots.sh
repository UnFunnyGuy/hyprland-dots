#!/usr/bin/env bash
# argument 1
# F for full screen
# C for selection
#
# argument 2
# S for copying to clipboard

# Directory & file name
dir=~/Desktop/Screenshots
filename=/$(date +'%d-%m-%y_%H-%M-%S_screenshot.png')

mkdir -p $dir

#type of screenshot
if [ "$1" == "F" ]; then
    shot_type=screen
elif [ "$1" == "C" ]; then
    shot_type=area
else
    notify-send "Please use correct args" --icon=$HOME/.config/hypr/scripts/assets/attention.png
    exit
fi

#save and copy ???
if [ -z "$2" ]; then
    cpsave=save
elif [ "$2" == "S" ]; then
    cpsave=copysave
else
    notify-send "Please use correct args" --icon=$HOME/.config/hypr/scripts/assets/attention.png
    exit
fi

function check_status {
    if [ $? -eq 0 ]; then
        notify-send "Screenshot Saved" --icon=$HOME/.config/hypr/scripts/assets/screenshot.png
    else
        notify-send "Error" "Please check your keybinds.conf , make sure you have grimblast installed" --icon=$HOME/.config/hypr/scripts/assets/attention.png
    fi
}

function full_screen {
    grimblast $cpsave $shot_type $dir$filename
    check_status
}

full_screen
