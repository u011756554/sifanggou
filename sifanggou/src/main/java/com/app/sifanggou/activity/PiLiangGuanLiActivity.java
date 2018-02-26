package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.ShangPinAdapter;
import com.app.sifanggou.adapter.ShangPinPiLiangAdapter;
import com.app.sifanggou.bean.ChuShouType;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.HuoJiaType;
import com.app.sifanggou.bean.SaleType;
import com.app.sifanggou.bean.ShangJiaXiaJiaType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCanAllocateShelfNumResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCommodityInfoResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.EditPriceDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class PiLiangGuanLiActivity extends BaseActivity {

    @ViewInject(R.id.tv_saleing)
    private TextView tvSaleing;
    @ViewInject(R.id.tv_saled)
    private TextView tvSaled;
    @ViewInject(R.id.ll_putong)
    private LinearLayout llPuTong;
    @ViewInject(R.id.ll_daili)
    private LinearLayout llDaiLi;
    @ViewInject(R.id.tv_putonghuojia)
    private TextView tvPuTongHuoJia;
    @ViewInject(R.id.tv_putonghuojia_line)
    private TextView viewPuTongHuoJia;
    @ViewInject(R.id.tv_dailihuojia)
    private TextView tvDaiLiHuoJia;
    @ViewInject(R.id.tv_dailihuojia_line)
    private TextView viewDaiLiHuoJia;
    @ViewInject(R.id.right_layout)
    private RelativeLayout rlRight;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rlAll;
    @ViewInject(R.id.rl_xiajia)
    private RelativeLayout rlXiaJia;
//    @ViewInject(R.id.rl_updateprice)
//    private RelativeLayout rlUpdatePrice;
    @ViewInject(R.id.rl_jixuchucang)
    private RelativeLayout rlJiXuChuCang;
    @ViewInject(R.id.ll_xiajia)
    private LinearLayout llXiaJia;
    @ViewInject(R.id.ll_chushou)
    private LinearLayout llChuShou;
    @ViewInject(R.id.rl_all_xiajia)
    private RelativeLayout rlAllXiaJia;
    @ViewInject(R.id.rl_shangjia)
    private RelativeLayout rlShangJia;

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
    private ShangPinPiLiangAdapter adapter;

    private LoginResponseBean loginBean;
    private HuoJiaType huoJiaType = HuoJiaType.PUTONG;
    private ChuShouType chushouType = ChuShouType.ONSALE;

    public static final String KEY_CHUSHOUTYPE = "key_PiLiangGuanLiActivity_chushoutype";
    private EditPriceDialog editPriceDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        initView();
        initListener();
        initData();
    }

    private void initIntentData() {
        String type = getIntent().getStringExtra(KEY_CHUSHOUTYPE);
        if (ChuShouType.OFFSALE.getType().equals(type)) {
            chushouType = ChuShouType.OFFSALE;
        } else {
            chushouType = ChuShouType.ONSALE;
        }
    }

    private void initView() {
        addBack(R.id.rl_back);
        //处理分页
        adapter = new ShangPinPiLiangAdapter(PiLiangGuanLiActivity.this,list);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
        listViewFooterView = LayoutInflater.from(PiLiangGuanLiActivity.this).inflate(R.layout.mode_more, null);
        noMoreText = (TextView) listViewFooterView.findViewById(R.id.no_more);
        loadingText = (TextView) listViewFooterView.findViewById(R.id.load_more);
        emptyViewView = LayoutInflater.from(PiLiangGuanLiActivity.this).inflate(R.layout.mode_empty, null);

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

        adapter.setPriceEditListener(new ShangPinPiLiangAdapter.PriceEditListener() {
            @Override
            public void updatePrice(final String commodity_id, final String a_price, final String b_price) {
//                if (editPriceDialog == null) {
//                    editPriceDialog = new EditPriceDialog(PiLiangGuanLiActivity.this);
//                }
//                editPriceDialog.setData(a_price,b_price);
//                editPriceDialog.setUpdatePriceListener(new EditPriceDialog.UpdatePriceListener() {
//                    @Override
//                    public void update(String aPrice, String bPrice) {
//                        if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null) {
//                            return;
//                        }
//                        pushEventBlock(EventCode.HTTP_UPDATECOMMODITYPRICE,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
//                                loginBean.getData().getLogin_info().getBusiness_info().getMobile(),commodity_id, aPrice,bPrice);
//                    }
//                });
//                editPriceDialog.show();
            }

            @Override
            public void updateCommodity(CommodityInfoBean bean) {
                Intent intent = new Intent(PiLiangGuanLiActivity.this,AddProductActivity.class);
                intent.putExtra(AddProductActivity.KEY_INITDATA,bean);
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }});

        //其他
        tvSaleing.setSelected(true);
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        refreshHuoJiaType();
        refreshSaleType();
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

    private void initListener() {
        tvSaleing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chushouType = ChuShouType.ONSALE;
                refreshSaleType();
            }
        });
        tvSaled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chushouType = ChuShouType.OFFSALE;
                refreshSaleType();
            }
        });
        llPuTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.PUTONG;
                refreshHuoJiaType();
            }
        });

        llDaiLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.DAILI;
                refreshHuoJiaType();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiLiangGuanLiActivity.this,AddHuoJiaActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size() + 1 || position == 0) {
                    return;
                }
                if (list.get(position - 1).isSelect()) {
                    list.get(position - 1).setSelect(false);
                } else {
                    list.get(position - 1).setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(CommodityInfoBean bean : list) {
                    bean.setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rlAllXiaJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(CommodityInfoBean bean : list) {
                    bean.setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rlXiaJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null) {
                    return;
                }
                ArrayList<Integer> idList = new ArrayList<Integer>();
                for(CommodityInfoBean bean : list) {
                    if (bean.isSelect()) {
                        idList.add(new Integer(bean.getCommodity_id()));
                    }
                }
                if (idList.size() == 0) {
                    CommonUtils.showToast("请选择商品");
                    return;
                }
                pushEventBlock(EventCode.HTTP_BATCHUPDATECOMMODITYSHELFSTATUS,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                        loginBean.getData().getLogin_info().getBusiness_info().getMobile(),idList, ShangJiaXiaJiaType.XIAJIA.getType());
            }
        });

        rlShangJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null) {
                    return;
                }
                ArrayList<Integer> idList = new ArrayList<Integer>();
                for(CommodityInfoBean bean : list) {
                    if (bean.isSelect()) {
                        idList.add(new Integer(bean.getCommodity_id()));
                    }
                }
                if (idList.size() == 0) {
                    CommonUtils.showToast("请选择商品");
                    return;
                }
                pushEventBlock(EventCode.HTTP_BATCHUPDATECOMMODITYSHELFSTATUS,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
                        loginBean.getData().getLogin_info().getBusiness_info().getMobile(),idList, ShangJiaXiaJiaType.SHANGJIA.getType());
            }
        });

