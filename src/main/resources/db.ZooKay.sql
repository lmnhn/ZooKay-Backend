
go 
use master 
create database ZooKayDB
go

use ZooKayDB

go
CREATE TABLE Ticket
(
  ticket_id VARCHAR(4) NOT NULL,
  ticket_price FLOAT NOT NULL,
  description varchar(255) not null,
  visit_date DATE NOT NULL,
  PRIMARY KEY (ticket_id)
);
go
CREATE TABLE Member
(
  phone_number VARCHAR(12) NOT NULL,
  Name VARCHAR(30) ,
  Email VARCHAR(30) NOT NULL,
  Address VARCHAR(50) NOT NULL,
  age int ,
  -- update 12/10/23
  DoB DATE ,
  -------------------------
  Gender CHAR(10) NOT NULL,

  PRIMARY KEY (phone_number)
);
go
CREATE TABLE Voucher
(
  ticket_id VARCHAR(4) NOT NULL,
  FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id),
  voucher_id varchar(5) primary key not null,
  expiration_date Date ,
  description varchar(255),
  --update 12/10/23
  coupon float not null
  ------------------------------------
);
go

CREATE TABLE Orders
(
  order_id INT identity(1,1) NOT NULL,
  Description VARCHAR(200) NOT NULL,
  order_date DATE NOT NULL,
  PRIMARY KEY (order_id),
  email varchar(255) not null,
  quantity int,
  ticket_id varchar(4) foreign key references Ticket(ticket_id),
  phone_nubmer varchar(12) foreign key references Member(phone_number),
  voucher_id varchar(5) foreign key references Voucher(voucher_id)
);

go
CREATE TABLE ZooArea
(
  zoo_area_id VARCHAR(5) NOT NULL,
  Description VARCHAR(200) NOT NULL,
  biome varchar(255) not null ,
  PRIMARY KEY (zoo_area_id)
);

go

create table Role  (
	role_id varchar(2) primary key NOT NULL,
	role_name varchar(255)
)

go
CREATE TABLE Account
(
  user_name VARCHAR(30) NOT NULL,
  Password VARCHAR(255) NOT NULL,
  Email VARCHAR(30) primary key NOT NULL,
  phone_number VARCHAR(12) NOT NULL,
  FOREIGN KEY (phone_number) REFERENCES Member(phone_number),
  active bit not null,
  otp_generated_time datetime,
  reset_pwd_token varchar(255),
  verification_token varchar(255),

  -- update 22/9
  role_id varchar(2) foreign key (role_id) references Role(role_id) not null
);
go
CREATE TABLE Employees
(
  Name VARCHAR(30) NOT NULL,
  emp_id INT identity(1,1) PRIMARY KEY not null ,
  phone_number VARCHAR(12) NOT NULL,
  DoB DATE NOT NULL,
  Email VARCHAR(30) NOT NULL,
  Address VARCHAR(255) NOT NULL,
  zoo_area_id VARCHAR(5) NOT NULL,
  Managed_by_EmpID INT ,
  FOREIGN KEY (zoo_area_id) REFERENCES [ZooArea](zoo_area_id),
  FOREIGN KEY (Managed_by_EmpID) REFERENCES Employees(emp_id),

  --chỉnh sửa ngày 20/9/23
  foreign key (Email) references Account(Email),
  active bit,
  qualification varbinary
);
go
CREATE TABLE Cage
(
  cage_id INT identity(1,1) NOT NULL,
  Description VARCHAR(200) NOT NULL,
  Capacity INT NOT NULL,
  zoo_area_id VARCHAR(5) NOT NULL,
  PRIMARY KEY (cage_id),
  FOREIGN KEY (zoo_area_id) REFERENCES ZooArea(zoo_area_id)
);
go
CREATE TABLE AnimalSpecies
(
  species_id INT identity(1,1) NOT NULL,
  Name VARCHAR(30) NOT NULL,
  Groups VARCHAR(100) NOT NULL,
  PRIMARY KEY (species_id)
);

go
CREATE TABLE Payment
(
  id int identity(1,1) not null,
  order_id INT NOT NULL,
  FOREIGN KEY (order_id) REFERENCES Orders(order_id),
  is_success bit
);


go
CREATE TABLE AnimalFood
(
  food_id INT identity(1,1) NOT NULL,
  Name VARCHAR(20) NOT NULL,
  Origin VARCHAR(50) NOT NULL,
  import_date DATE NOT NULL,
  Description VARCHAR(255) NOT NULL,
  PRIMARY KEY (food_id)
);
go

CREATE TABLE Animal
(
  animal_id INT identity(1,1) NOT NULL,
  Name VARCHAR(20) NOT NULL,
  Description VARCHAR(255) NOT NULL,
  Age INT NOT NULL,
  Gender VARCHAR(10) NOT NULL,
  Weight FLOAT NOT NULL,
  Height FLOAT NOT NULL,
  species_id INT NOT NULL,
  cage_id INT NOT NULL,
  PRIMARY KEY (animal_id),
  FOREIGN KEY (species_id) REFERENCES AnimalSpecies(species_id),
  FOREIGN KEY (cage_id) REFERENCES Cage(cage_id),
  image_animal varbinary
);
go
CREATE TABLE FeedingSchedule
(
  feed_schedule_id INT identity(1,1) NOT NULL,
  Description VARCHAR(255) NOT NULL,
  Quantity INT NOT NULL,
  food_id INT NOT NULL,
  species_id INT NOT NULL,
  PRIMARY KEY (feed_schedule_id),
  FOREIGN KEY (food_id) REFERENCES AnimalFood(food_id),
  FOREIGN KEY (species_id) REFERENCES AnimalSpecies(species_id)
);
go
CREATE TABLE ZooNews
(
  news_id INT identity(1,1) PRIMARY KEY NOT NULL,
  Content VARCHAR(255) NOT NULL,
  date_created date not  null,
  Description VARCHAR(255) NOT NULL,
  emp_id int Foreign key references Employees(emp_id)
);


go
CREATE TABLE TrainerSchedule
(
  trainer_schedule_id INT identity(1,1) NOT NULL,
  Description VARCHAR(255) NOT NULL,
  species_id INT NOT NULL,
  emp_id INT NOT NULL,
  PRIMARY KEY (trainer_schedule_id),
  FOREIGN KEY (species_id) REFERENCES AnimalSpecies(species_id),
  FOREIGN KEY (emp_id) REFERENCES Employees(emp_id),
  
  -- 22/10/23
  shift int,
  workDay date
);

go

Insert into Role (role_id,role_name) values 
 ('AD','Admin'),
 ('ST','Staff'),
 ('ZT','Zoo Trainer'),
 ('MB','Member');
 go
