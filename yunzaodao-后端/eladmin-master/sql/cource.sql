/*
Navicat MySQL Data Transfer

Source Server         : LocalSQL
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : earlytoclouds

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-03-19 22:35:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `course_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '课程名称',
  `course_code` varchar(10) NOT NULL DEFAULT '' COMMENT '课程代码',
  `course_place` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '上课地点，星期与时间用；间隔',
  `course_time` varchar(30) DEFAULT '' COMMENT '上课时间',
  `student_count` int(4) unsigned zerofill DEFAULT NULL COMMENT '选课人数',
  `teacher_name` varchar(10) DEFAULT '' COMMENT '授课教师姓名',
  `belong_college` varchar(10) DEFAULT '' COMMENT '课程归属学院',
  `create_uid` int(15) unsigned zerofill DEFAULT NULL COMMENT '课程创建者uid',
  `sign_count` int(3) unsigned zerofill DEFAULT NULL COMMENT '签到发起次数',
  `start_time` varchar(15) DEFAULT '' COMMENT '签到发起时间',
  `end_time` varchar(15) DEFAULT '' COMMENT '签到截止时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '工程实践', '003456003', '数计中-301', '周六;8:00--12:00', '0145', '池芝标', '数计学院', '000000000000001', '000', '', '');
INSERT INTO `course` VALUES ('2', '网络安全', '003456007', '东3-505', '周三；14:00--16:00', '0030', '廖祥文', '数计学院', '000000000000001', '000', '', '');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `name` varchar(5) NOT NULL DEFAULT '' COMMENT '学生姓名',
  `student_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学号',
  `college` varchar(10) NOT NULL DEFAULT '' COMMENT '所属学院',
  `major` varchar(10) NOT NULL DEFAULT '' COMMENT '专业',
  `education` varchar(5) NOT NULL DEFAULT '' COMMENT '学历',
  `create_time` varchar(255) DEFAULT '' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `name` varchar(5) NOT NULL DEFAULT '' COMMENT '教师姓名',
  `job_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '教职工号',
  `unisvercity` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属院校',
  `college` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属学院',
  `course` varchar(20) NOT NULL DEFAULT '' COMMENT '课程名称',
  `create_time` varchar(10) DEFAULT '' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
