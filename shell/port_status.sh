#!/bin/bash
port_status(){
    temp_file=`mktemp port_status.xxx`
    #1、判断依赖命令telnet是否存在
    [ ! -x /usr/bin/telnet ]&&echo "telnet: not found command!"&&exit 1
    #2、测试端口 $1 IP $2 port
    (telnet $1 $2<<EOF
    quit
    EOF
    )&>temp_file
    #3、判定分析文件中的内容
    if egrep "\^]" $temp_file &>/dev/null;then
        echo "$1 $2 is open!"
    else
        echo "$1 $2 is closed"
    fi
    rm -f $temp_file
}

port_status $1 $2