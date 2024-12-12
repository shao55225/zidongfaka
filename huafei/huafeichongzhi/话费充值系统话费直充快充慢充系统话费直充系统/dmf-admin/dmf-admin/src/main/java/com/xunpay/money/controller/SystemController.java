package com.xunpay.money.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.PhoneYzm;
import com.xunpay.money.model.SysConfig;

@RequiresPermissions("系统设置")
public class SystemController extends BaseController {

	@RequiresPermissions("config")
	public void listConfig() {
		setAttr("cs", SysConfig.dao.find("select * from sys_config"));
	}
	
	@RequiresPermissions("config:W")
	public void updateConfig() {
		Integer id = getParaToInt("id");
		String content = getPara("content");
		int row = Db.update("update sys_config set content = ? where id = ?", content, id);
		renderSuccess(row > 0);
//		SysConfig config = getModel(SysConfig.class, "c");
//		config.update();
//		message(true, "恭喜，系统参数修改成功", "/system/listConfig");
	}
	
	public void editConfig() {
		SysConfig config = SysConfig.dao.findById(getParaToInt());
		setAttr("c", config);
	}
	
	@RequiresPermissions("config:W")
	public void switchChannel() {
		setAttr("cs", Db.queryStr("select content from sys_config where code = 'default_type'").split(","));
	}
	
	@RequiresPermissions("config:W")
	public void updateChannel() {
		String[] channels = getParaValues("channel");
		String content = "";
		for (String channel : channels) {
			content += channel + ",";
		}
		if (content.endsWith(",")) {
			content = content.substring(0, content.length() - 1);
		}
		Db.update("update sys_config set content = ? where code = 'default_type'", content);
		message(true, "恭喜，设置成功", "/system/switchChannel");
	}
	
	
	public void ipConfig() {
		
		String select = "select * ";
		String except = " from phone_yzm ";
		List<Object> args = new ArrayList<Object>();
		except += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		except += getParaSql(args, "status", "status");
		keepPara();
		setAttr("page", PhoneYzm.dao.paginate(getParaToInt("page", 1), 40, select, except, args.toArray()));
		
		//setAttr("page", PhoneYzm.dao.paginate(getParaToInt("page", 1), 40);
	}
	
	public void updateIp() {
		Integer id = getParaToInt("id");
		String yzm = getPara("yzm");
		int row = Db.update("update phone_yzm set yzm = ? where id = ?", yzm, id);
		renderSuccess(row > 0);

	}
	
	
}
