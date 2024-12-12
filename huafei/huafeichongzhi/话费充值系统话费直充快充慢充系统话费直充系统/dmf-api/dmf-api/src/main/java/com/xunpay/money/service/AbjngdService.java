package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.xunpay.money.model.CompanyOrder;

public class AbjngdService {

	public String createOrder(CompanyOrder order) {
		String url = "http://sh.abjngd.com/basic/gateway/v1/OrderPay";
		Map<String, String> params = new HashMap<String, String>();
		params.put("merId", "364552108252402688165cd1f21acb96");
		params.put("businessOrderId", order.getOrderNo());
		params.put("tradeMoney", order.getOrderMoney().toString());
		params.put("payType", "2");
		params.put("asynURL", "http://39.98.89.111/notice/abjngd");
		
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json");
		try {
			post.setEntity(new StringEntity(JSONObject.fromObject(params).toString()));
			System.out.println(JSONObject.fromObject(params).toString(4));
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8"); 
			response.close();
			client.close();
			JSONObject json = JSONObject.fromObject(responseContent);
			if ("1000".equals(json.getString("code"))) {
				return json.getJSONObject("info").getString("codeurl");
			}
		} catch (Exception e) { }
		return null;
	}
	
}

