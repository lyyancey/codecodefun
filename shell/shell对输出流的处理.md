## shell对输出流的处理

- awk 介绍
- awk 基本用法
- awk 高级用法
- awk 小技巧

---

### 一、awk介绍

​	awk 是一种可以处理数据，产生格式化报表的语言,功能十分强大。***awk认为文件中的每一行是一条记录，记录与记录的分隔符为换行符，每一列是一个字段，字段与字段的分隔符默认是一个或多个空格或制表符。***

​	awk 的工作方式是读取数据，将每一行数据视为一条记录(record)每条记录以字段分隔符分成若干字段，然后输出各个字段的值。:beer:

​	awk 语法

`awk [options][BEGIN][program][END][file]`

```
常用命令选项
-F  fs    		指定描绘一行数据字段的文件分隔符 默认为空格
-f  file		指定读取程序的文件名
-v  var=value	定义awk程序中使用的变量和默认值
注意：awk 程序脚本由左大括号和右大括号定义。脚本命令必须放置在两个大括号之间。由于awk命令行假定程序是单文本字符串，所以必须将程序包括在单引号内。
	1.程序必须放在大括号内。
	2.程序必须要用单引号引起来。
awk程序运行优先级是：
	1.BEGIN:  在开始处理数据流之前执行,可选项。
	2.program:  如何处理数据流,必选项
	3.END:  处理完数据流之后执行,可选项
```

### 二、awk的基本用法---awk数据提取功能

	#### awk 对列(字段)的提取

字段提取：提取一个文本中的一列数据并打印输出。

```
字段相关内置变量:
$0  表示整行文本
$1  表示文本行中的第一个数据字段
$2  表示文本行中的第二个数据字段
$N  表示文本行中的第N个数据字段
$NF 表示文本行中的最后一个数据字段
```

```
打印全文本
awk '{print $0}' test
打印最后一列
awk '{print $NF}' test
打印第三列
awk '{print $3}' test
```

#### awk对记录(行)的提取

记录提取： 提取一个文本中的一行并打印输出

记录的提取方法有两种：a.通过行号  b.通过正则匹配

```
记录相关的内置变量
NR  指定行号
```

```
打印第三行的所有字段
awk 'NR==3{print $0}' test
-F 指定分隔符， 打印第一行的第一、二、三三个字段
awk -F "." 'NR==1{print $1,$2,$3}' test
指定打印格式
awk -F "." 'NR==1{print $1 "-" $2 "-" $3}' test
awk -F "." 'NR==1{print "account: "$1,"-",$2,"-"$3}' test
# 查看总的内存
head -3 /proc/meminfo |awk 'NR==1{print $2}'
```

#### awk 程序的优先级

​	关于awk程序的执行优先级，BEGIN是优先级最高的代码块，是在执行PROGRAM之前执行的，不需要提供数据源，因为不涉及到任何数据的处理，也不依赖与PROGRAM代码块;PROGRAM是对数据流干什么，是必选代码块，也是默认代码块。所以在执行时必修提供数据源;END是处理完数据流后的操作，如果要执行END代码块，就必须要PROGRAM的支持，单个无法执行。

```
awk 'BEGIN{print "hello ayitula"}{print $0}END{print "bey ayitula"}' test
# 不需要数据源，可以直接执行
awk 'BEGIN{print "Hello World"}'
# 没有提供数据流,所以无法执行成功
awk '{print "Hello World"}'
awk 'END{print "Hello World"}'
```

### 三、awk 的高级用法

​		awk是一门语言,那么就会符合语言的特性,除了可以定义变量外，还可以定义数组，还可以进行运算，流程控制。

	#### 	awk定义数组

​		数组定义方式：数组名[索引]=值

```
定义数组array，有两个元素，分别是100，200，打印数组元素。
awk 'BEGIN{array[0]=100;array[1]=200;print array[0],array[1]}'
# 打印电脑的内存使用率
head -2 /proc/meminfo |awk 'NR==1{t=$2}NR==2{f=$2;print(t-f)*100/t "%"}'
# 定义变量
awk 'BEGIN{name="liuyong";print name}'
```

#### 	awk运算

1. 赋值运算 =
2. 比较运算 > 、>=、==、 <、<=、 !=
3. 数学运算 +  -  *  /  %  **  ++  --
4. 逻辑运算  &&  ||
5. 匹配运算   ~   !~

1. 赋值运算：主要是针对变量或数组赋值，如：

   ​	变量赋值 name='yong' school='yyyy'

   ​	数组赋值  array[0]=100

   ```
   awk -v name='yong' 'BEGIN{print name}'
   yong
   awk 'BEGIN{shool="ayitula";print shool}'
   ayitula
   awk 'BEGIN{array[0]=100;print array[0]}'
   100
   ```

2. 比较运算，如果比较的是字符串则按ascii编码顺序表比较,如果结果返回为真则用1表示，如果返回假则用0表示。

```
awk 'BEGIN{print "a" >= "b"}'
0
# 产生十个数字放到num文件里
seq 1 10 > num
cat num
awk '$1>5{print $0}' num
awk 'BEGIN{print 1+1}'
```

### awk 的环境变量

|    变量     |                             描述                             |
| :---------: | :----------------------------------------------------------: |
| FIELDWIDTHS | 以空格分离的数字列表，用空格定义每个数据字段的精确宽度，自定义列宽 |
|     FS      |                       输入字段分隔符号                       |
|     OFS     |                       输出字段分隔符号                       |
|     RS      |                        输入记录分隔符                        |
|     ORS     |                        输出记录分隔符                        |

```
FIELDWIDTHS : 重新定义列宽并打印,注意不可以使用$0打印所有,因为$0是打印本行全内容,不会打印你定义的字段
awk 'BEGIN{FIELDWIDTHS="5 2 8"}NR==1{print $1,$2,$3}' etc/passwd
FS 设置输入字段分隔符
awk 'BEGIN{FS=":"}$1 !~"ro"{print $0}' passwd
awk 'BEGIN{FS=":"}$1=="root"{print $0}' passwd
OFS 设置输出字段分隔符
awk 'BEGIN{FS=":";OFS="-"}$1=="root"{print $1,$3,$5}'
awk 'BEGIN{RS=""}{print $1,$2,$3}'
awk 'BEGIN{RS="";ORS="####"}{print $1,$2,$3}' num
```



### awk流程控制的例子

```
# if 语句
awk {if($1>5)print $0}
awk '{if($1<5)print $1*2;else print $1/2}' num
# for 
awk -v 'sum=0' '{sum+=$1}END{print sum}' num
awk {sum=0;for(i=1;i<4;i++){sum+=$i} print sum}' num
# while
awk '{sum=0;i=1;while(i<150){sum+=$i;i++}print $sum}' num
# do while
awk '{sum=0;i=1;do{sum+=$i;i++}while(sum<150);print sum}' num2
# break
awk '{sum=0;i=1;while(i<4){sum+=$i;if(sum>150)break;i++}print sum}'
```

### awk 小技巧

```
学习用例
1 the quick brown fox jumps over the lazy cat . dog
2 the quick brown fox jumps over the lazy cat . dog
3 the quick brown fox       jumps over the lazy cat . dog
4 the quick brown fox jumps over the lazy cat . dog
5 the quick brown fox jumps over the lazy cat . dog

打印test文本的行数
awk 'END{print NR}' test
打印test文本最后一行内容
awk 'END{print $0}' test
打印test文本最后一行的列数
awk 'END{print NF}' test
```



