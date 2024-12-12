package com.xunpay.money.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.service.HeipayService;
import com.xunpay.money.service.PayService;
import com.xunpay.money.service.THDPayService;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;

public class Notice_______Controller extends BaseController {

	private static final Logger logger = Logger.getLogger(Notice_______Controller.class);
	
	/**
	 *  支付宝当面付回调
	 */
	public void alipay() {
		Map<String, String> reqMap = getReqMap(getRequest());
		String status = reqMap.get("trade_status");
		String tradeNo = reqMap.get("trade_no");
		double tradeMoney = Double.parseDouble(reqMap.get("total_amount"));
		String orderNo = reqMap.get("out_trade_no");
		String buyer = reqMap.get("buyer_logon_id");
		String buyerId = reqMap.get("buyer_id");
		String payTime = reqMap.get("gmt_payment");
		if (!"TRADE_SUCCESS".equals(status)) {
			logger.info("交易状态为" + status + "，不处理回调");
			renderSuccess(true);
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		if (order.getOrderMoney().doubleValue() != tradeMoney) {
			logger.error("订单金额不等于支付金额");
			renderSuccess(false);
			return;
		}
		
		boolean success = false;
		try {
			success = AlipaySignature.rsaCheckV1(reqMap, order.getRsaAlipay(), "utf-8", reqMap.get("sign_type"));
		} catch (AlipayApiException ex) {
			ex.printStackTrace();
		}
		if (!success) {
			logger.error("支付宝签名错误");
			renderSuccess(false);
			return;
		}
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(true);
			return;
		}
		
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(payTime);
		order.setTradeNo(tradeNo);
		order.setBuyerId(buyerId);
		order.setBuyerLogonId(buyer);
		order.setSettle("分账中");
		order.setNotice("回调中");
		order.update();
		
		// 修改支付宝信息
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -1);
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0, lasttime = ? where id = ?", order.getOrderMoney(), calendar.getTime(), order.getAlipayId());
		
