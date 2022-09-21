CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL  COMMENT '主键id',
  `username` varchar(250) NOT NULL COMMENT '用户名',
  `status` char(1) DEFAULT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
