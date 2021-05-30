## until 语句

- until 介绍
- until 语法
- 案例分享

---

###  一、until介绍

和while 正好相反，until 是条件为假开始执行，条件为真停止执行。

### 二、until语法

```
until [ condition ] # 注意，条件为假until才会循环，条件为真until 停止循环
	do
		commands代码块
done
```

### 三、案例

```shell
# 打印10-20的数字
#!/usr/bin/bash
init_num=10
until [ init_num -eq 20 ]
	do
		echo $init_num
		let init_num++
done
```

while 打印1-9 until 打印10-20

```shell
#!/usr/bin/bash
num=1
while [ $num -lt 10 ];do
	echo $num
	num=$((num+1))
	until [ $num -lt 10 ];do
		echo $num
		if [ $num -eq 20 ];then
			break
		fi
		num=$((num+1))
	done
done
```

