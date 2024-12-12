package com.xunpay.money.utils;

import java.util.List;

public class Combo {

	private Integer id;
	private String name;
	private List<Combo> children;

	public Combo() {}
	
	public Combo(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Combo> getChildren() {
		return children;
	}

	public void setChildren(List<Combo> children) {
		this.children = children;
	}

}
