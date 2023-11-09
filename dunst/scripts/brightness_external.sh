#!/usr/bin/env bash

arg=$1

state_file="$HOME/.config/hypr/scripts/assets/ext_monitor.txt"

function set_brightness() {
    brightness=$1
    if grep -q 'brightness = ' "$state_file"; then
        sed -i "s/brightness = [0-9-]*/brightness = $1/" "$state_file"
    else
        echo "brightness = $1" > "$state_file"
    fi
}

function set_script_state() {
    isRunning=$1
    if grep -q 'script_state = ' "$state_file"; then
        sed -i "s/script_state = [0-1]*/script_state = $1/" "$state_file"
    else
        echo "script_state = $1" >>"$state_file"
    fi
}

function perform_brightness_action() {
    set_script_state 1
    ddcutil setvcp 10 "$1"
    set_brightness "$1"
    set_script_state 0
}

# Initialize
if [ -f "$state_file" ]; then
    brightness=$(grep -oP 'brightness =\s+\K\d+' "$state_file")
    isRunning=$(grep -oP 'script_state =\s+\K\d+' "$state_file")
else
    brightness=60
    isRunning=0
    set_brightness $brightness
    set_script_state $isRunning
fi

# Check if the argument is a positive or negative value
if [[ $arg =~ ^[+-]?[0-9]+$ ]]; then
    if [[ $arg -gt 0 ]]; then
        # Increase brightness for a positive value
        new_brightness=$(($brightness + arg))
    else
        # Decrease brightness for a negative value
        new_brightness=$(($brightness - ${arg#[-]}))
    fi

    # Ensure the new brightness value stays within the range of 0-100
    if [[ $new_brightness -lt 0 ]]; then
        new_brightness=0
    elif [[ $new_brightness -gt 100 ]]; then
        new_brightness=100
    fi

    if [ "$new_brightness" = "100" ]; then
        dunstify -h string:x-dunst-stack-tag:brightness_ext "Full Brightness" --icon="$HOME/.config/hypr/scripts/assets/brightness.svg"
        if [ "$brightness" = "$new_brightness" ]; then 
            exit
        fi
    elif [ "$new_brightness" = "0" ]; then
        dunstify -h string:x-dunst-stack-tag:brightness_ext "Into the darkness"
        if [ "$brightness" = "$new_brightness" ]; then 
            exit
        fi
    else
        dunstify -h string:x-dunst-stack-tag:brightness_ext -h int:value:"$new_brightness" "Monitor : $new_brightness" --icon="$HOME/.config/hypr/scripts/assets/brightness.svg"
    fi

    if [ "$isRunning" == 0 ]; then
        perform_brightness_action $new_brightness
    elif [ "$isRunning" == 1 ]; then
        while [ "$isRunning" == 1 ]; do
            sleep 1
        done
        perform_brightness_action $new_brightness
    fi
    exit
else
    dunstify -h string:x-dunst-stack-tag:brightness_ext "Invalid argument." "Please use a frkn Number (e.g., 5) or a negative value (e.g., -5)."
    exit
fi
