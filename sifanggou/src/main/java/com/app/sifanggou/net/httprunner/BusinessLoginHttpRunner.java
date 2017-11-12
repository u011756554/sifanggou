package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class BusinessLoginHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String mobile = (String) event.getParamAtIndex(0);
        String password = (String) event.getParamAtIndex(1);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("password", password);
        map.put("mobile", mobile);

        String result = HttpUtils.doPost(URLUtils.BUSINESSLOGIN, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        LoginResponseBean param = gson.fromJson(result, LoginResponseBean.class);
        if (param == null) {
            event.setSuccess(false);
            return;
        }
        if (checkParams(event, param)) {
            event.setSuccess(true);
            event.addReturnParam(param);
        } else {
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
            event.setSuccess(false);
        }
    }
}
