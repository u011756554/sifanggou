package com.app.sifanggou.listener;

import android.view.View;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * Created by Administrator on 2017/10/19.
 */

public interface OSSCallBackListener {
    void onSuccess(PutObjectRequest request, PutObjectResult result,String tag);
    void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException);
}
