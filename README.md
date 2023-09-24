# 配件库存管理系统
**使用方法**  
*注：工作目录可以自己替换，不一定要在/root目录*
## 同步项目文件到服务器
```bash
rsync -a -v CSMS/ root@example.com:/root/
```
# 配置数据库
```bash
mysql -u root -p
```
- 创建数据库
```sql
create database csms;
```
- 切换到数据库csms
```sql
use csms;
```
- 执行sql脚本
```sql
source /root/csms.sql;
```
- 创建数据库用户warehouse
```sql
 CREATE USER warehouse@localhost IDENTIFIED BY '<your-password>';
```
- 授予warehouse对csms数据库的增删改查权限
```sql
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER
    -> ON csms.*
    -> TO warehouse@localhost;
```
# 安装tomcat、npm和nginx
```bash
sudo apt install tomcat9 npm nginx-full -y
```
## 连接到服务器并修改项目文件pom.xml
- 进入java项目目录
```bash
cd CSMSV2.0/CSMSV2
```
- 编译maven项目配置文件pom.xml
```xml
<properties>
	<maven.compiler.source>1.8</maven.compiler.source>
	<maven.compiler.target>1.8</maven.compiler.target>
</properties>
```
# 编译java项目
```bash
mvn clean install
```
# 复制编译好的文件到/var/lib/tomcat9/webapps
```bash
sudo rm -r /var/lib/tomcat9/webapps/ROOT/*
sudo cp -r CSMSV2-1.0-SNAPSHOT/* /var/lib/tomcat9/webapps/ROOT/
```
# 重启tomcat服务器并设置其开机自动启动
```bash
sudo systemctl restart tomcat9
sudo systemctl enable tomcat9
```
# 进入node项目目录并删除原有的node_modules文件夹
```bash
cd /root/csms
rm -rf node_modules
```
# 安装node项目依赖
```bash
npm install
```
# 修改请求网址
*注：example.com为示例域名，请换成自己的域名*
```bash
vim src/main.js
```
```javascript
axios.defaults.baseURL="https://warehouse-api.example.com/"
```
# 构建node项目
```bash
npm run build
```
# 安装node serve并测试node服务器是否可以启动
*注：若出现报错Cannot copy server address to clipboard: Couldn't find the `xsel` binary and fallback didn't work可以忽视，那个是将服务器地址写入剪贴板的命令*
```bash
sudo npm install -g serve
serve -s dist
```
# 将构建出的dist文件夹复制到/var/www/html
```bash
sudo cp -r dist/* /var/www/html/
```
# 设置DNS A记录*warehouse.example.com*
*可以在域名管理面板上添加，具体可以参考域名提供商的文档*
# 配置网站证书
*注：example.com为示例域名，请换成自己的域名*
```bash
sudo certbot certonly --dns-cloudflare --dns-cloudflare-credentials /etc/letsencrypt/cloudflare.ini  -d *.example.com
```
# 配置nginx反向代理
*注：新增配置要放在server块外面；example.com为示例域名，请换成自己的域名*
- 编辑nginx配置文件
```bash
sudo vim /etc/nginx/nginx.conf
```
```conf
stream {
# 将域名映射成一个配置名
  map $ssl_preread_server_name $backend_name {
    warehouse.example.com web;
    warehouse-api.example.com warehouse-api;
    default error;
  }
# 配置转发详情
  upstream web {
    server 127.0.0.1:10001;
  }
  upstream warehouse-api {
    server 127.0.0.1:10003;
  }
  upstream error {
    server 127.0.0.1:10004;
  }
# 监听 443 并开启 ssl_preread
  server {
    listen 443 reuseport;
    listen [::]:443 reuseport;
    proxy_pass $backend_name;
    ssl_preread on;
  }
}
```
```bash
sudo vim /etc/nginx/sites-enabled/default
```
```conf
server {
   listen       10001 ssl http2;
   listen       [::]:10001 ssl http2;
   server_name _;
   root /var/www/html;
   index index.html index.htm index.nginx-debian.html;
   add_header Strict-Transport-Security "max-age=31536000;includeSubDomains;preload" always;
   ssl_certificate /etc/letsencrypt/live/example.com/fullchain.pem;
   ssl_certificate_key /etc/letsencrypt/live/example.com/privkey.pem;
   location / {
      try_files $uri $uri/ =404;
   }
}
# 阻止所有非域名访问
server {
	listen 10004;
	listen [::]:10004;
 	return 444;
}
# tomcat反向代理
server {
        # 监听端口
        listen       10003 ssl http2;
        listen       [::]:10003 ssl http2;
        server_name  _;
        root         /var/www/html;
        index index.html index.php;
        # 非域名链接禁止访问
        if ($host != 'warehouse-api.example.com') { 
                return 444;
        }
        # 证书文件
        add_header Strict-Transport-Security "max-age=31536000;includeSubDomains;preload" always;
        ssl_certificate /etc/letsencrypt/live/example.com/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/example.com/privkey.pem;
        # 反向代理配置
        location / {
                proxy_pass http://127.0.0.1:8080/;
                proxy_http_version 1.1;  
                proxy_set_header Upgrade $http_upgrade;  
                proxy_set_header Connection 'upgrade';  
                proxy_set_header Host $host;  
                proxy_cache_bypass $http_upgrade;  
        }
        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
}
# 配置https重定向
server {
    if ($host = warehouse-api.example.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

	listen 80;
	listen [::]:80;
    server_name warehouse-api.example.com;
    return 404; # managed by Certbot

}
server {
    if ($host = warehouse.example.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

	listen 80;
	listen [::]:80;
    server_name warehouse.example.com;
    return 404; # managed by Certbot

}
```
- 测试nginx配置文件是否合法
```bash
sudo nginx -t
```
- 重启nginx服务器并设置其开机自动启动
```bash
sudo systemctl restart nginx
sudo systemctl enable nginx
```
- 打开浏览器测试服务器运行情况