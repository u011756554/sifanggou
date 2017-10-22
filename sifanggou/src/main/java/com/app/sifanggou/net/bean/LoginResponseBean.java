package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.LoginInfoBean;
import com.app.sifanggou.bean.LoginUserDataBean;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class LoginResponseBean extends BaseResponseBean {
    private LoginInfoBean data;

    public LoginInfoBean getData() {
        return data;
    }

    public void setData(LoginInfoBean data) {
        this.data = data;
    }
}
