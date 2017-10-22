package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventManager.OnEventRunner;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.google.gson.Gson;

public abstract class HttpRunner  implements OnEventRunner {

	protected Gson gson = new Gson();
	
	public static final String ERROR_10000 = "10000";  //参数xx未填写  
	public static final String ERROR_10001 = "10001";  //签名错误
	public static final String ERROR_10002 = "10002";  //时间戳过期 
	public static final String ERROR_20000 = "20000";  //数据库连接失败
	public static final String ERROR_20001 = "20001";  //数据库保存失败 
	public static final String ERROR_30000 = "30000";  //IM连接失败
	public static final String ERROR_40000 = "40000";  //mongoDB连接失败 
	public static final String ERROR_50000 = "50000";  //内部错误
	protected boolean checkParams(Event event,BaseResponseBean bean) {
		if(AppContext.URL_SUCCESSCODE.equals(bean.getStatusCode())) {
			return true;
		}
		return false;
	}
	
	protected void saveToken(BaseResponseBean bean) {

	}
}
