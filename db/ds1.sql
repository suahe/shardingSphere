CREATE TABLE `t_bill_2022_01` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_02` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_03` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_04` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `t_bill_2022_05` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_06` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_07` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_08` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_09` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_10` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_11` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_bill_2022_12` (
  `order_id` bigint(20) NOT NULL  COMMENT '订单id',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `status` char(1) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


delete from t_bill_2022_1;
delete from t_bill_2022_2;
delete from t_bill_2022_3;
delete from t_bill_2022_4;
delete from t_bill_2022_5;
delete from t_bill_2022_6;
delete from t_bill_2022_7;
delete from t_bill_2022_8;
delete from t_bill_2022_9;
delete from t_bill_2022_10;
delete from t_bill_2022_11;
delete from t_bill_2022_12;
