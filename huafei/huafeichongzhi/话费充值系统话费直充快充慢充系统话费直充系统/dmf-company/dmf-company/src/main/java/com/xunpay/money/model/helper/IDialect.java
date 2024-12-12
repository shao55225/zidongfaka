package com.xunpay.money.model.helper;


/**
 * 方言
 * @author tangshu
 *
 */
interface IDialect {
	
	String getJAVAType(String type);
	String getSQLType(String type);
	
}
