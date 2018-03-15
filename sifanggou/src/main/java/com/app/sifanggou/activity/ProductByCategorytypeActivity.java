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
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.CommodityTypeBean;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;
import com.app.sifanggou.bean.NodeBaseBean;
import com.app.sifanggou.bean.OrderTreeType;
import com.app.sifanggou.bean.RecommendCommodityBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityByCategoryCodeResponseBean;
import com.app.sifanggou.net.bean.GetCommodityTypeListResponseBean;
import com.app.sifanggou.net.bean.GetRecommendCommodityResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class ProductByCategorytypeActivity extends BaseActivity {

    @ViewInject(R.id.tv_mount)
    private TextView tvCarMount;
    @ViewInject(R.id.rl_car)
    private RelativeLayout rlCar;
    @ViewInject(R.id.btn_xiadan)
    private Button btnXiaDan;
    @ViewInject(R.id.btn_car)
    private Button btnCar;

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

    public static final String KEY_DATA = "key_ProductByCategorytypeActivity_type";
    private NodeBaseBean nodeBaseBean;
    private List<CommodityTypeBean> commodity_type_list = new ArrayList<CommodityTypeBean>();

    private String first_category_code = "";
    private String second_category_code = "";
    private String third_category_code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        //处理商品分页
        adapterProduct = new CommodityInfoBeanAdapter(ProductByCategorytypeActivity.this,dataListProduct);
        swipeRefreshLayoutProduct.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterViewProduct = LayoutInflater.from(ProductByCategorytypeActivity.this).inflate(R.layout.mode_more, null);
        noMoreTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.no_more);
        loadingTextProduct = (TextView) listViewFooterViewProduct.findViewById(R.id.load_more);
        emptyViewViewProduct = LayoutInflater.from(ProductByCategorytypeActivity.this).inflate(R.layout.mode_empty, null);

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
        nodeBaseBean = (NodeBaseBean) getIntent().getSerializableExtra(KEY_DATA);
        if (nodeBaseBean != null) {
            setTitle(nodeBaseBean.getName());
        }
        getData();
    }

    private void getData() {
        pushEventNoProgress(EventCode.HTTP_GETCOMMODITYTYPELIST);
    }

    private void initListener() {
        adapterProduct.setListener(new CommodityInfoBeanAdapter.AddListener() {

            @Override
            public void click(CommodityInfoBean bean) {
                Intent intent = new Intent(ProductByCategorytypeActivity.this,ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.KEY_DATA,bean);
                startActivity(intent);
            }
        });

        rlCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductByCategorytypeActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        btnXiaDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductByCategorytypeActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductByCategorytypeActivity.this,CarActivity.class);
                startActivity(intent);
            }
        });
    }


    private void carAdd(CommodityInfoBean bean) {
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
        if (TextUtils.isEmpty(first_category_code)
                && TextUtils.isEmpty(second_category_code)
                && TextUtils.isEmpty(third_category_code)) {
            return;
        }
        if (nodeBaseBean != null
                && loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            pageProduct = AppContext.PAGE;
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYBYCATEGORYCODE,first_category_code,
                    second_category_code,third_category_code,
                    OrderTreeType.PRICE.getType(),AppContext.PAGE_SIZE+"",pageProduct+"",
                    loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                    KEY_REFRESH);
        }
    }

    private void getDataProduct() {
        if (TextUtils.isEmpty(first_category_code)
                && TextUtils.isEmpty(second_category_code)
                && TextUtils.isEmpty(third_category_code)) {
            return;
        }
        if (nodeBaseBean != null
                && loginBean != null
                && loginBean.getData() != null
                && loginBean.getData().getLogin_info() != null
                && loginBean.getData().getLogin_info().getBusiness_info() != null
                && !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYBYCATEGORYCODE,first_category_code,
                    second_category_code,third_category_code,
                    OrderTreeType.PRICE.getType(),AppContext.PAGE_SIZE+"",pageProduct+"",
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

    private NodeBaseBean getFirstParentNode(NodeBaseBean node) {
        NodeBaseBean parentNodeBean = null;
        for(CommodityTypeBean commodityTypeBean : commodity_type_list) {
            if (node.getParent_category_code() == null) continue;
            if (node.getParent_category_code().equals(commodityTypeBean.getCategory_code())) {
                parentNodeBean = new NodeBaseBean();
                parentNodeBean.setCategory_code(commodityTypeBean.getCategory_code());
                parentNodeBean.setName(commodityTypeBean.getName());
                parentNodeBean.setParent_category_code(commodityTypeBean.getParent_category_code());
                break;
            }
        }
        return parentNodeBean;
    }

    private NodeBaseBean getSecondParentNode(NodeBaseBean node) {
        NodeBaseBean parentNodeBean = null;
        for(CommodityTypeBean ctb : commodity_type_list) {
            if (ctb.getChild_node_list() == null) continue;
            for (FirstChildNodeTypeBean firstChildNodeTypeBean : ctb.getChild_node_list()) {
                if (node.getParent_category_code().equals(firstChildNodeTypeBean.getCategory_code())) {
                    parentNodeBean = new NodeBaseBean();
                    parentNodeBean.setCategory_code(firstChildNodeTypeBean.getCategory_code());
                    parentNodeBean.setName(firstChildNodeTypeBean.getName());
                    parentNodeBean.setParent_category_code(firstChildNodeTypeBean.getParent_category_code());
                    break;
                }
            }
        }
        return parentNodeBean;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETCOMMODITYTYPELIST) {
            if (event.isSuccess()){
                GetCommodityTypeListResponseBean bean = (GetCommodityTypeListResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && bean.getData().getCommodity_type_list() != null && bean.getData().getCommodity_type_list().size() > 0) {
                    commodity_type_list.addAll(bean.getData().getCommodity_type_list());
                    if (nodeBaseBean != null) {
                        first_category_code = "";
                        second_category_code = "";
                        third_category_code = "";
                        if ("0".equals(nodeBaseBean.getParent_category_code())) {
                            first_category_code = nodeBaseBean.getCategory_code();
                        } else {
                            NodeBaseBean firstParentNode = getFirstParentNode(nodeBaseBean);
                            if (firstParentNode != null) {
                                first_category_code = firstParentNode.getCategory_code();
                                second_category_code = nodeBaseBean.getCategory_code();
                            } else {
                                NodeBaseBean sencodeNodeBaseBean = getSecondParentNode(nodeBaseBean);
                                if (sencodeNodeBaseBean != null) {
                                    second_category_code = sencodeNodeBaseBean.getCategory_code();
                                    third_category_code = nodeBaseBean.getCategory_code();
                                    NodeBaseBean nbb = getFirstParentNode(sencodeNodeBaseBean);
                                    if (nbb != null) {
                                        first_category_code = nbb.getCategory_code();
                                    }
                                }
                            }

                        }
                    }
                }
                refreshProduct();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
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
                    List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
                    for(CommodityInfoBean cc : bean.getData().getBusiness_commodity_info_list()) {
                        tmpList.add(cc);
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
                    GetBusinessCommodityByCategoryCodeResponseBean bean = (GetBusinessCommodityByCategoryCodeResponseBean) event.getReturnParamAtIndex(0);
                    if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_info_list() == null) {
                        return;
                    }
                    List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
                    for(CommodityInfoBean cc : bean.getData().getBusiness_commodity_info_list()) {
                        tmpList.add(cc);
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
