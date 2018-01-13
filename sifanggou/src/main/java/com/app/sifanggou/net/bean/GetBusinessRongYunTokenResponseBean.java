package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.AliyunToken;

/**
 * Created by Administrator on 2018/1/13.
 */

public class GetBusinessRongYunTokenResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
