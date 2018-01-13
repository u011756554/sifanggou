package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class PrinterActivity extends BaseActivity {

    @ViewInject(R.id.btn_confirm)
    private Button btnConfirm;
    @ViewInject(R.id.edt_zdcode)
    private EditText edtZDCODE;
    @ViewInject(R.id.edt_zdmy)
    private EditText edtZDMY;

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
    }

    private void initView() {
        setTitle("打印机");
        addBack(R.id.rl_back);

    }

    private void initListener() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null) {
                    return;
                }

                String machine_code = edtZDCODE.getText().toString();
                String qr_key = edtZDMY.getText().toString();

                if (TextUtils.isEmpty(machine_code)) {
                    CommonUtils.showToast("请输入机器码");
                    return;
                }

                if (TextUtils.isEmpty(qr_key)) {
                    CommonUtils.showToast("请输入密钥");
                    return;
                }

                String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                String trans_no = System.currentTimeMillis()+"";
                String sign = CommonUtils.getSign(business_code,user_name,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));

                pushEventBlock(EventCode.HTTP_ADDBUSINESSPRINTCONFIG,business_code,user_name,trans_no,sign,machine_code,qr_key);
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSPRINTCONFIG) {
            if (event.isSuccess()) {
                CommonUtils.showToast("设置成功");
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
