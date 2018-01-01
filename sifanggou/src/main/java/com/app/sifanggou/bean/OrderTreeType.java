package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/1.
 */

public enum OrderTreeType {
    PRICE ("0"),AMOUNT("1");

    private String type;
    OrderTreeType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
