DROP SCHEMA IF EXISTS `auction` ;
CREATE SCHEMA IF NOT EXISTS `auction` ;
CREATE TABLE IF NOT EXISTS `auction`.`user` (
  `id` BIGINT NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(25) NULL DEFAULT NULL,
  `username` VARCHAR(25) NULL DEFAULT NULL,
  `activated` BIT(1) NOT NULL,
  `activation_code` VARCHAR(255) NULL DEFAULT NULL,
  `balance` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `auction`.`role` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `auction`.`user_roles` (
  `user_id` BIGINT NOT NULL,
  `roles_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `roles_id`),
  INDEX `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id` ASC) VISIBLE,
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k`
    FOREIGN KEY (`user_id`)
    REFERENCES `auction`.`user` (`id`),
  CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d`
    FOREIGN KEY (`roles_id`)
    REFERENCES `auction`.`role` (`id`));

CREATE TABLE IF NOT EXISTS `auction`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL);

CREATE TABLE IF NOT EXISTS `auction`.`lot` (
  `id` BIGINT NOT NULL,
  `active` BIT(1) NOT NULL,
  `name` VARCHAR(255) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL,
  `owner_id` BIGINT NULL DEFAULT NULL,
  `current_price` INT NOT NULL,
  `redemption_price` INT NOT NULL,
  `start_date` DATETIME(1) NULL DEFAULT NULL,
  `end_date` DATETIME(1) NULL DEFAULT NULL,
  `description` VARCHAR(500) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL,
  `main_img` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `auction`.`subscribe` (
  `id` BIGINT NOT NULL,
  `lot_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_subscribe_lot` (`lot_id` ASC) VISIBLE,
  INDEX `FK_subscribe_user` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_subscribe_lot`
    FOREIGN KEY (`lot_id`)
    REFERENCES `auction`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_subscribe_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `auction`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `auction`.`image` (
  `id` BIGINT NOT NULL,
  `lot_id` BIGINT NULL DEFAULT NULL,
  `img_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_image_lot` (`lot_id` ASC) VISIBLE,
  CONSTRAINT `FK_image_lot`
    FOREIGN KEY (`lot_id`)
    REFERENCES `auction`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `auction`.`comment` (
  `id` BIGINT NOT NULL,
  `lot_id` BIGINT NULL DEFAULT NULL,
  `username` VARCHAR(25) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL,
  `comment` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_comment_lot` (`lot_id` ASC) VISIBLE,
  CONSTRAINT `FK_comment_lot`
    FOREIGN KEY (`lot_id`)
    REFERENCES `auction`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `auction`.`bet` (
  `id` BIGINT NOT NULL,
  `lot_id` BIGINT NULL DEFAULT NULL,
  `owner_id` BIGINT NULL DEFAULT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bet_lot` (`lot_id` ASC) VISIBLE,
  CONSTRAINT `FK_bet_lot`
    FOREIGN KEY (`lot_id`)
    REFERENCES `auction`.`lot` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    INSERT INTO `auction`.`hibernate_sequence` (`next_val`) VALUES ('10');
    INSERT INTO `auction`.`role` (`id`, `name`) VALUES ('1', 'USER');
    INSERT INTO `auction`.`role` (`id`, `name`) VALUES ('2', 'ADMIN');
    
    
