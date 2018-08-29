-- ****************** SqlDBM: MySQL ******************;
-- ***************************************************;

DROP TABLE `response`;


DROP TABLE `request`;


DROP TABLE `coordinator`;


DROP TABLE `course`;


DROP TABLE `student`;


DROP TABLE `semester`;


DROP TABLE `user`;



-- ************************************** `semester`

CREATE TABLE `semester`
(
 `year`               YEAR NOT NULL ,
 `registration_start` DATE NOT NULL ,
 `registration_end`   DATE NOT NULL ,

PRIMARY KEY (`year`, `registration_start`, `registration_end`)
);





-- ************************************** `user`

CREATE TABLE `user`
(
 `user_email` VARCHAR(45) NOT NULL ,
 `user_type`  ENUM('STUDENT, COORDINATOR') NOT NULL ,
 `user_pass`  VARCHAR(45) NOT NULL ,

PRIMARY KEY (`user_email`)
);





-- ************************************** `course`

CREATE TABLE `course`
(
 `course_code`        VARCHAR(45) NOT NULL ,
 `year`               YEAR NOT NULL ,
 `registration_start` DATE NOT NULL ,
 `registration_end`   DATE NOT NULL ,

PRIMARY KEY (`course_code`, `year`, `registration_start`, `registration_end`),
KEY `fkIdx_45` (`year`, `registration_start`, `registration_end`),
CONSTRAINT `FK_45` FOREIGN KEY `fkIdx_45` (`year`, `registration_start`, `registration_end`) REFERENCES `semester` (`year`, `registration_start`, `registration_end`)
);





-- ************************************** `student`

CREATE TABLE `student`
(
 `user_email` VARCHAR(45) NOT NULL ,

PRIMARY KEY (`user_email`),
KEY `fkIdx_19` (`user_email`),
CONSTRAINT `FK_19` FOREIGN KEY `fkIdx_19` (`user_email`) REFERENCES `user` (`user_email`)
);





-- ************************************** `request`

CREATE TABLE `request`
(
 `user_email`         VARCHAR(45) NOT NULL ,
 `year`               YEAR NOT NULL ,
 `course_code`        VARCHAR(45) NOT NULL ,
 `registration_start` DATE NOT NULL ,
 `registration_end`   DATE NOT NULL ,
 `request_form`       BLOB NOT NULL ,
 `request_approved`   TINYINT NOT NULL DEFAULT 0 ,

PRIMARY KEY (`user_email`, `year`, `course_code`, `registration_start`, `registration_end`),
KEY `fkIdx_26` (`user_email`),
CONSTRAINT `FK_26` FOREIGN KEY `fkIdx_26` (`user_email`) REFERENCES `student` (`user_email`),
KEY `fkIdx_36` (`year`, `registration_start`, `registration_end`),
CONSTRAINT `FK_36` FOREIGN KEY `fkIdx_36` (`year`, `registration_start`, `registration_end`) REFERENCES `semester` (`year`, `registration_start`, `registration_end`),
KEY `fkIdx_54` (`course_code`, `year`, `registration_start`, `registration_end`),
CONSTRAINT `FK_54` FOREIGN KEY `fkIdx_54` (`course_code`, `year`, `registration_start`, `registration_end`) REFERENCES `course` (`course_code`, `year`, `registration_start`, `registration_end`)
);





-- ************************************** `coordinator`

CREATE TABLE `coordinator`
(
 `user_email`         VARCHAR(45) NOT NULL ,
 `course_code`        VARCHAR(45) NOT NULL ,
 `year`               YEAR NOT NULL ,
 `registration_start` DATE NOT NULL ,
 `registration_end`   DATE NOT NULL ,

PRIMARY KEY (`user_email`, `course_code`, `year`, `registration_start`, `registration_end`),
KEY `fkIdx_10` (`user_email`),
CONSTRAINT `FK_10` FOREIGN KEY `fkIdx_10` (`user_email`) REFERENCES `user` (`user_email`),
KEY `fkIdx_82` (`course_code`, `year`, `registration_start`, `registration_end`),
CONSTRAINT `FK_82` FOREIGN KEY `fkIdx_82` (`course_code`, `year`, `registration_start`, `registration_end`) REFERENCES `course` (`course_code`, `year`, `registration_start`, `registration_end`)
);





-- ************************************** `response`

CREATE TABLE `response`
(
 `user_email`         VARCHAR(45) NOT NULL ,
 `year`               YEAR NOT NULL ,
 `course_code`        VARCHAR(45) NOT NULL ,
 `registration_start` DATE NOT NULL ,
 `registration_end`   DATE NOT NULL ,
 `response_form`      BLOB ,

PRIMARY KEY (`user_email`, `year`, `course_code`, `registration_start`, `registration_end`),
KEY `fkIdx_61` (`user_email`, `year`, `course_code`, `registration_start`, `registration_end`),
CONSTRAINT `FK_61` FOREIGN KEY `fkIdx_61` (`user_email`, `year`, `course_code`, `registration_start`, `registration_end`) REFERENCES `request` (`user_email`, `year`, `course_code`, `registration_start`, `registration_end`),
KEY `fkIdx_69` (`user_email`, `course_code`, `year`, `registration_start`, `registration_end`),
CONSTRAINT `FK_69` FOREIGN KEY `fkIdx_69` (`user_email`, `course_code`, `year`, `registration_start`, `registration_end`) REFERENCES `coordinator` (`user_email`, `course_code`, `year`, `registration_start`, `registration_end`)
);





