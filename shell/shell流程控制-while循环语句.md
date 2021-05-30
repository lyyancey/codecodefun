## shell流程控制-while 循环语句

- while 循环介绍
- while 循环语法
- while 实战

***

### 一、while 循环介绍

循环语句，与for 循环差不多。

特点：条件为真就进入循环，条件为假就退出循环，一般用在未知循环次数的环境。

### 二、while 循环语法

```
while [ 表达式 ]
	do
		command...
done

while [ 1 -eq 1 ] 或者 ((1 > 2))
	do
		command
		command
		...
done
```

### 三、while 实战

- 使用for循环和while 循环分别循环打印数组1-5

```shell
#!/usr/bin/bash
#for 循环打印
for ((i=1;i<=5;i++))
	do
		echo $i
done

#while 循环打印
#打印数字1-5
num=1
while [ $num -le 5 ]
	do
		echo $num
		let num++
done
```

- while 与shell 运算

  比较运算

  ```shell
  #按Q退出的场景
  #!/usr/bin/bash
  read -p "请输入一个小写字母,按Q退出:" choose
  while [ $choose != 'Q' ]
  	do
  		echo "你输入的是：$choose"
  		read -p "请输入一个小写字母,按Q退出：" choose
  done
  ```

  逻辑运算

  ```shell
  #!/usr/bin/bash
  read -p "money: " money
  read -p "car: " car
  read -p "house: " house
  while [ $money -lt 10000 ]&&[ $car -lt 1 ]&&[ $house -lt 2 ]
  	do
  	#不满足条件
  	echo "有请下一个"
  	read -p "money: " money
  	read -p "car: " car
  	read -p "house: " house
  done
  # 应征者满足条件
  echo "满足条件"
  ```

  特殊条件

  while 语句中可以使用特殊条件来进行循环：

  - 符号 ":"条件代表真，适用于无限循环。
  - 字符串"true"条件代表真，适用于无限循环。
  - 字符串"false"条件代表假。

  ```shell
  #!/usr/bin/bash
  # 特殊符号 ：代表真
  while : 
  	do
  		echo "hahaha"
  		sleep 1
  done
  
  # true 为真
  while true
  	do
  		echo "jjjj"
  done
  
  # false 为假
  while false
  	do
  		echo "HHHH"
  done
  ```

### while与循环控制语句

sleep语句

```shell
#!/usr/bin/bash
# 定义初始值
time=9
while [ $time -ge 0 ]
	do
		echo -n -e "\b$time"
		let time--
		sleep 1
done
#回车
echo
```

break

```shell
#!/usr/bin/bash
#Description: 输出数字1-9，当输出到5时停止
# 定义初始值
num=1
while [ $num -lt 10 ]
	do
		echo $num
		if [ $num -eq 5 ]
			then
				break
		fi
	#自动累加
	let num++
done
```

continue

```shell
#!/usr/bin/bash
#Description: 输出数字1-9，当等于5时跳过本次循环,输出1、2、3、4、6、7、8、9
#定义初值
num=0
while [ $num -lt 9 ]
	do
		#自动累加
		let num++
		#判断当前的值是否等于5
		if [ $num -eq 5 ]
			then
				continue
		fi
		echo $num
done
```

while 嵌套for循环

```shell
#!/usr/bin/bash
#Description: 实现九九乘法表
A=1
while [ $A -lt 10 ]
	do
		for ((B=1;B<=$A;B++))
			do
				echo -n -e "$B*$A=$((A*B)) \t"
		done
		echo
		let A++
done
```

while 嵌套 while循环

```shell
#!/usr/bin/bash
#定义
A=1
while [ $A -lt 10 ]
	do
		#定义B
		B=1
		while [ $B -le $A ]
			do
				echo -n -e "$B*$A=$((A*B)) \t"
				let B++
		done
		echo
		let A++
done
```

 