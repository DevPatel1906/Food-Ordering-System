-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 29, 2024 at 08:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oopd`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `cart` (IN `cid` INT, IN `rid` INT, IN `fid` INT, IN `fq` INT)   BEGIN
INSERT into cart values (cid,rid,fid,fq);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `customer_id` int(11) DEFAULT NULL,
  `restaurant_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL,
  `food_quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(45) NOT NULL,
  `customer_email` varchar(45) NOT NULL,
  `customer_password` varchar(45) NOT NULL,
  `customer_address` varchar(100) NOT NULL,
  `customer_city` varchar(20) NOT NULL,
  `customer_area` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `customer_name`, `customer_email`, `customer_password`, `customer_address`, `customer_city`, `customer_area`) VALUES
(1, 'dev', 'dev@Gmail.com', '1452', 'k-5 ishwer ami krupa flat ', 'ahmedabad', 'vejalpur'),
(3, 'aum', 'aum@gmail.com', '123', 'sa', 'sa', 'd');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `restaurant_id` int(11) DEFAULT NULL,
  `food_id` int(11) NOT NULL,
  `food_name` varchar(25) NOT NULL,
  `food_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`restaurant_id`, `food_id`, `food_name`, `food_price`) VALUES
(1, 1, 'Espresso', 150),
(1, 2, 'Cappuccino', 200),
(1, 3, 'Latte', 180),
(1, 4, 'Mocha', 220),
(1, 5, 'Americano', 160),
(2, 6, 'Big_Mac', 300),
(2, 7, 'Cheeseburger', 150),
(2, 8, 'French_Fries', 120),
(2, 9, 'Oreo_Shake', 180),
(2, 10, 'McFlurry', 200),
(3, 11, 'Cappuccino', 220),
(3, 12, 'Croissant', 150),
(3, 13, 'Caesar_Salad', 180),
(3, 14, 'Quiche_Lorraine', 200),
(3, 15, 'Berry_Smoothie', 160),
(4, 16, 'Margherita_Pizza', 250),
(4, 17, 'Pasta_Alfredo', 280),
(4, 18, 'Garlic_Bread', 120),
(4, 19, 'Caesar_Salad', 180),
(4, 20, 'Chocolate_Brownie', 150),
(5, 21, 'Paneer_Tikka', 300),
(5, 22, 'Punjabi_Thali', 350),
(5, 23, 'Dal_Makhani', 200),
(5, 24, 'Naan', 80),
(5, 25, 'Gulab_Jamun', 120);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `order_time` double DEFAULT NULL,
  `order_amount` int(11) DEFAULT NULL,
  `delivery_amount` int(11) DEFAULT NULL,
  `total_amount` int(11) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `customer_id`, `order_time`, `order_amount`, `delivery_amount`, `total_amount`, `order_status`) VALUES
(1, 1, 1724944719405, 500, 0, 400, 1),
(2, 1, 1724944957170, 750, 0, 750, 1),
(3, 1, 1724945567244, 220, 0, 220, 1),
(4, 1, 1724948577562, 300, 0, 300, 1),
(6, 3, 1724955410926, 530, 0, 530, 1),
(7, 3, 1724955541882, 580, 0, 580, 1),
(8, 3, 1724955586656, 160, 0, 160, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `restaurant_id` int(11) NOT NULL,
  `restaurant_rating` int(11) NOT NULL,
  `app_rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`restaurant_id`, `restaurant_rating`, `app_rating`) VALUES
(3, 3, 3),
(5, 1, 1),
(5, 2, 2),
(4, 5, 5),
(5, 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant` (
  `restaurant_id` int(11) NOT NULL,
  `restaurant_name` varchar(25) NOT NULL,
  `restaurant_city` varchar(25) NOT NULL,
  `restaurant_area` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`restaurant_id`, `restaurant_name`, `restaurant_city`, `restaurant_area`) VALUES
(1, 'Cafe_Coffee_Day', 'Ahmedabad', 'Satellite'),
(2, 'MC_Donalds', 'Ahmedabad', 'SBR'),
(3, 'Vince_cafe', 'Ahmedabad', 'Bopal'),
(4, 'Havmor_Eatery', 'Ahmedabad', 'Naranpura'),
(5, 'Honest', 'Ahmedabad', 'Satellite');

-- --------------------------------------------------------

--
-- Table structure for table `wishlist`
--

CREATE TABLE `wishlist` (
  `customer_id` int(11) DEFAULT NULL,
  `restaurant_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL,
  `food_quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `restaurant_id` (`restaurant_id`),
  ADD KEY `food_id` (`food_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `customer_email` (`customer_email`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`food_id`),
  ADD KEY `restaurant_id` (`restaurant_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD KEY `restaurant_id` (`restaurant_id`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`restaurant_id`);

--
-- Indexes for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `restaurant_id` (`restaurant_id`),
  ADD KEY `food_id` (`food_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `food_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `restaurant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  ADD CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`food_id`) REFERENCES `menu` (`food_id`);

--
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`);

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Constraints for table `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `menu` (`restaurant_id`);

--
-- Constraints for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  ADD CONSTRAINT `wishlist_ibfk_3` FOREIGN KEY (`food_id`) REFERENCES `menu` (`food_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
