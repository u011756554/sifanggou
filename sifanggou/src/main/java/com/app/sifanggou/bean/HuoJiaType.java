package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public enum HuoJiaType {
    DAILI("0"),PUTONG("1");

    private String type;
    HuoJiaType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
