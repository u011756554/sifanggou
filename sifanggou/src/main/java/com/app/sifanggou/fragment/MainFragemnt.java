package com.app.sifanggou.fragment;

import java.util.ArrayList;
import java.util.List;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.activity.ChatListActivity;
import com.app.sifanggou.activity.CustomScanActivity;
import com.app.sifanggou.activity.DianPuDetailActivity;
import com.app.sifanggou.activity.JiXuChuCangActivity;
import com.app.sifanggou.activity.JiXuProductListActivity;
import com.app.sifanggou.activity.MainActivity;
import com.app.sifanggou.activity.ProductDetailActivity;
import com.app.sifanggou.activity.ProductPicActivity;
import com.app.sifanggou.activity.RecommondProductActivity;
import com.app.sifanggou.activity.SearchActivity;
import com.app.sifanggou.activity.ShangPinGuanLiActivity;
import com.app.sifanggou.activity.ShouCangActivity;
import com.app.sifanggou.activity.UrlWebClientActivity;
import com.app.sifanggou.adapter.DianPuCollectGridAdapter;
import com.app.sifanggou.adapter.GuangGaoPagerAdapter;
import com.app.sifanggou.adapter.HotAdapter;
import com.app.sifanggou.adapter.ProductCollectGridAdapter;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.BusinessPartnerBean;
import com.app.sifanggou.bean.CommodityCollectBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.DiPaiBean;
import com.app.sifanggou.bean.RecommendCommodityBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessCommodityCollectResponseBean;
import com.app.sifanggou.net.bean.GetBusinessPartnerResponseBean;
import com.app.sifanggou.net.bean.GetFirstPageAdResponseBean;
import com.app.sifanggou.net.bean.GetRecommendCommodityResponseBean;
import com.app.sifanggou.net.bean.GuangGaoBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.MyGridView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainFragemnt extends BaseFragment {
	@ViewInject(R.id.ll_jixuchucang)
	private LinearLayout llJiXu;
	@ViewInject(R.id.ll_product_center)
	private LinearLayout llProductCenter;
	@ViewInject(R.id.ll_myorder)
	private LinearLayout llMyOrder;
	@ViewInject(R.id.rl_search)
	private RelativeLayout rlSearch;
	@ViewInject(R.id.iv_msg)
	private ImageView ivMsg;

	//收藏商品
	private LoginResponseBean loginBean;
	private ProductCollectGridAdapter productCollectGridAdapter;
	private List<CommodityInfoBean> commodityInfoBeanList = new ArrayList<CommodityInfoBean>();
	@ViewInject(R.id.gv_guanzhu_product)
	private MyGridView gvGuanZhuProduct;
	@ViewInject(R.id.rl_guanzhu_product)
	private RelativeLayout rlGuanZhuProduct;
	//收藏商家
	private DianPuCollectGridAdapter dianPuCollectGridAdapter;
	private List<BusinessInfoBean> businessInfoBeanList = new ArrayList<BusinessInfoBean>();
	@ViewInject(R.id.gv_guanzhu_dianpu)
	private MyGridView gvGuanZhuDianPu;
	@ViewInject(R.id.rl_guanzhu_dianpu)
	private RelativeLayout rlGuanZhuDianPu;
	//推荐商品
	private ProductCollectGridAdapter recommandGridAdapter;
	private List<CommodityInfoBean> recommandCommodityInfoBeanList = new ArrayList<CommodityInfoBean>();
	@ViewInject(R.id.rl_commandproduct)
	private RelativeLayout rlCommandProduct;
	@ViewInject(R.id.gv_commandproduct)
	private MyGridView gvCommnadProduct;

	private ViewPager mViewPager;
	private GuangGaoPagerAdapter adapter;
	private List<View> viewList = new ArrayList<View>();
	private List<GuangGaoBean> list = new ArrayList<GuangGaoBean>();
	
	private LinearLayout dotLayout;
	private ImageView[] dotViews;
	private int autoIndex = 0; // 自动轮询时自增坐标，能确定导航圆点的位置
	private int imgsize = 0;
	private boolean isLoop = false;	
	
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// 要做的事情
			if (mViewPager.getChildCount() > 0) {
				handler.postDelayed(this, AppContext.GUANGGAOTIME);
				autoIndex++;
				mViewPager.setCurrentItem(autoIndex % imgsize, true);
			}
		}
	};
	
	public void startLoopViewPager() {
		if (!isLoop && mViewPager != null) {
			handler.postDelayed(runnable, AppContext.GUANGGAOTIME);// 每两秒执行一次runnable.
			isLoop = true;
		}

	}
	
	public void stopLoopViewPager() {
		if (isLoop && mViewPager != null) {
			handler.removeCallbacks(runnable);
			isLoop = false;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView();
		initListener();
		initData();
		startLoopViewPager();
		mViewPager.setCurrentItem(0);
		super.onActivityCreated(savedInstanceState);
	}

	private void initData() {
		loginBean = PreManager.get(getActivity().getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
		getCollectProduct();
		getCollectDianPu();
		getRecommandProduct();
		getAd();
	}
	
	private void initView() {
		mViewPager = (ViewPager) contentView.findViewById(R.id.viewpager_guanggao);
		dotLayout = (LinearLayout) contentView.findViewById(R.id.ly_dot);
		adapter = new GuangGaoPagerAdapter(viewList);
		mViewPager.setAdapter(adapter);

		productCollectGridAdapter = new ProductCollectGridAdapter(getActivity(),commodityInfoBeanList);
		gvGuanZhuProduct.setAdapter(productCollectGridAdapter);

		dianPuCollectGridAdapter = new DianPuCollectGridAdapter(getActivity(),businessInfoBeanList);
		gvGuanZhuDianPu.setAdapter(dianPuCollectGridAdapter);

		recommandGridAdapter = new ProductCollectGridAdapter(getActivity(),recommandCommodityInfoBeanList);
		gvCommnadProduct.setAdapter(recommandGridAdapter);
	}	
	
	@SuppressWarnings("deprecation")
	private void initListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				autoIndex = arg0;
				for (int i = 0; i < dotViews.length; i++) {
					if (i == arg0) {
						dotViews[i].setSelected(true);
					} else {
						dotViews[i].setSelected(false);
					}
				}
			}
		});
		
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					stopLoopViewPager();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					startLoopViewPager();
				default:
					break;
				}
				return false;
			}
		});

		llJiXu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), JiXuProductListActivity.class);
				startActivity(intent);
			}
		});

		llProductCenter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShangPinGuanLiActivity.class);
				startActivity(intent);
			}
		});

		rlSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});

		ivMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), ChatListActivity.class);
