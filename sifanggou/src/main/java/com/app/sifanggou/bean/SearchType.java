package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/11/22.
 */

public enum SearchType {
    PRODUCT("PRODUCT"),MARKET("MARKET");

    private String type;
    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
