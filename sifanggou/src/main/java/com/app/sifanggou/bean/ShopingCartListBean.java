package com.app.sifanggou.bean;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ShopingCartListBean extends BaseBean {
    private String commodity_total_amount;
    private String commodity_total_num;
    private Map<String,CarBean> business_commodity_info;

    public String getCommodity_total_amount() {
        return commodity_total_amount;
    }

    public void setCommodity_total_amount(String commodity_total_amount) {
        this.commodity_total_amount = commodity_total_amount;
    }

    public String getCommodity_total_num() {
        return commodity_total_num;
    }

    public void setCommodity_total_num(String commodity_total_num) {
        this.commodity_total_num = commodity_total_num;
    }

    public Map<String, CarBean> getBusiness_commodity_info() {
        return business_commodity_info;
    }

    public void setBusiness_commodity_info(Map<String, CarBean> business_commodity_info) {
        this.business_commodity_info = business_commodity_info;
    }
}
