package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/2.
 */

public enum OrderType {
    WAITING_PAY ("waiting_pay"),WAITING_PAYEE("waiting_payee"),WAITING_RECEIPT ("waiting_receipt"),WAITING_DELIVER("waiting_deliver");

    private String type;
    OrderType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
