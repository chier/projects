/*
Navicat MySQL Data Transfer

Source Server         : mysql_local
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : codeweb

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-07-13 18:10:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bbs_module
-- ----------------------------
DROP TABLE IF EXISTS `bbs_module`;
CREATE TABLE `bbs_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  `turn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_module
-- ----------------------------
INSERT INTO `bbs_module` VALUES ('1', '课程', '', '1');
INSERT INTO `bbs_module` VALUES ('2', 'BBS', null, '2');

-- ----------------------------
-- Table structure for bbs_post
-- ----------------------------
DROP TABLE IF EXISTS `bbs_post`;
CREATE TABLE `bbs_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `content` text NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `has_reply` bit(1) NOT NULL DEFAULT b'0',
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topicID_P` (`topic_id`),
  KEY `userID_P` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_post
-- ----------------------------
INSERT INTO `bbs_post` VALUES ('201', '59', '1', '<div style=\"margin:0px 0px 0px 15px;padding:20px 0px;font-family:Verdana, Arial, 宋体;background-color:#F9F9F9;\">\r\n	<h2 style=\"font-family:微软雅黑;font-size:14px;\">\r\n		实例\r\n	</h2>\r\n	<p>\r\n		本例演示如何通过 closest() 完成事件委托。当被最接近的列表元素或其子后代元素被点击时，会切换黄色背景：\r\n	</p>\r\n<pre>$( document ).bind(\"click\", function( e ) {\r\n    $( e.target ).closest(\"li\").toggleClass(\"hilight\");\r\n  });\r\n</pre>\r\n	<p class=\"tiy\" style=\"vertical-align:middle;color:#FFFFFF;text-align:center;background-color:#F88E8B;\">\r\n		<a target=\"_blank\" href=\"http://www.w3school.com.cn/tiy/t.asp?f=jquery_traversing_closest\">亲自试一试</a>\r\n	</p>\r\n</div>\r\n<div style=\"margin:0px 0px 0px 15px;padding:20px 0px;font-family:Verdana, Arial, 宋体;background-color:#F9F9F9;\">\r\n	<h2 style=\"font-family:微软雅黑;font-size:14px;\">\r\n		定义和用法\r\n	</h2>\r\n	<p>\r\n		closest() 方法获得匹配选择器的第一个祖先元素，从当前元素开始沿 DOM 树向上。\r\n	</p>\r\n</div>', '2016-07-13 10:52:31', '\0', null);
INSERT INTO `bbs_post` VALUES ('202', '61', '1', '<p>&nbsp; &nbsp; &nbsp;dsf df&nbsp;</p>', '2016-07-13 11:47:17', '\0', null);
INSERT INTO `bbs_post` VALUES ('203', '61', '1', '<p>&nbsp;<img src=\"/codeweb//bbs/showPic/1468381645615blob.png\" _src=\"/codeweb//bbs/showPic/1468381645615blob.png\"/></p>', '2016-07-13 11:47:29', '\0', null);
INSERT INTO `bbs_post` VALUES ('204', '62', '1', '<p>dsfsdfd</p><p><br/></p><p><span style=\"line-height: 1.5;\">fd fd<br/>dffdf</span></p><p><span style=\"line-height: 1.5;\">dfdf<span><span><span>df&nbsp;<span style=\"background-color: rgb(221, 217, 195);\">fdfdf d &nbsp;dfd&nbsp;</span></span></span></span></span></p><p><span style=\"line-height: 1.5;\"><span><span><span><span style=\"background-color: rgb(221, 217, 195);\"><br/></span></span></span></span></span></p><p><span style=\"line-height: 1.5;\"><span><span><span><span style=\"background-color: rgb(221, 217, 195);\"><img src=\"/codeweb//bbs/showPic/1468381782525blob.png\" _src=\"/codeweb//bbs/showPic/1468381782525blob.png\"/></span></span></span></span></span></p><p><span style=\"line-height: 1.5;\"><span><span><span><span style=\"background-color: rgb(221, 217, 195);\"><br/></span></span></span></span></span></p><p><span style=\"line-height: 1.5;\"><span><span><span><span style=\"background-color: rgb(221, 217, 195);\"><br/></span></span></span></span></span></p>', '2016-07-13 11:49:45', '\0', null);
INSERT INTO `bbs_post` VALUES ('205', '62', '1', '<p><img src=\"/codeweb//bbs/showPic/1468381801253blob.png\" _src=\"/codeweb//bbs/showPic/1468381801253blob.png\"/></p>', '2016-07-13 11:50:04', '\0', null);
INSERT INTO `bbs_post` VALUES ('209', '64', '1', '<p>&nbsp; &nbsp; &nbsp;sdf sdfs</p>', '2016-07-13 13:25:37', '\0', null);
INSERT INTO `bbs_post` VALUES ('210', '65', '1', '<p>&nbsp; &nbsp; &nbsp;sdfdfsdfsdfsdf</p>', '2016-07-13 13:27:06', '\0', null);
INSERT INTO `bbs_post` VALUES ('211', '66', '1', '<p>sdfsdfsd</p>', '2016-07-13 13:27:28', '\0', null);
INSERT INTO `bbs_post` VALUES ('212', '66', '1', '<p>&nbsp; &nbsp; &nbsp;dsfsdf&nbsp;</p>', '2016-07-13 13:27:47', '\0', null);
INSERT INTO `bbs_post` VALUES ('213', '66', '1', '<p><a href=\"http://127.0.0.1:7700/codeweb/bbs/topic/66-1\" target=\"_blank\" title=\"课程\">http://127.0.0.1:7700/codeweb/bbs/topic/66-1&nbsp;</a></p>', '2016-07-13 13:47:07', '\0', null);
INSERT INTO `bbs_post` VALUES ('214', '67', '1', '<h2>第三方斯蒂芬放到</h2><p>dfdfdf<br/></p><p><br/></p><p><br/></p>', '2016-07-13 13:49:12', '\0', null);
INSERT INTO `bbs_post` VALUES ('215', '68', '1', '<p><img src=\"/codeweb//bbs/showPic/1468389086446blob.png\" _src=\"/codeweb//bbs/showPic/1468389086446blob.png\"/></p>', '2016-07-13 13:51:28', '\0', null);
INSERT INTO `bbs_post` VALUES ('216', '69', '4', '<p><img src=\"/codeweb//bbs/showPic/1468391755464blob.png\" _src=\"/codeweb//bbs/showPic/1468391755464blob.png\" style=\"width: 754px; height: 585px;\"/></p><p><br/></p><p><br/></p><p>ok，试试看了多发点 多发点，明天搞</p>', '2016-07-13 14:35:57', '\0', null);
INSERT INTO `bbs_post` VALUES ('217', '69', '4', '<p>&nbsp; &nbsp;<img src=\"/codeweb//bbs/showPic/1468391773228blob.png\" _src=\"/codeweb//bbs/showPic/1468391773228blob.png\" style=\"width: 680px; height: 445px;\"/></p>', '2016-07-13 14:36:14', '\0', null);
INSERT INTO `bbs_post` VALUES ('218', '69', '4', '<p>什么时候讲？</p>', '2016-07-13 14:37:49', '\0', null);
INSERT INTO `bbs_post` VALUES ('219', '69', '4', '<p><img src=\"/codeweb//bbs/showPic/1468392229548blob.png\" _src=\"/codeweb//bbs/showPic/1468392229548blob.png\" style=\"width: 700px; height: 444px;\"/></p>', '2016-07-13 14:43:51', '\0', null);

-- ----------------------------
-- Table structure for bbs_reply
-- ----------------------------
DROP TABLE IF EXISTS `bbs_reply`;
CREATE TABLE `bbs_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL DEFAULT '1',
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `content` varchar(300) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `topicID_R` (`topic_id`),
  KEY `postID_R` (`post_id`),
  KEY `userID_R` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_reply
-- ----------------------------
INSERT INTO `bbs_reply` VALUES ('3', '59', '201', '1', '三东方闪电', '2016-07-13 10:52:40');
INSERT INTO `bbs_reply` VALUES ('4', '59', '201', '1', '辅导费', '2016-07-13 10:52:42');
INSERT INTO `bbs_reply` VALUES ('5', '59', '201', '1', '第三代', '2016-07-13 11:09:03');
INSERT INTO `bbs_reply` VALUES ('6', '61', '203', '1', 'dsfds ', '2016-07-13 11:47:33');
INSERT INTO `bbs_reply` VALUES ('7', '61', '203', '1', 'df ', '2016-07-13 11:47:35');
INSERT INTO `bbs_reply` VALUES ('8', '62', '205', '1', 'thanks', '2016-07-13 11:50:23');
INSERT INTO `bbs_reply` VALUES ('9', '62', '204', '1', 'dsfsdf dfd ', '2016-07-13 11:51:49');
INSERT INTO `bbs_reply` VALUES ('12', '66', '211', '1', 'fdfdf', '2016-07-13 13:27:52');
INSERT INTO `bbs_reply` VALUES ('13', '68', '215', '1', '好困难', '2016-07-13 13:51:38');
INSERT INTO `bbs_reply` VALUES ('14', '69', '216', '4', '看着不错', '2016-07-13 14:37:30');
INSERT INTO `bbs_reply` VALUES ('15', '69', '216', '4', '精彩', '2016-07-13 14:37:34');
INSERT INTO `bbs_reply` VALUES ('16', '69', '218', '4', '有时间就讲', '2016-07-13 14:43:19');

-- ----------------------------
-- Table structure for bbs_topic
-- ----------------------------
DROP TABLE IF EXISTS `bbs_topic`;
CREATE TABLE `bbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `module_id` int(11) NOT NULL,
  `post_count` int(11) NOT NULL DEFAULT '1',
  `reply_count` int(11) NOT NULL DEFAULT '0',
  `pv` int(11) NOT NULL DEFAULT '0',
  `content` varchar(150) NOT NULL,
  `emotion` tinyint(2) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_nice` bit(1) NOT NULL DEFAULT b'0',
  `is_up` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `moduleID_T` (`module_id`),
  KEY `userID_T` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_topic
-- ----------------------------
INSERT INTO `bbs_topic` VALUES ('59', '1', '2', '1', '0', '4', '再发表一次看看那', null, '2016-07-13 10:52:31', '\0', '');
INSERT INTO `bbs_topic` VALUES ('60', '1', '2', '1', '0', '3', '地方对双方都', null, '2016-07-13 11:45:14', '', '');
INSERT INTO `bbs_topic` VALUES ('61', '1', '2', '2', '0', '4', 'dfdf ', null, '2016-07-13 11:47:17', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('62', '1', '2', '2', '0', '12', '再发表一次看看那', null, '2016-07-13 11:49:45', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('64', '1', '2', '1', '0', '3', 'sdfsdf', null, '2016-07-13 13:25:37', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('65', '1', '1', '1', '0', '2', 'sfsfs', null, '2016-07-13 13:27:06', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('66', '1', '1', '3', '0', '10', 'hello go', null, '2016-07-13 13:27:28', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('67', '1', '2', '1', '0', '2', '', null, '2016-07-13 13:49:12', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('68', '1', '1', '1', '0', '9', '关于什么什么的课程卡缴纳困难是发顺丰的', null, '2016-07-13 13:51:28', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('69', '4', '1', '4', '0', '82', 'Zookeeper', null, '2016-07-13 14:35:57', '', '');


-- ----------------------------
-- Table structure for bbs_user
-- ----------------------------
DROP TABLE IF EXISTS `bbs_user`;
CREATE TABLE `bbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  
  `score` int(11) DEFAULT '0' COMMENT '积分',
  `level` int(11) DEFAULT '1' COMMENT '积分换算成等级，新生，老生，班主任，教导主任，校长',
  `balance` int(11) DEFAULT '0' COMMENT '积分余额',
  `corp` varchar(128) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of git_user
-- ----------------------------
INSERT INTO `bbs_user` VALUES ('1', 'xxx', 'e10adc3949ba59abbe56e057f20f883e', 'xxx', '0', '1','0', 'xxx');
INSERT INTO `bbs_user` VALUES ('4', '李家智', 'e10adc3949ba59abbe56e057f20f883e', null, '140', '2','0', 'xxx');
INSERT INTO `bbs_user` VALUES ('5', '赵晴文', 'e10adc3949ba59abbe56e057f20f883e', 'zhaoqingwen@coamc.com', '1000', '5', '0', 'xxx');
INSERT INTO `bbs_user` VALUES ('6', '石萌', 'e10adc3949ba59abbe56e057f20f883e', 'shimeng@coamc.com', '12', '1', '0', 'xxx');
INSERT INTO `bbs_user` VALUES ('95', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'xxxx@coamc.com', '0', '1', '0', 'xxx');

