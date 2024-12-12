package com.xunpay.money.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;

public class DingService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getRsaAlipay();
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "v1.0");
		params.put("type", alipay.getPid());
		params.put("userId", "881711");
		params.put("requestNo", order.getOrderNo());
		params.put("amount", order.getOrderMoney().intValue() * 100 + "");
		params.put("callBackURL", "http://39.100.141.192/notice/ding");
		params.put("redirectUrl", "http://39.100.141.192/success.jsp");
		params.put("sign", getSign(params, alipay.getRsaPrivate()));
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-Type", "application/json");
			post.setEntity(new StringEntity(JSONObject.fromObject(params).toString()));
			System.out.println(JSONObject.fromObject(params).toString(4));
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8"); 
			response.close();
			client.close();
			System.out.println(responseContent);
			JSONObject json = JSONObject.fromObject(responseContent);
			if ("1".equals(json.getString("status"))) {
				order.setTradeNo(json.getString("orderNo"));
				return json.getString("payUrl");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=" + md5key);
		System.out.println("签名组成:" + sign);
		return EncryptUtils.encrypt(sign.toString());
	}
	
	public Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		sortMap.putAll(map);
        return sortMap;
	}
	
}
