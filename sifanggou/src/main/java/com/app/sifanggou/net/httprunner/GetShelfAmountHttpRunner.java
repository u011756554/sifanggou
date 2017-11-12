package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetShelfAmountResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class GetShelfAmountHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String num = (String) event.getParamAtIndex(0);
        String year_num = (String) event.getParamAtIndex(1);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("num", num);
        map.put("year_num", year_num);

        String result = HttpUtils.doPost(URLUtils.GETSHELFAMOUNT, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        GetShelfAmountResponseBean param = gson.fromJson(result, GetShelfAmountResponseBean.class);
        if (param == null) {
            event.setSuccess(false);
            return;
        }
        if (checkParams(event, param)) {
            event.setSuccess(true);
            event.addReturnParam(param);
        } else {
            event.setSuccess(false);
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
        }
    }
}
