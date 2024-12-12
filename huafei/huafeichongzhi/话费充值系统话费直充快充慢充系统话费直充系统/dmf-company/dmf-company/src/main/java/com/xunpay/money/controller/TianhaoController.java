package com.xunpay.money.controller;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.SysTianhao;
import com.xunpay.money.utils.HttpClientHelper;

public class TianhaoController extends BaseController  {

	public void list() {
		List<Record> items = Db.find("select * from sys_tianhao where user = 'tianhao'");
		setAttr("items", items);
	}
	
	public void load() {
		Integer id = getParaToInt();
		String url = Db.queryStr("select url from sys_tianhao where id = ?", id);
		renderJson(HttpClientHelper.sendGet(url));
	}
	
	public void address() {
		list();
	}
	
	public void add() {
		
	}
	
	public void save() {
		SysTianhao th = getModel(SysTianhao.class, "t");
		th.setUser("tianhao");
		th.save();
		message(true, "恭喜，添加成功", "/tianhao/address");
	}
	
	public void edit() {
		Integer id = getParaToInt();
		setAttr("t", SysTianhao.dao.findById(id));
	}
	
	public void update() {
		SysTianhao th = getModel(SysTianhao.class, "t");
		th.update();
		message(true, "恭喜，修改成功", "/tianhao/address");
	}
	
	public void delete() {
		Integer id = getParaToInt();
		Db.deleteById("sys_tianhao", id);
	}
}
