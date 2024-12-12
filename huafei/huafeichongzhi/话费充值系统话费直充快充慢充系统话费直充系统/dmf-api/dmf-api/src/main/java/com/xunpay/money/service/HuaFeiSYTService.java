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

public class HuaFeiSYTService {

	public String creatOrder(CompanyOrder order) {
		AlipayItem item = AlipayItem.dao.findById(order.getAlipayId());
		
//		String url = "http://103.61.37.30:9088/api/recharge/h5/order.do";
//		String md5key = "25d55ad283aa400af464c76d713c07ad";
		
		String url = item.getRsaAlipay(); //"http://www.hfsyt.com/pay/gateway";
		String md5key = item.getRsaPrivate(); //"23wrueifu0uhrg0jjfjuf216e6gxcvmv";
		Map<String, String> params = new HashMap<String, String>();
		params.put("agentAcct", order.getAgentName());//agentAcct 代理账号
		params.put("agentOrderId", order.getOrderNo());	//代理订单编号
		params.put("bizType", item.getAppid());	//业务类型
		params.put("number", order.getCompanyName());//number 充值账号
		params.put("amount", order.getOrderMoney().toString());	//充值金额
		params.put("timestamp", "" + System.currentTimeMillis());	//timestamp 请求时间戳
		//channel	//不传则使用默认渠道（QSWCASHIER）
		params.put("channel", "QSWCASHIER");
		//callback	//不传则使用绑定的默认地址
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		params.put("callback", "http://" + apiHost + "/zhupang/huaFeiSYT");
		
		String signUrl = SignUtils.sortUrl(params, md5key);
		params.put("sign", EncryptUtils.encrypt(signUrl));	//sign
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if (json.getInt("code") == 0) {
			return json.getJSONObject("data").getString("pay_url");
		}
		return null;
	}
}