		PayService payService = new PayService();
		// 分账
		payService.settleDmfOrder(order);
		// 回调
		payService.notice(order);
	}
	
	/**
	 *  支付宝红包回调
	 */
	public void alipayHb() {
		Map<String, String> reqMap = getReqMap(getRequest());
		String status = reqMap.get("status");
		String tradeNo = reqMap.get("auth_no");
		double tradeMoney = Double.parseDouble(reqMap.get("amount"));
		String orderNo = reqMap.get("out_order_no");
		String buyer = reqMap.get("payer_logon_id");
		String buyerId = reqMap.get("payer_user_id");
		String payTime = reqMap.get("gmt_trans");
		
		if (!"SUCCESS".equals(status)){
			logger.info("支付未成功，不处理回调");
			renderSuccess(true);
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		if (order.getOrderMoney().doubleValue() != tradeMoney) {
			logger.error("订单金额不等于支付金额");
			renderSuccess(false);
			return;
		}
		
		boolean success = false;
		try {
			success = AlipaySignature.rsaCheckV1(reqMap, order.getRsaAlipay(), "utf-8", reqMap.get("sign_type"));
		} catch (AlipayApiException ex) {
			ex.printStackTrace();
		}
		if (!success) {
			logger.error("支付宝签名错误");
			renderSuccess(false);
			return;
		}
		
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(true);
			return;
		}
		
		PayService payService = new PayService();
		// 分账
		payService.settleDmfOrder(order);
		
		if (!"分账成功".equals(order.getPayType())) {
			renderSuccess(false);
			return;
		}
		
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(payTime);
		order.setTradeNo(tradeNo);
		order.setBuyerId(buyerId);
		order.setBuyerLogonId(buyer);
		order.setNotice("回调中");
		order.update();
		
		// 修改支付宝信息
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -1);
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0, lasttime = ? where id = ?", order.getOrderMoney(), calendar.getTime(), order.getAlipayId());
		
		// 回调
		payService.notice(order);
	}
	
	private Map<String, String> getReqMap(HttpServletRequest request) {
		Map<String, String[]> paraMap = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		logger.info("**********************************");
		for (Iterator<String> iter = paraMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) paraMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			logger.info(name + " ----> " + valueStr);
		}
		logger.info("**********************************");
		return params;
	}
	
	/**
	 *  支付宝预付接口回调
	 */
	public void alipayFund() {
		Map<String, String> reqMap = getReqMap(getRequest());
		String status = reqMap.get("status");
		String tradeNo = reqMap.get("auth_no");
		double tradeMoney = Double.parseDouble(reqMap.get("amount"));
		String orderNo = reqMap.get("out_order_no");
		String buyer = reqMap.get("payer_logon_id");
		String buyerId = reqMap.get("payer_user_id");
		String payTime = reqMap.get("gmt_trans");
		if (!"SUCCESS".equals(status)){
			logger.info("支付未成功，不处理回调");
			renderSuccess(true);
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		if (order.getOrderMoney().doubleValue() != tradeMoney) {
			logger.error("订单金额不等于支付金额");
			renderSuccess(false);
			return;
		}
		
		boolean success = false;
		try {
			success = AlipaySignature.rsaCheckV1(reqMap, order.getRsaAlipay(), "utf-8", reqMap.get("sign_type"));
		} catch (AlipayApiException ex) {
			ex.printStackTrace();
		}
		if (!success) {
			logger.error("支付宝签名错误");
			renderSuccess(false);
			return;
		}
		
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(true);
			return;
		}
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		AlipayTradePayRequest request = new AlipayTradePayRequest();
		AlipayTradePayModel model = new AlipayTradePayModel();
		model.setOutTradeNo(orderNo);
	    model.setProductCode("PRE_AUTH_ONLINE");
	    model.setAuthNo(tradeNo);
	    model.setSubject("预授权转支付");
	    model.setTotalAmount(order.getOrderMoney().toString());
	    model.setSellerId(order.getPid());
	    model.setBuyerId(buyerId);
	    model.setStoreId("A001");
	    model.setTerminalId("A001");
	    model.setBody("预授权解冻转支付");
	    model.setAuthConfirmMode("COMPLETE");
	    request.setBizModel(model);
		try {
			logger.info("冻结转支付request：" + JSONObject.fromObject(request).toString());
			AlipayTradePayResponse response = alipayClient.execute(request);
			logger.info("冻结转支付response：" + JSONObject.fromObject(response).toString());
			if (response.isSuccess()) {
				tradeNo = response.getTradeNo();
			} else {
				logger.info("冻结转支付失败" + orderNo);
				renderSuccess(false);
				return;
			}
		} catch (AlipayApiException e) {
			renderSuccess(false);
			return;
		}
		
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(payTime);
		order.setTradeNo(tradeNo);
		order.setBuyerId(buyerId);
		order.setBuyerLogonId(buyer);
		order.setSettle("分账中");
		order.setNotice("回调中");
		order.update();
		
		// 修改支付宝信息
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -1);
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0, lasttime = ? where id = ?", order.getOrderMoney(), calendar.getTime(), order.getAlipayId());
		
		PayService payService = new PayService();
		// 分账
		payService.settleDmfOrder(order);
		// 回调
		payService.notice(order);
	}
	
	public void ypay() {
		getReqMap(getRequest());
		String memberid = getPara("memberid");
		String amount = getPara("amount");
		String datetime = getPara("datetime");
		String orderid = getPara("orderid");
		String tranSerno = getPara("transaction_id");
		String retCode = getPara("returncode");
		String sign = getPara("sign");

		if (!"00".equals(retCode)) {
			logger.error("ret_code不为00，不处理" + retCode);
			renderNull();
			return;
		}
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderid);
		if (order == null) {
			logger.error(orderid + "订单不存在");
			renderNull();
			return;
		}
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String srcSign = "amount=" + amount + "&datetime=" + datetime + "&memberid=" + alipay.getPid() + "&orderid=" + orderid + "&returncode=" + retCode + "&transaction_id=" + tranSerno + "&key=" + alipay.getRsaAlipay();
		if (!sign.equalsIgnoreCase(EncryptUtils.encrypt(srcSign))) {
			logger.error("签名错误：" + srcSign);
			renderNull();
			return;
		}
		if ("已支付".equals(order.getStatus())) {
			renderText("OK");
			return;
		}
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(tranSerno);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();

		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);

		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderText("OK");
	}
	
