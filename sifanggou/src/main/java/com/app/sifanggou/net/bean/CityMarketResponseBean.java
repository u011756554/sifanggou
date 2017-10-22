package com.app.sifanggou.net.bean;

import java.util.List;

import com.app.sifanggou.bean.CityMarketBean;

public class CityMarketResponseBean extends BaseResponseBean{

	private DataBean data;
	
	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public class DataBean {
		private List<CityMarketBean> city_market;

		public List<CityMarketBean> getCity_market() {
			return city_market;
		}

		public void setCity_market(List<CityMarketBean> city_market) {
			this.city_market = city_market;
		}
	}
}
