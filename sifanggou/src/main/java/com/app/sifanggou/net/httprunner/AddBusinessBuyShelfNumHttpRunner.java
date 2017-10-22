package com.app.sifanggou.net.httprunner;

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

public class AddBusinessBuyShelfNumHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String num = (String) event.getParamAtIndex(1);
        String year_num = (String) event.getParamAtIndex(2);
        String shelf_type = (String) event.getParamAtIndex(3);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("num", num);
        map.put("year_num", year_num);
        map.put("business_code", business_code);
        map.put("shelf_type", shelf_type);

        String result = HttpUtils.doPost(URLUtils.ADDBUSINESSBUYSHELFNUM, map);

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
