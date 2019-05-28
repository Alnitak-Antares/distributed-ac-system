## 登陆页面
### 页面功能
网页端输入账号密码，服务器端进行验证并跳转。
分为4种角色，有5种情况

1. 密码错误。跳转error.html。该页面执行一个alart警告，然后会自动重定向到登陆页面
2. 管理员登陆。管理员账号固定为：admin。登陆成功后跳转管理员页面：admin.html
3. 经理登陆。经理账号固定为：manager。登陆成功后转跳管理员页面：manager.html
4. 前台服务人员登陆。账号固定为：receptionist。登陆后转跳前台服务人员页面：receptionist.html
5. 房客登陆。成功后转跳房客温度控制页面：user.html
### 前后端接口及数据通信格式
```http
url: /login
methon: get
数据：（json格式）
username=xxxxxxx,
password=xxxxxxx
```
## 管理员页面
### 页面功能
管理员首先点击按钮开启主机电源，然后开始设置系统参数，设置完毕后点击开始启动空调系统（此处希望通过前端界面实现 通电->设置参数->启动 的三层递进逻辑，防止系统异常）
管理员界面需要能够实时检测各房间状态，前端定时向后端发送请求获取房间状态信息（在请求中需指明房间ID，即0～3，后端每次只会根据房间ID返回指定单个房间的信息，故前端需要发送多次请求）
### 前后端接口及数据通信格式
一、开机
```http
url: /admin/powerOn
methon:GET
body:
无
response: (可忽略)
READY
```
二、设置参数
```http
url: /admin/setParams
methon:POST
body: json格式，需要包含的参数如下
(value="defaultRoomTemp") int ,
(value="tempHighLimit") int ,
(value="tempLowLimit") int ,
(value="defaultTargetTemp") int ,
(value="feeRateHigh") double ,
(value="feeRateMiddle") double ,
(value="feeRateLow") double ,
(value="defaultFunSpeed") String )  //字符串, “LOW", "MIDDLE", "HIGH"三种
response: 可忽略
{
    "defaultRoomTemp": 30,
    "tempHighLimit": 30,
    "tempLowLimit": 16,
    "defaultTargetTemp": 24,
    "feeRateHigh": 3,
    "feeRateMiddle": 2,
    "feeRateLow": 1,
    "defaultFunSpeed": "MIDDLE",
    "systemState": "READY",
    "defaultFeeRate": 2
}
```
三、启动
```http
url: /admin/startup
methon:GET
body:
无
response: (可忽略)
ON
```
四、监视房间状态
```http
url: /admin/roomState/{roomID}   //{roomID}字段直接替换为0～3之间的房间ID号
methon:GET
body:
无
response: (json格式)
{
    "nowTemp": 0,				//当前室温
    "tarTemp": 0,
    "funSpeed": "OFF",
    "feeRate": 0,
    "totalFee": 0,
    "runningTime": 0,			//单位为秒
    "inService": false,			//是否服务
    "powerOn": false			//是否开机
}
```
## 前台页面
### 页面功能
一、给用户办理入住，包括
1. 查找空房
2. 如果有空房给用户创建账号，并初始化账单
如果返回的userid=-1说明没有申请成功,如果是null则申请成功
二、退房
前台给系统房间号，系统进行退房的处理，如果退房成功返回"success"，否则返回"error"

只有退房后才能进行查询账单服务。
三、查询详单
详单=每次服务对象的记录，
四、查询账单
账单=本次房客需要支付的钱。
四、退房
停止房间的账单计费

我们是这么理解详单与账单：当一个房客入住4天时，可能每隔一天酒店会给顾客两天消费的详单，但是最终在顾客只需要一张账单。
### 前后端接口及数据通信格式
一、办理入住
```http
url: /receptionist/adduser
methon:post
body:
phoneNumber : xxxxxxx     //我想的是手机号码作为顾客的登陆账号，或者是身份证都行
response:（json格式）
{
    "userid": null,     //这个userid是没用的……
    "username": "211376",
    "password": "8728",
    "roomid": 1             //房间id
}
```

