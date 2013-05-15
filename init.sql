-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AUTHORITIES`
--

DROP TABLE IF EXISTS `AUTHORITIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHORITIES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authDesc` varchar(60) DEFAULT NULL,
  `authName` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHORITIES`
--

LOCK TABLES `AUTHORITIES` WRITE;
/*!40000 ALTER TABLE `AUTHORITIES` DISABLE KEYS */;
INSERT INTO `AUTHORITIES` VALUES (1,'对角色的管理权限','ROLE_OPER'),(2,'对员工的管理权限','EMP_OPER'),(3,'普通用户权限','COMM'),(4,'部门管理权限','DEPT_OPER');
/*!40000 ALTER TABLE `AUTHORITIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DEPT`
--

DROP TABLE IF EXISTS `DEPT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DEPT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info` varchar(255) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1FF64533BB1F55` (`parent_id`),
  CONSTRAINT `FK1FF64533BB1F55` FOREIGN KEY (`parent_id`) REFERENCES `DEPT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DEPT`
--

LOCK TABLES `DEPT` WRITE;
/*!40000 ALTER TABLE `DEPT` DISABLE KEYS */;
INSERT INTO `DEPT` VALUES (1,'管钱','高级财务部',NULL),(2,'管','中级财务部',1),(3,'钱','中级2财务部',2),(4,'人','人力资源部',NULL),(5,'好','好人',4),(6,'好','不错',5),(7,'人','还行',6),(8,'差','差',7);
/*!40000 ALTER TABLE `DEPT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EMPS`
--

DROP TABLE IF EXISTS `EMPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(40) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` date DEFAULT NULL,
  `die` tinyint(1) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `hiredDay` date DEFAULT NULL,
  `idCard` varchar(40) DEFAULT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `realname` varchar(40) DEFAULT NULL,
  `salary` float NOT NULL,
  `username` varchar(40) NOT NULL,
  `dept_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2088AB14FD7FDA` (`dept_Id`),
  CONSTRAINT `FK2088AB14FD7FDA` FOREIGN KEY (`dept_Id`) REFERENCES `DEPT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPS`
--

LOCK TABLES `EMPS` WRITE;
/*!40000 ALTER TABLE `EMPS` DISABLE KEYS */;
INSERT INTO `EMPS` VALUES (1,NULL,0,NULL,0,NULL,NULL,'2013-05-08',NULL,'admin',NULL,NULL,0,'admin',NULL),(2,'订房',23,'2013-05-07',0,'3434','Female','2013-04-29','424','12345','34','经理',1000,'jingli',NULL),(3,'订房',23,'2013-05-07',0,'3434','Female','2013-04-29','424','12345','34','经理',1000,'jingli',3),(4,'',0,NULL,1,'','Female',NULL,'','2323232','','',0,'2323232',8),(5,'',0,NULL,0,'','Female',NULL,'','xxxxxxxxx','','',0,'ccccccccx',NULL),(6,'',0,NULL,0,'','Female',NULL,'','xxxxxxx','','',0,'xxxxxxxx',NULL),(7,'',0,NULL,0,'','Female',NULL,'','xxxxxx','','',0,'xxxxxxx',NULL),(8,'',0,NULL,0,'','Female',NULL,'','sssssss','','',0,'ssssssss',NULL),(9,'',0,NULL,0,'','Female',NULL,'','ccccc','','',0,'cccccccc',NULL),(10,'',0,NULL,0,'','Female',NULL,'','aaaaaaaaaa','','',0,'aaaaaaaaa',NULL),(11,'',0,NULL,0,'','Female',NULL,'','ddddddddddddd','','',0,'ddddddddddddd',NULL),(12,'',0,NULL,0,'','Female',NULL,'','ddddddd','','',0,'ddddddddd',NULL),(13,'',0,NULL,0,'','Female',NULL,'','232323232','','',0,'232323',NULL),(14,'',0,NULL,0,'','Female',NULL,'','232323','','',0,'232424',NULL);
/*!40000 ALTER TABLE `EMPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EMPS_ROLES`
--

DROP TABLE IF EXISTS `EMPS_ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPS_ROLES` (
  `EMP_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`EMP_ID`,`ROLE_ID`),
  KEY `FKB429160960E636D` (`ROLE_ID`),
  KEY `FKB4291609B55E1693` (`EMP_ID`),
  CONSTRAINT `FKB4291609B55E1693` FOREIGN KEY (`EMP_ID`) REFERENCES `EMPS` (`id`),
  CONSTRAINT `FKB429160960E636D` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPS_ROLES`
--

LOCK TABLES `EMPS_ROLES` WRITE;
/*!40000 ALTER TABLE `EMPS_ROLES` DISABLE KEYS */;
INSERT INTO `EMPS_ROLES` VALUES (1,1);
/*!40000 ALTER TABLE `EMPS_ROLES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLES`
--

DROP TABLE IF EXISTS `ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLES`
--

LOCK TABLES `ROLES` WRITE;
/*!40000 ALTER TABLE `ROLES` DISABLE KEYS */;
INSERT INTO `ROLES` VALUES (1,'系统管理员'),(2,'总经理'),(3,'经理'),(4,'员工管理员'),(5,'部门管理员');
/*!40000 ALTER TABLE `ROLES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLES_AUTHORITIES`
--

DROP TABLE IF EXISTS `ROLES_AUTHORITIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLES_AUTHORITIES` (
  `ROLE_ID` bigint(20) NOT NULL,
  `AUTHORITY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`AUTHORITY_ID`),
  KEY `FKDB21985F48CCA947` (`AUTHORITY_ID`),
  KEY `FKDB21985F60E636D` (`ROLE_ID`),
  CONSTRAINT `FKDB21985F60E636D` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLES` (`id`),
  CONSTRAINT `FKDB21985F48CCA947` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `AUTHORITIES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLES_AUTHORITIES`
--

LOCK TABLES `ROLES_AUTHORITIES` WRITE;
/*!40000 ALTER TABLE `ROLES_AUTHORITIES` DISABLE KEYS */;
INSERT INTO `ROLES_AUTHORITIES` VALUES (1,1),(2,1),(1,2),(2,2),(3,2),(4,2),(1,3),(2,3),(3,3),(4,3),(5,3),(1,4),(2,4),(3,4),(5,4);
/*!40000 ALTER TABLE `ROLES_AUTHORITIES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-05-08  4:28:11
