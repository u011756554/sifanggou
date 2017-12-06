package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.BusinessInfoBeanAdapter;
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.SearchType;
import com.app.sifanggou.bean.SearchTypeBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityInfoResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.net.bean.SearchBusinessCommodityOnNameResponseBean;
import com.app.sifanggou.net.bean.SerachBusinessOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SearchActivity extends BaseActivity {

    @ViewInject(R.id.edt_search)
    private EditText editSearch;
    @ViewInject(R.id.tv_search)
    private TextView tvSearch;
    @ViewInject(R.id.iv_delete)
    private ImageView ivDelete;
    @ViewInject(R.id.tv_type)
    private TextView tvType;
    @ViewInject(R.id.ll_city)
    private LinearLayout llCity;
    @ViewInject(R.id.rl_product)
    private RelativeLayout rlProduct;
    @ViewInject(R.id.rl_market)
    private RelativeLayout rlMarket;

    private LoginResponseBean loginBean;
    private SearchType searchType = SearchType.PRODUCT;
    private PopupWindow typeWindow;
    private TextView tvProduct;
    private TextView tvMarket;

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
    private CommodityInfoBeanAdapter adapterProduct;
    private List<CommodityInfoBean> dataListProduct = new ArrayList<CommodityInfoBean>();

    //商家列表数据显示
    @ViewInject(R.id.srl_market)
    private SwipeRefreshLayout swipeRefreshLayoutMarket;
    @ViewInject(R.id.ll_market)
    private ListView listViewMarket;
    private View listViewFooterViewMarket;
    private View emptyViewViewMarket;
    private TextView noMoreTextMarket;
    private TextView loadingTextMarket;
    private boolean isLoadingMarket = false;
    private boolean isOverMarket = false;
    private boolean isRefreshingMarket = false;
    private boolean isFirstMarket = true;
    private int pageSizeMarket = AppContext.PAGE_SIZE;
    private int pageMarket = AppContext.PAGE;
    private int headHeightMarket;
    private BusinessInfoBeanAdapter adapterMarket;
    private List<BusinessInfoBean> dataListMarket = new ArrayList<BusinessInfoBean>();

    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_MORE = "more";

    private int carCount = 0;

    public static final String KEY_TYPE = "key_SearchActivity_type";
    public static final String VULE_MARKET = "markert";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initData();
        refreshSearchType();
    }

    private void initView() {
        //处理商品分页
        adapterProduct = new CommodityInfoBeanAdapter(SearchActivity.this,dataListProduct);
        swipeRefreshLayoutProduct.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewProduct = LayoutInflater.from(SearchActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.no_more);
        loadingTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.load_more);
        emptyViewViewProduct = LayoutInflater.from(SearchActivity.this).inflate(R.layout.mode_empty, null);

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

        //处理商家分页
        adapterMarket = new BusinessInfoBeanAdapter(SearchActivity.this,dataListMarket);
        swipeRefreshLayoutMarket.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewMarket = LayoutInflater.from(SearchActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextMarket = (TextView) listViewFooterViewMarket.findViewById(R.id.no_more);
        loadingTextMarket = (TextView) listViewFooterViewMarket.findViewById(R.id.load_more);
        emptyViewViewMarket = LayoutInflater.from(SearchActivity.this).inflate(R.layout.mode_empty, null);

        listViewMarket.addHeaderView(emptyViewViewMarket);
        listViewMarket.addFooterView(listViewFooterViewMarket);
        listViewFooterViewMarket.setVisibility(View.GONE);
        emptyViewViewMarket.setVisibility(View.GONE);

        listViewMarket.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listViewMarket.getLastVisiblePosition() == listViewMarket.getCount() - 1)
                {
                    if(dataListMarket.size() != 0)
                        loadMarket();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

            }
        });

        listViewMarket.setAdapter(adapterMarket);

        swipeRefreshLayoutMarket.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMarket();
            }
        });
    }

    private void refreshSearchType() {
        if (searchType == SearchType.PRODUCT) {
            tvType.setText("商品");
            editSearch.setText("");
            editSearch.setHint("商品名称");
            rlProduct.setVisibility(View.VISIBLE);
            rlMarket.setVisibility(View.GONE);
            adapterProduct.clear();
            refreshProduct();
        } else {
            tvType.setText("商家");
            editSearch.setText("");
            editSearch.setHint("商家名称");
            rlProduct.setVisibility(View.GONE);
            rlMarket.setVisibility(View.VISIBLE);
            adapterMarket.clear();
            refreshMarket();
        }
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        String type = getIntent().getStringExtra(KEY_TYPE);
        if (VULE_MARKET.equals(type)) {
            searchType = SearchType.MARKET;
        }
    }

    private void initListener() {
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
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
                if (searchType == SearchType.PRODUCT) {
                    refreshProduct();
                } else {
                    refreshMarket();
                }

            }
        });

        llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeWindow == null) {
                    initPopupWindow();
                }
                typeWindow.showAsDropDown(llCity);
            }
        });

        adapterProduct.setListener(new CommodityInfoBeanAdapter.AddListener() {
            @Override
            public void add(CommodityInfoBean bean) {
                carAdd(bean);
            }
        });

        rlCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        listViewMarket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == dataListMarket.size() + 1 || position == 0) {
                    return;
                }
                Intent intent = new Intent(SearchActivity.this, DianPuDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPopupWindow() {
        typeWindow = new PopupWindow(this);
        typeWindow.setWidth(CommonUtils.dip2px(this,80));
        typeWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_type, null);
        tvProduct = (TextView) contentView.findViewById(R.id.tv_product);
        tvMarket = (TextView) contentView.findViewById(R.id.tv_market);
        typeWindow.setContentView(contentView);
        typeWindow.setOutsideTouchable(false);
        typeWindow.setFocusable(true);

        tvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = SearchType.PRODUCT;
                typeWindow.dismiss();
                refreshSearchType();
            }
        });

        tvMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = SearchType.MARKET;
                typeWindow.dismiss();
                refreshSearchType();
            }
        });
    }

    private void searchBusiness(String search,int item_num,int page_no,String tag) {
        pushEventNoProgress(EventCode.HTTP_SERACHBUSINESSONNAME,search,item_num+"",page_no+"",tag);
    }

    private void searchCommodity(String search,int item_num,int page_no,String tag) {
        pushEventNoProgress(EventCode.HTTP_SEARCHBUSINESSCOMMODITYONNAME,search,item_num+"",page_no+"",tag);
    }

    private void car() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEvent(EventCode.HTTP_UPDATEBUSINESSSHOPPINGCARTCOMMODITYNUM,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),20+"",1+"");
        }
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
        if (event.getEventCode() == EventCode.HTTP_SERACHBUSINESSONNAME) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    SerachBusinessOnNameResponseBean bean = (SerachBusinessOnNameResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_info_list() == null) {
                        return;
                    }
                    List<BusinessInfoBean> tmpList = bean.getData().getBusiness_info_list();
                    if (tmpList != null) {
                        pageMarket++;
                        dataListMarket.clear();
                        adapterMarket.updateData(tmpList);
                        if (dataListMarket.size() <= 0) {
                            setNoDataMarket(true);
                        } else {
                            setNoDataMarket(false);
                            if (tmpList.size() < pageSizeMarket) {
                                isOverMarket = true;
                                listViewFooterViewMarket.setVisibility(View.VISIBLE);
                                noMoreTextMarket.setVisibility(View.VISIBLE);
                                loadingTextMarket.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (dataListMarket.size() <= 0) {
                            setNoDataMarket(true);
                        } else {
                            setNoDataMarket(false);
                        }
                    }
                    swipeRefreshLayoutMarket.setRefreshing(false);
                    adapterMarket.notifyDataSetChanged();
                    isRefreshingMarket = false;
                } else {
                    SerachBusinessOnNameResponseBean bean = (SerachBusinessOnNameResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_info_list() == null) {
                        return;
                    }
                    List<BusinessInfoBean> tmpList = bean.getData().getBusiness_info_list();
                    isLoadingMarket = false;
                    if (tmpList != null) {
                        pageMarket++;
                        adapterMarket.addAll(tmpList);
                        if (dataListMarket.size() <= 0) {
                            setNoDataMarket(true);
                        } else {
                            setNoDataMarket(false);
                            if (tmpList.size() < pageSizeMarket) {
                                isOverMarket = true;
                                listViewFooterViewMarket.setVisibility(View.VISIBLE);
                                noMoreTextMarket.setVisibility(View.VISIBLE);
                                loadingTextMarket.setVisibility(View.GONE);
                            } else {
                                listViewFooterViewMarket.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (dataListMarket.size() <= 0) {
                            setNoDataMarket(true);
                        } else {
                            setNoDataMarket(false);
                        }
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_SEARCHBUSINESSCOMMODITYONNAME) {
            if (event.isSuccess()) {
                String type = (String) event.getReturnParamAtIndex(1);
                if (type.equals(KEY_REFRESH)) {
                    SearchBusinessCommodityOnNameResponseBean bean = (SearchBusinessCommodityOnNameResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getCommodity_info_list() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getCommodity_info_list();
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
                    SearchBusinessCommodityOnNameResponseBean bean = (SearchBusinessCommodityOnNameResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getCommodity_info_list() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = bean.getData().getCommodity_info_list();
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
        searchCommodity(editSearch.getText().toString(),AppContext.PAGE_SIZE,pageProduct,KEY_REFRESH);
    }

    private void getDataProduct() {
        searchCommodity(editSearch.getText().toString(),AppContext.PAGE_SIZE,pageProduct,KEY_MORE);
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

    //商家UI
    private void loadMarket() {
        if (!isRefreshingMarket && !isLoadingMarket && !isOverMarket) {
            isLoadingMarket = true;
            listViewFooterViewMarket.setVisibility(View.VISIBLE);
            loadingTextMarket.setVisibility(View.VISIBLE);
            noMoreTextMarket.setVisibility(View.GONE);
            isOverMarket = false;
            getDataMarket();
        }
    }

    private void refreshMarket() {
        if (!isLoadingMarket && !isRefreshingMarket) {
            listViewFooterViewMarket.setVisibility(View.GONE);
            isRefreshingMarket = true;
            isOverMarket = false;
            refreshDataMarket();
        }
    }

    private void refreshDataMarket() {
        pageMarket = AppContext.PAGE;
        searchBusiness(editSearch.getText().toString(),AppContext.PAGE_SIZE,pageMarket,KEY_REFRESH);
    }

    private void getDataMarket() {
        searchBusiness(editSearch.getText().toString(),AppContext.PAGE_SIZE,pageMarket,KEY_MORE);
    }

    public void setNoDataMarket(boolean show) {
        if (isFirstMarket) {
            CommonUtils.measureView(emptyViewViewMarket);
            headHeightMarket = emptyViewViewMarket.getMeasuredHeight();
            isFirstMarket = false;

        }
        showEmptyMarket(show);
    }

    private void showEmptyMarket(boolean show) {
        if (show) {
            emptyViewViewMarket.setVisibility(View.VISIBLE);
            emptyViewViewMarket.setPadding(0, 0, 0, 0);
            listViewFooterViewMarket.setVisibility(View.GONE);
            emptyViewViewMarket.setClickable(false);
        } else {
            emptyViewViewMarket.setVisibility(View.GONE);
            emptyViewViewMarket.setPadding(0, - headHeightMarket, 0, 0);
            emptyViewViewMarket.setClickable(false);
        }
    }
}
