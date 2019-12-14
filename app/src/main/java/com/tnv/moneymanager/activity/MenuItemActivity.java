package com.tnv.moneymanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.adapter.MenuItemAdapter;
import com.tnv.moneymanager.listener.OnItemClickRecyclerView;


public class MenuItemActivity extends AppCompatActivity{
    private RecyclerView mRcRecyclerView;
    private MenuItemAdapter adapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chọn...");
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
        mRcRecyclerView = findViewById(R.id.rc_list_data);
        adapter = new MenuItemAdapter(this);
        mRcRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickRecyclerView(new OnItemClickRecyclerView() {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MenuItemActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
