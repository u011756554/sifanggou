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
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.ZhiWeiDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.edt_pwd)
    private EditText edtPwd;

    private ZhiWeiType zhiWeiType = ZhiWeiType.OPERATOR;
    private ZhiWeiDialog zhiWeiDialog;
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
                String pwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    CommonUtils.showToast("请输入名字!");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    CommonUtils.showToast("请输入手机号!");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    CommonUtils.showToast("请输入登录密码!");
                    return;
                }

                String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                String user_name = PreManager.getString(getApplicationContext(),AppContext.USER_ACCOUNT);
                String trans_no = System.currentTimeMillis()+"";
                String sign = CommonUtils.getSign(business_code,user_name,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                try {
                    String password = CommonUtils.EncoderByMd5(pwd);
                    pushEventBlock(EventCode.HTTP_ADDBUSINESSSTAFF,business_code,user_name,trans_no,sign,phone,name,role,password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        rlZhiWei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhiWeiDialog == null) {
                    zhiWeiDialog = new ZhiWeiDialog(AddAccountActivity.this);
                    List<String> zhiweiList = new ArrayList<String>();
                    for (ZhiWeiType zwt : ZhiWeiType.values()) {
                        zhiweiList.add(zwt.getValue());
                    }
                    zhiWeiDialog.setData(zhiweiList,0);

                    zhiWeiDialog.setListener(new BaseDialog.DialogListener() {

                        @Override
                        public void update(Object object) {
                            String type = (String) object;
                            if (type != null) {
                                for (ZhiWeiType zwt : ZhiWeiType.values()) {
                                    if (zwt.getValue().equals(type)) {
                                        zhiWeiType = zwt;
                                        tvZhiWei.setText(type);
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }

                zhiWeiDialog.show();
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
