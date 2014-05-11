
drop database if exists `ams`;
create database `ams`;
use ams;

CREATE TABLE `User` (
  `id` varchar(36) NOT NULL,
  `userName` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `mobileNumber` varchar(36) DEFAULT NULL,
  `userTypeId` varchar(36) DEFAULT NULL,
  `userCode` varchar(36) DEFAULT NULL,
  `userLevelId` varchar(36) DEFAULT NULL,  
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Notice` (
  `id` varchar(36) NOT NULL,
  `title` varchar(255) NOT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `attachFileUrl` varchar(255) DEFAULT NULL,
  `content` TEXT DEFAULT NULL,
  `publishDate` date DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Reminder` (
  `id` varchar(36) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` TEXT DEFAULT NULL,
  `remindDate` datetime DEFAULT NULL,
  `userId` varchar(36) NOT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Department` (
  `id` varchar(36) NOT NULL,
  `departmentName` varchar(255) NOT NULL,
  `departmentDescription` TEXT DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Team` (
  `id` varchar(36) NOT NULL,
  `teamName` varchar(255) NOT NULL,
  `teamLeaderId` varchar(36) NOT NULL,
  `projectId` varchar(36) NOT NULL,
  `departmentId` varchar(36) NOT NULL,
  `teamDescription` TEXT DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Customer` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `contactPerson` varchar(36) NOT NULL,
  `position` varchar(36) NOT NULL,
  `contactMobileNumber` varchar(36) NOT NULL,
  `remark` TEXT DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `SystemConfig` (
  `id` varchar(36) NOT NULL,
  `configId` varchar(255) NOT NULL,
  `cfgValue` varchar(255) NOT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`) VALUES ('05c07bcc-833e-4b22-a8be-3c3a63609ac8','admin','96e79218965eb72c92a549dd5a330112',now(),now());
update User set mobileNumber="11111111", userCode="A000001";
