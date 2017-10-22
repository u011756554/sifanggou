package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CommodityTypeBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/6.
 */

public class GetCommodityTypeListResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<CommodityTypeBean> commodity_type_list;

        public List<CommodityTypeBean> getCommodity_type_list() {
            return commodity_type_list;
        }

        public void setCommodity_type_list(List<CommodityTypeBean> commodity_type_list) {
            this.commodity_type_list = commodity_type_list;
        }
    }

}
