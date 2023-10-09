-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema javaschool
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `javaschool` DEFAULT CHARACTER SET utf8mb4 ;
USE `javaschool`;

-- -----------------------------------------------------
-- Table `javaschool`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `dateOfBirth` DATE NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `javaschool`.`clients_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`clients_address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `postalCode` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `home` VARCHAR(45) NULL,
  `apartment` VARCHAR(45) NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_clients_address_client_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_clients_address_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `javaschool`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `javaschool`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `paymentMethod` VARCHAR(45) NULL,
  `deliveryMethod` VARCHAR(45) NULL,
  `paymentStatus` VARCHAR(45) NULL,
  `orderStatus` VARCHAR(45) NULL,
  `client_id` INT NOT NULL,
  `client_address_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_client_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_order_clients_address_idx` (`client_address_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `javaschool`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_clients_address`
    FOREIGN KEY (`client_address_id`)
    REFERENCES `javaschool`.`clients_address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `javaschool`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `price` DECIMAL(10,2) NULL,
  `category` VARCHAR(45) NULL,
  `parameters` VARCHAR(45) NULL,
  `weight` DECIMAL(10,2) NULL,
  `volume` DECIMAL(10,2) NULL,
  `quantityStock` INT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `javaschool`.`order_has_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`order_has_product` (
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`order_id`, `product_id`),
  INDEX `fk_order_has_product_product_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_has_product_order_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_product_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `javaschool`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_product_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `javaschool`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO `javaschool`.`client` (`name`, `surname`, `dateOfBirth`, `email`, `password`)
VALUES
    ('Alberto', 'Martín', '1999-07-31', 'alberto@example.com', 'password123'),
    ('María', 'Jimenez', '1985-03-20', 'maria@example.com', 'password321'),
    ('Juan', 'García', '1995-07-10', 'pedro@example.com', 'password456');

INSERT INTO `javaschool`.`clients_address` (`country`, `city`, `postalCode`, `street`, `home`, `apartment`, `client_id`)
VALUES
	('Spain', 'Madrid', '28001', 'Calle de Serrano', 'apartment','45B', 1),
    ('United States', 'New York', '10001', 'Main Street', 'apartment','123A', 2),
    ('France', 'Paris', '75008', 'Champs-Élysées', 'house','578', 3);
    
INSERT INTO `javaschool`.`order` (`paymentMethod`, `deliveryMethod`, `paymentStatus`, `orderStatus`, `client_id`, `client_address_id`)
VALUES
	('Credit Card', 'Express Shipping', 'Paid', 'Processing', 1, 2);


INSERT INTO `javaschool`.`product` (`title`, `price`, `category`, `parameters`, `weight`, `volume`, `quantityStock`)
VALUES
    ('MIDI Piano', 200, 'Musical Instruments', 'Type: MIDI, Color: Silver', 15, 0, 5),
    ('Modern Desk Lamp', 50, 'Home & Living', 'Power Source: Electric', 1, 0, 20),
    ('Rolex', 10000, 'Fashion', 'Material: Stainless Steel', 0, 0, 10);
 
 
INSERT INTO `javaschool`.`order_has_product` (`order_id`, `product_id`, `quantity`)
VALUES
    (1, 1, 3),
    (2, 2, 5), 
    (3, 3, 10); 
    
    
-- SHOW TABLES;
 SELECT * FROM `javaschool`.`client`;
 SELECT * FROM `javaschool`.`clients_address`;
 SELECT * FROM `javaschool`.`order`;
 SELECT * FROM `javaschool`.`product`;
 SELECT * FROM `javaschool`.`order_has_product`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;