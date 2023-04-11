-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: pos
-- ------------------------------------------------------
-- Server version	5.7.35-log

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'BEVERAGES'),(2,'BREAD'),(3,'CANNED GOODS'),(4,'DAIRT'),(5,'DRY/BAKING GOODS'),(6,'FROZEN FOODS'),(7,'MEAT'),(8,'FRUITS/VEGETABLES'),(9,'CLEANERS'),(10,'PAPER GOODS'),(11,'PERSONAL CARE'),(12,'OTHER');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `item_code` varchar(45) NOT NULL,
  `item_name` varchar(45) DEFAULT NULL,
  `in_stock` int(11) DEFAULT '0',
  `low_stock` int(11) DEFAULT '0',
  `original_price` double DEFAULT '0',
  `average_cost` double DEFAULT '0',
  `category_id` int(11) DEFAULT '0',
  `discountable` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('111111','SAMPLE UPDATE ITEM',100,10,30,33,3,1),('1234567871234567','ARGENTINA CORNED BEEF 35G',100,10,40,45,3,1),('1234567881234567','COKE IN CAN 8ML',90,10,15,20,1,1),('1234567891234567','SAN MARINO CORNED TUNA 50G',100,10,43,45,3,1),('2345939484359','NISSIN CUP NOODLES BEEF 20G',100,10,18,20,5,1),('3324336894326345','DATU PUTI SOY SAUCE 100ML',25,10,40,45,3,1),('3567923456781245','SURF FABCON 25GRAMS',48,10,23,25,11,1),('4392520050292342','NAGARAYA',23,5,30,35,12,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_expiration`
--

DROP TABLE IF EXISTS `item_expiration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_expiration` (
  `expiration_tag` varchar(10) NOT NULL,
  `date_of_expiration` datetime NOT NULL,
  `item_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`expiration_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_expiration`
--

LOCK TABLES `item_expiration` WRITE;
/*!40000 ALTER TABLE `item_expiration` DISABLE KEYS */;
INSERT INTO `item_expiration` VALUES ('0100','2023-04-08 18:57:48',1),('0101','2023-04-08 18:57:48',2),('0102','2023-04-08 18:57:48',3),('0103','2023-04-08 18:57:48',4),('0104','2023-04-08 18:57:48',1),('0105','2023-04-08 18:57:48',1),('0106','2023-04-08 18:57:48',4),('0107','2023-04-08 18:57:48',3),('0108','2023-04-08 18:57:48',2),('0109','2023-04-08 18:57:48',1),('0110','2023-04-08 18:57:48',1),('0111','2023-04-08 18:57:48',9),('0112','2023-04-08 18:57:48',8),('0113','2023-04-08 18:57:48',7),('0114','2023-04-08 18:57:48',6),('0115','2023-04-08 18:57:48',5),('0116','2023-04-08 18:57:48',4),('0117','2023-04-08 18:57:48',3),('0118','2023-04-08 18:57:48',2),('0119','2023-04-08 18:57:48',1),('0120','2023-04-08 18:57:48',1),('0121','2023-04-08 18:57:48',8),('0122','2023-04-08 18:57:48',9),('0123','2023-04-08 18:57:48',7),('0124','2023-04-08 18:57:48',6),('0125','2023-04-08 18:57:48',5),('0126','2023-04-08 18:57:48',4),('0127','2023-04-08 18:57:48',3),('0128','2023-04-08 18:57:48',2),('0129','2023-04-08 18:57:48',1),('0130','2023-04-08 18:57:48',1),('0131','2023-04-08 18:57:48',9),('0132','2023-04-08 18:57:48',8),('0133','2023-04-08 18:57:48',7),('0134','2023-04-08 18:57:48',6),('0135','2023-04-08 18:57:48',5),('0136','2023-04-08 18:57:48',4),('0137','2023-04-08 18:57:48',3),('0138','2023-04-08 18:57:48',2),('0139','2023-04-08 18:57:48',1),('0140','2023-04-08 18:57:48',1),('0141','2023-04-08 18:57:48',9),('0142','2023-04-08 18:57:48',8),('0143','2023-04-08 18:57:48',7),('0144','2023-04-08 18:57:48',6),('0145','2023-04-08 18:57:48',5),('0146','2023-04-08 18:57:48',4),('0147','2023-04-08 18:57:48',3),('0148','2023-04-08 18:57:48',2),('0149','2023-04-08 18:57:48',1),('0150','2023-04-08 18:57:48',1),('0151','2023-04-08 18:57:48',9),('0152','2023-04-08 18:57:48',8),('0153','2023-04-08 18:57:48',7),('0154','2023-04-08 18:57:48',6),('0155','2023-04-08 18:57:48',5),('0156','2023-04-08 18:57:48',4),('0157','2023-04-08 18:57:48',3),('0158','2023-04-08 18:57:48',2),('0159','2023-04-08 18:57:48',1),('0160','2023-04-08 18:57:48',1),('0161','2023-04-08 18:57:48',9),('0162','2023-04-08 18:57:48',8),('0163','2023-04-08 18:57:48',7),('0164','2023-04-08 18:57:48',6),('0165','2023-04-08 18:57:48',5),('0166','2023-04-08 18:57:48',4),('0167','2023-04-08 18:57:48',3),('0168','2023-04-08 18:57:48',2),('0169','2023-04-08 18:57:48',1),('0170','2023-04-08 18:57:48',1),('0171','2023-04-08 18:57:48',9),('0172','2023-04-08 18:57:48',8),('0173','2023-04-08 18:57:48',7),('0174','2023-04-08 18:57:48',6),('0175','2023-04-08 18:57:48',5),('0176','2023-04-08 18:57:48',4),('0177','2023-04-08 18:57:48',3),('0178','2023-04-08 18:57:48',2),('0179','2023-04-08 18:57:48',1),('0180','2023-04-08 18:57:48',1),('0181','2023-04-08 18:57:48',9),('0182','2023-04-08 18:57:48',8),('0183','2023-04-08 18:57:48',7),('0184','2023-04-08 18:57:48',6),('0185','2023-04-08 18:57:48',5),('0186','2023-04-08 18:57:48',4),('0187','2023-04-08 18:57:48',3),('0188','2023-04-08 18:57:48',2),('0189','2023-04-08 18:57:48',1),('0190','2023-04-08 18:57:48',1),('0191','2023-04-08 18:57:48',9),('0192','2023-04-08 18:57:48',0),('0193','2023-04-08 18:57:48',7),('0194','2023-04-08 18:57:48',6),('0195','2023-04-08 18:57:48',5),('0196','2023-04-08 18:57:48',4),('0197','2023-04-08 18:57:48',3),('0198','2023-04-08 18:57:48',2),('0199','2023-04-08 18:57:48',1),('0200','2023-04-08 18:57:48',1);
/*!40000 ALTER TABLE `item_expiration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_number` varchar(45) DEFAULT NULL,
  `item_code` varchar(45) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date_bought_from_supplier` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,'869613890','123456789',45,100,4500,'2022-04-29 00:00:00'),(2,'546870437','123456788',20,100,2000,'2023-04-01 00:00:00'),(3,'986632570','123456787',45,100,4500,'2023-04-01 00:00:00'),(4,'942320261','3324336894326345',45,100,4500,'2023-04-05 00:00:00'),(5,'164678596','4392520050292342',35,23,805,'2023-04-05 00:00:00'),(6,'117589974','3567923456781245',25,48,1200,'2023-04-08 00:00:00'),(7,'150890242','2345939484359',20,100,2000,'2023-04-08 00:00:00'),(8,'289739588','111111',33,100,3300,'2023-04-19 00:00:00');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_transaction`
--

DROP TABLE IF EXISTS `purchase_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_transaction` (
  `transaction_number` varchar(17) NOT NULL,
  `grand_total` double DEFAULT NULL,
  `total_items` int(11) DEFAULT NULL,
  `supplier` varchar(45) DEFAULT NULL,
  `supplier_location` varchar(45) DEFAULT NULL,
  `date_transacted` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_transaction`
--

LOCK TABLES `purchase_transaction` WRITE;
/*!40000 ALTER TABLE `purchase_transaction` DISABLE KEYS */;
INSERT INTO `purchase_transaction` VALUES ('117589974',1200,48,'MARCO JIMMY','NOVA BAYAN','2023-04-08 18:54:52'),('150890242',2000,100,'MARCO JIMMY','BAYAN','2023-04-08 18:57:48'),('164678596',805,23,'MARCO','BAYAN','2023-04-05 22:09:43'),('289739588',3300,100,'MARCO2','BAYAN2','2023-04-08 19:11:32'),('546870437',2000,100,'MARCO','NOVALICHES BAYAN','2023-04-01 10:47:20'),('869613890',4500,100,'MARCO','NOVALICHES BAYAN','2023-04-01 10:20:16'),('942320261',4500,100,'MARCO','NOVA BAYAN','2023-04-05 14:03:26'),('986632570',4500,100,'MARCO','NOVALICHES BAYAN','2023-04-01 10:48:32');
/*!40000 ALTER TABLE `purchase_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_number` varchar(45) DEFAULT NULL,
  `item_name` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date_transacted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'880736605','ARGENTINA CORNED BEEF 35G',45,5,225,'2023-04-05 13:35:47');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_transaction`
--

DROP TABLE IF EXISTS `sales_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_transaction` (
  `transaction_number` varchar(45) NOT NULL,
  `grand_total` double DEFAULT NULL,
  `total_items` int(11) DEFAULT NULL,
  `amount_paid` double DEFAULT NULL,
  `money_change` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `date_transacted` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_transaction`
--

LOCK TABLES `sales_transaction` WRITE;
/*!40000 ALTER TABLE `sales_transaction` DISABLE KEYS */;
INSERT INTO `sales_transaction` VALUES ('880736605',225,1,500,275,0,'2023-04-05 13:35:47');
/*!40000 ALTER TABLE `sales_transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-11  8:06:07
