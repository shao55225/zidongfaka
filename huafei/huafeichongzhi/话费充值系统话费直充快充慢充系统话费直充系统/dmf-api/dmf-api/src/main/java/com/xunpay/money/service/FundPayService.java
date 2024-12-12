package com.xunpay.money.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundAuthOrderVoucherCreateRequest;
import com.alipay.api.response.AlipayFundAuthOrderVoucherCreateResponse;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;

public class FundPayService {
	
	public String createOrder(CompanyOrder order) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		AlipayFundAuthOrderVoucherCreateRequest request = new AlipayFundAuthOrderVoucherCreateRequest();
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		request.setNotifyUrl("http://" + apiHost + "/notice/alipayFund");
		request.setBizContent("{" +
		"\"out_order_no\":\"" + order.getOrderNo() + "\"," +
		"\"out_request_no\":\"" + order.getOrderNo() + "\"," +
		"\"order_title\":\"" + order.getOutOrderNo() + "\"," +
		"\"payee_user_id\":\"" + order.getPid() + "\"," +
		"\"amount\":" + order.getOrderMoney() + "," +
		"\"pay_timeout\":\"2d\"," +
		"\"product_code\":\"PRE_AUTH_ONLINE\"," +
		"\"trans_currency\":\"USD\"," +
		"\"settle_currency\":\"USD\"" +
		"  }");
		try {
			AlipayFundAuthOrderVoucherCreateResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
				return response.getCodeValue();
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}

}