//				startActivity(intent);
				new IntentIntegrator(getActivity())
						.setOrientationLocked(true)
                		.setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
						.initiateScan(); // 初始化扫描
			}
		});

		gvGuanZhuDianPu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), DianPuDetailActivity.class);
				intent.putExtra(DianPuDetailActivity.KEY_DATA,businessInfoBeanList.get(position));
				startActivity(intent);
			}
		});

		gvGuanZhuProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
				intent.putExtra(ProductDetailActivity.KEY_DATA,commodityInfoBeanList.get(position));
				startActivity(intent);
			}
		});

		gvCommnadProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
				intent.putExtra(ProductDetailActivity.KEY_DATA,recommandCommodityInfoBeanList.get(position));
				startActivity(intent);
			}
		});
		rlGuanZhuDianPu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ShouCangActivity.class);
				startActivity(intent);
			}
		});

		rlGuanZhuProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ShouCangActivity.class);
				startActivity(intent);
			}
		});

		rlCommandProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),RecommondProductActivity.class);
				startActivity(intent);
			}
		});

		llMyOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)(getActivity())).pageChanged(4);
			}
		});
	}

	private void getAd() {
		pushEventNoProgress(EventCode.HTTP_GETFIRSTPAGEAD);
	}

	private void getCollectProduct() {
		if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
			pushEventNoProgress(EventCode.HTTP_GETBUSINESSCOMMODITYCOLLECT,
					loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
					AppContext.PAGE_SIZE+"",AppContext.PAGE+"","refresh");
		}
	}

	private void getCollectDianPu() {
		if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
			pushEventNoProgress(EventCode.HTTP_GETBUSINESSPARTNER,
					loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
					AppContext.PAGE_SIZE+"",AppContext.PAGE+"","refresh");
		}
	}

	private void getRecommandProduct() {
		pushEventNoProgress(EventCode.HTTP_GETRECOMMENDCOMMODITY,AppContext.PAGE_SIZE+"",AppContext.PAGE+"","refresh");
	}

	private  void refreshAd(List<GuangGaoBean> adList) {
		if (adList == null) {
			return;
		}
		list.clear();
		list.addAll(adList);

		imgsize = list.size();
		initDots(dotLayout, list);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		viewList.clear();
		for(int i=0; i<list.size() ; i++) {
			final GuangGaoBean bean = list.get(i);
			ImageView iv = new ImageView(getActivity());
			iv.setLayoutParams(param);
			iv.setScaleType(ScaleType.CENTER_CROP);
			ImageLoaderUtil.displayWithCache(bean.getAd_pic_url(), iv);
			viewList.add(iv);
			iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),UrlWebClientActivity.class);
					intent.putExtra(UrlWebClientActivity.KEY_TITILE, bean.getAd_txt());
					intent.putExtra(UrlWebClientActivity.KEY_URL, bean.getAd_pic_url());
					getActivity().startActivity(intent);
				}
			});
		}
		adapter.notifyDataSetChanged();
	}
	
	private void initDots(LinearLayout layout,List<GuangGaoBean> list) {
		if (list.size() > 0) {
			LayoutParams mParams = new LayoutParams(CommonUtils.dip2px(getActivity(), 5), CommonUtils.dip2px(getActivity(), 5));
			mParams.setMargins(CommonUtils.dip2px(getActivity(), 3), 0, CommonUtils.dip2px(getActivity(), 3), CommonUtils.dip2px(getActivity(), 3));// 设置小圆点左右之间的间隔

			dotViews = new ImageView[list.size()];
			layout.removeAllViews();

			for (int i = 0; i < list.size(); i++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setLayoutParams(mParams);
				imageView.setImageResource(R.drawable.icon_set_white);
				if (i == 0) {
					imageView.setSelected(true);// 默认启动时，选中第一个小圆点
				} else {
					imageView.setSelected(false);
				}
				dotViews[i] = imageView;// 得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
				layout.addView(imageView);// 添加到布局里面显示
			}
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (handler != null && runnable != null) {
			handler.removeCallbacks(runnable);
		}
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_GETRECOMMENDCOMMODITY) {
			if (event.isSuccess()) {
				GetRecommendCommodityResponseBean bean = (GetRecommendCommodityResponseBean) event.getReturnParamAtIndex(0);
				if (bean == null || bean.getData() == null
						|| bean.getData().getRecommend_commodity() == null) {
					return;
				}
				List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
				for(RecommendCommodityBean cc : bean.getData().getRecommend_commodity()) {
					tmpList.add(cc.getCommodity_info());
				}
				if (tmpList.size() >= 6) {
					tmpList = tmpList.subList(0,6);
				}
				recommandCommodityInfoBeanList.clear();
				recommandCommodityInfoBeanList.addAll(tmpList);
				recommandGridAdapter.notifyDataSetChanged();
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
		if (event.getEventCode() == EventCode.HTTP_GETFIRSTPAGEAD) {
			if (event.isSuccess()) {
				GetFirstPageAdResponseBean bean = (GetFirstPageAdResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null && bean.getData() != null) {
					refreshAd(bean.getData().getFirst_page_ad());
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}

		if(event.getEventCode() == EventCode.HTTP_GETBUSINESSPARTNER) {
			if (event.isSuccess()) {
				GetBusinessPartnerResponseBean bean = (GetBusinessPartnerResponseBean) event.getReturnParamAtIndex(0);
				if (bean == null || bean.getData().getBusiness_partner() == null) {
					return;
				}
				List<BusinessInfoBean> tmpList = new ArrayList<BusinessInfoBean>();
				for(BusinessPartnerBean bpb : bean.getData().getBusiness_partner()) {
					tmpList.add(bpb.getPartner_business_info());
				}
				if (tmpList.size() >= 6) {
					tmpList = tmpList.subList(0,6);
				}
				businessInfoBeanList.clear();
				businessInfoBeanList.addAll(tmpList);
				dianPuCollectGridAdapter.notifyDataSetChanged();
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}

		if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCOMMODITYCOLLECT) {
			if (event.isSuccess()) {
				GetBusinessCommodityCollectResponseBean bean = (GetBusinessCommodityCollectResponseBean) event.getReturnParamAtIndex(0);
				if (bean == null || bean.getData() == null || bean.getData().getBusiness_commodity_collect_list() == null) {
					return;
				}
				List<CommodityInfoBean> tmpList = new ArrayList<CommodityInfoBean>();
				for(CommodityCollectBean cc : bean.getData().getBusiness_commodity_collect_list()) {
					tmpList.add(cc.getCommodity_info());
				}
				if (tmpList.size() >= 6) {
					tmpList = tmpList.subList(0,6);
				}
				commodityInfoBeanList.clear();
				commodityInfoBeanList.addAll(tmpList);
				productCollectGridAdapter.notifyDataSetChanged();
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
	}
}
