package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.SignUtils;

public class NiniyhjService {
	
	public String createOrder(CompanyOrder order) {
		AlipayItem item = AlipayItem.dao.findById(order.getAlipayId());
		
		String url = item.getRsaAlipay(); //"http://www.niniyhj.com/pay/gateway";
		String md5key = item.getRsaPrivate(); //"c60812d5601851bae35a89907f94cdcf";
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", "pay");
		params.put("mch_id", item.getPid());
		params.put("paytype", item.getAppid());
		params.put("out_trade_no", order.getOrderNo());
		params.put("total_fee", order.getOrderMoney().toString());
		params.put("time", "" + System.currentTimeMillis());
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("notify_url", "http://" + apiHost + "/zhupang/niniyhj");
		
		String signUrl = SignUtils.sortUrl(params, md5key);
		params.put("sign", EncryptUtils.encrypt(signUrl));
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if (json.getInt("code") == 0) {
			return json.getJSONObject("data").getString("pay_url");
		}
		return null;
	}
	
	
}
