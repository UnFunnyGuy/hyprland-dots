#!/usr/bin/env bash
# pactl is required
# passing arg

check=$(pactl list short | grep -E "RUNNING.*alsa_output|alsa_output.*RUNNING")
check_mic=$(pactl list short | grep -E "RUNNING.*alsa_input|alsa_input.*RUNNING")

return_icon() {
    if [ -z "$1" ]; then
        echo ""
    else
        echo "$2"
    fi
}

speaker_check() {
    check=$(pactl list short | grep -E "RUNNING.*alsa_output|alsa_output.*RUNNING")

    if [ -z "$check" ]; then
        echo ""
    else
        echo "󯤱"
    fi

}

mic_check() {

    check_mic=$(pactl list short | grep -E "RUNNING.*alsa_input|alsa_input.*RUNNING")

    if [ -z "$check_mic" ]; then
        echo ""
    else
        echo "󩈙"
    fi
}

left() {
    if [ -n "$check" ] || [ -n "$check_mic" ]; then
        echo " "
    else
        echo ""
    fi
}

right() {
    if [ -n "$check" ] || [ -n "$check_mic" ]; then
        echo " "
    else
        echo ""
    fi
}

if [ "$1" == "S" ]; then
    speaker_check
elif [ "$1" == "M" ]; then
    mic_check
elif [ "$1" == "l" ]; then
    left
elif [ "$1" == "r" ]; then
    right
else
    exit
fi
