package com.xunpay.money.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class EdaPayService {

	public static final String username = "MFGW00002";
	public static final String token = "154c7b85056047cd9f9137101b222730";
	
	public String createOrder(CompanyOrder order) {
		String url = "http://api.eda-pay.com:8081/app/selectGetQrcode";
		Map<String, String> params = new HashMap<String, String>();
		params.put("qrcode_type", "4");
		params.put("merchantnum", order.getOutOrderNo());
		params.put("bzmoneyid", order.getOrderMoney().intValue() + "");
		params.put("loginname", username);
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("merchanturl", "http://" + apiHost + "/notice/eda");
		params.put("qudao_type", "1");
		params.put("remark", order.getOrderNo());
		String sign = params.get("qrcode_type")
				+ params.get("merchantnum") 
				+ params.get("bzmoneyid")
				+ params.get("loginname")
				+ params.get("merchanturl")
				+ params.get("qudao_type") + "{" + token + "}";
		params.put("sign", EncryptUtils.encrypt(sign));
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if ("0".equals(json.getString("systate"))) {
			return json.getJSONObject("data").getString("wapurl");
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://api.eda-pay.com:8081/app/selectGetQrcode";
		Map<String, String> params = new HashMap<String, String>();
		params.put("qrcode_type", "4");
		params.put("merchantnum", "T" + System.currentTimeMillis());
		params.put("bzmoneyid", "100");
		params.put("loginname", username);
		String apiHost = "test";//Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("merchanturl", "http://" + apiHost + "/notice/eda");
		params.put("qudao_type", "1");
		params.put("remark", "1234");
		String sign = params.get("qrcode_type")
				+ params.get("merchantnum") 
				+ params.get("bzmoneyid")
				+ params.get("loginname")
				+ params.get("merchanturl")
				+ params.get("qudao_type") + "{" + token + "}";
		params.put("sign", EncryptUtils.encrypt(sign));
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		System.out.println(json.toString(4));
	}
	
	private int getMoneyId(int money) {
		String url = "http://api.eda-pay.com:8081/app/bzmoney";
		Map<String, String> params = new HashMap<>();
		params.put("loginname", username);
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		int moneyId = 0;
		if ("0".equals(json.getString("systate"))) {
			JSONArray data = json.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject item = data.getJSONObject(i);
				if (money == item.getInt("bzmoney")) {
					moneyId = item.getInt("id");
					break;
				}
			}
		}
		return moneyId;
	}
	
}
