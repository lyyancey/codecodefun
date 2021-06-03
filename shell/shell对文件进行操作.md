## shell对文件进行操作

- 简介
- sed命令
- sed 小技巧

---

### 一、简介

​	在shell脚本编写中，时常会用到对文件的相关操作，比如增加内容、修改内容、删除部分内容、查看部分内容等，但是上述举例的这些操作一般都是需要在文本编辑器中才能操作，常用的文本编辑器如：gedit、vim、nano等又是交互式文本编辑器，脚本无法自己独立完成，必须有人的参与才可以完成。为了解决上述问题，linux为大家提供了一些命令，比如Perl，sed等命令。

### 二、sed介绍

​	sed 是linux 中提供的一个外部命令，他是一个行(流)编辑器，非交互式的对文件内容进行增删查改的操作，使用者只能在命令行输入编辑命令、指定文件名，然后在屏幕上查看输出。他和文本编辑器有本质的区别。

```
区别是
文本编辑器：编辑对象是文件
行编辑器：编辑对象是文件中的行
```

​	前者一次处理一个文本，后者一次处理一个文本中的行。默认情况下，是不会修改源文件

![sed基本原理](E:\codefun\codecodefun\shell\img\sed基本原理.png)

### 三、sed语法

#### sed命令语法

sed [options] '{command}[flags]'  [filename]

```
#命令选项
-e script			将脚本中指定的命令添加到处理输入时执行的命令中  多条件，一行中要有多个操作
-f script			将文件中指定的命令添加到处理输入时执行的命令中
-n				    抑制自动输出
-i					编辑文件内容
-i.bak				修改时同时创建.bak备份文件
-r					使用扩展的正则表达式
!					取反 （跟在模式条件后与shell有所区别）

# command	对文件干什么
# sed常用内部命令
a					在匹配后面添加append
i					在匹配前面添加


d					删除

s					查找替换 字符串
c					更改
y					转换		N D P


p					打印


#flag
数字				   表示新文本替换的模式
g					表示用新文本替换现有文本的全部实例
p					表示打印原始的文本
w filename			 将替换的结果写入文件
```

例子

```
数据
1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
3 the quick brown fox jumps over the lazy dog.
4 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.

开头的a是命令
sed 'ahelloworld' data1

1 the quick brown fox jumps over the lazy dog.
helloworld
2 the quick brown fox jumps over the lazy dog.
helloworld
3 the quick brown fox jumps over the lazy dog.
helloworld
4 the quick brown fox jumps over the lazy dog.
helloworld
5 the quick brown fox jumps over the lazy dog.

可以用\将命令与数据分开
sed 'a\helloworld' data1

1 the quick brown fox jumps over the lazy dog.
helloworld
2 the quick brown fox jumps over the lazy dog.
helloworld
3 the quick brown fox jumps over the lazy dog.
helloworld
4 the quick brown fox jumps over the lazy dog.
helloworld
5 the quick brown fox jumps over the lazy dog.
helloworld

在第二行和第四行后面追加helloworld
sed '2,4a\helloworld' data1

1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
helloworld
3 the quick brown fox jumps over the lazy dog.
helloworld
4 the quick brown fox jumps over the lazy dog.
helloworld
5 the quick brown fox jumps over the lazy dog.

# 匹配模式
sed '/3 the/a\hello world' data1
/3 the/   表示开启匹配模式
a         代表命令


1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
3 the quick brown fox jumps over the lazy dog.
hello world
4 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.

# i 为在匹配的字符串前面插入
sed '/3 the/i\hello world' data1

1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
hello world
3 the quick brown fox jumps over the lazy dog.
4 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.

# 根据匹配删除
sed '/3 the/d' data1

1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
4 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.

删除以#号开头的或者包含#的和空行
sed -r '/(^#d|#|^$)/d' data1

#将所有的dog 替换成cat
sed 's/dog/cat/' data1

#将2-4行的dog替换成cat
sed '2,4s/dog/cat/' data1
#将第二行的dog替换成cat
sed '2s/dog/cat/' data1
#匹配第三行进行替换
sed '/3 the /s/dog/cat/' data1

# c 更改
将data1中的每一行都改为helloworld
sed 'c\helloworld' data1

# 将第三行改为helloworld
sed '3c\helloworld' data1
#将2-4行改为helloworld
sed '2,4c\helloworld' data1
1 the quick brown fox jumps over the lazy dog.
helloworld
5 the quick brown fox jumps over the lazy dog.

y 字节的转换
a---->A
b---->B
c---->C
d---->D
e---->E
f---->F
g---->G
sed 'y/abcdefg/ABCDEFG/' data1


sed 'p' data1
将会打印两遍
1 the quick brown fox jumps over the lazy dog.
1 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
2 the quick brown fox jumps over the lazy dog.
3 the quick brown fox jumps over the lazy dog.
3 the quick brown fox jumps over the lazy dog.
4 the quick brown fox jumps over the lazy dog.
4 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.
5 the quick brown fox jumps over the lazy dog.

```

标志位

```
将文件中第二个dog替换成cat
sed 's/dog/cat/2' data1
1 the quick brown fox jumps over the lazy dog.cat
2 the quick brown fox jumps over the lazy dog.cat
3 the quick brown fox jumps over the lazy dog.cat
4 the quick brown fox jumps over the lazy dog.cat
5 the quick brown fox jumps over the lazy dog.cat

将文件中所有的dog替换成cat   g是global的意思
sed 's/dog/cat/g' data1
1 the quick brown fox jumps over the lazy cat.cat
2 the quick brown fox jumps over the lazy cat.cat
3 the quick brown fox jumps over the lazy cat.cat
4 the quick brown fox jumps over the lazy cat.cat
5 the quick brown fox jumps over the lazy cat.cat

将修改的文件保存到指定文件中
sed 's/dog/cat/w mfile' data1
抑制内存的输出   -n
sed -n '3s/dog/cat/p' data1
执行多个command   -e
sed -e 's/brown/green/;s/dog/cat/' data1

1 the quick green fox jumps over the lazy cat.dog
2 the quick green fox jumps over the lazy cat.dog
3 the quick green fox jumps over the lazy cat.dog
4 the quick green fox jumps over the lazy cat.dog
5 the quick green fox jumps over the lazy cat.dog

命令以文件的方式执行，文件中每一个命令都占一行
sed -f xx data1

直接修改源文件 -i
sed -i -f xx data1
先备份原文件，然后对源文件进行操作
sed -i.bak -f xx data1
先对文件备份到data1.bak 然后再对文件进行操作

通过管道输入到sed中
echo "tom is cool"|sed 's/tom/yong/'
```

#### sed 小技巧

``` 
$= 统计文本有多少行
sed -n '$=' data1

```

