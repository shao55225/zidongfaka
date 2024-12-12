package com.xunpay.money.utils;

import java.io.Serializable;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @ClassName: DtViewPage 
 * @Description: 分页模型
 * @author Gray 
 * @date 2016年4月24日 下午1:36:50 
 * 
 * @param <T>
 */
public class DtViewPage<T> implements Serializable {
	private static final long serialVersionUID = -7326751222887130277L;

	private int iTotalRecords;
	private String sEcho;
	private int iTotalDisplayRecords;
	private List<T> aaData; // rows

	public DtViewPage(Page<T> page, String sEcho) {
		super();
		this.iTotalRecords = page.getTotalRow();
		this.iTotalDisplayRecords = page.getTotalRow();
		this.sEcho = sEcho;
		this.aaData = page.getList();
	}

	public DtViewPage(int iTotalRecords, int iTotalDisplayRecords, String sEcho, List<T> aaData) {
		super();
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.sEcho = sEcho;
		this.aaData = aaData;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

}
