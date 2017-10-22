package com.app.sifanggou.view;

import java.util.ArrayList;
import java.util.List;

import com.app.sifanggou.R;
import com.app.sifanggou.view.BaseDialog.DialogListener;
import com.app.sifanggou.view.wheelview.TimeRange;
import com.app.sifanggou.view.wheelview.WheelView;
import com.app.sifanggou.view.wheelview.WheelView.OnItemSelectedListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MarketSelectDialog extends BaseDialog {

	private TextView tvCancel;
	private TextView tvConfirm;
	private WheelView wvProvince;
	private WheelView wvCity;
	private WheelView wvMarket;
//	private WheelView wvLevel;
	
	private List<String> provinceList = new ArrayList<String>();
	private List<String> cityList = new ArrayList<String>();
//	private List<String> levelList = new ArrayList<String>();
	private List<String> marketList = new ArrayList<String>();
	
	private String selectProvince;
	public MarketSelectDialog(Context context) {
		super(context);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_marketselect);
		initView();
	}

	private void initView() {
		addClickCancel();
		tvCancel = (TextView) findViewById(R.id.tv_cancel);
		tvConfirm = (TextView) findViewById(R.id.tv_confirm);
		wvProvince = (WheelView) findViewById(R.id.province_wv);
		wvCity = (WheelView) findViewById(R.id.city_wv);
//		wvLevel = (WheelView) findViewById(R.id.level_wv);
		wvMarket = (WheelView) findViewById(R.id.market_wv);
		
		tvCancel.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		tvConfirm.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});	
		
		refreshCityView(0);
//		refreshLevelView(0);
		refreshMarketView(0);
		refreshProvinceView(0);
	}
	
	private void refreshMarketView(int index) {
		if (wvMarket != null && marketList != null) {
			wvMarket.setItems(marketList, index);
			wvMarket.setOnItemSelectedListener(new OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(int selectedIndex, String item) {
					Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
					if (marketListener != null) {
						marketListener.selectMarket(item);
					}
				}
			});
			if (marketListener != null && marketList.size() > 0) {
				marketListener.selectMarket(marketList.get(index));
			}
		}
	}
	
//	private void refreshLevelView(int index) {
//		if (wvLevel != null && levelList != null) {
//			wvLevel.setItems(levelList, index);
//			wvLevel.setOnItemSelectedListener(new OnItemSelectedListener() {
//				
//				@Override
//				public void onItemSelected(int selectedIndex, String item) {
//					Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
//				}
//			});
//		}
//	}
	
	private void refreshCityView(int index) {
		if (wvCity != null && cityList != null) {
			wvCity.setItems(cityList, index);
			wvCity.setOnItemSelectedListener(new OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(int selectedIndex, String item) {
					Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
					if (marketListener != null) {
						marketListener.selectCity(item);
					}
				}
			});
		}
	}
	
	private void refreshProvinceView(int index) {
		if (wvProvince != null && provinceList != null && provinceList.size() > 0) {
			wvProvince.setItems(provinceList, index);
			wvProvince.setOnItemSelectedListener(new OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(int selectedIndex, String item) {
					Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
					if (marketListener != null) {
						marketListener.selectProvince(item);
					}
				}
			});
		}
	}
	
	public void setProvince(List<String> provinceList,int index) {
		this.provinceList = provinceList;
		refreshProvinceView(index);
	}
	
	public void setCity(List<String> cityList,int index) {
		this.cityList = cityList;
		refreshCityView(index);
	}
	
//	public void setLevel(List<String> levelList,int index) {
//		this.levelList = levelList;
//		refreshLevelView(index);
//	}
	
	public void setMarket(List<String> marketList,int index) {
		this.marketList = marketList;
		refreshMarketView(index);
	}
	
	private MarketListener marketListener;
	public interface MarketListener {
		void selectProvince(String province);
		void selectCity(String city);
		void selectMarket(String market);
	}
	
	public void setListener(MarketListener listener) {
		this.marketListener = listener;
	}
}
