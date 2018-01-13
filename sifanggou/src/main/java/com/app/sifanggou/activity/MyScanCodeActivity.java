package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.BaseBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class MyScanCodeActivity extends BaseActivity {

    @ViewInject(R.id.iv_code)
    private ImageView ivCode;

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
        setTitle("我的二维码");
        addBack(R.id.rl_back);

        if (loginBean != null) {
            ivCode.setImageBitmap(CommonUtils.encodeAsBitmap(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code()));
        }
    }

    private void initListener() {
        setRightTextClickListener("扫一扫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customScan();
            }
        });
    }

    public void customScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(true)
//                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,"扫描成功"+ScanResult,Toast.LENGTH_LONG).show();

                pushEventBlock(EventCode.HTTP_GETBUSINESSINFO,ScanResult);
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSINFO) {
            if (event.isSuccess()) {
                Intent intent = new Intent(MyScanCodeActivity.this,DianPuDetailActivity.class);
//                intent.putExtra(DianPuDetailActivity.KEY_DATA, BaseBean);
                startActivity(intent);
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
