package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class YzfService {
	
	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = "http://vip.ayayy258.com/gateway/index/checkpoint.do";
		Map<String, String> params = new HashMap<String, String>();
		params.put("account_id", alipay.getPid());
		params.put("content_type", "json");
		params.put("thoroughfare", alipay.getAppid());
		params.put("type", "2");
		params.put("out_trade_no", order.getOrderNo());
		params.put("robin", "2");
		params.put("keyId", order.getRsaPrivate());
		params.put("amount", String.format("%.2f", order.getOrderMoney()));
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("callback_url", "http://" + apiHost + "/notice/yunzhifu");
		params.put("success_url", "http://" + apiHost + "/success.jsp");
		params.put("error_url", "http://" + apiHost + "/error.jsp");
		String signStr = params.get("amount") + params.get("out_trade_no");
		params.put("sign", EncryptUtils.encrypt(EncryptUtils.encrypt(signStr) + params.get("keyId")));
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if (json.getInt("code") == 200) {
			return json.getJSONObject("data").getString("qrcode_url");
		}
		return "";
	}

}
