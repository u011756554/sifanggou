package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/14.
 */

public enum ShouCangType {
    SAVED("1"),NOSAVE("0");

    private String type;

    ShouCangType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
