CREATE TABLE `em_user_department` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) NOT NULL COMMENT '用户账号id',
  `define_department_id` char(36) NOT NULL COMMENT '部门id',
  `type` int(255) DEFAULT NULL COMMENT '类型',
  `is_manager` tinyint(11) DEFAULT NULL  COMMENT '是否部门管理员',
  `state` tinyint(11) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL  COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL  COMMENT '修改时间',
  `remark` varchar(100) DEFAULT NULL  COMMENT '备注',
  `create_user_id` varchar(50) DEFAULT NULL  COMMENT '创建人id',
  `last_modifyer_id` varchar(50) DEFAULT NULL COMMENT '最后修改人id',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户与部门关联表';