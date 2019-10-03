/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-10-03 15:20:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int(20) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `commentator` int(11) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modify` bigint(20) DEFAULT NULL,
  `like_count` int(11) DEFAULT '0',
  `content` varchar(255) DEFAULT NULL,
  `comment_count` int(11) unsigned zerofill DEFAULT '00000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('34', '6', '2', '6', '1570070292907', '1570070292907', '0', '1111111111111111111', '00000000000');
INSERT INTO `comment` VALUES ('35', '6', '1', '6', '1570070619130', '1570070619131', '0', '你好这是二级评论的测试内容', '00000000001');
INSERT INTO `comment` VALUES ('36', '35', '1', '6', '1570070730651', '1570070730651', '0', '你好这是二级评论的测试内容', '00000000000');
INSERT INTO `comment` VALUES ('37', '107', '2', '6', '1570076547013', '1570076547013', '0', '你好这是一条测试的评论！', '00000000001');
INSERT INTO `comment` VALUES ('38', '37', '1', '6', '1570076791859', '1570076791859', '0', '你好这是一条二级测试评论', '00000000000');
INSERT INTO `comment` VALUES ('39', '107', '2', '6', '1570084690740', '1570084690740', '0', '你好这又是一条测试评论！', '00000000000');

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier` int(255) DEFAULT NULL COMMENT '通知的人',
  `receiver` int(255) DEFAULT NULL COMMENT '接受的人',
  `outer_title` varchar(50) DEFAULT NULL COMMENT '问题或者评论的id',
  `type` int(255) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `staus` int(255) DEFAULT '0',
  `outer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('6', '6', '6', '1', '1', '1570084690753', '1', '107');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(40) DEFAULT NULL,
  `gmt_modify` bigint(40) DEFAULT NULL,
  `creator` int(40) DEFAULT NULL,
  `comment_count` int(10) unsigned zerofill DEFAULT '0000000000',
  `view_count` int(11) unsigned zerofill DEFAULT '00000000000',
  `like_count` int(11) unsigned zerofill DEFAULT '00000000000',
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('6', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000002', '00000000561', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('107', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000002', '00000000546', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('108', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000539', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('109', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000539', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('110', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000540', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('111', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000545', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('112', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('113', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('114', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('115', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('116', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('117', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('118', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('119', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('120', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('121', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');
INSERT INTO `question` VALUES ('122', '1', '请自行参考百度', '1569226716708', '1569226716708', '6', '0000000000', '00000000538', '00000000000', 'springboot');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `gmt_create` bigint(40) DEFAULT NULL,
  `gmt_modified` bigint(40) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('6', '23110935', 'Jackjoily', 'c1ebdb3e57644baa82e0b8feb5369d82', '1569402590553', '1569402590553', 'a student from qdu', 'https://avatars2.githubusercontent.com/u/23110935?v=4');
