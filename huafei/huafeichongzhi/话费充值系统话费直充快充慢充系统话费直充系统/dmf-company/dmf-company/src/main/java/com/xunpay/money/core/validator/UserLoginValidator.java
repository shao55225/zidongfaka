package com.xunpay.money.core.validator;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.xunpay.money.controller.VerifyCodeController;
import com.xunpay.money.model.AlipayAgent;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.utils.Constant;

public class UserLoginValidator extends Validator {
	
	@Override
	protected void handleError(Controller c) {
		c.keepPara("username");
		c.keepPara("rememberMe");
		c.renderJsp("login.jsp");
	}
	
	@Override
	protected void validate(Controller c) {
		String username = c.getPara("username");
		String password = c.getPara("password");
		//String verifyCode = c.getPara("verifyCode");
		if (StringUtils.isEmpty(username)) {
			addError(Constant.SHOWMSG, "用户名不能为空");
			return;
		}
		
		if (StringUtils.isEmpty(password)) {
			addError(Constant.SHOWMSG, "密码不能为空");
			return;
		}
		
		CompanyInfo  user = CompanyInfo.dao.findFirst("select * from company_info where username = ? and password = ? and is_del = 'N'", username, password);
		
		//CompanyInfo info=CompanyInfo.dao.findFirst("")
		
		if (user == null) {
			addError(Constant.SHOWMSG, "用户名或密码错误");
			return;
		}
		else {
		
			if(user.getStatus().equals(Constant.DISABLE)) {
				
			addError(Constant.SHOWMSG, "用户被禁用");
				
			return;
		  }
		}
	}
	
}
