package com.xunpay.money.core.validator;

import org.apache.commons.lang.StringUtils;

import com.xunpay.money.controller.VerifyCodeController;
import com.xunpay.money.model.SysUser;
import com.xunpay.money.utils.Constant;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

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
		String verifyCode = c.getPara("verifyCode");
		if (StringUtils.isEmpty(username)) {
			addError(Constant.SHOWMSG, "用户名不能为空");
			return;
		}
		if (StringUtils.isEmpty(password)) {
			addError(Constant.SHOWMSG, "密码不能为空");
			return;
		}
		if (!VerifyCodeController.check(c, verifyCode, "login")) {
			addError(Constant.SHOWMSG, "验证码输入错误");
			return;
		}
		SysUser user = SysUser.dao.findFirst("select * from sys_user where username = ? and password = ?", username, password);
		if (user == null) {
			addError(Constant.SHOWMSG, "用户名或密码错误");
			return;
		}
		if (Constant.DISABLE.equals(user.getStatus())) {
			addError(Constant.SHOWMSG, "该用户已被禁用");
			return;
		}
	}
	
}
