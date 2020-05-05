-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '学生姓名',
  `student_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学号',
  `college` varchar(20) NOT NULL DEFAULT '' COMMENT '所属学院',
  `create_time` varchar(20) DEFAULT '' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;