
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
  `groupId` varchar(36) DEFAULT NULL,
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
  `projectAttendanceManagerId` varchar(36) DEFAULT NULL,
  `projectName` varchar(255) NOT NULL,
  `workTimePeriod` varchar(255) NOT NULL,
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
  `taskId` varchar(36) default null,
  `weather` varchar(255) NOT NULL,
  `materialRecord` TEXT DEFAULT NULL,
  `workingRecord` TEXT DEFAULT NULL,
  `summary` TEXT DEFAULT NULL,
  `plan` TEXT DEFAULT NULL,
  `reportDay` date DEFAULT NULL,
  `isCommented` tinyint DEFAULT 0,  
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
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
  `creatorId` varchar(36) DEFAULT NULL,
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


CREATE TABLE `RoleGroup` (
  `id` varchar(36) NOT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `permissions` varchar(512) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `MenuItem` (
  `id` varchar(36) NOT NULL,
  `menuId` varchar(36) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `displayOrder` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Menu` (
  `id` varchar(36) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,  
  `menuGroupId` varchar(255)  DEFAULT NULL,  
  `dataOptions` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `displayOrder` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `CustomerContact` (
  `id` varchar(36) NOT NULL,
  `customerId` varchar(36) DEFAULT NULL,
  `contactPerson` varchar(255) DEFAULT NULL,  
  `position` varchar(255) DEFAULT NULL,
  `contactMobileNumber` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `ProjectTask` (
  `id` varchar(36) NOT NULL,
  `projectId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  `teamId` varchar(36) default null,
  `teamName` varchar(255) default NULL,
  `taskContactPhone` varchar(255) default NULL,
  `taskPeriod` varchar(255) default NULL,
  `description` TEXT DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  `projectStartDate` date DEFAULT NULL,
  `projectEndDate` date DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table Task add column projectTaskId varchar(36) default null;
alter table Task add column remark varchar(255) default null;




CREATE TABLE `AmsLog` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `dataId` varchar(36) NOT NULL,
  `tableName` varchar(36) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `urlPath` varchar(255) DEFAULT NULL,
  `logType` varchar(255) NOT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `LogItem` (
  `id` varchar(36) NOT NULL,
  `logId` varchar(36) NOT NULL,
  `field` varchar(36) DEFAULT NULL,
  `oldValue` varchar(255) DEFAULT NULL,
  `newValue` varchar(255) NOT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table AmsLog add column urlPath varchar(255) default null;
alter table LogItem add column tableName varchar(255) default null;
alter table AmsLog modify column dataId varchar(255) default null;
alter table LogItem modify column newValue varchar(255) default null;




alter table Task add column isDeleted boolean DEFAULT false;
alter table ProjectTask add column isDeleted boolean DEFAULT false;

alter table User add column isMultipleProject boolean DEFAULT false;
alter table User add column isMultipleTeam boolean DEFAULT false;















alter table User add column teamGroup varchar(255) default null;
alter table User add column idCard varchar(255) default null;
alter table CustomerContact add column remark varchar(255) default null;



INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`) VALUES ('05c07bcc-833e-4b22-a8be-3c3a63609ac8','admin','96e79218965eb72c92a549dd5a330112',now(),now());
update User set mobileNumber="11111111", userCode="A000001";
INSERT INTO `User`(`id`,`userName`,`password`,`createdOn`,`updatedOn`) VALUES ('12c07bcc-833e-4b22-a8be-3c3a63609ac8','dylan','96e79218965eb72c92a549dd5a330112',now(),now());

alter table dailyReport add column taskId varchar(36) default null;
alter table dailyReport add column taskId varchar(36) default null;

alter table Attendance add column hours int default 0;
alter table Attendance add column minutes int default 0;
alter table department add column departmentManagerId varchar(36) default null;

alter table notice add column priority int default 0;
alter table notice add column publisherId varchar(36) default null;
alter table notice add column publishEndDate datetime default null;

alter table project add column customerId varchar(36) default null;




alter table User add column bstatus int default 0;


alter table User add column address varchar(255) default null;
alter table User add column userCategory varchar(255) default null;
alter table Project add column workTimePeriod varchar(255) default null;
alter table User add column groupId varchar(36) default null;

alter table Project add column projectAttendanceManagerId varchar(36) default null;
alter table Attendance add column departmentId varchar(36) default null;

alter table Attendance add column projectId varchar(36) default null;

alter table Project modify column projectStatus varchar(255) default null;

alter table SalaryItem add column projectName varchar(255) default null;

alter table User modify column isAdmin boolean DEFAULT false;



alter table Attendance add column year int default 0;

alter table Attendance add column month int default 0;



alter table User add column remark TEXT default null;
alter table User add column reportManagerId varchar(36) default null;

alter table User add column displayForApp tinyint(1) default 1;
alter table User add column displayOrder int default 0;

alter table Customer add column displayForApp tinyint(1) default 1;
alter table Customer add column displayOrder int default 0;

alter table Project add column displayForApp tinyint(1) default 1;
alter table Project add column displayOrder int default 0;


alter table UserType add column displayForApp tinyint(1) default 1;
alter table UserType add column displayOrder int default 0;

alter table UserLevel add column displayForApp tinyint(1) default 1;
alter table UserLevel add column displayOrder int default 0;


alter table Department add column displayForApp tinyint(1) default 1;
alter table Department add column displayOrder int default 0;

alter table RoleGroup add column displayForApp tinyint(1) default 1;
alter table RoleGroup add column displayOrder int default 0;




alter table LogItem modify column newValue text default null;
alter table LogItem modify column oldValue text default null;


alter table Task modify column isDeleted tinyint(1) DEFAULT 0;
alter table ProjectTask modify column isDeleted tinyint(1) DEFAULT 0;





alter table Team add column displayForApp tinyint(1) default 1;
alter table Team add column displayOrder int default 0;


alter table Salary add column salaryFileName varchar(255) default null;
alter table ProjectTask add column taskFileName varchar(255) default null;


alter table Project modify column   `projectAttendanceManagerId` varchar(512) DEFAULT NULL;
alter table Project modify column   `projectManagerId` varchar(512) DEFAULT NULL;


alter table Salary add column `salaryPerDay` double DEFAULT 0;

alter table Team modify column  `projectId` varchar(512) DEFAULT NULL;

alter table AmsLog modify userId varchar(36) default null;

