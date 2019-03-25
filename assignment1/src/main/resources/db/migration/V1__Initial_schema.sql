CREATE TABLE `tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

  CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `banned` BIT NULL,
  `score` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));

CREATE TABLE `question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `text` VARCHAR(45) NOT NULL,
  `creation_date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `question_tag` (
  `questionID` INT NOT NULL,
  `tagID` INT NOT NULL,
  PRIMARY KEY (`questionID`, `tagID`),
  INDEX `tag_question_idx` (`tagID` ASC),
  CONSTRAINT `question_tag`
    FOREIGN KEY (`questionID`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `tag_question`
    FOREIGN KEY (`tagID`)
    REFERENCES `tag` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NOT NULL,
  `text` VARCHAR(45) NOT NULL,
  `creation_date_time` DATETIME NOT NULL,
  `questionID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `answer_question_idx` (`questionID` ASC),
  CONSTRAINT `answer_question`
    FOREIGN KEY (`questionID`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `vote` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` INT NOT NULL,
  `userID` INT NOT NULL,
  `questionID` INT NULL,
  `answerID` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_vote_idx` (`userID` ASC),
  INDEX `vote_question_idx` (`questionID` ASC),
  INDEX `vote_answer_idx` (`answerID` ASC),
  CONSTRAINT `user_vote`
    FOREIGN KEY (`userID`)
    REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `vote_question`
    FOREIGN KEY (`questionID`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `vote_answer`
    FOREIGN KEY (`answerID`)
    REFERENCES `answer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
