package com.app.sifanggou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.activity.DianPuDetailActivity;
import com.app.sifanggou.activity.ProductDetailActivity;
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.adapter.ShangPinListAdapter;
import com.app.sifanggou.adapter.ShangPinPiLiangAdapter;
import com.app.sifanggou.bean.CommityInfoType;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.SaleType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityInfoResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class DaiLiProductFragment extends BaseFragment {

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
    private int pageSize = AppContext.PAGE_SIZE;
    private int page = AppContext.PAGE;
    private int headHeight;
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    //数据区域
    private List<CommodityInfoBean> list=new ArrayList<CommodityInfoBean>();
    private CommodityInfoBeanAdapter adapter;

    private LoginResponseBean loginBean;
    private String BUSINESS_CODE = "";

    private String commityInfoTypeALLType = CommityInfoType.AGENCY.getType();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dailiproduct);
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
        adapter = new CommodityInfoBeanAdapter(getActivity(),list);
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

    private void initData() {
        loginBean = PreManager.get(getActivity().getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String business_code = bundle.getString(DianPuDetailActivity.KEY_ID);
            if (!TextUtils.isEmpty(business_code)) {
                BUSINESS_CODE = business_code;
                refreshData();
            }
        }
    }

    private void initListener() {
        adapter.setListener(new CommodityInfoBeanAdapter.AddListener() {

            @Override
            public void click(CommodityInfoBean bean) {
                Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_DATA,bean);
                startActivity(intent);
            }
        });
    }

    private void carAdd(CommodityInfoBean bean) {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_ADDBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),bean.getCommodity_id(),bean.getSelectCount()+"");
        }
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
        if (!TextUtils.isEmpty(BUSINESS_CODE)
                && loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            page = 0;
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYINFO2,BUSINESS_CODE,
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    commityInfoTypeALLType,AppContext.ITEM_NUM+"",page + "",KEY_REFRESH);
        }
    }

    private void getData() {
        if (!TextUtils.isEmpty(BUSINESS_CODE)
                && loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYINFO2,BUSINESS_CODE,
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    commityInfoTypeALLType,AppContext.ITEM_NUM+"",page + "",KEY_MORE);
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

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSSHOPPINGCART) {
            if (event.isSuccess()) {
                String commodity_num = (String) event.getReturnParamAtIndex(1);
                ((DianPuDetailActivity)getActivity()).carCount = ((DianPuDetailActivity)getActivity()).carCount + Integer.valueOf(commodity_num);
                ((DianPuDetailActivity)getActivity()).tvCarMount.setText(((DianPuDetailActivity)getActivity()).carCount + "");
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCOMMODITYINFO2) {
            String type = (String) event.getReturnParamAtIndex(1);
            if (type.equals(KEY_REFRESH)) {
                CommonUtils.showToast(KEY_REFRESH);
                if (event.isSuccess()) {
                    GetBusinessCommodityInfoResponseBean bean = (GetBusinessCommodityInfoResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info();
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
                    GetBusinessCommodityInfoResponseBean bean = (GetBusinessCommodityInfoResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info();
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
}
