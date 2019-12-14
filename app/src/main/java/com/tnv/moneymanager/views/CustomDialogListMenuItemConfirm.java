package com.tnv.moneymanager.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.adapter.MenuItemAdapter;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.model.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class CustomDialogListMenuItemConfirm extends Dialog {
    private Context mContext;
    private RecyclerView mRcDeliveryPoints;
    private MenuItemAdapter mAdapter;
    private ArrayList<MenuItem> mData = new ArrayList<>();
    private Button mBtnCancel;
    private TextView mTvTitle;
    private OnItemClickRecyclerView onItemClickRecyclerView;

    public CustomDialogListMenuItemConfirm(Context context) {
        super(context);
        mContext = context;
    }

    public void setOnItemClickRecyclerView(OnItemClickRecyclerView onItemClickRecyclerView) {
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_list_product_dialog);
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setAttributes(wlp);
        init();
    }

    public void init() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mRcDeliveryPoints = (RecyclerView) findViewById(R.id.rc_content);
        mRcDeliveryPoints.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MenuItemAdapter(mContext,mData);
        mRcDeliveryPoints.setAdapter(mAdapter);
        if (mAdapter != null)
            mAdapter.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
                @Override
                public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
                    if(onItemClickRecyclerView != null)
                        onItemClickRecyclerView.OnItemClick(holder,position);
                    CustomDialogListMenuItemConfirm.this.dismiss();
                }
            });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogListMenuItemConfirm.this.dismiss();
            }
        });
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void loadData(List<MenuItem> arrayList) {
        mData.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

}
