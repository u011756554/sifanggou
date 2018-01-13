package com.app.sifanggou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.ZhiWeiType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class AddAccountActivity extends BaseActivity {

    @ViewInject(R.id.edt_name)
    private EditText edtName;
    @ViewInject(R.id.edt_phone)
    private  EditText edtPhone;
    @ViewInject(R.id.rl_zhiwei)
    private RelativeLayout rlZhiWei;
    @ViewInject(R.id.tv_zhiwei)
    private TextView tvZhiWei;
    @ViewInject(R.id.btn_pay)
    private Button btnPay;

    private ZhiWeiType zhiWeiType = ZhiWeiType.OPERATOR;
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
        setTitle("添加子账户");
        addBack(R.id.rl_back);

        tvZhiWei.setText(zhiWeiType.getValue());
    }

    private void initListener() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null) {
                    return;
                }
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String role = zhiWeiType.getType();
                if (TextUtils.isEmpty(name)) {
                    CommonUtils.showToast("请输入名字");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    CommonUtils.showToast("请输入手机号");
                    return;
                }

                String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                String trans_no = System.currentTimeMillis()+"";
                String sign = CommonUtils.getSign(business_code,user_name,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));

                pushEventBlock(EventCode.HTTP_ADDBUSINESSSTAFF,business_code,user_name,trans_no,sign,phone,name,role);
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSSTAFF) {
            if (event.isSuccess()) {
                CommonUtils.showToast("添加成功");
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
