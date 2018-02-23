package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/1.
 */

public enum AddressTimeType {
    WORKDAY("3小时内送货（周边调货"),WEEKEND("当天送货（同城调货）"),NOLIMIT("3天内送货（备货/压仓）");

    private String type;
    AddressTimeType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
