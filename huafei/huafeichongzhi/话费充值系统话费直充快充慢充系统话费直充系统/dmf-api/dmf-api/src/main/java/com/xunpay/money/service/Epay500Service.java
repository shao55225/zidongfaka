package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class Epay500Service {

	private static final Logger logger = Logger.getLogger(Epay500Service.class);
	
	public String createOrder(CompanyOrder order) {
		String url = "https://www.500epay.com/pay?format=json";
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", "32");
		params.put("price", String.valueOf(order.getOrderMoney()));
		params.put("istype", "1");
		params.put("notify_url", "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/notice/epay500");
		if (StringUtils.isNotEmpty(order.getReturnUrl())) {
			params.put("return_url", order.getReturnUrl());
		} else {
			params.put("return_url", "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/success.jsp");
		}
		params.put("orderid", order.getOrderNo());
		params.put("orderuid", order.getOutOrderNo());
		params.put("goodsname", order.getTitle());
		params.put("attach", order.getToken());
		params.put("key", EncryptUtils.encrypt(params.get("goodsname") + params.get("istype") + params.get("notify_url") + params.get("orderid") + params.get("orderuid") + params.get("price") + params.get("return_url") + "ecde7482ba051717552655cf0d6cfd5f" + params.get("uid")));
		String result = HttpClientHelper.sendPost(url, params);
		logger.info("500epay返回：" + result);
		JSONObject json = JSON.parseObject(result);
		if ("1".equals(json.getString("code"))) {
			return json.getJSONObject("data").getString("qrcode");
		}
		return null;
	}
	
}
