package com.tnv.moneymanager.listener;


import android.support.v7.widget.RecyclerView;

import com.tnv.moneymanager.model.Bill;


public interface OnItemMenuClickRecyclerView {
    void OnItemClick(RecyclerView.ViewHolder holder, long idBill);
}
