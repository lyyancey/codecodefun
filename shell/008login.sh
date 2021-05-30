#!/usr/bin/bash
clear
echo "Centos Linux 7 (Core)"
echo "kernel `uname -r` an `uname -m`\n"

echo -n -e "$HOSTNAME login: "
read acc
read -s -p "password: " pw