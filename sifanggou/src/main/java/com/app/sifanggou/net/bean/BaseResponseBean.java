package com.app.sifanggou.net.bean;

import java.io.Serializable;
import java.util.Map;

public class BaseResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statusCode;
	private String message;

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
