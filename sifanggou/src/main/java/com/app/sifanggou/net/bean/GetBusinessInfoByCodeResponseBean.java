package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessInfoBean;

/**
 * Created by Administrator on 2018/1/18.
 */

public class GetBusinessInfoByCodeResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private BusinessInfoBean business_info;

        public BusinessInfoBean getBusiness_info() {
            return business_info;
        }

        public void setBusiness_info(BusinessInfoBean business_info) {
            this.business_info = business_info;
        }
    }
}
