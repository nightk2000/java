域名1：a.test.com
域名2：b.test.com

在域名1系统登录，创建cookie后，想在域名2获取到这个cookie，操作要点如下：
1、创建cookie时，设置cookie.setDomain(".test.com")
2、域名2系统配置过滤器：

```
// cookie跨域设置
String Origin = req.getHeader("Origin");
if( Origin!=null && Origin.indexOf("test.com")>=0 ){
    // 仅允许*.test.com来源请求的跨域cookie
    // Access-Control-Allow-Origin=*时，Access-Control-Allow-Credentials=true设置是无效的，所以需要增加判断进行来源识别
    res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
    res.setHeader("Access-Control-Allow-Credentials", "true");
}else{
    res.setHeader("Access-Control-Allow-Origin", "*");
}
```

3、ajax如何携带跨域Cookie
```
$.ajax({
	type: "POST",
	url: '',
	data: {},
	dataType:"json",
	xhrFields: {// 添加此项配置
		withCredentials: true  //设置为true
	},
	success: function(json){
	},
	complete :function(XMLHttpRequest, textStatus){
	},
	error:function(XMLHttpRequest, textStatus, errorThrown){
	}
});
```

4、xhr请求如何携带跨域Cookie
```
var xhr = new XMLHttpRequest();
xhr.open("POST", 'url', true);
xhr.withCredentials = true;    // 只需要在send之前设置xhr.withCredentials = true，即可携带跨域Cookie
xhr.send(new FormData());
```

设置好之后，配合hosts文件修改，即可调试前后端分离式系统
