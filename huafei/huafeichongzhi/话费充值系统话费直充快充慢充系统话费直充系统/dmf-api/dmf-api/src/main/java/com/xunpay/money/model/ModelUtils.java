package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class ModelUtils {

	public static void loadModels(ActiveRecordPlugin arp) {
		arp.addMapping("company_channel", "id", CompanyChannel.class);
		arp.addMapping("alipay_agent", "id", AlipayAgent.class);
		arp.addMapping("alipay_agent_bill", "id", AlipayAgentBill.class);
		arp.addMapping("alipay_agent_draw", "id", AlipayAgentDraw.class);
		arp.addMapping("alipay_item", "id", AlipayItem.class);
		arp.addMapping("alipay_temp", "id", AlipayTemp.class);
		arp.addMapping("company_alipay", "id", CompanyAlipay.class);
		arp.addMapping("company_bill", "id", CompanyBill.class);
		arp.addMapping("company_draw", "id", CompanyDraw.class);
		arp.addMapping("company_info", "id", CompanyInfo.class);
		arp.addMapping("company_order", "id", CompanyOrder.class);
		arp.addMapping("company_order_notice", "id", CompanyOrderNotice.class);
		arp.addMapping("company_order_settle", "id", CompanyOrderSettle.class);
		arp.addMapping("company_settle_item", "id", CompanySettleItem.class);
		arp.addMapping("draw_item", "id", DrawItem.class);
		arp.addMapping("sys_config", "id", SysConfig.class);
		arp.addMapping("sys_resources", "id", SysResources.class);
		arp.addMapping("sys_task", "id", SysTask.class);
		arp.addMapping("sys_tianhao", "id", SysTianhao.class);
		arp.addMapping("sys_user", "id", SysUser.class);
		arp.addMapping("sys_user_resources", "id", SysUserResources.class);
		arp.addMapping("coke", "id", Coke.class);
		arp.addMapping("phone_delete", "id", PhoneDelete.class);
		arp.addMapping("cmcc_ck", "id", CmccCk.class);
		arp.addMapping("phone_yzm", "id", PhoneYzm.class);
		arp.addMapping("company_apiorder", "id",CompanyApiOrder.class);
		arp.addMapping("company_ip", "id",CompanyIp.class);
		arp.addMapping("company_agent_order", "id",CompanyAgentOrder.class);
	}
}