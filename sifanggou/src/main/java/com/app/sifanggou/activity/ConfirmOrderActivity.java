package com.app.sifanggou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AdressBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessDefaultDeliverAddressResponseBean;
import com.app.sifanggou.net.bean.GetBusinessShoppingCartListResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/12/2.
 */

public class ConfirmOrderActivity extends BaseActivity {

    @ViewInject(R.id.rl_add)
    private RelativeLayout rlAdd;
    @ViewInject(R.id.rl_address)
    private RelativeLayout rlAddress;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_phone)
    private TextView tvPhone;
    @ViewInject(R.id.tv_address)
    private TextView tvAddress;
    @ViewInject(R.id.tv_times)
    private TextView tvTimes;
    @ViewInject(R.id.tv_price)
    private TextView tvPrice;
    @ViewInject(R.id.tv_sp_price)
    private TextView tvSPrice;
    @ViewInject(R.id.tv_yunfei)
    private TextView tvYunFei;
    @ViewInject(R.id.btn_tijiao)
    private Button btnTiJiao;

    private AdressBean mAdressBean;

    public static final String KEY_DATA = "key_ConfirmOrderActivity_data";
    private GetBusinessShoppingCartListResponseBean dataBean;
    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void initData() {
        dataBean = (GetBusinessShoppingCartListResponseBean) getIntent().getSerializableExtra(KEY_DATA);
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);

        getMorenAdress();
    }

    private void getMorenAdress() {
        if (loginBean == null
                || loginBean.getData() == null
                || loginBean.getData().getLogin_info() == null
                || loginBean.getData().getLogin_info().getBusiness_info() == null
                || TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            return;
        }
        pushEventNoProgress(EventCode.HTTP_GETBUSINESSDEFAULTDELIVERADDRESS,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code());
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("确认订单");

        if (dataBean != null) {
            if (dataBean.getData() != null && dataBean.getData().getBusiness_shoppingcart_list() != null && !TextUtils.isEmpty(dataBean.getData().getBusiness_shoppingcart_list().getCommodity_total_amount())) {
                float price = Float.valueOf(dataBean.getData().getBusiness_shoppingcart_list().getCommodity_total_amount()) / 100;
                tvPrice.setText("￥"+price);
                tvSPrice.setText("￥"+price);
            }

            tvYunFei.setText("￥0");
        }
    }

    private void refreshView(){
        if (mAdressBean == null) {
            rlAddress.setVisibility(View.GONE);
            rlAdd.setVisibility(View.VISIBLE);
        } else {
            rlAddress.setVisibility(View.VISIBLE);
            rlAdd.setVisibility(View.GONE);

            tvName.setText(mAdressBean.getReceiver_name());
            tvPhone.setText(mAdressBean.getMobile());
            tvAddress.setText(mAdressBean.getAddress());
            tvTimes.setText(mAdressBean.getDelivery_time());
        }
    }

    private void initListener(){
        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this,AdressActivity.class);
                intent.putExtra(AdressActivity.KEY_SELECT,AdressActivity.TYPE_SELECT);
                startActivityForResult(intent,REQUEST_ADD);
            }
        });

        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this,AdressActivity.class);
                intent.putExtra(AdressActivity.KEY_SELECT,AdressActivity.TYPE_SELECT);
                intent.putExtra(AdressActivity.KEY_DATA,mAdressBean);
                startActivityForResult(intent,REQUEST_ADD);
            }
        });

        btnTiJiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null ||dataBean == null) {
                    return;
                }
                if (mAdressBean == null) {
                    CommonUtils.showToast("无效地址");
                    return;
                }
                String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                String trans_no = System.currentTimeMillis() + "";
                String sign = CommonUtils.getSign(business_code,user_name,trans_no, PreManager.getString(getApplicationContext(), AppContext.USER_PWD));

                String amount = dataBean.getData().getBusiness_shoppingcart_list().getCommodity_total_amount();
                String express_fee = "0";
                String pay_mode = "offline";
                String delivery_id = mAdressBean.getDelivery_id();

                pushEventBlock(EventCode.HTTP_BUSINESSSUBMITORDER,business_code,user_name,trans_no,sign,amount,express_fee,pay_mode,delivery_id);
            }
        });
    }

    private static final int REQUEST_ADD = 0x1;
    public static final String RESULT_ADDRESS = "key_ConfirmOrderActivity_address";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ADD) {
                if (data != null) {
                    mAdressBean = (AdressBean) data.getSerializableExtra(RESULT_ADDRESS);
                    refreshView();
                }
            }
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSDEFAULTDELIVERADDRESS) {
            if (event.isSuccess()) {
                GetBusinessDefaultDeliverAddressResponseBean bean = (GetBusinessDefaultDeliverAddressResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && bean.getData().getDefault_deliver_address() != null) {
                    mAdressBean = bean.getData().getDefault_deliver_address().getDeliver_address();
                    refreshView();
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_BUSINESSSUBMITORDER) {
            if (event.isSuccess()) {
                CommonUtils.showToast("下单成功");
                Intent intent = new Intent(ConfirmOrderActivity.this, BurOrderTabActivity.class);
                intent.putExtra(BurOrderTabActivity.KEY_TYPE,BurOrderTabActivity.VALUE_TYPE_DAIJIE);
                startActivity(intent);
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
