## case 多条件分支语句

- case 介绍
- case 语法
- shell特殊变量

---

### 一、case介绍

类似于switch...case语句

### 二、case语法

```
case $var in 			定义变量;var代表变量名
pattern 1)				模式1;用 | 分割多个模式,相当于or
	command1			需要执行的语句
	;;					两个分号代表命令结束
pattern 2)
	command2
	;;
pattern 3)
	command3
	;;
		*)				default,不满足以上模式，默认执行*)下面的语句
	command4
	;;
esac					esac表示case语句结束
```

例子

```shell
#!/usr/bin/bash
read -p "num: " N
case $N in
1)
	echo haha
;;
2)
	echo hehe
;;
*)
	echo "1|2"
;;
esac
```



