package com.xunpay.money.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.utils.DateUtils;

public class IndexController extends BaseController {

	public void index() {
	}
	
	public void tip() {
		
		
	}

	public void welcome() {
		String date = getPara("date");
		if (null == date) {
			date = DateUtils.getYmd(new Date());
		}
		setAttr("date", date);
		String sql = "select company_id, (select nickname from company_info c where c.id = company_id) as company_name,date_format(addtime, '%Y-%m-%d') as date,count(*) as c,sum(order_money) as order_money,sum(company_rebate_money) as rebate_money,sum(agent_rebate_money) as agent_money,sum(order_money - company_rebate_money) as in_money, count(*) as c from company_order where status = '已支付' and date_format(addtime, '%Y-%m-%d') = '" + date + "' group by company_id";
		List<Record> rs = Db.find(sql);
		List<Record> srs = Db.find("select company_id, count(*) as c from company_order where date_format(addtime, '%Y-%m-%d') = '" + date + "' group by company_id");
		for (Record r : rs) {
			for (Record sr : srs) {
				if (r.getInt("company_id").equals(sr.getInt("company_id"))) {
					r.set("sr", (double) ((double) r.getLong("c")) / ((double) sr.getLong("c")));
				}
			}
		}
		setAttr("rs", rs);
	}
	
	public void drawNum() {
		long a = Db.queryLong("select count(*) from company_draw where status = '待审核'");
		long b = Db.queryLong("select count(*) from alipay_agent_draw where status = '待审核'");
		renderJson("{\"a\":" + a + ", \"b\":" + b + "}");
	}
}
