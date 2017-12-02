package com.app.sifanggou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.AddressAdapter;
import com.app.sifanggou.bean.AdressBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessDeliverAddressResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class AdressActivity extends BaseActivity {
    @ViewInject(R.id.rl_add)
    private RelativeLayout rlAdd;

    //列表数据展示
    @ViewInject(R.id.srl_adresslist)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.ll_adress)
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

    private AddressAdapter adapter;
    private List<AdressBean> list = new ArrayList<AdressBean>();
    private LoginResponseBean loginBean;
    public static final String KEY_SELECT = "key_AdressActivity_select";
    public static final String TYPE = "select";
    private String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("地址管理");

        //处理分页
        adapter = new AddressAdapter(AdressActivity.this,list,type);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterView = LayoutInflater.from(AdressActivity.this).inflate(R.layout.mode_more, null);
        noMoreText = (TextView) listViewFooterView.findViewById(R.id.no_more);
        loadingText = (TextView) listViewFooterView.findViewById(R.id.load_more);
        emptyViewView = LayoutInflater.from(AdressActivity.this).inflate(R.layout.mode_empty, null);

        listView.addHeaderView(emptyViewView);
        listView.addFooterView(listViewFooterView);
        listViewFooterView.setVisibility(View.GONE);
        emptyViewView.setVisibility(View.GONE);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listView.getLastVisiblePosition() == listView.getCount() - 1)
                {
                    if(list.size() != 0)
                        load();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });

        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }});
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        type = getIntent().getStringExtra(KEY_SELECT);
        if (type == null) {
            type = "";
        }
        refreshData();
    }

    private void initListener() {
        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdressActivity.this,AddAdressActivity.class);
                startActivityForResult(intent,REQUESTCODE_ADD);
            }
        });
    }

    private void load() {
        if (!isRefreshing && !isLoading && !isOver) {
            isLoading = true;
            listViewFooterView.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.VISIBLE);
            noMoreText.setVisibility(View.GONE);
            isOver = false;
            getData();
        }
    }

    private void refresh() {
        if (!isLoading && !isRefreshing) {
            listViewFooterView.setVisibility(View.GONE);
            isRefreshing = true;
            isOver = false;
            refreshData();
        }
    }

    private void refreshData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            page = 0;
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSDELIVERADDRESS,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),AppContext.ITEM_NUM+"",page + "",KEY_REFRESH);
        }
    }

    private void getData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSDELIVERADDRESS,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),AppContext.ITEM_NUM+"",page + "",KEY_MORE);
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSDELIVERADDRESS) {
            String type = (String) event.getReturnParamAtIndex(1);
            if (type.equals(KEY_REFRESH)) {
                CommonUtils.showToast(KEY_REFRESH);
                if (event.isSuccess()) {
                    GetBusinessDeliverAddressResponseBean bean = (GetBusinessDeliverAddressResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getDeliver_address_list() == null) {
                        return;
                    }
                    List<AdressBean> tmpList = bean.getData().getDeliver_address_list();
                    if (tmpList != null) {
                        page++;
                        list.clear();
                        adapter.updateData(tmpList);
                        if (list.size() <= 0) {
                            setNoData(true);
                        } else {
                            setNoData(false);
                            if (tmpList.size() < pageSize) {
                                isOver = true;
                                listViewFooterView.setVisibility(View.VISIBLE);
                                noMoreText.setVisibility(View.VISIBLE);
                                loadingText.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (list.size() <= 0) {
                            setNoData(true);
                        } else {
                            setNoData(false);
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    isRefreshing = false;
                } else {
                    CommonUtils.showToast(event.getFailMessage());
                }
            } else {
                CommonUtils.showToast(KEY_MORE);
                if (event.isSuccess()) {
                    GetBusinessDeliverAddressResponseBean bean = (GetBusinessDeliverAddressResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getDeliver_address_list() == null) {
                        return;
                    }
                    List<AdressBean> tmpList = bean.getData().getDeliver_address_list();
                    isLoading = false;
                    if (tmpList != null) {
                        page++;
                        adapter.addAll(tmpList);
                        if (list.size() <= 0) {
                            setNoData(true);
                        } else {
                            setNoData(false);
                            if (tmpList.size() < pageSize) {
                                isOver = true;
                                listViewFooterView.setVisibility(View.VISIBLE);
                                noMoreText.setVisibility(View.VISIBLE);
                                loadingText.setVisibility(View.GONE);
                            } else {
                                listViewFooterView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (list.size() <= 0) {
                            setNoData(true);
                        } else {
                            setNoData(false);
                        }
                    }
                } else {
                    CommonUtils.showToast(event.getFailMessage());
                }
            }
        }
    }


    public void setNoData(boolean show) {
        if (isFirst) {
            CommonUtils.measureView(emptyViewView);
            headHeight = emptyViewView.getMeasuredHeight();
            isFirst = false;

        }
        showEmpty(show);
    }

    private void showEmpty(boolean show) {
        if (show) {
            emptyViewView.setVisibility(View.VISIBLE);
            emptyViewView.setPadding(0, 0, 0, 0);
            listViewFooterView.setVisibility(View.GONE);
            emptyViewView.setClickable(false);
        } else {
            emptyViewView.setVisibility(View.GONE);
            emptyViewView.setPadding(0, - headHeight, 0, 0);
            emptyViewView.setClickable(false);
        }
    }

    private static final int REQUESTCODE_ADD = 0x1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUESTCODE_ADD) {
                refresh();
            }
        }
    }
}
