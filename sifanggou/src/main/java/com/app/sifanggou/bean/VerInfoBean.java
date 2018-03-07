package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/2/27.
 */

public class VerInfoBean extends BaseBean {
    private String download_url;
    private String version;

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