//	/**
//	 * 支付狗回调
//	 */
//	public void paydog() {
//		String order_id = getPara("order_id");
//		String trade_no = getPara("trade_no");
//		String channel = getPara("channel");
//		String money = getPara("money");
//		String remark = getPara("remark");
//		String order_uid = getPara("order_uid");
//		String goods_name = getPara("goods_name");
//		String key = getPara("key");
//		String sign = "YNjAZ3fyUzUruiUjuieQrQbmUNvuUbYNvEJb" + trade_no + order_id + channel + money + remark + order_uid + goods_name;
//		
//		logger.info("sing:" + sign + " ----> " + key);
//		if (!EncryptUtils.encrypt(sign).equals(key)) {
//			renderSuccess(false);
//			return;
//		}
//		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", order_id);
//		if (order == null) {
//			logger.error("订单不存在");
//			renderSuccess(false);
//			return;
//		}
//		if ("已支付".equals(order.getStatus())) {
//			renderSuccess(true);
//			return;
//		}
//		// 修改订单状态
//		order.setStatus("已支付");
//		order.setPaytime(DateUtils.getYmdHmis(new Date()));
//		order.setTradeNo(trade_no);
//		order.setSettle("分账成功");
//		order.setNotice("回调中");
//		order.update();
//		
//		PayService payService = new PayService();
//		payService.createBill(order);
//		// 回调
//		payService.notice(order);
//		
//		// 修改支付宝信息
//		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
//		
//		renderSuccess(true);
//	}
	
	/**
	 * 天昊的平台回调
	 */
	public void heipay() {
		String orderNo = getPara("psn");
		String money = getPara("money");
		String sign = getPara("sign");
		String sn = getPara("sn");
		
		Map<String, String> params = new HashMap<>();
		params.put("psn", orderNo);
		params.put("money", money);
		params.put("sn", sn);
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String srcSign = HeipayService.getSign(params, alipay.getRsaAlipay());
		if (!sign.equals(EncryptUtils.encrypt(srcSign))) {
			renderSuccess(false);
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(true);
			return;
		}
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(sn);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderSuccess(true);
	}
	
	/**
	 * 9哥的云支付回调
	 */
	public void yunzhifu() {
		String status = getPara("status");
		String amount = getPara("amount");
		String orderNo = getPara("out_trade_no");
		String tradeNo = getPara("trade_no");
		String sign = getPara("sign");
		String accountKey = getPara("account_key");
		
		logger.info("status       --> " + status);
		logger.info("amount       --> " + amount);
		logger.info("out_trade_no --> " + orderNo);
		logger.info("trade_no     --> " + tradeNo);
		logger.info("sign         --> " + sign);
		logger.info("account_key  --> " + accountKey);
		
		
		
		if (!"success".equals(status)) {
			renderSuccess(true);
			return;
		}
		String signStr = EncryptUtils.encrypt(EncryptUtils.encrypt(amount + orderNo) + accountKey);
		if (!sign.equals(signStr)) {
			renderSuccess(true);
			return;
		}
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		
		if (Double.parseDouble(amount) < order.getOrderMoney().doubleValue()) {
			renderSuccess(false);
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(false);
			return;
		}
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(tradeNo);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderSuccess(true);
	}
	
	/**
	 * 迅哥-安卓钉钉回调
	 */
	public void scream() {
		String noticeResult = readAsString(getRequest());
		logger.info(noticeResult);
		JSONObject result = JSONObject.fromObject(noticeResult);
		String context = result.getString("context");
		JSONObject paramObj = JSONObject.fromObject(context);
		
		String merchNo = paramObj.getString("merchNo");
		String orderNo = paramObj.getString("orderNo");
		String businessNo = paramObj.getString("businessNo");
		String orderState = paramObj.getString("orderState");
		String amount = paramObj.getString("amount");
		String realAmount = paramObj.getString("realAmount");
		logger.info("merchNo        --> " + merchNo);
		logger.info("orderNo        --> " + orderNo);
		logger.info("businessNo     --> " + businessNo);
		logger.info("orderState     --> " + orderState);
		logger.info("amount         --> " + amount);
		logger.info("realAmount     --> " + realAmount);
		
		if (!"1".equals(orderState)) {
			renderSuccess(true);
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		
		if (Double.parseDouble(amount) < order.getOrderMoney().doubleValue()) {
			renderSuccess(false);
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(false);
			return;
		}
		
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String sign = EncryptUtils.encrypt(EncryptUtils.encryptBASE64(context).trim().replaceAll("\n", "").replaceAll("\r", "") + alipay.getRsaPrivate());
		if (!sign.equals(result.getString("sign"))) {
			logger.error("验签失败：" + sign);
			renderSuccess(false);
			return;
		}
		
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(businessNo);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderText("ok");
	}
	
	public void thdPay() {
		String money = getPara("amount");
		String orderNo = getPara("orderid");
		String result = getPara("returncode");
		String sign = getPara("sign");
		String trainNo = getPara("transaction_id");
		
		if (!"00".equals(result)) {
			renderSuccess(false);
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where out_order_no = ?", orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		
		Map<String, String> params = getReqMap(getRequest());
		params.remove("sign");
		params.remove("attach");
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		if (!THDPayService.getSign(params, alipay.getRsaPrivate()).equalsIgnoreCase(sign)) {
			renderSuccess(false);
			return;
		}
		
		if (Double.parseDouble(money) < order.getOrderMoney().doubleValue()) {
			renderSuccess(false);
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			renderText("OK");
			return;
		}
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(trainNo);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderText("OK");
	}
	
	public void weixin() {
		String appid = getPara("appid");
		String sign = getPara("sign");
		String orderNo = getPara("order_no");
		String outOrderNo = getPara("out_order_no");
		String amount = getPara("returnamount");
		logger.info("appid        	--> " + appid);
		logger.info("sign         	--> " + sign);
		logger.info("order_no     	--> " + orderNo);
		logger.info("out_order_no   --> " + outOrderNo);
		logger.info("returnamount   --> " + amount);
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where out_order_no = ? and trade_no = ?", outOrderNo, orderNo);
		if (order == null) {
			logger.error("订单不存在");
			renderSuccess(false);
			return;
		}
		
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String targetSign = EncryptUtils.encrypt(appid + alipay.getRsaPrivate() + orderNo);
		if (!sign.equals(targetSign)) {
			logger.error("签名错误");
			renderSuccess(false);
			return;
		}
		
		if (Double.parseDouble(amount) < order.getOrderMoney().doubleValue()) {
			logger.error("金额错误");
			renderSuccess(false);
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			logger.error("订单已支付，不处理回调");
			renderSuccess(false);
			return;
		}
		// 修改订单状态
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderSuccess(true);
	}
	
	public void kuaizhan() {
		String noticeResult = readAsString(getRequest());
		JSONObject paramJson = JSONObject.fromObject(noticeResult);
		String order_id = paramJson.getString("order_id");
		String orderNo = paramJson.getString("orderNo");
		String money = paramJson.getString("money");
		String mch = paramJson.getString("mch");
		String pay_type = paramJson.getString("pay_type");
		String status = paramJson.getString("status");
		String sign = paramJson.getString("sign");
		String time = paramJson.getString("time");
		logger.info("order_id        	--> " + order_id);
		logger.info("orderNo        	--> " + orderNo);
		logger.info("money        		--> " + money);
		logger.info("mch        		--> " + mch);
		logger.info("pay_type        	--> " + pay_type);
		logger.info("status       	 	--> " + status);
		logger.info("sign        		--> " + sign);
		logger.info("time        		--> " + time);
		
		if (!"1".equals(status)) {
			renderText("ERROR");
			return;
		}
		
		CompanyOrder order = CompanyOrder.dao.findFirst("select * from company_order where order_no = ?", order_id);
		if (order == null) {
			logger.error("订单不存在");
			renderText("ERROR");
			return;
		}
		
		String targetSign = EncryptUtils.encrypt(order_id + orderNo + money + mch + pay_type + time + EncryptUtils.encrypt(order.getRsaPrivate()));
		if (!sign.equals(targetSign)) {
			logger.error("签名：" + sign + " != " + targetSign);
			logger.error("签名错误");
			renderText("ERROR");
			return;
		}
		
		if (Double.parseDouble(money) * 100 < order.getOrderMoney().doubleValue()) {
			logger.error("支付金额错误");
			renderText("ERROR");
			return;
		}
		
		if ("已支付".equals(order.getStatus())) {
			renderSuccess(true);
			return;
		}
		// 修改订单状态
		
		order.setStatus("已支付");
		order.setPaytime(DateUtils.getYmdHmis(new Date()));
		order.setTradeNo(orderNo);
		order.setSettle("分账成功");
		order.setNotice("回调中");
		order.update();
		
		PayService payService = new PayService();
		payService.createBill(order);
		// 回调
		payService.notice(order);
		
		// 修改支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1, nopay_num = 0 where id = ?", order.getOrderMoney(), order.getAlipayId());
		renderSuccess(true);
	}
	
}
