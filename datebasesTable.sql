drop table User;
drop table Bill;
drop table ServiceDetail;

create table User
(
  userId int auto_increment primary key ,
  userName nvarchar(20),
  password nvarchar(20),
  RoomId int
)default charset=utf8;

create table Bill(
  billId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  userId int,
  totalFee double(10,2),
  runningTime int,
  scheduleCounter int,
  detailedRecordCounter int,
  powerOnCounter int,
  ChangeTempCounter int,
  ChangeFunCounter int

)default charset=utf8;

create table ServiceDetail(
  serviceDetailId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  userId int,
  funSpeed varchar(20),
  feeRate double(10,2),
  fee double(10,2)
)