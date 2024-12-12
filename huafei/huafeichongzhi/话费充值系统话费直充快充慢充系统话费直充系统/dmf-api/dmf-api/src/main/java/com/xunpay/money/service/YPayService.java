package com.xunpay.money.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class YPayService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getAppid();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("pay_memberid", alipay.getPid());
		params.put("pay_orderid", order.getOrderNo());
		params.put("pay_applydate", DateUtils.get(new Date(), "yyyyMMdd"));
		params.put("pay_bankcode", alipay.getRsaPrivate());
		String apiHost = "http://" + Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("pay_notifyurl", apiHost + "/notice/ypay");
		params.put("pay_callbackurl", apiHost + "/success.jsp");
		params.put("pay_amount", order.getOrderMoney().toString());
		params.put("pay_md5sign", getSign(params, alipay.getRsaAlipay()));
		params.put("pay_productname", "gold");
		if ("form".equals(alipay.getRemark())) {
			StringBuffer resultHtml = new StringBuffer();
			resultHtml.append("正在跳转支付...<form id='payForm' style='display:none;' action='http://www.newvps.cn/Pay_Index.html' method='post'>");
			for (String key : params.keySet()) {
				resultHtml.append("<input name='" + key + "' value='" + params.get(key) + "'>");
			}
			resultHtml.append("</form>");
			resultHtml.append("<script>document.getElementById('payForm').submit();</script>");
			order.setRemark(resultHtml.toString());
			return apiHost + "/order/form/" + order.getToken();	
		} else {
			String result = HttpClientHelper.sendPost(url, params);
			if (StringUtils.isNotEmpty(result) && result.indexOf("\"status\":\"error\"") < 0) {
				JSONObject json = JSONObject.fromObject(result);
				return json.getJSONObject("data").getString("payurl");
			}
			
		}
		return null;
	}
	
	public static String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=").append(md5key);
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
