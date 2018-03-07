package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.VerInfoBean;

/**
 * Created by Administrator on 2018/2/27.
 */

public class GetVerInfoResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private VerInfoBean ver_info;

        public VerInfoBean getVer_info() {
            return ver_info;
        }

        public void setVer_info(VerInfoBean ver_info) {
            this.ver_info = ver_info;
        }
    }
}
