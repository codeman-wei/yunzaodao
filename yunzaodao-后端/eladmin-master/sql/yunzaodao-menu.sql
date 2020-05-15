/*
Navicat MySQL Data Transfer

Source Server         : git
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : yunzaodao

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-05-13 19:09:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `pid` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `component_name` varchar(20) DEFAULT '-' COMMENT '组件名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKqcf9gem97gqa5qjm4d3elcqt5` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '\0', '系统管理', null, '0', '1', 'system', 'system', '\0', '\0', null, '2018-12-18 15:11:29', null, '0');
INSERT INTO `menu` VALUES ('2', '\0', '用户管理', 'system/user/index', '1', '2', 'peoples', 'user', '\0', '\0', 'User', '2018-12-18 15:14:44', 'user:list', '1');
INSERT INTO `menu` VALUES ('3', '\0', '角色管理', 'system/role/index', '1', '3', 'role', 'role', '\0', '\0', 'Role', '2018-12-18 15:16:07', 'roles:list', '1');
INSERT INTO `menu` VALUES ('5', '\0', '菜单管理', 'system/menu/index', '1', '5', 'menu', 'menu', '\0', '\0', 'Menu', '2018-12-18 15:17:28', 'menu:list', '1');
INSERT INTO `menu` VALUES ('6', '\0', '系统监控', null, '0', '10', 'monitor', 'monitor', '\0', '\0', null, '2018-12-18 15:17:48', null, '0');
INSERT INTO `menu` VALUES ('7', '\0', '操作日志', 'monitor/log/index', '6', '11', 'log', 'logs', '\0', '\0', 'Log', '2018-12-18 15:18:26', null, '1');
INSERT INTO `menu` VALUES ('9', '\0', 'SQL监控', 'monitor/sql/index', '6', '18', 'sqlMonitor', 'druid', '\0', '\0', 'Sql', '2018-12-18 15:19:34', null, '1');
INSERT INTO `menu` VALUES ('10', '\0', '组件管理', null, '0', '50', 'zujian', 'components', '\0', '\0', null, '2018-12-19 13:38:16', null, '0');
INSERT INTO `menu` VALUES ('11', '\0', '图标库', 'components/icons/index', '10', '51', 'icon', 'icon', '\0', '\0', 'Icons', '2018-12-19 13:38:49', null, '1');
INSERT INTO `menu` VALUES ('14', '\0', '邮件工具', 'tools/email/index', '36', '35', 'email', 'email', '\0', '\0', 'Email', '2018-12-27 10:13:09', null, '1');
INSERT INTO `menu` VALUES ('15', '\0', '富文本', 'components/Editor', '10', '52', 'fwb', 'tinymce', '\0', '\0', 'Editor', '2018-12-27 11:58:25', null, '1');
INSERT INTO `menu` VALUES ('16', '\0', '图床管理', 'tools/picture/index', '36', '33', 'image', 'pictures', '\0', '\0', 'Pictures', '2018-12-28 09:36:53', 'pictures:list', '1');
INSERT INTO `menu` VALUES ('18', '\0', '存储管理', 'tools/storage/index', '36', '34', 'qiniu', 'storage', '\0', '\0', 'Storage', '2018-12-31 11:12:15', 'storage:list', '1');
INSERT INTO `menu` VALUES ('19', '\0', '支付宝工具', 'tools/aliPay/index', '36', '37', 'alipay', 'aliPay', '\0', '\0', 'AliPay', '2018-12-31 14:52:38', null, '1');
INSERT INTO `menu` VALUES ('28', '\0', '定时任务', 'system/timing/index', '36', '31', 'timing', 'timing', '\0', '\0', 'Timing', '2019-01-07 20:34:40', 'timing:list', '1');
INSERT INTO `menu` VALUES ('30', '\0', '代码生成', 'generator/index', '36', '32', 'dev', 'generator', '', '\0', 'GeneratorIndex', '2019-01-11 15:45:55', null, '1');
INSERT INTO `menu` VALUES ('32', '\0', '异常日志', 'monitor/log/errorLog', '6', '12', 'error', 'errorLog', '\0', '\0', 'ErrorLog', '2019-01-13 13:49:03', null, '1');
INSERT INTO `menu` VALUES ('33', '\0', 'Markdown', 'components/MarkDown', '10', '53', 'markdown', 'markdown', '\0', '\0', 'Markdown', '2019-03-08 13:46:44', null, '1');
INSERT INTO `menu` VALUES ('34', '\0', 'Yaml编辑器', 'components/YamlEdit', '10', '54', 'dev', 'yaml', '\0', '\0', 'YamlEdit', '2019-03-08 15:49:40', null, '1');
INSERT INTO `menu` VALUES ('35', '\0', '学院管理', 'system/college/index', '118', '6', 'dept', 'college', '\0', '\0', 'College', '2019-03-25 09:46:00', 'dept:list', '1');
INSERT INTO `menu` VALUES ('36', '\0', '系统工具', '', '0', '30', 'sys-tools', 'sys-tools', '\0', '\0', null, '2019-03-29 10:57:35', null, '0');
INSERT INTO `menu` VALUES ('38', '\0', '接口文档', 'tools/swagger/index', '36', '36', 'swagger', 'swagger2', '\0', '\0', 'Swagger', '2019-03-29 19:57:53', null, '1');
INSERT INTO `menu` VALUES ('39', '\0', '字典管理', 'system/dict/index', '1', '8', 'dictionary', 'dict', '\0', '\0', 'Dict', '2019-04-10 11:49:04', 'dict:list', '1');
INSERT INTO `menu` VALUES ('41', '\0', '在线用户', 'monitor/online/index', '6', '10', 'Steve-Jobs', 'online', '\0', '\0', 'OnlineUser', '2019-10-26 22:08:43', null, '1');
INSERT INTO `menu` VALUES ('44', '\0', '用户新增', '', '2', '2', '', '', '\0', '\0', '', '2019-10-29 10:59:46', 'user:add', '2');
INSERT INTO `menu` VALUES ('45', '\0', '用户编辑', '', '2', '3', '', '', '\0', '\0', '', '2019-10-29 11:00:08', 'user:edit', '2');
INSERT INTO `menu` VALUES ('46', '\0', '用户删除', '', '2', '4', '', '', '\0', '\0', '', '2019-10-29 11:00:23', 'user:del', '2');
INSERT INTO `menu` VALUES ('48', '\0', '角色创建', '', '3', '2', '', '', '\0', '\0', '', '2019-10-29 12:45:34', 'roles:add', '2');
INSERT INTO `menu` VALUES ('49', '\0', '角色修改', '', '3', '3', '', '', '\0', '\0', '', '2019-10-29 12:46:16', 'roles:edit', '2');
INSERT INTO `menu` VALUES ('50', '\0', '角色删除', '', '3', '4', '', '', '\0', '\0', '', '2019-10-29 12:46:51', 'roles:del', '2');
INSERT INTO `menu` VALUES ('52', '\0', '菜单新增', '', '5', '2', '', '', '\0', '\0', '', '2019-10-29 12:55:07', 'menu:add', '2');
INSERT INTO `menu` VALUES ('53', '\0', '菜单编辑', '', '5', '3', '', '', '\0', '\0', '', '2019-10-29 12:55:40', 'menu:edit', '2');
INSERT INTO `menu` VALUES ('54', '\0', '菜单删除', '', '5', '4', '', '', '\0', '\0', '', '2019-10-29 12:56:00', 'menu:del', '2');
INSERT INTO `menu` VALUES ('56', '\0', '学院新增', '', '35', '2', '', '', '\0', '\0', '', '2019-10-29 12:57:09', 'dept:add', '2');
INSERT INTO `menu` VALUES ('57', '\0', '学院编辑', '', '35', '3', '', '', '\0', '\0', '', '2019-10-29 12:57:27', 'dept:edit', '2');
INSERT INTO `menu` VALUES ('58', '\0', '学院删除', '', '35', '4', '', '', '\0', '\0', '', '2019-10-29 12:57:41', 'dept:del', '2');
INSERT INTO `menu` VALUES ('64', '\0', '字典新增', '', '39', '2', '', '', '\0', '\0', '', '2019-10-29 13:00:17', 'dict:add', '2');
INSERT INTO `menu` VALUES ('65', '\0', '字典编辑', '', '39', '3', '', '', '\0', '\0', '', '2019-10-29 13:00:42', 'dict:edit', '2');
INSERT INTO `menu` VALUES ('66', '\0', '字典删除', '', '39', '4', '', '', '\0', '\0', '', '2019-10-29 13:00:59', 'dict:del', '2');
INSERT INTO `menu` VALUES ('70', '\0', '图片上传', '', '16', '2', '', '', '\0', '\0', '', '2019-10-29 13:05:34', 'pictures:add', '2');
INSERT INTO `menu` VALUES ('71', '\0', '图片删除', '', '16', '3', '', '', '\0', '\0', '', '2019-10-29 13:05:52', 'pictures:del', '2');
INSERT INTO `menu` VALUES ('73', '\0', '任务新增', '', '28', '2', '', '', '\0', '\0', '', '2019-10-29 13:07:28', 'timing:add', '2');
INSERT INTO `menu` VALUES ('74', '\0', '任务编辑', '', '28', '3', '', '', '\0', '\0', '', '2019-10-29 13:07:41', 'timing:edit', '2');
INSERT INTO `menu` VALUES ('75', '\0', '任务删除', '', '28', '4', '', '', '\0', '\0', '', '2019-10-29 13:07:54', 'timing:del', '2');
INSERT INTO `menu` VALUES ('77', '\0', '上传文件', '', '18', '2', '', '', '\0', '\0', '', '2019-10-29 13:09:09', 'storage:add', '2');
INSERT INTO `menu` VALUES ('78', '\0', '文件编辑', '', '18', '3', '', '', '\0', '\0', '', '2019-10-29 13:09:22', 'storage:edit', '2');
INSERT INTO `menu` VALUES ('79', '\0', '文件删除', '', '18', '4', '', '', '\0', '\0', '', '2019-10-29 13:09:34', 'storage:del', '2');
INSERT INTO `menu` VALUES ('80', '\0', '服务监控', 'monitor/server/index', '6', '14', 'codeConsole', 'server', '\0', '', 'ServerMonitor', '2019-11-07 13:06:39', 'server:list', '1');
INSERT INTO `menu` VALUES ('82', '\0', '生成配置', 'generator/config', '36', '33', 'dev', 'generator/config/:tableName', '', '', 'GeneratorConfig', '2019-11-17 20:08:56', '', '1');
INSERT INTO `menu` VALUES ('83', '\0', '图表库', 'components/Echarts', '10', '50', 'chart', 'echarts', '', '\0', 'Echarts', '2019-11-21 09:04:32', '', '1');
INSERT INTO `menu` VALUES ('90', '\0', '运维管理', '', '0', '20', 'mnt', 'mnt', '\0', '', 'Mnt', '2019-11-09 10:31:08', null, '0');
INSERT INTO `menu` VALUES ('92', '\0', '服务器', 'mnt/server/index', '90', '22', 'server', 'mnt/serverDeploy', '\0', '\0', 'ServerDeploy', '2019-11-10 10:29:25', 'serverDeploy:list', '1');
INSERT INTO `menu` VALUES ('93', '\0', '应用管理', 'mnt/app/index', '90', '23', 'app', 'mnt/app', '\0', '\0', 'App', '2019-11-10 11:05:16', 'app:list', '1');
INSERT INTO `menu` VALUES ('94', '\0', '部署管理', 'mnt/deploy/index', '90', '24', 'deploy', 'mnt/deploy', '\0', '\0', 'Deploy', '2019-11-10 15:56:55', 'deploy:list', '1');
INSERT INTO `menu` VALUES ('97', '\0', '部署备份', 'mnt/deployHistory/index', '90', '25', 'backup', 'mnt/deployHistory', '\0', '\0', 'DeployHistory', '2019-11-10 16:49:44', 'deployHistory:list', '1');
INSERT INTO `menu` VALUES ('98', '\0', '数据库管理', 'mnt/database/index', '90', '26', 'database', 'mnt/database', '\0', '\0', 'Database', '2019-11-10 20:40:04', 'database:list', '1');
INSERT INTO `menu` VALUES ('102', '\0', '删除', '', '97', '999', '', '', '\0', '\0', '', '2019-11-17 09:32:48', 'deployHistory:del', '2');
INSERT INTO `menu` VALUES ('103', '\0', '服务器新增', '', '92', '999', '', '', '\0', '\0', '', '2019-11-17 11:08:33', 'serverDeploy:add', '2');
INSERT INTO `menu` VALUES ('104', '\0', '服务器编辑', '', '92', '999', '', '', '\0', '\0', '', '2019-11-17 11:08:57', 'serverDeploy:edit', '2');
INSERT INTO `menu` VALUES ('105', '\0', '服务器删除', '', '92', '999', '', '', '\0', '\0', '', '2019-11-17 11:09:15', 'serverDeploy:del', '2');
INSERT INTO `menu` VALUES ('106', '\0', '应用新增', '', '93', '999', '', '', '\0', '\0', '', '2019-11-17 11:10:03', 'app:add', '2');
INSERT INTO `menu` VALUES ('107', '\0', '应用编辑', '', '93', '999', '', '', '\0', '\0', '', '2019-11-17 11:10:28', 'app:edit', '2');
INSERT INTO `menu` VALUES ('108', '\0', '应用删除', '', '93', '999', '', '', '\0', '\0', '', '2019-11-17 11:10:55', 'app:del', '2');
INSERT INTO `menu` VALUES ('109', '\0', '部署新增', '', '94', '999', '', '', '\0', '\0', '', '2019-11-17 11:11:22', 'deploy:add', '2');
INSERT INTO `menu` VALUES ('110', '\0', '部署编辑', '', '94', '999', '', '', '\0', '\0', '', '2019-11-17 11:11:41', 'deploy:edit', '2');
INSERT INTO `menu` VALUES ('111', '\0', '部署删除', '', '94', '999', '', '', '\0', '\0', '', '2019-11-17 11:12:01', 'deploy:del', '2');
INSERT INTO `menu` VALUES ('112', '\0', '数据库新增', '', '98', '999', '', '', '\0', '\0', '', '2019-11-17 11:12:43', 'database:add', '2');
INSERT INTO `menu` VALUES ('113', '\0', '数据库编辑', '', '98', '999', '', '', '\0', '\0', '', '2019-11-17 11:12:58', 'database:edit', '2');
INSERT INTO `menu` VALUES ('114', '\0', '数据库删除', '', '98', '999', '', '', '\0', '\0', '', '2019-11-17 11:13:14', 'database:del', '2');
INSERT INTO `menu` VALUES ('116', '\0', '生成预览', 'generator/preview', '36', '999', 'java', 'generator/preview/:tableName', '', '', 'Preview', '2019-11-26 14:54:36', null, '1');
INSERT INTO `menu` VALUES ('117', '\0', '课程管理', 'system/course/index', '118', '1', 'date', 'course', '\0', '\0', 'Course', '2020-03-20 10:14:04', 'course:lsit', '1');
INSERT INTO `menu` VALUES ('118', '\0', '班课管理', null, '0', '3', 'sys-tools', 'study', '\0', '\0', null, '2020-03-27 08:58:38', null, '0');
INSERT INTO `menu` VALUES ('119', '\0', '签到管理', 'system/signHistory/index', '118', '3', 'nested', 'sign', '\0', '\0', 'Sign', '2020-05-10 13:40:15', 'sign:list', '1');
INSERT INTO `menu` VALUES ('120', '\0', '学生管理', 'system/student/index', '118', '4', 'people', 'student', '\0', '\0', 'Student', '2020-05-10 13:43:08', 'student:list', '1');
