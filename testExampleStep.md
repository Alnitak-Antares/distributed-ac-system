### 测试用例1：

#### 1. 管理员登陆

#### 2. 管理员打开系统电源（finish)

get

http://localhost:8080/admin/powerOn

#### 3. 管理员设置参数

post

http://localhost:8080/admin/setParams?defaultRoomTemp=30&tempHighLimit=30&tempLowLimit=16&defaultTargetTemp=24&feeRateHigh=3&feeRateMiddle=2&feeRateLow=1&defaultFunSpeed=MIDDLE

#### 4. 管理员开启系统（finish)

get

http://localhost:8080/admin/startup

#### 5.前台服务人员给房客办理入住（finish)

post

http://localhost:8080/receptionist/adduser?idnumber=211376

http://localhost:8080/receptionist/adduser?idnumber=211377

http://localhost:8080/receptionist/adduser?idnumber=211378

http://localhost:8080/receptionist/adduser?idnumber=211379

http://localhost:8080/receptionist/adduser?idnumber=211340
#### 6. 房客在房间内

##### 6.1 登陆

get

http://localhost:8080/login?username=211376&password=4202

##### 6.2 开机

post

http://localhost:8080/customer/requestOn?roomID=1

##### 6.3 改变温度

##### 6.4 改变风速

##### 6.5 查看费用

##### 6.6 关机

post

http://localhost:8080/customer/requestOff?roomID=1

#### 7. 前台服务人员给房客办理退房（finish)

post

http://localhost:8080/receptionist/checkout?roomid=1

#### 8. 前台服务人员打印详单（finish)

#### 9. 前台服务人员打印账单（finish)

#### 10. 管理人员查看房间统计信息

##### get

http://localhost:8080/manager/queryreport?type_Report=1&year=2019&month=01&day=01&list_Roomid=1

##### 





