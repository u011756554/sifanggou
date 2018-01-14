package com.app.sifanggou.net.bean;

/**
 * Created by Administrator on 2018/1/14.
 */

public class IsBusinessPartnerResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String is_partner;

        public String getIs_partner() {
            return is_partner;
        }

        public void setIs_partner(String is_partner) {
            this.is_partner = is_partner;
        }
    }
}
