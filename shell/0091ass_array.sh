#!/usr/bin/bash
#声明一个关联数组
declare -A ass_array1
declare -A ass_array2

#一次只赋一个值
ass_array1[num]=28
#一次赋多个值
ass_array1=([name]="liuyong" [age]=18)
echo ${ass_array1[name]}
