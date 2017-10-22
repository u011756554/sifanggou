package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/7.
 */

public enum ChuShouType {
    ONSALE("onsale"),OFFSALE("offsale");

    private String type;
    ChuShouType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
