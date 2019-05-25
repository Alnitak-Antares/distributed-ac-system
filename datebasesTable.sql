create table User
(
  userId int auto_increment primary key ,
  userName nvarchar(20),
  password nvarchar(20),
  startTime varchar(20)
)default charset=utf8;

create table RoomStatis
(
  roomId int auto_increment primary key ,
  turnOnCounter int,
  runningTime int,
  totalServiceCount int,
  billCounter float(10,2),
  ChangeTermCounter int,
  ChangeFunCounter int
)default charset=utf8;

create table Bill(
  billId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  userId int,
  billFee float(10,2)
)default charset=utf8;

create table ServiceDetail(
  serviceId int auto_increment primary key,
  startTime varchar(20),
  stopTime varchar(20),
  roomId int,
  billId int,
  FunSpeed int
)