package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/1.
 */

public enum AddressTimeType {
    WORKDAY("周一至周五收货"),WEEKEND("周六周日节假日收货"),NOLIMIT("收货时间不限");

    private String type;
    AddressTimeType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
