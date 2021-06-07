## shell脚本的案例

- 监测一个机器的存活状态

```shell
#!/bin/bash
#Description
#Author:Liu Yong
#Create Time: 2021/6/7
#监控目标主机的状态
#监控方法 ping ICMP协议
#ping通 host up
#ping不通 host down
#1. 关于禁ping 防止ddos
#  禁的是陌生人  禁止所有  允许你的IP
#满足条件
#网络有延迟 what's up   假报警问题
#ping的取值问题  报警阈值 3次全部失败 报警机器 down
#ping 的频率  秒级  5秒 or 1秒
#main
 for((i=1;i<4;i++));do
 #测试代码
 	if ping -c 1 $1 &>/dev/null;then
 		export ping_count"$i"=1
 	else
 		export ping_count"$i"=0
 	fi
 #时间间隔
 	sleep 1
 done
 #3次ping失败报警
 if [ $ping_count1 -eq $ping_count2 ] && [ $ping_count2 -eq $ping_count3 ] && [ $ping_count1 -eq 1 ];then
 	echo "$1 is up"
 else
 	echo "$1 is down"
 fi
 unset ping_count1
 unset ping_count2
 unset ping_count3
```

- 监控一个端口的存活状态

```shell
#!/bash/bin
#等价于监控一个服务的状态
#Description
#Author: LiuYong
#Create Time:2021/6/7
#监控方法
#通过systemctl service 服务启动状态
#lsof 查看端口是否存在
#查看进程是否存在
#服务假死怎么办  或者压力过大 无法响应 | 服务down了上述东西还在
#测试端口是否有相应
# telnet协议
# mktemp  port_status.xxx  创建临时文件
# mktemp -d port_status.xxx 创建一个临时文件夹
#main
port_status (){
	temp_file=`mktemp port_status.XXX`
	# 1、判断依赖命令telnet是否存在
	[ ! -x /usr/bin/telnet ]&&echo "telnet: not found command"&&exit 1
	# 2、测试端口 $1 IP    $2 port
	(telnet $1 $2<<EOF
	quit
	EOF
	)&>$temp_file
	#3、判定分析文件中的内容
	if egrep "\^]" $temp_file &>/dev/null;then
		echo "$1 $2 is open"
	else
		echo "$1 $2 is close"
	fi
	rm -f $temp_file
}
#别忘了函数传参
port_status $1 $2
```



1. 找到使用CPU|memory前十的进程
2. 监控内存使用率脚本
3. 监控硬盘IO脚本
4. lamp安装脚本
5. mysql binlog日志备份脚本 备份到备份服务器
6. 新建user01-user20用户,要求密码是随机6位数 密码取值范围a-zA-Z0-0-9,要求密码不能只是单一的数字或小写大写字母。
7. shell随机数：写一个猜数字脚本,数字范围是1-100，定制计数器,每次猜完都要告诉用户猜大了或猜小了，如果猜对了跳出脚本并输出计数器的值。

