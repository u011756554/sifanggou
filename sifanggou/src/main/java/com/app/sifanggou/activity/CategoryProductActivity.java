package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.BusinessInfoBeanAdapter;
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.OrderTreeType;
import com.app.sifanggou.bean.SearchType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityByCategoryCodeResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.net.bean.SearchBusinessCommodityOnNameResponseBean;
import com.app.sifanggou.net.bean.SerachBusinessOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class CategoryProductActivity extends BaseActivity {

    //商品列表数据展示
    @ViewInject(R.id.srl_product)
    private SwipeRefreshLayout swipeRefreshLayoutProduct;
    @ViewInject(R.id.ll_product)
    private ListView listViewProduct;
    @ViewInject(R.id.tv_mount)
    private TextView tvCarMount;
    @ViewInject(R.id.rl_car)
    private RelativeLayout rlCar;
    @ViewInject(R.id.btn_car)
    private Button btnCar;
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
    private int carCount = 0;

    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    public static final String KEY_FIRSTTYPE = "key_CategoryProductActivity_first";
    public static final String KEY_SECOND = "key_CategoryProductActivity_second";
    private String first_category_code = "";
    private String second_category_code = "";

    private OrderTreeType orderTreeType = OrderTreeType.PRICE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("产品列表");
        setRightResource(R.drawable.icon_search_dianpu);
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryProductActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        //处理商品分页
        adapterProduct = new CommodityInfoBeanAdapter(CategoryProductActivity.this,dataListProduct);
        swipeRefreshLayoutProduct.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewProduct = LayoutInflater.from(CategoryProductActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.no_more);
        loadingTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.load_more);
        emptyViewViewProduct = LayoutInflater.from(CategoryProductActivity.this).inflate(R.layout.mode_empty, null);

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
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        first_category_code = getIntent().getStringExtra(KEY_FIRSTTYPE);
        second_category_code = getIntent().getStringExtra(KEY_SECOND);
        refreshProduct();
    }

    private void initListener() {
        adapterProduct.setListener(new CommodityInfoBeanAdapter.AddListener() {

            @Override
            public void click(CommodityInfoBean bean) {
                Intent intent = new Intent(CategoryProductActivity.this,ProductDetailActivity.class);
                intent.putExtra(ProductPicActivity.KEY_DATA,bean);
                startActivity(intent);
            }
        });

        rlCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryProductActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryProductActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carAdd(CommodityInfoBean bean) {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_ADDBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),bean.getCommodity_id(),bean.getSelectCount()+"");
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

        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCOMMODITYBYCATEGORYCODE) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    GetBusinessCommodityByCategoryCodeResponseBean bean = (GetBusinessCommodityByCategoryCodeResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info_list() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info_list();
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
                    GetBusinessCommodityByCategoryCodeResponseBean bean = (GetBusinessCommodityByCategoryCodeResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info_list() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getBusiness_commodity_info_list();
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
        if(loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYBYCATEGORYCODE,first_category_code,second_category_code,orderTreeType.getType(),AppContext.PAGE_SIZE+"",pageProduct+"",
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    KEY_REFRESH);
        }
    }

    private void getDataProduct() {
        if(loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYBYCATEGORYCODE,first_category_code,second_category_code,orderTreeType.getType(),AppContext.PAGE_SIZE+"",pageProduct+"",
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    KEY_MORE);
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
}
