package com.xunpay.money.utils;

import com.xunpay.money.core.MyShiroRealm.ShiroUser;

import org.apache.shiro.SecurityUtils;

public final class ShiroUtils {

	public static ShiroUser getUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	public static Integer getUserId() {
		ShiroUser user = getUser();
		if (user == null) {
			return null;
		}
		return user.getId();
	}

	public static String getUsername() {
		ShiroUser user = getUser();
		if (user == null) {
			return null;
		}
		return user.getUsername();
	}

	public static String getRole() {
		ShiroUser user = getUser();
		if (user == null) {
			return null;
		}
		return user.getRole();
	}

}
