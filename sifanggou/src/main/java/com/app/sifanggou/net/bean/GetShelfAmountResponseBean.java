package com.app.sifanggou.net.bean;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class GetShelfAmountResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String shelf_amount;

        public String getShelf_amount() {
            return shelf_amount;
        }

        public void setShelf_amount(String shelf_amount) {
            this.shelf_amount = shelf_amount;
        }
    }
}
