CREATE TABLE `sagas_order`(
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
	`status` TINYINT(1) DEFAULT 0 COMMENT '订单状态' ,
	`param_hash` INT(5) NOT NULL COMMENT '参数hash' ,
	`type` TINYINT(1) NOT NULL COMMENT '类型' ,
	`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
	`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
	PRIMARY KEY(`id`) ,
	UNIQUE KEY `uk_order_no_type`(`order_no`) ,
	UNIQUE KEY `uk_params_hash_type`(`param_hash` , `type`) ,
	KEY `idx_status`(`status`) ,
	KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '订单表';

CREATE TABLE `sagas_process_order`(
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
	`process_no` VARCHAR(64) NOT NULL COMMENT '过程编号' ,
	`param` VARCHAR(128) NOT NULL COMMENT '参数' ,
	`class_name` VARCHAR(64) NOT NULL COMMENT '类名' ,
	`mothed_name` VARCHAR(64) NOT NULL COMMENT '方法名' ,
	`status` TINYINT(1) DEFAULT 0 COMMENT '状态' ,
	`order` TINYINT(1) NOT NULL COMMENT '序号' ,
	`re_send` TINYINT(4) NOT NULL COMMENT '重发次数' ,
	`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
	`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
	PRIMARY KEY(`id`) ,
	UNIQUE KEY `uk_order_no`(`order_no` , `order`) ,
	UNIQUE KEY `uk_process_no`(`process_no`) ,
	KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '过程表';

CREATE TABLE `sagas_order_history`(
														`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
														`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
														`status` TINYINT(1) DEFAULT 0 COMMENT '订单状态' ,
														`param_hash` INT(5) NOT NULL COMMENT '参数hash' ,
														`type` TINYINT(1) NOT NULL COMMENT '类型' ,
														`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
														`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
														PRIMARY KEY(`id`) ,
														UNIQUE KEY `uk_order_no_type`(`order_no`) ,
														KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '订单历史表';

CREATE TABLE `sagas_process_order_history`(
																		`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
																		`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
																		`process_no` VARCHAR(64) NOT NULL COMMENT '过程编号' ,
																		`param` VARCHAR(128) NOT NULL COMMENT '参数' ,
																		`class_name` VARCHAR(64) NOT NULL COMMENT '类名' ,
																		`mothed_name` VARCHAR(64) NOT NULL COMMENT '方法名' ,
																		`status` TINYINT(1) DEFAULT 0 COMMENT '状态' ,
																		`order` TINYINT(1) NOT NULL COMMENT '序号' ,
																		`re_send` TINYINT(4) NOT NULL COMMENT '重发次数' ,
																		`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
																		`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
																		PRIMARY KEY(`id`) ,
																		UNIQUE KEY `uk_order_no`(`order_no` , `order`) ,
																		UNIQUE KEY `uk_process_no`(`process_no`) ,
																		KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '过程历史表';


CREATE TABLE `sagas_flow`(
																		`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
																		`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
																		`status` TINYINT(1) DEFAULT 0 COMMENT '订单状态' ,
																		`param_hash` INT(5) NOT NULL COMMENT '参数hash' ,
																		`type` TINYINT(1) NOT NULL COMMENT '类型' ,
																		`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
																		`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
																		PRIMARY KEY(`id`) ,
																		KEY `idk_order_no_type`(`order_no`) ,
																		KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '订单流水表';


CREATE TABLE `sagas_process_flow`(
																						`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
																						`order_no` VARCHAR(64) NOT NULL COMMENT '订单编号' ,
																						`process_no` VARCHAR(64) NOT NULL COMMENT '过程编号' ,
																						`param` VARCHAR(128) NOT NULL COMMENT '参数' ,
																						`class_name` VARCHAR(64) NOT NULL COMMENT '类名' ,
																						`mothed_name` VARCHAR(64) NOT NULL COMMENT '方法名' ,
																						`status` TINYINT(1) DEFAULT 0 COMMENT '状态' ,
																						`order` TINYINT(1) NOT NULL COMMENT '序号' ,
																						`re_send` TINYINT(4) NOT NULL COMMENT '重发次数' ,
																						`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
																						`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
																						PRIMARY KEY(`id`) ,
																						KEY `idk_order_no`(`order_no` , `order`) ,
																						KEY `idk_process_no`(`process_no`) ,
																						KEY `idx_create_time`(`create_time`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '过程流水表';

CREATE table `sagas_business_lock` (
  `id` int(11) not null  auto_increment comment '自增主键',
  `order_no` varchar(64) not  null  comment '订单编号',
	`type` TINYINT(1) NOT NULL COMMENT '类型' ,
	`thread` int(11) not null default  0 comment '在用线程数',
	`create_time` DATETIME NOT NULL COMMENT '创建时间' ,
	`modify_time` DATETIME DEFAULT NULL COMMENT '修改时间' ,
	PRIMARY KEY(`id`) ,
	UNIQUE KEY `uk_order_no_type`(`order_no`) ,
	KEY `idx_create_time`(`create_time`)
)ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '业务锁表';