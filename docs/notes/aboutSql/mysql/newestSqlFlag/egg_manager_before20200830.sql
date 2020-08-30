/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : egg_manager

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2020-08-30 22:05:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for em_announcement
-- ----------------------------
DROP TABLE IF EXISTS `em_announcement`;
CREATE TABLE `em_announcement` (
  `fid` char(36) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `key_word` varchar(100) DEFAULT NULL COMMENT '关键字',
  `publish_department` char(36) DEFAULT NULL COMMENT '发布的部门',
  `content` text,
  `tag_ids` varchar(1000) DEFAULT NULL COMMENT '公告标签',
  `accessory` varchar(255) DEFAULT NULL COMMENT '附件',
  `state` tinyint(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_announcement_draft
-- ----------------------------
DROP TABLE IF EXISTS `em_announcement_draft`;
CREATE TABLE `em_announcement_draft` (
  `fid` char(36) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `key_word` varchar(100) DEFAULT NULL COMMENT '关键字',
  `publish_department` char(36) DEFAULT NULL COMMENT '发布的部门',
  `content` text,
  `tag_ids` varchar(1000) DEFAULT NULL COMMENT '公告标签',
  `accessory` varchar(255) DEFAULT NULL COMMENT '附件',
  `is_published` tinyint(11) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_announcement_tag
-- ----------------------------
DROP TABLE IF EXISTS `em_announcement_tag`;
CREATE TABLE `em_announcement_tag` (
  `fid` char(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `ordering` int(11) DEFAULT NULL COMMENT '排序',
  `state` tinyint(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_bill_record
-- ----------------------------
DROP TABLE IF EXISTS `em_bill_record`;
CREATE TABLE `em_bill_record` (
  `fid` char(36) NOT NULL,
  `bill_type` char(36) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `quantity` decimal(10,2) DEFAULT NULL COMMENT '个数',
  `total_price` decimal(10,2) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL COMMENT '记录的时间',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_bill_type
-- ----------------------------
DROP TABLE IF EXISTS `em_bill_type`;
CREATE TABLE `em_bill_type` (
  `fid` varchar(36) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type_num` int(11) DEFAULT NULL,
  `default_unit_price` decimal(10,2) DEFAULT NULL COMMENT '默认单价',
  `remark` varchar(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user` varchar(36) DEFAULT NULL,
  `last_modifyer` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单类型';

-- ----------------------------
-- Table structure for em_define_department
-- ----------------------------
DROP TABLE IF EXISTS `em_define_department`;
CREATE TABLE `em_define_department` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `parent_id` char(36) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定义的部门';

-- ----------------------------
-- Table structure for em_define_group
-- ----------------------------
DROP TABLE IF EXISTS `em_define_group`;
CREATE TABLE `em_define_group` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` char(36) DEFAULT NULL,
  `is_inherit` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定义的用户组';

-- ----------------------------
-- Table structure for em_define_job
-- ----------------------------
DROP TABLE IF EXISTS `em_define_job`;
CREATE TABLE `em_define_job` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_define_menu
-- ----------------------------
DROP TABLE IF EXISTS `em_define_menu`;
CREATE TABLE `em_define_menu` (
  `fid` char(36) NOT NULL DEFAULT '',
  `parent_id` char(36) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `icon_name` varchar(100) DEFAULT NULL,
  `router_url` varchar(255) DEFAULT NULL,
  `href_url` varchar(255) DEFAULT NULL,
  `url_jump_type` int(11) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `excel_model_conf` text,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';

-- ----------------------------
-- Table structure for em_define_module
-- ----------------------------
DROP TABLE IF EXISTS `em_define_module`;
CREATE TABLE `em_define_module` (
  `fid` char(36) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,
  `type` int(50) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for em_define_permission
-- ----------------------------
DROP TABLE IF EXISTS `em_define_permission`;
CREATE TABLE `em_define_permission` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `ensure` tinyint(4) DEFAULT NULL COMMENT '是否确认发布',
  `type` int(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限表';

-- ----------------------------
-- Table structure for em_define_role
-- ----------------------------
DROP TABLE IF EXISTS `em_define_role`;
CREATE TABLE `em_define_role` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色定义表';

-- ----------------------------
-- Table structure for em_define_tenant
-- ----------------------------
DROP TABLE IF EXISTS `em_define_tenant`;
CREATE TABLE `em_define_tenant` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '租户名',
  `code` varchar(255) DEFAULT NULL,
  `db_code` varchar(255) DEFAULT NULL COMMENT '数据库标识',
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='租户定义表';

-- ----------------------------
-- Table structure for em_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `em_operation_log`;
CREATE TABLE `em_operation_log` (
  `fid` char(36) NOT NULL COMMENT '主键',
  `user_account_id` varchar(50) DEFAULT NULL COMMENT '用户主键',
  `ip_addr` varchar(32) DEFAULT NULL,
  `class_name` varchar(300) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `action_args` text COMMENT '方法参数',
  `result` text,
  `exception` text,
  `action` varchar(50) DEFAULT NULL COMMENT '操作',
  `model_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `log_description` varchar(64) DEFAULT NULL COMMENT '日志描述',
  `signature_long` text COMMENT '请求的方法完整内容',
  `aspect_kind` varchar(255) DEFAULT NULL,
  `aspect_notify_type` varchar(255) DEFAULT NULL COMMENT 'aop通知方式',
  `token_bean` varchar(255) DEFAULT NULL COMMENT '发起请求的token ->json',
  `headers` varchar(255) DEFAULT NULL COMMENT '发起请求的header ->json',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '发起请求的uri',
  `request_url` varchar(255) DEFAULT NULL COMMENT '发起请求的路径',
  `return_type_name` varchar(255) DEFAULT NULL COMMENT '返回值类型',
  `declared_annotations` text COMMENT '定义的注解->json',
  `is_success` int(2) DEFAULT NULL COMMENT '是否成功 1:成功 2异常',
  `message` longtext COMMENT '异常堆栈信息',
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='操作日志表';

-- ----------------------------
-- Table structure for em_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `em_role_menu`;
CREATE TABLE `em_role_menu` (
  `fid` char(36) NOT NULL,
  `define_role_id` char(36) DEFAULT NULL,
  `define_menu_id` char(36) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色授权菜单';

-- ----------------------------
-- Table structure for em_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `em_role_permission`;
CREATE TABLE `em_role_permission` (
  `fid` char(36) NOT NULL,
  `define_role_id` char(36) DEFAULT NULL,
  `define_permission_id` char(36) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限';

-- ----------------------------
-- Table structure for em_user_account
-- ----------------------------
DROP TABLE IF EXISTS `em_user_account`;
CREATE TABLE `em_user_account` (
  `fid` varchar(50) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `account` varchar(100) NOT NULL,
  `nick_name` varchar(100) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(36) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` tinyint(11) DEFAULT NULL,
  `user_type_num` int(11) DEFAULT NULL,
  `user_type` int(255) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `locked` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户账号表';

-- ----------------------------
-- Table structure for em_user_group
-- ----------------------------
DROP TABLE IF EXISTS `em_user_group`;
CREATE TABLE `em_user_group` (
  `fid` char(36) NOT NULL,
  `define_group_id` char(36) DEFAULT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户组';

-- ----------------------------
-- Table structure for em_user_job
-- ----------------------------
DROP TABLE IF EXISTS `em_user_job`;
CREATE TABLE `em_user_job` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `define_job_id` char(36) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户-职务';

-- ----------------------------
-- Table structure for em_user_role
-- ----------------------------
DROP TABLE IF EXISTS `em_user_role`;
CREATE TABLE `em_user_role` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `define_role_id` char(36) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色';

-- ----------------------------
-- Table structure for em_user_tenant
-- ----------------------------
DROP TABLE IF EXISTS `em_user_tenant`;
CREATE TABLE `em_user_tenant` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `define_tenant_id` char(36) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `is_manager` tinyint(11) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户与租户关联';

-- ----------------------------
-- Table structure for em_user_thirdparty
-- ----------------------------
DROP TABLE IF EXISTS `em_user_thirdparty`;
CREATE TABLE `em_user_thirdparty` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `open_id` char(50) DEFAULT NULL,
  `provider_type` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `state` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `last_modifyer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for test1
-- ----------------------------
DROP TABLE IF EXISTS `test1`;
CREATE TABLE `test1` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
