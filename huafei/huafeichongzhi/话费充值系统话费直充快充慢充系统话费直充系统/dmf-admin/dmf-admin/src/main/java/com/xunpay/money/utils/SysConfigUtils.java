package com.xunpay.money.utils;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;

public class SysConfigUtils {

	public static String getConfig(String code) {
		String config = Db.queryStr("select content from sys_config where code = ?", code);
		if (StringUtils.isEmpty(config)) {
			return StringUtils.EMPTY;
		}
		return config;
	}
	
}
