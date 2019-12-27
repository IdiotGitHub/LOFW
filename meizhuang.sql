-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: meizhuang
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `item_info`
--

DROP TABLE IF EXISTS `item_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `con_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `favourite` int(255) NOT NULL DEFAULT '0',
  `likes` int(255) NOT NULL DEFAULT '0',
  `comments` int(255) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_info`
--

LOCK TABLES `item_info` WRITE;
/*!40000 ALTER TABLE `item_info` DISABLE KEYS */;
INSERT INTO `item_info` VALUES (1,'美妆论坛第一次测试贴','',1,1,2,3,'2019-11-26 17:23:38'),(2,'论坛第二次测试贴','',0,1,2,3,'2019-11-27 08:36:09'),(7,'第三次测试上传图片','./img/conImg/54f233f6-6825-4d7b-92c7-a4f5de258ada.jpg',0,1,0,4,'2019-11-27 10:47:42'),(8,'1','',0,0,0,5,'2019-12-19 15:19:04'),(9,'','',0,0,0,5,'2019-12-19 15:31:13'),(10,'','./img/conImg/24c76eb7-7f85-4721-bb7f-ce2a1009382f.png',0,0,0,5,'2019-12-19 16:06:59'),(11,'','./img/conImg/4f09bd57-e00c-45ac-8aae-e0d308d31021.jpg',0,0,0,5,'2019-12-19 16:15:15'),(12,'','./img/conImg/9ff0ad3a-303a-4a63-8c23-8c6dd35e92c9.png',0,0,0,5,'2019-12-19 16:38:41'),(13,'','./img/conImg/46495c9f-2e18-4175-92ac-190be634ffa0.png',0,0,0,5,'2019-12-20 09:48:26'),(14,'','./img/conImg/16f29c48-b2e8-4233-ad14-68d52e5e9ab9.png',0,0,0,5,'2019-12-20 09:58:38'),(15,'','./img/conImg/06b14489-f20b-4136-acf5-b616bc8ead5e.JPG',0,0,0,8,'2019-12-20 10:00:59'),(16,'','./img/conImg/187b9c77-b23d-4d33-a36c-0ddaa5adb552.png',0,0,0,8,'2019-12-20 10:08:30'),(17,'','./img/conImg/15d8724c-81a1-41f0-81d4-6d363199a525.png',0,0,0,8,'2019-12-20 10:09:22'),(18,'','./img/conImg/1fbfe676-fa35-4150-8b2f-8e0a78c0363f.png',0,0,0,5,'2019-12-20 10:09:56'),(19,'','./img/conImg/6fa668fa-b681-440c-a01a-605a8d94c7e9.png',0,1,0,5,'2019-12-20 10:19:21'),(20,'我是狗庚','',0,0,0,5,'2019-12-20 10:28:09'),(21,'我是狗庚2号','',1,1,0,5,'2019-12-20 10:33:14');
/*!40000 ALTER TABLE `item_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_comment`
--

DROP TABLE IF EXISTS `user_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `datetime` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_comment`
--

LOCK TABLES `user_comment` WRITE;
/*!40000 ALTER TABLE `user_comment` DISABLE KEYS */;
INSERT INTO `user_comment` VALUES (1,'美妆第一次测试评论测试','2019-11-26 16:20:01',3,1),(2,'美妆第一次测试第二条评论','2019-11-26 16:20:28',3,1),(3,'第二次测试第一次评论测试','2019-11-27 08:36:37',4,2),(4,'第二次测试贴第二次评论测试','2019-11-27 08:37:04',3,2);
/*!40000 ALTER TABLE `user_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favourite`
--

DROP TABLE IF EXISTS `user_favourite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_favourite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favourite`
--

LOCK TABLES `user_favourite` WRITE;
/*!40000 ALTER TABLE `user_favourite` DISABLE KEYS */;
INSERT INTO `user_favourite` VALUES (2,5,1,'2019-12-13 22:29:40'),(4,5,21,'2019-12-20 11:08:41');
/*!40000 ALTER TABLE `user_favourite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_followed`
--

DROP TABLE IF EXISTS `user_followed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_followed` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '关注人id',
  `followed_id` int(11) NOT NULL DEFAULT '0' COMMENT '被关注人的id',
  `datetime` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_followed`
--

LOCK TABLES `user_followed` WRITE;
/*!40000 ALTER TABLE `user_followed` DISABLE KEYS */;
INSERT INTO `user_followed` VALUES (5,5,3,'2019-12-16 15:15:46'),(10,3,4,'2019-12-17 15:44:29'),(14,8,5,'2019-12-20 09:47:30'),(15,5,8,'2019-12-20 10:08:57');
/*!40000 ALTER TABLE `user_followed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '用户年龄',
  `gender` tinyint(255) NOT NULL DEFAULT '0' COMMENT '用户性别，默认0，1为男，2为女',
  `user_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `telephone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户电话',
  `status` tinyint(255) NOT NULL DEFAULT '0' COMMENT '用户账户状态默认为0，为1可用，为2不可用',
  `fans` int(11) NOT NULL DEFAULT '0' COMMENT '用户粉丝数',
  `follows` int(11) NOT NULL DEFAULT '0' COMMENT '用户关注数',
  `sign` text COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户个性签名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `telephone_index` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (3,'杨阳','13123456789',22,2,'./img/touxiang/touxiang1.jpg','13123456789',1,1,1,''),(4,'小许','15123456789',23,1,'./img/touxiang/xuxuxu.jpeg','15123456789',1,1,0,''),(5,'狗庚','18123456789',23,1,'./img/touxiang/gougeng.jpeg','18123456789',1,1,2,''),(8,'mj','17123456789',22,1,'./img/touxiang/touxiang1.jpg','17123456789',1,1,1,'');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_like`
--

DROP TABLE IF EXISTS `user_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_like`
--

LOCK TABLES `user_like` WRITE;
/*!40000 ALTER TABLE `user_like` DISABLE KEYS */;
INSERT INTO `user_like` VALUES (1,5,2,'2019-12-13 14:58:54'),(4,5,1,'2019-12-13 18:38:12'),(6,3,7,'2019-12-17 13:36:58'),(9,5,19,'2019-12-20 10:52:50'),(10,5,21,'2019-12-20 10:53:42');
/*!40000 ALTER TABLE `user_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_password`
--

DROP TABLE IF EXISTS `user_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '关联用户表的id',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户密码，使用MD5进行加密',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_password`
--

LOCK TABLES `user_password` WRITE;
/*!40000 ALTER TABLE `user_password` DISABLE KEYS */;
INSERT INTO `user_password` VALUES (3,3,'4QrcOUm6Wau+VuBX8g+IPg=='),(4,4,'4QrcOUm6Wau+VuBX8g+IPg=='),(5,5,'4QrcOUm6Wau+VuBX8g+IPg=='),(7,8,'4QrcOUm6Wau+VuBX8g+IPg==');
/*!40000 ALTER TABLE `user_password` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-27  9:16:50
