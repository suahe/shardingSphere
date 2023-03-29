CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `username` varchar(250) NOT NULL COMMENT '用户名',
  `telephone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `password` varchar(250) NOT NULL COMMENT '密码',
  `mail` varchar(50) NOT NULL COMMENT '邮件',
  `dep_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `status` char(1) NOT NULL COMMENT '用户状态(1-正常,2-冻结)',
  `remark` char(250) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username_unique` (`username`) USING BTREE,
  UNIQUE KEY `mail_unique` (`mail`) USING BTREE,
  UNIQUE KEY `telephone_unique` (`telephone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL  COMMENT '主键id',
  `user_id` varchar(32) NOT NULL COMMENT '用户名',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(250) NOT NULL COMMENT '角色名称',
  `type` varchar(5) NOT NULL COMMENT '角色类型',
  `code` varchar(10) NOT NULL COMMENT '角色编码',
  `status` char(1) NOT NULL COMMENT '角色状态',
  `remark` char(250) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code_unique` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_role_menu` (
  `id` varchar(32) NOT NULL  COMMENT '主键id',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL  COMMENT '主键id',
  `name` varchar(250) NOT NULL COMMENT '角色名称',
  `permission` varchar(50) NOT NULL COMMENT '菜单权限',
  `type` varchar(5) NOT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
  `sort` int(11) NOT NULL COMMENT '排序',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `path` varchar(250) NOT NULL COMMENT '路径',
  `icon` varchar(250) DEFAULT NULL COMMENT '图表',
  `component` varchar(250) DEFAULT NULL COMMENT '组件',
  `visible` char(1) DEFAULT '1' COMMENT '可见状态(1:可见;0:不可见)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_log` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键id',
  `log_type` int(2) DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
  `log_content` varchar(1000) DEFAULT NULL COMMENT '日志内容',
  `operate_type` int(2) DEFAULT NULL COMMENT '操作类型',
  `userid` varchar(32) DEFAULT NULL COMMENT '操作用户账号',
  `username` varchar(100) DEFAULT NULL COMMENT '操作用户名称',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `method` varchar(500) DEFAULT NULL COMMENT '请求java方法',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `request_param` longtext COMMENT '请求参数',
  `request_type` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `response_res` varchar(1024) DEFAULT NULL COMMENT '响应结果',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_table_userid` (`userid`) USING BTREE,
  KEY `index_logt_ype` (`log_type`) USING BTREE,
  KEY `index_operate_type` (`operate_type`) USING BTREE,
  KEY `index_log_type` (`log_type`) USING BTREE,
  KEY `idx_sl_userid` (`userid`) USING BTREE,
  KEY `idx_sl_log_type` (`log_type`) USING BTREE,
  KEY `idx_sl_operate_type` (`operate_type`) USING BTREE,
  KEY `idx_sl_create_time` (`create_time`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统日志表';
