-- MySQL dump 10.16  Distrib 10.1.34-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: g_mission_emp
-- ------------------------------------------------------
-- Server version	10.1.34-MariaDB

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
-- Table structure for table `accompagne`
--

DROP TABLE IF EXISTS `accompagne`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accompagne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idtransport` int(11) DEFAULT NULL,
  `cin` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cin` (`cin`),
  CONSTRAINT `accompagne_ibfk_1` FOREIGN KEY (`cin`) REFERENCES `employe` (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accompagne`
--

LOCK TABLES `accompagne` WRITE;
/*!40000 ALTER TABLE `accompagne` DISABLE KEYS */;
/*!40000 ALTER TABLE `accompagne` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avion`
--

DROP TABLE IF EXISTS `avion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numbon` varchar(10) NOT NULL,
  `prix` double NOT NULL,
  `date` varchar(20) NOT NULL,
  `destination` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avion`
--

LOCK TABLES `avion` WRITE;
/*!40000 ALTER TABLE `avion` DISABLE KEYS */;
INSERT INTO `avion` VALUES (1,'1',22,'dd','des'),(2,'11',11,'da','des'),(4,'2',33,'dd','eee');
/*!40000 ALTER TABLE `avion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctm`
--

DROP TABLE IF EXISTS `ctm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numbon` varchar(10) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `destination` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctm`
--

LOCK TABLES `ctm` WRITE;
/*!40000 ALTER TABLE `ctm` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employe`
--

DROP TABLE IF EXISTS `employe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employe` (
  `cin` varchar(10) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `grade` varchar(40) DEFAULT NULL,
  `fonction` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employe`
--

LOCK TABLES `employe` WRITE;
/*!40000 ALTER TABLE `employe` DISABLE KEYS */;
INSERT INTO `employe` VALUES ('11','aa','bb','gg','ff',0),('22','e2','p2','g','f',NULL);
/*!40000 ALTER TABLE `employe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mission`
--

DROP TABLE IF EXISTS `mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mission` (
  `id` int(11) NOT NULL,
  `type` varchar(30) DEFAULT NULL,
  `data` varchar(10) DEFAULT NULL,
  `cin` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cin` (`cin`),
  CONSTRAINT `mission_ibfk_1` FOREIGN KEY (`cin`) REFERENCES `employe` (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mission`
--

LOCK TABLES `mission` WRITE;
/*!40000 ALTER TABLE `mission` DISABLE KEYS */;
INSERT INTO `mission` VALUES (0,'mis1','2019','11');
/*!40000 ALTER TABLE `mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `misson_t`
--

DROP TABLE IF EXISTS `misson_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `misson_t` (
  `cin` varchar(10) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`cin`,`id`),
  KEY `id` (`id`),
  CONSTRAINT `misson_t_ibfk_1` FOREIGN KEY (`cin`) REFERENCES `employe` (`cin`),
  CONSTRAINT `misson_t_ibfk_2` FOREIGN KEY (`id`) REFERENCES `avion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `misson_t`
--

LOCK TABLES `misson_t` WRITE;
/*!40000 ALTER TABLE `misson_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `misson_t` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numbon` varchar(10) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `destination` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train`
--

LOCK TABLES `train` WRITE;
/*!40000 ALTER TABLE `train` DISABLE KEYS */;
/*!40000 ALTER TABLE `train` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicule`
--

DROP TABLE IF EXISTS `vehicule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marque` varchar(30) DEFAULT NULL,
  `destination` varchar(40) DEFAULT NULL,
  `distance` varchar(20) DEFAULT NULL,
  `conducteur` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `conducteur` (`conducteur`),
  CONSTRAINT `vehicule_ibfk_1` FOREIGN KEY (`conducteur`) REFERENCES `employe` (`cin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicule`
--

LOCK TABLES `vehicule` WRITE;
/*!40000 ALTER TABLE `vehicule` DISABLE KEYS */;
INSERT INTO `vehicule` VALUES (1,'golf','fes','100 km','11');
/*!40000 ALTER TABLE `vehicule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-14 23:33:05
