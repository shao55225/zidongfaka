package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class WeixinService {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = "http://" + alipay.getPid() + "/v2/precreate_v2.ashx";
		String appid = alipay.getAppid();
		String appkey = alipay.getRsaPrivate();
		String orderNo = order.getOutOrderNo();
		String sign = EncryptUtils.encrypt(appid + appkey + orderNo);
		String amount = order.getOrderMoney().toString();
		String payType = alipay.getRsaAlipay();
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("sign", sign);
		params.put("out_order_no", orderNo);
		params.put("amount", amount);
		params.put("payType", payType);
		params.put("callbackUrl", "http://119.23.215.133:8080/notice/weixin");
		String result = HttpClientHelper.sendPost(url, params);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(result);
		if (json.containsKey("order_no") && StringUtils.isNotEmpty(json.getString("order_no"))) {
			String tradeNo = json.getString("order_no");
			if (json.containsKey("url") && StringUtils.isNotEmpty("url")) {
				order.setTradeNo(tradeNo);
				order.setQrUrl(json.getString("url"));
				return order.getQrUrl();
			}
		}
		return null;
		
	}
	
}
