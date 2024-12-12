package com.xunpay.money.service;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;

public class NineChaoService {
	
	private static final Logger logger = Logger.getLogger(NineChaoService.class);

	public String createOrder(CompanyOrder order) {
		String url = "http://47.108.64.236:8099/mch/order/create";
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		DecimalFormat df = new DecimalFormat(".00");
		params.put("amount", df.format(order.getOrderMoney()));
		params.put("createTime", System.currentTimeMillis());
		params.put("invokeUrl", "http://39.98.89.111/notice/ninechao");
		params.put("orderNo", order.getOrderNo());
		params.put("token", "89781498469c852c236871cee454b8c5");
		params.put("type", 1);
		params.put("userIp", "");
		String signJson = JSONObject.toJSONString(params);
		logger.info("签名：" + signJson);
		params.put("sign", EncryptUtils.encrypt(signJson));
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-Type", "application/json");
			post.setEntity(new StringEntity(JSONObject.toJSONString(params)));
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8"); 
			response.close();
			client.close();
			logger.info("9chao返回参数：" + responseContent);
			JSONObject obj = JSONObject.parseObject(responseContent);
			if ("200".equals(obj.getString("code"))) {
				return obj.getString("payUrl");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
