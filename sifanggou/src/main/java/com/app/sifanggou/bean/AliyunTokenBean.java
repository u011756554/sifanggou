package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class AliyunTokenBean extends BaseBean {
    private String requestId;
    private CredentialsBean credentials;
    private AssumedRoleUserBean assumedRoleUser;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public CredentialsBean getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsBean credentials) {
        this.credentials = credentials;
    }

    public AssumedRoleUserBean getAssumedRoleUser() {
        return assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUserBean assumedRoleUser) {
        this.assumedRoleUser = assumedRoleUser;
    }
}
