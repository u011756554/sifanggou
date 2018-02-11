package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.DeliverAddressBean;

/**
 * Created by Administrator on 2018/1/17.
 */

public class GetBusinessDefaultDeliverAddressResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private DeliverAddressBean default_deliver_address;

        public DeliverAddressBean getDefault_deliver_address() {
            return default_deliver_address;
        }

        public void setDefault_deliver_address(DeliverAddressBean default_deliver_address) {
            this.default_deliver_address = default_deliver_address;
        }
    }
}
