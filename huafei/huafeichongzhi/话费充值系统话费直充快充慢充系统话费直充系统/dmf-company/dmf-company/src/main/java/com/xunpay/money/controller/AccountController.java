package com.xunpay.money.controller;

import com.xunpay.money.core.BaseController;
import com.xunpay.money.core.MyShiroRealm.ShiroUser;
import com.xunpay.money.core.validator.UserLoginValidator;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.HttpRequestUtils;
import com.xunpay.money.utils.ShiroUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Before;
import com.jfinal.ext.plugin.shiro.ShiroKit;
import com.jfinal.plugin.activerecord.Db;

public class AccountController extends BaseController {

	public void login() {
		Subject user = SecurityUtils.getSubject();
		if (user.getPrincipal() != null) {
			if (user.isAuthenticated()) {
				redirect(ShiroKit.getSuccessUrl());
				return;
			} else {
				// 判断是否记住了登录，如果记住登录 重新登录一下
				if (user.isRemembered()) {
					try {
						// 记住登录状态的时候，重新登录一次
						ShiroUser shiroUser = ShiroUtils.getUser();
						UsernamePasswordToken token = new UsernamePasswordToken(
								shiroUser.getUsername(),
								shiroUser.getPassword(), true,
								HttpRequestUtils.getIpAddress(getRequest()));
						user.login(token);
						redirect(ShiroKit.getSuccessUrl());
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		redirect(ShiroKit.getLoginUrl());
	}

	@Before(UserLoginValidator.class)
	public void doLogin() {
		String username = getPara("username");
		String password = getPara("password");
		String rememberMe = getPara("rememberMe");
		if ("admin".equals(username)) {
			rememberMe = Constant.YES;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, Constant.YES.equals(rememberMe),
				HttpRequestUtils.getIpAddress(getRequest()));
		Subject user = SecurityUtils.getSubject();
		try {
			user.login(token);
			redirect(ShiroKit.getSuccessUrl());
		} catch (Exception e) {
			redirect(ShiroKit.getLoginUrl());
		}
	}
	
	public void password() {
		
	}
	
	//商户个人配置信息
	public void config() {
		
	  Integer  id=ShiroUtils.getUserId();
		
	  CompanyInfo  companyInfo=CompanyInfo.dao.findFirst(" select  appid as appid ,md5key as md5key  from company_info where id='"+id+"' ");
		
	  String appid=companyInfo.getAppid();
		
	  String md5key=companyInfo.getMd5key();
	  
	  setAttr("appid", appid);
	  
	  setAttr("md5key", md5key);
		
		
		
	}
	
	public void editPassword() {
		String password = getPara("newPassword");
		Db.update("update company_info set password = ? where id = ?", password, ShiroUtils.getUserId());
		message(true, "恭喜，密码修改成功。", "/welcome");
	}
	public void editPayPassword() {
		String password = getPara("newPayPassword");
		Db.update("update company_info set pay_password = ? where id = ?", password, ShiroUtils.getUserId());
		message(true, "恭喜，密码修改成功。", "/welcome");
	}
	

}
