package com.app.sifanggou.net.httprunner;

import java.util.HashMap;

import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;

public class GetVerifyCodeHttpRunner extends HttpRunner {

	@Override
	public void onEventRun(Event event) throws Exception {
		// TODO Auto-generated method stub
		String mobile = (String) event.getParamAtIndex(0);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		
		String result = HttpUtils.doPost(URLUtils.GETVERIFYCODE, map);
		
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
