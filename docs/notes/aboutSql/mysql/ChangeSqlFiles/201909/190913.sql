/*
Navicat MySQL Data Transfer

Source Server         : myblog
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : egg_manager

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-09-13 23:47:49
*/

SET FOREIGN_KEY_CHECKS=0;

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
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义的用户组';

-- ----------------------------
-- Table structure for em_define_menu
-- ----------------------------
DROP TABLE IF EXISTS `em_define_menu`;
CREATE TABLE `em_define_menu` (
  `fid` char(36) NOT NULL DEFAULT '',
  `define_module_id` char(36) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `type` varchar(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for em_define_permission
-- ----------------------------
DROP TABLE IF EXISTS `em_define_permission`;
CREATE TABLE `em_define_permission` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for em_define_role
-- ----------------------------
DROP TABLE IF EXISTS `em_define_role`;
CREATE TABLE `em_define_role` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色定义表';

-- ----------------------------
-- Table structure for em_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `em_operation_log`;
CREATE TABLE `em_operation_log` (
  `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `action_args` varchar(300) DEFAULT NULL COMMENT '方法参数',
  `user_account_id` varchar(50) DEFAULT NULL COMMENT '用户主键',
  `class_name` varchar(300) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `ip_addr` varchar(32) DEFAULT NULL,
  `model_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `log_description` varchar(64) DEFAULT NULL COMMENT '日志描述',
  `action` varchar(50) DEFAULT NULL COMMENT '操作',
  `is_success` int(2) DEFAULT NULL COMMENT '是否成功 1:成功 2异常',
  `message` longtext COMMENT '异常堆栈信息',
  `type` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Table structure for em_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `em_role_permission`;
CREATE TABLE `em_role_permission` (
  `fid` char(36) NOT NULL,
  `define_role_id` char(36) DEFAULT NULL,
  `define_permission_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Table structure for em_user_account
-- ----------------------------
DROP TABLE IF EXISTS `em_user_account`;
CREATE TABLE `em_user_account` (
  `fid` varchar(50) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `nick_name` varchar(100) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `phone` varchar(36) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `sex` int(11) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号表';

-- ----------------------------
-- Table structure for em_user_group
-- ----------------------------
DROP TABLE IF EXISTS `em_user_group`;
CREATE TABLE `em_user_group` (
  `fid` char(36) NOT NULL,
  `define_group_id` char(36) DEFAULT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Table structure for em_user_role
-- ----------------------------
DROP TABLE IF EXISTS `em_user_role`;
CREATE TABLE `em_user_role` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `user_role_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

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
  `state` int(11) DEFAULT NULL,
  `version` int(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
