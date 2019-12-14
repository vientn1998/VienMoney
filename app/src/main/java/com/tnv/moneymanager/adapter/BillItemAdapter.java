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
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.db.MenuItemDao;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.listener.OnItemMenuClickRecyclerView;
import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.utils.StringUtils;
import com.tnv.moneymanager.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Bill> mData = new ArrayList<>();
    private AppDatabase db;
    private MenuDao menuDao;
    private MenuItemDao menuItemDao;
    public BillItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BillItemAdapter(Context mContext, List<Bill> mData) {
        this.mContext = mContext;
        this.mData = mData;
        db = AppDatabase.getDatabase(mContext);
        menuDao = db.menuDao();
        menuItemDao = db.menuItemDao();
    }

    private OnItemClickRecyclerView onItemClickRecyclerView;

    public BillItemAdapter(Context mContext, OnItemClickRecyclerView onItemClickRecyclerView) {
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
                .inflate(R.layout.item_bill_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Bill item = mData.get(i);
        viewHolder.tvNote.setText(item.getDescription().isEmpty() ? "không có" : item.getDescription());
        viewHolder.tvTotal.setText(StringUtils.formatPrice(item.getMoney()));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(item.getItemId() != 0){
                    final MenuItem menuItem = menuItemDao.getMenuItem(item.getItemId());
                    if(menuItem != null){
                        viewHolder.tvTitle.setText(menuItem.getName());
                        viewHolder.imvIcon.post(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.imvIcon.setImageDrawable(ImageUtils.getIcon(mContext,menuItem.getImage()));
                            }
                        });
                    }
                }else{
                    final Menu menu = menuDao.findById(item.getMenuId());
                    if(menu != null){
                        viewHolder.tvTitle.setText(menu.getName());
                        viewHolder.imvIcon.post(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.imvIcon.setImageDrawable(ImageUtils.getIcon(mContext,menu.getImage()));
                            }
                        });
                    }

                }
            }
        };
        runnable.run();


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickRecyclerView != null)
                    onItemClickRecyclerView.OnItemClick(viewHolder, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvNote,tvTotal;
        ImageView imvIcon;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvNote = view.findViewById(R.id.tv_note);
            tvTotal = view.findViewById(R.id.tv_total);
            imvIcon = view.findViewById(R.id.imv_icon);
        }
    }

}
