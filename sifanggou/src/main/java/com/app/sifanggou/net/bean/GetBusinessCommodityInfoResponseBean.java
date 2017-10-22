package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CommodityInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class GetBusinessCommodityInfoResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<CommodityInfoBean> business_commodity_info;

        public List<CommodityInfoBean> getBusiness_commodity_info() {
            return business_commodity_info;
        }

        public void setBusiness_commodity_info(List<CommodityInfoBean> business_commodity_info) {
            this.business_commodity_info = business_commodity_info;
        }
    }
}
