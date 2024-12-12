package com.xunpay.money.utils;

public class Result {

	private boolean success;
	private String msg;
	private Object content;

	public Result() {
	}

	public Result(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	
	public Object getContent() {
		return content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
