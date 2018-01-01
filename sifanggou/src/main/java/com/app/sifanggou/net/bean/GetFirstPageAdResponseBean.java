package com.app.sifanggou.net.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/30.
 */

public class GetFirstPageAdResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<GuangGaoBean> first_page_ad;

        public List<GuangGaoBean> getFirst_page_ad() {
            return first_page_ad;
        }

        public void setFirst_page_ad(List<GuangGaoBean> first_page_ad) {
            this.first_page_ad = first_page_ad;
        }
    }
}
