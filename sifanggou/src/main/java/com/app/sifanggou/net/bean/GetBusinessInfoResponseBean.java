package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.httprunner.HttpRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class GetBusinessInfoResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<BusinessInfoBean> business_commodity_info;

        public List<BusinessInfoBean> getBusiness_commodity_info() {
            return business_commodity_info;
        }

        public void setBusiness_commodity_info(List<BusinessInfoBean> business_commodity_info) {
            this.business_commodity_info = business_commodity_info;
        }
    }
}
