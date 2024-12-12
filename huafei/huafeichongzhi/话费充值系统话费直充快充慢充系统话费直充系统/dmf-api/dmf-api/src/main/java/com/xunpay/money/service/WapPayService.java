package com.xunpay.money.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;

public class WapPayService {

	
	
	public String createOrder(CompanyOrder order) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		request.setNotifyUrl("http://" + apiHost + "/notice/alipay");
		request.setBizContent("{" +
	            "    \"out_trade_no\":\"" + order.getOrderNo() + "\"," +
	            "    \"product_code\":\"QUICK_WAP_WAY\"," +
	            "    \"total_amount\":" + order.getOrderMoney() + "," +
	            "    \"subject\":\"" + order.getOutOrderNo() + "\"," +
	            "    \"body\":\"" + order.getOutOrderNo() + "\"" +
	            "  }");
	    String form = "";
	    try {
	        form = alipayClient.pageExecute(request).getBody();
	        order.setRemark(form);
	        return "http://" + apiHost + "/order/form/" + order.getToken();
	    } catch (AlipayApiException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
}
