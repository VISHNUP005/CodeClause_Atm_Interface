create database atm;
use atm;
SET sql_safe_updates=0;
create table Atm1(
username varchar(110),
card_no varchar(20) primary key,
pin int,
account_type varchar(20),
current_balance int
);





create table transactions(
card_no varchar(16) NOT NULL,
    FOREIGN KEY (card_no)
        REFERENCES Atm1 (card_no)
        ON DELETE CASCADE,
type_trans varchar(30),
date_of_t date,
amount int
);

insert into Atm1 values('Saketh','1212121212121212',1234,'savings',15000);
insert into Atm1 values('Varun','3434343434343434',5678,'savings',25000);
insert into Atm1 values('Vishnu','5656565656565656',1010,'savings',65000);
insert into Atm1 values('Sandeep',1234123412341234,1111,'savings',9000);
insert into Atm1 values('Sudeep',1234567890123456,0000,'savings',90000);
select * from transactions;
select * from Atm1;

