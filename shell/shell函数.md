## shell 函数

- 函数介绍
- 函数语法
- 函数应用

---

### 一、函数介绍

​	shell 中允许将一组命令集合或语句形成一段可用的代码，这些代码块称为shell函数。给这段代码起个名字称为函数名，后续可直接调用该段代码的功能。

​	将完成一个功能的一段代码进行命名、封装。

函数的优点

- 代码模块化，调用方便，节省内存
- 代码量少，排错简单
- 可以改变代码执行顺序，有利于代码复用

### 二、函数定义

```
语法一：
函数名(){
	代码块
	return N
}
语法二：
function 函数名{
	代码块
	return N
}
函数中return说明:
1.	return可以结束一个函数,类似于前面讲的循环控制语句break(结束当前循环,执行循环体后面的代码)。
2.	return默认返回函数中最后一个命令的退出状态，也可以给定参数值，该参数值的范围时0-256之间。
3.	如果没有return命令，函数将返回最后一个shell的退出值。
```

例子

```shell
#!/usr/bin/bash
start(){
	echo "Apache start ....         [OK]"
	#return 0
}
stop(){
	echo "Apache stop .....        [FAIL]"
	return 0
}

function start{
	echo "Apache stop ....     [FAIL]"
	return 0
}
```

