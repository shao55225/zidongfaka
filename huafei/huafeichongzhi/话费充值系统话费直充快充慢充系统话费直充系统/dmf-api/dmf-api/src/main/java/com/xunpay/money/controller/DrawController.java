package com.xunpay.money.controller;

import org.apache.commons.lang.StringUtils;

import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.utils.EncryptUtils;

public class DrawController extends BaseController {

	public void request() {
		String appId = getPara("appId");
		String moneyStr = getPara("money");
		String type = getPara("type");
		String name = getPara("name");
		String account = getPara("account");
		String bank = getPara("bank");
		String sign = getPara("sign");
		if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(sign)) {
			result(false, "非法访问appId和sign不能为空", null);
			return;
		}
		double money  = 0;
		try {
			money = Double.parseDouble(moneyStr);
		} catch (NumberFormatException e) {
			result(false, "请输入正确的金额", null);
			return;
		}
		if (money <= 0) {
			result(false, "请输入正确的金额", null);
			return;
		}
		CompanyInfo company = CompanyInfo.dao.findFirst("select * from company_info where status = '正常' and is_del = 'N' and appid = ?", appId);
		if (company == null) {
			result(false, "商户不存在，或已被禁用", null);
			return;
		}
		
		String systemSign = EncryptUtils.encrypt(appId + "^" + moneyStr + "^" + account + "^" + company.getMd5key());
		if (!systemSign.equals(sign)) {
			result(false, "签名错误", null);
			return;
		}
		
	}
	
}
