/*
Navicat MySQL Data Transfer

Source Server         : myblog
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : egg_manager

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-09-13 00:15:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for define_group
-- ----------------------------
DROP TABLE IF EXISTS `define_group`;
CREATE TABLE `define_group` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` char(36) DEFAULT NULL,
  `is_inherit` int(11) DEFAULT NULL,
  `type` varchar(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义的用户组';

-- ----------------------------
-- Table structure for define_menu
-- ----------------------------
DROP TABLE IF EXISTS `define_menu`;
CREATE TABLE `define_menu` (
  `fid` char(36) NOT NULL DEFAULT '',
  `define_module_id` char(36) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `type` varchar(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for define_permission
-- ----------------------------
DROP TABLE IF EXISTS `define_permission`;
CREATE TABLE `define_permission` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for define_role
-- ----------------------------
DROP TABLE IF EXISTS `define_role`;
CREATE TABLE `define_role` (
  `fid` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色定义表';

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `fid` char(36) NOT NULL,
  `define_role_id` char(36) DEFAULT NULL,
  `define_permission_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
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
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号表';

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `fid` char(36) NOT NULL,
  `define_group_id` char(36) DEFAULT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `fid` char(36) NOT NULL,
  `user_account_id` char(36) DEFAULT NULL,
  `user_role_id` char(36) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';
