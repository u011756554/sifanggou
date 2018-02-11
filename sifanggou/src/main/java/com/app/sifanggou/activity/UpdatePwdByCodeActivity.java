package com.app.sifanggou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/17.
 */

public class UpdatePwdByCodeActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rlBack;
    @ViewInject(R.id.edt_phone)
    private EditText edtPhone;
    @ViewInject(R.id.edt_code)
    private EditText edtCode;
    @ViewInject(R.id.edt_pwd)
    private EditText edtPwd;
    @ViewInject(R.id.tv_getcode)
    private TextView tvGetCode;
    @ViewInject(R.id.btn_set)
    private Button btnSet;

    private int time = 60;
    private boolean isCoding = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {

    }

    private void initListener() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtPhone.getText().toString())) {
                    CommonUtils.showToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(edtCode.getText().toString())) {
                    CommonUtils.showToast("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(edtPwd.getText().toString())) {
                    CommonUtils.showToast("请输入密码");
                    return;
                }
                pushEventBlock(EventCode.HTTP_BUSINESSCHANGEPASSWORD,edtPhone.getText().toString(),edtCode.getText().toString(),edtPwd.getText().toString());
            }
        });

        tvGetCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    CommonUtils.showToast("请输入完整");
                    return;
                }
                time = 60;
                isCoding = true;
                tvGetCode.setEnabled(false);
                pushEventNoProgress(EventCode.HTTP_GETVERIFYCODE,edtPhone.getText().toString());
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        // TODO Auto-generated method stub
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETVERIFYCODE) {
            if (event.isSuccess()) {
                CommonUtils.showToast("验证码发送成功");
                refreshCode();
            } else {
                CommonUtils.showToast(event.getFailMessage());
                isCoding = false;
                tvGetCode.setEnabled(true);
                tvGetCode.setText("重新获取");
            }
        }

        if (event.getEventCode() == EventCode.HTTP_BUSINESSCHANGEPASSWORD) {
            if (event.isSuccess()) {
                CommonUtils.showToast("修改密码成功");
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());;
            }
        }
    }

    private void refreshCode() {
        if (isCoding) {
            tvGetCode.setEnabled(false);
            if (time <= 60 && time > 0) {
                time = time - 1;
                tvGetCode.setText(time+"");
                tvGetCode.setEnabled(false);
                isCoding = true;
                mHandler.postDelayed(timeRunnable, 1000);
            }
            if (time == 0) {
                tvGetCode.setText("重新获取");
                tvGetCode.setEnabled(true);
                isCoding = false;
            }
        }
    }

    private Handler mHandler = new Handler();

    private Runnable timeRunnable = new Runnable() {

        @Override
        public void run() {
            refreshCode();
        }

    };

    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null && timeRunnable != null) {
            mHandler.removeCallbacks(timeRunnable);
        }
    };

}
