package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.AllBusinessUrgentSellCommodityAdapter;
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.bean.AllBusinessUrgentSellCommodityBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetAllBusinessUrgentSellCommodityResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.net.bean.SearchBusinessCommodityOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class JiXuProductListActivity extends BaseActivity {

    @ViewInject(R.id.btn_xiadan)
    private Button btnXiaDan;
    //商品列表数据展示
    @ViewInject(R.id.srl_product)
    private SwipeRefreshLayout swipeRefreshLayoutProduct;
    @ViewInject(R.id.ll_product)
    private ListView listViewProduct;
    @ViewInject(R.id.tv_mount)
    private TextView tvCarMount;
    @ViewInject(R.id.rl_car)
    private RelativeLayout rlCar;
    private View listViewFooterViewProduct;
    private View emptyViewViewProduct;
    private TextView noMoreTextProduct;
    private TextView loadingTextProduct;
    private boolean isLoadingProduct = false;
    private boolean isOverProduct = false;
    private boolean isRefreshingProduct = false;
    private boolean isFirstProduct = true;
    private int pageSizeProduct = AppContext.PAGE_SIZE;
    private int pageProduct = AppContext.PAGE;
    private int headHeightProduct;
    private AllBusinessUrgentSellCommodityAdapter adapterProduct;
    private List<AllBusinessUrgentSellCommodityBean> dataListProduct = new ArrayList<AllBusinessUrgentSellCommodityBean>();

    private LoginResponseBean loginBean;
    private int carCount = 0;

    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("急需出仓");

        adapterProduct = new AllBusinessUrgentSellCommodityAdapter(JiXuProductListActivity.this,dataListProduct);
        swipeRefreshLayoutProduct.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewProduct = LayoutInflater.from(JiXuProductListActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.no_more);
        loadingTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.load_more);
        emptyViewViewProduct = LayoutInflater.from(JiXuProductListActivity.this).inflate(R.layout.mode_empty, null);

        listViewProduct.addHeaderView(emptyViewViewProduct);
        listViewProduct.addFooterView(listViewFooterViewProduct);
        listViewFooterViewProduct.setVisibility(View.GONE);
        emptyViewViewProduct.setVisibility(View.GONE);

        listViewProduct.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listViewProduct.getLastVisiblePosition() == listViewProduct.getCount() - 1)
                {
                    if(dataListProduct.size() != 0)
                        loadProduct();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });

        listViewProduct.setAdapter(adapterProduct);

        swipeRefreshLayoutProduct.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshProduct();
            }
        });

        refreshProduct();
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
    }

    private void initListener() {
        adapterProduct.setListener(new AllBusinessUrgentSellCommodityAdapter.AddListener() {
            @Override
            public void add(AllBusinessUrgentSellCommodityBean bean) {
                carAdd(bean);
            }

            @Override
            public void click(AllBusinessUrgentSellCommodityBean bean) {
                Intent intent = new Intent(JiXuProductListActivity.this,ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_ID,bean.getCommodity_id());
                startActivity(intent);
            }
        });

        rlCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JiXuProductListActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        btnXiaDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JiXuProductListActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carAdd(AllBusinessUrgentSellCommodityBean bean) {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_ADDBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),bean.getCommodity_id(),bean.getSelectCount()+"");
        }
    }

    //商品UI
    private void loadProduct() {
        if (!isRefreshingProduct && !isLoadingProduct && !isOverProduct) {
            isLoadingProduct = true;
            listViewFooterViewProduct.setVisibility(View.VISIBLE);
            loadingTextProduct.setVisibility(View.VISIBLE);
            noMoreTextProduct.setVisibility(View.GONE);
            isOverProduct = false;
            getDataProduct();
        }
    }

    private void refreshProduct() {
        if (!isLoadingProduct && !isRefreshingProduct) {
            listViewFooterViewProduct.setVisibility(View.GONE);
            isRefreshingProduct = true;
            isOverProduct = false;
            refreshDataProduct();
        }
    }

    private void refreshDataProduct() {
        pageProduct = AppContext.PAGE;
        pushEventNoProgress(EventCode.HTTP_GETALLBUSINESSURGENTSELLCOMMODITY,AppContext.PAGE_SIZE+"",pageProduct+"",KEY_REFRESH);
    }

    private void getDataProduct() {
        pushEventNoProgress(EventCode.HTTP_GETALLBUSINESSURGENTSELLCOMMODITY,AppContext.PAGE_SIZE+"",pageProduct+"",KEY_MORE);

    }

    public void setNoDataProduct(boolean show) {
        if (isFirstProduct) {
            CommonUtils.measureView(emptyViewViewProduct);
            headHeightProduct = emptyViewViewProduct.getMeasuredHeight();
            isFirstProduct = false;

        }
        showEmptyProduct(show);
    }

    private void showEmptyProduct(boolean show) {
        if (show) {
            emptyViewViewProduct.setVisibility(View.VISIBLE);
            emptyViewViewProduct.setPadding(0, 0, 0, 0);
            listViewFooterViewProduct.setVisibility(View.GONE);
            emptyViewViewProduct.setClickable(false);
        } else {
            emptyViewViewProduct.setVisibility(View.GONE);
            emptyViewViewProduct.setPadding(0, - headHeightProduct, 0, 0);
            emptyViewViewProduct.setClickable(false);
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSSHOPPINGCART) {
            if (event.isSuccess()) {
                String commodity_num = (String) event.getReturnParamAtIndex(1);
                carCount =  carCount + Integer.valueOf(commodity_num);
                tvCarMount.setText(carCount+"");
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_GETALLBUSINESSURGENTSELLCOMMODITY) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    GetAllBusinessUrgentSellCommodityResponseBean bean = (GetAllBusinessUrgentSellCommodityResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getAll_business_urgent_sell_commodity() == null) {
                        return;
                    }
                    List<AllBusinessUrgentSellCommodityBean> tmpList = bean.getData().getAll_business_urgent_sell_commodity();
                    if (tmpList != null) {
                        pageProduct++;
                        dataListProduct.clear();
                        adapterProduct.updateData(tmpList);
                        if (dataListProduct.size() <= 0) {
                            setNoDataProduct(true);
                        } else {
                            setNoDataProduct(false);
                            if (tmpList.size() < pageSizeProduct) {
                                isOverProduct = true;
                                listViewFooterViewProduct.setVisibility(View.VISIBLE);
                                noMoreTextProduct.setVisibility(View.VISIBLE);
                                loadingTextProduct.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (dataListProduct.size() <= 0) {
                            setNoDataProduct(true);
                        } else {
                            setNoDataProduct(false);
                        }
                    }
                    swipeRefreshLayoutProduct.setRefreshing(false);
                    adapterProduct.notifyDataSetChanged();
                    isRefreshingProduct = false;
                } else {
                    GetAllBusinessUrgentSellCommodityResponseBean bean = (GetAllBusinessUrgentSellCommodityResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getAll_business_urgent_sell_commodity() == null) {
                        return;
                    }
                    List<AllBusinessUrgentSellCommodityBean> tmpList = bean.getData().getAll_business_urgent_sell_commodity();
                    isLoadingProduct = false;
                    if (tmpList != null) {
                        pageProduct++;
                        adapterProduct.addAll(tmpList);
                        if (dataListProduct.size() <= 0) {
                            setNoDataProduct(true);
                        } else {
                            setNoDataProduct(false);
                            if (tmpList.size() < pageSizeProduct) {
                                isOverProduct = true;
                                listViewFooterViewProduct.setVisibility(View.VISIBLE);
                                noMoreTextProduct.setVisibility(View.VISIBLE);
                                loadingTextProduct.setVisibility(View.GONE);
                            } else {
                                listViewFooterViewProduct.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (dataListProduct.size() <= 0) {
                            setNoDataProduct(true);
                        } else {
                            setNoDataProduct(false);
                        }
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
