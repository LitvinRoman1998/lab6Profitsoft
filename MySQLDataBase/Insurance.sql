-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: insurance
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `patronimic` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `client_type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Adress` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `contract` (
  `contract_id` int(11) NOT NULL AUTO_INCREMENT,
  `conclusion_date` date NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `fk_client_id` int(11) NOT NULL,
  PRIMARY KEY (`contract_id`),
  KEY `fk_contract_client_idx` (`fk_client_id`),
  CONSTRAINT `fk_contract_client` FOREIGN KEY (`fk_client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_has_insuredperson`
--

DROP TABLE IF EXISTS `contract_has_insuredperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `contract_has_insuredperson` (
  `contract_num` int(11) NOT NULL,
  `insuredperson_num` int(11) NOT NULL,
  PRIMARY KEY (`contract_num`,`insuredperson_num`),
  KEY `fk_contract_has_insuredperson_insuredperson1_idx` (`insuredperson_num`),
  CONSTRAINT `fk_contract_has_insuredperson_contract1` FOREIGN KEY (`contract_num`) REFERENCES `contract` (`contract_id`),
  CONSTRAINT `fk_contract_has_insuredperson_insuredperson1` FOREIGN KEY (`insuredperson_num`) REFERENCES `insuredperson` (`insuredperson_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_has_insuredperson`
--

LOCK TABLES `contract_has_insuredperson` WRITE;
/*!40000 ALTER TABLE `contract_has_insuredperson` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_has_insuredperson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insuredperson`
--

DROP TABLE IF EXISTS `insuredperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `insuredperson` (
  `InsuredPerson_id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `patronimic` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthDay` date NOT NULL,
  `Insurance_Price` int(11) NOT NULL,
  `Tax_Identification_Number` int(11) unsigned NOT NULL,
  PRIMARY KEY (`InsuredPerson_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insuredperson`
--

LOCK TABLES `insuredperson` WRITE;
/*!40000 ALTER TABLE `insuredperson` DISABLE KEYS */;
/*!40000 ALTER TABLE `insuredperson` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-03 22:52:20
