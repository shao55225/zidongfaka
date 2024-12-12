package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.HttpClientHelper;

public class YigekuService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getRsaAlipay();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", alipay.getPid());
		params.put("data", "{\"amount\" : \"" + order.getOrderMoney().intValue() + "\", \"order_no\" : \"" + order.getOrderNo() + "\", \"mark\" : \"test\"}");
		params.put("sign_type", "RSA");
		params.put("pay_type", alipay.getAppid());
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("notify_url", "http://" + apiHost + "/notice/yigeku");
		
		try {
			String sign = AlipaySignature.rsaSign(params, alipay.getRsaPrivate(), "utf-8");
			params.put("sign", sign);
			String result = HttpClientHelper.sendPost(url, params);
			JSONObject json = JSONObject.fromObject(result);
			if ("1".equals(json.getString("code"))) {
				return json.getString("data");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
