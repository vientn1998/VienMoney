package com.tnv.moneymanager.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.activity.AddBillActivity;
import com.tnv.moneymanager.activity.OverviewActivity;
import com.tnv.moneymanager.adapter.BillAdapter;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.BillDao;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.listener.OnItemMenuClickRecyclerView;
import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.SharedPreferenceHelper;
import com.tnv.moneymanager.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeforeMonthFragment extends Fragment {
    private RecyclerView recyclerView;
    View view;
    private AppDatabase db;
    private MenuDao menuDao;
    private BillAdapter adapter;
    private BillDao billDao;
    private TextView mTvTotal;
    private List<Bill> bills = new ArrayList<>();
    private View mView;
    private User mUser;
    protected ProgressDialog mProgressDialog;
    public BeforeMonthFragment() {
        // Required empty public constructor
    }

    public void showLoading(boolean isShow) {
        try {
            if (isShow) {
                mProgressDialog.show();
            } else {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (IllegalArgumentException ex) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        db = AppDatabase.getDatabase(getContext());
        billDao = db.billDao();
        showLoading(true);
        mView = view.findViewById(R.id.view_choose_overview);
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String strResult = SharedPreferenceHelper.getInstance(getActivity()).get(Constance.PREF_USER);
                if (strResult != null && !strResult.isEmpty()) {
                    mUser = new Gson().fromJson(strResult, User.class);
                }
                mTvTotal = view.findViewById(R.id.tv_total);
                recyclerView = view.findViewById(R.id.rc_list_data);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                loadData();
            }
        }, 1000);
        return view;
    }

    private void loadData() {
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.set(Calendar.MONTH,calendarLast.get(Calendar.MONTH) - 1);
        calendarLast.set(Calendar.DAY_OF_MONTH, calendarLast.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendarLast.set(Calendar.HOUR_OF_DAY,23);
        calendarLast.set(Calendar.MINUTE,59);
        mTvTotal.setText(StringUtils.formatPrice(billDao.getSumPriceByMonthFirst(calendarLast.getTime(),mUser.getUid())));
        List<Integer> listDay = billDao.getBillGroupMonthFirst(calendarLast.getTime(),mUser.getUid());
        List<Bill> listBill = new ArrayList<>();
        for (int i = 0; i < listDay.size(); i++) {
            long total = billDao.getSumPriceBillByDayFirst(calendarLast.getTime(),listDay.get(i),mUser.getUid());
            List<Bill> listItem = billDao.getBillByDayFirst( calendarLast.getTime(),listDay.get(i),mUser.getUid());
            Bill item = new Bill();
            item.setMoney(total);
            item.setDate(listItem.get(0).getDate());
            item.setList(listItem);
            listBill.add(item);
        }
        adapter = new BillAdapter(getActivity(),listBill);
        recyclerView.setAdapter(adapter);
        showLoading(false);
        if(adapter != null){
            adapter.setOnItemMenuClickRecyclerView(new OnItemMenuClickRecyclerView() {
                @Override
                public void OnItemClick(RecyclerView.ViewHolder holder, long idBill) {
                    startActivity(new Intent(getActivity(), AddBillActivity.class).putExtra(Constance.EXTRA_BILL,billDao.getById(idBill)));
                }
            });
        }
    }


    private BroadcastReceiver _refreshReceiverBill = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadData();
        }
    };
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(Constance.BROADCAST_ADD_BILL);
        getActivity().registerReceiver(_refreshReceiverBill, filter);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OverviewActivity.class).putExtra(Constance.EXTRA_TYPE_MONTH,Constance.TYPE_MONTH.First));
            }
        });

    }
}
