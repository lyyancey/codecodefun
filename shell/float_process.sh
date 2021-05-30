#!/bin/bash
#Description: 
#Author: Liu Yong
#Create Time: 2021/5/30

NUM1=`echo "1.5|bc|cut -d "." -f1"`
NUM2=$((2*10))

test $NUM1 -gt $NUM2;echo $?