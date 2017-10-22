package com.app.sifanggou.net.httprunner;

import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.AgentLevelResponseBean;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class GetAgentLevelInfoHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String result = HttpUtils.doGetString(URLUtils.GETAGENTLEVELINFO);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        AgentLevelResponseBean param = gson.fromJson(result, AgentLevelResponseBean.class);
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
