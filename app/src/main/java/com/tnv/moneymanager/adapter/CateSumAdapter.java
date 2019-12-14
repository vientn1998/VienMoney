package com.tnv.moneymanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.listener.OnItemMenuClickRecyclerView;
import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.KeyValue;
import com.tnv.moneymanager.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CateSumAdapter extends RecyclerView.Adapter<CateSumAdapter.ViewHolder> {
    private Context mContext;
    private List<KeyValue> mData = new ArrayList<>();

    public CateSumAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CateSumAdapter(Context mContext, List<KeyValue> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    private OnItemMenuClickRecyclerView onItemMenuClickRecyclerView;

    public void setOnItemMenuClickRecyclerView(OnItemMenuClickRecyclerView onItemMenuClickRecyclerView) {
        this.onItemMenuClickRecyclerView = onItemMenuClickRecyclerView;
    }


    //private IOnItemClickListener mOnClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cate_sum, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final KeyValue item = mData.get(i);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvMoney.setText(StringUtils.formatPrice(item.getMoney()));
        switch (item.getId()) {
            case 0:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_1));
                break;
            case 1:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_2));
                break;
            case 2:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_3));
                break;
            case 3:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_4));
                break;
            case 4:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_5));
                break;
            case 5:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_6));
                break;
            case 6:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_7));
                break;
            case 7:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_8));
                break;
            case 8:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_9));
                break;
            case 9:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_10));
                break;
            default:
                viewHolder.imvIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dot_icon_circle_1));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMoney;
        ImageView imvIcon;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvMoney = view.findViewById(R.id.tv_money);
            imvIcon = view.findViewById(R.id.imv_icon);
        }
    }

}
