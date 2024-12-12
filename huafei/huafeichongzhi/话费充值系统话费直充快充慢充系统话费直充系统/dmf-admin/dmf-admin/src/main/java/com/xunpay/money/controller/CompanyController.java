package com.xunpay.money.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.AlipayAgent;
import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyAlipay;
import com.xunpay.money.model.CompanyApiInfo;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyChannel;
import com.xunpay.money.model.CompanyDraw;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.DrawItem;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.ShiroUtils;

@RequiresPermissions("商户管理")
public class CompanyController extends BaseController {

	/////////////////////////////////////////////////////////////
	
	@RequiresPermissions("company")
		
	/**
	 * 商户的通道管理
	 * 	
	 * **/
	public void channelMgr() {
		
		String id = getPara();
		
		String select = " SELECT \r\n" + 
				"	channel.code, \r\n" + 
				"	channel.type, \r\n" + 
				"	channel.nickname,\r\n" + 
				"	child.rebate,\r\n" + 
				"	child.`status`,\r\n" + 
				"	info.id,\r\n" + 
				"	info.username \r\n" + 
				"FROM\r\n" + 
				"	company_channel AS channel\r\n" + 
				"	LEFT JOIN company_channel_child AS child ON child.chanid = channel.id\r\n" + 
				"	LEFT JOIN company_info AS info ON child.infoid = info.id WHERE   info.id = '"+id+"' ";
				
		List<Record> recordList =Db.find(select);
		
		keepPara();
		
		setAttr("page",recordList);
	}
	
	/**
	 *  商户通道管理保存
	 *  
	 * **/
	
	public void channelSave() {
		
		String id=getPara("id");
		
		String rebate=getPara("rebate");
		
		String status=getPara("status");
		
		String code=getPara("code");
		
		Integer channelId=Db.queryInt("select id from company_channel where code ='"+code+"' ");
		
		
		Db.update("delete from company_settle_item where company_id = ? and id = ?", ShiroUtils.getUserId(), getParaToInt());
		
		//查询 company_channel_child的id
        int childId=Db.queryInt("select id from  company_channel_child  where   infoid ='"+id+"' and chanid='"+channelId+"'  ");
	
		int row=Db.update("update company_channel_child set infoid = ? , rebate = ? ,status =?  where id = ? ", id,rebate,status,childId);
		
		System.out.println("保存受影响的行数为=======》"+row);
		
		if(row==1) {
			
			result(true, "保存成功", null);
		}
	}	
	
