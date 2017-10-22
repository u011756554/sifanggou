package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.BusinessCanAllocateShelfNumBean;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class GetBusinessCanAllocateShelfNumResponseBean extends BaseResponseBean {
    private BusinessCanAllocateShelfNumBean data;

    public BusinessCanAllocateShelfNumBean getData() {
        return data;
    }

    public void setData(BusinessCanAllocateShelfNumBean data) {
        this.data = data;
    }
}
