package com.xunpay.money.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xunpay.money.model.SysUser;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.common.base.Objects;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 用户中心shiro域
 * 
 */
public class MyShiroRealm extends AuthorizingRealm implements Serializable {
	
	private static final long serialVersionUID = 8328703170741621322L;

	/** 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		ShiroUser user = (ShiroUser) pc.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(user.getPermissions());
		info.addRole(user.getRole());
		return info;
	}

	/** 认证回调函数,登录时调用 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String pwd = String.valueOf(token.getPassword());
		SysUser user = SysUser.dao.findFirst("select * from sys_user where username = ?", token.getUsername());
		if (user != null && StrKit.notBlank(pwd) && pwd.equals(user.getPassword())) {
			if (Constant.DISABLE.equals(user.getStatus())) {
				throw new DisabledAccountException("用户已被禁用");
			}
			ShiroUser shiroUser = new ShiroUser();
			shiroUser.setId(user.getId());
			shiroUser.setRealname(user.getNickname());
			shiroUser.setPassword(user.getPassword());
			shiroUser.setUsername(token.getUsername());
			shiroUser.setLastLogin(DateUtils.getYmdHmis(user.getLogintime()));
			shiroUser.setLastIp(user.getLoginip());
			shiroUser.setCurLogin(DateUtils.getYmdHmis(new Date()));
			shiroUser.setCurIp(token.getHost());
			
			List<Record> resources = null;
			if (user.getId() == 1) {
				shiroUser.setRole("admin");
				resources = Db.find("select *,'R,W' as auth from sys_resources");
			} else if ("管理员".equals(user.getStatus())){
				shiroUser.setRole("admin");
				resources = Db.find("select distinct a.*,b.auth from sys_resources a, sys_user_resources b where a.id = b.resources_id and b.user_id = ?", user.getId());
			} else {
				shiroUser.setRole("user");
				resources = Db.find("select distinct a.*,b.auth from sys_resources a, sys_user_resources b where a.id = b.resources_id and b.user_id = ?", user.getId());
			}
			for (Record r : resources) {
				if (!shiroUser.getPermissions().contains(r.getStr("group_name"))) {
					shiroUser.addPermission(r.getStr("group_name"));
				}
				shiroUser.addPermission(r.getStr("code"));
				String[] auths = r.getStr("auth").split(",");
				for (String auth : auths) {
					shiroUser.addPermission(r.getStr("code") + ":" + auth);
				}
			}
			user.setLogintime(new Date());
			user.setLoginip(token.getHost());
			user.update();
			return new SimpleAuthenticationInfo(shiroUser, pwd, shiroUser.getRealname());
		}
		throw new DisabledAccountException("用户名或密码错误");
	}

	/** 自定登录用户类，用户将登录的用户信息保存到系统中 */
	public class ShiroUser implements Serializable {

		private static final long serialVersionUID = 1671790724187302506L;

		private Integer id;
		private String username;
		private String password;
		private String realname;
		private String role;
		private String curIp;
		private String curLogin;
		private String lastIp;
		private String lastLogin;
		private List<String> permissions;

		public ShiroUser() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getRealname() {
			return realname;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getCurIp() {
			return curIp;
		}

		public void setCurIp(String curIp) {
			this.curIp = curIp;
		}

		public String getCurLogin() {
			return curLogin;
		}

		public void setCurLogin(String curLogin) {
			this.curLogin = curLogin;
		}

		public String getLastIp() {
			return lastIp;
		}

		public void setLastIp(String lastIp) {
			this.lastIp = lastIp;
		}

		public String getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(String lastLogin) {
			this.lastLogin = lastLogin;
		}

		public List<String> getPermissions() {
			if (permissions == null) {
				permissions = new ArrayList<String>();
			}
			return permissions;
		}

		public void setPermissions(List<String> permissions) {
			this.permissions = permissions;
		}
		
		public void addPermission(String permission) {
			if (permissions == null) {
				permissions = new ArrayList<String>();
			}
			permissions.add(permission);
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return username;
		}

		/**
		 * 重载hashCode,只计算username;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(username);
		}

		/**
		 * 重载equals,只计算username;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username)) {
				return false;
			}
			return true;
		}

	}
}
