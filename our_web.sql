/*
 Navicat Premium Data Transfer

 Source Server         : 我自己的阿里云_39.106.82.217
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 39.106.82.217:3306
 Source Schema         : our_web

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 06/02/2021 21:50:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `article_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `article` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cno` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `no` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `article_create_time` datetime(2) NOT NULL,
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `no`(`no`) USING BTREE,
  CONSTRAINT `no` FOREIGN KEY (`no`) REFERENCES `user` (`no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `blog_file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `no` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blog_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blog_file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blog_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(2) NOT NULL,
  `is_open` enum('1','0') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `size` bigint(20) NOT NULL,
  `views` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`blog_file_id`) USING BTREE,
  INDEX `blog_no`(`no`) USING BTREE,
  CONSTRAINT `blog_no` FOREIGN KEY (`no`) REFERENCES `user` (`no`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `cno` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cname` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for class_task
-- ----------------------------
DROP TABLE IF EXISTS `class_task`;
CREATE TABLE `class_task`  (
  `class_task_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cno` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `task_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`class_task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for down_file_history
-- ----------------------------
DROP TABLE IF EXISTS `down_file_history`;
CREATE TABLE `down_file_history`  (
  `down_file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `file_id` bigint(20) NOT NULL,
  `down_file_time` datetime(2) NOT NULL,
  PRIMARY KEY (`down_file_id`) USING BTREE,
  INDEX `file_id`(`file_id`) USING BTREE,
  CONSTRAINT `file_id` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(2) NOT NULL,
  `size` bigint(20) NOT NULL,
  `cno` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `class_task_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
  `sc_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cno` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sc_id`) USING BTREE,
  INDEX `cno`(`cno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sign_in
-- ----------------------------
DROP TABLE IF EXISTS `sign_in`;
CREATE TABLE `sign_in`  (
  `sign_in_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `no` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`sign_in_id`) USING BTREE,
  INDEX `sign_in_no`(`no`) USING BTREE,
  CONSTRAINT `sign_in_no` FOREIGN KEY (`no`) REFERENCES `user` (`no`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `no` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(2) NOT NULL,
  `update_time` datetime(2) NOT NULL,
  `role` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `perm` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `stuNum`(`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for 3agoBlog
-- ----------------------------
DROP VIEW IF EXISTS `3agoBlog`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `3agoBlog` AS select `blog`.`blog_file_id` AS `blog_file_id`,`blog`.`no` AS `no`,`blog`.`blog_file_path` AS `blog_file_path`,`blog`.`blog_file_name` AS `blog_file_name`,`blog`.`blog_title` AS `blog_title`,`blog`.`create_time` AS `create_time`,`blog`.`is_open` AS `is_open`,`blog`.`size` AS `size`,`blog`.`views` AS `views`,`user`.`user_name` AS `user_name` from (`blog` join `user`) where ((`blog`.`is_open` = '1') and (`blog`.`no` = `user`.`no`) and ((to_days(`blog`.`create_time`) - to_days(now())) <= 0) and ((to_days(`blog`.`create_time`) - to_days(now())) > -(3))) order by `blog`.`views` desc,`blog`.`create_time` desc limit 0,6;

-- ----------------------------
-- Procedure structure for insert_sign_in
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_sign_in`;
delimiter ;;
CREATE PROCEDURE `insert_sign_in`(IN dno VARCHAR(11) ,OUT info VARCHAR(255))
  SQL SECURITY INVOKER
begin
		select no into info from sign_in WHERE no=dno and create_time>DATE_FORMAT(NOW(),'%Y-%m-%d');
		if(info is  null) THEN
				insert into sign_in(no,create_time) values (dno,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));
		end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectAllBlog
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectAllBlog`;
delimiter ;;
CREATE PROCEDURE `selectAllBlog`(in dviews VARCHAR(5),in dpage BIGINT(40))
  READS SQL DATA 
  SQL SECURITY INVOKER
begin
if(dviews='1')THEN
    select blog.*,user.user_name from blog,user where is_open='1' and blog.no=user.no
        order by views desc,create_time desc
        limit dpage,12;
elseif(dviews='0')
THEN
select blog.*,user.user_name from blog,user where is_open='1' and blog.no=user.no
        order by create_time desc
        limit dpage,12;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectAllBlogForSuccessView
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectAllBlogForSuccessView`;
delimiter ;;
CREATE PROCEDURE `selectAllBlogForSuccessView`()
BEGIN
	SELECT * FROM 3agoBlog
union
select blog.*,user.user_name from blog,user
        where is_open='1'
         and blog.no=user.no
					AND blog_file_id not in(

				SELECT blog_file_id FROM 3agoBlog
) 
        order by views desc,create_time desc
        limit 0,8;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectBlogByNo
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectBlogByNo`;
delimiter ;;
CREATE PROCEDURE `selectBlogByNo`(in dviews VARCHAR(5),in dno VARCHAR(11),in dpage BIGINT(40))
  READS SQL DATA 
  SQL SECURITY INVOKER
begin
if(dviews='1')THEN
select blog.*,user.user_name from blog,user where blog.no=dno and blog.no=user.no and is_open='1'
        order by views desc,create_time desc
        limit dpage,12;
elseif(dviews='0')
THEN
select blog.*,user.user_name from blog,user where blog.no=dno and blog.no=user.no and is_open='1'
        order by create_time desc
        limit dpage,12;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectMySelfBlog
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectMySelfBlog`;
delimiter ;;
CREATE PROCEDURE `selectMySelfBlog`(in dviews VARCHAR(5),in dno VARCHAR(11),in dpage BIGINT(40))
  READS SQL DATA 
  SQL SECURITY INVOKER
begin
if(dviews='1')THEN
select blog.*,user.user_name from blog,user where blog.no=dno and blog.no=user.no
        order by views desc,create_time desc
        limit dpage,12;
elseif(dviews='0')
THEN
select blog.*,user.user_name from blog,user where blog.no=dno and blog.no=user.no
        order by create_time desc
        limit dpage,12;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectSignInByNoAndTime
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectSignInByNoAndTime`;
delimiter ;;
CREATE PROCEDURE `selectSignInByNoAndTime`(in dno VARCHAR(11),in startTime datetime(2),in lastTime datetime(2))
  READS SQL DATA 
  SQL SECURITY INVOKER
begin
		declare tm VARCHAR(20);
		SELECT max(no) into tm from  sign_in WHERE
				sign_in.no=dno
        and sign_in.create_time between startTime and lastTime;
if (tm is null)THEN
SELECT user_name FROM `user` where no=dno;
else 
    select sign_in.*,user_name from sign_in,user
        where sign_in.no=dno
        and sign_in.create_time between startTime and lastTime
        and sign_in.no=user.no
        order by create_time;
end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for selectWebData
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectWebData`;
delimiter ;;
CREATE PROCEDURE `selectWebData`()
BEGIN

	SELECT COUNT(*) into @sumUser FROM user;

SELECT COUNT(*) into @sumBlogFile FROM blog;

SELECT SUM(blog.size)into @sumBlogFileSize FROM blog;

SELECT COUNT(*)  into @sumCreateClass FROM class;

SELECT COUNT(*) into @sumClassFile FROM file;

SELECT SUM(file.size) into @sumClassFileSize FROM file;
SELECT @sumUser as sumUser,@sumBlogFile as sumBlogFile,@sumBlogFileSize as sumBlogFileSize,@sumCreateClass as sumCreateClass
,@sumClassFile as sumClassFile,@sumClassFileSize as sumClassFileSize;

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
