package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.OrderNoBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class GetBusinenessInOutOrderInfoResponseBean extends BaseResponseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<OrderNoBaseBean> business_order_info;

        public List<OrderNoBaseBean> getBusiness_order_info() {
            return business_order_info;
        }

        public void setBusiness_order_info(List<OrderNoBaseBean> business_order_info) {
            this.business_order_info = business_order_info;
        }
    }
}
