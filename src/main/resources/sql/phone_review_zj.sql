/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : phone_review

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 30/03/2025 22:45:06
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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前公告的状态（published、schedule定时等等）',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布的时间',
  `priority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优先级',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `schedule_time` datetime(0) NULL DEFAULT NULL COMMENT '定时发布的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, '小米', 'Xiaomi', 'http://localhost:8080/images/e155bbc5-5ba4-4dc6-9775-d11ea9c47753.jpg', 1, '小米手机', '中国', '2025-03-30 21:11:03');
INSERT INTO `brand` VALUES (2, '华为', 'HuaWei', 'http://localhost:8080/images/1294dc54-c23e-4aaf-81fe-9cd8784b785d.jpg', 1, '华为手机', '中国', '2025-03-29 19:54:18');
INSERT INTO `brand` VALUES (3, 'OPPO', 'OPPO', 'http://localhost:8080/images/brand_oppo.jpg', 1, 'OPPO智能手机', '中国', '2025-01-29 22:20:36');
INSERT INTO `brand` VALUES (4, 'vivo', 'vivo', 'http://localhost:8080/images/brand_vivo.jpg', 1, 'vivo智能手机', '中国', '2025-01-31 22:20:36');
INSERT INTO `brand` VALUES (5, '三星', 'Samsung', 'http://localhost:8080/images/brand_samsung.jpg', 1, '三星智能手机', '韩国', '2025-02-03 22:20:36');
INSERT INTO `brand` VALUES (6, '苹果', 'Apple', 'http://localhost:8080/images/brand_apple.jpg', 1, '苹果智能手机', '美国', '2025-02-08 22:20:36');
INSERT INTO `brand` VALUES (7, '一加', 'OnePlus', 'http://localhost:8080/images/brand_oneplus.jpg', 1, '一加智能手机', '中国', '2025-02-13 22:20:36');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '文章主键id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发表评论的时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `post_id` bigint(20) NULL DEFAULT NULL COMMENT '文章的id',
  `favorite` int(11) NULL DEFAULT NULL COMMENT '评论的点赞量',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论的状态(appending，approved，rejected）',
  `root_id` bigint(20) NULL DEFAULT NULL COMMENT '最顶层的评论id（用于回复时使用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `comment` VALUES (12, '测试再次回复', 1, 5, '2025-03-30 09:21:05', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (13, '你觉着呢', 1, 11, '2025-03-30 09:27:40', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (14, '我觉得很好呀', 5, 13, '2025-03-30 09:45:53', 0, 1, NULL, 'approved', 5);
INSERT INTO `comment` VALUES (15, '这款手机的拍照能力确实令人印象深刻，尤其是夜景模式下的表现', 1, NULL, '2025-02-24 22:20:36', 0, 2, 5, 'approved', NULL);
INSERT INTO `comment` VALUES (16, '同意楼上观点，不过价格有点偏高，希望后续能有优惠', 2, 1, '2025-02-25 22:20:36', 0, 2, 3, 'approved', 1);
INSERT INTO `comment` VALUES (17, '屏幕素质确实一流，但是续航表现一般，重度使用一天很难撑下来', 3, NULL, '2025-02-26 22:20:36', 0, 2, 4, 'approved', NULL);
INSERT INTO `comment` VALUES (18, '系统优化做得不错，比上一代流畅很多', 4, NULL, '2025-02-27 22:20:36', 0, 2, 2, 'approved', NULL);
INSERT INTO `comment` VALUES (19, '作为一个摄影爱好者，这款手机的相机系统确实让我很满意', 5, 3, '2025-02-28 22:20:36', 0, 2, 6, 'approved', 3);
INSERT INTO `comment` VALUES (20, '游戏性能如何？有没有发热问题？', 6, NULL, '2025-03-01 22:20:36', 0, 2, 1, 'approved', NULL);
INSERT INTO `comment` VALUES (21, '玩了几款大型游戏，发热控制得不错，但是耗电比较快', 7, 6, '2025-03-02 22:20:36', 0, 2, 3, 'approved', 6);
INSERT INTO `comment` VALUES (22, '拍照样张能不能多分享一些？特别是夜景和人像', 8, NULL, '2025-03-03 22:20:36', 0, 2, 2, 'approved', NULL);
INSERT INTO `comment` VALUES (23, '这个价位确实值得购买，综合素质很强', 1, NULL, '2025-03-04 22:20:36', 0, 3, 4, 'approved', NULL);
INSERT INTO `comment` VALUES (24, 'S笔功能很实用，尤其是做笔记和精细操作时', 2, NULL, '2025-03-05 22:20:36', 0, 3, 3, 'approved', NULL);
INSERT INTO `comment` VALUES (25, '200MP的主摄确实厉害，细节表现非常出色', 3, NULL, '2025-03-06 22:20:36', 0, 3, 5, 'approved', NULL);
INSERT INTO `comment` VALUES (26, '续航表现一般，希望厂商能在这方面多下功夫', 4, 11, '2025-03-07 22:20:36', 0, 3, 2, 'approved', 11);
INSERT INTO `comment` VALUES (27, '系统有点臃肿，预装应用太多了', 5, NULL, '2025-03-08 22:20:36', 0, 3, 3, 'approved', NULL);
INSERT INTO `comment` VALUES (28, '钛金属机身确实轻了不少，握持感更好了', 6, NULL, '2025-03-09 22:20:36', 0, 4, 4, 'approved', NULL);
INSERT INTO `comment` VALUES (29, 'iOS系统流畅度确实没话说，但生态封闭是个问题', 7, NULL, '2025-03-10 22:20:36', 0, 4, 3, 'approved', NULL);
INSERT INTO `comment` VALUES (30, '相机系统提升不大，有点失望', 8, NULL, '2025-03-11 22:20:36', 0, 4, 2, 'approved', NULL);
INSERT INTO `comment` VALUES (31, '性能确实强，玩游戏很流畅，而且不怎么发热', 1, NULL, '2025-03-12 22:20:36', 0, 5, 5, 'approved', NULL);
INSERT INTO `comment` VALUES (32, '充电速度快是亮点，半小时就能充到80%以上', 2, 17, '2025-03-13 22:20:36', 0, 5, 3, 'approved', 17);
INSERT INTO `comment` VALUES (33, '系统很干净，几乎没有广告和推送，用着很舒服', 3, NULL, '2025-03-14 22:20:36', 0, 5, 4, 'approved', NULL);
INSERT INTO `comment` VALUES (34, '徕卡调校的相机确实有特点，色彩很讨喜', 4, NULL, '2025-03-15 22:20:36', 0, 6, 5, 'approved', NULL);

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `target_id` int(11) NULL DEFAULT NULL COMMENT '目标的类型的主键id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `type` int(11) NULL DEFAULT NULL COMMENT '点赞的类型(0:评论、1: 帖子）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点赞表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `favorite` VALUES (19, 6, 14, '2025-03-30 16:51:38', 3);
INSERT INTO `favorite` VALUES (27, 6, 1, '2025-03-30 21:08:00', 1);

-- ----------------------------
-- Table structure for item_similarities
-- ----------------------------
DROP TABLE IF EXISTS `item_similarities`;
CREATE TABLE `item_similarities`  (
  `post1_id` bigint(20) NOT NULL COMMENT '文章1ID',
  `post2_id` bigint(20) NOT NULL COMMENT '文章2ID',
  `similarity` float NOT NULL COMMENT '相似度值，范围0-1',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`post1_id`, `post2_id`) USING BTREE,
  INDEX `idx_post2`(`post2_id`) USING BTREE,
  INDEX `idx_similarity`(`similarity`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文章相似度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_similarities
-- ----------------------------
INSERT INTO `item_similarities` VALUES (2, 3, 0.75, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (2, 4, 0.68, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (2, 5, 0.72, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (2, 6, 0.78, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (2, 7, 0.65, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (2, 9, 0.92, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (3, 4, 0.7, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (3, 5, 0.68, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (3, 6, 0.65, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (3, 7, 0.6, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (3, 10, 0.88, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (4, 5, 0.67, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (4, 6, 0.63, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (4, 7, 0.58, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (4, 11, 0.9, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (5, 6, 0.82, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (5, 7, 0.7, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (5, 12, 0.85, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (6, 7, 0.75, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (6, 13, 0.87, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `item_similarities` VALUES (7, 14, 0.89, '2025-03-30 22:20:36', '2025-03-30 22:20:36');

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `is_read` tinyint(1) NULL DEFAULT NULL COMMENT '是否已读',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知的类型(system、comment、comment_reply...)',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端提示链接地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知的标题',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `notification` VALUES (52, '欢迎使用只能手机评测论坛，请完善您的个人资料', '2025-03-30 15:56:39', 1, 'system', '/user-center', '系统通知', 6);
INSERT INTO `notification` VALUES (53, '您的评论 <b>测试再次回复</b> 未通过审核，原因为：敏感内容', '2025-03-30 15:58:39', 0, 'system', NULL, '评论审核', 1);
INSERT INTO `notification` VALUES (54, '您的评论 <b>测试再次回复</b>已通过审核', '2025-03-30 15:58:44', 0, 'system', '/review/1', '评论审核', 1);
INSERT INTO `notification` VALUES (55, '用户 <b>svwh</b> 回复了您的评论', '2025-03-30 15:58:44', 0, 'comment-reply', '/review/1', '评论回复', 1);
INSERT INTO `notification` VALUES (56, '用户 <b>svwh</b> 评论了您的评测 <b>asdfasdfasdfasdf</b>', '2025-03-30 15:58:44', 0, 'comment', '/review/1', '新的评论', 5);
INSERT INTO `notification` VALUES (57, '欢迎使用只能手机评测论坛，请完善您的个人资料', '2025-03-30 16:10:13', 0, 'system', '/user-center', '系统通知', 7);
INSERT INTO `notification` VALUES (58, '用户 <b>null</b> 点赞了您的评论<b></b>', '2025-03-30 16:51:38', 0, 'comment_like', '/review/1', '您的评论被点赞了', 5);
INSERT INTO `notification` VALUES (59, '用户 <b>null</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-30 16:54:46', 0, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (60, '用户 <b>null</b> 点赞了您的评论<b></b>', '2025-03-30 16:55:15', 0, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (61, '用户 <b>null</b> 点赞了您的评论<b></b>', '2025-03-30 16:55:17', 0, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (62, '用户 <b>null</b> 点赞了您的评论<b></b>', '2025-03-30 16:55:19', 0, 'comment_like', '/review/1', '您的评论被点赞了', 1);
INSERT INTO `notification` VALUES (63, '您的评测 <b>asdfasdfasdfasdf</b>未通过审核，请重新提交评测', '2025-03-30 18:32:04', 0, 'system', '/user-center', '评测审核', 5);
INSERT INTO `notification` VALUES (64, '您的评测 <b>asdfasdfasdfasdf</b>已通过审核，请查看', '2025-03-30 18:32:05', 0, 'system', '/review/1', '评测审核', 5);
INSERT INTO `notification` VALUES (65, '用户 <b>null</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-30 18:52:51', 0, 'post_like', '/review/1', '您的评测被点赞了', 5);
INSERT INTO `notification` VALUES (66, '用户 <b>null</b> 点赞了您的评测 asdfasdfasdfasdf', '2025-03-30 21:08:00', 0, 'post_like', '/review/1', '您的评测被点赞了', 5);

-- ----------------------------
-- Table structure for phone
-- ----------------------------
DROP TABLE IF EXISTS `phone`;
CREATE TABLE `phone`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `release_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `popularity` int(11) NULL DEFAULT NULL COMMENT '受欢迎的程度',
  `status` int(11) NULL DEFAULT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of phone_model
