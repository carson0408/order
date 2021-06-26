

create table order_table
(
id int primary key auto_increment,

order_name varchar(64)
);

create table order_item
(
product_id varchar(64)  not null ,
qty int not null,
order_table int not null,
order_table_key int primary key auto_increment
);

 create table address (
 `street` varchar(64) NULL DEFAULT NULL,
 `zip` varchar(64) NULL DEFAULT NULL ,
 `order_table` int(11) NULL DEFAULT 0 ,
 `id` int primary key auto_increment
  );

 create table order_status (
 `state` int(11) NULL DEFAULT 0,
 `order_table` int(11) NULL DEFAULT 0  ,
  `id` int primary key auto_increment,
  );

