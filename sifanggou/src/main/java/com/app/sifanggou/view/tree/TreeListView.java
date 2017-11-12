package com.app.sifanggou.view.tree;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

import com.app.sifanggou.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TreeListView extends ListView {
	ListView treelist = null;
	TreeAdapter ta = null;
	private int colorType = 0;//默认为0，默认白色，1为黑色
	
	public TreeListView(Context context, List<NodeResource> res,int type) {
		super(context);
		treelist = this;
		this.colorType = type;
		treelist.setFocusable(false);
		treelist.setBackgroundColor(0xffffff);
		treelist.setFadingEdgeLength(0);
		treelist.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
//		treelist.setDrawSelectorOnTop(false);
//		treelist.setCacheColorHint(0xffffff);
//		treelist.setDivider(getResources().getDrawable(R.drawable.divider_list));
		treelist.setDivider(null);
		treelist.setScrollBarStyle(SCROLLBARS_OUTSIDE_INSET);
		treelist.setPadding(0, 0, 10, 0);
//		treelist.setDividerHeight(1);
//		treelist.setFastScrollEnabled(true);
//		treelist.setScrollBarStyle(NO_ID);
		treelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("print", "被点击");
				Node node = (Node) ((TreeAdapter) parent.getAdapter()).getItem(position);
//				Node node = ((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
				if (node.isLeaf()) {
					if (node != null && mListener != null ) {
						mListener.update(node);
					}
				} else {
					ta.ExpandOrCollapse(position);
				}
			}
		});
		initNode(context, initNodRoot(res), false, -1, -1, 1);
	}

	/**
	 *
	 * 
	 */
	public List<Node> initNodRoot(List<NodeResource> res) {
		ArrayList<Node> list = new ArrayList<Node>();
		ArrayList<Node> roots = new ArrayList<Node>();
		Map<String, Node> nodemap = new HashMap<String, Node>();
		for (int i = 0; i < res.size(); i++) {
			NodeResource nr = res.get(i);
			Node n = new Node(nr.getValue(), nr.getValue(), nr.getParentId(), nr.getCurId(),
					R.drawable.icon_edit,nr.getCurId());
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

	public void initNode(Context context, List<Node> root, boolean hasCheckBox,
			int tree_ex_id, int tree_ec_id, int expandLevel) {
		ta = new TreeAdapter(context, root);
		// 设置整个树是否显示复选框
		ta.setCheckBox(hasCheckBox);
		ta.setColorType(this.colorType);
		// 设置展开和折叠时图标
		// ta.setCollapseAndExpandIcon(R.drawable.tree_ex, R.drawable.tree_ec);
		int tree_ex_id_ = (tree_ex_id == -1) ? R.drawable.tree_ex : tree_ex_id;
		int tree_ec_id_ = (tree_ec_id == -1) ? R.drawable.tree_ec : tree_ec_id;
		ta.setCollapseAndExpandIcon(tree_ex_id_, tree_ec_id_);
		// 设置默认展开级别
		ta.setExpandLevel(expandLevel);
		this.setAdapter(ta);
	}

	/* 返回当前所有选中节点的List数组 */
	public List<Node> get() {
		return ta.getSelectedNode();
	}
	
	private UpdateListener mListener;
	public static interface UpdateListener {
		void update(Node node);
	}
	
	public void setListener(UpdateListener mListener) {
		this.mListener = mListener;
	}

}
