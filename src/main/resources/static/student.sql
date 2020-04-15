CREATE TABLE `student` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
	`note` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;