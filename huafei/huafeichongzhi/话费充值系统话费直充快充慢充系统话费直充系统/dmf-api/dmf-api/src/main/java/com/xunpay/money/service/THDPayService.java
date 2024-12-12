package com.xunpay.money.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;

public class THDPayService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		Map<String, String> params = new HashMap<String, String>();
		params.put("pay_memberid", alipay.getPid());
		params.put("pay_orderid", order.getOutOrderNo());
		params.put("pay_applydate", DateUtils.getYmdHmis(new Date()));
		params.put("pay_bankcode", alipay.getAppid());
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		String notifyUrl = "http://" + apiHost + "/notice/thdPay";
		params.put("pay_notifyurl", notifyUrl);
		params.put("pay_callbackurl", "http://" + apiHost + "/success.jsp");
		params.put("pay_amount", order.getOrderMoney().toString());
		params.put("pay_md5sign", getSign(params, alipay.getRsaPrivate()));
		params.put("pay_productname", order.getTitle());
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<body>");
		html.append("<form id='payform' action='" + alipay.getRsaAlipay() + "' method='post'>");
		for (String key : params.keySet()) {
			html.append("<input name='" + key + "' value='" + params.get(key) + "' />");
		}
		html.append("</form>");
		html.append("<script type='text/javascript'>document.getElementById('payform').submit();</script>");
		html.append("</body>");
		html.append("</html>");
		order.setRemark(html.toString());
		return "http://" + apiHost + "/order/form/" + order.getToken();
//		String result = HttpClientHelper.sendPost(alipay.getRsaAlipay(), params);
//		if (StringUtils.isNotEmpty(result)) {
//			try {
//				JSONObject obj = JSONObject.fromObject(result);
//				return obj.getJSONObject("data").getString("url");
//			} catch (Exception e) {
//				order.setRemark(result);
//				return "http://" + apiHost + "/order/form/" + order.getToken();
//			}
//		}
//		return null;
	}
	
	public static String getSign(Map<String, String> map, String apikey) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=").append(apikey);
		System.out.println(sign);
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