-- ----------------------------
INSERT INTO `phone_model` VALUES (1, 'test', 1, 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', '小米15旗舰', NULL, NULL, 5999.00, '2025-03-29 00:00:00', NULL, 1);
INSERT INTO `phone_model` VALUES (2, 'test', 1, '', '', NULL, NULL, 0.00, '2025-03-29 00:00:00', NULL, NULL);
INSERT INTO `phone_model` VALUES (3, 'test', 1, '', '', NULL, NULL, 400.00, '2025-03-29 00:00:00', NULL, NULL);
INSERT INTO `phone_model` VALUES (4, 'Mate40', 2, 'http://localhost:8080/images/e3c3528e-d1c0-4437-bd75-d7d2fd94b281.jpg', '华为mate40', NULL, '2025-03-29 20:02:36', 3900.00, '2025-03-14 00:00:00', NULL, NULL);
INSERT INTO `phone_model` VALUES (5, 'Reno10 Pro', 3, 'http://localhost:8080/images/phone_oppo_reno10.jpg', 'OPPO Reno10 Pro 5G手机', 1, '2025-02-18 22:20:36', 3299.00, '2024-11-30 22:20:36', 85, 1);
INSERT INTO `phone_model` VALUES (6, 'X100 Pro', 4, 'http://localhost:8080/images/phone_vivo_x100.jpg', 'vivo X100 Pro 旗舰手机', 1, '2025-02-20 22:20:36', 5999.00, '2024-12-20 22:20:36', 92, 1);
INSERT INTO `phone_model` VALUES (7, 'Galaxy S23 Ultra', 5, 'http://localhost:8080/images/phone_samsung_s23.jpg', '三星Galaxy S23 Ultra旗舰手机', 1, '2025-02-23 22:20:36', 8999.00, '2024-10-31 22:20:36', 88, 1);
INSERT INTO `phone_model` VALUES (8, 'iPhone 15 Pro', 6, 'http://localhost:8080/images/phone_iphone15.jpg', '苹果iPhone 15 Pro旗舰手机', 1, '2025-02-28 22:20:36', 7999.00, '2024-10-01 22:20:36', 95, 1);
INSERT INTO `phone_model` VALUES (9, 'OnePlus 12', 7, 'http://localhost:8080/images/phone_oneplus12.jpg', '一加12旗舰手机', 1, '2025-03-05 22:20:36', 4699.00, '2024-12-30 22:20:36', 87, 1);
INSERT INTO `phone_model` VALUES (10, '小米14 Pro', 1, 'http://localhost:8080/images/phone_xiaomi14.jpg', '小米14 Pro旗舰手机', 1, '2025-03-10 22:20:36', 4999.00, '2024-12-10 22:20:36', 90, 1);
INSERT INTO `phone_model` VALUES (11, 'Mate 60 Pro', 2, 'http://localhost:8080/images/phone_huawei_mate60.jpg', '华为Mate 60 Pro旗舰手机', 1, '2025-03-15 22:20:36', 6999.00, '2024-11-20 22:20:36', 93, 1);

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
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
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, 5, 'asdfasdfasdfasdf', 'adsssssssssssssssssssssssssssssssssssssssssssssasdfffffffffffffffffffff', 4, '2025-03-29 21:00:01', NULL, NULL, 27, 1, 'http://localhost:8080/images/49ea10ad-e5ad-4e17-8ddb-400d1a571526.jpg', 4, 3, 1, 3, 0, 2, 2, 2, NULL);
INSERT INTO `posts` VALUES (2, 5, 'OPPO Reno10 Pro深度评测：影像新高度', 'OPPO Reno10 Pro在影像方面有了显著提升，特别是人像摄影能力非常出色。外观设计时尚轻薄，握持感舒适。屏幕显示效果鲜艳，色彩准确度高。性能方面满足日常使用需求，但对于重度游戏玩家可能略显不足。', 5, '2025-02-23 22:20:36', NULL, 1, 156, 1, 'http://localhost:8080/images/review_oppo_reno10.jpg', 4, 4, 3, 5, 4, 4, 3, 4, NULL);
INSERT INTO `posts` VALUES (3, 1, 'vivo X100 Pro评测：影像旗舰新标杆', 'vivo X100 Pro搭载了自研的V2影像芯片，拍照能力非常强大，尤其是夜景和长焦表现。屏幕素质顶级，高刷新率体验流畅。性能方面不负旗舰之名，各类大型游戏运行流畅。', 6, '2025-02-25 22:20:36', NULL, 1, 212, 1, 'http://localhost:8080/images/review_vivo_x100.jpg', 5, 5, 4, 5, 4, 4, 4, 5, NULL);
INSERT INTO `posts` VALUES (4, 2, '三星Galaxy S23 Ultra评测：S笔旗舰的巅峰之作', '三星Galaxy S23 Ultra延续了Note系列的优良传统，S笔操作便捷高效。200MP主摄带来了极致的拍照体验，细节表现出色。骁龙8 Gen2处理器性能强劲，散热控制良好。', 7, '2025-02-28 22:20:36', NULL, 1, 178, 1, 'http://localhost:8080/images/review_samsung_s23.jpg', 4, 5, 5, 5, 3, 4, 5, 4, NULL);
INSERT INTO `posts` VALUES (5, 3, 'iPhone 15 Pro深度评测：钛金属机身的轻奢体验', 'iPhone 15 Pro采用了全新的钛金属中框，重量明显减轻，握持感更佳。A17 Pro处理器性能强大，尤其是GPU性能提升明显。iOS系统流畅度一如既往地出色，生态优势明显。', 8, '2025-03-02 22:20:36', NULL, 1, 254, 1, 'http://localhost:8080/images/review_iphone15.jpg', 5, 4, 5, 4, 3, 5, 6, 4, NULL);
INSERT INTO `posts` VALUES (6, 4, '一加12评测：性能怪兽的回归', '一加12回归了\"性能怪兽\"的称号，搭载骁龙8 Gen3处理器，性能表现极为出色。散热系统升级，长时间游戏也不会有明显发热。充电速度快，续航表现也相当不错。', 9, '2025-03-04 22:20:36', NULL, 1, 189, 1, 'http://localhost:8080/images/review_oneplus12.jpg', 4, 5, 5, 4, 4, 4, 7, 4, NULL);
INSERT INTO `posts` VALUES (7, 6, '小米14 Pro评测：全能旗舰的诚意之作', '小米14 Pro在各方面都有明显提升，特别是影像系统的升级非常明显。徕卡调校的相机系统色彩还原度高，人像模式出色。骁龙8 Gen3处理器性能强劲，MIUI系统优化良好。', 10, '2025-03-06 22:20:36', NULL, 1, 219, 1, 'http://localhost:8080/images/review_xiaomi14.jpg', 4, 5, 5, 4, 4, 4, 1, 4, NULL);
INSERT INTO `posts` VALUES (8, 7, '华为Mate 60 Pro评测：国产芯的突破之作', '华为Mate 60 Pro搭载自研麒麟处理器，在各方面表现均衡。卫星通话功能是一大亮点，解决了无信号区域的通讯问题。超光变相机系统拍照能力出众，尤其是远摄表现。', 11, '2025-03-08 22:20:36', NULL, 1, 267, 1, 'http://localhost:8080/images/review_huawei_mate60.jpg', 5, 4, 4, 5, 4, 5, 2, 5, NULL);
INSERT INTO `posts` VALUES (9, 8, 'OPPO Reno10 Pro游戏性能测试', 'OPPO Reno10 Pro在游戏方面表现如何？本文进行了详细的游戏测试，包括《原神》、《王者荣耀》等主流游戏的帧率表现和发热情况。总体来说，中度游戏表现良好，但重度游戏可能会有些压力。', 5, '2025-03-10 22:20:36', NULL, 1, 154, 1, 'http://localhost:8080/images/review_oppo_game.jpg', 3, 4, 3, 0, 3, 3, 3, 3, NULL);
INSERT INTO `posts` VALUES (10, 3, 'vivo X100 Pro摄影体验', 'vivo X100 Pro的摄影能力确实令人印象深刻，尤其是人像模式下的虚化效果非常自然。夜景模式下的噪点控制和细节保留做得相当出色，长焦端的表现也超出预期。', 6, '2025-03-12 22:20:36', NULL, 1, 167, 1, 'http://localhost:8080/images/review_vivo_camera.jpg', 0, 0, 0, 5, 0, 0, 4, 5, NULL);
INSERT INTO `posts` VALUES (11, 2, '三星Galaxy S23 Ultra续航测试', '三星Galaxy S23 Ultra的电池容量虽然没有提升，但通过系统优化和芯片效能提升，续航有了明显改善。本文进行了详细的续航测试，包括日常使用、游戏、视频播放等场景下的表现。', 7, '2025-03-14 22:20:36', NULL, 1, 141, 1, 'http://localhost:8080/images/review_samsung_battery.jpg', 0, 0, 0, 0, 5, 0, 5, 4, NULL);
INSERT INTO `posts` VALUES (12, 1, 'iPhone 15 Pro系统体验', 'iOS 17带来了诸多新功能，本文详细测试了iPhone 15 Pro上的系统新特性，包括交互设计、功能改进和性能优化等方面。总体来说，系统流畅度和稳定性依然是iOS的强项。', 8, '2025-03-16 22:20:36', NULL, 1, 198, 1, 'http://localhost:8080/images/review_iphone_system.jpg', 0, 0, 0, 0, 0, 5, 6, 5, NULL);
INSERT INTO `posts` VALUES (13, 4, '一加12屏幕素质分析', '一加12采用了2K分辨率的LTPO屏幕，本文对其色彩还原、亮度表现、刷新率等方面进行了专业测试。结果显示，这块屏幕在色准和亮度方面表现出色，是目前市场上顶级的手机屏幕之一。', 9, '2025-03-18 22:20:36', NULL, 1, 163, 1, 'http://localhost:8080/images/review_oneplus_screen.jpg', 0, 5, 0, 0, 0, 0, 7, 5, NULL);
INSERT INTO `posts` VALUES (14, 5, '小米14 Pro性能测试：骁龙8 Gen3实力几何？', '小米14 Pro搭载的骁龙8 Gen3处理器性能如何？本文通过安兔兔、GeekBench等专业跑分软件进行了详细测试，并进行了游戏实测和多任务处理能力评估。', 10, '2025-03-20 22:20:36', NULL, 1, 187, 1, 'http://localhost:8080/images/review_xiaomi_performance.jpg', 0, 0, 5, 0, 0, 0, 1, 5, NULL);
INSERT INTO `posts` VALUES (15, 6, '华为Mate 60 Pro外观设计赏析', '华为Mate 60 Pro的设计语言延续了Mate系列的经典风格，同时加入了新元素。本文从材质、工艺、握持感等多角度分析了这款旗舰手机的外观设计特点。', 11, '2025-03-22 22:20:36', NULL, 1, 143, 1, 'http://localhost:8080/images/review_huawei_design.jpg', 5, 0, 0, 0, 0, 0, 2, 5, NULL);

