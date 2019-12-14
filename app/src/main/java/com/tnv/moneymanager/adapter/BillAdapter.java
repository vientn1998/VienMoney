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
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.utils.StringUtils;
import com.tnv.moneymanager.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context mContext;
    private List<Bill> mData = new ArrayList<>();

    public BillAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BillAdapter(Context mContext, List<Bill> mData) {
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
                .inflate(R.layout.item_bill, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Bill item = mData.get(i);
        viewHolder.tvDayNumber.setText(StringUtils.getDayNumberFromDate(item.getDate().toString()));
        viewHolder.tvDay.setText(StringUtils.getDayFromDate(item.getDate().toString()));
        viewHolder.tvDate.setText(StringUtils.getDateFromDate(item.getDate().toString()));
        viewHolder.tvTotal.setText(StringUtils.formatPrice(item.getMoney()));
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        BillItemAdapter adapter = null;
        if (item.getList() != null) {
            adapter = new BillItemAdapter(mContext, item.getList());
            viewHolder.recyclerView.setAdapter(adapter);
            viewHolder.recyclerView.setNestedScrollingEnabled(true);
            adapter.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
                @Override
                public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
                    if (onItemMenuClickRecyclerView != null)
                        onItemMenuClickRecyclerView.OnItemClick(viewHolder, item.getList().get(position).getBid());
                }
            });
        }

//        final BillItemAdapter finalAdapter = adapter;
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemMenuClickRecyclerView != null)
//                    if(finalAdapter != null){
//                        finalAdapter.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
//                            @Override
//                            public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
//                                onItemMenuClickRecyclerView.OnItemClick(viewHolder,item.getList().get(position).getBid());
//                            }
//                        });
//                    }
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayNumber, tvDay, tvDate, tvTotal;
        RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.rc_list_data);
            tvDayNumber = view.findViewById(R.id.tv_day_number);
            tvDay = view.findViewById(R.id.tv_day);
            tvDate = view.findViewById(R.id.tv_date);
            tvTotal = view.findViewById(R.id.tv_total);
        }
    }

}
