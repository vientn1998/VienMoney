package com.tnv.moneymanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tnv.moneymanager.R;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private Context mContext;
    private List<MenuItem> mData = new ArrayList<>();

    public MenuItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public MenuItemAdapter(Context mContext, List<MenuItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    private OnItemClickRecyclerView onItemClickRecyclerView;

    public MenuItemAdapter(Context mContext, OnItemClickRecyclerView onItemClickRecyclerView) {
        this.mContext = mContext;
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    public void setOnItemClickRecyclerView(OnItemClickRecyclerView onItemClickRecyclerView) {
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    //private IOnItemClickListener mOnClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_menu_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final MenuItem item = mData.get(i);
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.imvIcon.post(new Runnable() {
            @Override
            public void run() {
                viewHolder.imvIcon.setImageDrawable(ImageUtils.getIcon(mContext,item.getImage()));
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickRecyclerView != null)
                    onItemClickRecyclerView.OnItemClick(viewHolder, i);
            }
        });

        if (i == mData.size() - 1)
            viewHolder.viewLine.setVisibility(View.GONE);
        else
            viewHolder.viewLine.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imvIcon;
        View viewLine;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            imvIcon = view.findViewById(R.id.imv_icon);
            viewLine = view.findViewById(R.id.view_line);
        }
    }

}
