在前端恰当的使用缓存功能，能有效的减轻服务器的压力；
之前的浏览器有支持轻量级数据库sqlite，但是后来被indexBD取缔；
这篇文章就以indexBD为缓存功能的实例。

```
var dbObj = { name:'mydb', version:1, db:null};   //定义数据库链接存储对象
function open(data){
    var request = window.indexedDB.open(data.name, data.version, true);   //打开version=1的名为mydb的数据库，若没有则创建
    request.onerror = function(e){   //数据库连接出错事件
        console.log("db open error",e);
    };
    request.onsuccess = function(e){        //成功建立数据库连接
        data.db = e.target.result;   
        console.log(data);
    }
    request.onupgradeneeded = function(e){        //若mydb为新创建的数据库，则会执行此方法
        var db = e.target.result;
        if(!db.objectStoreNames.contains('bookTable')){    //mydb里里没有此表
            db.createObjectStore('bookTable',{keyPath:"bookId",autoIncrement:false});   //创建表，指定keyPath(相当于map的key，唯一)
        }
        if(!db.objectStoreNames.contains('menuTable')){
            var store = db.createObjectStore('menuTable',{keyPath:"menuId"});
            store.createIndex("bookIndex", "bookId", { unique: false }); //创建bookId唯一索引
        }
        if(!db.objectStoreNames.contains('bodyTable')){
            db.createObjectStore('bodyTable',{keyPath:"menuId"});
        }
        console.log('DB version changed to ' + e);
    };
}
function close(data){
    data.db.close();    //关闭数据库
}
function addBook(item){
    item.bookId = id();    //生成记录id(key)
    var trans = dbObj.db.transaction(["bookTable"],'readwrite');    //以读写方式打开数据表
    var store = trans.objectStore("bookTable");   //打开表的store存储空间
    store.put(item);           //插入或更新记录，若已存在该bookId的记录则更新，不存在则插入
    return item.bookId;
}
function getBook(key){   
    var trans = dbObj.db.transaction(["bookTable"],'readonly');
    var store = trans.objectStore("bookTable");
    if( key ){   //指定参数key，若bookTable里存在对应的记录，则会返回该记录
        var request = store.get(key);
    }else{    //未指定key，获取bookTable里的所有记录
        var request = store.getAll();
    }
    request.onsuccess = function(e){
        console.log(e.target.result);    //输出查询结果
    }
}
function addMenu(item){
    item.menuId = id();
    var trans = dbObj.db.transaction(["menuTable"],'readwrite');
    var store = trans.objectStore("menuTable");
    store.put(item);
    return item.menuId;
}
function menuList(bookId){
    var trans = dbObj.db.transaction(["menuTable"],'readonly');
    var store = trans.objectStore("menuTable");
    var range = IDBKeyRange.lowerBound(bookId);   //指定该索引下，需要查询的值
    var index = store.index("bookIndex");    //根据索引进行查询
    console.log("menulist",bookId);   
    index.get(range).onsuccess = function(e){
        console.log(e.target.result);    //输出该索引下指定值对应的记录
    }
}
function addChapter(item){
    var trans = dbObj.db.transaction("bodyTable",'readwrite');
    var store = trans.objectStore("bodyTable");
    var request = store.get(value);
    request.onsuccess = function(e){
        var obj = e.target.result;
        obj.name = 'book title edit';  //修改记录并更新
        obj = item;
        store.put(obj);
    };
}
function getChapter(menuId){
    var trans = dbObj.db.transaction("bodyTable",'readonly');
    var store = trans.objectStore("bodyTable");
    var request = store.get(menuId);
    request.onsuccess = function(e){
        return e.target.result;
    }
}
function id(){
    var date = new Date();
    return date.getTime();
}

open(dbObj);    //打开数据库

setTimeout(function(){
    var bookId = addBook({ name:'novelName', url:'http://000' });
    for( var i=0;i<10;i++ ){
        var obj = { title:'title'+i, bookId:bookId, url:'http://111' };
        addMenu(obj);  //生成并插入记录
    }
    setTimeout(function(){
        menuList(bookId);    //查询插入的记录
    }, 1000);
}, 1000);

setTimeout(function(){
    close(dbObj);    //关闭数据库
}, 3000);
```
