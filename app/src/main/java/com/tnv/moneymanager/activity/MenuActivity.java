package com.tnv.moneymanager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.db.BillDao;
import com.tnv.moneymanager.db.MenuItemDao;
import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.Menu;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tnv.moneymanager.adapter.MenuAdapter;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;
import com.tnv.moneymanager.listener.OnItemClickViewRecyclerView;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.StringUtils;
import com.tnv.moneymanager.views.CustomDialogListMenuItemConfirm;
import com.tnv.moneymanager.views.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView mRcListMenu;
    MenuAdapter adapter;
    private Toolbar toolbar;
    private AppDatabase db;
    private MenuDao menuDao;
    private MenuItemDao menuItemDao;
    private List<Menu> listMenu = new ArrayList<>();
    private List<MenuItem> listMenuItem = new ArrayList<>();
    protected ProgressDialog mProgressDialog;
    private BillDao billDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = AppDatabase.getDatabase(this);
        menuDao = db.menuDao();
        menuItemDao = db.menuItemDao();
        listMenu = menuDao.getAll();
        billDao = db.billDao();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chọn danh mục");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRcListMenu = findViewById(R.id.rc_list_data);
        adapter = new MenuAdapter(this, listMenu);
        mRcListMenu.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRcListMenu.addItemDecoration(itemDecoration);
        mRcListMenu.setAdapter(adapter);
        adapter.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
                listMenuItem.clear();
                listMenuItem = menuItemDao.getById(listMenu.get(position).getMenuId());
                if (listMenuItem.size() > 0) {
                    CustomDialogListMenuItemConfirm productConfirm = new CustomDialogListMenuItemConfirm(MenuActivity.this);
                    productConfirm.show();
                    productConfirm.setTitle("Chọn " + listMenu.get(position).getName().toLowerCase());
                    productConfirm.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
                        @Override
                        public void OnItemClick(RecyclerView.ViewHolder holder, final int position) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("isMenu", false);
                                    returnIntent.putExtra("result", listMenuItem.get(position));
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            }, 100);
                        }
                    });
                    productConfirm.loadData(listMenuItem);
                } else {
                    toast("Danh mục rỗng !", Constance.TYPE_TOAST.Warning);
                }
            }
        });
        adapter.setOnItemClickViewRecyclerView(new OnItemClickViewRecyclerView() {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("isMenu", true);
                returnIntent.putExtra("result", listMenu.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void toast(String message, Constance.TYPE_TOAST typeToast) {
        switch (typeToast) {
            case Success:
                Toasty.success(this, message, Toast.LENGTH_SHORT).show();
                break;
            case Error:
                Toasty.error(this, message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toasty.info(this, message, Toast.LENGTH_SHORT).show();

        }

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
}
