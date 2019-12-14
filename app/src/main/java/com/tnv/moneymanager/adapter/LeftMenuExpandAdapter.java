package com.tnv.moneymanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.model.*;
import com.tnv.moneymanager.model.LeftMenuItem.MenuIds;
import java.util.HashMap;
import java.util.List;

public class LeftMenuExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<LeftMenuItem> listDataHeader;
    private HashMap<LeftMenuItem, List<LeftMenuItem>> listChildData;
    private MenuIds mSelectedItemId;
    private MenuIds mSelectedGroupId;

    public LeftMenuExpandAdapter(Context context, List<LeftMenuItem> listDataHeader,
                                 HashMap<LeftMenuItem, List<LeftMenuItem>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listChildData = listChildData;
    }

    public void setSelectedItemId(MenuIds id) {
        mSelectedItemId = id;
        notifyDataSetChanged();
    }

    public void setSelectedGroupId(MenuIds id) {
        mSelectedGroupId = id;
        notifyDataSetChanged();
    }

    @Override
    public LeftMenuItem getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    R.layout.left_menu_item, parent, false);
            holder = new ViewHolder();
            holder.layoutItem = convertView.findViewById(R.id.layout_item);
            holder.tvItemTitle = convertView
                    .findViewById(R.id.tv_item_title);
            holder.divider = convertView.findViewById(R.id.view_divider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LeftMenuItem item = getChild(groupPosition, childPosition);
        holder.layoutItem.setVisibility(View.VISIBLE);
        holder.tvItemTitle.setText(item.getTitleRes());
        if (mSelectedItemId != item.getId()) {
            holder.layoutItem.setSelected(false);
            holder.tvItemTitle.setTextColor(Color.parseColor("#414141"));
        } else {
            holder.layoutItem.setSelected(true);
            holder.tvItemTitle.setTextColor(Color.parseColor("#B3152B"));
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public LeftMenuItem getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final ViewHeaderHolder viewHeaderHolder;
        LeftMenuItem header = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_left_menu_group, null);
            viewHeaderHolder = new ViewHeaderHolder();
            viewHeaderHolder.tvHeaderTitle = convertView.findViewById(R.id.lblListHeader);
            viewHeaderHolder.imvArrowIcon = convertView.findViewById(R.id.image_left_menu_header);
            viewHeaderHolder.layoutItem = convertView.findViewById(R.id.layout_item);
            viewHeaderHolder.viewUnreal = convertView.findViewById(R.id.view_unreal);
            viewHeaderHolder.divider = convertView.findViewById(R.id.view_divider);
            convertView.setTag(viewHeaderHolder);
        } else {
            viewHeaderHolder = (ViewHeaderHolder) convertView.getTag();
        }
        if (groupPosition == 1) {
            viewHeaderHolder.viewUnreal.setVisibility(View.VISIBLE);
        } else {
            viewHeaderHolder.viewUnreal.setVisibility(View.GONE);
        }
        if (mSelectedGroupId == header.getId()) {
            viewHeaderHolder.divider.setBackgroundColor(Color.parseColor("#B3152B"));
        } else {
            viewHeaderHolder.divider.setBackgroundColor(Color.parseColor("#B9B9B9"));
        }
        viewHeaderHolder.tvHeaderTitle.setText(header.getTitleRes());
        if (getGroup(groupPosition).isSpanable()) {
            viewHeaderHolder.imvArrowIcon.setImageResource(isExpanded ? R.drawable.ic_expand_more : R.drawable.ic_collapse_up);
            if (mSelectedGroupId == header.getId() && !isExpanded) {
                //mSelectedGroupId = null;
            }
        } else {
            viewHeaderHolder.imvArrowIcon.setImageResource(0);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHeaderHolder {
        TextView tvHeaderTitle;
        ImageView imvArrowIcon;
        View layoutItem;
        View viewUnreal;
        View divider;
    }

    private class ViewHolder {
        View layoutItem;
        TextView tvItemTitle;
        View divider;
    }
}

