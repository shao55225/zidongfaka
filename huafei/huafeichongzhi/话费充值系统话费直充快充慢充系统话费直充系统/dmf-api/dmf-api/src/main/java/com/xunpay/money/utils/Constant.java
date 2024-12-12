package com.xunpay.money.utils;

import java.math.BigDecimal;

public class Constant {

	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String FAIL = "fail";
	
	public static final String SHOWMSG = "showMsg";
	
	public static final String NORMAL = "正常";
	public static final String DISABLE = "禁用";
	
	public static final String COMPANY_DRAW = "商户提现";
	public static final String AGENT_DRAW = "码商提现";
	
	public static final double MAX_DRAWMONEY = 50000;
	public static final String PT_SETTLE = "平台分账";
	public static final String SH_SETTLE = "商户分账";
	
	public static final String PAYTYPE_DMF = "alipay_dmf";


	public static final String DEALING="DEALING";
	public static final String FAILED="FAILED";
	public static final String SUCCESS="SUCCESS";
	public static final String UNKNOWN="UNKNOWN";
	public static final String UNCONFIRMED="UNCONFIRMED";

	public static final BigDecimal MOBILE=new BigDecimal(0.02);
	public static final BigDecimal UNICOM=new BigDecimal(0.02);
	public static final BigDecimal TELECOM=new BigDecimal(0.02);
	public static final BigDecimal ZSH=new BigDecimal(0.02);

}
