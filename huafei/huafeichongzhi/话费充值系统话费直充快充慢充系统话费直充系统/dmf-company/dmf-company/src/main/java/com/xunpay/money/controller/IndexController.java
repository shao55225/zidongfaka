package com.xunpay.money.controller;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.utils.ShiroUtils;

public class IndexController extends BaseController {

	public void index() {
		//select balance  from company_bill order by id  desc  limit  1

		List<CompanyInfo> cs = CompanyInfo.dao.find("select * from company_info where pid = ?", ShiroUtils.getUserId());
	
		setAttr("cs", cs);
	}
	
	public void welcome() {
		
		List<Record> rs = Db.find("select date_format(addtime, '%Y-%m-%d') as date,count(*) as c,sum(order_money) as order_money, sum(order_money - company_rebate_money) as in_money from company_apiorder where company_id = ? and status = '充值成功' group by date_format(addtime, '%Y-%m-%d') order by date desc", ShiroUtils.getUserId());
		setAttr("balance", Db.queryBigDecimal("select balance from company_info where id = ?", ShiroUtils.getUserId()));
		setAttr("rs", rs);
	}
	
	public void apidoc() {
		setAttr("c", CompanyInfo.dao.findById(ShiroUtils.getUserId()));
		setAttr("api_host", Db.queryStr("select content from sys_config where code = 'api_host'"));
	}

}
