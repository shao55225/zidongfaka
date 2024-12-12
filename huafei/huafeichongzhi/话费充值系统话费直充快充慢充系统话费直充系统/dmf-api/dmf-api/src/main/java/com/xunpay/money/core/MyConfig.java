package com.xunpay.money.core;

import java.io.File;

import com.xunpay.money.controller.OrderController;
import com.xunpay.money.core.plugin.QuartzPlugin;
import com.xunpay.money.model.ModelUtils;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * 程序入口和配置中心
 * 2016年4月19日
 */
public class MyConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		PropKit.use("config.properties");
		constants.setDevMode(false);
		constants.setError403View(PropKit.get("web.403"));
		constants.setError404View(PropKit.get("web.404"));
		constants.setError500View(PropKit.get("web.500"));
		constants.setBaseViewPath(PropKit.get("web.view"));
		constants.setViewType(ViewType.JSP);
		constants.setEncoding("utf-8");
	}

	@Override
	public void configHandler(Handlers handlers) {

	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
	}

	@Override
	public void configPlugin(Plugins plugins) {
		loadPropertyFile(PropKit.get("db.config", "jdbc.properties"));
		// 添加c3p0连接池插件
		C3p0Plugin cp = new C3p0Plugin(getProperty("jdbc.url", "localhost"),
				getProperty("jdbc.username", "root"),
				getProperty("jdbc.password", ""));
		plugins.add(cp);
		
		// 数据库model映射插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		arp.setShowSql(false);
		// 加载所有的model
		ModelUtils.loadModels(arp);
		plugins.add(arp);
		plugins.add(new QuartzPlugin());
	}

	@Override
	public void configRoute(Routes routes) {
		try {
			ControllerRoutes.loadRoutes(routes, new File(getClass().getResource("/").getFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
