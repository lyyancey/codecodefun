#!/bin/bash
#Description
#Author:Liu Yong
#Create Time:2021/6/7
#检测目标主机的状态
#监控方法 ping ICMP协议
for((i=1;i<4;i++));do
	if ping -c 1 $1 &>/dev/null;then
		export ping_count"$i"=1
	else
		export ping_count"$i"=0
	fi
#时间间隔
	sleep 1
done
#3次ping失败报警
if [$ping_count1 -eq $ping_count2] && [$ping_count2 -eq $ping_count3] && [$ping_count1 -eq 0];then
	echo "$1 is down"
else
	echo "$1 is up"
fi

unset ping_count1
unset ping_count2
unset ping_count3