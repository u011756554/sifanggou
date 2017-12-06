package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.OrderType;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/12/2.
 */

public class BuyOrderActivity extends BaseActivity {

    //列表数据展示
    @ViewInject(R.id.srl_orderlist)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.ll_order)
    ListView listView;
    private View listViewFooterView;
    private View emptyViewView;
    private TextView noMoreText;
    private TextView loadingText;
    private boolean isLoading = false;
    private boolean isOver = false;
    private boolean isRefreshing = false;
    private boolean isFirst = true;
    private int pageSize = AppContext.PAGE_SIZE;
    private int page = AppContext.PAGE;
    private int headHeight;
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("买进");
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            page = 0;
            pushEventNoProgress(EventCode.HTTP_GETBUSINENESSINOUTORDERINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(), OrderType.WAITING_PAY.getType(),AppContext.ITEM_NUM+"",page + "",KEY_REFRESH,"2017","12");
        }
    }

}
