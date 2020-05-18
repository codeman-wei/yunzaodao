/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : yunzaodao

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 18/05/2020 15:54:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_password_reset_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `college_id` bigint(0) NULL DEFAULT NULL,
  `enabled` bit(1) NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKgtle6mm88hvntwsdice7jaapj`(`college_id`) USING BTREE,
  CONSTRAINT `FKgtle6mm88hvntwsdice7jaapj` FOREIGN KEY (`college_id`) REFERENCES `dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '梁少斌', '男', '15988164559', '1101945419@qq.com', '190320015', '2020-05-17 10:36:13', '2020-05-10 13:58:24', 8, b'1', '$2a$10$pmGlm9rGidPdTSoZiAahGu1Fsfp8jVX41A7Gp221AKRMVVFb56Pku');
INSERT INTO `student` VALUES (2, '陈甘霖', '男', '15980266968', '460678255@qq.com', '031502522', NULL, '2020-05-10 13:58:36', 1, b'1', '$2a$10$yqM2grrTO9vgg/vKfhZLoOuTUMmx3vZw3QXES4a2bkVf2FgJHT1BO');
INSERT INTO `student` VALUES (3, '蔡鸿杰', '男', '15980266233', '928383651@qq.com', '190327001', NULL, '2020-05-13 19:26:46', 116, b'1', '$2a$10$upShgjmXeSJg.2WwlIQbBeTwnC7ruRfWXDtxXwtA5uptjrnSkj7AS');
INSERT INTO `student` VALUES (4, '林建洲', '男', '15659877459', '724117508qq.com', '190327055', NULL, '2020-05-13 19:27:33', 119, b'1', '$2a$10$1RjSb5/3i/R3Hys9CqGcgODmzk3BbYkPRn5fPUUab3gc7nxsTC5Jm');
INSERT INTO `student` VALUES (5, '林树凯', '男', '1598665329', '1035422136@qq.com', '190327058', NULL, '2020-05-13 19:32:40', 116, b'1', '$2a$10$1yQkKYMkdsCRpTLP2wbXje//n0jHnPIkri5v8M6xRDKCa/N/rTbwK');
INSERT INTO `student` VALUES (6, '王灿杰', '男', '15874966963', '896567891@qq.com', '190327071', NULL, '2020-05-13 19:37:42', 13, b'1', '$2a$10$tUamkQu9Su1sZMgvwYnItuZjtkuTT9p7Mer.39hDjEXGcT3lOmxWi');
INSERT INTO `student` VALUES (7, '朱雨航', '男', '15749822639', '645544531@qq.com', '190327106', NULL, '2020-05-13 19:38:30', 119, b'1', '$2a$10$uujBZbWnh2Rf0cMjwq5DSet2wMjjYWK2WPlMDIA2sej1gIeLZFZ0W');
INSERT INTO `student` VALUES (8, '吴君毅', '男', '15846559725', '867588970@qq.com', '190327081', NULL, '2020-05-13 19:44:21', 7, b'1', '$2a$10$kXO9UgQH5JZ65gIqRbJfWelC1IlD/ML1d6AiX2HvxwA4WCWsOUt2G');
INSERT INTO `student` VALUES (9, '熊乾程', '男', '15688165559', '910079546@qq.com', '190327084', NULL, '2020-05-14 08:07:43', 8, b'1', '$2a$10$v.0QoubgN3mvIEgNMSIrEOIrc2L11Eg6v4WkzC2j.WeDUFm/dbUDu');
INSERT INTO `student` VALUES (10, '郭星宇', '男', '15666165559', '91045546@qq.com', '190d327022', NULL, '2020-05-17 14:02:42', 8, b'1', '123456');
INSERT INTO `student` VALUES (12, '王灿杰', NULL, '15695917757', '896567891@qq.com', '190327071', NULL, '2020-05-18 15:42:40', 8, b'1', '$2a$10$LOY3Tb.14PGkiU8vReZnlOuQy6nJzu5OfjoGV.jI/GmeV2siYP.Ru');

SET FOREIGN_KEY_CHECKS = 1;
