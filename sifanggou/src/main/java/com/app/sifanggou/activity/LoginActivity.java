package com.app.sifanggou.activity;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.MyApplication;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AliyunToken;
import com.app.sifanggou.bean.AliyunTokenBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessInfoByCodeResponseBean;
import com.app.sifanggou.net.bean.GetBusinessRongYunTokenResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.btn_login)
	private Button btnLogin;
	@ViewInject(R.id.rl_register)
	private RelativeLayout rlRegister;
	@ViewInject(R.id.tv_forget)
	private TextView tvForget;
	@ViewInject(R.id.edt_phone)
	private EditText edtPhone;
	@ViewInject(R.id.edt_pwd)
	private EditText edtPwd;
	@ViewInject(R.id.tv_register)
	private TextView tvRegiser;
	@ViewInject(R.id.rl_delete)
	private RelativeLayout rlDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initListener();
	}

	private void initView() {
		String phone = PreManager.getString(getApplicationContext(),AppContext.USER_ACCOUNT);
		if(!TextUtils.isEmpty(phone)) {
			edtPhone.setText(phone);
		}
	}
	
	private void initListener() {
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(edtPhone.getText().toString())) {
					CommonUtils.showToast("输入手机号");
					return;
				}
				if(TextUtils.isEmpty(edtPwd.getText().toString())) {
					CommonUtils.showToast("输入密码");
					return;
				}
				try {
					pushEventBlock(EventCode.HTTP_BUSINESSLOGIN,edtPhone.getText().toString(),CommonUtils.EncoderByMd5(edtPwd.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		rlRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});

		tvRegiser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		tvForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ForGetPwdActivity.class);
				startActivity(intent);
			}
		});

		rlDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApplication.instance.exit();
				finish();
			}
		});
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSINFOBYCODE) {
            if (event.isSuccess()) {
				GetBusinessInfoByCodeResponseBean bean = (GetBusinessInfoByCodeResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null && bean.getData() != null && bean.getData().getBusiness_info() != null) {
					Uri headUri = Uri.parse(bean.getData().getBusiness_info().getHead_pic_url());
					UserInfo userInfo = new UserInfo(bean.getData().getBusiness_info().getBusiness_code(),
							bean.getData().getBusiness_info().getName(),headUri);
					RongIM.getInstance().refreshUserInfoCache(userInfo);
				}
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
		if (event.getEventCode() == EventCode.HTTP_BUSINESSLOGIN) {
			if (event.isSuccess()) {
				PreManager.putString(getApplicationContext(),AppContext.USER_ACCOUNT,edtPhone.getText().toString());
				try {
					PreManager.putString(getApplicationContext(),AppContext.USER_PWD,CommonUtils.EncoderByMd5(edtPwd.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				LoginResponseBean loginBean = (LoginResponseBean) event.getReturnParamAtIndex(0);
				if (loginBean != null
						&& loginBean.getData() != null
						&& loginBean.getData().getLogin_info() != null
						&& loginBean.getData().getLogin_info().getBusiness_info() != null) {
					PreManager.put(getApplicationContext(), AppContext.USER_LOGIN,loginBean);

					Uri headUri = Uri.parse(loginBean.getData().getLogin_info().getBusiness_info().getHead_pic_url());

					UserInfo userInfo = new UserInfo(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
							loginBean.getData().getLogin_info().getName(),headUri);
					RongIM.getInstance().setCurrentUserInfo(userInfo);
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                        @Override
                        public UserInfo getUserInfo(String userId) {
                            return findUserById(userId);
                        }
                    },true);

					pushEventBlock(EventCode.HTTP_GETBUSINESSRONGYUNTOKEN,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code());
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}

		if (event.getEventCode() == EventCode.HTTP_GETBUSINESSRONGYUNTOKEN) {
			if (event.isSuccess()) {
				GetBusinessRongYunTokenResponseBean bean = (GetBusinessRongYunTokenResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null
						&& bean.getData() != null
						&& !TextUtils.isEmpty(bean.getData().getToken())) {
					connect(bean.getData().getToken());
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
	}

	/**
	 * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
	 * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
	 * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
	 *
	 * @param token    从服务端获取的用户身份令牌（Token）。
	 * @param callback 连接回调。
	 * @return RongIM  客户端核心类的实例。
	 */
	private void connect(String token) {

		if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext()))) {

			RongIM.connect(token, new RongIMClient.ConnectCallback() {

				/**
				 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
				 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
				 */
				@Override
				public void onTokenIncorrect() {

				}

				/**
				 * 连接融云成功
				 * @param userid 当前 token 对应的用户 id
				 */
				@Override
				public void onSuccess(String userid) {
					Log.d("LoginActivity", "--onSuccess" + userid);
					CommonUtils.showToast("登录成功");
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					finish();
				}

				/**
				 * 连接融云失败
				 * @param errorCode 错误码，可到官网 查看错误码对应的注释
				 */
				@Override
				public void onError(RongIMClient.ErrorCode errorCode) {

				}
			});
		}
	}

	private UserInfo findUserById(String userId) {
        Uri headUri = Uri.parse(AppContext.URL_HEAD);
        UserInfo userInfo = new UserInfo(userId,userId,headUri);
        pushEventNoProgress(EventCode.HTTP_GETBUSINESSINFOBYCODE,userId);
        return userInfo;
    }
}
