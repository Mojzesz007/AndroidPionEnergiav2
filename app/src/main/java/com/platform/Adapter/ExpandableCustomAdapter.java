package com.platform.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.platform.Model.ChildDataModel;
import com.platform.Model.ParentDataModel;
import com.platform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableCustomAdapter extends BaseExpandableListAdapter {

    private ArrayList<ParentDataModel> headerData;
    private HashMap<ParentDataModel, ArrayList<ChildDataModel>> childData;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public ExpandableCustomAdapter(Context mContext, ArrayList<ParentDataModel> headerData,
                                   HashMap<ParentDataModel, ArrayList<ChildDataModel>> childData) {
        this.mContext = mContext;
        this.headerData = headerData;
        this.childData = childData;
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return this.headerData.size();
    }

    @Override
    public int getChildrenCount(int headPosition) {
        return this.childData.get(this.headerData.get(headPosition)).size();
    }

    @Override
    public Object getGroup(int headPosition) {
        return this.headerData.get(headPosition);
    }

    @Override
    public Object getChild(int headPosition, int childPosition) {
        return this.childData.get(this.headerData.get(headPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int headPosition) {
        return headPosition;
    }

    @Override
    public long getChildId(int headPosition, int childPosition) {
        return this.childData.get(this.headerData.get(headPosition))
                .get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int headPosition, boolean is_expanded, View view, ViewGroup headGroup) {

        ParentDataModel heading = (ParentDataModel) getGroup(headPosition);
        if (view==null){
            view = layoutInflater.inflate(R.layout.list_group,null);
        }

        ImageView parentImg = (ImageView) view.findViewById(R.id.parentImg);

        TextView headerTv = view.findViewById(R.id.listTitle);
        headerTv.setText(heading.getTitle());
        parentImg.setImageResource(heading.getImage());
        return view;
    }

    @Override
    public View getChildView(int headPosition, int childPosition, boolean islastChild, View view, ViewGroup viewGroup) {

        ChildDataModel child = (ChildDataModel) getChild(headPosition, childPosition);
        View view1 = layoutInflater.inflate(R.layout.activity_main, null);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, null);
        }

        TextView childTv = (TextView) view.findViewById(R.id.expandabledListItem);
        ImageView childImg = (ImageView) view.findViewById(R.id.childImg);

        childTv.setText(child.getTitle());
        childImg.setImageResource(child.getImage());
        return view;
    }

    @Override
    public boolean isChildSelectable(int headPosition, int childPosition) {
        return true;
    }
}
