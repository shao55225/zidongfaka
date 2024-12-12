package com.xunpay.money.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class Pay149297Service {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String orderNo = order.getOrderNo();
		String callbackurl = "http://47.92.253.105/notice/pay149297";
		String amount = new DecimalFormat("0.00").format(order.getOrderMoney());
		System.out.println(amount);
		String url = "http://pay.149297.cn/Pay/GateWay";
		url += "?parter=" + alipay.getPid();
		url += "&type=" + alipay.getAppid();
		url += "&value=" + amount;
		url += "&orderid=" + orderNo;
		try {
			url += "&callbackurl=" + URLEncoder.encode(callbackurl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += "&device=wap";
		String sign = "parter=" + alipay.getPid() + "&type=" + alipay.getAppid() + "&value=" + amount + "&orderid=" + orderNo + "&callbackurl=" + callbackurl + alipay.getRsaPrivate();
		url += "&sign=" + EncryptUtils.encrypt(sign);
		String result = HttpClientHelper.sendGet(url);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		result = result.substring(result.indexOf("http://"));
		result = result.substring(0, result.indexOf("\""));
		return result.trim();
	}
	
}
