-- 用户
INSERT INTO `test_ds`.`sys_user`(`id`, `username`, `telephone`, `password`, `mail`, `dep_id`, `status`, `remark`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'admin', '15659851606', '$2a$10$X5U/ZPaOosqgM9UgiKkghOeST2wMned1R/mzivg7MJwvko8RNYEPq', '1054599027@qq.com', NULL, '1', NULL, NULL, NULL, NULL, NULL);

-- 用户状态字典
INSERT INTO `sys_dict`(`id`, `dict_name`, `dict_code`, `description`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `type`) VALUES ('fc6cd58fde2e8481db10d3a1e68ce70c', '用户状态', 'user_status', NULL, 0, 'admin', '2019-03-18 21:57:25', 'admin', '2019-03-18 23:11:58', 1);
INSERT INTO `sys_dict_item`(`id`, `dict_id`, `item_text`, `item_value`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('78ea6cadac457967a4b1c4eb7aaa418c', 'fc6cd58fde2e8481db10d3a1e68ce70c', '正常', '1', NULL, NULL, 1, 'admin', '2019-03-18 23:30:28', NULL, NULL);
INSERT INTO `sys_dict_item`(`id`, `dict_id`, `item_text`, `item_value`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('81fb2bb0e838dc68b43f96cc309f8257', 'fc6cd58fde2e8481db10d3a1e68ce70c', '冻结', '2', NULL, NULL, 1, 'admin', '2019-03-18 23:30:37', NULL, NULL);