//        rlUpdatePrice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (loginBean == null || loginBean.getData() == null || loginBean.getData().getLogin_info() == null || loginBean.getData().getLogin_info().getBusiness_info() == null) {
//                    return;
//                }
//                List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
//                for(CommodityInfoBean bean : list) {
//                    if (bean.isSelect()) {
//                        tmpList.add(bean);
//                    }
//                }
//                if (tmpList.size() == 0) {
//                    CommonUtils.showToast("请选择商品");
//                    return;
//                }
//                if (tmpList.size() != 1) {
//                    CommonUtils.showToast("最多修改一款商品价格");
//                    return;
//                }
//                CommodityInfoBean bean = tmpList.get(0);
//                pushEventBlock(EventCode.HTTP_UPDATECOMMODITYPRICE,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
//                        loginBean.getData().getLogin_info().getBusiness_info().getMobile(),bean.getCommodity_id(), "111","111");
//            }
//        });

        rlJiXuChuCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiLiangGuanLiActivity.this,JiXuChuCangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void refreshData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(), huoJiaType.getType());
            String saleType = "";
            if (chushouType == ChuShouType.ONSALE && huoJiaType == HuoJiaType.PUTONG) {
                saleType = SaleType.ON_SALE_COMMON.getType();
            } else if(chushouType == ChuShouType.ONSALE && huoJiaType == HuoJiaType.DAILI) {
                saleType = SaleType.ON_SALE_AGENCY.getType();
            } else if(chushouType == ChuShouType.OFFSALE && huoJiaType == HuoJiaType.PUTONG) {
                saleType = SaleType.OFF_SALE_COMMON.getType();
            } else if(chushouType == ChuShouType.OFFSALE && huoJiaType == HuoJiaType.DAILI) {
                saleType = SaleType.OFF_SALE_AGENCY.getType();
            }
            page = 0;
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),saleType,AppContext.ITEM_NUM+"",page + "",KEY_REFRESH);
        }
    }

    private void getData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            String saleType = "";
            if (chushouType == ChuShouType.ONSALE && huoJiaType == HuoJiaType.PUTONG) {
                saleType = SaleType.ON_SALE_COMMON.getType();
            } else if(chushouType == ChuShouType.ONSALE && huoJiaType == HuoJiaType.DAILI) {
                saleType = SaleType.ON_SALE_AGENCY.getType();
            } else if(chushouType == ChuShouType.OFFSALE && huoJiaType == HuoJiaType.PUTONG) {
                saleType = SaleType.OFF_SALE_COMMON.getType();
            } else if(chushouType == ChuShouType.OFFSALE && huoJiaType == HuoJiaType.DAILI) {
                saleType = SaleType.OFF_SALE_AGENCY.getType();
            }
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),saleType,AppContext.ITEM_NUM+"",page + "",KEY_MORE);
        }
    }

    private void refreshHuoJiaType() {
        if (huoJiaType == HuoJiaType.PUTONG) {
            tvPuTongHuoJia.setSelected(true);
            viewPuTongHuoJia.setSelected(true);
            tvDaiLiHuoJia.setSelected(false);
            viewDaiLiHuoJia.setSelected(false);
        } else {
            tvPuTongHuoJia.setSelected(false);
            viewPuTongHuoJia.setSelected(false);
            tvDaiLiHuoJia.setSelected(true);
            viewDaiLiHuoJia.setSelected(true);
        }
        refreshData();
    }

    private void refreshSaleType() {
        if (chushouType == ChuShouType.ONSALE) {
            tvSaleing.setSelected(true);
            tvSaled.setSelected(false);
            llChuShou.setVisibility(View.VISIBLE);
            llXiaJia.setVisibility(View.GONE);
        } else {
            tvSaleing.setSelected(false);
            tvSaled.setSelected(true);
            llChuShou.setVisibility(View.GONE);
            llXiaJia.setVisibility(View.VISIBLE);
        }
        refreshData();
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_UPDATECOMMODITYPRICE) {
            if (event.isSuccess()) {
               CommonUtils.showToast("价格修改成功");
                refresh();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM) {
            if (event.isSuccess()) {
                GetBusinessCanAllocateShelfNumResponseBean bean = (GetBusinessCanAllocateShelfNumResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getShelf_num())) {

                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_BATCHUPDATECOMMODITYSHELFSTATUS) {
            if (event.isSuccess()) {
                String enable = (String) event.getReturnParamAtIndex(1);
                if (enable.equals(ShangJiaXiaJiaType.XIAJIA.getType())) {
                    CommonUtils.showToast("下架成功");
                } else {
                    CommonUtils.showToast("上架成功");
                }
                refresh();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCOMMODITYINFO) {
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
