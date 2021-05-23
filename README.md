# order

SQL
_use order_s;
 create table order_table(
 
 `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `order_name` varchar(64) NULL DEFAULT NULL COMMENT '订单名称' ,
  primary key(`id`)
 )ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 create table order_item (
 `product_id` varchar(64) NULL DEFAULT NULL COMMENT '产品id' ,
 `qty` int(11) NULL DEFAULT 0 COMMENT "金额" ,
 `order_table` int(11) NULL DEFAULT 0 COMMENT '订单id' ,
 `order_table_key` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  primary key(`order_table_key`),
  CONSTRAINT `order_item_ibfk` FOREIGN KEY(`order_table`) REFERENCES `order_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 create table address (
 `street` varchar(64) NULL DEFAULT NULL COMMENT '街道',
 `zip` varchar(64) NULL DEFAULT NULL COMMENT '位置' ,
 `order_table` int(11) NULL DEFAULT 0 COMMENT '订单id' ,
 `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  PRIMARY KEY (`id`),
  CONSTRAINT `address_ibfk` FOREIGN KEY(`order_table`) REFERENCES `order_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 create table order_status (
 `state` int(11) NULL DEFAULT 0 COMMENT '状态' ,
 `order_table` int(11) NULL DEFAULT 0 COMMENT '订单id' ,
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  PRIMARY KEY (`id`),
  CONSTRAINT `status_ibfk` FOREIGN KEY(`order_table`) REFERENCES `order_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8;_
