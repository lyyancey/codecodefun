yun -y install wget gcc pcre-devel zlib-devel
wget http://nginx.org/download/nginx-1.16.0.tar.gz
tar xf nginx-1.16.0.tar.gz
cd nginx-1.16.0
./configure --prefix=/usr/local
make -j 4
make install
# 然后用命令 'chmod 700 文件名 '  给文件执行权限, 不要用777