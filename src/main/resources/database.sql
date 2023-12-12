-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema clothes_store
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `clothes_store` ;

-- -----------------------------------------------------
-- Schema clothes_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clothes_store` DEFAULT CHARACTER SET utf8 ;
USE `clothes_store` ;

-- -----------------------------------------------------
-- Table `clothes_store`.`clothing`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`clothing` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`clothing` (
                                                          `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                          `name` VARCHAR(45) NOT NULL,
                                                          `size` ENUM('XS', 'S', 'M', 'L', 'XL', 'XXL') NOT NULL,
                                                          `color` VARCHAR(45) NOT NULL,
                                                          `season` ENUM('WINTER', 'SUMMER', 'FALL', 'SPRING') NOT NULL,
                                                          `amount` INT NOT NULL,
                                                          `actual_price` DECIMAL(8,2) NOT NULL,
                                                          `sex` ENUM('MALE', 'FEMALE', 'UNISEX') NOT NULL,
                                                          PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clothes_store`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`user` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`user` (
                                                      `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                      `name` VARCHAR(30) NOT NULL,
                                                      `surname` VARCHAR(30) NOT NULL,
                                                      `email` VARCHAR(45) NOT NULL,
                                                      `password` VARCHAR(45) NOT NULL,
                                                      `phone` VARCHAR(13) NOT NULL,
                                                      `role` ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
                                                      PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clothes_store`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`order` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`order` (
                                                       `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                       `datetime` TIMESTAMP NOT NULL DEFAULT NOW(),
                                                       `status` ENUM('PROCESSING', 'ACCEPTED', 'SENT', 'DELIVERED') NOT NULL DEFAULT 'PROCESSING',
                                                       PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clothes_store`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`user_order` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`user_order` (
                                                            `order_id` INT UNSIGNED NOT NULL,
                                                            `user_id` INT UNSIGNED NOT NULL,
                                                            `description` VARCHAR(100) NULL,
                                                            `datetime` TIMESTAMP NOT NULL DEFAULT NOW(),
                                                            INDEX `fk_table1_order_idx` (`order_id` ASC) VISIBLE,
                                                            INDEX `fk_table1_user1_idx` (`user_id` ASC) VISIBLE,
                                                            PRIMARY KEY (`order_id`, `user_id`),
                                                            CONSTRAINT `fk_table1_order`
                                                                FOREIGN KEY (`order_id`)
                                                                    REFERENCES `clothes_store`.`order` (`id`)
                                                                    ON DELETE RESTRICT
                                                                    ON UPDATE CASCADE,
                                                            CONSTRAINT `fk_table1_user1`
                                                                FOREIGN KEY (`user_id`)
                                                                    REFERENCES `clothes_store`.`user` (`id`)
                                                                    ON DELETE RESTRICT
                                                                    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clothes_store`.`clothing_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`clothing_order` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`clothing_order` (
                                                                `clothing_id` INT UNSIGNED NOT NULL,
                                                                `order_id` INT UNSIGNED NOT NULL,
                                                                `amount` INT NOT NULL,
                                                                `current_price` DECIMAL(8,2) NOT NULL,
                                                                INDEX `fk_table2_clothing1_idx` (`clothing_id` ASC) VISIBLE,
                                                                INDEX `fk_table2_order1_idx` (`order_id` ASC) VISIBLE,
                                                                PRIMARY KEY (`clothing_id`, `order_id`),
                                                                CONSTRAINT `fk_table2_clothing1`
                                                                    FOREIGN KEY (`clothing_id`)
                                                                        REFERENCES `clothes_store`.`clothing` (`id`)
                                                                        ON DELETE RESTRICT
                                                                        ON UPDATE CASCADE,
                                                                CONSTRAINT `fk_table2_order1`
                                                                    FOREIGN KEY (`order_id`)
                                                                        REFERENCES `clothes_store`.`order` (`id`)
                                                                        ON DELETE RESTRICT
                                                                        ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clothes_store`.`delivery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clothes_store`.`delivery` ;

CREATE TABLE IF NOT EXISTS `clothes_store`.`delivery` (
                                                          `order_id` INT UNSIGNED NOT NULL,
                                                          `city` VARCHAR(45) NOT NULL,
                                                          `street` VARCHAR(45) NOT NULL,
                                                          `house_number` VARCHAR(6) NOT NULL,
                                                          `entrance` INT NULL,
                                                          `apartment_number` INT NULL,
                                                          INDEX `fk_delivery_order1_idx` (`order_id` ASC) VISIBLE,
                                                          PRIMARY KEY (`order_id`),
                                                          CONSTRAINT `fk_delivery_order1`
                                                              FOREIGN KEY (`order_id`)
                                                                  REFERENCES `clothes_store`.`order` (`id`)
                                                                  ON DELETE CASCADE
                                                                  ON UPDATE CASCADE)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


# INSERT INTO user (name, surname, email, password, phone)
# VALUES ('Іван', 'Петров', 'ivan@example.com', 'пароль123', '+380123456789'),
#        ('Марія', 'Іванова', 'maria@example.com', 'пароль456', '+380987654321'),
#        ('Олег', 'Сидоров', 'oleg@example.com', 'пароль789', '+380777888999');
# INSERT INTO user (name, surname, email, password, phone, role)
# VALUES ('Адміністратор', 'Адміністраторович', 'admin@example.com', 'admin123', '+380111222333', 'ADMIN');
#
#
# INSERT INTO clothing (name, size, color, season, amount, actual_price, sex)
# VALUES ('Футболка 1', 'M', 'Червона', 'SUMMER', 10, 19.99, 'UNISEX'),
#        ('Шорти', 'L', 'Сині', 'SUMMER', 20, 29.99, 'MALE'),
#        ('Сукня', 'M', 'Рожева', 'SPRING', 15, 49.99, 'FEMALE'),
#        ('Куртка', 'XL', 'Чорна', 'WINTER', 8, 79.99, 'UNISEX'),
#        ('Футболка', 'M', 'Червона', 'SUMMER', 10, 19.99, 'UNISEX');
#
# INSERT INTO `order`( datetime, status) VALUES (DEFAULT, default);
# INSERT INTO `order`( datetime, status) VALUES (DEFAULT, default);
# INSERT INTO `order`( datetime, status) VALUES (DEFAULT, default);
#
# INSERT INTO user_order (order_id, user_id, description, datetime)
# VALUES (1, 1, 'Замовлення 1', NOW()),
#        (2, 2, 'Замовлення 2', NOW());
# INSERT INTO clothing_order (clothing_id, order_id, amount, current_price)
# VALUES (1, 1, 5, 99.95),
#        (2, 2, 3, 149.97);
# INSERT INTO delivery (order_id, city, street, house_number, entrance, apartment_number)
# VALUES (1, 'Київ', 'Головна', '123', 1, 101),
#        (2, 'Львів', 'Центральна', '456', 2, 202);