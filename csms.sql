/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : localhost:3306
 Source Schema         : csms

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 22/05/2023 13:59:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_table`;
CREATE TABLE `admin_table`  (
  `ucode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ucode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_table
-- ----------------------------
INSERT INTO `admin_table` VALUES ('admin', 'admin', '12345678910');

-- ----------------------------
-- Table structure for id_table
-- ----------------------------
DROP TABLE IF EXISTS `id_table`;
CREATE TABLE `id_table`  (
  `id` int NOT NULL,
  `passwd` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `idtype` int NOT NULL,
  `state` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `idtype` CHECK ((`idtype` = 0) or (`idtype` = 1))
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of id_table
-- ----------------------------
INSERT INTO `id_table` VALUES (20200000, '$2a$10$90GOtyeQCZo.r/s/z013M.RaYE.E/C6Q.8fXh8tnX4/1OVKXbr2Uy', '12345@qq.com', 0, 1);
INSERT INTO `id_table` VALUES (20200001, '$2a$10$WaynhpMJDVJA5bnruX0LyOzRbu3I/neu1e4zVyzarqF3DLaHz5Iia', '123456@xx.com', 1, 1);
INSERT INTO `id_table` VALUES (20200002, '$2a$10$bHXkSFqlr5tIsCinCmFP5e7K9iXECY7Fid6Cq7HoWXg.Jrbvuu1zC', '1431279@qq.com', 1, 0);
INSERT INTO `id_table` VALUES (20200003, '$2a$10$1IpWRS9ZgcfVQnEpIvsIfOlwtMwG6RP9lHTG/kSXddXKHKXSnjSSy', '123456@qq.com', 1, 0);
INSERT INTO `id_table` VALUES (20200011, '$2a$10$AYme4B.0wDITqFmNb.E2tuvnJ.pUeDL37EePmlv/opStVGrtHa0hi', '邮箱待添加', 1, 0);
INSERT INTO `id_table` VALUES (20200012, '$2a$10$V5DwJO7J8jk7RvH2UdH11Op/seBZtTQhf5j3GeWJeQy5xiy4svv4e', '邮箱待添加', 1, 0);
INSERT INTO `id_table` VALUES (20200013, '$2a$10$xfo/PfL.DzKlUkXtDN8COu7zndy/MVY0BTu.DKGUVfu9JNX7ayNPe', '邮箱待添加', 1, 0);
INSERT INTO `id_table` VALUES (20200014, '$2a$10$7Qr7hwQE13bBvg.GcQiUoebtedFmKTUo52KSFafFCDoFcyK2ajBTe', '邮箱待添加', 1, 0);
INSERT INTO `id_table` VALUES (20200015, '$2a$10$XwaOSvDHULt1k.rZ0FhGUeHWDijs4kNC2LffPewv7n.aNNlZjtRGe', '12345@xx.com', 1, 0);

-- ----------------------------
-- Table structure for in_table
-- ----------------------------
DROP TABLE IF EXISTS `in_table`;
CREATE TABLE `in_table`  (
  `icode` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pcode` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` int NOT NULL,
  `itime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `rcode` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stype` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ucode` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`icode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of in_table
-- ----------------------------
INSERT INTO `in_table` VALUES ('168466596420200001', '1003', 40, '2023-05-14 18:46:04', '3001', '中型', '20200001');
INSERT INTO `in_table` VALUES ('168466598920200001', '1004', 60, '2023-05-15 18:46:29', '3001', '大型', '20200001');
INSERT INTO `in_table` VALUES ('168466611720200001', '1001', 20, '2023-05-16 18:48:37', '3001', '小型', '20200001');
INSERT INTO `in_table` VALUES ('168466614620200001', '1002', 20, '2023-05-17 18:49:07', '3001', '小型', '20200001');
INSERT INTO `in_table` VALUES ('168466616720200001', '1004', 40, '2023-05-18 18:49:27', '3001', '中型', '20200001');
INSERT INTO `in_table` VALUES ('168466618420200001', '1004', 60, '2023-05-19 18:49:45', '3001', '大型', '20200001');
INSERT INTO `in_table` VALUES ('168466623020200001', '1007', 40, '2023-05-20 18:50:30', '3001', '中型', '20200001');
INSERT INTO `in_table` VALUES ('168466625420200001', '1007', 40, '2023-05-21 18:50:55', '3001', '中型', '20200001');
INSERT INTO `in_table` VALUES ('168466685520200001', '1003', 20, '2023-05-21 19:00:56', '3001', '小型', '20200001');
INSERT INTO `in_table` VALUES ('168473417420200001', '1005', 40, '2023-05-22 13:42:54', '3001', '中型', '20200001');

-- ----------------------------
-- Table structure for out_table
-- ----------------------------
DROP TABLE IF EXISTS `out_table`;
CREATE TABLE `out_table`  (
  `ocode` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pcode` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rcode` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` int NOT NULL,
  `otime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `ucode` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ocode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of out_table
-- ----------------------------
INSERT INTO `out_table` VALUES ('168466603020200001', '1001', '3001', 20, '2023-05-17 18:47:11', '20200001');
INSERT INTO `out_table` VALUES ('168466606920200001', '1002', '3001', 20, '2023-05-18 18:47:49', '20200001');
INSERT INTO `out_table` VALUES ('168466609020200001', '1003', '3001', 40, '2023-05-19 18:48:10', '20200001');
INSERT INTO `out_table` VALUES ('168466610520200001', '1004', '3001', 60, '2023-05-20 18:48:25', '20200001');
INSERT INTO `out_table` VALUES ('168466623920200001', '1007', '3001', 40, '2023-05-21 18:50:39', '20200001');
INSERT INTO `out_table` VALUES ('168466686820200001', '1003', '3001', 20, '2023-05-21 19:01:08', '20200001');
INSERT INTO `out_table` VALUES ('168473426720200001', '1005', '3001', 20, '2023-05-22 13:44:28', '20200001');

-- ----------------------------
-- Table structure for pa_po_table
-- ----------------------------
DROP TABLE IF EXISTS `pa_po_table`;
CREATE TABLE `pa_po_table`  (
  `pcode` int NOT NULL,
  `rcode` int NOT NULL,
  `scode` int NOT NULL,
  `stype` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` int NOT NULL,
  PRIMARY KEY (`pcode`, `rcode`, `scode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pa_po_table
-- ----------------------------
INSERT INTO `pa_po_table` VALUES (1001, 3001, 2001, '小型', 20);
INSERT INTO `pa_po_table` VALUES (1002, 3001, 2002, '小型', 20);
INSERT INTO `pa_po_table` VALUES (1004, 3001, 2004, '中型', 40);
INSERT INTO `pa_po_table` VALUES (1004, 3001, 2009, '大型', 60);
INSERT INTO `pa_po_table` VALUES (1005, 3001, 2006, '中型', 20);
INSERT INTO `pa_po_table` VALUES (1007, 3001, 2005, '中型', 40);

-- ----------------------------
-- Table structure for part_table
-- ----------------------------
DROP TABLE IF EXISTS `part_table`;
CREATE TABLE `part_table`  (
  `pcode` int NOT NULL AUTO_INCREMENT COMMENT '配件号',
  `pname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ptype` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manufacture` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `protime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Warrantytime` int NOT NULL,
  `info` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` int NOT NULL COMMENT '一箱中有几个商品',
  PRIMARY KEY (`pcode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1008 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of part_table
-- ----------------------------
INSERT INTO `part_table` VALUES (1001, 'RTX4090', '显卡', '技嘉', '2023-05-21 18:24:28', 2, '无', 20);
INSERT INTO `part_table` VALUES (1002, 'i9-13900k', 'cpu', 'intel', '2023-05-21 18:24:31', 2, '散片', 40);
INSERT INTO `part_table` VALUES (1003, 'gpro wireless', '鼠标', '罗技', '2023-05-21 18:24:35', 2, '无线鼠标', 20);
INSERT INTO `part_table` VALUES (1004, '毒蝰v2', '鼠标', '雷蛇', '2023-05-21 18:24:41', 2, '三模鼠标', 20);
INSERT INTO `part_table` VALUES (1005, '达尔优A87', '键盘', '达尔优', '2023-05-21 18:24:46', 1, 'RGB炫光版', 10);
INSERT INTO `part_table` VALUES (1006, 'Ryzen 5 7600X', 'cpu', 'AMD', '2023-05-21 18:24:53', 2, '盒装', 20);
INSERT INTO `part_table` VALUES (1007, '友达IPS', '显示器', '雷神', '2023-05-21 18:24:59', 1, '240hz,ips,17英寸', 5);

-- ----------------------------
-- Table structure for role_table
-- ----------------------------
DROP TABLE IF EXISTS `role_table`;
CREATE TABLE `role_table`  (
  `idtype` int NOT NULL,
  `typeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`idtype`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_table
-- ----------------------------
INSERT INTO `role_table` VALUES (0, '仓库管理员');
INSERT INTO `role_table` VALUES (1, '普通员工');

-- ----------------------------
-- Table structure for sh_table
-- ----------------------------
DROP TABLE IF EXISTS `sh_table`;
CREATE TABLE `sh_table`  (
  `scode` int NOT NULL AUTO_INCREMENT,
  `stype` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `snum` int NULL DEFAULT NULL COMMENT '货架容量',
  `whcode` int NULL DEFAULT NULL,
  PRIMARY KEY (`scode`) USING BTREE,
  CONSTRAINT `snum` CHECK (`snum` > 0)
) ENGINE = InnoDB AUTO_INCREMENT = 2042 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sh_table
-- ----------------------------
INSERT INTO `sh_table` VALUES (2001, '小型', 20, 3001);
INSERT INTO `sh_table` VALUES (2002, '小型', 20, 3001);
INSERT INTO `sh_table` VALUES (2003, '小型', 20, 3001);
INSERT INTO `sh_table` VALUES (2004, '中型', 40, 3001);
INSERT INTO `sh_table` VALUES (2005, '中型', 40, 3001);
INSERT INTO `sh_table` VALUES (2006, '中型', 40, 3001);
INSERT INTO `sh_table` VALUES (2007, '中型', 40, 3001);
INSERT INTO `sh_table` VALUES (2008, '中型', 40, 3001);
INSERT INTO `sh_table` VALUES (2009, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2010, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2011, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2012, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2013, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2014, '大型', 60, 3001);
INSERT INTO `sh_table` VALUES (2015, '小型', 20, 3002);
INSERT INTO `sh_table` VALUES (2016, '小型', 20, 3002);
INSERT INTO `sh_table` VALUES (2017, '小型', 20, 3002);
INSERT INTO `sh_table` VALUES (2018, '中型', 60, 3002);
INSERT INTO `sh_table` VALUES (2019, '中型', 60, 3002);
INSERT INTO `sh_table` VALUES (2020, '中型', 60, 3002);
INSERT INTO `sh_table` VALUES (2021, '大型', 60, 3002);
INSERT INTO `sh_table` VALUES (2022, '大型', 60, 3002);
INSERT INTO `sh_table` VALUES (2023, '大型', 60, 3002);
INSERT INTO `sh_table` VALUES (2024, '大型', 60, 3002);
INSERT INTO `sh_table` VALUES (2025, '大型', 60, 3002);
INSERT INTO `sh_table` VALUES (2026, '小型', 20, 3003);
INSERT INTO `sh_table` VALUES (2027, '小型', 20, 3003);
INSERT INTO `sh_table` VALUES (2028, '小型', 20, 3003);
INSERT INTO `sh_table` VALUES (2029, '小型', 20, 3003);
INSERT INTO `sh_table` VALUES (2030, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2031, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2032, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2033, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2034, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2035, '中型', 40, 3003);
INSERT INTO `sh_table` VALUES (2036, '大型', 60, 3003);
INSERT INTO `sh_table` VALUES (2037, '大型', 60, 3003);
INSERT INTO `sh_table` VALUES (2038, '大型', 60, 3003);
INSERT INTO `sh_table` VALUES (2039, '大型', 60, 3003);
INSERT INTO `sh_table` VALUES (2040, '大型', 60, 3003);
INSERT INTO `sh_table` VALUES (2041, '大型', 60, 3003);

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `ucode` int NOT NULL AUTO_INCREMENT,
  `rcode` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uname` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ucode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20200006 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES (20200000, NULL, 'admin', '12345678910');
INSERT INTO `user_table` VALUES (20200001, '3001', '张三', '12345678901');
INSERT INTO `user_table` VALUES (20200002, '3002', '李四', '12345678911');
INSERT INTO `user_table` VALUES (20200003, '3003', '王五', '12345678912');

-- ----------------------------
-- Table structure for wh_table
-- ----------------------------
DROP TABLE IF EXISTS `wh_table`;
CREATE TABLE `wh_table`  (
  `whcode` int NOT NULL AUTO_INCREMENT,
  `whname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `snum` int NOT NULL,
  `mnum` int NOT NULL,
  `bnum` int NOT NULL,
  PRIMARY KEY (`whcode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3005 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wh_table
-- ----------------------------
INSERT INTO `wh_table` VALUES (3001, '一号仓', '北京市海淀区', 3, 2, 5);
INSERT INTO `wh_table` VALUES (3002, '二号仓', '北京市朝阳区', 3, 3, 5);
INSERT INTO `wh_table` VALUES (3003, '三号仓', '北京市清河区', 4, 6, 6);

SET FOREIGN_KEY_CHECKS = 1;
