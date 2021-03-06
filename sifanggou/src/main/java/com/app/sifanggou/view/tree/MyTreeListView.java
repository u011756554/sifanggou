package com.app.sifanggou.view.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.sifanggou.R;

public class MyTreeListView extends ListView {

	ListView treelist = null;
	MyTreeAdapter ta = null;
	private boolean isSingle;
	public MyTreeListView(Context context,boolean isSingle) {
		// TODO Auto-generated constructor stub
		super(context);
		treelist = this;
		this.isSingle = isSingle;
		treelist.setFocusable(false);
		treelist.setBackgroundColor(0xffffff);
		treelist.setFadingEdgeLength(0);
		treelist.setDrawSelectorOnTop(false);
		treelist.setCacheColorHint(0xffffff);
		treelist.setDivider(getResources().getDrawable(R.drawable.divider_list));
		treelist.setDividerHeight(1);
		treelist.setFastScrollEnabled(true);
//		treelist.setScrollBarStyle(View.Scroll);
		treelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("print", "被点击");
				Node node = ((MyTreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
//				if (node != null && mListener != null) {
//					mListener.update(node);
//				}
			}
		});
	}
	
	public void setData(Context context,List<NodeResource> res) {
		initNode(context, initNodRoot(res), true, -1, -1, 1);
	}
	
	public void initNode(Context context, List<Node> root, boolean hasCheckBox,
			int tree_ex_id, int tree_ec_id, int expandLevel) {
		ta = new MyTreeAdapter(context, root);
		// 设置整个树是否显示复选框
		ta.setCheckBox(true);
		ta.setSingle(isSingle);
		ta.setListener(mListener);
		// 设置展开和折叠时图标
		// ta.setCollapseAndExpandIcon(R.drawable.tree_ex, R.drawable.tree_ec);
		int tree_ex_id_ = (tree_ex_id == -1) ? R.drawable.tree_ex : tree_ex_id;
		int tree_ec_id_ = (tree_ec_id == -1) ? R.drawable.tree_ec : tree_ec_id;
		ta.setCollapseAndExpandIcon(tree_ex_id_, tree_ec_id_);
		// 设置默认展开级别
		ta.setExpandLevel(expandLevel);
		this.setAdapter(ta);
	}
	
	public List<Node> initNodRoot(List<NodeResource> res) {
		ArrayList<Node> list = new ArrayList<Node>();
		ArrayList<Node> roots = new ArrayList<Node>();
		Map<String, Node> nodemap = new HashMap<String, Node>();
		for (int i = 0; i < res.size(); i++) {
			NodeResource nr = res.get(i);
			Node n = new Node(nr.getTitle(), nr.getValue(), nr.getParentId(), nr.getCurId(),
					nr.getIconId(),nr.getCompanyId());
			nodemap.put(n.getCurId(), n);// 生成map树
		}
		Set<String> set = nodemap.keySet();
		Collection<Node> collections = nodemap.values();
		Iterator<Node> iterator = collections.iterator();
		while (iterator.hasNext()) {// 添加所有根节点到root中
			Node n = iterator.next();
			if (!set.contains(n.getParentId()))
				roots.add(n);
			list.add(n);
		}
		for (int i = 0; i < list.size(); i++) {
			Node n = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				Node m = list.get(j);
				if (m.getParentId().equals(n.getCurId())) {
					n.addNode(m);
					m.setParent(n);
				} else if (m.getCurId().equals(n.getParentId())) {
					m.addNode(n);
					n.setParent(m);
				}
			}
		}
		return roots;
	}
	
	private UpdateListener mListener;
	public static interface UpdateListener {
		void update(List<String> idList);
	}
	
	public void setListener(UpdateListener mListener) {
		this.mListener = mListener;
	}
}
