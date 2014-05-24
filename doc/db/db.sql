
drop database if exists `ams`;
create database `ams`;
use ams;

CREATE TABLE `User` (
  `id` varchar(36) NOT NULL,
  `userName` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,  
  `userCategory` varchar(255) DEFAULT NULL,
  `mobileNumber` varchar(36) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
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


CREATE TABLE `Attendance` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `operatorId` varchar(36) DEFAULT NULL,
  `teamId` varchar(36) DEFAULT NULL,
  `attendanceTimeSelectType` int DEFAULT 0,
  `time` double DEFAULT NULL,
  `attendanceDate` DATE DEFAULT NULL,
  `attendanceType` int DEFAULT 0,
  `attendanceDayType` int DEFAULT 0,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Salary` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `totalSalary` double DEFAULT NULL,
  `deductedSalary` double DEFAULT NULL,
  `remainingSalaray` double DEFAULT NULL,
  `year` int DEFAULT 0,
  `month` int DEFAULT 0,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `SalaryItem` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `salaryId` varchar(36) DEFAULT NULL,
  `dispayOrder` int default 0,
  `projectId` varchar(36) DEFAULT NULL,
  `attendanceDays` double DEFAULT 0,
  `totolSalary` double DEFAULT 0,
  `performanceSalary` double DEFAULT 0,
  `performanceSalaryUnit` double DEFAULT 0,
  `comment` varchar(255) DEFAULT NULL,
  `year` int DEFAULT 0,
  `month` int DEFAULT 0,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `DeductedSalaryItem` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `salaryId` varchar(36) DEFAULT NULL,
  `dispayOrder` int default 0,
  `projectId` varchar(36) DEFAULT NULL,
  `totolSalary` double DEFAULT 0,
  `comment` varchar(255) DEFAULT NULL,
  `year` int DEFAULT 0,
  `month` int DEFAULT 0,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `Notice` (
  `id` varchar(36) NOT NULL,
  `title` varchar(255) NOT NULL,
  `attachFileUrl` varchar(255) DEFAULT NULL,
  `content` TEXT DEFAULT NULL,
  `publishDate` date DEFAULT NULL,
  `publishEndDate` datetime DEFAULT NULL,
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
  `departmentManagerId` varchar(36) NOT NULL,
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


CREATE TABLE `Pic` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `dailyReportId` varchar(36) DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  `picUrl` varchar(255) NOT NULL,
  `uploadAddress` varchar(255) DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
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



CREATE TABLE `Project` (
  `id` varchar(36) NOT NULL,
  `departmentId` varchar(36) DEFAULT NULL,
  `customerId` varchar(36) DEFAULT NULL,
  `projectName` varchar(255) NOT NULL,
  `projectManagerId` varchar(36) DEFAULT NULL,
  `projectDescription` TEXT DEFAULT NULL,
  `projectStatus` int DEFAULT 0,
  `projectStartDate` date DEFAULT NULL,
  `projectEndDate` date DEFAULT NULL,  
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `DailyReport` (
  `id` varchar(36) NOT NULL,
  `projectId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `taskId` varchar(36) default null
  `weather` varchar(255) NOT NULL,
  `materialRecord` TEXT DEFAULT NULL,
  `workingRecord` TEXT DEFAULT NULL,
  `summary` TEXT DEFAULT NULL,
  `plan` TEXT DEFAULT NULL,
  `reportDay` date DEFAULT NULL,
  `isCommented` tinyint DEFAULT 0,  
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Task` (
  `id` varchar(36) NOT NULL,
  `projectId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `teamId` varchar(36) default null,
  `unit` varchar(36) default null,
  `price` double default 0,
  `amount` double default 0,
  `amountDescription` varchar(36) default null,
  `priceDescription` varchar(36) default null,
  `taskName` varchar(255) NOT NULL,
  `teamName` varchar(255) default NULL,
  `taskContactPhone` varchar(255) default NULL,
  `taskPeriod` varchar(255) default NULL,
  `description` TEXT DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  `projectStartDate` date DEFAULT NULL,
  `projectEndDate` date DEFAULT NULL,
  `displayOrder` int(11) DEFAULT 0,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `DailyReportView` (
  `id` varchar(36) NOT NULL,
  `dailyReportId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `DailyReportComment` (
  `id` varchar(36) NOT NULL,
  `dailyReportId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `comment` varchar(255) NOT NULL,
  `commentDate` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE TABLE `EmployeeTeam` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `teamId` varchar(36) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `EmployeeProject` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `projectId` varchar(36) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `UserType` (
  `id` varchar(36) NOT NULL,
  `typeName` varchar(255) DEFAULT NULL,
  `typeDescription` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `UserLevel` (
  `id` varchar(36) NOT NULL,
  `levelName` varchar(255) DEFAULT NULL,
  `levelDescription` varchar(255) DEFAULT NULL,
  `userTypeId` varchar(36) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




alter table dailyReport add column taskId varchar(36) default null;
INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`) VALUES ('05c07bcc-833e-4b22-a8be-3c3a63609ac8','admin','96e79218965eb72c92a549dd5a330112',now(),now());
update User set mobileNumber="11111111", userCode="A000001";


alter table Attendance add column hours int default 0;
alter table Attendance add column minutes int default 0;
alter table department add column departmentManagerId varchar(36) default null;

alter table notice add column priority int default 0;
alter table notice add column publisherId varchar(36) default null;
alter table notice add column publishEndDate datetime default null;

alter table project add column customerId varchar(36) default null;

INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`) VALUES ('12c07bcc-833e-4b22-a8be-3c3a63609ac8','dylan','96e79218965eb72c92a549dd5a330112',now(),now());



alter table User add column bstatus int default 0;


alter table User add column address varchar(255) default null;
alter table User add column userCategory varchar(255) default null;

