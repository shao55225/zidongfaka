package com.xunpay.money.core;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.xunpay.money.utils.Constant;
import com.xunpay.money.utils.DateUtils;

public class BaseController extends Controller {
	
	protected void renderSuccess(boolean success) {
		renderText(success ? Constant.SUCCESS : Constant.FAIL);
	}
	
	protected void result(boolean success, String message, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", success ? Constant.SUCCESS : Constant.FAIL);
		result.put("msg", message);
		result.put("data", data == null ? new Object() : data);
		renderJson(result);
	}
	
	protected void message(boolean success, String message, String dumpUrl) {
		setAttr("message", message);
		setAttr("dumpUrl", getRequest().getContextPath() + dumpUrl);
		renderJsp(success ? "/WEB-INF/view/success.jsp" : "/WEB-INF/view/error.jsp");
	}
	
	protected String getParaSql(String key, String column) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and " + column + " = '" + _key + "'";
		}

		return result;
	}

	protected String getParaSql(List<Object> args, String key, String column) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and " + column + " = ?";
			args.add(_key);
		}

		return result;
	}

	protected String getParaSqlLike(String key, String column) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and " + column + " like '%" + _key + "%'";
		}

		return result;
	}

	protected String getParaSqlLike(List<Object> args, String key, String column) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and " + column + " like ?";
			args.add("%" + _key + "%");
		}

		return result;
	}
	protected String getParaDateCompare(String beginDateKey, String endDateKey, String column) {
		String _beginDateKey =beginDateKey; //getPara(beginDateKey);
		String _endDateKey =endDateKey; 

		String result = "";
		if (StrKit.notBlank(_beginDateKey)) {
			result += " and " + column + " >= '" + _beginDateKey.trim() + "'";
		}

		if (StrKit.notBlank(_endDateKey)) {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_endDateKey.trim()));
			c.add(Calendar.DATE, 1);

			result += " and " + column + "<= '" + _endDateKey.trim() + "'";
		}

		return result;
	}


	protected String getParaStringDateColumnCompare(String beginDateKey, String endDateKey, String stringDateColumn) {
		String _beginDateKey = getPara(beginDateKey);
		String _endDateKey = getPara(endDateKey);

		String result = "";
		if (StrKit.notBlank(_beginDateKey)) {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_beginDateKey.trim()));

			result += " and date_format(" + stringDateColumn + ", '%Y-%m-%d %H:%i:%s') >= date_format('"
					+ DateUtils.getYmdHmis(c.getTime()) + "', '%Y-%m-%d %H:%i:%s')";
		}

		if (StrKit.notBlank(_endDateKey)) {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_endDateKey.trim()));
			c.add(Calendar.DATE, 1);

			result += " and date_format(" + stringDateColumn + ", '%Y-%m-%d %H:%i:%s') < date_format('"
					+ DateUtils.getYmdHmis(c.getTime()) + "', '%Y-%m-%d %H:%i:%s')";
		}

		return result;
	}

	/** 字符串日期类型对比 */
	protected String getParaStringDateColumnCompare(List<Object> args, String beginDateKey, String endDateKey,
			String stringDateColumn) {
		String _beginDateKey = getPara(beginDateKey);
		String _endDateKey = getPara(endDateKey);

		String result = "";
		if (StrKit.notBlank(_beginDateKey)) {
			result += " and date_format(" + stringDateColumn + ", '%Y-%m-%d %H:%i:%s') >= ?";
			args.add(DateUtils.parseYmd(_beginDateKey.trim()));
		}

		if (StrKit.notBlank(_endDateKey)) {
			result += " and date_format(" + stringDateColumn + ", '%Y-%m-%d %H:%i:%s') < ?";

			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_endDateKey.trim()));
			c.add(Calendar.DATE, 1);
			args.add(c.getTime());
		}

		return result;
	}

	protected String getParaDateColumnCompare(List<Object> args, String beginDateKey, String endDateKey, String column) {
		String _beginDateKey = getPara(beginDateKey);
		String _endDateKey = getPara(endDateKey);

		String result = "";
		if (StrKit.notBlank(_beginDateKey)) {
			result += " and " + column + " >= ?";
			args.add(DateUtils.parseYmd(_beginDateKey.trim()));
		}

		if (StrKit.notBlank(_endDateKey)) {
			result += " and " + column + " < ?";

			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_endDateKey.trim()));
			c.add(Calendar.DATE, 1);
			args.add(c.getTime());
		}

		return result;
	}

	protected String getParaDateColumnCompare(String beginDateKey, String endDateKey, String column) {
		String _beginDateKey = getPara(beginDateKey);
		String _endDateKey = getPara(endDateKey);

		String result = "";
		if (StrKit.notBlank(_beginDateKey)) {
			result += " and " + column + " >= date_format('" + _beginDateKey.trim() + "', '%Y-%m-%d')";
		}

		if (StrKit.notBlank(_endDateKey)) {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseYmd(_endDateKey.trim()));
			c.add(Calendar.DATE, 1);

			result += " and " + column + " < date_format('" + DateUtils.getYmd(c.getTime()) + "', '%Y-%m-%d')";
		}

		return result;
	}

	protected String getParaSqlLikeWithOr(String key, String... columns) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and (";
			for (int i = 0; i < columns.length; i++) {
				result += (i == 0 ? "" : " or ") + columns[i] + " like '%" + _key + "%'";
			}
			result += ")";
		}

		return result;
	}

	protected String getParaSqlLikeWithOr(List<Object> args, String key, String... columns) {
		String _key = getPara(key);
		String result = "";
		if (StrKit.notBlank(_key)) {
			result += " and (";
			for (int i = 0; i < columns.length; i++) {
				result += (i == 0 ? "" : " or ") + columns[i].trim() + " like ?";
				args.add("%" + _key + "%");
			}
			result += ")";
		}

		return result;
	}

}
