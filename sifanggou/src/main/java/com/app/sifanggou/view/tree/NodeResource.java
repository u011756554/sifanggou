package com.app.sifanggou.view.tree;

import java.io.Serializable;

import android.text.TextUtils;

public class NodeResource implements Comparable<NodeResource> , Serializable{
	protected String parentId;
	protected String title;
	protected String value;
	protected int iconId;
	protected String curId;
	private String companyId;
	
	public NodeResource() {
		
	}

	public NodeResource(String parentId, String curId, String title,
			String value, int iconId) {
		super();
		this.parentId = parentId;
		this.title = title;
		this.value = value;
		this.iconId = iconId;
		this.curId = curId;
	}

	public String getParentId() {
		return parentId;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public int getIconId() {
		return iconId;
	}

	public String getCurId() {
		return curId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public void setCurId(String curId) {
		this.curId = curId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public int compareTo(NodeResource another) {
		// TODO Auto-generated method stub
		String otherValue = another.value;
		if (TextUtils.isEmpty(value) || TextUtils.isEmpty(otherValue)) {
			return 0;
		} 
		if ("总经办".equals(value)) {
			return -1;
		}
		if ("总经办".equals(otherValue)) {
			return 1;
		}
		return 0;
	}

}
