package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/7.
 */

public enum ShangJiaXiaJiaType {
    SHANGJIA("1"),XIAJIA("0");

    private String type;
    ShangJiaXiaJiaType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
