package com.app.sifanggou.bean;

import com.app.sifanggou.net.bean.BaseResponseBean;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class LoginInfoBean extends BaseBean {
    private LoginUserDataBean login_info;

    public LoginUserDataBean getLogin_info() {
        return login_info;
    }

    public void setLogin_info(LoginUserDataBean login_info) {
        this.login_info = login_info;
    }
}
