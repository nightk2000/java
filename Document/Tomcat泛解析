tomcat本身不支持域名泛解析，所以需要使用泛解析的项目无法完成正常部署；

在这种情况下，通过nginx转发到tomcat，可以间接实现泛解析；

在通过网址访问tomcat时，若未配置对应的 <host name="xxx.com" .../>，tomcat会自动将该请求全部转到 <Engine defaultHost="localhost" .../>来处理，

这样我们可以将localhost作为我们泛解析项目的部署位置。

具体配置如下：
nginx:
server {
    listen 80;
    server_name *.xxx.com;
    location / {
        proxy_set_header   Host    $host;   #host配置是用来转发请求地址，这样在项目中就可以获取到泛解析的请求网址
        proxy_set_header   X-Real-IP $server_addr;
        proxy_set_header   REMOTE-HOST $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   Referer $http_referer;
        proxy_pass http://127.0.0.1:8080/;   #这里填写对应tomcat中localhost的访问路径
    }
}

tomcat:
<Engine defaultHost="localhost" .../>

<!-- name必须与Engine.defaultHost一致-->
<Host name="localhost"  appBase="webapps/项目部署文件夹" ...>
<Context path="" reloadable="false" docBase="" />
</Host>

至于一个tomcat只有一个defaultHost怎么部署多个泛解析项目，只需要配置多个Service即可，
每个Service都需绑定一个新端口，这样就可以通过nginx转发到多个端口达到多个泛解析项目的目的。
