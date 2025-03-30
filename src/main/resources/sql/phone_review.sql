/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : phone_review

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 30/03/2025 16:21:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前公告的状态（published、schedule定时等等）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布的时间',
  `priority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优先级',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `schedule_time` datetime NULL DEFAULT NULL COMMENT '定时发布的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, 'testes', '2025-03-29 12:13:24', NULL, 'test', 'published', '2025-03-29 04:13:18', 'medium', NULL, NULL);

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机品牌的名称',
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机品牌的英文名称',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机品牌的LOGO',
  `status` int(11) NULL DEFAULT NULL COMMENT '当前品牌的状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌的描述',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机品牌所属的国家',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, '小米', 'Xiaomi', 'http://localhost:8080/images/e155bbc5-5ba4-4dc6-9775-d11ea9c47753.jpg', 1, '小米手机', '中国', NULL);
INSERT INTO `brand` VALUES (2, '华为', 'HuaWei', 'http://localhost:8080/images/1294dc54-c23e-4aaf-81fe-9cd8784b785d.jpg', 1, '华为手机', '中国', '2025-03-29 19:54:18');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '文章主键id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '发表评论的用户id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '回复用户评论的用户id（被回复）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发表评论的时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `post_id` bigint(20) NULL DEFAULT NULL COMMENT '文章的id',
  `favorite` int(11) NULL DEFAULT NULL COMMENT '评论的点赞量',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论的状态(appending，approved，rejected）',
  `root_id` bigint(20) NULL DEFAULT NULL COMMENT '最顶层的评论id（用于回复时使用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 'test', 1, NULL, '2025-03-29 22:15:05', 0, 1, NULL, 'approved', NULL);
INSERT INTO `comment` VALUES (2, '你好啊', 1, NULL, '2025-03-29 22:48:02', 0, 1, NULL, 'rejected', NULL);
INSERT INTO `comment` VALUES (3, 'testsetse', 1, NULL, '2025-03-29 22:49:39', 0, 1, NULL, 'rejected', NULL);
INSERT INTO `comment` VALUES (4, '1234', 1, NULL, '2025-03-29 22:53:59', 0, 1, NULL, 'rejected', NULL);
INSERT INTO `comment` VALUES (5, 'test2', 1, NULL, '2025-03-30 00:00:20', 0, 1, NULL, 'approved', NULL);
INSERT INTO `comment` VALUES (6, '你号啊', 5, 5, '2025-03-30 00:01:11', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (7, '你算个啥', 1, 5, '2025-03-30 00:05:35', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (8, '你好个der', 1, 5, '2025-03-30 00:16:01', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (9, '123456', 5, 5, '2025-03-30 00:25:02', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (10, '测试回复', 1, 5, '2025-03-30 09:18:53', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (11, '测试再次回复', 5, 5, '2025-03-30 09:20:29', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (12, '测试再次回复', 1, 5, '2025-03-30 09:21:05', 0, 1, NULL, 'appending', 5);
INSERT INTO `comment` VALUES (13, '你觉着呢', 1, 11, '2025-03-30 09:27:40', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (14, '我觉得很好呀', 5, 13, '2025-03-30 09:45:53', 0, 1, NULL, 'approved', 5);

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `target_id` int(11) NULL DEFAULT NULL COMMENT '目标的类型的主键id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int(11) NULL DEFAULT NULL COMMENT '点赞的类型(0:评论、1: 帖子）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (1, 5, 1, '2025-03-29 21:49:59', 2);
INSERT INTO `favorite` VALUES (2, 5, 1, '2025-03-29 22:00:45', 1);
INSERT INTO `favorite` VALUES (9, 5, 1, '2025-03-29 23:57:41', 3);
INSERT INTO `favorite` VALUES (11, 1, 9, '2025-03-30 09:55:35', 3);
INSERT INTO `favorite` VALUES (12, 1, 6, '2025-03-30 09:55:44', 3);
INSERT INTO `favorite` VALUES (13, 1, 5, '2025-03-30 09:57:04', 3);
INSERT INTO `favorite` VALUES (14, 1, 1, '2025-03-30 10:20:49', 3);
INSERT INTO `favorite` VALUES (15, 1, 1, '2025-03-30 10:21:04', 2);
INSERT INTO `favorite` VALUES (16, 1, 11, '2025-03-30 11:17:24', 3);
INSERT INTO `favorite` VALUES (18, 1, 1, '2025-03-30 11:30:31', 1);
INSERT INTO `favorite` VALUES (19, 1, 14, '2025-03-30 12:26:11', 3);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_read` tinyint(1) NULL DEFAULT NULL COMMENT '是否已读',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知的类型(system、comment、comment_reply...)',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端提示链接地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知的标题',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (1, '欢迎使用只能手机评测论坛，请完善您的个人资料', '2025-03-29 01:58:16', 1, 'system', '/user-center', '系统通知', 5);
INSERT INTO `notification` VALUES (2, '您的评测 <b>asdfasdfasdfasdf</b>已通过审核，请查看', '2025-03-29 21:26:58', 1, 'system', '/review/1', '评测审核', 5);
INSERT INTO `notification` VALUES (3, '用户 <b>svwh</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-29 22:00:45', 1, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (4, '用户 <b>test</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-29 22:07:13', 1, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (5, '用户 <b>test</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-29 22:15:05', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (6, '您的评论 <b>test</b>未通过审核，请重新提交', '2025-03-29 22:42:54', 1, 'system', NULL, '评论审核', 1);
INSERT INTO `notification` VALUES (7, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-29 22:42:54', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (8, '您的评论 <b>你好啊</b>未通过审核，原因为：敏感内容', '2025-03-29 22:48:34', 1, 'system', NULL, '评论审核', 1);
INSERT INTO `notification` VALUES (9, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-29 22:48:34', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (10, '您的评论 <b>testsetse</b>未通过审核，原因为：敏感内容', '2025-03-29 22:49:50', 1, 'system', NULL, '评论审核', 1);
INSERT INTO `notification` VALUES (11, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-29 22:51:18', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (12, '您的评论 <b>1234</b> 未通过审核，原因为：攻击性语言', '2025-03-29 22:54:20', 1, 'system', NULL, '评论审核', 1);
INSERT INTO `notification` VALUES (13, '您的评测 <b>asdfasdfasdfasdf</b>未通过审核，请重新提交评测', '2025-03-29 22:54:48', 1, 'system', '/user-center', '评测审核', 5);
INSERT INTO `notification` VALUES (14, '您的评测 <b>asdfasdfasdfasdf</b>已通过审核，请查看', '2025-03-29 22:54:49', 1, 'system', '/review/1', '评测审核', 5);
INSERT INTO `notification` VALUES (15, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-29 23:56:53', 1, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (16, '用户 <b>svwh</b> 点赞了您的评论<b></b>', '2025-03-29 23:57:41', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (17, '您的评论 <b>test2</b>已通过审核', '2025-03-30 00:00:32', 1, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (18, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 00:00:32', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (20, '您的评论 <b>你号啊</b>已通过审核', '2025-03-30 00:04:23', 1, 'system', '/review/1', '评论审核', 5);
INSERT INTO `notification` VALUES (21, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 00:04:23', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (22, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 00:04:23', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (23, '您的评论 <b>你算个啥</b>已通过审核', '2025-03-30 00:05:44', 1, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (24, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 00:05:44', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (25, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 00:05:44', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (26, '您的评论 <b>你好个der</b>已通过审核', '2025-03-30 00:24:25', 1, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (27, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 00:24:25', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (28, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 00:24:25', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (29, '您的评论 <b>123456</b>已通过审核', '2025-03-30 00:25:11', 1, 'system', '/review/1', '评论审核', 5);
INSERT INTO `notification` VALUES (30, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 00:25:11', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (31, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 00:25:11', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (32, '您的评论 <b>测试回复</b>已通过审核', '2025-03-30 09:19:15', 1, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (33, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 09:19:15', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (34, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 09:19:15', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (35, '您的评论 <b>测试再次回复</b>已通过审核', '2025-03-30 09:20:36', 1, 'system', '/review/1', '评论审核', 5);
INSERT INTO `notification` VALUES (36, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 09:20:36', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (37, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 09:20:36', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (38, '您的评论 <b>你觉着呢</b>已通过审核', '2025-03-30 09:28:06', 1, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (39, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 09:28:06', 1, 'comment-reply', '/review/1', '评论回复', 5);
INSERT INTO `notification` VALUES (40, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 09:28:06', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (41, '您的评论 <b>我觉得很好呀</b>已通过审核', '2025-03-30 09:46:32', 1, 'system', '/review/1', '评论审核', 5);
INSERT INTO `notification` VALUES (42, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 09:46:32', 1, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (43, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 09:46:32', 1, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (44, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 09:55:17', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (45, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 09:55:35', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (46, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 09:55:44', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (47, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 09:57:04', 1, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (48, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 10:20:49', 1, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (49, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 11:17:24', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (50, '用户 <b>test</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-30 11:30:17', 1, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (51, '用户 <b>test</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-30 11:30:31', 1, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (52, '用户 <b>test</b> 点赞了您的评论<b></b>', '2025-03-30 12:26:11', 1, 'comment_like', '/review/1', '您的评论被点赞了', 5);

-- ----------------------------
-- Table structure for phone
-- ----------------------------
DROP TABLE IF EXISTS `phone`;
CREATE TABLE `phone`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phone
-- ----------------------------

-- ----------------------------
-- Table structure for phone_model
-- ----------------------------
DROP TABLE IF EXISTS `phone_model`;
CREATE TABLE `phone_model`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机型号',
  `brand_id` bigint(255) NULL DEFAULT NULL COMMENT '手机品牌主键id',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机相关的图片',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `release_date` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `popularity` int(11) NULL DEFAULT NULL COMMENT '受欢迎的程度',
  `status` int(11) NULL DEFAULT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phone_model
-- ----------------------------
INSERT INTO `phone_model` VALUES (1, 'test', 1, 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', '小米15旗舰', NULL, NULL, 5999.00, '2025-03-29 00:00:00', NULL, 1);
INSERT INTO `phone_model` VALUES (2, 'test', 1, '', '', NULL, NULL, 0.00, '2025-03-29 00:00:00', NULL, NULL);
INSERT INTO `phone_model` VALUES (3, 'test', 1, '', '', NULL, NULL, 400.00, '2025-03-29 00:00:00', NULL, NULL);
INSERT INTO `phone_model` VALUES (4, 'Mate40', 2, 'http://localhost:8080/images/e3c3528e-d1c0-4437-bd75-d7d2fd94b281.jpg', '华为mate40', NULL, '2025-03-29 20:02:36', 3900.00, '2025-03-14 00:00:00', NULL, NULL);

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of photo
-- ----------------------------
INSERT INTO `photo` VALUES (1, 'http://localhost:8080/images/a1717f40-9485-4cfa-807d-7e511dedd021.jpg', 5);
INSERT INTO `photo` VALUES (2, 'http://localhost:8080/images/849a21db-f7ac-47fe-aba2-be8765bc705b.jpg', 5);
INSERT INTO `photo` VALUES (3, 'http://localhost:8080/images/4c68469e-ec60-4922-9243-8b49dc538f80.jpg', 5);
INSERT INTO `photo` VALUES (4, 'http://localhost:8080/images/8addf015-d7c2-4ee6-a274-25dfb38c2805.jpg', 5);
INSERT INTO `photo` VALUES (5, 'http://localhost:8080/images/1294dc54-c23e-4aaf-81fe-9cd8784b785d.jpg', 5);
INSERT INTO `photo` VALUES (6, 'http://localhost:8080/images/e5b2924b-08db-4d8a-8342-5cc80edef0c7.jpg', 5);
INSERT INTO `photo` VALUES (7, 'http://localhost:8080/images/e3c3528e-d1c0-4437-bd75-d7d2fd94b281.jpg', 5);
INSERT INTO `photo` VALUES (8, 'http://localhost:8080/images/e155bbc5-5ba4-4dc6-9775-d11ea9c47753.jpg', 5);
INSERT INTO `photo` VALUES (9, 'http://localhost:8080/images/49ea10ad-e5ad-4e17-8ddb-400d1a571526.jpg', 5);
INSERT INTO `photo` VALUES (10, 'http://localhost:8080/images/4ce7f0b7-c867-4c03-97b1-a5e0685d9de6.jpg', 1);
INSERT INTO `photo` VALUES (11, 'http://localhost:8080/images/1f84b281-1ffe-4b94-bea3-48ae35ad2bd7.jpg', 1);
INSERT INTO `photo` VALUES (12, 'http://localhost:8080/images/3a252d50-cd53-43ef-9d68-a1ff8a7a6f38.jpg', 1);
INSERT INTO `photo` VALUES (13, 'http://localhost:8080/images/9cd86e0d-6815-4245-98e0-f000eb5ad73c.jpg', 1);
INSERT INTO `photo` VALUES (14, 'http://localhost:8080/images/5d352027-827f-4d5c-a50b-f43327a22a59.jpg', 5);
INSERT INTO `photo` VALUES (15, 'http://localhost:8080/images/5e317b0a-18e2-43fc-97bb-d7c6c390c455.jpg', 1);
INSERT INTO `photo` VALUES (16, 'http://localhost:8080/images/471218af-b593-414a-ada0-9aefdd2b99f8.jpg', 1);
INSERT INTO `photo` VALUES (17, 'http://localhost:8080/images/adc4f2a0-5db7-4c69-aa69-faec2f8eae56.jpg', 1);

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户主键id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `phone_model_id` bigint(20) NULL DEFAULT NULL COMMENT '手机型号id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '是否开启',
  `views` bigint(20) NULL DEFAULT 0 COMMENT '浏览数',
  `status` int(11) NULL DEFAULT NULL COMMENT '帖子状态(1: 正常，0：已屏蔽,2：待审核)',
  `images` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片列表',
  `appearance_rating` int(11) NULL DEFAULT NULL COMMENT '外观表现',
  `screen_rating` int(11) NULL DEFAULT NULL COMMENT '屏幕表现',
  `performance_rating` int(11) NULL DEFAULT NULL COMMENT '性能表现',
  `camera_rating` int(11) NULL DEFAULT NULL COMMENT '相机表现',
  `battery_rating` int(11) NULL DEFAULT NULL COMMENT '电池表现',
  `system_rating` int(11) NULL DEFAULT NULL COMMENT '系统表现',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `rating` decimal(11, 0) NULL DEFAULT NULL COMMENT '综合评分',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, 5, 'asdfasdfasdfasdf', 'adsssssssssssssssssssssssssssssssssssssssssssssasdfffffffffffffffffffff', 4, '2025-03-29 21:00:01', NULL, NULL, 17, 1, 'http://localhost:8080/images/49ea10ad-e5ad-4e17-8ddb-400d1a571526.jpg', 4, 3, 1, 3, 0, 2, 2, 2, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户角色（admin、user)',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户登录的密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的头像（注册时没有用系统默认的）',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用评论功能',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的邮箱',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的个人简介',
  `status` int(11) NULL DEFAULT 1 COMMENT '用户的状态',
  `is_muted` tinyint(1) NULL DEFAULT 0 COMMENT '当前用户是否已被禁止发言',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', 'test', 'user', 'CF70D5A6EDB26657B165571DE8CCB45C', 'http://localhost:8080/images/adc4f2a0-5db7-4c69-aa69-faec2f8eae56.jpg', 0, 1, '2025-03-04 13:48:26', '2606714301@qq.com', 'asdfasdfasd', 1, 0);
INSERT INTO `user` VALUES (2, 'admin', 'admin', 'admin', 'C9D21E89DC04F9F2B446B4FBDAFDF4B8', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-27 13:48:30', NULL, NULL, 1, 0);
INSERT INTO `user` VALUES (5, 'svwhcx', 'svwh', 'admin', 'C9D21E89DC04F9F2B446B4FBDAFDF4B8', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-27 13:48:33', '1548222103@qq.com', '个人简介测试', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
