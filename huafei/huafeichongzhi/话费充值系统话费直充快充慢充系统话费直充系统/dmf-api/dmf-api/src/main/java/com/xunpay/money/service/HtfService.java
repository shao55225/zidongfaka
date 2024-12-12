package com.xunpay.money.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.RandomUtils;

public class HtfService {

	public String createOrder(CompanyOrder order) {
		AlipayItem item = AlipayItem.dao.findById(order.getAlipayId());
		String url = item.getRsaAlipay();
		Map<String, String> params = new HashMap<String, String>();
		params.put("mch_id", item.getPid());
		params.put("out_trade_no", order.getOrderNo());
		DecimalFormat df = new DecimalFormat("0.00");
		params.put("total_fee", df.format(order.getOrderMoney()));
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("notify_url", "http://" + apiHost + "/notice/htf");
		params.put("pay_type", item.getAppid());
		params.put("nonce_str", RandomUtils.randomString(32));
		params.put("body", "金币充值");
		params.put("goodsname", "充值");
		params.put("front_url", "http://" + apiHost + "/success.jsp");
		String signUrl = 
						 "body=" + params.get("body") + 
						 "&front_url=" + params.get("front_url") + 
						 "&goodsname=" + params.get("goodsname") + 
						 "&mch_id=" + params.get("mch_id") + 
						 "&nonce_str=" + params.get("nonce_str") + 
						 "&notify_url=" + params.get("notify_url") +
						 "&out_trade_no=" + params.get("out_trade_no") + 
						 "&pay_type=" + params.get("pay_type") + 
						 "&total_fee="  + params.get("total_fee") + 
						 "&" + item.getRsaPrivate();
		params.put("signature", EncryptUtils.encrypt(signUrl).toUpperCase());
		String result = HttpClientHelper.sendPost(url, params);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		order.setRemark(result);
		order.setQrUrl("http://" + apiHost + "/order/form/" + order.getToken());
		return order.getQrUrl();
	}
	
}
