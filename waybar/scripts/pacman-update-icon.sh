#!/usr/bin/env bash

pkgs=$(~/.config/waybar/scripts/checkupdates 2>/dev/null | wc -l)

if [ "$pkgs" -eq 0 ]; then
    class="uptodate"
    icon=󪥔
    pkgs=""
else
    class="update"
    icon=󪥔
fi

#fix $icon bs later

result="{\"text\":\""$pkgs"\",\"alt\":\""$icon"\",\"class\":\""$class"\"}"
echo -e $result
