package com.xunpay.money.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;

public class YunyifuService {

	public String createOrder(CompanyOrder order) {
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		String url = "http://pay.yunyifu888.com/Pay_Index.html";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("pay_memberid", "10083");
	    params.put("pay_orderid", order.getOrderNo());
	    params.put("pay_applydate", DateUtils.getYmdHmis(new Date()));
	    params.put("pay_bankcode", "904");
	    params.put("pay_notifyurl", "http://" + apiHost + "/notice/yunyifu");
	    params.put("pay_callbackurl", "http://" + apiHost + "/success.jsp");
	    params.put("pay_amount", order.getOrderMoney() + "");
	    params.put("pay_md5sign", EncryptUtils.encrypt(getSign(params)).toUpperCase());
	    params.put("pay_productname", "充值");
	    
	    StringBuffer formHtml = new StringBuffer("<form id='payForm' action='" + url + "' method='post'>");
	    for (String key : params.keySet()) {
	    	formHtml.append("<input type='hidden' name='" + key + "' value='" + params.get(key) + "'/>");
	    }
	    formHtml.append("</form>");
	    formHtml.append("<script type='text/javascript'>");
	    formHtml.append("document.getElementById('payForm').submit();");
	    formHtml.append("</script>");
	    
		order.setRemark(formHtml.toString());
		return "http://" + apiHost + "/order/form/" + order.getToken();
	}
	
	public static String getSign(Map<String, String> map) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=rtvoflk6f0jojqr1vb8bppm2x0oeeytk");
		System.out.println("签名组成:" + sign);
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
