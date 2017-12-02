package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.AdressBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GetBusinessDeliverAddressResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<AdressBean> deliver_address_list;

        public List<AdressBean> getDeliver_address_list() {
            return deliver_address_list;
        }

        public void setDeliver_address_list(List<AdressBean> deliver_address_list) {
            this.deliver_address_list = deliver_address_list;
        }
    }
}
