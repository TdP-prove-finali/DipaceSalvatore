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



INSERT INTO themes(id, name) VALUES (1,'Technic');
INSERT INTO themes(id, name) VALUES (22,'Creator');
INSERT INTO themes(id, name) VALUES (50,'Town');
INSERT INTO themes(id, name) VALUES (112,'Racers');
INSERT INTO themes(id, name) VALUES (126,'Space');
INSERT INTO themes(id, name) VALUES (147,'Pirates');
INSERT INTO themes(id, name) VALUES (155,'Modular Buildings');
INSERT INTO themes(id, name) VALUES (158,'Star Wars');
INSERT INTO themes(id, name) VALUES (186,'Castle');
INSERT INTO themes(id, name) VALUES (204,'Designer Sets');
INSERT INTO themes(id, name) VALUES (206,'Seasonal');
INSERT INTO themes(id, name) VALUES (233,'Train');
INSERT INTO themes(id, name) VALUES (246,'Harry Potter');
INSERT INTO themes(id, name) VALUES (252,'Architecture');
INSERT INTO themes(id, name) VALUES (254,'Bulk Bricks');
INSERT INTO themes(id, name) VALUES (258,'Mindstorms');
INSERT INTO themes(id, name) VALUES (263,'Pirates of the Caribbean');
INSERT INTO themes(id, name) VALUES (264,'Indiana Jones');
INSERT INTO themes(id, name) VALUES (269,'Cars');
INSERT INTO themes(id, name) VALUES (270,'Ben 10');
INSERT INTO themes(id, name) VALUES (271,'Prince of Persia');
INSERT INTO themes(id, name) VALUES (272,'SpongeBob SquarePants');
INSERT INTO themes(id, name) VALUES (273,'Studios');
INSERT INTO themes(id, name) VALUES (275,'Toy Story');
INSERT INTO themes(id, name) VALUES (276,'Sculptures');
INSERT INTO themes(id, name) VALUES (278,'Model Team');
INSERT INTO themes(id, name) VALUES (279,'4 Juniors');
INSERT INTO themes(id, name) VALUES (296,'Adventurers');
INSERT INTO themes(id, name) VALUES (301,'Other');
INSERT INTO themes(id, name) VALUES (302,'Agents');
INSERT INTO themes(id, name) VALUES (304,'Alpha Team');
INSERT INTO themes(id, name) VALUES (307,'Aquazone');
INSERT INTO themes(id, name) VALUES (315,'Atlantis');
INSERT INTO themes(id, name) VALUES (317,'Avatar');
INSERT INTO themes(id, name) VALUES (318,'Belville');
INSERT INTO themes(id, name) VALUES (324,'Bionicle');
INSERT INTO themes(id, name) VALUES (363,'Boat');
INSERT INTO themes(id, name) VALUES (364,'Building Set with People');
INSERT INTO themes(id, name) VALUES (365,'System');
INSERT INTO themes(id, name) VALUES (384,'Dino 2010');
INSERT INTO themes(id, name) VALUES (385,'Dino Attack');
INSERT INTO themes(id, name) VALUES (386,'Dinosaurs');
INSERT INTO themes(id, name) VALUES (387,'Discovery');
INSERT INTO themes(id, name) VALUES (388,'Disney''s Mickey Mouse');
INSERT INTO themes(id, name) VALUES (389,'Exo-Force');
INSERT INTO themes(id, name) VALUES (390,'Fabuland');
INSERT INTO themes(id, name) VALUES (397,'Factory');
INSERT INTO themes(id, name) VALUES (398,'FIRST LEGO League');
INSERT INTO themes(id, name) VALUES (399,'Freestyle');
INSERT INTO themes(id, name) VALUES (400,'Hero Factory');
INSERT INTO themes(id, name) VALUES (404,'Hobby Sets');
INSERT INTO themes(id, name) VALUES (405,'Homemaker');
INSERT INTO themes(id, name) VALUES (406,'Inventor');
INSERT INTO themes(id, name) VALUES (407,'Island Xtreme Stunts');
INSERT INTO themes(id, name) VALUES (408,'LEGO Brand Store');
INSERT INTO themes(id, name) VALUES (411,'Legoland');
INSERT INTO themes(id, name) VALUES (425,'Legoland Parks');
INSERT INTO themes(id, name) VALUES (432,'Master Building Academy');
INSERT INTO themes(id, name) VALUES (433,'Minitalia');
INSERT INTO themes(id, name) VALUES (434,'Ninja');
INSERT INTO themes(id, name) VALUES (435,'Ninjago');
INSERT INTO themes(id, name) VALUES (437,'Pharaoh''s Quest');
INSERT INTO themes(id, name) VALUES (438,'Power Functions');
INSERT INTO themes(id, name) VALUES (439,'Power Miners');
INSERT INTO themes(id, name) VALUES (440,'Primo');
INSERT INTO themes(id, name) VALUES (441,'Quatro');
INSERT INTO themes(id, name) VALUES (442,'Rock Raiders');
INSERT INTO themes(id, name) VALUES (443,'Service Packs');
INSERT INTO themes(id, name) VALUES (458,'Sports');
INSERT INTO themes(id, name) VALUES (463,'Spybiotics');
INSERT INTO themes(id, name) VALUES (464,'Time Cruisers');
INSERT INTO themes(id, name) VALUES (465,'Universal Building Set');
INSERT INTO themes(id, name) VALUES (474,'Vikings');
INSERT INTO themes(id, name) VALUES (475,'Western');
INSERT INTO themes(id, name) VALUES (478,'X-Pod');
INSERT INTO themes(id, name) VALUES (480,'Znap');
INSERT INTO themes(id, name) VALUES (481,'Dino');
INSERT INTO themes(id, name) VALUES (494,'Friends');
INSERT INTO themes(id, name) VALUES (497,'Books');
INSERT INTO themes(id, name) VALUES (500,'Clikits');
INSERT INTO themes(id, name) VALUES (501,'Gear');
INSERT INTO themes(id, name) VALUES (504,'Duplo');
INSERT INTO themes(id, name) VALUES (507,'Educational and Dacta');
INSERT INTO themes(id, name) VALUES (535,'Collectible Minifigures');
INSERT INTO themes(id, name) VALUES (558,'Monster Fighters');
INSERT INTO themes(id, name) VALUES (559,'Value Packs');
INSERT INTO themes(id, name) VALUES (560,'Universe');
INSERT INTO themes(id, name) VALUES (561,'The Hobbit and Lord of the Rings');
INSERT INTO themes(id, name) VALUES (570,'Teenage Mutant Ninja Turtles');
INSERT INTO themes(id, name) VALUES (571,'Legends of Chima');
INSERT INTO themes(id, name) VALUES (575,'The Lone Ranger');
INSERT INTO themes(id, name) VALUES (576,'LEGO Ideas and CUUSOO');
INSERT INTO themes(id, name) VALUES (577,'Minecraft');
INSERT INTO themes(id, name) VALUES (578,'The LEGO Movie');
INSERT INTO themes(id, name) VALUES (579,'Disney Princess');
INSERT INTO themes(id, name) VALUES (580,'Mixels');
INSERT INTO themes(id, name) VALUES (590,'Fusion');
INSERT INTO themes(id, name) VALUES (591,'Juniors');
INSERT INTO themes(id, name) VALUES (598,'Promotional');
INSERT INTO themes(id, name) VALUES (599,'LEGO Exclusive');
INSERT INTO themes(id, name) VALUES (600,'Elves');
INSERT INTO themes(id, name) VALUES (601,'Speed Champions');
INSERT INTO themes(id, name) VALUES (602,'Jurassic World');
INSERT INTO themes(id, name) VALUES (603,'Scooby-Doo');
INSERT INTO themes(id, name) VALUES (604,'Dimensions');
INSERT INTO themes(id, name) VALUES (605,'Nexo Knights');
INSERT INTO themes(id, name) VALUES (606,'Angry Birds');
INSERT INTO themes(id, name) VALUES (607,'Ghostbusters');
INSERT INTO themes(id, name) VALUES (608,'Disney');
INSERT INTO themes(id, name) VALUES (610,'Brickheadz');
INSERT INTO themes(id, name) VALUES (617,'DC Super Hero Girls');
INSERT INTO themes(id, name) VALUES (621,'Classic');
INSERT INTO themes(id, name) VALUES (625,'Unikitty!');
INSERT INTO themes(id, name) VALUES (646,'Xtra');
INSERT INTO themes(id, name) VALUES (654,'The Powerpuff Girls');
INSERT INTO themes(id, name) VALUES (668,'Scala');
INSERT INTO themes(id, name) VALUES (669,'Overwatch');
INSERT INTO themes(id, name) VALUES (671,'Forma');
INSERT INTO themes(id, name) VALUES (676,'Hidden Side');
INSERT INTO themes(id, name) VALUES (680,'Stranger Things');
INSERT INTO themes(id, name) VALUES (682,'Trolls: World Tour');
INSERT INTO themes(id, name) VALUES (683,'LEGO Originals');
INSERT INTO themes(id, name) VALUES (685,'Galidor');
INSERT INTO themes(id, name) VALUES (686,'Chinese Traditional Festivals');
INSERT INTO themes(id, name) VALUES (688,'DOTS');
INSERT INTO themes(id, name) VALUES (689,'Minions');
INSERT INTO themes(id, name) VALUES (690,'Super Mario');
INSERT INTO themes(id, name) VALUES (693,'Monkie Kid');
INSERT INTO themes(id, name) VALUES (694,'Brick Sketches');
INSERT INTO themes(id, name) VALUES (695,'Super Heroes DC');
INSERT INTO themes(id, name) VALUES (696,'Super Heroes Marvel');
INSERT INTO themes(id, name) VALUES (709,'LEGO Art');
INSERT INTO themes(id, name, parent_id) VALUES (2,'Arctic Technic',1);
INSERT INTO themes(id, name, parent_id) VALUES (3,'Competition',1);
INSERT INTO themes(id, name, parent_id) VALUES (4,'Expert Builder',1);
INSERT INTO themes(id, name, parent_id) VALUES (5,'Model',1);
INSERT INTO themes(id, name, parent_id) VALUES (6,'Airport',5);
INSERT INTO themes(id, name, parent_id) VALUES (7,'Construction',5);
INSERT INTO themes(id, name, parent_id) VALUES (8,'Farm',5);
INSERT INTO themes(id, name, parent_id) VALUES (9,'Fire',5);
INSERT INTO themes(id, name, parent_id) VALUES (10,'Harbor',5);
INSERT INTO themes(id, name, parent_id) VALUES (11,'Off-Road',5);
INSERT INTO themes(id, name, parent_id) VALUES (12,'Race',5);
INSERT INTO themes(id, name, parent_id) VALUES (13,'Riding Cycle',5);
INSERT INTO themes(id, name, parent_id) VALUES (14,'Robot',5);
INSERT INTO themes(id, name, parent_id) VALUES (15,'Traffic',5);
INSERT INTO themes(id, name, parent_id) VALUES (16,'RoboRiders',1);
INSERT INTO themes(id, name, parent_id) VALUES (17,'Speed Slammers',1);
INSERT INTO themes(id, name, parent_id) VALUES (18,'Star Wars',1);
INSERT INTO themes(id, name, parent_id) VALUES (19,'Supplemental',1);
INSERT INTO themes(id, name, parent_id) VALUES (20,'Throwbot Slizer',1);
INSERT INTO themes(id, name, parent_id) VALUES (21,'Universal Building Set',1);
INSERT INTO themes(id, name, parent_id) VALUES (23,'Basic Model',22);
INSERT INTO themes(id, name, parent_id) VALUES (24,'Airport',23);
INSERT INTO themes(id, name, parent_id) VALUES (25,'Castle',23);
INSERT INTO themes(id, name, parent_id) VALUES (26,'Construction',23);
INSERT INTO themes(id, name, parent_id) VALUES (27,'Race',23);
INSERT INTO themes(id, name, parent_id) VALUES (28,'Harbor',23);
INSERT INTO themes(id, name, parent_id) VALUES (29,'Train',23);
INSERT INTO themes(id, name, parent_id) VALUES (30,'Traffic',23);
INSERT INTO themes(id, name, parent_id) VALUES (31,'Creature',23);
INSERT INTO themes(id, name, parent_id) VALUES (32,'Robot',23);
INSERT INTO themes(id, name, parent_id) VALUES (33,'Food & Drink',23);
INSERT INTO themes(id, name, parent_id) VALUES (34,'Building',23);
INSERT INTO themes(id, name, parent_id) VALUES (35,'Cargo',23);
INSERT INTO themes(id, name, parent_id) VALUES (37,'Basic Set',22);
INSERT INTO themes(id, name, parent_id) VALUES (38,'Model',22);
INSERT INTO themes(id, name, parent_id) VALUES (39,'Traffic',38);
INSERT INTO themes(id, name, parent_id) VALUES (40,'Creature',38);
INSERT INTO themes(id, name, parent_id) VALUES (42,'Airport',38);
INSERT INTO themes(id, name, parent_id) VALUES (43,'Building',38);
INSERT INTO themes(id, name, parent_id) VALUES (44,'Recreation',38);
INSERT INTO themes(id, name, parent_id) VALUES (45,'Cargo',38);
INSERT INTO themes(id, name, parent_id) VALUES (46,'Harbor',38);
INSERT INTO themes(id, name, parent_id) VALUES (48,'Supplemental',22);
INSERT INTO themes(id, name, parent_id) VALUES (49,'Mecha',22);
INSERT INTO themes(id, name, parent_id) VALUES (51,'Arctic',50);
INSERT INTO themes(id, name, parent_id) VALUES (52,'City',50);
INSERT INTO themes(id, name, parent_id) VALUES (53,'Airport',52);
INSERT INTO themes(id, name, parent_id) VALUES (54,'Cargo',52);
INSERT INTO themes(id, name, parent_id) VALUES (55,'Coast Guard',52);
INSERT INTO themes(id, name, parent_id) VALUES (56,'Construction',52);
INSERT INTO themes(id, name, parent_id) VALUES (57,'Farm',52);
INSERT INTO themes(id, name, parent_id) VALUES (58,'Fire',52);
INSERT INTO themes(id, name, parent_id) VALUES (59,'Harbor',52);
INSERT INTO themes(id, name, parent_id) VALUES (60,'Hospital',52);
INSERT INTO themes(id, name, parent_id) VALUES (61,'Police',52);
INSERT INTO themes(id, name, parent_id) VALUES (62,'Supplemental',52);
INSERT INTO themes(id, name, parent_id) VALUES (63,'Traffic',52);
INSERT INTO themes(id, name, parent_id) VALUES (64,'Off-Road',52);
INSERT INTO themes(id, name, parent_id) VALUES (65,'Arctic',52);
INSERT INTO themes(id, name, parent_id) VALUES (66,'Trains',52);
INSERT INTO themes(id, name, parent_id) VALUES (67,'Classic Town',50);
INSERT INTO themes(id, name, parent_id) VALUES (68,'Airport',67);
INSERT INTO themes(id, name, parent_id) VALUES (69,'Building',67);
INSERT INTO themes(id, name, parent_id) VALUES (70,'Cargo',67);
INSERT INTO themes(id, name, parent_id) VALUES (71,'Coast Guard',67);
INSERT INTO themes(id, name, parent_id) VALUES (72,'Construction',67);
INSERT INTO themes(id, name, parent_id) VALUES (73,'Farm',67);
INSERT INTO themes(id, name, parent_id) VALUES (74,'Fire',67);
INSERT INTO themes(id, name, parent_id) VALUES (75,'Food & Drink',67);
INSERT INTO themes(id, name, parent_id) VALUES (76,'Station',67);
INSERT INTO themes(id, name, parent_id) VALUES (77,'Harbor',67);
INSERT INTO themes(id, name, parent_id) VALUES (78,'Hospital',67);
INSERT INTO themes(id, name, parent_id) VALUES (79,'Off-Road',67);
INSERT INTO themes(id, name, parent_id) VALUES (80,'Police',67);
INSERT INTO themes(id, name, parent_id) VALUES (81,'Post Office',67);
INSERT INTO themes(id, name, parent_id) VALUES (82,'Race',67);
INSERT INTO themes(id, name, parent_id) VALUES (83,'Recreation',67);
INSERT INTO themes(id, name, parent_id) VALUES (84,'Supplemental',67);
INSERT INTO themes(id, name, parent_id) VALUES (85,'Traffic',67);
INSERT INTO themes(id, name, parent_id) VALUES (86,'Divers',50);
INSERT INTO themes(id, name, parent_id) VALUES (87,'Extreme Team',50);
INSERT INTO themes(id, name, parent_id) VALUES (88,'Launch Command',50);
INSERT INTO themes(id, name, parent_id) VALUES (89,'Outback',50);
INSERT INTO themes(id, name, parent_id) VALUES (90,'Paradisa',50);
INSERT INTO themes(id, name, parent_id) VALUES (91,'Race',50);
INSERT INTO themes(id, name, parent_id) VALUES (92,'Res-Q',50);
INSERT INTO themes(id, name, parent_id) VALUES (93,'Space Port',50);
INSERT INTO themes(id, name, parent_id) VALUES (94,'Town Jr.',50);
INSERT INTO themes(id, name, parent_id) VALUES (95,'Cargo',94);
INSERT INTO themes(id, name, parent_id) VALUES (96,'Coast Guard',94);
INSERT INTO themes(id, name, parent_id) VALUES (97,'Construction',94);
INSERT INTO themes(id, name, parent_id) VALUES (98,'Fire',94);
INSERT INTO themes(id, name, parent_id) VALUES (99,'Gas Station',94);
INSERT INTO themes(id, name, parent_id) VALUES (100,'Police',94);
INSERT INTO themes(id, name, parent_id) VALUES (101,'Race',94);
INSERT INTO themes(id, name, parent_id) VALUES (102,'Supplemental',94);
INSERT INTO themes(id, name, parent_id) VALUES (103,'Traffic',94);
INSERT INTO themes(id, name, parent_id) VALUES (104,'Town Plan',50);
INSERT INTO themes(id, name, parent_id) VALUES (105,'World City',50);
INSERT INTO themes(id, name, parent_id) VALUES (106,'Airport',105);
INSERT INTO themes(id, name, parent_id) VALUES (107,'Coast Guard',105);
INSERT INTO themes(id, name, parent_id) VALUES (108,'Fire',105);
INSERT INTO themes(id, name, parent_id) VALUES (111,'Police',105);
INSERT INTO themes(id, name, parent_id) VALUES (113,'Drome Racers',112);
INSERT INTO themes(id, name, parent_id) VALUES (114,'Ferrari',112);
INSERT INTO themes(id, name, parent_id) VALUES (115,'Lamborghini',112);
INSERT INTO themes(id, name, parent_id) VALUES (116,'Power Racers',112);
INSERT INTO themes(id, name, parent_id) VALUES (117,'Radio Control',112);
INSERT INTO themes(id, name, parent_id) VALUES (118,'Speed Racer',112);
INSERT INTO themes(id, name, parent_id) VALUES (119,'Supplemental',112);
INSERT INTO themes(id, name, parent_id) VALUES (120,'Tiny Turbos',112);
INSERT INTO themes(id, name, parent_id) VALUES (121,'Track System',112);
INSERT INTO themes(id, name, parent_id) VALUES (122,'Williams F1',112);
INSERT INTO themes(id, name, parent_id) VALUES (123,'World Racers',112);
INSERT INTO themes(id, name, parent_id) VALUES (125,'Xalax',112);
INSERT INTO themes(id, name, parent_id) VALUES (127,'Alien Conquest',126);
INSERT INTO themes(id, name, parent_id) VALUES (128,'Blacktron I',126);
INSERT INTO themes(id, name, parent_id) VALUES (129,'Blacktron II',126);
INSERT INTO themes(id, name, parent_id) VALUES (130,'Classic Space',126);
INSERT INTO themes(id, name, parent_id) VALUES (131,'Exploriens',126);
INSERT INTO themes(id, name, parent_id) VALUES (132,'Futuron',126);
INSERT INTO themes(id, name, parent_id) VALUES (133,'Ice Planet 2002',126);
INSERT INTO themes(id, name, parent_id) VALUES (134,'Insectoids',126);
INSERT INTO themes(id, name, parent_id) VALUES (135,'Life On Mars',126);
INSERT INTO themes(id, name, parent_id) VALUES (136,'M:Tron',126);
INSERT INTO themes(id, name, parent_id) VALUES (137,'Mars Mission',126);
INSERT INTO themes(id, name, parent_id) VALUES (138,'RoboForce',126);
INSERT INTO themes(id, name, parent_id) VALUES (139,'Space Police I',126);
INSERT INTO themes(id, name, parent_id) VALUES (140,'Space Police II',126);
INSERT INTO themes(id, name, parent_id) VALUES (141,'Space Police III',126);
INSERT INTO themes(id, name, parent_id) VALUES (142,'Spyrius',126);
INSERT INTO themes(id, name, parent_id) VALUES (143,'Supplemental',126);
INSERT INTO themes(id, name, parent_id) VALUES (144,'UFO',126);
INSERT INTO themes(id, name, parent_id) VALUES (145,'Unitron',126);
INSERT INTO themes(id, name, parent_id) VALUES (146,'Galaxy Squad',126);
INSERT INTO themes(id, name, parent_id) VALUES (148,'Pirates I',147);
INSERT INTO themes(id, name, parent_id) VALUES (149,'Imperial Armada',148);
INSERT INTO themes(id, name, parent_id) VALUES (151,'Imperial Soldiers',148);
INSERT INTO themes(id, name, parent_id) VALUES (152,'Islanders',148);
INSERT INTO themes(id, name, parent_id) VALUES (153,'Pirates II',147);
INSERT INTO themes(id, name, parent_id) VALUES (154,'Pirates III',147);
INSERT INTO themes(id, name, parent_id) VALUES (156,'Mini',155);
INSERT INTO themes(id, name, parent_id) VALUES (157,'Bricktober',155);
INSERT INTO themes(id, name, parent_id) VALUES (171,'Ultimate Collector Series',158);
INSERT INTO themes(id, name, parent_id) VALUES (187,'Black Falcons',186);
INSERT INTO themes(id, name, parent_id) VALUES (188,'Black Knights',186);
INSERT INTO themes(id, name, parent_id) VALUES (189,'Classic Castle',186);
INSERT INTO themes(id, name, parent_id) VALUES (190,'Crusaders',186);
INSERT INTO themes(id, name, parent_id) VALUES (191,'Dark Forest',186);
INSERT INTO themes(id, name, parent_id) VALUES (193,'Fantasy Era',186);
INSERT INTO themes(id, name, parent_id) VALUES (194,'Forestmen',186);
INSERT INTO themes(id, name, parent_id) VALUES (195,'Fright Knights',186);
INSERT INTO themes(id, name, parent_id) VALUES (196,'Kingdoms',186);
INSERT INTO themes(id, name, parent_id) VALUES (197,'Knights Kingdom I',186);
INSERT INTO themes(id, name, parent_id) VALUES (198,'Knights Kingdom II',186);
INSERT INTO themes(id, name, parent_id) VALUES (199,'Lion Knights',186);
INSERT INTO themes(id, name, parent_id) VALUES (200,'My Own Creation',186);
INSERT INTO themes(id, name, parent_id) VALUES (201,'Royal Knights',186);
INSERT INTO themes(id, name, parent_id) VALUES (202,'Supplemental',186);
INSERT INTO themes(id, name, parent_id) VALUES (203,'Wolfpack',186);
INSERT INTO themes(id, name, parent_id) VALUES (205,'Building',204);
INSERT INTO themes(id, name, parent_id) VALUES (207,'Advent',206);
INSERT INTO themes(id, name, parent_id) VALUES (208,'City',207);
INSERT INTO themes(id, name, parent_id) VALUES (209,'Star Wars',207);
INSERT INTO themes(id, name, parent_id) VALUES (210,'Belville',207);
INSERT INTO themes(id, name, parent_id) VALUES (211,'Castle',207);
INSERT INTO themes(id, name, parent_id) VALUES (212,'Classic Basic',207);
INSERT INTO themes(id, name, parent_id) VALUES (213,'Clikits',207);
INSERT INTO themes(id, name, parent_id) VALUES (214,'Creator',207);
INSERT INTO themes(id, name, parent_id) VALUES (215,'Pirates',207);
INSERT INTO themes(id, name, parent_id) VALUES (216,'Friends',207);
INSERT INTO themes(id, name, parent_id) VALUES (227,'Christmas',206);
INSERT INTO themes(id, name, parent_id) VALUES (228,'Creator',227);
INSERT INTO themes(id, name, parent_id) VALUES (229,'Easter',206);
INSERT INTO themes(id, name, parent_id) VALUES (230,'Halloween',206);
INSERT INTO themes(id, name, parent_id) VALUES (231,'Thanksgiving',206);
INSERT INTO themes(id, name, parent_id) VALUES (232,'Valentine',206);
INSERT INTO themes(id, name, parent_id) VALUES (234,'12V',233);
INSERT INTO themes(id, name, parent_id) VALUES (235,'4.5V',233);
INSERT INTO themes(id, name, parent_id) VALUES (236,'9V',233);
INSERT INTO themes(id, name, parent_id) VALUES (237,'My Own Creation',236);
INSERT INTO themes(id, name, parent_id) VALUES (238,'My Own Train',236);
INSERT INTO themes(id, name, parent_id) VALUES (239,'World City',236);
INSERT INTO themes(id, name, parent_id) VALUES (240,'RC Train',233);
INSERT INTO themes(id, name, parent_id) VALUES (241,'Supplemental',233);
INSERT INTO themes(id, name, parent_id) VALUES (242,'12V',241);
INSERT INTO themes(id, name, parent_id) VALUES (243,'4.5V',241);
INSERT INTO themes(id, name, parent_id) VALUES (244,'9V',241);
INSERT INTO themes(id, name, parent_id) VALUES (245,'RC Train',241);
INSERT INTO themes(id, name, parent_id) VALUES (253,'Skylines',252);
INSERT INTO themes(id, name, parent_id) VALUES (255,'Castle',254);
INSERT INTO themes(id, name, parent_id) VALUES (256,'Technic',254);
INSERT INTO themes(id, name, parent_id) VALUES (257,'Train',254);
INSERT INTO themes(id, name, parent_id) VALUES (259,'NXT',258);
INSERT INTO themes(id, name, parent_id) VALUES (260,'RCX',258);
INSERT INTO themes(id, name, parent_id) VALUES (261,'Star Wars',258);
INSERT INTO themes(id, name, parent_id) VALUES (262,'EV3',258);
INSERT INTO themes(id, name, parent_id) VALUES (274,'Jurassic Park III',273);
INSERT INTO themes(id, name, parent_id) VALUES (277,'Mosaic',276);
INSERT INTO themes(id, name, parent_id) VALUES (280,'Jack Stone',279);
INSERT INTO themes(id, name, parent_id) VALUES (287,'Spider-Man',279);
INSERT INTO themes(id, name, parent_id) VALUES (297,'Desert',296);
INSERT INTO themes(id, name, parent_id) VALUES (298,'Dino Island',296);
INSERT INTO themes(id, name, parent_id) VALUES (299,'Jungle',296);
INSERT INTO themes(id, name, parent_id) VALUES (300,'Orient Expedition',296);
INSERT INTO themes(id, name, parent_id) VALUES (303,'Ultra Agents',302);
INSERT INTO themes(id, name, parent_id) VALUES (305,'Mission Deep Sea',304);
INSERT INTO themes(id, name, parent_id) VALUES (306,'Mission Deep Freeze',304);
INSERT INTO themes(id, name, parent_id) VALUES (308,'Aquanauts',307);
INSERT INTO themes(id, name, parent_id) VALUES (309,'Aquaraiders I',307);
INSERT INTO themes(id, name, parent_id) VALUES (310,'Aquaraiders II',307);
INSERT INTO themes(id, name, parent_id) VALUES (311,'Aquasharks',307);
INSERT INTO themes(id, name, parent_id) VALUES (312,'Hydronauts',307);
INSERT INTO themes(id, name, parent_id) VALUES (313,'Stingrays',307);
INSERT INTO themes(id, name, parent_id) VALUES (319,'Fairy-Tale',318);
INSERT INTO themes(id, name, parent_id) VALUES (320,'Golden Land',318);
INSERT INTO themes(id, name, parent_id) VALUES (321,'Hospital',318);
INSERT INTO themes(id, name, parent_id) VALUES (322,'Playhouse',318);
INSERT INTO themes(id, name, parent_id) VALUES (323,'Recreation',318);
INSERT INTO themes(id, name, parent_id) VALUES (325,'Agori',324);
INSERT INTO themes(id, name, parent_id) VALUES (326,'Barraki',324);
INSERT INTO themes(id, name, parent_id) VALUES (327,'Battle Vehicles',324);
INSERT INTO themes(id, name, parent_id) VALUES (328,'Bohrok',324);
INSERT INTO themes(id, name, parent_id) VALUES (329,'Bohrok Va',324);
INSERT INTO themes(id, name, parent_id) VALUES (330,'Bohrok-Kal',324);
INSERT INTO themes(id, name, parent_id) VALUES (331,'Glatorian',324);
INSERT INTO themes(id, name, parent_id) VALUES (332,'Glatorian Legends',324);
INSERT INTO themes(id, name, parent_id) VALUES (333,'Matoran of Light',324);
INSERT INTO themes(id, name, parent_id) VALUES (334,'Matoran of Mahri Nui',324);
INSERT INTO themes(id, name, parent_id) VALUES (335,'Matoran of Mata Nui',324);
INSERT INTO themes(id, name, parent_id) VALUES (336,'Matoran of Metru Nui',324);
INSERT INTO themes(id, name, parent_id) VALUES (337,'Matoran of Voya Nui',324);
INSERT INTO themes(id, name, parent_id) VALUES (338,'Mistika',324);
INSERT INTO themes(id, name, parent_id) VALUES (339,'Phantoka',324);
INSERT INTO themes(id, name, parent_id) VALUES (340,'Piraka',324);
INSERT INTO themes(id, name, parent_id) VALUES (341,'Playsets',324);
INSERT INTO themes(id, name, parent_id) VALUES (342,'Rahaga',324);
INSERT INTO themes(id, name, parent_id) VALUES (343,'Rahi',324);
INSERT INTO themes(id, name, parent_id) VALUES (344,'Rahkshi',324);
INSERT INTO themes(id, name, parent_id) VALUES (345,'Stars',324);
INSERT INTO themes(id, name, parent_id) VALUES (346,'Supplemental',324);
INSERT INTO themes(id, name, parent_id) VALUES (347,'Titans',324);
INSERT INTO themes(id, name, parent_id) VALUES (348,'Toa',324);
INSERT INTO themes(id, name, parent_id) VALUES (349,'Toa Hagah',324);
INSERT INTO themes(id, name, parent_id) VALUES (350,'Toa Hordika',324);
INSERT INTO themes(id, name, parent_id) VALUES (351,'Toa Inika',324);
INSERT INTO themes(id, name, parent_id) VALUES (352,'Toa Mahri',324);
INSERT INTO themes(id, name, parent_id) VALUES (353,'Toa Metru',324);
INSERT INTO themes(id, name, parent_id) VALUES (354,'Toa Nuva',324);
INSERT INTO themes(id, name, parent_id) VALUES (355,'Tohunga',324);
INSERT INTO themes(id, name, parent_id) VALUES (356,'Turaga',324);
INSERT INTO themes(id, name, parent_id) VALUES (357,'Vahki',324);
INSERT INTO themes(id, name, parent_id) VALUES (358,'Visorak',324);
INSERT INTO themes(id, name, parent_id) VALUES (359,'Warriors',324);
INSERT INTO themes(id, name, parent_id) VALUES (360,'Protectors',324);
INSERT INTO themes(id, name, parent_id) VALUES (361,'Skull Spiders',324);
INSERT INTO themes(id, name, parent_id) VALUES (362,'Toa Okoto',324);
INSERT INTO themes(id, name, parent_id) VALUES (366,'Basic Set',365);
INSERT INTO themes(id, name, parent_id) VALUES (367,'Building',365);
INSERT INTO themes(id, name, parent_id) VALUES (368,'HO 1:87 Vehicles',365);
INSERT INTO themes(id, name, parent_id) VALUES (369,'Jumbo Bricks',365);
INSERT INTO themes(id, name, parent_id) VALUES (370,'Mosaic',365);
INSERT INTO themes(id, name, parent_id) VALUES (371,'Supplemental',365);
INSERT INTO themes(id, name, parent_id) VALUES (372,'Town Plan',365);
INSERT INTO themes(id, name, parent_id) VALUES (373,'Vehicle',365);
INSERT INTO themes(id, name, parent_id) VALUES (374,'Airport',373);
INSERT INTO themes(id, name, parent_id) VALUES (375,'Farm',373);
INSERT INTO themes(id, name, parent_id) VALUES (376,'Fire',373);
INSERT INTO themes(id, name, parent_id) VALUES (377,'Harbor',373);
INSERT INTO themes(id, name, parent_id) VALUES (378,'Traffic',373);
INSERT INTO themes(id, name, parent_id) VALUES (379,'Supplemental',373);
INSERT INTO themes(id, name, parent_id) VALUES (380,'Train',373);
INSERT INTO themes(id, name, parent_id) VALUES (381,'Construction',373);
INSERT INTO themes(id, name, parent_id) VALUES (382,'Cargo',373);
INSERT INTO themes(id, name, parent_id) VALUES (383,'Wooden Box Set',365);
INSERT INTO themes(id, name, parent_id) VALUES (401,'Heroes',400);
INSERT INTO themes(id, name, parent_id) VALUES (402,'Vehicles',400);
INSERT INTO themes(id, name, parent_id) VALUES (403,'Villains',400);
INSERT INTO themes(id, name, parent_id) VALUES (409,'Monthly Mini Model Build',408);
INSERT INTO themes(id, name, parent_id) VALUES (410,'Pick A Model',408);
INSERT INTO themes(id, name, parent_id) VALUES (412,'Airport',411);
INSERT INTO themes(id, name, parent_id) VALUES (413,'Building',411);
INSERT INTO themes(id, name, parent_id) VALUES (414,'Castle',411);
INSERT INTO themes(id, name, parent_id) VALUES (415,'Coast Guard',411);
INSERT INTO themes(id, name, parent_id) VALUES (416,'Construction',411);
INSERT INTO themes(id, name, parent_id) VALUES (417,'Fire',411);
INSERT INTO themes(id, name, parent_id) VALUES (418,'Gas Station',411);
INSERT INTO themes(id, name, parent_id) VALUES (419,'Harbor',411);
INSERT INTO themes(id, name, parent_id) VALUES (420,'Hospital',411);
INSERT INTO themes(id, name, parent_id) VALUES (421,'Police',411);
INSERT INTO themes(id, name, parent_id) VALUES (422,'Space',411);
INSERT INTO themes(id, name, parent_id) VALUES (423,'Vehicle',411);
INSERT INTO themes(id, name, parent_id) VALUES (424,'Western',411);
INSERT INTO themes(id, name, parent_id) VALUES (426,'Bionicle',425);
INSERT INTO themes(id, name, parent_id) VALUES (427,'Holiday',425);
INSERT INTO themes(id, name, parent_id) VALUES (430,'Pirates',425);
INSERT INTO themes(id, name, parent_id) VALUES (436,'Airjitzu',435);
INSERT INTO themes(id, name, parent_id) VALUES (444,'Adventurers',443);
INSERT INTO themes(id, name, parent_id) VALUES (445,'Aquazone',443);
INSERT INTO themes(id, name, parent_id) VALUES (446,'Belville',443);
INSERT INTO themes(id, name, parent_id) VALUES (447,'Castle',443);
INSERT INTO themes(id, name, parent_id) VALUES (448,'Fabuland',443);
INSERT INTO themes(id, name, parent_id) VALUES (449,'Pirates',443);
INSERT INTO themes(id, name, parent_id) VALUES (450,'Primo',443);
INSERT INTO themes(id, name, parent_id) VALUES (451,'Scala',443);
INSERT INTO themes(id, name, parent_id) VALUES (452,'Space',443);
INSERT INTO themes(id, name, parent_id) VALUES (453,'Technic',443);
INSERT INTO themes(id, name, parent_id) VALUES (454,'Town',443);
INSERT INTO themes(id, name, parent_id) VALUES (455,'Classic Town',454);
INSERT INTO themes(id, name, parent_id) VALUES (456,'Train',443);
INSERT INTO themes(id, name, parent_id) VALUES (457,'Western',443);
INSERT INTO themes(id, name, parent_id) VALUES (459,'Basketball',458);
INSERT INTO themes(id, name, parent_id) VALUES (460,'Gravity Games',458);
INSERT INTO themes(id, name, parent_id) VALUES (461,'Hockey',458);
INSERT INTO themes(id, name, parent_id) VALUES (462,'Soccer',458);
INSERT INTO themes(id, name, parent_id) VALUES (466,'Airport',465);
INSERT INTO themes(id, name, parent_id) VALUES (467,'Basic',465);
INSERT INTO themes(id, name, parent_id) VALUES (468,'Basic Model',465);
INSERT INTO themes(id, name, parent_id) VALUES (469,'Basic Set',465);
INSERT INTO themes(id, name, parent_id) VALUES (470,'Classic Basic',465);
INSERT INTO themes(id, name, parent_id) VALUES (471,'Ferries',465);
INSERT INTO themes(id, name, parent_id) VALUES (472,'Gears',465);
INSERT INTO themes(id, name, parent_id) VALUES (473,'Supplemental',465);
INSERT INTO themes(id, name, parent_id) VALUES (476,'Cowboys',475);
INSERT INTO themes(id, name, parent_id) VALUES (477,'Indians',475);
INSERT INTO themes(id, name, parent_id) VALUES (498,'Technic',497);
INSERT INTO themes(id, name, parent_id) VALUES (499,'Train',497);
INSERT INTO themes(id, name, parent_id) VALUES (502,'Game',501);
INSERT INTO themes(id, name, parent_id) VALUES (503,'Key Chain',501);
INSERT INTO themes(id, name, parent_id) VALUES (505,'Basic Set',504);
INSERT INTO themes(id, name, parent_id) VALUES (506,'Cars',504);
INSERT INTO themes(id, name, parent_id) VALUES (509,'Adventurers',507);
INSERT INTO themes(id, name, parent_id) VALUES (510,'Boat',507);
INSERT INTO themes(id, name, parent_id) VALUES (511,'Building Set with People',507);
INSERT INTO themes(id, name, parent_id) VALUES (512,'Castle',507);
INSERT INTO themes(id, name, parent_id) VALUES (513,'Classic',507);
INSERT INTO themes(id, name, parent_id) VALUES (514,'Creator',507);
INSERT INTO themes(id, name, parent_id) VALUES (515,'Dinosaurs',507);
INSERT INTO themes(id, name, parent_id) VALUES (516,'Duplo and Explore',507);
INSERT INTO themes(id, name, parent_id) VALUES (517,'Learning',507);
INSERT INTO themes(id, name, parent_id) VALUES (518,'Mindstorms',507);
INSERT INTO themes(id, name, parent_id) VALUES (519,'NXT',518);
INSERT INTO themes(id, name, parent_id) VALUES (520,'RCX',518);
INSERT INTO themes(id, name, parent_id) VALUES (521,'WeDo',518);
INSERT INTO themes(id, name, parent_id) VALUES (523,'Samsonite',507);
INSERT INTO themes(id, name, parent_id) VALUES (524,'Service Packs',507);
INSERT INTO themes(id, name, parent_id) VALUES (525,'Technic',524);
INSERT INTO themes(id, name, parent_id) VALUES (526,'Soft Bricks',507);
INSERT INTO themes(id, name, parent_id) VALUES (527,'Space',507);
INSERT INTO themes(id, name, parent_id) VALUES (528,'Supplemental',507);
INSERT INTO themes(id, name, parent_id) VALUES (529,'Technic',507);
INSERT INTO themes(id, name, parent_id) VALUES (530,'Control Lab',529);
INSERT INTO themes(id, name, parent_id) VALUES (531,'eLAB',529);
INSERT INTO themes(id, name, parent_id) VALUES (532,'Supplemental',529);
INSERT INTO themes(id, name, parent_id) VALUES (533,'Town',507);
INSERT INTO themes(id, name, parent_id) VALUES (534,'Universal Building Set',507);
INSERT INTO themes(id, name, parent_id) VALUES (536,'Series 1 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (537,'Series 2 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (538,'Series 3 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (539,'Series 4 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (540,'Series 5 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (541,'Series 6 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (542,'Series 7 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (543,'Series 8 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (544,'Series 9 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (545,'Series 10 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (546,'Team GB',535);
INSERT INTO themes(id, name, parent_id) VALUES (547,'Series 11 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (548,'Series 12 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (549,'The LEGO Movie Series',535);
INSERT INTO themes(id, name, parent_id) VALUES (550,'The Simpsons',535);
INSERT INTO themes(id, name, parent_id) VALUES (551,'Series 13 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (552,'Series 14 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (553,'The Simpsons Series 2',535);
INSERT INTO themes(id, name, parent_id) VALUES (554,'Series 15 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (555,'Disney Series 1',535);
INSERT INTO themes(id, name, parent_id) VALUES (556,'Series 16 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (557,'DFB Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (562,'The Hobbit',561);
INSERT INTO themes(id, name, parent_id) VALUES (566,'The Lord of the Rings',561);
INSERT INTO themes(id, name, parent_id) VALUES (572,'Speedorz',571);
INSERT INTO themes(id, name, parent_id) VALUES (573,'Constraction',571);
INSERT INTO themes(id, name, parent_id) VALUES (574,'Legend Beasts',571);
INSERT INTO themes(id, name, parent_id) VALUES (581,'Series 1',580);
INSERT INTO themes(id, name, parent_id) VALUES (582,'Series 2',580);
INSERT INTO themes(id, name, parent_id) VALUES (583,'Series 3',580);
INSERT INTO themes(id, name, parent_id) VALUES (584,'Series 4',580);
INSERT INTO themes(id, name, parent_id) VALUES (585,'Series 5',580);
INSERT INTO themes(id, name, parent_id) VALUES (586,'Series 6',580);
INSERT INTO themes(id, name, parent_id) VALUES (587,'Series 7',580);
INSERT INTO themes(id, name, parent_id) VALUES (588,'Series 8',580);
INSERT INTO themes(id, name, parent_id) VALUES (589,'Series 9',580);
INSERT INTO themes(id, name, parent_id) VALUES (592,'DC Comics Super Heroes',591);
INSERT INTO themes(id, name, parent_id) VALUES (593,'Disney Princess',591);
INSERT INTO themes(id, name, parent_id) VALUES (595,'Friends',591);
INSERT INTO themes(id, name, parent_id) VALUES (596,'Marvel Super Heroes',591);
INSERT INTO themes(id, name, parent_id) VALUES (597,'Ninjago',591);
INSERT INTO themes(id, name, parent_id) VALUES (609,'The LEGO Batman Movie Series 1',535);
INSERT INTO themes(id, name, parent_id) VALUES (611,'Series 17 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (614,'Jungle',52);
INSERT INTO themes(id, name, parent_id) VALUES (615,'Cars',591);
INSERT INTO themes(id, name, parent_id) VALUES (616,'Ninjago The Movie',435);
INSERT INTO themes(id, name, parent_id) VALUES (618,'Ninjago The Movie',535);
INSERT INTO themes(id, name, parent_id) VALUES (619,'Cities of Wonders',408);
INSERT INTO themes(id, name, parent_id) VALUES (620,'Jurassic World: Fallen Kingdom',591);
INSERT INTO themes(id, name, parent_id) VALUES (622,'Series 18 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (626,'Bob the Builder',504);
INSERT INTO themes(id, name, parent_id) VALUES (627,'Disney Planes',504);
INSERT INTO themes(id, name, parent_id) VALUES (628,'Dora the Explorer',504);
INSERT INTO themes(id, name, parent_id) VALUES (629,'Miles From Tomorrowland',504);
INSERT INTO themes(id, name, parent_id) VALUES (630,'Spiderman',504);
INSERT INTO themes(id, name, parent_id) VALUES (631,'Thomas & Friends',504);
INSERT INTO themes(id, name, parent_id) VALUES (632,'Town',504);
INSERT INTO themes(id, name, parent_id) VALUES (633,'Toy Story',504);
INSERT INTO themes(id, name, parent_id) VALUES (634,'Trains',504);
INSERT INTO themes(id, name, parent_id) VALUES (635,'Winnie the Pooh',504);
INSERT INTO themes(id, name, parent_id) VALUES (636,'My Town',632);
INSERT INTO themes(id, name, parent_id) VALUES (638,'Jake and the Never Land Pirates',504);
INSERT INTO themes(id, name, parent_id) VALUES (639,'Sofia the First',504);
INSERT INTO themes(id, name, parent_id) VALUES (640,'Disney Princess',504);
INSERT INTO themes(id, name, parent_id) VALUES (641,'Disney''s Mickey Mouse',504);
INSERT INTO themes(id, name, parent_id) VALUES (647,'Castle',504);
INSERT INTO themes(id, name, parent_id) VALUES (648,'Doc McStuffins',504);
INSERT INTO themes(id, name, parent_id) VALUES (650,'Legoville',632);
INSERT INTO themes(id, name, parent_id) VALUES (651,'Pirates',504);
INSERT INTO themes(id, name, parent_id) VALUES (652,'Playhouse',504);
INSERT INTO themes(id, name, parent_id) VALUES (653,'DC Comics',504);
INSERT INTO themes(id, name, parent_id) VALUES (655,'Powered Up',438);
INSERT INTO themes(id, name, parent_id) VALUES (656,'Harry Potter and Fantastic Beasts Series 1',535);
INSERT INTO themes(id, name, parent_id) VALUES (657,'Action Wheelers',504);
INSERT INTO themes(id, name, parent_id) VALUES (658,'Dino',504);
INSERT INTO themes(id, name, parent_id) VALUES (659,'Dolls',504);
INSERT INTO themes(id, name, parent_id) VALUES (660,'Little Forest Friends',504);
INSERT INTO themes(id, name, parent_id) VALUES (661,'Little Robots',504);
INSERT INTO themes(id, name, parent_id) VALUES (662,'Princess Castle',504);
INSERT INTO themes(id, name, parent_id) VALUES (663,'Rattles',504);
INSERT INTO themes(id, name, parent_id) VALUES (664,'Toolo',504);
INSERT INTO themes(id, name, parent_id) VALUES (665,'Western',504);
INSERT INTO themes(id, name, parent_id) VALUES (666,'Zooters',504);
INSERT INTO themes(id, name, parent_id) VALUES (667,'Fantastic Beasts',246);
INSERT INTO themes(id, name, parent_id) VALUES (670,'The LEGO Movie II',578);
INSERT INTO themes(id, name, parent_id) VALUES (672,'Creator 3-in-1',22);
INSERT INTO themes(id, name, parent_id) VALUES (673,'Creator Expert',22);
INSERT INTO themes(id, name, parent_id) VALUES (674,'Early Creator',22);
INSERT INTO themes(id, name, parent_id) VALUES (675,'The LEGO Movie Series II',535);
INSERT INTO themes(id, name, parent_id) VALUES (677,'Disney Series 2',535);
INSERT INTO themes(id, name, parent_id) VALUES (679,'Mars Exploration',52);
INSERT INTO themes(id, name, parent_id) VALUES (681,'Series 19 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (684,'DC Super Heroes',535);
INSERT INTO themes(id, name, parent_id) VALUES (687,'Frozen II',608);
INSERT INTO themes(id, name, parent_id) VALUES (691,'Harry Potter and Fantastic Beasts Series 2',535);
INSERT INTO themes(id, name, parent_id) VALUES (692,'Series 20 Minifigures',535);
INSERT INTO themes(id, name, parent_id) VALUES (697,'Batman',695);
INSERT INTO themes(id, name, parent_id) VALUES (698,'UCS',697);
INSERT INTO themes(id, name, parent_id) VALUES (699,'Constraction',695);
INSERT INTO themes(id, name, parent_id) VALUES (700,'Justice League',695);
INSERT INTO themes(id, name, parent_id) VALUES (701,'Superman',695);
INSERT INTO themes(id, name, parent_id) VALUES (702,'Avengers',696);
INSERT INTO themes(id, name, parent_id) VALUES (703,'Constraction',696);
INSERT INTO themes(id, name, parent_id) VALUES (704,'Guardians of the Galaxy',696);
INSERT INTO themes(id, name, parent_id) VALUES (705,'Iron Man',696);
INSERT INTO themes(id, name, parent_id) VALUES (706,'Spider-Man',696);
INSERT INTO themes(id, name, parent_id) VALUES (707,'X-Men',696);
INSERT INTO themes(id, name, parent_id) VALUES (708,'The LEGO Batman Movie',697);
INSERT INTO themes(id, name, parent_id) VALUES (710,'Harry Potter',207);
INSERT INTO themes(id, name, parent_id) VALUES (711,'The LEGO Batman Movie Series 2',535);

COMMIT;