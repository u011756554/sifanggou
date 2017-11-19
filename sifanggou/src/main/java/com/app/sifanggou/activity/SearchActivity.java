package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SearchActivity extends BaseActivity {

    @ViewInject(R.id.edt_search)
    private EditText editSearch;
    @ViewInject(R.id.tv_search)
    private TextView tvSearch;
    @ViewInject(R.id.tv_cancel)
    private TextView tvCancel;

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
    }

    private void initListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                carget();
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editSearch.getText().toString();
                if (TextUtils.isEmpty(search)) {
                    CommonUtils.showToast("请输入完整");
                    return;
                }
                searchCommodity(search);
            }
        });
    }

    private void searchBusiness(String search) {
        String item_num = "10";
        String page_no = "0";
        pushEvent(EventCode.HTTP_SERACHBUSINESSONNAME,search,item_num,page_no);
    }

    private void searchCommodity(String search) {
        String item_num = "10";
        String page_no = "0";
        pushEvent(EventCode.HTTP_SEARCHBUSINESSCOMMODITYONNAME,search,item_num,page_no);
    }

    private void car() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEvent(EventCode.HTTP_UPDATEBUSINESSSHOPPINGCARTCOMMODITYNUM,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),20+"",1+"");
        }
    }

    private void carAdd() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEvent(EventCode.HTTP_ADDBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),20+"",1+"");
        }
    }

    private void cardel() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEvent(EventCode.HTTP_DELBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),20+"");
        }
    }

    private void carget() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEvent(EventCode.HTTP_GETBUSINESSSHOPPINGCARTLIST,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code());
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_SERACHBUSINESSONNAME) {
            if (event.isSuccess()) {

            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_SEARCHBUSINESSCOMMODITYONNAME) {
            if (event.isSuccess()) {

            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
