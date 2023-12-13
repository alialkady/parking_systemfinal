create database parking_system
GO
use parking_system
GO
create table operator(
username varchar(20) unique,--add unique
password varchar(8) unique,--add unique
shift_time int	
)
drop table operator
GO
create table customers (
entry_id varchar(10) unique,
plate_number varchar(10) unique , --maybe need to use unique constraint
transaction_date datetime,
slot int default 0,
exit_transaction datetime,
customer_payment decimal default 0.0

)
insert into customers(entry_id,plate_number) values('123','abc')
drop table customers
Go
create table spots(
spot int unique, --add unique
spot_free varchar(10)	
)
Go
create table payment(
shift_order int unique,
shifts_payment decimal
)
drop table payment
Go

