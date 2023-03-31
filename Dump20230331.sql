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
  `in_stock` int(11) DEFAULT NULL,
  `low_stock` int(11) DEFAULT NULL,
  `average_cost` double DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `discountable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('102-82-7739','Ranchero - Primerba, Paste',50,10,884,1,1),('106-11-1143','Snapple Lemon Tea',42,10,860,1,1),('111-28-1220','Bacon Strip Precooked',51,10,627,1,1),('123-456-787','LUCKY ME BEEF NOODLES',120,10,25,5,1),('123-456-788','SPAM 100G',120,10,120,3,1),('123-456-789','SAN MARINO CORNED TUNA 50G',100,10,40,3,0),('124-94-6358','Soda Water - Club Soda, 355 Ml',5,10,112,1,0),('134-29-3195','Wine La Vielle Ferme Cote Du',0,10,631,1,1),('137-37-6520','Coffee - Hazelnut Cream',25,10,850,1,1),('147-62-0264','Schnappes Peppermint - Walker',33,10,711,1,1),('168-28-9710','Squash - Pattypan, Yellow',79,10,821,1,1),('179-48-1458','Napkin White - Starched',10,10,630,1,1),('181-67-4147','Bread - Corn Muffaleta Onion',93,10,647,1,1),('195-76-1411','Island Oasis - Mango Daiquiri',91,10,138,NULL,NULL),('196-90-4014','Wine - Magnotta - Cab Sauv',16,10,294,NULL,NULL),('205-89-4419','Nut - Peanut, Roasted',35,10,673,NULL,NULL),('210-87-4761','Mushroom - Crimini',75,10,805,NULL,NULL),('218-20-6662','Sauce - Marinara',13,10,742,NULL,NULL),('228-72-8231','Lemonade - Mandarin, 591 Ml',22,10,115,NULL,NULL),('233-51-4068','Mousse - Passion Fruit',5,10,486,NULL,NULL),('248-70-0024','Bread - Bistro Sour',85,10,102,NULL,NULL),('255-78-9171','Brandy - Bar',93,10,815,NULL,NULL),('260-25-8385','Pork - Ham Hocks - Smoked',8,10,468,NULL,NULL),('274-12-4450','Pepper - Cubanelle',51,10,29,NULL,NULL),('282-09-8092','Cabbage - Red',55,10,554,NULL,NULL),('286-38-4143','Wine - Puligny Montrachet A.',80,10,938,NULL,NULL),('291-89-5704','Lumpfish Black',48,10,634,NULL,NULL),('298-19-8774','Lamb - Whole Head Off,nz',50,10,258,NULL,NULL),('301-19-7039','Pike - Frozen Fillet',49,10,397,NULL,NULL),('311-97-9083','Soup Campbells Split Pea And Ham',73,10,526,NULL,NULL),('332-19-1446','Smirnoff Green Apple Twist',21,10,136,NULL,NULL),('332-46-2010','Sesame Seed Black',44,10,650,NULL,NULL),('335-54-5954','Wine - Chardonnay South',77,10,166,NULL,NULL),('342-73-3846','Garam Masala Powder',83,10,338,NULL,NULL),('345-51-4065','Lid Coffeecup 12oz D9542b',34,10,824,NULL,NULL),('389-68-1800','Potatoes - Idaho 80 Count',88,10,299,NULL,NULL),('396-80-9147','Buffalo - Short Rib Fresh',96,10,771,NULL,NULL),('396-93-3828','Sandwich Wrap',31,10,979,NULL,NULL),('398-67-6383','Beef - Montreal Smoked Brisket',69,10,336,NULL,NULL),('398-86-0905','Pork Casing',47,10,342,NULL,NULL),('403-96-1268','Bread - Rosemary Focaccia',20,10,532,NULL,NULL),('416-46-6681','Extract - Raspberry',62,10,501,NULL,NULL),('416-93-2577','Soup - Campbells Pasta Fagioli',38,10,171,NULL,NULL),('417-75-0187','Crab - Meat',98,10,600,NULL,NULL),('425-40-8765','Wine - Marlbourough Sauv Blanc',100,10,966,NULL,NULL),('434-67-6664','Pastry - Key Limepoppy Seed Tea',90,10,300,NULL,NULL),('440-55-1119','Gelatine Leaves - Envelopes',9,10,983,NULL,NULL),('442-07-5610','Soup - Campbells Mac N Cheese',29,10,563,NULL,NULL),('443-87-1804','Tea - Orange Pekoe',82,10,64,NULL,NULL),('454-55-1899','Pepper - Pablano',57,10,393,NULL,NULL),('458-07-5008','Tomatoes - Orange',68,10,644,NULL,NULL),('459-31-3191','Carrots - Purple, Organic',52,10,792,NULL,NULL),('463-11-2904','Olives - Kalamata',39,10,181,NULL,NULL),('4801668500224','DATU PUTI 1LITER',99,10,45,12,1),('481-37-6109','Puree - Raspberry',58,10,370,NULL,NULL),('493-98-1416','Beans - Fine',14,10,287,NULL,NULL),('502-44-8906','Beer - Creemore',23,10,688,NULL,NULL),('510-51-9589','Smirnoff Green Apple Twist',95,10,96,NULL,NULL),('513-84-6130','Veal - Round, Eye Of',32,10,722,NULL,NULL),('537-98-9604','Milk - 2% 250 Ml',72,10,223,NULL,NULL),('544-53-6322','Bay Leaf',70,10,798,NULL,NULL),('555SARD','555 Sardines',30,10,30,NULL,NULL),('569-92-7949','Rabbit - Whole',56,10,473,NULL,NULL),('585-37-2747','Edible Flower - Mixed',19,10,492,NULL,NULL),('586-09-0215','Grapes - Green',46,10,334,NULL,NULL),('602-92-4895','Bread - Bistro Sour',79,10,202,NULL,NULL),('603-42-8025','Grapes - Black',6,10,71,NULL,NULL),('603-44-9457','Wine - Tribal Sauvignon',60,10,890,NULL,NULL),('605-91-1723','Hinge W Undercut',30,10,489,NULL,NULL),('624-22-5618','Oil - Truffle, Black',74,10,370,NULL,NULL),('626-38-8258','Salmon - Atlantic, Skin On',43,10,830,NULL,NULL),('648-34-9693','Chicken - Whole Roasting',27,10,179,NULL,NULL),('668-74-9004','Ocean Spray - Kiwi Strawberry',28,10,646,NULL,NULL),('674-33-2275','Gelatine Leaves - Bulk',25,10,651,NULL,NULL),('694-86-5190','Cheese - Bocconcini',64,10,396,NULL,NULL),('704-73-5957','Cake - Lemon Chiffon',37,10,296,NULL,NULL),('705-05-5111','Graham Cracker Mix',10,10,327,NULL,NULL),('706-15-0452','Tea - Mint',45,10,137,NULL,NULL),('719-78-6135','Long Island Ice Tea',53,10,203,NULL,NULL),('722-47-6776','Cheese - Montery Jack',89,10,983,NULL,NULL),('723-05-9908','Pork - Sausage Casing',65,10,176,NULL,NULL),('745-83-4113','Lambcasing',59,10,61,NULL,NULL),('748485800332','ARGENTINA LIVER SPREAD 85G',10,10,25,3,1),('765-61-6539','Veal - Leg',24,10,133,NULL,NULL),('773-21-5323','Syrup - Monin - Blue Curacao',61,10,981,NULL,NULL),('791-26-5373','Beef - Chuck, Boneless',12,10,687,NULL,NULL),('807-97-5779','Zucchini - Green',18,10,170,NULL,NULL),('810-90-6035','Bread - Rye',54,10,28,NULL,NULL),('814-29-3465','Ezy Change Mophandle',67,10,219,NULL,NULL),('831-30-9166','Bagel - Everything Presliced',4,10,454,NULL,NULL),('847-77-3223','Tea Peppermint',92,10,264,NULL,NULL),('853-65-5804','Wine - German Riesling',66,10,727,NULL,NULL),('858-44-0261','Stainless Steel Cleaner Vision',81,10,144,NULL,NULL),('865-61-2648','Kumquat',97,10,54,NULL,NULL),('867-45-9264','Pork - Back, Long Cut, Boneless',71,10,357,NULL,NULL),('867-46-8759','Tea - Jasmin Green',36,10,915,NULL,NULL),('867-85-5543','The Pop Shoppe - Cream Soda',40,10,661,NULL,NULL),('869-02-5724','Cheese Cloth No 100',15,10,501,NULL,NULL),('876-43-2556','Sauce - Chili',26,10,604,NULL,NULL),('883-57-1253','Wine - Barbera Alba Doc 2001',84,10,632,NULL,NULL),('884-49-5280','Cheese - St. Andre',78,10,421,NULL,NULL),('886-63-9780','Halibut - Whole, Fresh',94,10,220,NULL,NULL),('891-73-4493','Brandy - Bar',11,10,587,NULL,NULL),('ARCBEEF1','Argentina Corned Beef',20,10,35,NULL,NULL),('CTUNA1','Century Tuna',0,10,50,NULL,NULL);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,'2311900081881069','123-456-789',40,100,4000,'2023-03-29 00:00:00'),(2,'7754964624884382','123-456-788',120,120,14400,'2023-03-30 00:00:00'),(3,'1647399062031014','123-456-787',25,120,3000,'2023-03-28 00:00:00'),(4,'1305687192103924','748485800332',25,100,2500,'2023-03-31 00:00:00'),(5,'1612650859697276','4801668500224',45,99,4455,'2023-03-31 00:00:00');
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
INSERT INTO `purchase_transaction` VALUES ('1305687192103924',2500,100,'MARCO','BAYAN','2023-03-31 10:00:25'),('1612650859697276',4455,99,'MARCO','NOVALICHES BAYAN','2023-03-31 11:49:43'),('1647399062031014',3000,120,'SM FAIRVIEW','SM','2023-03-30 14:27:31'),('2311900081881069',4000,100,'MARCO2','NOVA BAYAN2','2023-03-31 11:53:34'),('7754964624884382',14400,120,'PUREGOLD','NOVA BAYAN','2023-03-30 14:25:36');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'9045560964519462','Ranchero - Primerba, Paste',NULL,1,884),(2,'9045560964519462','Bacon Strip Precooked',NULL,2,1254),(3,'7140760830066861','Snapple Lemon Tea',0,2,1720),(4,'7140760830066861','Ranchero - Primerba, Paste',0,1,884),(5,'3173434914259750','Snapple Lemon Tea',860,2,1720),(6,'3173434914259750','Schnappes Peppermint - Walker',711,1,711),(7,'3173434914259750','Ranchero - Primerba, Paste',884,1,884),(8,'6403380536057313','Coffee - Hazelnut Cream',850,1,850),(9,'6403380536057313','Ranchero - Primerba, Paste',884,1,884),(10,'9561391577059486','Ranchero - Primerba, Paste',884,1,884),(11,'4177925586139009','Coffee - Hazelnut Cream',850,1,850),(12,'4177925586139009','Ranchero - Primerba, Paste',884,1,884),(13,'4177925586139009','Squash - Pattypan, Yellow',821,1,821),(14,'4177925586139009','Napkin White - Starched',630,1,630),(15,'4177925586139009','Wine La Vielle Ferme Cote Du',631,2,1262),(16,'4177925586139009','Bacon Strip Precooked',627,1,627),(17,'3617992756978719','Ranchero - Primerba, Paste',884,1,884),(18,'3617992756978719','Napkin White - Starched',630,1,630),(19,'3617992756978719','Schnappes Peppermint - Walker',711,1,711),(20,'3617992756978719','Coffee - Hazelnut Cream',850,1,850),(21,'3617992756978719','Snapple Lemon Tea',860,1,860),(22,'3617992756978719','Wine La Vielle Ferme Cote Du',631,3,1893),(23,'3617992756978719','Squash - Pattypan, Yellow',821,1,821),(24,'3617992756978719','Soda Water - Club Soda, 355 Ml',112,2,224),(25,'3617992756978719','Bread - Corn Muffaleta Onion',647,1,647),(26,'3617992756978719','Bacon Strip Precooked',627,1,627),(27,'4281630145733005','Ranchero - Primerba, Paste',884,1,884),(28,'4281630145733005','Wine La Vielle Ferme Cote Du',631,1,631),(29,'4281630145733005','Napkin White - Starched',630,1,630),(30,'4281630145733005','Schnappes Peppermint - Walker',711,1,711),(31,'4281630145733005','Coffee - Hazelnut Cream',850,1,850),(32,'4281630145733005','Snapple Lemon Tea',860,1,860),(33,'4281630145733005','Soda Water - Club Soda, 355 Ml',112,1,112),(34,'4281630145733005','Squash - Pattypan, Yellow',821,1,821),(35,'4281630145733005','Bread - Corn Muffaleta Onion',647,1,647),(36,'4281630145733005','Bacon Strip Precooked',627,1,627),(37,'1827331292476956','Ranchero - Primerba, Paste',884,1,884),(38,'1827331292476956','Wine La Vielle Ferme Cote Du',631,1,631),(39,'1827331292476956','Napkin White - Starched',630,1,630),(40,'1827331292476956','Schnappes Peppermint - Walker',711,1,711),(41,'1827331292476956','Coffee - Hazelnut Cream',850,1,850),(42,'1827331292476956','Snapple Lemon Tea',860,1,860),(43,'1827331292476956','Soda Water - Club Soda, 355 Ml',112,1,112),(44,'1827331292476956','Squash - Pattypan, Yellow',821,1,821),(45,'1827331292476956','Bread - Corn Muffaleta Onion',647,1,647),(46,'1827331292476956','Bacon Strip Precooked',627,1,627),(47,'6065531243989340','Ranchero - Primerba, Paste',884,1,884),(48,'6065531243989340','Napkin White - Starched',630,1,630),(49,'6065531243989340','Schnappes Peppermint - Walker',711,1,711),(50,'6065531243989340','Coffee - Hazelnut Cream',850,1,850),(51,'6065531243989340','Snapple Lemon Tea',860,1,860),(52,'6065531243989340','Soda Water - Club Soda, 355 Ml',112,1,112),(53,'6065531243989340','Squash - Pattypan, Yellow',821,1,821),(54,'6065531243989340','Bread - Corn Muffaleta Onion',647,1,647),(55,'6065531243989340','Bacon Strip Precooked',627,1,627),(56,'7492280834336330','Ranchero - Primerba, Paste',884,1,884),(57,'7492280834336330','Napkin White - Starched',630,1,630),(58,'7492280834336330','Schnappes Peppermint - Walker',711,1,711),(59,'7492280834336330','Coffee - Hazelnut Cream',850,1,850),(60,'7492280834336330','Snapple Lemon Tea',860,1,860),(61,'7492280834336330','Soda Water - Club Soda, 355 Ml',112,1,112),(62,'7492280834336330','Squash - Pattypan, Yellow',821,1,821),(63,'7492280834336330','Bread - Corn Muffaleta Onion',647,1,647),(64,'7492280834336330','Bacon Strip Precooked',627,1,627),(65,'1477720514587641','Ranchero - Primerba, Paste',884,1,884),(66,'1477720514587641','Napkin White - Starched',630,1,630),(67,'1477720514587641','Schnappes Peppermint - Walker',711,1,711),(68,'1477720514587641','Coffee - Hazelnut Cream',850,1,850),(69,'1477720514587641','Snapple Lemon Tea',860,1,860),(70,'1477720514587641','Soda Water - Club Soda, 355 Ml',112,1,112),(71,'1477720514587641','Squash - Pattypan, Yellow',821,1,821),(72,'1477720514587641','Bread - Corn Muffaleta Onion',647,1,647),(73,'1477720514587641','Bacon Strip Precooked',627,1,627),(74,'9416296896968543','Soda Water - Club Soda, 355 Ml',112,22,2464),(75,'4892712066414754','Soda Water - Club Soda, 355 Ml',112,5,560),(76,'3050173871821535','Snapple Lemon Tea',860,1,860),(77,'5728411992719249','Snapple Lemon Tea',860,2,1720),(78,'9653237553162904','ARGENTINA LIVER SPREAD 85G',25,90,2250),(79,'6412594341147219','Ranchero - Primerba, Paste',884,11,9724),(80,'6679542695956897','DATU PUTI 1LITER',40,1,40);
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
INSERT INTO `sales_transaction` VALUES ('1477720514587641',6142,9,6200,58,0,'2023-03-25 15:33:26'),('1827331292476956',6773,10,7000,227,0,'2023-03-25 15:06:55'),('3050173871821535',860,1,1000,140,0,'2023-03-26 19:38:06'),('3173434914259750',3315,3,3500,185,0,'2023-03-24 14:44:34'),('3617992756978719',8147,10,9000,853,0,'2023-03-25 14:51:09'),('4177925586139009',5074,6,6000,926,0,'2023-03-25 14:36:34'),('4281630145733005',6773,10,7000,227,0,'2023-03-25 15:01:48'),('4892712066414754',560,1,3100,76,0,'2023-03-26 19:34:09'),('5728411992719249',1720,1,1800,80,0,'2023-03-26 19:38:44'),('6065531243989340',6142,9,6200,58,0,'2023-03-25 15:17:59'),('6403380536057313',1734,2,1800,66,0,'2023-03-25 13:49:01'),('6412594341147219',9724,1,9800,76,0,'2023-03-31 10:23:46'),('6679542695956897',40,1,50,10,0,'2023-03-31 11:36:27'),('7140760830066861',2604,2,2700,96,0,'2023-03-24 14:36:05'),('7492280834336330',6142,9,6200,58,0,'2023-03-25 15:27:10'),('9045560964519462',2138,2,2200,62,0,'2023-03-24 13:43:19'),('9416296896968543',2464,1,2500,36,0,'2023-03-26 19:32:35'),('9561391577059486',884,1,1000,116,0,'2023-03-25 14:15:05'),('9653237553162904',2250,1,2500,250,0,'2023-03-31 10:02:57');
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

-- Dump completed on 2023-03-31 13:24:31
