package com.app.sifanggou.bean;

public class DiPaiBean extends BaseBean {
	
	private String url;
	private String name;
	private String price;
	private boolean isStar;
	public boolean isStar() {
		return isStar;
	}
	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
