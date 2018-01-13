package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CommodityCollectBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/6.
 */

public class GetBusinessCommodityCollectResponseBean extends BaseResponseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<CommodityCollectBean> business_commodity_collect_list;

        public List<CommodityCollectBean> getBusiness_commodity_collect_list() {
            return business_commodity_collect_list;
        }

        public void setBusiness_commodity_collect_list(List<CommodityCollectBean> business_commodity_collect_list) {
            this.business_commodity_collect_list = business_commodity_collect_list;
        }
    }
}
