-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: quiz
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `questionid` int NOT NULL AUTO_INCREMENT,
  `questionstring` text NOT NULL,
  PRIMARY KEY (`questionid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (6,'Which of the following is NOT a predefined stream?'),(8,'A variable whose value cannot be changed is declared:'),(9,'Binding of data members and functions in a class is called _______'),(10,'The mothod that gives the text representation of an object is _____'),(12,'The static data members of a class are initialized using _______'),(13,'To check if two objects have same content, we use _______'),(14,'Which of the following statement is false?'),(15,'Java resolves calls to overridden methods during runtime. This is called:'),(16,'An interface that has just one abstract method is called _____ interface'),(17,'Which of the following is a marker interface?');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionoption`
--

DROP TABLE IF EXISTS `questionoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionoption` (
  `optionid` int NOT NULL AUTO_INCREMENT,
  `optionname` varchar(100) NOT NULL,
  `questionid` int NOT NULL,
  `isanswer` set('yes','no') NOT NULL,
  PRIMARY KEY (`optionid`),
  KEY `questionid` (`questionid`),
  CONSTRAINT `questionoption_ibfk_1` FOREIGN KEY (`questionid`) REFERENCES `question` (`questionid`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionoption`
--

LOCK TABLES `questionoption` WRITE;
/*!40000 ALTER TABLE `questionoption` DISABLE KEYS */;
INSERT INTO `questionoption` VALUES (9,'System.in',6,'no'),(10,'System.error',6,'no'),(11,'System.out',6,'no'),(12,'System.err',6,'yes'),(17,'static',8,'no'),(18,'public',8,'no'),(19,'final',8,'yes'),(20,'void',8,'no'),(21,'Encapsulation',9,'yes'),(22,'Abstraction',9,'no'),(23,'Inheritance',9,'no'),(24,'Polymorphism',9,'no'),(25,'toString',10,'yes'),(26,'hashCode',10,'no'),(27,'equals',10,'no'),(28,'clone',10,'no'),(33,'constructor',12,'no'),(34,'static method',12,'no'),(35,'static block',12,'yes'),(36,'static fields',12,'no'),(37,'=',13,'no'),(38,'==',13,'no'),(39,'equals()',13,'yes'),(40,'!=',13,'no'),(41,'An abstract class can not be instantiated',14,'no'),(42,'An abstract class cannot be extended',14,'yes'),(43,'A final class can not be subclassed',14,'no'),(44,'A final method cannot be overridden',14,'no'),(45,'static binding',15,'no'),(46,'dynamic binding',15,'yes'),(47,'method calling',15,'no'),(48,'method resolving',15,'no'),(49,'functional',16,'yes'),(50,'empty',16,'no'),(51,'final',16,'no'),(52,'marker',16,'no'),(53,'Comparable',17,'no'),(54,'ActionListener',17,'no'),(55,'Cloneable',17,'yes'),(56,'MouseListener',17,'no');
/*!40000 ALTER TABLE `questionoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizresult`
--

DROP TABLE IF EXISTS `quizresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizresult` (
  `quizid` int NOT NULL AUTO_INCREMENT,
  `studentid` int NOT NULL,
  `score` int NOT NULL,
  `quizdate` date NOT NULL,
  PRIMARY KEY (`quizid`),
  KEY `studentid` (`studentid`),
  CONSTRAINT `quizresult_ibfk_1` FOREIGN KEY (`studentid`) REFERENCES `student` (`studentid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizresult`
--

LOCK TABLES `quizresult` WRITE;
/*!40000 ALTER TABLE `quizresult` DISABLE KEYS */;
INSERT INTO `quizresult` VALUES (1,1,9,'2023-09-04'),(2,2,3,'2023-09-06'),(3,3,7,'2023-09-06'),(4,4,7,'2023-09-06'),(5,5,10,'2023-09-06');
/*!40000 ALTER TABLE `quizresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizuser`
--

DROP TABLE IF EXISTS `quizuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizuser` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(12) NOT NULL,
  `usertype` set('admin','student') NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizuser`
--

LOCK TABLES `quizuser` WRITE;
/*!40000 ALTER TABLE `quizuser` DISABLE KEYS */;
INSERT INTO `quizuser` VALUES (1,'nayans','nayan21#','admin'),(2,'stud','stud123#','student'),(4,'ajays','123#','student'),(5,'monicat','moni12','student'),(6,'suls','sul74','student'),(7,'kunald','kunal','student'),(8,'akashp','akash1#','student');
/*!40000 ALTER TABLE `quizuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `studentid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mailid` varchar(100) NOT NULL,
  `mobilenumber` char(10) NOT NULL,
  PRIMARY KEY (`studentid`),
  KEY `userid` (`userid`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `quizuser` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,4,'Ajay','Shinde','Pune','ajayshinde123@gmail.com','8975969344'),(2,5,'Monica','Thatte','Pune','monicathatte@gmail.com','9767841735'),(3,6,'Sulochana','Sawant','Pune','sulochanasawant74@gmail.com','9767841735'),(4,7,'Kunal','Deshmukh','Mumbai','kunald@gmail.com','6374821394'),(5,8,'Akash','Patil','Pune','akashpatil567@gmail.com','2345678210');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-07  0:06:38
