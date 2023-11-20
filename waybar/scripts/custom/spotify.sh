class=$(playerctl metadata --player=spotify --format '{{lc(status)}}')

if [[ $class == "playing" ]]; then
  info=$(playerctl metadata --player=spotify --format '{{title}}')
  if [[ ${#info} > 30 ]]; then
    info=$(echo $info | cut -c1-40)"..."
  fi
  text=$info" "$icon
elif [[ $class == "paused" ]]; then
  text=$icon
elif [[ $class == "stopped" ]]; then
  text=""
fi

echo -e "{\"text\":\""$text"\", \"class\":\""$class"\"}"