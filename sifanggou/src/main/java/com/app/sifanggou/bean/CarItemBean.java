package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/11/24.
 */

public class CarItemBean extends BaseBean {
    private String commodity_id;
    private String price;
    private String business_code;
    private String commodity_num;
    private String commodity_name;

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getCommodity_num() {
        return commodity_num;
    }

    public void setCommodity_num(String commodity_num) {
        this.commodity_num = commodity_num;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }
}
