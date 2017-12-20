package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/12/20.
 */

public class OrderNoBaseBean extends BaseBean {
    private String order_no;
    private String seller_payee_status;
    private String create_time;
    private String buyer_business_code;
    private String commodity_info_list;
    private String buyer_pay_status;
    private String buyer_receive_status;
    private SellerInfoBean seller_business_info;
    private String total_amount;
    private String sub_order_no;
    private String total_num;
    private String seller_delivery_status;
    private String buyer_receive_time;
    private String seller_business_code;
    private boolean isShow = true;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSeller_payee_status() {
        return seller_payee_status;
    }

    public void setSeller_payee_status(String seller_payee_status) {
        this.seller_payee_status = seller_payee_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getBuyer_business_code() {
        return buyer_business_code;
    }

    public void setBuyer_business_code(String buyer_business_code) {
        this.buyer_business_code = buyer_business_code;
    }

    public String getCommodity_info_list() {
        return commodity_info_list;
    }

    public void setCommodity_info_list(String commodity_info_list) {
        this.commodity_info_list = commodity_info_list;
    }

    public String getBuyer_pay_status() {
        return buyer_pay_status;
    }

    public void setBuyer_pay_status(String buyer_pay_status) {
        this.buyer_pay_status = buyer_pay_status;
    }

    public String getBuyer_receive_status() {
        return buyer_receive_status;
    }

    public void setBuyer_receive_status(String buyer_receive_status) {
        this.buyer_receive_status = buyer_receive_status;
    }

    public SellerInfoBean getSeller_business_info() {
        return seller_business_info;
    }

    public void setSeller_business_info(SellerInfoBean seller_business_info) {
        this.seller_business_info = seller_business_info;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSub_order_no() {
        return sub_order_no;
    }

    public void setSub_order_no(String sub_order_no) {
        this.sub_order_no = sub_order_no;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getSeller_delivery_status() {
        return seller_delivery_status;
    }

    public void setSeller_delivery_status(String seller_delivery_status) {
        this.seller_delivery_status = seller_delivery_status;
    }

    public String getBuyer_receive_time() {
        return buyer_receive_time;
    }

    public void setBuyer_receive_time(String buyer_receive_time) {
        this.buyer_receive_time = buyer_receive_time;
    }

    public String getSeller_business_code() {
        return seller_business_code;
    }

    public void setSeller_business_code(String seller_business_code) {
        this.seller_business_code = seller_business_code;
    }
}
