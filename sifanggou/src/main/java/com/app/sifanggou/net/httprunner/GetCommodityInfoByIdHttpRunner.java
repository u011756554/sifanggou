package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.GetCommodityInfoByIdResponseBean;
import com.app.sifanggou.net.bean.GetRecommendCommodityResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/18.
 */

public class GetCommodityInfoByIdHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String commodity_id = (String) event.getParamAtIndex(0);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("commodity_id", commodity_id);

        String result = HttpUtils.doPost(URLUtils.GETCOMMODITYINFOBYID, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        GetCommodityInfoByIdResponseBean param = gson.fromJson(result, GetCommodityInfoByIdResponseBean.class);
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
