package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class PingddService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		
		String url = alipay.getRsaAlipay();
		String key = alipay.getRsaPrivate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", alipay.getPid());
		params.put("orderid", order.getOrderNo());
		params.put("ordertype", alipay.getAppid());
		params.put("amount", order.getOrderMoney().intValue() * 100 + "");
		params.put("callbackurl", "http://39.100.141.192/notice/pingdd");
		params.put("remark", "用户充值");
		params.put("sign", EncryptUtils.encrypt((params.get("userid") + params.get("orderid") + params.get("ordertype") + params.get("amount") + params.get("callbackurl") + key).toLowerCase()));
		
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject obj = JSONObject.fromObject(result);
		if ("0".equals(obj.getString("code"))) {
			return obj.getString("payurl");
		}
		return null;
	}
	
}
