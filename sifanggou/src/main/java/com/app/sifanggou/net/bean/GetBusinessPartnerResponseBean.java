package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessPartnerBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/6.
 */

public class GetBusinessPartnerResponseBean extends BaseResponseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<BusinessPartnerBean> business_partner;

        public List<BusinessPartnerBean> getBusiness_partner() {
            return business_partner;
        }

        public void setBusiness_partner(List<BusinessPartnerBean> business_partner) {
            this.business_partner = business_partner;
        }
    }


}
