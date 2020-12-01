#基本表结构
CREATE TABLE `obl_` (
  `fid` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `create_user_id` char(19) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modifyer_id` char(19) DEFAULT NULL COMMENT '最后修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) DEFAULT NULL COMMENT '是否已删除?0:否1:是',
  `deleted_time` int(11) DEFAULT NULL COMMENT '数据删除时间',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='xxx表';