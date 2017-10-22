package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.AliyunTokenBean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class GetAliyunstsTokenResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private AliyunTokenBean aliyun_sts_token;

        public AliyunTokenBean getAliyun_sts_token() {
            return aliyun_sts_token;
        }

        public void setAliyun_sts_token(AliyunTokenBean aliyun_sts_token) {
            this.aliyun_sts_token = aliyun_sts_token;
        }
    }
}
