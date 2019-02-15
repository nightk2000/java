Jvm内存状态查看
Linux：

`jmap -heap [pid]`


线程快照信息导出
Linux：

`jstack -l [pid] > /usr/local/tomcat/[pid].stack`


查看进程内哪个线程(子进程)CPU占用最高
Linux：

`top -H -p [pid]`


将线程（子进程）pid转换为16进制
Linux:

`printf "%x\n" [pid]`

例：
`printf "%x\n" 6053`

`17a5`


在.stack文件中查找 **nid=17a5** 的线程，即可定位该线程对应的class
