#!/usr/bin/bash
#Author: Liu Yong
#Create Time: 2021/5/30
#Description:
#nginx service manage script


#variables
nginx_install_doc=/usr/local/nginx
nginxd=$nginx_install_doc/sbin/nginx
pid_file=$nginx_install_doc/logs/nginx.pid
proc=nginx

# Source function library.
if [ -f /etc/init.d/functions ];then
    . /etc/init.d/functions
else
    echo "not found file /etc/init.d/functions"
    exit 1
fi

if [ -f $pid_file ];then
    nginx_process_id=`cat $pid_file`
    nginx_process_num=`ps aux |grep $nginx_process_id|grep -v "grep" |wc -l`
fi

#function
start(){
    #如果nginx没有启动直接启动，否则报错 已经启动
    if [ -f $pid_file ]&&[ $nginx_process_num -ge 1 ];then
        echo "nginx_running..."
    else
        if [ -f $pid_file ]&&[ $nginx_process_num -lt 1 ];then
            rm -f $pid_file
            #echo "nginx start `daemon $nginxd`"
            action "nginx start" $nginxd
        fi
        #echo "nginx start `daemon $nginxd`"
        action "nginx start " $nginxd
    fi
}

stop(){
    if [ -f $pid_file ]&&[ $nginx_process_num -ge 1 ];then
        action "nginx stop" killall -s QUIT $proc
        rm -rf $pid_file
    else
        action "nginx stop" killall -s QUIT $proc 2>/dev/null
    fi
}

restart(){
    stop
    sleep 1
    start
}

reload(){
    if [ -f $pid_file ]&&[ $nginx_process_num -ge 1 ];then
        action "nginx reload" killall -s HUP $proc
    else
        action "nginx reload " killall -s HUP $proc 2>/dev/null
    fi
}

status(){
    if [ -f $pid_file ]&&[ $nginx_process_num -ge 1 ];then
        echo "nginx running..."
    else
        echo "nginx stop"
    fi
}
#callable
case $1 in
start) start;;
stop) stop;;
restart) restart;;
reload) reload;;
status) status;;
*) echo "USAGE: $0 start|stop|restart|reload|status";;
esac

#将此文件拷贝到/etc/init.d/nginxd          cp nginx_manahe.sh /etc/init.d/nginxd
#chmod 755 /etc/init.d/nginxd
#/etc/init.d/nginxd   start
#lsof -i :80