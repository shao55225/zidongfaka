package com.xunpay.money.utils;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @ClassName: PageUtil
 * @Description: 分页操作类
 * @author Gray
 * @date 2016年4月24日 下午1:38:18
 * 
 */
public class PageUtil {
	private Map<String, String[]> reqParamsMap;
	private int iDisplayStart;
	private int iDisplayLength;
	private int iSortingCols;
	private String sEcho;
	private static final Object[] NULL_PARA_ARRAY = new Object[0];

	/**
	 * <p>Title: PageUtil</p>
	 * <p>Description: 分页操作工具类 </p>
	 * @param reqParamsMap 请求参数map
	 */
	public PageUtil(Map<String, String[]> reqParamsMap) {
		this.reqParamsMap = reqParamsMap;
		this.iDisplayStart = toInt(reqParamsMap.get("iDisplayStart")[0], 0);
		this.iDisplayLength = toInt(reqParamsMap.get("iDisplayLength")[0], -1);
		this.iSortingCols = toInt(reqParamsMap.get("iSortingCols")[0], 0);
		this.sEcho = reqParamsMap.get("sEcho")[0];
	}

	/**
	 * @see #getDataTable(String, String, Object...)
	 */
	public DtViewPage<Record> getDataTable(String select, String sqlExceptSelect) {
		return getDataTable(select, sqlExceptSelect, NULL_PARA_ARRAY);
	}
	
	/**
	 * get object of page...
	 * @param select
	 * @param sqlExceptSelect
	 * @param paras
	 * @return
	 */
	public DtViewPage<Record> getDataTable(String select, String sqlExceptSelect, Object... paras) {
		Page<Record> records = null;
		String sortSql = "";//排序字段
		
		if (iSortingCols > 0) {
			if (sqlExceptSelect.toLowerCase().indexOf("order by") > -1) {
				throw new RuntimeException("cannot to set order by, need to remove...");
			}
			
			for (int i = 0; i < iSortingCols; i++) {
				String colOrderBy = getParameter("mDataProp_" + getParameterToInt("iSortCol_" + i));
				String colOrderType = getParameter("sSortDir_" + i);
				sortSql += colOrderBy + " " + colOrderType + ",";
			}
			
			if (sortSql.length() > 0) {
				sortSql = " order by " + sortSql.substring(0, sortSql.length() - 1);
			}
			
			sqlExceptSelect += sortSql;
		}
		
		if (iDisplayLength == -1) {// 查询所有的
			List<Record> list = Db.find(select + sqlExceptSelect, paras);
			records = new Page<Record>(list, 1, list.size(), list.size(), list.size());
		} else {
			records = Db.paginate((iDisplayStart + 1 + iDisplayLength)/iDisplayLength, iDisplayLength, select, sqlExceptSelect, paras);
		}
		
		return new DtViewPage<Record>(records,sEcho);
	}

	private Integer toInt(String value, Integer defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		
		value = value.trim();
		
		if (value.startsWith("N") || value.startsWith("n"))
			return -Integer.parseInt(value.substring(1));
		
		return Integer.parseInt(value);
	}

	public String getParameter(String name) {
		return reqParamsMap.get(name)[0];
	}

	public String getParameter(String name, String defaultValue) {
		String result = reqParamsMap.get(name)[0];
		return result != null && !"".equals(result) ? result : defaultValue;
	}

	public Integer getParameterToInt(String name) {
		return toInt(reqParamsMap.get(name)[0], null);
	}

	public Integer getParameterToInt(String name, Integer defaultValue) {
		return toInt(reqParamsMap.get(name)[0], defaultValue);
	}

	public Map<String, String[]> getReqParamsMap() {
		return reqParamsMap;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public String getsEcho() {
		return sEcho;
	}

}
