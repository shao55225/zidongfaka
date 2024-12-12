package com.xunpay.money.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.HttpClientHelper;

public class PaydogService {
	
	private static final Logger logger = Logger.getLogger(PaydogService.class);
	
	public String createOrder(CompanyOrder order) {
		String url = "http://www.ngldw.cn/get_qrcode";
		Map<String, String> params = new HashMap<String, String>();
		AlipayItem item = AlipayItem.dao.findById(order.getAlipayId());
		params.put("uid", item.getRsaPrivate());
		params.put("auth_code", item.getRsaAlipay());
		params.put("money", order.getOrderMoney().toString());
		params.put("channel", "alipay");
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("post_url", "http://" + apiHost + "/notice/paydog");
		if (StringUtils.isNotEmpty(order.getReturnUrl())) {
			params.put("return_url", order.getReturnUrl());
		} else {
			params.put("return_url", "http://" + apiHost + "/success.jsp");
		}
		params.put("order_id", order.getOrderNo());
		params.put("order_uid", order.getOutOrderNo());
		params.put("goods_name", order.getTitle());
		logger.info("支付dog请求参数：" + JSON.toJSONString(params));
		String result = HttpClientHelper.sendPost(url, params);
		logger.info("支付dog返回参数：" + result);
		String qrcode = JSON.parseObject(result).getString("qrcode");
		if (qrcode.startsWith("alipays://")) {
			try {
				qrcode = "http://" + apiHost + "/alipay_h5.jsp?url=" + URLEncoder.encode(qrcode, "utf-8");
				qrcode = "alipays://platformapi/startapp?appId=20000067&url="+URLEncoder.encode(qrcode, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return qrcode;
	}

}
