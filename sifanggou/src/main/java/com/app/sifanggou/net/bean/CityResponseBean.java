package com.app.sifanggou.net.bean;

import java.util.List;

import com.app.sifanggou.bean.CityDataBean;

public class CityResponseBean extends BaseResponseBean {

	private DataBean data;
	
	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public class DataBean {
		private List<CityDataBean> city_info;

		public List<CityDataBean> getCity_info() {
			return city_info;
		}

		public void setCity_info(List<CityDataBean> city_info) {
			this.city_info = city_info;
		}
		
	}
}
