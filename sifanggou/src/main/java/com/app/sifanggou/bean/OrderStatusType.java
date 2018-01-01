package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/24.
 */

public enum OrderStatusType {
    PAID ("paid"),RECEIPTED("receipted");

    private String type;
    OrderStatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
