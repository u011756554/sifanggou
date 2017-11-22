package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CommodityInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class SearchBusinessCommodityOnNameResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<CommodityInfoBean> commodity_info_list;

        public List<CommodityInfoBean> getCommodity_info_list() {
            return commodity_info_list;
        }

        public void setCommodity_info_list(List<CommodityInfoBean> commodity_info_list) {
            this.commodity_info_list = commodity_info_list;
        }
    }
}
