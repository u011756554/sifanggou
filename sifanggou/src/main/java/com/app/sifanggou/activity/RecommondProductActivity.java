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
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.bean.CommodityCollectBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.RecommendCommodityBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityCollectResponseBean;
import com.app.sifanggou.net.bean.GetRecommendCommodityResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class RecommondProductActivity extends BaseActivity {

    @ViewInject(R.id.tv_mount)
    private TextView tvCarMount;
    @ViewInject(R.id.rl_car)
    private RelativeLayout rlCar;
    @ViewInject(R.id.btn_xiadan)
    private Button btnXiaDan;

    //商品列表数据展示
    @ViewInject(R.id.srl_product)
    private SwipeRefreshLayout swipeRefreshLayoutProduct;
    @ViewInject(R.id.ll_product)
    private ListView listViewProduct;
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
    private CommodityInfoBeanAdapter adapterProduct;
    private List<CommodityInfoBean> dataListProduct = new ArrayList<CommodityInfoBean>();

    private LoginResponseBean loginBean;
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    private int carCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("推荐商品");
        //处理商品分页
        adapterProduct = new CommodityInfoBeanAdapter(RecommondProductActivity.this,dataListProduct);
        swipeRefreshLayoutProduct.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewProduct = LayoutInflater.from(RecommondProductActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.no_more);
        loadingTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.load_more);
        emptyViewViewProduct = LayoutInflater.from(RecommondProductActivity.this).inflate(R.layout.mode_empty, null);

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
        adapterProduct.setListener(new CommodityInfoBeanAdapter.AddListener() {

            @Override
            public void click(CommodityInfoBean bean) {
                Intent intent = new Intent(RecommondProductActivity.this,ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_DATA,bean);
                startActivity(intent);
            }
        });

        rlCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommondProductActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        btnXiaDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommondProductActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });
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
        if (loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())){
            pageProduct = AppContext.PAGE;
            pushEventNoProgress(EventCode.HTTP_GETRECOMMENDCOMMODITY,
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    AppContext.PAGE_SIZE+"",pageProduct+"",KEY_REFRESH);
        }
    }

    private void getDataProduct() {
        if (loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())){
            pushEventNoProgress(EventCode.HTTP_GETRECOMMENDCOMMODITY,
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    AppContext.PAGE_SIZE+"",pageProduct+"",KEY_MORE);
        }
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
        if (event.getEventCode() == EventCode.HTTP_GETRECOMMENDCOMMODITY) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    GetRecommendCommodityResponseBean bean = (GetRecommendCommodityResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getRecommend_commodity() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
                    for(RecommendCommodityBean cc : bean.getData().getRecommend_commodity()) {
                        CommodityInfoBean tmpInfoBean = cc.getCommodity_info();
                        if (tmpInfoBean != null) {
                            tmpInfoBean.setBusiness_info(cc.getBusiness_info());
                        }
                        tmpList.add(tmpInfoBean);
                    }

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
                    GetRecommendCommodityResponseBean bean = (GetRecommendCommodityResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getRecommend_commodity() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
                    for(RecommendCommodityBean cc : bean.getData().getRecommend_commodity()) {
                        tmpList.add(cc.getCommodity_info());
                    }
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
