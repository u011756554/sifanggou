package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/21.
 */

public enum CommityInfoType {

    ALL("all"),COMMON("common"),AGENCY("agency"),NEW_PRODUCT("new_product");

    private String type;
    CommityInfoType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
