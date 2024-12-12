package com.xunpay.money.utils;

import java.util.regex.Pattern;

public class Validator {
	
	public static final Pattern TELPHONE = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
	
	public static boolean isTelphone(String telphone) {
		if (telphone == null) {
			return false;
		}
		return TELPHONE.matcher(telphone).find();
	}

}
