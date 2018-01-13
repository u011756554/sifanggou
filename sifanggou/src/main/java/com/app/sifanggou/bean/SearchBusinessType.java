package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/6.
 */

public enum SearchBusinessType {
    NAME("0"),SCAN("1");

    private String type;

    SearchBusinessType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
