drop table RoomStatis;
drop table Bill;
drop table ServiceDetail;

create table User
(
  userId int auto_increment primary key ,
  userName nvarchar(20),
  password nvarchar(20),
  startTime varchar(20)
)default charset=utf8;

create table Bill(
  billId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  userId int,
  totalFee float(10,2),
  runningTime int,
  totalServiceCount int,
  ChangeTermCounter int,
  ChangeFunCounter int
)default charset=utf8;

create table ServiceDetail(
  serviceId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  billId int,
  FunSpeed int,
  fee float(10,2)
)