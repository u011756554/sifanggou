package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/8.
 */

public enum ZhiWeiType {
    OPERATOR("operator","业务员"),FINANCE("finance","财务人员"),BOSS("boss","老板");

    private String type;
    private String value;
    ZhiWeiType(String type,String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }
}
