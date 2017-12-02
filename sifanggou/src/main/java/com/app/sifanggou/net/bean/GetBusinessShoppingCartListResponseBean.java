package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.CarBean;
import com.app.sifanggou.bean.ShopingCartListBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/24.
 */

public class GetBusinessShoppingCartListResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private ShopingCartListBean business_shoppingcart_list;

        public ShopingCartListBean getBusiness_shoppingcart_list() {
            return business_shoppingcart_list;
        }

        public void setBusiness_shoppingcart_list(ShopingCartListBean business_shoppingcart_list) {
            this.business_shoppingcart_list = business_shoppingcart_list;
        }
    }
}
