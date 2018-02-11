package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.CommodityInfoBean;

/**
 * Created by Administrator on 2018/1/18.
 */

public class GetCommodityInfoByIdResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private CommodityInfoBean commodity_info;
        private BusinessInfoBean business_info;

        public CommodityInfoBean getCommodity_info() {
            return commodity_info;
        }

        public void setCommodity_info(CommodityInfoBean commodity_info) {
            this.commodity_info = commodity_info;
        }

        public BusinessInfoBean getBusiness_info() {
            return business_info;
        }

        public void setBusiness_info(BusinessInfoBean business_info) {
            this.business_info = business_info;
        }
    }
}
