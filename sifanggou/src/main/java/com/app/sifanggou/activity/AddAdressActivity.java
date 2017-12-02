package com.app.sifanggou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AddressTimeType;
import com.app.sifanggou.bean.AdressBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.AddressTypeDialog;
import com.app.sifanggou.view.BaseDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class AddAdressActivity extends BaseActivity {

    @ViewInject(R.id.edt_shouhuo)
    private EditText edtShouHuo;
    @ViewInject(R.id.edt_phone)
    private EditText edtPhone;
    @ViewInject(R.id.tv_time)
    private TextView tvTime;
    @ViewInject(R.id.tv_type)
    private TextView tvType;
    @ViewInject(R.id.tv_area)
    private TextView tvArea;
    @ViewInject(R.id.edt_address)
    private EditText edtAddress;

    private AddressTypeDialog addressTypeDialog;

    private String timeType = AddressTimeType.WORKDAY.getType();
    private String area = "";
    private LoginResponseBean loginBean;

    public static final String KEY_TYPE = "key_AddAdressActivity_type";
    public static final String KEY_DATA = "key_AddAdressActivity_data";
    public static final String TYPE_EDIT = "edit";
    private String type = "";
    private AdressBean adressBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra(KEY_TYPE);
        if (type == null) {
            type = "";
        }
        adressBean = (AdressBean) getIntent().getSerializableExtra(KEY_DATA);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        if (type.equals(TYPE_EDIT)) {
            setTitle("编辑收货地址");
        } else {
            setTitle("添加收货地址");
        }

        addressTypeDialog = new AddressTypeDialog(AddAdressActivity.this);

        if (adressBean != null) {
            edtShouHuo.setText(adressBean.getReceiver_name());
            edtPhone.setText(adressBean.getMobile());
            edtAddress.setText(adressBean.getAddress());
            tvTime.setText(adressBean.getDelivery_time());
        }
    }

    private void initData() {
        List<String> typeList = new ArrayList<String>();
        for(int i = 0 ;i< AddressTimeType.values().length ; i++) {
            typeList.add(AddressTimeType.values()[i].getType());
        }
        addressTypeDialog.setData(typeList,1);
        area = tvArea.getText().toString();
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
    }

    private void initListener() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressTypeDialog.setListener(new BaseDialog.DialogListener() {
                    @Override
                    public void update(Object object) {
                        String type = (String) object;
                        timeType = type;
                        tvTime.setText(type);
                    }
                });
                addressTypeDialog.show();
            }
        });

        setRightTextClickListener("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null) {
                    return;
                }
                if (TextUtils.isEmpty(edtShouHuo.getText().toString())) {
                    CommonUtils.showToast("输入收货人");
                    return;
                }
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    CommonUtils.showToast("输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(edtAddress.getText().toString())) {
                    CommonUtils.showToast("输入收货地址");
                    return;
                }
                String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                String trans_no = System.currentTimeMillis() + "";
                String sign = CommonUtils.getSign(business_code,user_name,trans_no, PreManager.getString(getApplicationContext(), AppContext.USER_PWD));
                String receiver_name = edtShouHuo.getText().toString();
                String mobile = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();
                if (type.equals(TYPE_EDIT)) {
                    pushEventBlock(EventCode.HTTP_UPDATEBUSINESSDELIVERADDRESS,business_code,user_name,trans_no,sign,adressBean.getDelivery_id(),receiver_name,mobile,address,timeType);
                } else {
                    pushEventBlock(EventCode.HTTP_ADDBUSINESSDELIVERADDRESS,business_code,user_name,trans_no,sign,receiver_name,mobile,address,timeType);
                }
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSDELIVERADDRESS) {
            if (event.isSuccess()) {
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_UPDATEBUSINESSDELIVERADDRESS) {
            if (event.isSuccess()) {
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
