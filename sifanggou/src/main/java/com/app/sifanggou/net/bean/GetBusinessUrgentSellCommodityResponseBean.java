package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.UrgentSellCommodityBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class GetBusinessUrgentSellCommodityResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<UrgentSellCommodityBean> urgent_sell_commodity;

        public List<UrgentSellCommodityBean> getUrgent_sell_commodity() {
            return urgent_sell_commodity;
        }

        public void setUrgent_sell_commodity(List<UrgentSellCommodityBean> urgent_sell_commodity) {
            this.urgent_sell_commodity = urgent_sell_commodity;
        }
    }
}
