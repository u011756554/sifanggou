package com.app.sifanggou.net.bean;

import java.util.List;

import com.app.sifanggou.bean.ProvinceDataBean;

public class ProvinceResponseBean extends BaseResponseBean {

	private DataBean data;
	
	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public class DataBean {
		private List<ProvinceDataBean> province_info;

		public List<ProvinceDataBean> getProvince_info() {
			return province_info;
		}

		public void setProvince_info(List<ProvinceDataBean> province_info) {
			this.province_info = province_info;
		}
	}
}
