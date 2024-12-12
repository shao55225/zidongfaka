package com.xunpay.money.service;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;

public class ScreamPayService {

	public String createOrder(CompanyOrder order) throws Exception {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		
		JSONObject params = new JSONObject();
		params.put("encryptType", "MD5");
		JSONObject content = new JSONObject();
		content.put("merchNo", alipay.getPid());
		content.put("orderNo", order.getOrderNo());
		content.put("amount", String.format("%.2f", order.getOrderMoney()));
		content.put("currency", "CNY");
		content.put("outChannel", alipay.getAppid());
		content.put("bankCode", "1001");
		content.put("title", "1");
		content.put("product", "1");
		content.put("memo", "1");
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		String notifyUrl = "http://" + apiHost + "/notice/scream";
		content.put("returnUrl", "http://" + apiHost + "/success.jsp");
		content.put("notifyUrl", notifyUrl);
		content.put("reqTime", DateUtils.get(new Date(), "yyyyMMddHHmmss"));
		content.put("userId", "1");
		params.put("sign", EncryptUtils.encrypt(EncryptUtils.encryptBASE64(content.toString()).replace("\n", "").replace("\r", "") + alipay.getRsaPrivate()));
		params.put("context", EncryptUtils.encryptBASE64(content.toString()).replace("\n", "").replace("\r", ""));
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(alipay.getRsaAlipay());
		post.addHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(params.toString()));
		System.out.println(params.toString(4));
		
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8"); 
		response.close();
		client.close();

		JSONObject json = JSONObject.fromObject(responseContent);
		if ("0".equals(json.getString("code")) && Constant.SUCCESS.equals(json.getString("msg"))) {
			String context = json.getString("context");
			return JSONObject.fromObject(context).getString("qrcode_url");
		}
		return null;
	}
	
}