二、办理退房
```
url: /receptionist/checkout
method: get
body:{
	roomid : xx
}
response:
"success"或者"error"
```
三、请求详单
```
url: /receptionist/createrdr
methon:get
body:
roomid : xxxxx
starttime : "yyyy-mm-ddThh:mm:ss"      //格式严格,字符T表示一个分割，这是java默认的格式
stoptime : "yyyy-mm-ddThh:mm:ss"
response:（json格式）
{
    "serviceDetailList": [
        {
            "servicedetailid": 1,
            "starttime": "2019-01-01T01:00:00",
            "stoptime": "2019-01-01T01:00:00",
            "roomid": 1,
            "funspeed": "3",
            "feerate": 4,
            "fee": 5
        },
        {
            "servicedetailid": 2,
            "starttime": "2019-01-01T01:00:00",
            "stoptime": "2019-01-01T01:00:00",
            "roomid": 1,
            "funspeed": "7",
            "feerate": 8,
            "fee": 9
        }
    ]
}
```
四、请求账单
```
http
url: /receptionist/createinvoice
methon:get
body:
roomid : xxxxx
starttime : "yyyy-mm-ddThh:mm:ss"     //格式严格,字符T表示一个分割，这是java默认的格式
stoptime : "yyyy-mm-ddThh:mm:ss"
response:（json格式）
{
    "billid": 1,
    "starttime": "2019-01-01T01:00:00",
    "stoptime": "2019-01-01T01:00:00",
    "roomid": 1,
    "userid": 1,
    "totalfee": 2,
    "runningtime": 3,
    "schedulecounter": 4,
    "detailedrecordcounter": 5,
    "poweroncounter": 6,
    "changetempcounter": 7,
    "changefuncounter": 8
}
```

## 经理页面
### 页面功能
经理的功能是查看报表。报表=房间的账单的统计信息。
说明：输入的时间为查询的日期
当选择日报时只返回日期对应的报表，
当选择月报时返回日期对应的月份的报表
当选择年报时返回日期对应的年份的报表
### 前后端接口及数据通信格式
```http
url:/manager/queryreport
methon:get
body：
list_Roomid:1
list_Roomid:2
type_Report : 0/1/2       // 0 日报，1周报 ，2年报
year: yyyy        //固定4位数字，表示查询的起始年份
month: mm      //固定2位数字，表示查询的起始月份
day: dd            //固定2位数字，表示查询的起始日期

response（json格式）:
[
    {
        "roomid": 1,
        "totalfee": 0,
        "runningtime": 0,
        "scheduleCounter": 0,
        "detailedRecordCounter": 0,
        "powerOnCounter": 0,
        "changetempcounter": 0,
        "changefuncounter": 0
    },
    {
        "roomid": 2,
        "totalfee": 0,
        "runningtime": 0,
        "scheduleCounter": 0,
        "detailedRecordCounter": 0,
        "powerOnCounter": 0,
        "changetempcounter": 0,
        "changefuncounter": 0
    }
]

p.s. 代码提供了另一个接口,但是老师要求是的是日报，周报，年报，所以这个接口似乎没用，如果你们前端想写漂亮一点也行。
url:/manager/queryreportTwo
methon:get
body：
list_Roomid:1
list_Roomid:2
startTime: 2019-01-01T00:00:00
stopTime: 2019-01-01T23:59:59
response跟上面的请求是一样的。
```
## 顾客页面
### 页面功能
顾客页面需要向顾客提供开机、调温、调风、关机功能，并能实时展示当前室温、风速、总费用等状态（定时向后端请求），数据呈现的样式由前端进行界面设计，后端会提供接口返回当前房间的所有状态信息
为了区分不同顾客和房间，要求前端在顾客每一次请求的HTTP body中加上顾客的roomID参数
### 前后端接口及数据通信格式
一、开空调
```
http
url: /customer/requestOn
methon:POST
body: 
(value="roomID") int )
response: 可忽略
"success"
or "Error: It's powerOn."
```
二、关空调
```
http
url: /customer/requestOff
methon:POST
body: 
(value="roomID") int )
response: 可忽略
"success"
or "Error: It's powerOff."
```
三、获取房间状态信息
```
http
url: /customer/requestRoomState
methon:GET
body:
(value="roomID") int )
response:  与管理员部分监视房间状态接口的返回值一致
```
四、调温
```
http
url: /customer/changeTargetTemp
methon:POST
body:
(value="roomID") int )
(value="targetTemp") int )
response: 可忽略
```
五、调风
```
http
url: /customer/changeFanSpeed
methon:POST
body:
(value="roomID") int )
(value="targetFanSpeed") String )  //字符串, “LOW", "MIDDLE", "HIGH"三种
response: 可忽略
```