CREATE DATABASE  IF NOT EXISTS `stock_management_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `stock_management_system`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: stock_management_system
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `deposit_withdrawal`
--

DROP TABLE IF EXISTS deposit_withdrawal;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE deposit_withdrawal (
  transaction_id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  amount decimal(10,2) NOT NULL,
  transaction_type enum('deposit','withdrawal') NOT NULL,
  `status` enum('pending','completed','cancelled') NOT NULL DEFAULT 'pending',
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (transaction_id),
  KEY user_id (user_id),
  CONSTRAINT deposit_withdrawal_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposit_withdrawal`
--

LOCK TABLES deposit_withdrawal WRITE;
/*!40000 ALTER TABLE deposit_withdrawal DISABLE KEYS */;
/*!40000 ALTER TABLE deposit_withdrawal ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_history`
--

DROP TABLE IF EXISTS price_history;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE price_history (
  price_id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  price decimal(10,2) NOT NULL,
  date_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (price_id),
  KEY stock_id (stock_id),
  CONSTRAINT price_history_ibfk_1 FOREIGN KEY (stock_id) REFERENCES stock (stock_id)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_history`
--

LOCK TABLES price_history WRITE;
/*!40000 ALTER TABLE price_history DISABLE KEYS */;
INSERT INTO price_history VALUES (1,1,137.35,'2024-04-28 14:27:43'),(2,1,135.83,'2024-04-28 14:28:33'),(3,1,141.85,'2024-04-28 14:28:34'),(4,1,136.50,'2024-04-28 14:28:35'),(5,1,141.71,'2024-04-28 14:28:36'),(6,2,2389.28,'2024-04-28 14:29:27'),(7,2,2384.05,'2024-04-28 14:29:36'),(8,3,3285.14,'2024-04-28 14:29:50'),(9,3,3288.80,'2024-04-28 14:29:51'),(10,3,3284.33,'2024-04-28 14:29:52'),(11,11,170.30,'2024-04-01 07:00:00'),(12,11,171.25,'2024-04-02 07:00:00'),(13,11,169.80,'2024-04-03 07:00:00'),(14,11,172.50,'2024-04-04 07:00:00'),(15,11,175.00,'2024-04-05 07:00:00'),(16,11,174.20,'2024-04-06 07:00:00'),(17,11,173.90,'2024-04-07 07:00:00'),(18,11,172.75,'2024-04-08 07:00:00'),(19,11,172.00,'2024-04-09 07:00:00'),(20,11,174.00,'2024-04-10 07:00:00');
/*!40000 ALTER TABLE price_history ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS stock;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE stock (
  stock_id int NOT NULL AUTO_INCREMENT,
  label varchar(50) NOT NULL,
  company_name varchar(100) NOT NULL,
  initial_price decimal(10,2) NOT NULL,
  trading_price decimal(10,2) NOT NULL,
  dividends decimal(10,2) DEFAULT NULL,
  available_stocks int NOT NULL,
  profit_percentage decimal(5,2) DEFAULT NULL,
  other_properties text,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (stock_id),
  UNIQUE KEY label (label)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES stock WRITE;
/*!40000 ALTER TABLE stock DISABLE KEYS */;
INSERT INTO stock VALUES (1,'AAPL','Apple Inc.',135.50,140.25,1.50,10000,12.50,NULL,'2024-04-28 11:17:15','2024-04-28 11:17:15'),(2,'GOOGL','Alphabet Inc.',2350.00,2385.75,2.25,15000,14.20,NULL,'2024-04-28 11:17:28','2024-04-28 11:17:28'),(3,'AMZN','Amazon.com Inc.',3250.50,3289.25,0.00,12000,9.80,NULL,'2024-04-28 11:17:45','2024-04-28 11:17:45'),(4,'MSFT','Microsoft Corporation',245.75,248.50,1.75,20000,11.00,NULL,'2024-04-28 11:17:51','2024-04-28 11:17:51'),(5,'TSLA','Tesla Inc.',700.25,715.00,0.00,8000,15.60,NULL,'2024-04-28 11:17:56','2024-04-28 11:17:56'),(6,'FB','Meta Platforms Inc.',315.75,320.25,1.00,15000,13.20,NULL,'2024-04-28 11:18:03','2024-04-28 11:18:03'),(7,'NFLX','Netflix Inc.',530.80,540.25,0.75,10000,10.80,NULL,'2024-04-28 11:18:58','2024-04-28 11:18:58'),(8,'NVDA','NVIDIA Corporation',625.40,630.75,1.50,12000,12.30,NULL,'2024-04-28 11:19:07','2024-04-28 11:19:07'),(9,'ADBE','Adobe Inc.',475.60,480.25,0.50,8000,9.50,NULL,'2024-04-28 11:19:17','2024-04-28 11:19:17'),(10,'PYPL','PayPal Holdings Inc.',265.90,270.00,0.25,15000,11.20,NULL,'2024-04-28 11:19:24','2024-04-28 11:19:24'),(11,'JNJ','Johnson & Johnson',170.30,175.00,2.00,20000,8.70,NULL,'2024-04-28 11:19:30','2024-04-28 11:19:30');
/*!40000 ALTER TABLE stock ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_order`
--

DROP TABLE IF EXISTS stock_order;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE stock_order (
  order_id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  stock_id int NOT NULL,
  order_type enum('buy','sell') NOT NULL,
  order_price decimal(10,2) NOT NULL,
  order_quantity int NOT NULL,
  order_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('pending','completed','cancelled') NOT NULL DEFAULT 'pending',
  PRIMARY KEY (order_id),
  KEY user_id (user_id),
  KEY stock_id (stock_id),
  CONSTRAINT stock_order_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT stock_order_ibfk_2 FOREIGN KEY (stock_id) REFERENCES stock (stock_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_order`
--

LOCK TABLES stock_order WRITE;
/*!40000 ALTER TABLE stock_order DISABLE KEYS */;
/*!40000 ALTER TABLE stock_order ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trading_session`
--

DROP TABLE IF EXISTS trading_session;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE trading_session (
  session_id int NOT NULL AUTO_INCREMENT,
  start_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  end_time timestamp NULL DEFAULT NULL,
  `status` enum('open','closed') NOT NULL DEFAULT 'open',
  admin_user_id int DEFAULT NULL,
  PRIMARY KEY (session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trading_session`
--

LOCK TABLES trading_session WRITE;
/*!40000 ALTER TABLE trading_session DISABLE KEYS */;
/*!40000 ALTER TABLE trading_session ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS users;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE users (
  user_id int NOT NULL AUTO_INCREMENT,
  firstname varchar(50) DEFAULT NULL,
  lastname varchar(50) DEFAULT NULL,
  username varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_admin tinyint(1) NOT NULL DEFAULT '0',
  balance decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES users WRITE;
/*!40000 ALTER TABLE users DISABLE KEYS */;
INSERT INTO users VALUES (1,'Mahmoud','Khawaja','mahmoud.khawaja97@gmail.com','Ilovebitches2024','2024-05-03 16:25:22','2024-05-03 16:25:22',1,0.00),(2,'Leo','Messi','messi@gmail.com','abdomota2024','2024-05-03 16:27:13','2024-05-03 16:27:13',0,0.00);
/*!40000 ALTER TABLE users ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-03 22:44:07
