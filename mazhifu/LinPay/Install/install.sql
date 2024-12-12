DROP TABLE IF EXISTS `LinPay_Config`;
create table `LinPay_Config` (
`k` varchar(320) NOT NULL,
`v` text NULL,
PRIMARY KEY  (`k`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO `LinPay_Config` VALUES ('admin_user', 'admin');
INSERT INTO `LinPay_Config` VALUES ('admin_pass', 'UQ_Password');
INSERT INTO `LinPay_Config` VALUES('gonggao', '欢迎使用本支付,有问题请咨询客服处理');
INSERT INTO `LinPay_Config` VALUES('sitename', 'LinPay免授权');
INSERT INTO `LinPay_Config` VALUES('title', 'LinPay');
INSERT INTO `LinPay_Config` VALUES('keywords', 'LinPay支付系统,HXStudio,UQ');
INSERT INTO `LinPay_Config` VALUES('description', 'LinPay支付系统，免签约码支付，收款即时到账。');
INSERT INTO `LinPay_Config` VALUES('mail_type', '本地发件一');
INSERT INTO `LinPay_Config` VALUES('mail_smtp', 'smtp.qq.com');
INSERT INTO `LinPay_Config` VALUES('mail_port', '465');
INSERT INTO `LinPay_Config` VALUES('mail_name', '11@qq.com');
INSERT INTO `LinPay_Config` VALUES('mail_pwd', '11');
INSERT INTO `LinPay_Config` VALUES('webwh', '关闭维护');
INSERT INTO `LinPay_Config` VALUES('template', 'LinPay');
INSERT INTO `LinPay_Config` VALUES('tc_api', 'https://t.alcy.cc/pc/');
INSERT INTO `LinPay_Config` VALUES('kf_qq', '1759920773');
INSERT INTO `LinPay_Config` VALUES('reg', '1');
INSERT INTO `LinPay_Config` VALUES('findpwd', '1');
INSERT INTO `LinPay_Config` VALUES('Cloud', '<option value="bendi">桌面微信本地云端</option><option value="http://101.34.87.162:9999">aPad云端(上海)</option><option value="http://101.34.87.162:8848">iPad云端(上海)</option><option value="http://101.34.87.162:4499">QQ云端(上海)</option>');

DROP TABLE IF EXISTS `LinPay_Log_Data`;
CREATE TABLE `LinPay_Log_Data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `data` text NULL,
  `addtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_Code`;
CREATE TABLE `LinPay_Code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(11) DEFAULT NULL COMMENT '类型',
  `code` varchar(32) DEFAULT NULL COMMENT '验证码',
  `to` varchar(100) DEFAULT NULL COMMENT '邮箱地址',  
  `time` int(11) DEFAULT NULL COMMENT '间隔时间',
  `ip` varchar(255) DEFAULT NULL COMMENT '发送者IP',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_User`;
CREATE TABLE `LinPay_User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(32) NOT NULL COMMENT '账号',
  `pass` varchar(32) NOT NULL COMMENT '密码',
  `user_pid` varchar(255) NOT NULL COMMENT '用户对接PID',
  `user_key` varchar(255) NOT NULL COMMENT '用户对接密钥',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(32) NOT NULL COMMENT '电子邮箱',
  `money` varchar(32) DEFAULT '0.00' COMMENT '额度',
  `vip_time` datetime DEFAULT NULL COMMENT '会员过期时间',
  `email_status` int(1) DEFAULT '0' COMMENT '邮件发送信息',
  `music` int(1) DEFAULT '0' COMMENT '支付页语音功能',
  `pay_lxfs` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `pay_tcnr` varchar(255) DEFAULT NULL COMMENT '弹窗',
  `Pay_Template` varchar(255) DEFAULT 'LinPay' COMMENT '使用模板',
  `outtime` varchar(32) DEFAULT '180' COMMENT '超时时间',
  `status` int(1) DEFAULT '0' COMMENT '状态',
  `addtime` datetime DEFAULT NULL COMMENT '用户添加时间',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_Qrlist`;
CREATE TABLE `LinPay_Qrlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL COMMENT '所属账户',
  `qr_url` varchar(300) NOT NULL COMMENT '支付URL',
  `yd_url` varchar(300) DEFAULT NULL COMMENT '云端协议地址',
  `type` varchar(32) NOT NULL COMMENT '支付类型',
  `td_type` varchar(32) NOT NULL COMMENT '通道类型',
  `beizhu` varchar(32) DEFAULT NULL COMMENT '二维码备注',
  `wx_name` varchar(50) DEFAULT NULL COMMENT '绑定的微信店员',
  `cookie` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '登录COOKIE',
  `alipay_f2fid` varchar(3000)  COMMENT '支付宝f2fid',
  `alipay_f2fpubilc` varchar(3000) COMMENT '支付宝f2fpubilc',
  `alipay_f2fkey` varchar(3000) COMMENT '支付宝f2fkey',
  `money` varchar(32) NOT NULL COMMENT '金额',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  `num` int(11) DEFAULT '0' COMMENT '顺序',
  `crontime` varchar(288) DEFAULT '57600' COMMENT '监控时间',
  `endtime` datetime DEFAULT NULL COMMENT '失效时间',
  `addtime` datetime DEFAULT NULL COMMENT '用户添加时间',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_Qrlist_List`;
CREATE TABLE `LinPay_Qrlist_List` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `td_type` varchar(32) NOT NULL COMMENT '通道标识',
  `type` varchar(32) NOT NULL COMMENT '支付类型',
  `type_name` varchar(32) NOT NULL COMMENT '支付昵称',
  `td_name` varchar(32) NOT NULL COMMENT '通道昵称',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `LinPay_Qrlist_List` 
(`id`, `td_type`, `type`, `type_name`, `td_name`, `status`, `addtime`) 
VALUES 
(NULL, 'wxpay_dy', 'wxpay', '微信', '微信店员', 0, NOW()),
(NULL, 'wxpay_ipad', 'wxpay', '微信', '微信iPad', 1, NOW()),
(NULL, 'wxpay_apad', 'wxpay', '微信', '微信aPad', 1, NOW()),
(NULL, 'wxpay_vzq', 'wxpay', '微信', '微信转QQ', 1, NOW()),
(NULL, 'wxpay_uos', 'wxpay', '微信', '桌面微信免挂', 1, NOW()),
(NULL, 'wxpay_pc', 'wxpay', '微信', '微信PC自挂', 1, NOW()),
(NULL, 'wxpay_app', 'wxpay', '微信', '微信App挂机', 1, NOW()),
(NULL, 'alipay_mg', 'alipay', '支付宝', '支付宝免挂', 1, NOW()),
(NULL, 'alipay_mck', 'alipay', '支付宝', '支付宝免CK', 1, NOW()),
(NULL, 'alipay_app', 'alipay', '支付宝', '支付宝App挂机', 1, NOW()),
(NULL, 'qqpay_mg', 'qqpay', 'QQ', 'QQ免挂', 1, NOW()),
(NULL, 'qqpay_yd', 'qqpay', 'QQ', 'QQ云端', 1, NOW());

DROP TABLE IF EXISTS `LinPay_Notice`;
CREATE TABLE `LinPay_Notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(288) NOT NULL COMMENT '标题',
  `msg` varchar(288) NOT NULL COMMENT '内容',
  `color` varchar(288) NOT NULL COMMENT '颜色',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '公告状态',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `LinPay_Notice` (`id`, `title`, `msg`, `color`, `status`, `addtime`) VALUES (NULL, 'LinPay公告', 'LinPay欢迎您的使用，有问题请联系客户QQ1759920773', '#F50000', 1, NOW());

DROP TABLE IF EXISTS `LinPay_Order`;
CREATE TABLE `LinPay_Order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_pid` varchar(64) NOT NULL COMMENT '用户PID',
  `qr_id` int(11) NOT NULL COMMENT '通道ID',
  `price` varchar(64) NOT NULL COMMENT '实际金额',
  `money` varchar(64) NOT NULL COMMENT '提交金额',
  `name` varchar(288) NOT NULL COMMENT '订单名称',
  `trade_no` varchar(64) NOT NULL COMMENT '系统_订单号',
  `out_trade_no` varchar(64) NOT NULL COMMENT '用户_订单号',
  `notify_url` varchar(288) DEFAULT NULL COMMENT '异步通知地址',
  `return_url` varchar(288) DEFAULT NULL COMMENT '同步通知地址',
  `type` varchar(20) NOT NULL COMMENT '支付方式',
  `outtime` varchar(32) NOT NULL COMMENT '超时时间',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `endtime` datetime DEFAULT NULL COMMENT '完成支付时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '支付状态',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_Package`;
CREATE TABLE `LinPay_Package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '套餐名称',
  `vip_time` int(11) NOT NULL DEFAULT '1' COMMENT '会员天数',
  `xgcs` int (11) NOT NULL DEFAULT '1' COMMENT '套餐限购次数',
  `money` int(11) NOT NULL DEFAULT '100' COMMENT '套餐额度',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '套餐价格',
  `introduce` text NOT NULL COMMENT '套餐介绍',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LinPay_Package_Log`;
CREATE TABLE `LinPay_Package_Log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Package_id` varchar(255) NOT NULL COMMENT '套餐ID',
  `trade_no` varchar(255) NOT NULL COMMENT '购买套餐的订单号',
  `user_pid` varchar(255) NOT NULL COMMENT '购买套餐的pid',
  `gmcs` varchar(255) NOT NULL COMMENT '购买套餐的次数',
  `ip` varchar(255) NOT NULL COMMENT '购买套餐人的ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;