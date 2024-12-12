package com.xunpay.money.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class HeipayService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getAppid() + "/home/api/createOrder";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", alipay.getRsaPrivate());
		params.put("type", "alipay");
		params.put("price", order.getOrderMoney().toString());
		params.put("psn", order.getOrderNo());
		String sign = EncryptUtils.encrypt(getSign(params, alipay.getRsaAlipay()));
		params.put("sign", sign);
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("notify_url", "http://" + apiHost + "/notice/heipay");
		
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if ("100".equals(json.getString("code")) && StringUtils.isNotEmpty(json.getString("pay_url"))) {
			return json.getString("pay_url");
		}
		return null;
	}
	
	public static String getSign(Map<String, String> map, String md5Key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.setLength(sign.length() - 1);
		sign.append(md5Key);
		return sign.toString();
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
