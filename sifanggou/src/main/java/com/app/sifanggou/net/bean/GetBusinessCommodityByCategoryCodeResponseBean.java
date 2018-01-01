package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CommodityInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class GetBusinessCommodityByCategoryCodeResponseBean extends BaseResponseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<CommodityInfoBean> business_commodity_info_list;

        public List<CommodityInfoBean> getBusiness_commodity_info_list() {
            return business_commodity_info_list;
        }

        public void setBusiness_commodity_info_list(List<CommodityInfoBean> business_commodity_info_list) {
            this.business_commodity_info_list = business_commodity_info_list;
        }
    }
}
