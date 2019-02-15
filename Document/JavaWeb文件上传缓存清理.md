在开发JavaWeb时，经常会遇到上传文件功能。

在使用apache.commons.fileupload包上传文件时，若文件大小超过设置的内存缓存区域大小，就会在指定目录生成.tmp临时文件；
在服务器接收到文件之后，会根据需求对文件大小进行验证，当超过限制时会跳过该文件的处理；
若在跳过文件处理时，未进行过IO操作，那么该文件的.tmp临时文件就会遗留在服务器，对服务器造成存储空间浪费。

这时就需要在跳过文件的时候手动处理来删除对应的.tmp，只需要在跳过之前增加一行item.delete();即可;
具体应用代码如下：

```
//检查文件上传请求是否为切片请求
boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
if (!isMultipart) {
    return;
}
// 获取缓存目录路径
String tempDirectory = this.getServletContext().getRealPath("upload/temp") + "/";
// 创建缓存池  
DiskFileItemFactory factory = new DiskFileItemFactory();
// 设置，内存缓存区域大小、缓存路径  
factory.setSizeThreshold( 524288 ); //yourMaxMemorySize, 512K, 1024*512=524288
factory.setRepository(new File(tempDirectory));    //yourTempDirectory
//factory.setRepository(new File(System.getProperty("java.io.tmpdir")));        //tomcat default tmpdir

// 创建文件上传处理线程  
ServletFileUpload upload = new ServletFileUpload(factory);
// 设置文件上传监听器
upload.setProgressListener(new FileUploadProgressListener());
// 解析请求
List items = null;
try {
    items = upload.parseRequest(request);
} catch (FileUploadException e1) {
    e1.printStackTrace();
}
        
// 文件上传处理
Iterator iter = items.iterator();
Map<String,String> formMap = new HashMap<String,String>();
while (iter.hasNext())
{
    FileItem item = (FileItem)iter.next();
    //整个表单的所有域都会被解析，要先判断一下是普通表单域还是文件上传域
    if (item.isFormField()) {
        // fileName   //指定文件名称
        // fileType   //指定文件扩展名
        formMap.put(item.getFieldName(), item.getString());
    } else {
        String contentType = item.getContentType();
        long sizeInBytes = item.getSize();
        //String fieldName = item.getFieldName();
        String itemName = item.getName();
        //boolean isInMem = item.isInMemory();
        //System.out.println(fieldName + ":" + fileName);
        //System.out.println("类型：" + contentType);
        //System.out.println("是否在内存：" + isInMem);
        //System.out.println("文件大小" + sizeInBytes);

        if( sizeInBytes > 1048576*5 ){    //文件最大限制 5M， 1024*1024=1048576
            item.delete();    // 未处理缓存文件，手动删除
            Result.Failed(ResCode.Fail, "文件大小限制 1M");
            return;
        }

        System.out.println( String.format("%s fileName: %s, contentType: %s", DateUtil.now(), itemName, contentType) );
               
        //获取文件扩展名
        String ext = null;
        if( itemName.lastIndexOf(".")>0 ){
            ext = itemName.substring(itemName.lastIndexOf("."));
        }
               
        if( /*判断文件类型*/ ){    //不允许的文件类型
            item.delete();    // 未处理缓存文件，手动删除
            Result.Failed(ResCode.Fail, "不允许的文件类型");
            return;
        }

        /**
         * TODO io相关文件写盘操作代码段
         */
               
        //上传成功，返回文件url
        return;
    }
}
```
