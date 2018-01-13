package com.app.sifanggou.net.bean;

/**
 * Created by Administrator on 2018/1/6.
 */

public class GetBusinessInviteCodeResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String business_invite_code;

        public String getBusiness_invite_code() {
            return business_invite_code;
        }

        public void setBusiness_invite_code(String business_invite_code) {
            this.business_invite_code = business_invite_code;
        }
    }
}
