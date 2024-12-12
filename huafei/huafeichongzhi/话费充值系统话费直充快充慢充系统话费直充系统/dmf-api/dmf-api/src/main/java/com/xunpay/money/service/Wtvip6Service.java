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
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class Wtvip6Service {
	
	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getRsaAlipay(); // "http://www.wtvip6.cc:3020/api/pay/create_order";
		String md5key = alipay.getRsaPrivate(); // "RWSMMZ4ZKKHKSRHNU3SKYEENZ3FCEHAS8W881NJOFYZWO96KRG9BR8CGUJUUQIQQUVEWLPEJYEZLWTU3OE834LJ77KZNKZWQPHWNKPG7BMQLVNFE3OJYF06YAA1JKTU4";
		Map<String, String> params = new HashMap<String, String>();
		params.put("mchId", alipay.getPid()); //"58"
		params.put("appId", alipay.getAppid()); // "3b4692d46d7a428d8b85f8965714e455" 
		params.put("productId", alipay.getRemark()); //"8007"
		params.put("mchOrderNo", order.getOrderNo());
		params.put("currency", "cny");
		params.put("amount", order.getOrderMoney().intValue() * 100 + "");
		params.put("clientIp", "127.0.0.1");
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("notifyUrl", "http://" + apiHost + "/notice/wtvip");
		params.put("subject", "A");
		params.put("body", "B");
		params.put("sign", getSign(params, md5key));
		for (String key : params.keySet()) {
			System.out.println(key + " -> " + params.get(key));
		}
		Map<String, String> p = new HashMap<String, String>();
		p.put("params", JSONObject.fromObject(params).toString());
		String result = HttpClientHelper.sendPost(url, p);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(result);
		if ("SUCCESS".equals(json.getString("retCode"))) {
			return json.getString("payUrl");
		}
		return null;
	}
	
	public static String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=" + md5key);
		System.out.println("签名组成:" + sign);
		return EncryptUtils.encrypt(sign.toString()).toUpperCase();
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
