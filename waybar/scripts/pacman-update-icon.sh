#!/usr/bin/env bash

pkgs=$(.config/waybar/scripts/checkupdates 2>/dev/null | wc -l)

if [ "$pkgs" == 0 ]; then
    echo ""
else
    echo $pkgs
fi
