package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.AllBusinessUrgentSellCommodityBean;
import com.app.sifanggou.bean.CommodityInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class GetAllBusinessUrgentSellCommodityResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<AllBusinessUrgentSellCommodityBean> all_business_urgent_sell_commodity;

        public List<AllBusinessUrgentSellCommodityBean> getAll_business_urgent_sell_commodity() {
            return all_business_urgent_sell_commodity;
        }

        public void setAll_business_urgent_sell_commodity(List<AllBusinessUrgentSellCommodityBean> all_business_urgent_sell_commodity) {
            this.all_business_urgent_sell_commodity = all_business_urgent_sell_commodity;
        }
    }
}
