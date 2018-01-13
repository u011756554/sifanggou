package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/13.
 */

public class AliyunToken extends BaseBean {
    private String requestId;
    private CredentialsTokenBean credentials;
    private AssumedRoleUserTokenBean assumedRoleUser;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public CredentialsTokenBean getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsTokenBean credentials) {
        this.credentials = credentials;
    }

    public AssumedRoleUserTokenBean getAssumedRoleUser() {
        return assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUserTokenBean assumedRoleUser) {
        this.assumedRoleUser = assumedRoleUser;
    }
}
