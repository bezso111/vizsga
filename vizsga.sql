-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2023. Jan 20. 14:59
-- Kiszolgáló verziója: 5.7.36
-- PHP verzió: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `vizsga`
--
CREATE DATABASE IF NOT EXISTS `vizsga` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci;
USE `vizsga`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `alapkamat`
--

DROP TABLE IF EXISTS `alapkamat`;
CREATE TABLE IF NOT EXISTS `alapkamat` (
  `kamat` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `alapkamat`
--

INSERT INTO `alapkamat` (`kamat`) VALUES
(125);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `befektetés`
--

DROP TABLE IF EXISTS `befektetes`;
CREATE TABLE IF NOT EXISTS `befektetes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `befekteto_id` int(11) NOT NULL,
  `osszeg` int(11) NOT NULL,
  `futamido` int(11) NOT NULL,
  `kamat` int(11) NOT NULL,
  `befektetes` tinyint(1) NOT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `oszerendeles_datum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`befekteto_id`) REFERENCES `users`(`id`),
  FOREIGN KEY (`partner_id`) REFERENCES `befektetes`(`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `name` varchar(80) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
