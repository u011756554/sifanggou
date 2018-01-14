package com.app.sifanggou.net.bean;

/**
 * Created by Administrator on 2018/1/14.
 */

public class IsBusinessCollectCommodityResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String is_collect;

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }
    }
}
