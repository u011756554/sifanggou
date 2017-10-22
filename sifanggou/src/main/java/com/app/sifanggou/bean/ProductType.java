package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/6.
 */

public enum ProductType {
    COMMON ("common"),AGENCY("agency");

    private String type;
    ProductType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
