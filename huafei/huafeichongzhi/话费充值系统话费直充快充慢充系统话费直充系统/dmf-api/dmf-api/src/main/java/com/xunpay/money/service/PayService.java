package com.xunpay.money.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundCouponOperationQueryRequest;
import com.alipay.api.request.AlipayFundCouponOrderDisburseRequest;
import com.alipay.api.request.AlipayFundCouponOrderPagePayRequest;
import com.alipay.api.request.AlipayTradeOrderSettleRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundCouponOperationQueryResponse;
import com.alipay.api.response.AlipayFundCouponOrderPagePayResponse;
import com.alipay.api.response.AlipayTradeOrderSettleResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayAgent;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.CompanyOrderNotice;
import com.xunpay.money.model.CompanyOrderSettle;
import com.xunpay.money.model.CompanySettleItem;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class PayService {

	private static final Logger logger = Logger.getLogger(PayService.class);
	
	public String getQrcode(CompanyOrder order) {
		if ("alipay_dmf".equals(order.getPayType())) {
			return alipayDmf(order);
		} else if ("alipay_hb".equals(order.getPayType())) {
			return alipayHb(order);
		} else if ("alipay_fund".equals(order.getPayType())){
			return new FundPayService().createOrder(order);
		} else if ("ypay".equals(order.getPayType())) {
			return new YPayService().createOrder(order);
		} else if ("paydog".equals(order.getPayType())) {
			return new PaydogService().createOrder(order);
		} else if ("alipay_wap".equals(order.getPayType())) {
			return new WapPayService().createOrder(order);
		} else if ("heipay".equals(order.getPayType())) {
			return new HeipayService().createOrder(order);
		} else if ("yunyifu".equals(order.getPayType())) {
			return new YunyifuService().createOrder(order);
		} else if ("yunzhifu".equals(order.getPayType())) {
			return new YzfService().createOrder(order);
		} else if ("scream".equals(order.getPayType())) {
			try {
				return new ScreamPayService().createOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("weixin".equals(order.getPayType())) {
			return new THDPayService().createOrder(order);
		} else if ("hypay".equals(order.getPayType())) {
			return new HyPayService().createOrder(order);
		} else if ("kuaizhan".equals(order.getPayType())) {
			return new KuaizhanService().createOrder(order);
		} else if ("eda".equals(order.getPayType())) {
			return new EdaPayService().createOrder(order);
		} else if ("ding".equals(order.getPayType())) {
			return new DingService().createOrder(order);
		} else if ("suixing".equals(order.getPayType())) {
			return new SuixinPayService().createOrder(order);
		} else if ("zhanshi".equals(order.getPayType())) {
			return new ZhanshiService().createOrder(order);
		} else if ("yigeku".equals(order.getPayType())) {
			return new YigekuService().createOrder(order);
		} else if ("xunfh".equals(order.getPayType())) {
			return new XunhfService().createOrder(order);
		} else if ("aipay".equals(order.getPayType())) {
			return new AipayBoxSevice().createOrder(order);
		} else if ("abjngd".equals(order.getPayType())) {
			return new AbjngdService().createOrder(order);
		} else if ("pingdd".equals(order.getPayType())) {
			return new PingddService().createOrder(order);
		} else if ("91pay".equals(order.getPayType())) {
			return new Pay91Service().createOrder(order);
		} else if ("9chao".equals(order.getPayType())) {
			return new NineChaoService().createOrder(order);
		} else if ("149297".equals(order.getPayType())) {
			return new Pay149297Service().createOrder(order);
		} else if ("wtvip".equals(order.getPayType())) {
			return new Wtvip6Service().createOrder(order);
		} else if ("htf".equals(order.getPayType())) {
			return new HtfService().createOrder(order);
		} else if ("yunso".equals(order.getPayType())) {
			return new YunSoService().createOrder(order);
		} else if ("niniyhj".equals(order.getPayType())) {
			return new NiniyhjService().createOrder(order);
		} else if ("huaFeiSYT".equals(order.getPayType())) {
			return new HuaFeiSYTService().creatOrder(order);
		}
		return null;
	}
	
	private String alipayHb(CompanyOrder order) {
		AlipayClient alipayClient = new com.xunpay.money.alipay.DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		AlipayFundCouponOrderPagePayRequest request = new AlipayFundCouponOrderPagePayRequest();
		request.setNotifyUrl(Db.queryStr("select content from sys_config where code = 'notice_url'") + "Hb");
		request.setBizContent("{" +
				"\"out_order_no\":\"" + order.getOrderNo() + "\"," +
				"\"out_request_no\":\"" + order.getOrderNo() + "\"," +
				"\"order_title\":\"发送红包\"," +
				"\"amount\":" + order.getOrderMoney() + "," +
				"\"pay_timeout\":\"1h\"," +
				"\"extra_param\":\"{\\\"merchantExt\\\":\\\"key=value\\\"}\"" +
				"  }");
		try {
			AlipayFundCouponOrderPagePayResponse response = alipayClient.execute(request);
			String html = response.getBody();
			html = html.substring(html.indexOf("https://qr.alipay.com/"));
			html = html.substring(0, html.indexOf("\""));
			return html;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 当面付产码
	 * @param alipay
	 * @param order
	 * @return
	 */
	private String alipayDmf(CompanyOrder order) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setNotifyUrl(Db.queryStr("select content from sys_config where code = 'notice_url'"));
		request.setBizContent("{" +
				"\"out_trade_no\":\"" + order.getOrderNo() + "\"," +
				"\"seller_id\":\"" + order.getPid() + "\"," +
				"\"total_amount\":" + order.getOrderMoney() + "," +
				"\"subject\":\"" + order.getTitle() + "\"," +
				"\"timeout_express\":\"5m\"," +
				"\"merchant_order_no\":\"" + order.getOutOrderNo() + "\"," +
				"\"qr_code_timeout_express\":\"5m\"" +
				"  }");
		try {
			AlipayTradePrecreateResponse response = null;
			for (int i = 0; i < 3; i++) {
				response = alipayClient.execute(request);
				if (response.isSuccess()) {
					// Db.update("update alipay_item set status = '待使用', lasttime = ? where id = ?", new Date(), order.getAlipayId());
					return response.getQrCode();
				}
			}
			Db.update("update alipay_item set status = '下线', remark = '产码失败，请检测是否清退' where id = ?", order.getAlipayId());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 分账结算
	 * @param alipay
	 * @param order
	 */
	public void settleDmfOrder(CompanyOrder order) {
		if (!"alipay_dmf".equals(order.getPayType()) 
				&& !"alipay_wap".equals(order.getPayType())
				&& !"alipay_hb".equals(order.getPayType())
				&& !"alipay_fund".equals(order.getPayType())) {
			return;
		}
		if ("分账成功".equals(order.getSettle())) {
			return;
		}
		if ("alipay_dmf".equals(order.getPayType()) || "alipay_fund".equals(order.getPayType()) || "alipay_wap".equals(order.getPayType())) {
			JSONArray settleArray = new JSONArray();
			List<CompanyOrderSettle> cosList = CompanyOrderSettle.dao.find("select * from company_order_settle where order_id = ?", order.getId());
			for (CompanyOrderSettle cos : cosList) {
				JSONObject item = new JSONObject();
				item.put("trans_out", cos.getTransOut().trim());
				item.put("trans_in", cos.getTransIn().trim());
				item.put("amount", cos.getSettleMoney());
				item.put("desc", String.valueOf(cos.getId()));
				settleArray.add(item);
			}
			
			JSONObject requestJson = new JSONObject();
			requestJson.put("out_request_no", order.getOrderNo());
			requestJson.put("trade_no", order.getTradeNo());
			requestJson.put("royalty_parameters", settleArray);
			requestJson.put("operator_id", "A0001");
			
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
			AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
			request.setBizContent(requestJson.toJSONString());
			try {
				order.setSettletime(new Date());
				AlipayTradeOrderSettleResponse response = alipayClient.execute(request);
				order.setSettleresult(response.getSubMsg());
				if (response.isSuccess()) {
					order.setSettle("分账成功");
					for (CompanyOrderSettle cos : cosList) {
						Db.update("update company_settle_item set settle_num = settle_num + 1, total_money = total_money + ?, today_money = today_money + ? where id = ?", cos.getSettleMoney(), cos.getSettleMoney(), cos.getItemId());
					}
					// 分账成功以后 创建账单
					createBill(order);
				} else {
					order.setSettle("分账失败");
					if ("isv.insufficient-isv-permissions".equals(response.getSubCode())) {
						Db.update("update alipay_item set status = '已封号' where id = ?", order.getAlipayId());
					}
				}
				order.update();
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
		} else if ("alipay_hb".equals(order.getPayType())) {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
			List<CompanyOrderSettle> cosList = CompanyOrderSettle.dao.find("select * from company_order_settle where order_id = ? and (remark IS NULL OR remark <> '分账成功')", order.getId());
			boolean success = true;
			for (CompanyOrderSettle cos : cosList) {
				AlipayFundCouponOrderDisburseRequest request = new AlipayFundCouponOrderDisburseRequest();
				request.setBizContent("{" +
				"\"out_order_no\":\"" + DateUtils.getDateSerializable("S", "yyMMddHHmmssSSS", 8) + "\"," +
				"\"deduct_auth_no\":\"" + order.getTradeNo() + "\"," +
				"\"deduct_out_order_no\":\"" + order.getOrderNo() + "\"," +
				"\"out_request_no\":\"" + DateUtils.getDateSerializable("S", "yyMMddHHmmssSSS", 8) + "\"," +
				"\"order_title\":\"红包打款\"," +
				"\"amount\":" + cos.getSettleMoney() + "," +
				"\"payee_user_id\":\"" + cos.getTransIn().trim() + "\"," +
				"\"pay_timeout\":\"1h\"," +
				"\"extra_param\":\"{\\\"merchantExt\\\":\\\"key=value\\\"}\"" +
				"  }");
				try {
					logger.info("红包分账请求：" + JSON.toJSONString(request));
					AlipayResponse response = alipayClient.execute(request);
					logger.info("红包分账返回：" + JSON.toJSONString(response));
					if (response.isSuccess()) {
						cos.setRemark("分账成功");
					} else {
						success = false;
						cos.setRemark("分账失败");
						order.setSettleresult(response.getSubMsg());
						if ("isv.insufficient-isv-permissions".equals(response.getSubCode())) {
							Db.update("update alipay_item set status = '已封号' where id = ?", order.getAlipayId());
						}
					}
					cos.update();
				} catch (AlipayApiException e) {
					e.printStackTrace();
					logger.error("红包分账报错：" + e.getMessage());
				}
			}
			if (success) {
				order.setSettle("分账成功");
				for (CompanyOrderSettle cos : cosList) {
					Db.update("update company_settle_item set settle_num = settle_num + 1, total_money = total_money + ?, today_money = today_money + ? where id = ?", cos.getSettleMoney(), cos.getSettleMoney(), cos.getItemId());
				}
				createBill(order);
			} else {
				order.setSettle("分账失败");
			}
			order.update();
		}
	}
	
	/**
	 * 拆分分账信息
	 * @param alipay
	 * @param order
	 */
	public void preSettleDmfOrder(CompanyOrder order) {
		if (!"alipay_dmf".equals(order.getPayType())
				&& !"alipay_wap".equals(order.getPayType())
				&& !"alipay_hb".equals(order.getPayType())
				&& !"alipay_fund".equals(order.getPayType())) {
			return;
		}
		BigDecimal settleMoney = order.getOrderMoney();
		// 结算给商户
		CompanySettleItem settleItem = CompanySettleItem.dao.findFirst("select * from company_settle_item where company_id = ? and status = '正常' and (max_money > today_money or max_money = 0) order by today_money asc limit 0, 1", order.getCompanyId());
		if (settleItem != null && StringUtils.isNotEmpty(settleItem.getPid())) {
			CompanyOrderSettle settle = new CompanyOrderSettle();
			settle.setItemId(settleItem.getId());
			settle.setOrderId(order.getId());
			settle.setOrderNo(order.getOrderNo());
			settle.setOrderMoney(order.getOrderMoney());
			settle.setAlipayId(order.getAlipayId());
			settle.setAlipayName(order.getAlipayName());
			settle.setTransOut(order.getPid());
			settle.setTransIn(settleItem.getPid().trim());
			settle.setTransInAccount(settleItem.getAlipayAccount());
			settle.setType(Constant.SH_SETTLE);
			settle.setSettleMoney(order.getOrderMoney().subtract(order.getCompanyRebateMoney()));
			settle.setCompanyId(order.getCompanyId());
			settle.save();
			
			settleMoney = settleMoney.subtract(settle.getSettleMoney());
		}
		
		// 获取平台的分账pid
		CompanySettleItem systemSettleItem = CompanySettleItem.dao.findFirst("select * from company_settle_item where company_id = 0 and status = '正常' and (max_money > today_money or max_money = 0) order by today_money asc limit 0, 1");
		// 结算佣金给平台
		CompanyOrderSettle settle = new CompanyOrderSettle();
		settle.setOrderId(order.getId());
		settle.setOrderNo(order.getOrderNo());
		settle.setOrderMoney(order.getOrderMoney());
		settle.setAlipayId(order.getAlipayId());
		settle.setAlipayName(order.getAlipayName());
		settle.setTransOut(order.getPid());
		if (systemSettleItem == null) {
			String pid = Db.queryStr("select content from sys_config where code = 'default_settle_pid'");
			settle.setTransIn(pid);
			settle.setTransInAccount("未设置,使用默认支付宝");
		} else {
			settle.setItemId(systemSettleItem.getId());
			settle.setTransIn(systemSettleItem.getPid().trim());
			settle.setTransInAccount(systemSettleItem.getAlipayAccount());
		}
		settle.setType(Constant.PT_SETTLE);
		
		BigDecimal psm = settleMoney.subtract(order.getOrderMoney().multiply(order.getAlipayRebate())).setScale(2, BigDecimal.ROUND_DOWN);
		settle.setSettleMoney(psm);
		settle.save();
	}

	/**
	 * 回调商户
	 * @param order
	 */
	public void notice(CompanyOrder order) {
		if ("已支付".equals(order.getStatus()) && StringUtils.isNotEmpty(order.getNoticeUrl())) {
			StringBuffer noticeUrl = new StringBuffer();
			if (!order.getNoticeUrl().startsWith("http://")
					&& !order.getNoticeUrl().startsWith("https://")) {
				noticeUrl.append("http://");
			}
			noticeUrl.append(order.getNoticeUrl());
			if (noticeUrl.indexOf("?") > 0) {
				noticeUrl.append("&");
			} else {
				noticeUrl.append("?");
			}
			CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());
			noticeUrl.append("appId=").append(company.getAppid());
			noticeUrl.append("&order_no=").append(order.getOrderNo());
			noticeUrl.append("&out_order_no=").append(order.getOutOrderNo());
			noticeUrl.append("&trade_money=").append(order.getOrderMoney());
			String tradeTime = order.getPaytime().replaceAll("-", "").replaceAll(" ", "").replace(":", "");
			noticeUrl.append("&trade_time=").append(tradeTime);
			noticeUrl.append("&rebate=").append(order.getCompanyRebate());
			noticeUrl.append("&rebate_money=").append(order.getCompanyRebateMoney());
			String sign = company.getAppid() + "^" + order.getOrderNo() + "^" + order.getOutOrderNo() + "^" + tradeTime + "^" + company.getMd5key();
			noticeUrl.append("&sign=").append(EncryptUtils.encrypt(sign));
			
			order.setNoticetime(new Date());
			String result = HttpClientHelper.sendGet(noticeUrl.toString());
			if (StringUtils.isNotEmpty(result)) {
				result = result.trim();
			}
			order.setNoticeresult(result);
			order.setNotice(Constant.SUCCESS.equals(result) ? "回调成功" : "回调失败");
			order.update();
			
			CompanyOrderNotice notice = new CompanyOrderNotice();
			notice.setOrderId(order.getId());
			notice.setOrderNo(order.getOrderNo());
			notice.setOutOrderNo(order.getOutOrderNo());
			notice.setNoticeUrl(noticeUrl.toString());
			notice.setNoticeTime(order.getNoticetime());
			notice.setNoticeNum(1);
			notice.setNoticeResult(result);
			notice.save();
		} else {
			order.setNotice("回调失败");
			order.update();
		}
	}

	/**
	 * 创建结算账单
	 * @param order
	 */
	public void createBill(CompanyOrder order) {
		if (Constant.YES.equals(order.getIsBill())) {
			return;
		}
		String defaultStatus = "已结算";
		// 结算订单商户
		CompanyInfo company = CompanyInfo.dao.findById(order.getCompanyId());
		
		// 查询是否已经分账
		CompanyOrderSettle cos = CompanyOrderSettle.dao.findFirst("select * from company_order_settle where order_id = ? and company_id = ?", order.getId(), company.getId());
		if (cos == null) {
			CompanyBill bill = new CompanyBill();
			bill.setCompanyId(company.getId());
			bill.setCompanyName(company.getUsername());
			bill.setOrderId(order.getId());
			bill.setOrderNo(order.getOrderNo());
			bill.setOrderMoney(order.getOrderMoney());
			bill.setTax(order.getCompanyRebate());
			bill.setTaxMoney(order.getCompanyRebateMoney());
			bill.setInMoney(order.getOrderMoney().subtract(order.getCompanyRebateMoney()));
			bill.setType("订单收款");
			bill.setBalance(company.getBalance().add(bill.getInMoney()));
			bill.setRemark("(1)未设置分账账号,转入余额");
			bill.setAddtime(new Date());
			bill.setStatus(defaultStatus);
			bill.save();
			Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), company.getId());
		}
		// 结算商户得上级代理
		int level = 2;
		while (company != null && company.getPid() != null && company.getPid() > 0) {
			CompanyInfo faterCompany = CompanyInfo.dao.findById(company.getPid());
			if (faterCompany != null) {
				CompanyBill bill = new CompanyBill();
				bill.setCompanyId(faterCompany.getId());
				bill.setCompanyName(faterCompany.getUsername());
				bill.setOrderId(order.getId());
				bill.setOrderNo(order.getOrderNo());
				bill.setOrderMoney(order.getOrderMoney());
				bill.setTax(company.getAlipayPcRebate().subtract(faterCompany.getAlipayPcRebate()));
				bill.setTaxMoney(order.getOrderMoney().multiply(bill.getTax()));
				bill.setInMoney(bill.getTaxMoney());
				bill.setBalance(faterCompany.getBalance().add(bill.getInMoney()));
				bill.setType("代理分红");
				bill.setRemark("(" + level + ")来自您的下线:" + company.getUsername());
				bill.setAddtime(new Date());
				bill.setStatus(defaultStatus);
				bill.save();
				Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), faterCompany.getId());
				level++;
			}
			company = faterCompany;
		}
		
		// 结算码商的账单
		AlipayAgent agent = AlipayAgent.dao.findById(order.getAgentId());
		if (agent != null) {
			AlipayAgentBill bill = new AlipayAgentBill();
			bill.setAgentId(agent.getId());
			bill.setAgentName(agent.getUsername());
			bill.setAlipayId(order.getAlipayId());
			bill.setAlipayName(order.getAlipayName());
			bill.setOrderId(order.getId());
			bill.setOrderNo(order.getOrderNo());
			bill.setOrderMoney(order.getOrderMoney());
			bill.setStatus(defaultStatus);
			bill.setType("订单收款");
			bill.setRebate(agent.getRebate().subtract(order.getAlipayRebate()));
			bill.setRebateMoney(order.getOrderMoney().multiply(bill.getRebate()));
			bill.setBalance(agent.getBalance().add(bill.getRebateMoney()));
			bill.setRemark("(1)");
			bill.setAddtime(new Date());
			bill.save();
			Db.update("update alipay_agent set balance = balance + ? where id = ?", bill.getRebateMoney(), agent.getId());
		}
		// 结算商户得上级代理
		level = 2;
		while (agent != null && agent.getPid() != null && agent.getPid() > 0) {
			AlipayAgent faterAgent = AlipayAgent.dao.findById(agent.getPid());
			if(faterAgent != null) {
				AlipayAgentBill bill = new AlipayAgentBill();
				bill.setAgentId(faterAgent.getId());
				bill.setAgentName(faterAgent.getUsername());
				bill.setAlipayId(order.getAlipayId());
				bill.setAlipayName(order.getAlipayName());
				bill.setOrderId(order.getId());
				bill.setOrderNo(order.getOrderNo());
				bill.setOrderMoney(order.getOrderMoney());
				bill.setStatus(defaultStatus);
				bill.setType("代理分红");
				bill.setRebate(faterAgent.getRebate().subtract(agent.getRebate()));
				bill.setRebateMoney(order.getOrderMoney().multiply(bill.getRebate()));
				bill.setBalance(faterAgent.getBalance().add(bill.getRebateMoney()));
				bill.setRemark("(" + level + ")来自您的下线:" + agent.getUsername());
				bill.setAddtime(new Date());
				bill.save();
				Db.update("update alipay_agent set balance = balance + ? where id = ?", bill.getRebateMoney(), faterAgent.getId());
				level++;
			}
			agent = faterAgent;
		}
		order.setIsBill(Constant.YES);
		order.update();
	}
	
	public void queryDmfOrder(CompanyOrder order) {
		if (!"alipay_dmf".equals(order.getPayType())
				&& !"alipay_wap".equals(order.getPayType())
				&& !"alipay_hb".equals(order.getPayType())
				&& !"alipay_fund".equals(order.getPayType())) {
			return;
		}
		if ("已支付".equals(order.getStatus())) {
			return;
		}
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				order.getAppid(), order.getRsaPrivate(), "json", "GBK", order.getRsaAlipay(), "RSA2");
		if ("alipay_dmf".equals(order.getPayType()) || "alipay_fund".equals(order.getPayType()) || "alipay_wap".equals(order.getPayType())) {
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\"" + order.getOrderNo() + "\"}");
			AlipayTradeQueryResponse response = null;
			try {
				response = alipayClient.execute(request);
			} catch (AlipayApiException e1) {
				e1.printStackTrace();
			}
			
			String status = response.getTradeStatus();
			// 只处理交易成功的回调
			if (!"TRADE_SUCCESS".equals(status)) {
				if (StringUtils.isNotEmpty(response.getBuyerUserId())) {
					order.setBuyerId(response.getBuyerUserId());
					order.setBuyerLogonId(response.getBuyerLogonId());
					order.update();
				}
				return;
			}
			// 修改订单状态
			order.setStatus("已支付");
			order.setPaytime(DateUtils.getYmdHmis(response.getSendPayDate()));
			order.setTradeNo(response.getTradeNo());
			order.setBuyerId(response.getBuyerUserId());
			order.setBuyerLogonId(response.getBuyerLogonId());
			order.setSettle("分账中");
			order.setNotice("回调中");
			order.update();
		} else if ("alipay_hb".equals(order.getPayType())) {
			AlipayFundCouponOperationQueryRequest request = new AlipayFundCouponOperationQueryRequest();
			//request.setBizContent("{\"auth_no\":\"" + order.getTradeNo() + "\", \"out_order_no\":\"" + order.getOrderNo() + "\"}");
			request.setBizContent("{" +
					//"\"auth_no\":\"" + order.getTradeNo() + "\"," +
					"\"out_order_no\":\"" + order.getOrderNo() + "\"," +
					//"\"operation_id\":\"20181231661935356302\"," +
					"\"out_request_no\":\"" + order.getOrderNo() + "\"" +
					"  }");
			try {
				AlipayFundCouponOperationQueryResponse response = alipayClient.execute(request);
				if (!"SUCCESS".equals(response.getStatus())) {
					return;
				}
				order.setTradeNo(response.getAuthNo());
				order.setStatus("已支付");
				order.setPaytime(DateUtils.getYmdHmis(response.getGmtTrans()));
				order.setBuyerId(response.getPayerUserId());
				order.setBuyerLogonId(response.getPayeeLogonId());
				order.setSettle("分账中");
				order.setNotice("回调中");
				order.update();
			} catch (AlipayApiException e) {
				e.printStackTrace();
				return;
			}
		}
		
		// 更新支付宝信息
		Db.update("update alipay_item set total_money = total_money + ?, pay_num = pay_num + 1 where id = ?", order.getOrderMoney(), order.getAlipayId());
		
		// 分账
		settleDmfOrder(order);
		// 回调
		notice(order);
		// 创建结算账单
		createBill(order);
	}
	
	
}
