package com.xunpay.money.service;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class SuixinPayService {

	private static final Logger logger = Logger.getLogger(SuixinPayService.class);
	
	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getRsaAlipay();
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "1.0");
		params.put("customerid", alipay.getPid());
		params.put("sdorderno", order.getOrderNo());
		params.put("total_fee", new DecimalFormat(".00").format(order.getOrderMoney().doubleValue()));
		params.put("returnurl", "http://39.98.89.111/success.jsp");
		params.put("notifyurl", "http://39.98.89.111/notice/suixin");
		params.put("sign", getSign(params, alipay.getRsaPrivate()));
		params.put("paytype", alipay.getAppid());
		logger.info("请求参数:" + JSONObject.fromObject(params).toString(4));
		String result = HttpClientHelper.sendPost(url, params);
		logger.info("返回:" + result);
		JSONObject json = JSONObject.fromObject(result);
		if (json.getInt("code") == 1) {
			return json.getString("pay_url");
		}
		return null;
	}
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "1.0");
		params.put("customerid", "10934");
		params.put("sdorderno", "T" + System.currentTimeMillis());
		params.put("total_fee", "100.00");
		params.put("returnurl", "http://39.98.89.111/success.jsp");
		params.put("notifyurl", "http://39.98.89.111/notice/suixin");
		params.put("sign", getSign(params, "5b1a9b2a3af6cb33009d5f51da6da10d2b56d6b4"));
		params.put("paytype", "recharge");
		logger.info("请求参数:" + JSONObject.fromObject(params).toString(4));
		String result = HttpClientHelper.sendPost("http://159.138.129.94:6789/apisubmit", params);
		logger.info("返回:" + result);
		JSONObject json = JSONObject.fromObject(result);
		System.out.println(json.toString(4));
	}
	
	public static String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=" + md5key);
		System.out.println("签名组成:" + sign);
		String result = EncryptUtils.encrypt(sign.toString()).toUpperCase();
		System.out.println("签名完成:" + result);
		return result;
	}
	
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
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
