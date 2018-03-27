package com.app.sifanggou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.activity.DianPuDetailActivity;
import com.app.sifanggou.activity.ProductDetailActivity;
import com.app.sifanggou.activity.SearchActivity;
import com.app.sifanggou.adapter.BusinessInfoBeanAdapter;
import com.app.sifanggou.adapter.CommodityInfoBeanAdapter;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.SearchBusinessType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.net.bean.SerachBusinessOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class DianPuFragment extends BaseFragment {
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	@ViewInject(R.id.right_layout)
	private RelativeLayout rlRight;
	@ViewInject(R.id.right_image)
	private ImageView ivRight;

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

	private LoginResponseBean loginBean;

	private SearchBusinessType searchBusinessType = SearchBusinessType.NAME;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_dianpu);
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
		tvTitle.setText("店铺");
		rlRight.setVisibility(View.VISIBLE);
		ivRight.setImageResource(R.drawable.icon_search_dianpu);

		adapterMarket = new BusinessInfoBeanAdapter(getActivity(),dataListMarket);
		swipeRefreshLayoutMarket.setColorSchemeResources(R.color.color_banner,R.color.color_banner,R.color.color_banner,R.color.color_banner);
		listViewFooterViewMarket = LayoutInflater.from(getActivity()).inflate(R.layout.mode_more, null);
		noMoreTextMarket = (TextView) listViewFooterViewMarket.findViewById(R.id.no_more);
		loadingTextMarket = (TextView) listViewFooterViewMarket.findViewById(R.id.load_more);
		emptyViewViewMarket = LayoutInflater.from(getActivity()).inflate(R.layout.mode_empty, null);

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

	private void initData() {
		loginBean = PreManager.get(getActivity().getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
		refreshDataMarket();
	}

	private void initListener() {
		rlRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SearchActivity.class);
				intent.putExtra(SearchActivity.KEY_TYPE,SearchActivity.VULE_MARKET);
				startActivity(intent);
			}
		});

		adapterMarket.setListener(new BusinessInfoBeanAdapter.UpdateListener() {
			@Override
			public void click(BusinessInfoBean bean) {
				Intent intent = new Intent(getActivity(),DianPuDetailActivity.class);
				intent.putExtra(DianPuDetailActivity.KEY_DATA,bean);
				startActivity(intent);
			}
		});
	}

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
		searchBusiness("",AppContext.PAGE_SIZE,pageMarket,KEY_REFRESH);
	}

	private void getDataMarket() {
		searchBusiness("",AppContext.PAGE_SIZE,pageMarket,KEY_MORE);
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

	private void searchBusiness(String search,int item_num,int page_no,String tag) {
		if(loginBean != null && loginBean.getData() != null
				&& loginBean.getData().getLogin_info() != null
				&& loginBean.getData().getLogin_info().getBusiness_info() != null
				&& !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
			pushEventNoProgress(EventCode.HTTP_SERACHBUSINESSONNAME,
					loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
					search,
					item_num+"",page_no+"",searchBusinessType.getType(),tag);
		}
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
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
	}
}
