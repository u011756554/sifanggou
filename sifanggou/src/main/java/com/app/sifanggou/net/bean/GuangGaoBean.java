package com.app.sifanggou.net.bean;

public class GuangGaoBean extends BaseResponseBean {

	private String id;
	private String createDate;
	private String lastUpdateDate;
	private String outUrl;
	private String picUrl;
	private String publish;
	private String title;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
