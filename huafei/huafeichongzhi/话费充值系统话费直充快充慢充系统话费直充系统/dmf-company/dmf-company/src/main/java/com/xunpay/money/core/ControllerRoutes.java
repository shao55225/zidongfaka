package com.xunpay.money.core;

import java.io.File;

import com.xunpay.money.model.annotation.View;

import org.apache.commons.lang.StringUtils;

import com.jfinal.config.Routes;
import com.jfinal.kit.StrKit;

/**
 * 根据‘约定’配置路由（约定大于配置）
 * 1，controller类必须继承
 * 2，controller类必须在BASEDIR目录下
 * 3，controller类必须以Controller结尾
 * 4，路由url，以BASEDIR为“/”目录，如[BASEDIR].user.LoginController则url为/user/login
 * 5，IndexController代码认为他为“/”
 * 2016年4月19日
 */
public class ControllerRoutes {

	private static final String CLASSPATH = new File(ControllerRoutes.class.getResource("/").getFile()).getPath();
	private static final String BASEDIR = "com.xunpay.money.controller";
	private static final String SUFFIX = "Controller.class";
	
	@SuppressWarnings("all")
	public static void loadRoutes(Routes routes, File f) throws Exception {
		File files[] = f.listFiles();
		for (File file : files) {
			String path = file.getPath().substring(CLASSPATH.length() + 1);
			String className = path.replace(File.separator, ".");
			if (file.isDirectory()) {
				loadRoutes(routes, file);
			} else if (className.startsWith(BASEDIR) && className.endsWith(SUFFIX)) {
				String[] ks = className.replace(BASEDIR, "").replace(SUFFIX, "").split("\\.");
				String packageName = "";
				String controllerName = "";
				for (int i = 0; ks !=null && i < ks.length; i++) {
					String k = ks[i];
					if (StringUtils.isNotEmpty(k)) {
						if (i < ks.length - 1) {
							packageName += "/" + StrKit.firstCharToLowerCase(k);
						} else if (!"index".equalsIgnoreCase(k)) {
							controllerName += "/" + StrKit.firstCharToLowerCase(k);
						}
					}
				}
				if (StringUtils.isEmpty(packageName) && StringUtils.isEmpty(controllerName)) {
					controllerName = "/";
				}
				Class clazz = Class.forName(className.replace(".class", ""));
				View view = (View) clazz.getAnnotation(View.class);
				if (view != null) {
					routes.add(packageName + controllerName, clazz, view.value());
				} else {
					routes.add(packageName + controllerName, clazz, packageName + controllerName);
				}
			}
		}
	}
	
}
