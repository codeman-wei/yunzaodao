/*
Navicat MySQL Data Transfer

Source Server         : LocalSQL
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : earlytoclouds

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-04-26 21:57:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `pid` bigint(20) NOT NULL COMMENT '上级部门',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';


-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '福州大学', '0', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('7', '数学与计算机科学学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('8', '电气工程及其自动化学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('13', '经济与管理学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('116', '机械工程及自动化学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('117', '化学化工学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('118', '土木工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('119', '环境与资源学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('120', '生物科学与工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('121', '外国语学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('122', '物理与信息工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('123', '建筑学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('124', '人文社会科学学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('125', '马克思主义学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('126', '厦门工艺美术学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('127', '材料科学与工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('128', '法学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('129', '紫金矿业学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('130', '八方物流学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('131', '应用技术与继续教育学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('132', '海外教育学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('133', '至诚学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('134', '阳光学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('135', '从教任职', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('136', '信息中心', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('137', '图书馆', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('138', '空间中心', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('139', '生物和医药技术研究院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('140', '机械工程及自动化学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('141', '化学化工学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('142', '土木工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('143', '环境与资源学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('144', '生物科学与工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('145', '外国语学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('146', '物理与信息工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('147', '建筑学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('148', '人文社会科学学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('149', '马克思主义学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('150', '厦门工艺美术学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('151', '材料科学与工程学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('152', '法学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('153', '紫金矿业学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('154', '八方物流学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('155', '应用技术与继续教育学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('156', '海外教育学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('157', '体育教学部', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('158', '至诚学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('159', '阳光学院', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('160', '从教任职', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('161', '信息中心', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('162', '图书馆', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('163', '空间中心', '1', b'1', '2020-04-26 21:56:16');
INSERT INTO `dept` VALUES ('164', '生物和医药技术研究院', '1', b'1', '2020-04-26 21:56:16');
