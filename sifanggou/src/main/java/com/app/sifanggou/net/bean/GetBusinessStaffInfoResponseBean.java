package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessStaffBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class GetBusinessStaffInfoResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<BusinessStaffBean> business_staff_list;

        public List<BusinessStaffBean> getBusiness_staff_list() {
            return business_staff_list;
        }

        public void setBusiness_staff_list(List<BusinessStaffBean> business_staff_list) {
            this.business_staff_list = business_staff_list;
        }
    }
}
