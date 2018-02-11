package com.app.sifanggou.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessInviteCodeResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/6.
 */

public class InviteCodeActivity extends BaseActivity {

    @ViewInject(R.id.tv_code)
    private TextView tvCode;
    @ViewInject(R.id.btn_share)
    private Button btnShare;

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        setTitle("邀请码");
        addBack(R.id.rl_back);
        setRightResource(R.drawable.icon_share_white);
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        if (loginBean != null) {
            pushEvent(EventCode.HTTP_GETBUSINESSINVITECODE,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code());
            if (loginBean.getData() != null
                    && loginBean.getData().getLogin_info() != null
                    && loginBean.getData().getLogin_info().getBusiness_info() != null
                    && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getInvite_code())) {
                tvCode.setText(loginBean.getData().getLogin_info().getBusiness_info().getInvite_code());
            }
        }
    }

    private void initListener() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invitCode = tvCode.getText().toString();
                if (TextUtils.isEmpty(invitCode)) {
                    CommonUtils.showToast("邀请码为空");
                    return;
                }
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(invitCode);
                CommonUtils.showToast("复制成功");
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invitCode = tvCode.getText().toString();
                if (TextUtils.isEmpty(invitCode)) {
                    CommonUtils.showToast("邀请码为空");
                    return;
                }
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(invitCode);
                CommonUtils.showToast("复制成功");
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSINVITECODE) {
            if (event.isSuccess()) {
                GetBusinessInviteCodeResponseBean bean = (GetBusinessInviteCodeResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getBusiness_invite_code())) {
                    tvCode.setText(bean.getData().getBusiness_invite_code());
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

    }
}
