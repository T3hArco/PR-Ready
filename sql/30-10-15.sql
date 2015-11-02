# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.38)
# Database: swp2
# Generation Time: 2015-10-30 09:42:52 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Enrollments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Enrollments`;

CREATE TABLE `Enrollments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table Quizzes
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Quizzes`;

CREATE TABLE `Quizzes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `logo` varchar(35) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Quizzes` WRITE;
/*!40000 ALTER TABLE `Quizzes` DISABLE KEYS */;

INSERT INTO `Quizzes` (`id`, `name`, `logo`, `description`)
VALUES
	(1,'Test','Test',NULL),
	(2,'Test','Test','Dit is een test quiz!'),
	(3,'Test','Test','Dit is een test quiz!'),
	(4,'Test','Test','Dit is een test quiz!'),
	(5,'Test','Test','Dit is een test quiz!'),
	(6,'Test','Test','Dit is een test quiz!'),
	(7,'Test','Test','Dit is een test quiz!'),
	(8,'Test','Test','Dit is een test quiz!'),
	(9,'Test','Test','Dit is een test quiz!'),
	(10,'Test','Test','Dit is een test quiz!'),
	(11,'Test','Test','Dit is een test quiz!'),
	(12,'Test','Test','Dit is een test quiz!'),
	(13,'Test','Test','Dit is een test quiz!'),
	(14,'Test','Test','Dit is een test quiz!'),
	(15,'Test','Test','Dit is een test quiz!'),
	(16,'Test','Test','Dit is een test quiz!'),
	(17,'Test','Test','Dit is een test quiz!'),
	(18,'Test','Test','Dit is een test quiz!'),
	(19,'Test','Test','Dit is een test quiz!'),
	(20,'Test','Test','Dit is een test quiz!'),
	(21,'Test','Test','Dit is een test quiz!'),
	(22,'Test','Test','Dit is een test quiz!'),
	(23,'Test','Test','Dit is een test quiz!'),
	(24,'Test','Test','Dit is een test quiz!'),
	(25,'Test','Test','Dit is een test quiz!'),
	(26,'Test','Test','Dit is een test quiz!'),
	(27,'Test','Test','Dit is een test quiz!'),
	(28,'Test','Test','Dit is een test quiz!'),
	(29,'Test','Test','Dit is een test quiz!'),
	(30,'Test','Test','Dit is een test quiz!'),
	(31,'Test','Test','Dit is een test quiz!'),
	(32,'Test','Test','Dit is een test quiz!'),
	(33,'Test','Test','Dit is een test quiz!'),
	(34,'Test','Test','Dit is een test quiz!'),
	(35,'Test','Test','Dit is een test quiz!'),
	(36,'Test','Test','Dit is een test quiz!'),
	(37,'Test','Test','Dit is een test quiz!'),
	(38,'Test','Test','Dit is een test quiz!'),
	(39,'Test','Test','Dit is een test quiz!'),
	(40,'Test','Test','Dit is een test quiz!'),
	(41,'Test','Test','Dit is een test quiz!'),
	(42,'Test','Test','Dit is een test quiz!'),
	(43,'Test','Test','Dit is een test quiz!'),
	(44,'Test','Test','Dit is een test quiz!'),
	(45,'Test','Test','Dit is een test quiz!'),
	(46,'Test','Test','Dit is een test quiz!'),
	(47,'Test','Test','Dit is een test quiz!'),
	(48,'Test','Test','Dit is een test quiz!'),
	(49,'Test','Test','Dit is een test quiz!'),
	(50,'Test','Test','Dit is een test quiz!'),
	(51,'Test','Test','Dit is een test quiz!'),
	(52,'Test','Test','Dit is een test quiz!'),
	(53,'Test','Test','Dit is een test quiz!'),
	(54,'Test','Test','Dit is een test quiz!');

/*!40000 ALTER TABLE `Quizzes` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Sessions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Sessions`;

CREATE TABLE `Sessions` (
  `id` int(11) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `token` varchar(32) DEFAULT NULL,
  `expires` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_token` (`token`),
  KEY `fk_userid` (`userid`),
  CONSTRAINT `fk_userid` FOREIGN KEY (`userid`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table Users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `role` enum('USER','ADMINISTRATOR') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `name_2` (`username`),
  UNIQUE KEY `name_3` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;

INSERT INTO `Users` (`id`, `username`, `password`, `role`)
VALUES
	(1,'test','098f6bcd4621d373cade4e832627b4f6','USER'),
	(6,'a','0cc175b9c0f1b6a831c399e269772661','USER'),
	(7,'arco','0cb157999ac9aacd61ad0e5c7cd0f51d','USER'),
	(8,'\'','3590cb8af0bbb9e78c343b52b93773c9','USER'),
	(9,'\"','b15835f133ff2e27c7cb28117bfae8f4','USER'),
	(11,'','d41d8cd98f00b204e9800998ecf8427e','USER'),
	(12,'christophe','098f6bcd4621d373cade4e832627b4f6','USER');

/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
