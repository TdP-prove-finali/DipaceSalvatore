-- --------------------------------------------------------
-- Host:                         localhost
-- Versione server:              10.5.5-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database lego
CREATE DATABASE IF NOT EXISTS `lego` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `lego`;

-- Dump della struttura di tabella lego.colors
CREATE TABLE IF NOT EXISTS `colors` (
  `id` int(4) NOT NULL,
  `name` varchar(200) NOT NULL,
  `rgb` varchar(6) NOT NULL,
  `is_trans` varchar(1) NOT NULL DEFAULT '0' COMMENT 't --> true, f --> false',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Indice 2` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.elements
CREATE TABLE IF NOT EXISTS `elements` (
  `element_id` varchar(10) NOT NULL,
  `part_num` varchar(20) NOT NULL,
  `color_id` int(4) NOT NULL,
  KEY `FK_elements_parts` (`part_num`),
  KEY `FK_elements_colors` (`color_id`),
  CONSTRAINT `FK_elements_colors` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`),
  CONSTRAINT `FK_elements_parts` FOREIGN KEY (`part_num`) REFERENCES `parts` (`part_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.inventories
CREATE TABLE IF NOT EXISTS `inventories` (
  `id` int(4) NOT NULL,
  `version` int(4) NOT NULL,
  `set_num` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK__sets` (`set_num`),
  CONSTRAINT `FK__sets` FOREIGN KEY (`set_num`) REFERENCES `sets` (`set_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.inventory_minifigs
CREATE TABLE IF NOT EXISTS `inventory_minifigs` (
  `inventory_id` int(4) NOT NULL,
  `fig_num` varchar(20) NOT NULL,
  `quantity` int(4) NOT NULL,
  KEY `FK__inventories` (`inventory_id`),
  KEY `FK__minifigs` (`fig_num`),
  CONSTRAINT `FK__inventories` FOREIGN KEY (`inventory_id`) REFERENCES `inventories` (`id`),
  CONSTRAINT `FK__minifigs` FOREIGN KEY (`fig_num`) REFERENCES `minifigs` (`fig_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.inventory_parts
CREATE TABLE IF NOT EXISTS `inventory_parts` (
  `inventory_id` int(4) NOT NULL,
  `part_num` varchar(20) NOT NULL DEFAULT '',
  `color_id` int(4) NOT NULL DEFAULT 0,
  `quantity` int(4) NOT NULL DEFAULT 0,
  `is_spare` varchar(1) NOT NULL DEFAULT '0',
  KEY `FK_inventory_parts_colors` (`color_id`),
  KEY `FK_inventory_parts_inventories` (`inventory_id`),
  KEY `FK_inventory_parts_parts` (`part_num`),
  CONSTRAINT `FK_inventory_parts_colors` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`),
  CONSTRAINT `FK_inventory_parts_inventories` FOREIGN KEY (`inventory_id`) REFERENCES `inventories` (`id`),
  CONSTRAINT `FK_inventory_parts_parts` FOREIGN KEY (`part_num`) REFERENCES `parts` (`part_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.inventory_sets
CREATE TABLE IF NOT EXISTS `inventory_sets` (
  `inventory_id` int(4) NOT NULL,
  `set_num` varchar(20) NOT NULL DEFAULT '',
  `quantity` int(4) NOT NULL,
  KEY `FK_inventory_sets_inventories` (`inventory_id`),
  KEY `FK_inventory_sets_sets` (`set_num`),
  CONSTRAINT `FK_inventory_sets_inventories` FOREIGN KEY (`inventory_id`) REFERENCES `inventories` (`id`),
  CONSTRAINT `FK_inventory_sets_sets` FOREIGN KEY (`set_num`) REFERENCES `sets` (`set_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.magazzino
CREATE TABLE IF NOT EXISTS `magazzino` (
  `part_num` varchar(20) NOT NULL,
  `color_id` int(4) NOT NULL DEFAULT 0,
  `quantity` int(4) NOT NULL DEFAULT 50,
  KEY `FK_magazzino_parts` (`part_num`),
  KEY `FK_magazzino_colors` (`color_id`),
  CONSTRAINT `FK_magazzino_colors` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`),
  CONSTRAINT `FK_magazzino_parts` FOREIGN KEY (`part_num`) REFERENCES `parts` (`part_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.minifigs
CREATE TABLE IF NOT EXISTS `minifigs` (
  `fig_num` varchar(20) NOT NULL,
  `name` varchar(256) NOT NULL,
  `num_parts` int(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`fig_num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.parts
CREATE TABLE IF NOT EXISTS `parts` (
  `part_num` varchar(20) NOT NULL,
  `name` varchar(250) NOT NULL,
  `part_cat_id` int(4) NOT NULL,
  `part_material` varchar(50) NOT NULL,
  PRIMARY KEY (`part_num`),
  KEY `FK_parts_part_categories` (`part_cat_id`),
  CONSTRAINT `FK_parts_part_categories` FOREIGN KEY (`part_cat_id`) REFERENCES `part_categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.part_categories
CREATE TABLE IF NOT EXISTS `part_categories` (
  `id` int(4) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Indice 2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.part_relationships
CREATE TABLE IF NOT EXISTS `part_relationships` (
  `rel_type` varchar(1) NOT NULL,
  `child_part_num` varchar(20) NOT NULL,
  `parent_part_num` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.sets
CREATE TABLE IF NOT EXISTS `sets` (
  `set_num` varchar(20) NOT NULL,
  `name` varchar(256) NOT NULL,
  `year` int(4) NOT NULL DEFAULT 0,
  `theme_id` int(5) NOT NULL,
  `num_parts` int(6) NOT NULL,
  PRIMARY KEY (`set_num`),
  KEY `FK_sets_themes` (`theme_id`),
  CONSTRAINT `FK_sets_themes` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabella dei set';

-- L’esportazione dei dati non era selezionata.

-- Dump della struttura di tabella lego.themes
CREATE TABLE IF NOT EXISTS `themes` (
  `id` int(5) NOT NULL,
  `name` varchar(64) NOT NULL DEFAULT '',
  `parent_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='temi';

-- L’esportazione dei dati non era selezionata.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
