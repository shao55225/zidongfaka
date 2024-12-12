package com.xunpay.money.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyDraw;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanySettleItem;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.ShiroUtils;

public class FinanceController extends BaseController {

	public void listBill() {
		String select = "select *";
		String except = "from company_bill where company_id = " + ShiroUtils.getUserId();
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "orderNo", "order_no");
		except += getParaSql(args, "status", "status");
		except += getParaSql(args, "type", "type");
		keepPara();
		setAttr("page", AlipayAgentBill.dao.paginate(getParaToInt("page", 1), 30, select, except, args.toArray()));
	}
	
	public void listDraw() {
		String select = "select *";
		String except = "from company_draw where company_id = " + ShiroUtils.getUserId();
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "realname", "account");
		except += getParaSql(args, "status", "status");
		keepPara();
		setAttr("page", CompanyDraw.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	//申请提现===> 差额去除
	public void addDraw() {
		
		Integer id =ShiroUtils.getUserId();
		// 订单信息
		String sql1 = "select company_id, date_format(addtime, '%Y-%m-%d') as date,sum(order_money) as order_money,sum(company_rebate_money) as company_money,sum(order_money - company_rebate_money) as in_money from company_order where status = '已支付' and company_id = ? group by company_id, date_format(addtime, '%Y-%m-%d')";
		List<Record> rs = Db.find(sql1, id);

		// 商户提现
		String sql3 = "select date_format(addtime, '%Y-%m-%d') as date,sum(draw_money) as draw_money from company_draw where company_id = ? and status = '已审核' group by date_format(addtime, '%Y-%m-%d')";
		
		List<Record> rs3 = Db.find(sql3, id);
		
		BigDecimal intotle=new BigDecimal(0);
		
		BigDecimal dxftotle=new BigDecimal(0);
		
		BigDecimal margintotle=Db.queryBigDecimal("select  wechatrebate  from      company_info where id="+id+" ");
		
			for (Record r : rs) {
				
					intotle=intotle.add(r.getBigDecimal("in_money"));
					
			}
	
			for (Record r3 : rs3) {
				
					dxftotle=dxftotle.add(r3.getBigDecimal("draw_money"));
					
				}
		
		System.out.println("总收款===================>"+intotle);
		
		System.out.println("总下发===================>"+dxftotle);
		
		//去除冻结的
		System.out.println("总冻结===================>"+margintotle);
		
		//setAttr("balance", Db.queryBigDecimal("select balance from company_info where id = ?", ShiroUtils.getUserId()));
		
		BigDecimal draw_money=Db.queryBigDecimal("select sum(draw_money) from company_draw where status = '待审核' and company_id = " + id);
		
		BigDecimal trueMoney=new BigDecimal(0);
		
		if(draw_money!=null) {
			
			
			 trueMoney=intotle.subtract(dxftotle).subtract(margintotle).subtract(draw_money);
			
		}
		else{
			
			trueMoney=intotle.subtract(dxftotle).subtract(margintotle);
		 
		}
		
		Db.update("update company_info set balance =  ? where id = ?", trueMoney, id);
		
		setAttr("balance", trueMoney);
		
	}
	
	//提现操作
	public void saveDraw() {
		CompanyInfo company = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		CompanyDraw draw = getModel(CompanyDraw.class, "d");
		if (draw.getDrawMoney().doubleValue() <= 0 || draw.getDrawMoney().doubleValue() > company.getBalance().doubleValue()) {
			message(false, "转账金额必须小于等于余额且大于0", "/finance/listDraw");
			return;
		}
		
		/**
		// 计算手续费
		double drawMoney = draw.getDrawMoney().doubleValue();
		
	
		double chargeMoney = 0;
		int num = (int) (drawMoney / 50000);
		chargeMoney = num * 5;
		if (drawMoney % 50000 >= 20000) {
			chargeMoney += 5;
		} else if (drawMoney % 50000 < 20000 && drawMoney % 50000 > 0){
//			chargeMoney += 10;
			chargeMoney += 5;
		}
		
		**/
		
		draw.setChargeMoney(BigDecimal.valueOf(0));
		draw.setCompanyId(company.getId());
		draw.setCompanyName(company.getNickname());
		draw.setStatus("待审核");
		draw.setAddtime(new Date());
		draw.save();
		
		CompanyBill bill = new CompanyBill();
		bill.setCompanyId(company.getId());
		bill.setCompanyName(company.getNickname());
		bill.setDrawId(draw.getId());
		bill.setInMoney(BigDecimal.ZERO.subtract(draw.getDrawMoney()));
		bill.setType("用户提现");
		bill.setStatus("已结算");
		bill.setAddtime(new Date());
		// 计算余额
		Db.update("update company_info set balance = balance - ? where id = ?", draw.getDrawMoney(), ShiroUtils.getUserId());
		bill.setBalance(Db.queryBigDecimal("select balance from company_info where id = ?", ShiroUtils.getUserId()));
		bill.setOvertime(new Date());
		bill.save();
		
		message(true, "恭喜，申请提现成功", "/finance/listDraw");
	}
	
	public void settleItem() {
		setAttr("ss", CompanySettleItem.dao.find("select * from company_settle_item where company_id = ?", ShiroUtils.getUserId()));
	}
	
	public void addSettleItem() {}
	
	public void editSettleItem() {
		setAttr("s", CompanySettleItem.dao.findFirst("select * from company_settle_item where company_id = ? and id = ?", ShiroUtils.getUserId(), getParaToInt()));
	}
	
	public void saveSettleItem() {
		CompanySettleItem item = getModel(CompanySettleItem.class, "s");
		item.setCompanyId(ShiroUtils.getUserId());
		item.setAddtime(new Date());
		item.setSettleNum(0);
		item.setTotalMoney(BigDecimal.ZERO);
		item.save();
		message(true, "恭喜，收款支付宝添加成功", "/finance/settleItem");
	}
	
	public void updateSettleItem() {
		CompanySettleItem item = getModel(CompanySettleItem.class, "s");
		if (item.getCompanyId() != null) {
			message(true, "修改参数错误", "/finance/settleItem");
			return;
		}
		item.setCompanyId(ShiroUtils.getUserId());
		item.save();
		message(true, "恭喜，收款支付宝添加成功", "/finance/settleItem");
	}
	
	public void deleteSettleItem() {
		Db.update("delete from company_settle_item where company_id = ? and id = ?", ShiroUtils.getUserId(), getParaToInt());
		message(true, "恭喜，收款支付宝删除成功", "/finance/settleItem");
	}
	
	public void upSettleItem() {
		Db.update("update company_settle_item set status = '正常' where company_id = ? and id = ?", ShiroUtils.getUserId(), getParaToInt());
		redirect("/finance/settleItem");
	}
	
	public void downSettleItem() {
		Db.update("update company_settle_item set status = '禁用' where company_id = ? and id = ?", ShiroUtils.getUserId(), getParaToInt());
		redirect("/finance/settleItem");
	}
	
	public void createQr() {
		CompanyInfo company = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", company.getAppid());
		params.put("orderNo", "S" + System.currentTimeMillis() + (long) (Math.random() * 10000000L));
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		params.put("money", "1.00");
		params.put("sign", EncryptUtils.encrypt(params.get("appId") + "^"
							 + params.get("orderNo") + "^"
							 + params.get("money") + "^"
							 + params.get("timestamp") + "^" + company.getMd5key()));
		String url = "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/order/create";
		renderJson(HttpClientHelper.sendPost(url, params));
	}
	
	public void queryOrder() {
		String orderNo = getPara("orderNo");
		CompanyInfo company = CompanyInfo.dao.findById(ShiroUtils.getUserId());
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", company.getAppid());
		params.put("orderNo", orderNo);
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		params.put("sign", EncryptUtils.encrypt(params.get("appId") + "^"
				 + params.get("orderNo") + "^"
				 + params.get("timestamp") + "^" + company.getMd5key()));
		String url = "http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/order/query";
		renderJson(HttpClientHelper.sendPost(url, params));
	}
}
