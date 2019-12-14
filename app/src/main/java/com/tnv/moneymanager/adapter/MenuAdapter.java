package com.tnv.moneymanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.listener.OnItemClickViewRecyclerView;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Context mContext;
    private List<Menu> mData = new ArrayList<>();

    public MenuAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public MenuAdapter(Context mContext, List<Menu> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    private OnItemClickRecyclerView onItemClickRecyclerView;
    private OnItemClickViewRecyclerView onItemClickViewRecyclerView;

    public MenuAdapter(Context mContext, OnItemClickRecyclerView onItemClickRecyclerView) {
        this.mContext = mContext;
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    public void setOnItemClickViewRecyclerView(OnItemClickViewRecyclerView onItemClickViewRecyclerView) {
        this.onItemClickViewRecyclerView = onItemClickViewRecyclerView;
    }

    public void setOnItemClickRecyclerView(OnItemClickRecyclerView onItemClickRecyclerView) {
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Menu item = mData.get(i);
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.imvIcon.post(new Runnable() {
            @Override
            public void run() {
                viewHolder.imvIcon.setImageDrawable(ImageUtils.getIcon(mContext,item.getMenuId()));
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickRecyclerView != null)
                    onItemClickRecyclerView.OnItemClick(viewHolder, i);
            }
        });
        viewHolder.viewChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickViewRecyclerView != null)
                    onItemClickViewRecyclerView.OnItemClick(viewHolder, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imvIcon;
        LinearLayout viewChoose;
        public ViewHolder(View view) {
            super(view);
            viewChoose = view.findViewById(R.id.view_choose);
            tvTitle = view.findViewById(R.id.tv_title);
            imvIcon = view.findViewById(R.id.imv_icon);
        }
    }

}
