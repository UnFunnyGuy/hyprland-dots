#!/bin/bash

function fix_jetbrains() {
	if [ "$1" = 'Select Methods to Override/Implement' ]; then
		sleep 0.1
		resize_and_center_active_float 800 900
	fi
}

function resize_and_center_active_float() {
	hyprctl --batch "dispatch resizeactive exact $1 $2; dispatch centerwindow"
}

socat -u UNIX-CONNECT:/tmp/hypr/"$HYPRLAND_INSTANCE_SIGNATURE"/.socket2.sock - | while read -r event; do
	eventType="${event/>*/}"
	if [ "$eventType" = 'activewindow' ]; then
		class="${event/,*/}" && class="${class##*>}"
		title="${event#*,}"
		if [[ "$class" =~ ^jetbrains-.*$ ]]; then
			fix_jetbrains "$title"
		fi
        # Here you can do some other tricks
	# elif [ "$eventType" = 'monitoradded' ]; then
	# 	~/.config/hypr/scripts/detect_monitor.sh "$eventType"
	fi
done
