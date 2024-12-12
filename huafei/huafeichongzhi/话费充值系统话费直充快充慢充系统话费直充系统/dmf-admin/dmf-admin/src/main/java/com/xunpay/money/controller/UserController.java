package com.xunpay.money.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.ext.plugin.shiro.ShiroKit;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.SysResources;
import com.xunpay.money.model.SysUser;
import com.xunpay.money.model.SysUserResources;
import com.xunpay.money.utils.ShiroUtils;

@RequiresPermissions("用户权限管理")
public class UserController extends BaseController {

	@RequiresPermissions("user:R")
	public void listUser() {
		String sql = "select * from sys_user where 1=1";
		List<Object> args = new ArrayList<Object>();
		sql += getParaSqlLikeWithOr(args, "account", "nickname", "username");
		sql += getParaSql(args, "status", "status");
		keepPara();
		setAttr("users", SysUser.dao.find(sql, args.toArray()));
	}
	
	@RequiresPermissions("user:W")
	public void addUser() {}
	
	@RequiresPermissions("user:W")
	public void saveUser() {
		SysUser user = getModel(SysUser.class, "u");
		user.save();
		message(true, "恭喜，用户添加成功", "/user/listUser");
	}
	
	@RequiresPermissions("user:W")
	public void editUser() {
		Integer id = getParaToInt();
		if (id == 1 && ShiroUtils.getUserId() != 1) {
			redirect(ShiroKit.getUnauthorizedUrl());
			return;
		}
		setAttr("u", SysUser.dao.findById(id));
	}
	
	@RequiresPermissions("user:W")
	public void deleteUser() {
		Integer id = getParaToInt();
		if (id == 1) {
			redirect(ShiroKit.getUnauthorizedUrl());
			return;
		}
		SysUser.dao.deleteById(id);
		message(true, "恭喜，用户删除成功", "/user/listUser");
	}
	
	@RequiresPermissions("user:W")
	public void authUser() {
		Integer id = getParaToInt();
		if (id == 1 && ShiroUtils.getUserId() != 1) {
			redirect(ShiroKit.getUnauthorizedUrl());
			return;
		}
		setAttr("u", SysUser.dao.findById(id));
		setAttr("rs", SysResources.dao.find("select * from sys_resources order by group_name"));
		setAttr("urs", SysUserResources.dao.find("select * from sys_user_resources where user_id = ?", id));
	}
	
	@RequiresPermissions("user:W")
	public void saveAuth() {
		Integer userId = getParaToInt("userId");
		String[] auths = getParaValues("auth");
		String[] authRW = getParaValues("authRW");
		List<String> rwList = Arrays.asList(authRW);
		Db.update("delete from sys_user_resources where user_id = ?", userId);
		for (String auth : auths) {
			SysUserResources sur = new SysUserResources();
			sur.setUserId(userId);
			sur.setResourcesId(Integer.parseInt(auth));
			String rw = "";
			if (rwList.contains(auth + "_R")) {
				rw += "R";
			}
			if (rwList.contains(auth + "_W")) {
				rw += "W";
			}
			if ("RW".equals(rw)) {
				rw = "R,W";
			}
			sur.setAuth(rw);
			sur.save();
		}
		message(true, "恭喜，权限设置成功", "/user/listUser");
	}
}
