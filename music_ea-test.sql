-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Erstellungszeit: 10. Apr 2017 um 17:01
-- Server-Version: 10.1.22-MariaDB
-- PHP-Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `enginealpha`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `music`
--

CREATE TABLE `music` (
  `artist` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `song` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `length` char(5) COLLATE latin1_general_ci NOT NULL,
  `genre` varchar(50) COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Daten für Tabelle `music`
--

INSERT INTO `music` (`artist`, `song`, `length`, `genre`) VALUES
('Ed Sheeran', 'Castle On The Hill', '4.21', 'Pop-Rock'),
('Clean Bandit, Zara Larsson', 'Symphony', '3:32', 'Pop'),
('Empire of the sun', 'High And Low', '3:44', 'Alternative/Indie'),
('Mumford & Sons', 'Little Lion Man', '03:00', 'Rock'),
('Flogging Molly', 'Float', '04:43', 'punk'),
('Luke Christopher', 'Not To Learn', '04:08', 'Rap'),
('Bebe Rexha', 'I got You', '3:16', 'Pop'),
('Social Distortion', 'Ball and Chain', '04:42', 'Punk, Rock n Roll'),
('Marshmello', 'Alone', '3:20', 'EDM'),
('AC/DC', 'Rocker', '02:50', 'Hard Rock');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
