-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: todo
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `taskid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `datecreated` datetime NOT NULL,
  `description` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `task` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `duedate` datetime DEFAULT NULL,
  PRIMARY KEY (`taskid`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (30,1,'2019-09-13 13:10:17','run home 2019 1971','go home','2019-09-26 05:35:17'),(31,1,'2019-09-13 12:59:48','for another day','buy bread','2019-09-02 00:00:48'),(35,1,'2019-09-13 12:23:43','nesting','testing testing','2019-09-22 16:40:43'),(36,1,'2019-09-12 15:41:19','testing','maintaining','2019-09-25 22:21:00'),(39,1,'2019-09-13 12:29:49','we are testing updates','we','2019-09-13 18:29:49'),(40,1,'2019-09-13 12:45:09','night','nightly','2019-09-13 12:45:09'),(41,1,'2019-09-13 12:55:48','fwfwfw','fdwfw','2019-09-13 14:55:00'),(42,1,'2019-09-13 13:15:47','dwqdw','dwdwq','2019-09-13 13:15:47'),(43,1,'2019-09-13 13:22:55','dwdwdw','dwdw','2019-09-11 17:22:55'),(44,1,'2019-09-13 13:28:06','21212121','dw21','2019-09-24 17:28:06'),(45,1,'2019-09-13 13:33:45','13313131','2f221f','2019-09-20 17:33:45'),(46,44,'2019-09-13 13:40:33','drive','go home and drive','2019-09-25 18:40:33');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'a','b','a','1','a','a@a.com','Female'),(2,'Dorel','Cornel','dorelcornel12','123456','Taiwan','dorelcornel@yahoo.com','Female'),(3,'Patrunjel','Marar','verdeata','1212121','Patagonia','verdeata@mail.com','Male'),(4,'john','doe','johndoe','1234','Nowhere','johndoe@mail.com','Male'),(8,'TheOneAndOnly','Only','one','1234','FarAway','one@only.com','Male'),(34,'we','de','we','dede','www','ddd','Female'),(43,'who','the','who','121212','dede','dedede','Male'),(44,'ov','ov','ov','1234','a','ov@ov.com','Male');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13 11:57:00
