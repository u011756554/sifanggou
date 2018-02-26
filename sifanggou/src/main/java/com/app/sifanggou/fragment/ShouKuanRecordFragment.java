package com.app.sifanggou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.CarAdapter;
import com.app.sifanggou.adapter.CarItemAdapter;
import com.app.sifanggou.adapter.InOutOrderInfoAdapter;
import com.app.sifanggou.bean.CarBean;
import com.app.sifanggou.bean.OrderNoBaseBean;
import com.app.sifanggou.bean.OrderType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinenessInOutOrderInfoResponseBean;
import com.app.sifanggou.net.bean.GetBusinessShoppingCartListResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.MyListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ShouKuanRecordFragment extends BaseFragment {

    //列表数据展示
    @ViewInject(R.id.srl_splist)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.ll_sp)
    private MyListView listView;
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

    private InOutOrderInfoAdapter adapter;
    private List<OrderNoBaseBean> list = new ArrayList<OrderNoBaseBean>();
    private LoginResponseBean loginBean;

    private String chooseYear = CommonUtils.getYear();
    private String chosseMonth = CommonUtils.getMonth();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shoukuanrecord);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        initView();
        initListener();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        //处理分页
        adapter = new InOutOrderInfoAdapter(getActivity(),list,InOutOrderInfoAdapter.TYPE_DAISHOUKUAN);

        swipeRefreshLayout.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.mode_more, null);
        noMoreText = (TextView) listViewFooterView.findViewById(R.id.no_more);
        loadingText = (TextView) listViewFooterView.findViewById(R.id.load_more);
        emptyViewView = LayoutInflater.from(getActivity()).inflate(R.layout.mode_empty, null);

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

    private void initListener() {
        adapter.setDaiShouListener(new InOutOrderInfoAdapter.DaiShouListener() {
            @Override
            public void shouKuan(OrderNoBaseBean bean) {

            }

            @Override
            public void chat(OrderNoBaseBean bean) {
                if (bean.getBuyer_business_info() != null) {
                    RongIM.getInstance().startPrivateChat(getActivity(), bean.getBuyer_business_code(), bean.getBuyer_business_info().getName());
                }
            }
        });
    }

    private void initData() {
        loginBean = PreManager.get(getActivity().getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        refreshData();
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
            pushEventNoProgress(EventCode.HTTP_GETBUSINENESSINOUTORDERINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(), OrderType.RECEIPTED.getType(),AppContext.ITEM_NUM+"",page + "",KEY_REFRESH,chooseYear,chosseMonth);
        }
    }

    private void getData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINENESSINOUTORDERINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(), OrderType.RECEIPTED.getType(),AppContext.ITEM_NUM+"",page + "",KEY_MORE,chooseYear,chosseMonth);
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINENESSINOUTORDERINFO) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    GetBusinenessInOutOrderInfoResponseBean bean = (GetBusinenessInOutOrderInfoResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_order_info() == null) {
                        return;
                    }
                    List<OrderNoBaseBean> tmpList = new ArrayList<OrderNoBaseBean>();
                    for(OrderNoBaseBean carBean : bean.getData().getBusiness_order_info()) {
                        tmpList.add(carBean);
                    }
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
                    GetBusinenessInOutOrderInfoResponseBean bean = (GetBusinenessInOutOrderInfoResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_order_info() == null) {
                        return;
                    }
                    List<OrderNoBaseBean> tmpList = new ArrayList<OrderNoBaseBean>();
                    for(OrderNoBaseBean carBean : bean.getData().getBusiness_order_info()) {
                        tmpList.add(carBean);
                    }
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

    public void chooseTime(String year,String month) {
        this.chooseYear = year;
        this.chosseMonth = month;
        refresh();
    }
}
