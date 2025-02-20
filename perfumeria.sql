CREATE DATABASE  IF NOT EXISTS `perfumeria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `perfumeria`;
-- MySQL dump 10.13  Distrib 8.4.3, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: perfumeria
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `brand` varchar(255) NOT NULL,
  `category` tinyint NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(255) NOT NULL,
  `discount` float NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `product_chk_1` CHECK ((`category` between 0 and 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (152,'Giorgio Armani',0,'Marino refrescante','Acqua di Gio',130,'0e07d20d-ce12-4281-bca0-6668901bda0d_aqua.png',0),(202,'Dior',0,'Amaderado fresco','Sauvage',150,'c759d5d9-4ac9-4db8-ab22-67fdb4d1e4ff_dior.jpg',0),(252,'Chanel',0,'Aromático cítrico','Bleu de Chanel',160,'00d5b8cb-53e2-43f0-ab45-8e05743ebf00_bleuchanel.jpg',0),(253,'Giorgio Armani',0,'Elegante especiado','Armani Code',140,'4a861bf8-1745-494b-8edb-85b5bd27ee3d_armani.jpg',0),(254,'Antonio Banderas',0,'Fresco marino','Antonio Banderas Blue',70,'3568fff9-87c6-401e-9be4-ab1fb5115bc3_antoniobanderas.jpg',0),(255,'Pepe Jeans',0,'Dulce especiado','Pepe Jeans Cocktail Shaker',90,'3b588f5a-4ab5-4443-99bf-4091fc33351d_pepeJeans.jpg',0),(256,'Police',0,'Intenso masculino','Police To Be',80,'411c0414-743b-47fe-b351-bb993ebc0f76_police.jpg',0),(257,'Pepe Jeans',0,'Dulce seductor','Pepe Jeans London',95,'1017d981-a998-420b-b8bc-1f88bad0bc10_pepe.jpg',0),(258,'DSquared2',0,'Herbal amaderado','Potion',120,'a06e5831-1aee-478c-95ae-d5e0848e048b_potion.jpg',0),(259,'Yves Saint Laurent',0,'Elegante moderno','Y Le Parfum',150,'5e06387c-2573-4e2b-a239-e829d03728ef_ysl.jpg',0),(260,'Lancôme',1,'Floral dulce','La Vie Est Belle',120,'766e9a6e-2e93-4572-8d48-8d0287808d8e_belle.png',0),(261,'Dolce & Gabbana',1,'Cítrico afrutado','Light Blue',110,'9e7153e5-0b80-4d30-8ea1-cb0d786ff4ca_light_blue.png',0),(262,'Tom Ford',1,'Exótico lujoso','Tom Ford Black Orchid',180,'164f1c1b-5b9f-4216-b0c1-5091c8b5bf09_orchid.png',0),(263,'Carolina Herrera',1,'Sensual dulce','Good Girl',140,'0a97728c-1bd4-40f6-977a-094b1962cd56_good_girl.png',0),(264,'Chanel',1,'Clásico elegante','Chanel Nº5',170,'2a666b41-d986-477d-9b85-1b5b63c18e97_chanel.jpg',0),(265,'Chanel',1,'Fresco sofisticado','Coco Mademoiselle',160,'f7fd65d9-3c10-479e-89fe-96077a2f6a44_cocochanel.jpg',0),(266,'Lancôme',1,'Femenino radiante','Idôle',130,'b47a4f74-cad3-4c6a-ad8e-210f8918c82a_idole.png',0),(267,'Giorgio Armani',1,'Moderno floral','Si',135,'71f3c79c-fd91-4077-adff-abdd5c5a10bb_si.png',0),(268,'Dior',1,'Floral sofisticado','J\'adore',150,'a86605ca-62ff-41fd-898d-9dcd5c53a387_adore.png',0),(269,'Yves Saint Laurent',1,'Dulce misterioso','Black Opium',145,'2fca0fa9-fbe0-44e6-9919-37f194c755d2_opium.png',0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_seq`
--

DROP TABLE IF EXISTS `product_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_seq`
--

LOCK TABLES `product_seq` WRITE;
/*!40000 ALTER TABLE `product_seq` DISABLE KEYS */;
INSERT INTO `product_seq` VALUES (351);
/*!40000 ALTER TABLE `product_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,2),(152,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `birth_date` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `mobile_num` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-01-29 17:32:46.116000','emiliano.correa.amigo@gmail.com','$2a$10$u.lAfUcF4GqISK18UhMPD.4WmFG66mtwSWLTZ0dgOhn4RUovCxTEy','2025-01-29 17:32:46.116000',NULL,'','',''),(2,'2025-01-29 18:30:57.425000','evelin@gmail.com','$2a$10$wRN/Cq.wR3tGH/0ap2MA5.XC/JFjbI77rzk5PKmfUGHIyTr0ztkfe','2025-01-29 18:30:57.425000',NULL,'','',''),(152,NULL,'admin@admin.com','$2a$10$GXpAPk7hGTloImD5z8FW1e0VGf5VvniJoXmDUBqPRo9qFiqgkq49K',NULL,'2025-02-20 16:48:03.829000','eladmin','1234567890','Admin'),(153,NULL,'facu@hotmail.com','$2a$10$uh3U0zuy.MyQof6CTJAPV.IbBPCv6EBsOuj1kcCfaxa/rauAblPKS',NULL,'1995-03-10 21:00:00.000000','Reiseng','12345678','Facu');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_seq`
--

DROP TABLE IF EXISTS `users_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_seq`
--

LOCK TABLES `users_seq` WRITE;
/*!40000 ALTER TABLE `users_seq` DISABLE KEYS */;
INSERT INTO `users_seq` VALUES (251);
/*!40000 ALTER TABLE `users_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-20 17:27:51
