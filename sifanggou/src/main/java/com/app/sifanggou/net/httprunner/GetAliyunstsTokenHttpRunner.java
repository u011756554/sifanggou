package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.bean.AliyunTokenBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.CityMarketResponseBean;
import com.app.sifanggou.net.bean.GetAliyunstsTokenResponseBean;
import com.app.sifanggou.net.bean.GetAliyunstsTokenStringResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class GetAliyunstsTokenHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        int random = (int) (Math.random() * 1000000);
        String result = HttpUtils.doGetString(URLUtils.GETALIYUNSTSTOKEN+ File.separator+random);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        GetAliyunstsTokenStringResponseBean param = gson.fromJson(result, GetAliyunstsTokenStringResponseBean.class);
        AliyunTokenBean tokenBean = gson.fromJson(param.getData().getAliyun_sts_token(),AliyunTokenBean.class);
        if (param == null) {
            event.setSuccess(false);
            return;
        }
        if (checkParams(event, param)) {

            event.setSuccess(true);
            event.addReturnParam(tokenBean);
        } else {
            event.setSuccess(false);
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
        }
    }
}
