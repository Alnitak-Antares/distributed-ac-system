
//是一个报文
$.ajax({
    //头部的参数，这个基本不
    headers: {

    },
    type:"GET", //请求的方法
    url:"http://localhost:8080/html", //url
    dataType:"json",        //数据交互的方式
    global:"false",
    data:{                  //数据内容，
            "username":"admin",
            "password":"admin"
    },
    success:function(data) {        //成功时的回调函数，data是从后端发送来的数据，就是数据格式中的response
        //   alert(data);
        // json 的数据格式 {
        //  "key1":value1,
        //  "key2":value2,
        //  "key3":value3,
        // }
        // 比如说要去出某个参数，直接data.xxx就行了
        // 比如，data.key1,值为value1
        // 如果data是个list，那么就 data[i].key1,就取出数据里面第一个元素的key1
    },
    /* success:function(data){
       alert(JSON.stringify(data));
     },*/
    error:function(){
        alert("Please Log In First");
        // $(location).attr('href', '/rentHomeProj_war/signin');
    }
})