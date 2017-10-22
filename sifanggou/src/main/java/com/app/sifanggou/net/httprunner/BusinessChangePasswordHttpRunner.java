package com.app.sifanggou.net.httprunner;

import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class BusinessChangePasswordHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String mobile = (String) event.getParamAtIndex(0);
        String verify_code = (String) event.getParamAtIndex(1);
        String password = (String) event.getParamAtIndex(2);

        HashMap<String, String> map = new HashMap<String, String>();
            map.put("password", CommonUtils.EncoderByMd5(password));
            map.put("verify_code", verify_code);
            map.put("mobile", mobile);

        String result = HttpUtils.doPost(URLUtils.BUSINESSCHANGEPASSWORD, map);

            Log.i(AppContext.LOG_NET, result);
            if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        BaseResponseBean param = gson.fromJson(result, BaseResponseBean.class);
            if (param == null) {
            event.setSuccess(false);
            return;
        }
            if (checkParams(event, param)) {
            event.setSuccess(true);
            event.addReturnParam(param);
        } else {
            event.setSuccess(false);
        }
    }
}
