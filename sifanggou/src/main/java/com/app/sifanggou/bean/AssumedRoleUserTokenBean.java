package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/13.
 */

public class AssumedRoleUserTokenBean extends BaseBean {
    private String arn;
    private String assumedRoleId;

    public String getAssumedRoleId() {
        return assumedRoleId;
    }

    public void setAssumedRoleId(String assumedRoleId) {
        this.assumedRoleId = assumedRoleId;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }
}
