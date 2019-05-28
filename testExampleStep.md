### 测试用例1：

#### 1. 管理员登陆

#### 2. 管理员打开系统电源（finish)

http://localhost:8080/admin/powerOn

#### 3. 管理员设置参数

#### 4. 管理员开启系统（finish)

http://localhost:8080/admin/startup

#### 5.前台服务人员给房客办理入住（finish)

http://localhost:8080/receptionist/adduser?idnumber=211376

http://localhost:8080/receptionist/adduser?idnumber=211377

http://localhost:8080/receptionist/adduser?idnumber=211378

http://localhost:8080/receptionist/adduser?idnumber=211379

http://localhost:8080/receptionist/adduser?idnumber=211340
#### 6. 房客在房间内

##### 6.1 开机

##### 6.2 改变温度

##### 6.3 改变风速

##### 6.4 查看费用

##### 6.5 关机

#### 7. 前台服务人员给房客办理退房（finish)

http://localhost:8080/receptionist/checkout?roomid=1

#### 8. 前台服务人员打印详单（finish)

#### 9. 前台服务人员打印账单（finish)

