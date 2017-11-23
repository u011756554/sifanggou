package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.CarAdapter;
import com.app.sifanggou.adapter.ShangPinAdapter;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.HuoJiaType;
import com.app.sifanggou.bean.SaleType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCommodityInfoResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class CarActivity extends BaseActivity {
    @ViewInject(R.id.tv_price)
    private TextView tvPrice;
    @ViewInject(R.id.btn_xiadan)
    private Button btnXiaDan;

    //列表数据展示
    @ViewInject(R.id.srl_splist)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.ll_sp)
    ListView listView;
    private View listViewFooterView;
    private View emptyViewView;
    private TextView noMoreText;
    private TextView loadingText;
    private boolean isLoading = false;
    private boolean isOver = false;
    private boolean isRefreshing = false;
    private boolean isFirst = true;
    private int pageSize = 10;
    private int page = 1;
    private int headHeight;
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    private CarAdapter adapter;
    private List<BaseResponseBean> list = new ArrayList<BaseResponseBean>();
    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        setTitle("购物车");
        addBack(R.id.rl_back);

        //处理分页
        adapter = new CarAdapter(CarActivity.this,list);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterView = LayoutInflater.from(CarActivity.this).inflate(R.layout.mode_more, null);
        noMoreText = (TextView) listViewFooterView.findViewById(R.id.no_more);
        loadingText = (TextView) listViewFooterView.findViewById(R.id.load_more);
        emptyViewView = LayoutInflater.from(CarActivity.this).inflate(R.layout.mode_empty, null);

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
        refreshData();
    }

    private void initListener() {

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
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSSHOPPINGCARTLIST,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),AppContext.ITEM_NUM+"",page + "",KEY_REFRESH);
        }
    }

    private void getData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSSHOPPINGCARTLIST,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),AppContext.ITEM_NUM+"",page + "",KEY_MORE);
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSSHOPPINGCARTLIST) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
//                    if (event.isSuccess()) {
//                        GetBusinessCommodityInfoResponseBean bean = (GetBusinessCommodityInfoResponseBean) event.getReturnParamAtIndex(0);
//                        if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info() == null) {
//                            return;
//                        }
//                        List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info();
//                        if (tmpList != null) {
//                            page++;
//                            list.clear();
//                            adapter.updateData(tmpList);
//                            if (list.size() <= 0) {
//                                setNoData(true);
//                            } else {
//                                setNoData(false);
//                                if (tmpList.size() < pageSize) {
//                                    isOver = true;
//                                    listViewFooterView.setVisibility(View.VISIBLE);
//                                    noMoreText.setVisibility(View.VISIBLE);
//                                    loadingText.setVisibility(View.GONE);
//                                }
//                            }
//                        } else {
//                            if (list.size() <= 0) {
//                                setNoData(true);
//                            } else {
//                                setNoData(false);
//                            }
//                        }
//                        swipeRefreshLayout.setRefreshing(false);
//                        adapter.notifyDataSetChanged();
//                        isRefreshing = false;
//                    } else {
//                        CommonUtils.showToast(event.getFailMessage());
//                    }
                } else {
                    if (event.isSuccess()) {
//                        GetBusinessCommodityInfoResponseBean bean = (GetBusinessCommodityInfoResponseBean) event.getReturnParamAtIndex(0);
//                        if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info() == null) {
//                            return;
//                        }
//                        List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info();
//                        isLoading = false;
//                        if (tmpList != null) {
//                            page++;
//                            adapter.addAll(tmpList);
//                            if (list.size() <= 0) {
//                                setNoData(true);
//                            } else {
//                                setNoData(false);
//                                if (tmpList.size() < pageSize) {
//                                    isOver = true;
//                                    listViewFooterView.setVisibility(View.VISIBLE);
//                                    noMoreText.setVisibility(View.VISIBLE);
//                                    loadingText.setVisibility(View.GONE);
//                                } else {
//                                    listViewFooterView.setVisibility(View.GONE);
//                                }
//                            }
//                        } else {
//                            if (list.size() <= 0) {
//                                setNoData(true);
//                            } else {
//                                setNoData(false);
//                            }
//                        }
                    } else {
                        CommonUtils.showToast(event.getFailMessage());
                    }
                }

            } else {
                CommonUtils.showToast(event.getFailMessage());
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
}