package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/2.
 */

public enum OrderType {
    WAITING_PAY ("waiting_pay"),PAID("paid"),WAITING_PAYEE ("waiting_payee"),RECEIPTED("receipted");

    private String type;
    OrderType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
