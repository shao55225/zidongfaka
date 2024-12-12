package com.xunpay.money.controller;

import com.xunpay.money.core.BaseController;
import com.xunpay.money.core.MyShiroRealm.ShiroUser;
import com.xunpay.money.core.validator.UserLoginValidator;
import com.xunpay.money.utils.*;

import net.sf.json.JSONObject;
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
				// 鍒ゆ柇鏄惁璁颁綇浜嗙櫥褰曪紝濡傛灉璁颁綇鐧诲綍 閲嶆柊鐧诲綍涓�涓�
				if (user.isRemembered()) {
					try {
						// 璁颁綇鐧诲綍鐘舵�佺殑鏃跺�欙紝閲嶆柊鐧诲綍涓�娆�
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
	
	public void password() {}
	
	public void editPassword() {
		String password = getPara("newPassword");
		Db.update("update sys_user set password = ? where id = ?", password, ShiroUtils.getUserId());
		message(true, "恭喜，密码修改成功", "/welcome");
	}
	public void editPayPassword() {
		String password = getPara("newPayPassword");
		Db.update("update sys_user set pay_password = ? where id = ?", password, ShiroUtils.getUserId());
		message(true, "恭喜，密码修改成功", "/welcome");
	}

	/**
	 * 运营商下单测试
	 *
	 */
	public void invest() {
		String phone = getPara("phone");
		String addresses = getPara("addresses");
		String money = getPara("money");
		if (phone.equals("") || phone==null){
			message(true, "修改参数错误", "/account/password");
			return;
		}
		if (addresses.equals("") || addresses==null || addresses.contains("IP地址")){
			message(true, "修改参数错误", "/account/password");
			return;
		}
		if (money.equals("") || money==null){
			message(true, "修改参数错误", "/account/password");
			return;
		}
		String order_no="A" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);
		String notify_url="http://fast.mynatapp.cc/api/order/create";
		String s_id="15859027006319913957";
		String md5=money+notify_url+order_no+phone+s_id+"2Kyk7wuYB1HIzGXdk17VAwjCbtL0mJbDggqUTBW3U4tdhqHI7CSfayT1ebT3S6Z5";
		String sign= EncryptUtils.encrypt(md5).toUpperCase();
		StringBuffer stringBuffer=new StringBuffer(addresses);
		stringBuffer.append("phone="+phone).
				append("&order_no="+order_no).
				append("&s_id="+s_id).
				append("&amount="+money).
				append("&notify_url="+notify_url).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendPost(url,null);
		JSONObject json = JSONObject.fromObject(s);
		message(true, json.getString("msg"), "/welcome");
	}


	/**
	 * 四方支付测试
	 *
	 */
	public void create() {
		String addresses = getPara("addresses");
		String money = getPara("money");
		if (addresses.equals("") || addresses==null || addresses.contains("IP地址")){
			message(true, "修改参数错误", "/account/password");
			return;
		}
		if (money.equals("") || money==null){
			message(true, "修改参数错误", "/account/password");
			return;
		}
		String order_no="A" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);
		String notify_url="http://wwww.baidu.com";
		String s_id="15859036582004330878";
		String md5=money+notify_url+order_no+s_id+"TOu9LhmzIaO2PFINumTjfCLsNIM7cadfSmng8GKVhGuquF7wKitm65BcdlLc3snf";
		String sign= EncryptUtils.encrypt(md5).toUpperCase();
		StringBuffer stringBuffer=new StringBuffer(addresses);
		stringBuffer.append("order_no="+order_no).
				append("&s_id="+s_id).
				append("&amount="+money).
				append("&notify_url="+notify_url).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendPost(url,null);
		JSONObject json = JSONObject.fromObject(s);
		String urlt="";
		if (json.getString("result").equals("SUCCESS")){
			urlt =JSONObject.fromObject(json.get("data").toString()).getString("qrCode");
		}else{
			urlt= json.getString("msg");
		}
		message(true, urlt, "/welcome");
	}

}