	@RequiresPermissions("company")
	public void listCompany() {
		String select = "select *";
		String except = "from company_info where is_del = 'N' ";
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		except += getParaSql(args, "status", "status");
		keepPara();
		setAttr("page", CompanyInfo.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	
	@RequiresPermissions("company")
	public void listApiCompany() {
		String select = "select *";
		String except = "from company_api_info where is_del = 'N' ";
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		except += getParaSql(args, "status", "status");
		keepPara();
		setAttr("page", CompanyInfo.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	
	@RequiresPermissions("company:W")
	public void addCompany() {}
	
	@RequiresPermissions("company:W")
	public void saveCompany() {
		
		CompanyInfo company = getModel(CompanyInfo.class, "c");
		
		company.setBalance(BigDecimal.ZERO);
		
		company.setForSelf(Constant.NO);
		
		company.setAddtime(new Date());
		
		company.save();
		
		List<CompanyChannel> companyChannel=CompanyChannel.dao.find("	select id   from company_channel  where  is_del='N' and  status='正常' ");
		
		//更新company_channel_child
//		for (int i = 0; i < companyChannel.size(); i++) {
//			
//			String updateSql="	INSERT INTO `pay`.`company_channel_child`"
//					
//			+ " (`infoid`, `chanid`, `rebate`, `status`) VALUES ('"+company.getId()+"', '"+companyChannel.get(i).getId()+"', '0', '正常')";
//			
//	        Db.update(updateSql);	
//		}
//		
		message(true, "恭喜，商户添加成功", "/company/listCompany");
	}
	
	@RequiresPermissions("company:W")
	public void editCompany() {
		CompanyInfo company = CompanyInfo.dao.findById(getParaToInt());
		setAttr("c", company);
	}
	
	@RequiresPermissions("company:W")
	public void updateCompany() {
		CompanyInfo company = getModel(CompanyInfo.class, "c");
		company.update();
		message(true, "恭喜，商户修改成功", "/company/listCompany");
	}
	
	@RequiresPermissions("company:W")
	public void deleteCompany() {
		Db.update("update company_info set is_del = 'Y' where id = ?", getParaToInt());
		message(true, "恭喜，商户删除成功", "/company/listCompany");
	}
	
	@RequiresPermissions("company:W")
	public void addApiCompany() {}
	
	@RequiresPermissions("company:W")
	public void saveApiCompany() {
		
		CompanyApiInfo company = getModel(CompanyApiInfo.class, "c");
		
		company.setBalance(BigDecimal.ZERO);
		
		company.setForSelf(Constant.NO);
		
		company.setAddtime(new Date());
		
		company.save();
		
		message(true, "恭喜，商户添加成功", "/company/listApiCompany");
	}
	
	@RequiresPermissions("company:W")
	public void editApiCompany() {
		CompanyApiInfo company = CompanyApiInfo.dao.findById(getParaToInt());
		setAttr("c", company);
	}
	
	@RequiresPermissions("company:W")
	public void updateApiCompany() {
		CompanyApiInfo company = getModel(CompanyApiInfo.class, "c");
		company.update();
		message(true, "恭喜，商户修改成功", "/company/listApiCompany");
	}
	
	@RequiresPermissions("company:W")
	public void deleteApiCompany() {
		Db.update("update company_api_info set is_del = 'Y' where id = ?", getParaToInt());
		message(true, "恭喜，商户删除成功", "/company/listApiCompany");
	}
	
	
	
	@RequiresPermissions("company:R")
	public void report() {
		Integer id = getParaToInt();
		// 订单信息
		String sql1 = "select company_id, date_format(addtime, '%Y-%m-%d') as date,sum(order_money) as order_money,sum(company_rebate_money) as company_money,sum(order_money - company_rebate_money) as in_money from company_order where status = '已支付' and company_id = ? group by company_id, date_format(addtime, '%Y-%m-%d')";
		List<Record> rs = Db.find(sql1, id);
		// 代理分红
		String sql2 = "select date_format(addtime,'%Y-%m-%d') as date, sum(in_money) as agent_money from company_bill where company_id = ? and type= '代理分红' group by date_format(addtime,'%Y-%m-%d')";
		List<Record> rs2 = Db.find(sql2, id);
		// 商户提现
		String sql3 = "select date_format(addtime, '%Y-%m-%d') as date,sum(draw_money) as draw_money from company_draw where company_id = ? and status = '已审核' group by date_format(addtime, '%Y-%m-%d')";
		List<Record> rs3 = Db.find(sql3, id);
		// 支付宝分账
		String sql4 = "select date_format(addtime, '%Y-%m-%d') as date,sum(settle_money) as settle_money from `company_order_settle` s left join company_order o on s.order_id = o.id where s.company_id = ? and o.settle = '分账成功' and o.settleresult is null group by date_format(addtime, '%Y-%m-%d')";
		List<Record> rs4 = Db.find(sql4, id);
		
		// 支付宝分账失败
		String sql5 = "select date_format(addtime, '%Y-%m-%d') as date,sum(order_money - company_rebate_money) as settle_error_money from `company_order` where company_id = ? and settle = '分账失败' group by date_format(addtime, '%Y-%m-%d')";
		List<Record> rs5 = Db.find(sql5, id);
		
		CompanyInfo company = CompanyInfo.dao.findById(id);
		Calendar c = Calendar.getInstance();
		c.setTime(null == company.getAddtime()? (new Date()):company.getAddtime()); 
		String itDate = DateUtils.getYmd(c.getTime());
		String curDate = DateUtils.getYmd(new Date());
		
		List<Record> result = new ArrayList<Record>();
		while (curDate.compareTo(itDate) > -1) {
			boolean add = false;
			Record row = new Record();
			for (Record r : rs) {
				if (itDate.equals(r.getStr("date"))) {
					row.setColumns(r);
					add = true;
				}
			}
			
			for (Record r2 : rs2) {
				if (itDate.equals(r2.getStr("date"))) {
					row.setColumns(r2);
					add = true;
				}
			}
			
			for (Record r3 : rs3) {
				if (itDate.equals(r3.getStr("date"))) {
					row.setColumns(r3);
					add = true;
				}
			}
			
			for (Record r4 : rs4) {
				if (itDate.equals(r4.getStr("date"))) {
					row.setColumns(r4);
					add = true;
				}
			}
			
			for (Record r5 : rs5) {
				if (itDate.equals(r5.getStr("date"))) {
					row.setColumns(r5);
					add = true;
				}
			}
			
			if (add) {
				result.add(row);
			}
			c.add(Calendar.DATE, 1);
			itDate = DateUtils.getYmd(c.getTime());
		}
		setAttr("id",id);
		setAttr("result", result);
		setAttr("c", company);
		setAttr("dxf", Db.queryBigDecimal("select sum(draw_money) from company_draw where status = '待审核' and company_id = " + id));
	}
	
	//2132.650
	@RequiresPermissions("company:R")
	public void checkBill() {
		setAttr("bills", Db.find("select order_no, order_money, tax, tax_money, in_money, balance, type, addtime from company_bill where company_id = ?", getParaToInt()));
	}
	
	@RequiresPermissions("company:W")
	public void forAlipay() {
		Integer cid = getParaToInt();
		Db.update("delete from company_alipay where alipay_id not in (select id from alipay_item where is_del = 'N')");
		setAttr("cid", cid);
		setAttr("caList", CompanyAlipay.dao.find("select * from company_alipay where company_id = ?", cid));
		setAttr("alipays", AlipayItem.dao.find("select * from alipay_item"));
	}
	
	@RequiresPermissions("company:W")
	public void addAlipay() {
		Integer cid = getParaToInt("cid");
		Integer aid = getParaToInt("aid");
		CompanyAlipay ca = new CompanyAlipay();
		ca.setAlipayId(aid);
		ca.setCompanyId(cid);
		ca.save();
		renderSuccess(true);
	}
	
	@RequiresPermissions("company:W")
	public void removeAlipay() {
		Integer cid = getParaToInt("cid");
		Integer aid = getParaToInt("aid");
		Db.update("delete from company_alipay where company_id = ? and alipay_id = ?", cid, aid);
		renderSuccess(true);
	}
	
	/////////////////////////////////////////////////////////////
	
	@RequiresPermissions("company_draw")
	public void listDraw() {
		
		String addtime = getPara("addtime");
		
		String status = getPara("status");
		
		String select = "select *";
		
		String except = "from company_draw where 1=1 ";
		
		if (null == status) {
			
			except += " and status ='待审核' ";
			
			setAttr("status", "待审核");
			
		}
	
		if (StringUtils.isNotEmpty(addtime)) {
			except += " and date_format(addtime, '%Y-%m-%d') = '" + addtime + "'";
		}
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "company_name", "realname", "account");
		except += getParaSql(args, "status", "status");
		except += " order by id desc";
		keepPara();
		setAttr("sysUser", ShiroUtils.getUsername());
		setAttr("page", CompanyDraw.dao.paginate(getParaToInt("page", 1), 10, select, except, args.toArray()));
	}
	
	@RequiresPermissions("company_draw:W")
	public void approveDraw() {
		CompanyDraw draw = CompanyDraw.dao.findById(getParaToInt());
		if (!"待审核".equals(draw.getStatus())) {
			message(false, "对不起，提现状态错误", "/company/listDraw");
			return;
		}
		if (!ShiroUtils.getUsername().equals(draw.getSysUsername())) {
			message(false, "该笔提现已经被【" + draw.getSysUsername() + "】锁单了，您无法操作", "/company/viewDraw/" + draw.getId());
			return;
		}
		draw.setStatus("已审核");
		draw.setDrawtime(new Date());
		draw.update();
		
		BigDecimal drawMoney = draw.getDrawMoney();
		while (drawMoney.doubleValue() > 0) {
			DrawItem item = new DrawItem();
			item.setType(Constant.COMPANY_DRAW);
			item.setDrawId(draw.getId());
			item.setStatus("待处理");
			if (drawMoney.doubleValue() > Constant.MAX_DRAWMONEY) {
				item.setDrawMoney(BigDecimal.valueOf(Constant.MAX_DRAWMONEY));
				drawMoney = drawMoney.subtract(item.getDrawMoney());
			} else {
				item.setDrawMoney(drawMoney);
				drawMoney = BigDecimal.ZERO;
			}
			item.setAddtime(new Date());
			item.save();
		}
		message(true, "恭喜，已审核通过", "/company/viewDraw/" + draw.getId());
	}
	
	@RequiresPermissions("company_draw:W")
	public void rejectDraw() {
		CompanyDraw draw = CompanyDraw.dao.findById(getParaToInt());
		if (!"待审核".equals(draw.getStatus())) {
			message(false, "对不起，提现状态错误", "/company/listDraw");
			return;
		}
		if (!ShiroUtils.getUsername().equals(draw.getSysUsername())) {
			message(false, "该笔提现已经被【" + draw.getSysUsername() + "】锁单了，您无法操作", "/company/viewDraw/" + draw.getId());
			return;
		}
		draw.setStatus("已拒绝");
		draw.setDrawtime(new Date());
		draw.update();
		
		// 生成驳回账单
		Db.update("update company_info set balance = balance + ? where id = ?", draw.getDrawMoney(), draw.getCompanyId());
		CompanyBill bill = new CompanyBill();
		bill.setCompanyId(draw.getCompanyId());
		bill.setCompanyName(draw.getCompanyName());
		bill.setDrawId(draw.getId());
		bill.setInMoney(draw.getDrawMoney());
		bill.setType("提现驳回");
		bill.setStatus("已结算");
		bill.setRemark(draw.getRemark());
		bill.save();
		
		message(true, "该笔提现已经拒绝", "/company/viewDraw/" + draw.getId());
	}
	
	@RequiresPermissions("company_draw:R")
	public void viewDraw() {
		CompanyDraw draw = CompanyDraw.dao.findById(getParaToInt());
		List<DrawItem> items = DrawItem.dao.find("select * from draw_item where type = ? and draw_id = ?", Constant.COMPANY_DRAW, draw.getId());
		setAttr("d", draw);
		setAttr("items", items);
		setAttr("sysUser", ShiroUtils.getUsername());
	}
	
	@RequiresPermissions("company_draw:W")
	public void reloadDraw() {
		DrawItem item = DrawItem.dao.findById(getParaToInt());
		if (!"转账失败".equals(item.getStatus())) {
			renderSuccess(false);
			return;
		}
		item.setStatus("已重新发起");
		item.update();
		
		DrawItem newItem = new DrawItem();
		newItem.setType(item.getType());
		newItem.setDrawId(item.getDrawId());
		newItem.setStatus("待处理");
		newItem.setDrawMoney(item.getDrawMoney());
		newItem.setChargeMoney(item.getChargeMoney());
		newItem.setRemark(ShiroUtils.getUsername() + "-新发起");
		newItem.setAddtime(new Date());
		newItem.save();
		redirect("/company/viewDraw/" + item.getDrawId());
	}
	
	@RequiresPermissions("company_draw:W")
	public void lockDraw() {
		CompanyDraw draw = CompanyDraw.dao.findById(getParaToInt());
		if (!"待审核".equals(draw.getStatus())) {
			message(false, "对不起，提现状态错误", "/company/viewDraw/" + draw.getId());
			return;
		}
		if (StringUtils.isNotEmpty(draw.getSysUsername())) {
			message(false, "该笔提现已经被【" + draw.getSysUsername() + "】锁单了，您无法锁单", "/company/viewDraw/" + draw.getId());
			return;
		}
		draw.setSysUsername(ShiroUtils.getUsername());
		draw.update();
		redirect("/company/viewDraw/" + draw.getId());
	}
	
	@RequiresPermissions("company_draw:W")
	public void unlockDraw() {
		CompanyDraw draw = CompanyDraw.dao.findById(getParaToInt());
		if (!"待审核".equals(draw.getStatus())) {
			message(false, "对不起，提现状态错误", "/company/viewDraw/" + draw.getId());
			return;
		}
		if (!ShiroUtils.getUsername().equals(draw.getSysUsername())) {
			message(false, "该笔提现已经被【" + draw.getSysUsername() + "】锁单了，您无法解锁", "/company/viewDraw/" + draw.getId());
			return;
		}
		draw.setSysUsername(null);
		draw.update();
		redirect("/company/viewDraw/" + draw.getId());
	}
	
	@RequiresPermissions("company_draw:W")
	public void setDrawRemark() {
		Db.update("update company_draw set remark = ? where id = ?", getPara("remark"), getParaToInt("id"));
		renderNull();
	}
	
	@RequiresPermissions("company:W")
	@RequiresRoles("admin")
	public void addBalance() {
		
		Integer id  = getParaToInt("id");
		
		Double amount = Double.parseDouble(getPara("amount"));
		
		Db.update("update company_info set balance = balance + ? where id = ?", amount, id);
		
		CompanyBill bill = new CompanyBill();
		bill.setCompanyId(id);
		bill.setInMoney(BigDecimal.valueOf(amount));
		bill.setType("加额");
		bill.setRemark("管理员" + ShiroUtils.getUsername() + "加额");
		bill.setStatus("已结算");
		CompanyInfo company = CompanyInfo.dao.findById(id);
		bill.setBalance(company.getBalance());
		bill.setCompanyName(company.getNickname());
  		bill.setAddtime(new Date());
		bill.save();
		
		//存正数
		if(amount<0) {
		
				Double freeze = Math.abs(amount);
			
				Db.update("update company_info set wechatrebate = wechatrebate + ? where id = ?", freeze, id);
		
		}
		
		renderSuccess(true);
	}
	
	//如果差额大于0自动去除差额 
	public void updateBalance() {
		
		Integer id  = getParaToInt("id");
		
		Double amount = Double.parseDouble(getPara("amount"));
		
		Db.update("update company_info set balance = balance - ? where id = ?", amount, id);
		
		System.out.println("=======>差额自动调整成功=============>");
		
		renderSuccess(true);
		
		
	}
	
	//如果差额小于0则保存差额
	public void saveMargin() {
		
		Integer id  = getParaToInt("id");
		
		//存正数
		Double amount = Math.abs(Double.parseDouble( getPara("amount")));
		
		Db.update("update company_info set wechatrebate = ? where id = ?", amount, id);

		System.out.println("=======>差额保存成功=============>");
		
		renderSuccess(true);
		
	}
	

	
	
	
}
