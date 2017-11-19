package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SerachBusinessOnNameResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<BusinessInfoBean> business_info_list;

        public List<BusinessInfoBean> getBusiness_info_list() {
            return business_info_list;
        }

        public void setBusiness_info_list(List<BusinessInfoBean> business_info_list) {
            this.business_info_list = business_info_list;
        }
    }
}
