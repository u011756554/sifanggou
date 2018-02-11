package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
 * Created by Administrator on 2018/1/16.
 */

public class UpdateNickNameActivity extends BaseActivity {

    @ViewInject(R.id.edt_nickname)
    private EditText edtNickName;

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private  void initView() {
        addBack(R.id.rl_back);
        setTitle("修改昵称");

    }

    private void initListener() {
        setRightTextClickListener("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = edtNickName.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    CommonUtils.showToast("请输入昵称");
                    return;
                }
                if (loginBean != null && loginBean.getData() != null
                        && loginBean.getData().getLogin_info() != null
                        && loginBean.getData().getLogin_info().getBusiness_info() != null
                        && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
                    pushEventBlock(EventCode.HTTP_UPDATEBUSINESSINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                            nickName,"","","","","","","","","");
                }
            }
        });
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        if (loginBean != null) {
            edtNickName.setText(loginBean.getData().getLogin_info().getName());
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_UPDATEBUSINESSINFO) {
            if (event.isSuccess()) {
                CommonUtils.showToast("修改昵称成功");
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
