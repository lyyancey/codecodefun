#!/usr/bin/bash
#Author: Liu Yong
#Create Time:2021/5/29
#Script Description: harddisk partition script

fdisk /dev/sdb <<EOF
n
p
3

+534M
w
EOF
##作业
#4块  分两个区/盘
#所有的1区    做成LVM-LV100分区   全部空间  /date/lv00
#所有的2区    做成一个raid分区     全部空间   /data/raid5
#全部通过脚本来完成
#要求开机挂载
#squid 硬盘 IO瓶颈    硬盘存储初始化脚本