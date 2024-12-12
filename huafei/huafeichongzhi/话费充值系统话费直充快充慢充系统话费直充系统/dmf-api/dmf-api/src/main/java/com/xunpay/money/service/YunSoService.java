package com.xunpay.money.service;

import java.net.URLEncoder;
import java.text.DecimalFormat;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;

public class YunSoService {

	public String createOrder(CompanyOrder order) {
		try {
			AlipayItem item = AlipayItem.dao.findById(order.getAlipayId());
			String key = item.getRsaPrivate();
			DecimalFormat df = new DecimalFormat("0.00");
			String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
			String signUrl = "_input_charset=utf-8&money=" + df.format(order.getOrderMoney()) + "&name=A&notify_url=" + URLEncoder.encode("http://" + apiHost + "/notice/yunso", "utf-8") + "&out_trade_no=" + order.getOrderNo() + "&pid=" + item.getPid() + "&return_url=" + URLEncoder.encode("http://" + apiHost + "/success.jsp", "utf-8") + "&sitename=B&type=" + item.getAppid();
			String url = item.getRsaAlipay() + "?" + signUrl + "&sign=" + EncryptUtils.encrypt(signUrl + key) + "&sign_type=MD5";
			return url;
		} catch (Exception e) {
		}
		return null;
	}
	
}
