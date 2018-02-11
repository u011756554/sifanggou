package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/17.
 */

public class DeliverAddressBean extends BaseBean {
    private AdressBean deliver_address;
    private String delivery_id;
    private String business_code;

    public AdressBean getDeliver_address() {
        return deliver_address;
    }

    public void setDeliver_address(AdressBean deliver_address) {
        this.deliver_address = deliver_address;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }
}
