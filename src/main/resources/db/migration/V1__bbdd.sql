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
  `date_of_birth` DATE NULL,
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
  `postal_code` VARCHAR(45) NULL,
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
-- Table `javaschool`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `payment_method` VARCHAR(45) NULL,
  `delivery_method` VARCHAR(45) NULL,
  `payment_status` VARCHAR(45) NULL,
  `order_status` VARCHAR(45) NULL,
  `client_id` INT NOT NULL,
  `clients_address_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_client_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_orders_clients_address_idx` (`clients_address_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `javaschool`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_clients_address`
    FOREIGN KEY (`clients_address_id`)
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
  `quantity_stock` INTEGER NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `javaschool`.`order_has_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `javaschool`.`order_has_product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orders_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_has_product_product_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_has_product_orders_idx` (`orders_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_product_orders`
    FOREIGN KEY (`orders_id`)
    REFERENCES `javaschool`.`orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_product_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `javaschool`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

