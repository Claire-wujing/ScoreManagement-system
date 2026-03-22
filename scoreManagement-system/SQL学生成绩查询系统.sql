/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : scoremanage

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 06/01/2025 11:59:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `clazzNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级号',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  PRIMARY KEY (`clazzNo`) USING BTREE,
  INDEX `studentId`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('2023计科1班', '1');
INSERT INTO `clazz` VALUES ('2023计科2班', '1');
INSERT INTO `clazz` VALUES ('2023计科3班', '1');
INSERT INTO `clazz` VALUES ('2023计科4班', '1');
INSERT INTO `clazz` VALUES ('2023计科5班', '1');
INSERT INTO `clazz` VALUES ('2023计科6班', '1');
INSERT INTO `clazz` VALUES ('2023计科7班', '1');
INSERT INTO `clazz` VALUES ('2023计科8班', '1');
INSERT INTO `clazz` VALUES ('2023计科9班', '0');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `courseNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程号',
  `courseName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `semster` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学期',
  PRIMARY KEY (`courseNo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('JK20240101', 'Java程序设计', '0', '2024-2025-1');
INSERT INTO `course` VALUES ('JK20240102', '面向对象程序设计', '1', '2024-2025-1');
INSERT INTO `course` VALUES ('JK20240103', '计算机组成原理', '1', '2024-2025-1');
INSERT INTO `course` VALUES ('JK20240201', '网页设计与制作', '1', '2024-2025-2');
INSERT INTO `course` VALUES ('JK20240104', 'Python编程', '1', '2024-2025-1');
INSERT INTO `course` VALUES ('TY20240201', '大学体育', '1', '2024-2025-2');
INSERT INTO `course` VALUES ('SJ20240201', '数据库原理与应用', '1', '2024-2025-2');
INSERT INTO `course` VALUES ('GX20240201', '大学生心理健康', '1', '2024-2025-2');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `studentId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  `courseNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程号',
  `score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '成绩',
  INDEX `studentF`(`studentId`) USING BTREE,
  INDEX `courseF`(`courseNo`) USING BTREE,
  CONSTRAINT `courseF` FOREIGN KEY (`courseNo`) REFERENCES `course` (`courseNo`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `studentF` FOREIGN KEY (`studentId`) REFERENCES `student` (`studentId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('2023030101', 'JK20240102', '98');
INSERT INTO `score` VALUES ('2023030101', 'JK20240103', '95');
INSERT INTO `score` VALUES ('2023030102', 'JK20240104', '88');
INSERT INTO `score` VALUES ('2023030102', 'JK20240102', '92');
INSERT INTO `score` VALUES ('2023030201', 'TY20240201', '85');
INSERT INTO `score` VALUES ('2023030201', 'SJ20240201', '90');
INSERT INTO `score` VALUES ('2023030202', 'GX20240201', '96');
INSERT INTO `score` VALUES ('2023030202', 'JK20240201', '89');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `studentId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `clazzNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '班级号',
  PRIMARY KEY (`studentId`) USING BTREE,
  INDEX `clazzfk`(`clazzNo`) USING BTREE,
  CONSTRAINT `clazzfk` FOREIGN KEY (`clazzNo`) REFERENCES `clazz` (`clazzNo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2023030101', '王小明', 20, '男', '123456', '1', '2023计科1班');
INSERT INTO `student` VALUES ('2023030102', '李小红', 19, '女', '123456', '1', '2023计科1班');
INSERT INTO `student` VALUES ('2023030103', '张磊', 21, '男', '123456', '1', '2023计科1班');
INSERT INTO `student` VALUES ('2023030201', '刘芳', 20, '女', '123456', '1', '2023计科2班');
INSERT INTO `student` VALUES ('2023030202', '赵强', 20, '男', '123456', '1', '2023计科2班');
INSERT INTO `student` VALUES ('2023030203', '孙丽', 19, '女', '123456', '1', '2023计科2班');
INSERT INTO `student` VALUES ('2023030301', '周杰', 21, '男', '123456', '1', '2023计科3班');
INSERT INTO `student` VALUES ('2023030901', '吴浩', 20, '男', '123456', '1', '2023计科9班');
INSERT INTO `student` VALUES ('2023030902', '郑晓', 19, '女', '123456', '1', '2023计科9班');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacherId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '教师号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `age` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年龄',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `courseNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程号',
  PRIMARY KEY (`teacherId`) USING BTREE,
  INDEX `courseFk`(`courseNo`) USING BTREE,
  CONSTRAINT `courseFk` FOREIGN KEY (`courseNo`) REFERENCES `course` (`courseNo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('T2024001', '王老师', '35', '男', '123456', '1', 'JK20240201');
INSERT INTO `teacher` VALUES ('T2024002', '李老师', '28', '女', '123456', '1', 'JK20240102');
INSERT INTO `teacher` VALUES ('T2024003', '张老师', '42', '男', '123456', '1', 'JK20240102');
INSERT INTO `teacher` VALUES ('T2024004', '刘老师', '38', '男', '123456', '1', 'JK20240102');
INSERT INTO `teacher` VALUES ('T2024005', '赵老师', '30', '男', '123456', '1', 'JK20240102');
INSERT INTO `teacher` VALUES ('T2024006', '孙老师', '45', '女', '123456', '1', 'JK20240104');
INSERT INTO `teacher` VALUES ('T2024007', '周老师', '40', '女', '123456', '1', 'JK20240104');

SET FOREIGN_KEY_CHECKS = 1;