-- ----------------------------
-- Table structure for recommendation_logs
-- ----------------------------
DROP TABLE IF EXISTS `recommendation_logs`;
CREATE TABLE `recommendation_logs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '推荐评测ID',
  `algorithm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推荐算法：user_cf-基于用户的协同过滤，item_cf-基于物品的协同过滤，hybrid-混合推荐',
  `score` float NOT NULL COMMENT '推荐分数',
  `position` int(11) NOT NULL COMMENT '推荐位置',
  `clicked` tinyint(4) NULL DEFAULT 0 COMMENT '是否被点击：0-否，1-是',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '推荐时间',
  `click_time` datetime(0) NULL DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_post_id`(`post_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_algorithm`(`algorithm`) USING BTREE,
  INDEX `idx_is_clicked`(`clicked`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '推荐日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recommendation_logs
-- ----------------------------
INSERT INTO `recommendation_logs` VALUES (1, 1, 7, 'item_cf', 0.85, 1, 1, '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `recommendation_logs` VALUES (2, 1, 9, 'user_cf', 0.78, 2, 0, '2025-03-10 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (3, 1, 13, 'hybrid', 0.82, 3, 1, '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `recommendation_logs` VALUES (4, 1, 8, 'item_cf', 0.72, 1, 0, '2025-03-15 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (5, 1, 14, 'user_cf', 0.75, 2, 1, '2025-03-15 22:20:36', '2025-03-15 22:20:36');
INSERT INTO `recommendation_logs` VALUES (6, 1, 11, 'hybrid', 0.8, 3, 0, '2025-03-15 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (7, 2, 6, 'item_cf', 0.82, 1, 1, '2025-03-12 22:20:36', '2025-03-12 22:20:36');
INSERT INTO `recommendation_logs` VALUES (8, 2, 12, 'user_cf', 0.75, 2, 0, '2025-03-12 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (9, 2, 4, 'hybrid', 0.79, 3, 1, '2025-03-12 22:20:36', '2025-03-12 22:20:36');
INSERT INTO `recommendation_logs` VALUES (10, 2, 14, 'item_cf', 0.7, 1, 0, '2025-03-17 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (11, 2, 8, 'user_cf', 0.73, 2, 1, '2025-03-17 22:20:36', '2025-03-17 22:20:36');
INSERT INTO `recommendation_logs` VALUES (12, 2, 10, 'hybrid', 0.77, 3, 0, '2025-03-17 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (13, 3, 5, 'item_cf', 0.8, 1, 1, '2025-03-14 22:20:36', '2025-03-14 22:20:36');
INSERT INTO `recommendation_logs` VALUES (14, 3, 11, 'user_cf', 0.73, 2, 0, '2025-03-14 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (15, 3, 3, 'hybrid', 0.77, 3, 1, '2025-03-14 22:20:36', '2025-03-14 22:20:36');
INSERT INTO `recommendation_logs` VALUES (16, 3, 13, 'item_cf', 0.68, 1, 0, '2025-03-19 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (17, 3, 7, 'user_cf', 0.71, 2, 1, '2025-03-19 22:20:36', '2025-03-19 22:20:36');
INSERT INTO `recommendation_logs` VALUES (18, 3, 9, 'hybrid', 0.75, 3, 0, '2025-03-19 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (19, 4, 4, 'item_cf', 0.78, 1, 1, '2025-03-16 22:20:36', '2025-03-16 22:20:36');
INSERT INTO `recommendation_logs` VALUES (20, 4, 10, 'user_cf', 0.71, 2, 0, '2025-03-16 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (21, 4, 2, 'hybrid', 0.75, 3, 1, '2025-03-16 22:20:36', '2025-03-16 22:20:36');
INSERT INTO `recommendation_logs` VALUES (22, 4, 12, 'item_cf', 0.66, 1, 0, '2025-03-21 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (23, 4, 6, 'user_cf', 0.69, 2, 1, '2025-03-21 22:20:36', '2025-03-21 22:20:36');
INSERT INTO `recommendation_logs` VALUES (24, 4, 8, 'hybrid', 0.73, 3, 0, '2025-03-21 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (25, 5, 3, 'item_cf', 0.76, 1, 1, '2025-03-18 22:20:36', '2025-03-18 22:20:36');
INSERT INTO `recommendation_logs` VALUES (26, 5, 9, 'user_cf', 0.69, 2, 0, '2025-03-18 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (27, 5, 1, 'hybrid', 0.73, 3, 1, '2025-03-18 22:20:36', '2025-03-18 22:20:36');
INSERT INTO `recommendation_logs` VALUES (28, 5, 11, 'item_cf', 0.64, 1, 0, '2025-03-23 22:20:36', NULL);
INSERT INTO `recommendation_logs` VALUES (29, 5, 5, 'user_cf', 0.67, 2, 1, '2025-03-23 22:20:36', '2025-03-23 22:20:36');
INSERT INTO `recommendation_logs` VALUES (30, 5, 7, 'hybrid', 0.71, 3, 0, '2025-03-23 22:20:36', NULL);

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
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的邮箱',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的个人简介',
  `status` int(11) NULL DEFAULT 1 COMMENT '用户的状态',
  `is_muted` tinyint(1) NULL DEFAULT 0 COMMENT '当前用户是否已被禁止发言',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', 'test', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'http://localhost:8080/images/adc4f2a0-5db7-4c69-aa69-faec2f8eae56.jpg', 0, 1, NULL, NULL, 'asdfasdfasd', 1, 0);
INSERT INTO `user` VALUES (2, 'admin', 'admin', 'admin', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, NULL, NULL, NULL, 1, 0);
INSERT INTO `user` VALUES (3, 'liandong111', NULL, 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-30 15:56:39', '4822221121@qq.com', '热爱生活手机控一枚~', 1, 0);
INSERT INTO `user` VALUES (4, 'liandong11', NULL, 'admin', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-30 15:56:39', '48222221@qq.com', '热爱生活手机控一枚~', 1, 0);
INSERT INTO `user` VALUES (5, 'svwhcx', 'svwh', 'admin', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, NULL, '1548222103@qq.com', NULL, 1, 0);
INSERT INTO `user` VALUES (6, 'liandong', NULL, 'admin', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-30 15:56:39', '4822222@qq.com', '热爱生活手机控一枚~', 1, 0);
INSERT INTO `user` VALUES (7, 'liandong1', NULL, 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-30 16:10:13', '480252715@qq.com', NULL, 1, 0);
INSERT INTO `user` VALUES (8, 'user1', '手机达人', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-02-28 22:20:36', 'user1@example.com', '热爱科技，专注手机评测5年', 1, 0);
INSERT INTO `user` VALUES (9, 'user2', '科技咖', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-02 22:20:36', 'user2@example.com', '数码博主，分享最新手机体验', 1, 0);
INSERT INTO `user` VALUES (10, 'user3', '拍照控', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-05 22:20:36', 'user3@example.com', '专注手机摄影，分享拍照技巧', 1, 0);
INSERT INTO `user` VALUES (11, 'user4', '游戏玩家', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-10 22:20:36', 'user4@example.com', '手游发烧友，关注手机性能', 1, 0);
INSERT INTO `user` VALUES (12, 'user5', '电池党', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-15 22:20:36', 'user5@example.com', '续航至上，分享省电技巧', 1, 0);
INSERT INTO `user` VALUES (13, 'user6', '性价比专家', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-20 22:20:36', 'user6@example.com', '为你推荐最具性价比的手机', 1, 0);
INSERT INTO `user` VALUES (14, 'user7', '旗舰控', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-25 22:20:36', 'user7@example.com', '只用旗舰机，分享高端体验', 1, 0);
INSERT INTO `user` VALUES (15, 'user8', '系统优化师', 'user', 'A6453B0F8A0B3170B1A50E81102CB714', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 0, 1, '2025-03-27 22:20:36', 'user8@example.com', '专注系统优化与定制', 1, 0);

-- ----------------------------
-- Table structure for user_interactions
-- ----------------------------
DROP TABLE IF EXISTS `user_interactions`;
CREATE TABLE `user_interactions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交互ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_id` bigint(20) NOT NULL COMMENT '交互对象ID（文章ID、评论ID等）',
  `item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交互对象类型：post-文章，comment-评论，phone-手机型号',
  `action_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交互类型：like-点赞，favorite-收藏，view-浏览',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_item_action`(`user_id`, `item_id`, `item_type`, `action_type`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_item_id_type`(`item_id`, `item_type`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_action_type`(`action_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户交互表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interactions
-- ----------------------------
INSERT INTO `user_interactions` VALUES (1, 1, 2, 'post', 'view', '2025-02-28 22:20:36', '2025-02-28 22:20:36');
INSERT INTO `user_interactions` VALUES (2, 1, 3, 'post', 'view', '2025-03-01 22:20:36', '2025-03-01 22:20:36');
INSERT INTO `user_interactions` VALUES (3, 1, 4, 'post', 'view', '2025-03-02 22:20:36', '2025-03-02 22:20:36');
INSERT INTO `user_interactions` VALUES (4, 1, 5, 'post', 'view', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (5, 1, 6, 'post', 'view', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (6, 1, 7, 'post', 'view', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (7, 1, 10, 'post', 'view', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (8, 1, 12, 'post', 'view', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (9, 2, 2, 'post', 'view', '2025-03-01 22:20:36', '2025-03-01 22:20:36');
INSERT INTO `user_interactions` VALUES (10, 2, 3, 'post', 'view', '2025-03-02 22:20:36', '2025-03-02 22:20:36');
INSERT INTO `user_interactions` VALUES (11, 2, 5, 'post', 'view', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (12, 2, 7, 'post', 'view', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (13, 2, 9, 'post', 'view', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (14, 2, 11, 'post', 'view', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (15, 2, 13, 'post', 'view', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (16, 3, 2, 'post', 'view', '2025-03-02 22:20:36', '2025-03-02 22:20:36');
INSERT INTO `user_interactions` VALUES (17, 3, 4, 'post', 'view', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (18, 3, 6, 'post', 'view', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (19, 3, 8, 'post', 'view', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (20, 3, 10, 'post', 'view', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (21, 3, 12, 'post', 'view', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (22, 3, 14, 'post', 'view', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (23, 4, 3, 'post', 'view', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (24, 4, 5, 'post', 'view', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (25, 4, 7, 'post', 'view', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (26, 4, 9, 'post', 'view', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (27, 4, 11, 'post', 'view', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (28, 4, 13, 'post', 'view', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (29, 5, 2, 'post', 'view', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (30, 5, 4, 'post', 'view', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (31, 5, 6, 'post', 'view', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (32, 5, 8, 'post', 'view', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (33, 5, 10, 'post', 'view', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (34, 5, 12, 'post', 'view', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (35, 5, 14, 'post', 'view', '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `user_interactions` VALUES (36, 1, 2, 'post', 'like', '2025-03-01 22:20:36', '2025-03-01 22:20:36');
INSERT INTO `user_interactions` VALUES (37, 1, 4, 'post', 'like', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (38, 1, 6, 'post', 'like', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (39, 1, 10, 'post', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (40, 1, 3, 'comment', 'like', '2025-02-27 22:20:36', '2025-02-27 22:20:36');
INSERT INTO `user_interactions` VALUES (41, 1, 7, 'comment', 'like', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (42, 1, 11, 'comment', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (43, 2, 3, 'post', 'like', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (44, 2, 5, 'post', 'like', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (45, 2, 7, 'post', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (46, 2, 11, 'post', 'like', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (47, 2, 1, 'comment', 'like', '2025-02-26 22:20:36', '2025-02-26 22:20:36');
INSERT INTO `user_interactions` VALUES (48, 2, 5, 'comment', 'like', '2025-03-02 22:20:36', '2025-03-02 22:20:36');
INSERT INTO `user_interactions` VALUES (49, 2, 9, 'comment', 'like', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (50, 3, 2, 'post', 'like', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (51, 3, 4, 'post', 'like', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (52, 3, 8, 'post', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (53, 3, 12, 'post', 'like', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (54, 3, 2, 'comment', 'like', '2025-02-27 22:20:36', '2025-02-27 22:20:36');
INSERT INTO `user_interactions` VALUES (55, 3, 6, 'comment', 'like', '2025-03-03 22:20:36', '2025-03-03 22:20:36');
INSERT INTO `user_interactions` VALUES (56, 3, 10, 'comment', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (57, 4, 3, 'post', 'like', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (58, 4, 7, 'post', 'like', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (59, 4, 9, 'post', 'like', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (60, 4, 13, 'post', 'like', '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `user_interactions` VALUES (61, 4, 4, 'comment', 'like', '2025-02-28 22:20:36', '2025-02-28 22:20:36');
INSERT INTO `user_interactions` VALUES (62, 4, 8, 'comment', 'like', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (63, 4, 12, 'comment', 'like', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (64, 5, 2, 'post', 'like', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (65, 5, 6, 'post', 'like', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (66, 5, 10, 'post', 'like', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (67, 5, 14, 'post', 'like', '2025-03-11 22:20:36', '2025-03-11 22:20:36');
INSERT INTO `user_interactions` VALUES (68, 5, 1, 'comment', 'like', '2025-02-25 22:20:36', '2025-02-25 22:20:36');
INSERT INTO `user_interactions` VALUES (69, 5, 5, 'comment', 'like', '2025-03-01 22:20:36', '2025-03-01 22:20:36');
INSERT INTO `user_interactions` VALUES (70, 5, 9, 'comment', 'like', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (71, 5, 13, 'comment', 'like', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (72, 1, 2, 'post', 'favorite', '2025-03-02 22:20:36', '2025-03-02 22:20:36');
INSERT INTO `user_interactions` VALUES (73, 1, 6, 'post', 'favorite', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (74, 1, 10, 'post', 'favorite', '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `user_interactions` VALUES (75, 2, 3, 'post', 'favorite', '2025-03-04 22:20:36', '2025-03-04 22:20:36');
INSERT INTO `user_interactions` VALUES (76, 2, 7, 'post', 'favorite', '2025-03-08 22:20:36', '2025-03-08 22:20:36');
INSERT INTO `user_interactions` VALUES (77, 2, 11, 'post', 'favorite', '2025-03-12 22:20:36', '2025-03-12 22:20:36');
INSERT INTO `user_interactions` VALUES (78, 3, 2, 'post', 'favorite', '2025-03-05 22:20:36', '2025-03-05 22:20:36');
INSERT INTO `user_interactions` VALUES (79, 3, 8, 'post', 'favorite', '2025-03-09 22:20:36', '2025-03-09 22:20:36');
INSERT INTO `user_interactions` VALUES (80, 3, 12, 'post', 'favorite', '2025-03-13 22:20:36', '2025-03-13 22:20:36');
INSERT INTO `user_interactions` VALUES (81, 4, 3, 'post', 'favorite', '2025-03-06 22:20:36', '2025-03-06 22:20:36');
INSERT INTO `user_interactions` VALUES (82, 4, 9, 'post', 'favorite', '2025-03-10 22:20:36', '2025-03-10 22:20:36');
INSERT INTO `user_interactions` VALUES (83, 4, 13, 'post', 'favorite', '2025-03-14 22:20:36', '2025-03-14 22:20:36');
INSERT INTO `user_interactions` VALUES (84, 5, 4, 'post', 'favorite', '2025-03-07 22:20:36', '2025-03-07 22:20:36');
INSERT INTO `user_interactions` VALUES (85, 5, 8, 'post', 'favorite', '2025-03-11 22:20:36', '2025-03-11 22:20:36');
INSERT INTO `user_interactions` VALUES (86, 5, 12, 'post', 'favorite', '2025-03-15 22:20:36', '2025-03-15 22:20:36');

-- ----------------------------
-- Table structure for user_similarities
-- ----------------------------
DROP TABLE IF EXISTS `user_similarities`;
CREATE TABLE `user_similarities`  (
  `user1_id` bigint(20) NOT NULL COMMENT '用户1ID',
  `user2_id` bigint(20) NOT NULL COMMENT '用户2ID',
  `similarity` float NOT NULL COMMENT '相似度值，范围0-1',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`user1_id`, `user2_id`) USING BTREE,
  INDEX `idx_user2`(`user2_id`) USING BTREE,
  INDEX `idx_similarity`(`similarity`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户相似度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_similarities
-- ----------------------------
INSERT INTO `user_similarities` VALUES (1, 2, 0.65, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 3, 0.58, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 4, 0.72, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 5, 0.53, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 6, 0.48, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 7, 0.67, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (1, 8, 0.45, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 3, 0.7, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 4, 0.55, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 5, 0.62, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 6, 0.58, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 7, 0.5, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (2, 8, 0.48, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (3, 4, 0.48, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (3, 5, 0.75, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (3, 6, 0.52, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (3, 7, 0.45, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (3, 8, 0.6, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (4, 5, 0.42, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (4, 6, 0.68, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (4, 7, 0.73, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (4, 8, 0.55, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (5, 6, 0.5, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (5, 7, 0.4, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (5, 8, 0.65, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (6, 7, 0.58, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (6, 8, 0.52, '2025-03-30 22:20:36', '2025-03-30 22:20:36');
INSERT INTO `user_similarities` VALUES (7, 8, 0.47, '2025-03-30 22:20:36', '2025-03-30 22:20:36');

SET FOREIGN_KEY_CHECKS = 1;
