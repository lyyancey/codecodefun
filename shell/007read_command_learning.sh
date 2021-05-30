#!/usr/bin/bash
clear
#echo -n -e "Login: "
#read acc
read -p "Login: " acc
echo -n -e "Password: "
read -t 50 -n 6 -s pw

echo "account:  $acc    password: $pw"
