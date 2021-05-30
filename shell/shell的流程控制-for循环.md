## shell 的流程控制 - for循环

- for 循环介绍
- for 语法
- 循环控制

---

### 一、for 循环介绍

```
for 循环称为条件循环， 或者for i in。其实前者说的就是for的特性,for循环的次数和给予的条件是成正比的
```

### 二、for语法

#### 语法一

- 列表for循环：用于将一组命令执行**已知的次数**，下面给出for循环语句的基本格式：

```
for variable_name in {list}
	do
		command
		command
		...
	done
# 或者
for variable in a b c
	do
		command
		command
		...
	done
```

```shell
#!/usr/bin/bash
#Author: Liu Yong
#Create Time: 2021/5/30
#Description: 循环输出1-9数字

for i in `seq 1 9`
	do
		echo $i
done
```

#### 语法二

C 式的for 命令

```
for ((变量;条件;自增运算))
	do
		代码块
done

#多变量的用法
for ((A=1,b=10;A<10,B>1;A++,B--))
```

### 三、for 条件

for条件不同的赋值方式

- 赋值来自一个范围

```shell
for var in {1..10}
	do
		echo $var
done
```

- 直接赋值

```shell
for var in 1 2 3 4 5
	do
		echo $var
done
```

- 赋值来自命令

```shell
for var in `seq 10`
	do
		echo $var
done
```

### 四、循环控制语句

- sleep N 脚本执行到该步休眠N秒

```shell
#!/usr/bin/bash
#Author:
#Create Time:
#Script Description:
echo -n "倒计时："
for i in `seq 9 -1 1`
	do
		echo -n -e "\b$i"
		sleep 1
done
echo "执行完毕"
```

```shell
#!/usr/bin/bash
#Author:Liu Yong
#Create Time:2021/5/30
#Script Description:监控主机是否存活的脚本
for (( ;; ))
	do
		ping -c1 $1 &>/dev/null
		if [ $? -eq 0 ]
			then
				echo "`date +"%F %H:%M:%S"`: $1 is UP"
			else
				echo "`date +"%F %H:%M:%S"`: $1 is Down"
			fi
			#控制脚本节奏,生产环境下建议一分钟及以上
			sleep 5
done
```

- continue 语句

作用：跳过某次循环,继续执行下一次循环；表示循环体内下面的代码不执行，重新开始下一次循环。

```shell
#!/usr/bin/bash
#Author:
#Create Time
#Script Description:
for ((i=1;i<10;i++))
	do
		if [ $i -eq 5 ]
			then
				 continue
		else
			echo $i
		fi
done
echo "执行完毕"
```

- break 语句

作用：跳出循环，执行循环体后面的代码。

```shell
#!/usr/bin/bash
#Author:
#Create Time
#Script Description:

for i in `seq 1 9`
	do
		echo $i
		if [ $i -eq 5 ]
			then
				break 
		fi
done
echo "执行完毕"
```

从最内部的循环开始从1计数，break 后面跟几就跳出哪层循环。

```shell
#!/usr/bin/bash
#Author
#Create Time
#Script Description:
for ((i=0;i<100;i++))
	do
		for ((;;))
			do
				echo "HHHH"
				break 2 #跳出从内层往外数的第二层循环
		done
done
```

- shift 命令

作用：shift 命令用于对参数的左移，通常用于在不知道传入参数个数的情况下依次遍历每个参数，然后进行相应的处理。每次执行shift(不带参数)时,销毁一个参数，后面的参数前移。shift 命令一次移动参数的个数由其所带的参数指定。shift命令还有一个重要的用途,bash 定义了9个位置变量,从$1到$9，这并不意味着用户在命令行只能使用9个参数,借助shift命令可以访问多于9个的参数。shift 9 命令把$10 移到$1。

```shell
#!/usr/bin/bash
while [ $# != 0 ]
	do
		echo "param is $1, param size is $#"
		shift
done
```

- exit 脚本退出命令

作用：退出程序并释放占用的系统资源

```shell
#!/usr/bin/bash
for i in 'seq 1 9'
	do
		echo $i
		if [ $i -eq 5 ]
			then
				exit 0
		fi
done
echo "执行完毕"
```

