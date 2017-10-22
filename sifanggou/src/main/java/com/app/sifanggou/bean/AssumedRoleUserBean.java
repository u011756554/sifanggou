package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class AssumedRoleUserBean extends BaseBean {
    private String arn;
    private String assumedRoleId;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getAssumedRoleId() {
        return assumedRoleId;
    }

    public void setAssumedRoleId(String assumedRoleId) {
        this.assumedRoleId = assumedRoleId;
    }
}
