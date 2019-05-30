### 测试用例1：

#### 1.1 管理员登陆

#### 1.2 管理员打开系统电源（finish)

get

http://localhost:8080/admin/powerOn

#### 1.3 管理员设置参数

post

http://localhost:8080/admin/setParams?defaultRoomTemp=30&tempHighLimit=30&tempLowLimit=16&defaultTargetTemp=24&feeRateHigh=3&feeRateMiddle=2&feeRateLow=1&defaultFunSpeed=MIDDLE

#### 1.4 管理员开启系统（finish)

get

http://localhost:8080/admin/startup



#### 2.前台服务人员给房客办理入住（finish)

post

http://localhost:8080/receptionist/adduser?idnumber=211376

http://localhost:8080/receptionist/adduser?idnumber=211377

http://localhost:8080/receptionist/adduser?idnumber=211378

http://localhost:8080/receptionist/adduser?idnumber=211379

http://localhost:8080/receptionist/adduser?idnumber=211340
#### 3. 房客在房间内

##### 3.1.1 房客登陆

get

http://localhost:8080/login?username=211376&password=4202

##### 3.1.2 开机

post

http://localhost:8080/customer/requestOn?roomID=1

##### 3.1.3 设置初始温度

post

http://localhost:8080/customer/setInitTemp?roomID=1&initTemp=28
##### 3.1.4 改变温度
post
http://localhost:8080/customer/changeTargetTemp?roomID=1&targetTemp=20

##### 3.1.5 改变风速
post
http://localhost:8080/customer/changeFanSpeed?roomID=1&targetFanSpeed=28

##### 3.1.6 查看费用
get
http://localhost:8080/customer/requestRoomState?roomID=1

##### 3.1.7 关机

post

http://localhost:8080/customer/requestOff?roomID=1
##### 3.1.8 管理人员查看房间状态

get
http://localhost:8080/admin/roomState/1

#### 4.1 前台服务人员给房客办理退房（finish)

post

http://localhost:8080/receptionist/checkout?roomid=1

#### 4.2 前台服务人员打印详单（finish)
get
http://localhost:8080/receptionist/createrdr?roomid=1&starttime=2019-05-30T01:00:00&stoptime=2019-07-01T01:00:00

#### 4.3 前台服务人员打印账单（finish)
get
http://localhost:8080/receptionist/createinvoice?roomid=1&starttime=2019-05-30T01:00:00&stoptime=2019-07-01T01:00:00

#### 5 管理人员查看房间统计信息

get
http://localhost:8080/manager/queryreport?type_Report=1&year=2019&month=01&day=01&list_Roomid=1

##### 





