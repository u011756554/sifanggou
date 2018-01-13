package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.sifanggou.R;
import com.app.sifanggou.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class UpdateOldPwdActivity extends BaseActivity {

    @ViewInject(R.id.edt_pwd)
    private EditText edtPwd;

    @ViewInject(R.id.edt_pwd_again)
    private EditText edtPwdAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setTitle("修改密码");
        addBack(R.id.rl_back);
    }

    private void initListener() {
        setRightTextClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = edtPwd.getText().toString();
                String pwdAgain = edtPwdAgain.getText().toString();

                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                    CommonUtils.showToast("请输入完整");
                    return;
                }

                if (!pwd.equals(pwdAgain)) {
                    CommonUtils.showToast("两次密码输入不一致");
                    return;
                }


            }
        });
    }
}
