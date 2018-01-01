package com.app.sifanggou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.activity.CategoryProductActivity;
import com.app.sifanggou.activity.SearchActivity;
import com.app.sifanggou.adapter.CategorySecondTypeAdapter;
import com.app.sifanggou.adapter.CategoryTypeAdapter;
import com.app.sifanggou.bean.CommodityTypeBean;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetCommodityTypeListResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.view.MyListView;
import com.app.sifanggou.view.tree.TreeUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment {
	@ViewInject(R.id.lv_type_one)
	private ListView lvTypeOne;
	@ViewInject(R.id.lv_type_two)
	private ListView lvTypeTwo;
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	@ViewInject(R.id.right_layout)
	private RelativeLayout rlRight;

	private List<CommodityTypeBean> commodity_type_list = new ArrayList<CommodityTypeBean>();
    private CategoryTypeAdapter adapter;
	private CategorySecondTypeAdapter secondTypeAdapter;
	private List<FirstChildNodeTypeBean> child_node_list = new ArrayList<FirstChildNodeTypeBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_time);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		getData();
	}

	private void initView() {
		tvTitle.setText("四方购分类");
		rlRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SearchActivity.class);
				startActivity(intent);
			}
		});
        adapter = new CategoryTypeAdapter(getActivity(),commodity_type_list);
		adapter.setListener(new CategoryTypeAdapter.CheckListener() {
			@Override
			public void check(int position) {
				if (commodity_type_list.get(position).getChild_node_list() != null) {
					child_node_list.clear();
					child_node_list.addAll(commodity_type_list.get(position).getChild_node_list());
					secondTypeAdapter.notifyDataSetChanged();
				}
			}
		});
        lvTypeOne.setAdapter(adapter);

        lvTypeOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setDefSelect(position);

                for (int i = 0 ; i < commodity_type_list.size() ; i++) {
                    if (i == position) {
                        commodity_type_list.get(i).setSelected(true);
                    } else {
                        commodity_type_list.get(i).setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


		secondTypeAdapter = new CategorySecondTypeAdapter(getActivity(),child_node_list);
		secondTypeAdapter.setListener(new CategorySecondTypeAdapter.SecondListener() {
			@Override
			public void check(FirstChildNodeTypeBean bean) {
				Intent intent = new Intent(getActivity(), CategoryProductActivity.class);
				intent.putExtra(CategoryProductActivity.KEY_FIRSTTYPE,bean.getParent_category_code());
				intent.putExtra(CategoryProductActivity.KEY_SECOND,bean.getCategory_code());
				getActivity().startActivity(intent);
			}
		});
		lvTypeTwo.setAdapter(secondTypeAdapter);
	}

	private void getData() {
		pushEventNoProgress(EventCode.HTTP_GETCOMMODITYTYPELIST);
	}


	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_GETCOMMODITYTYPELIST) {
			if (event.isSuccess()){
				GetCommodityTypeListResponseBean bean = (GetCommodityTypeListResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null && bean.getData() != null && bean.getData().getCommodity_type_list() != null && bean.getData().getCommodity_type_list().size() > 0) {
					commodity_type_list.addAll(bean.getData().getCommodity_type_list());
					adapter.notifyDataSetChanged();
					adapter.setDefSelect(1);
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
	}
}
