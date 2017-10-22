package com.app.sifanggou.net.httprunner;

import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.CityMarketResponseBean;
import com.app.sifanggou.net.bean.GetCommodityTypeListResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/6.
 */

public class GetCommodityTypeListHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        {
            // TODO Auto-generated method stub
            HashMap<String, String> map = new HashMap<String, String>();

            String result = HttpUtils.doGetString(URLUtils.GETCOMMODITYTYPELIST);

            Log.i(AppContext.LOG_NET, result);
            if (CommonUtils.isEmpty(result)) {
                event.setSuccess(false);
                event.setFailException(new Exception("网络错误"));
                return;
            }

            GetCommodityTypeListResponseBean param = gson.fromJson(result, GetCommodityTypeListResponseBean.class);
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
}
