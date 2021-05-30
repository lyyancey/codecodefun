## 流程控制

### if 语法

####  语法一：单 if 语句

语句格式

```
if [ condition ]       	#condition值为true or false
	then
		commands
fi
```

例如：

```shell
#!/usr/bin/bash
#Description:
#Author:
#Create Time:
#假如/temp/abc这个文件夹，那么就创建一个
if [ !-d /tmp/abc ]
	then
		mkdir -v /tmp/abc
		echo "123"
		echo "/tmp/abc ok"
fi
```

```shell
#!/usr/bin/bash
#Description:判断当前用户是不是root用户
#Author:yancey
#Create Time:2021/5/30
if [ $USER != 'root' ]
	then
		echo "ERROR: need to be root so that"
		exit 1
fi
```

#### 语法二：if-then-else语句

语句格式

```
if [ condition ]
	then
		commands1
else
		commands2
fi
```

例如

```shell
#!/usr/bin/bash
#Description:
#Author:
#Create Time:
if [ $USER == 'root' ]
	then
		echo "hey admin"
else
	echo "hey guest"
fi
```

#### 语法三 if ... elif ... else

```
if [ condition 1 ] #满足第一个条件
	then		#真
		commands1  #执行commands1代码块
elif [ condition 2 ] #满足第二个条件
	then		#真
		commands2  #执行commands2代码块
...
else		#前面的条件都没满足
	commandX       #执行commandsX代码块
fi	#结束if代码块
```

```shell
#!/usr/bin/bash
#Description:
#Author:
#Create Time:
#判断两个整数的关系

if [ $1 -eq $2 ]
	then
		echo "$1 = $2"
else
	if [ $1 -gt $2 ]
		then
			echo "$1 > $2"
	else
		echo "$1 < $2"
	fi
fi
#$1 $2 是 用命令行给传参的意思
#下面是非嵌套的写法
if [ $1 -eq $2 ]
	then
		echo "$1 = $2"
elif [ $1 -gt $2 ]
	then
		echo "$1 > $2"
else
	echo "$1 < $2"
fi
```

#### if 的高级用法

- 条件符号使用双圆括号，可以在条件中植入数学表达式 if (())

  ```shell
  #!/usr/bin/bash
  #Author:
  #Create Time:
  #Description:
  
  if (((5+5-5)*5/5 > 10))
  	then
  		echo "yes"
  else
  		echo "No"
  fi
  ```

- 使用双方括号，可以在条件中使用通配符

  ```shell
  #!/usr/bin/bash
  #Author:
  #Create Time:
  #Description:
  for var in ab ac rx bx rvv vt
  	do
  		if [[ "$var" == r* ]]
  			then
  				echo "$var"
  		fi
  done	
  ```

  ### 简写 if

  省去了关键字，条件为真采用&&符号连接命令快，条件为假采用||连接命令快，简写if一般用在简单的判断中。  

  ```shell
  #!/usr/bin/bash
  #Author:
  #Create Time:
  #Description:
  
  if [ ! -d /tmp/yong ]
  	then
  		mkdir /tmp/yong
  fi
  #可以简写为
  [ ! -d /tmp/yong ] && mkdir /tmp/yong
  
  #####################
  if [ $USER == 'root' ]
  	then
  		echo "hello root"
  else
  	echo "hello guest"
  fi
  #可以简写为
  [ $USER == 'root' ]&&echo "hello root" || echo "hello guest"
  ```

  