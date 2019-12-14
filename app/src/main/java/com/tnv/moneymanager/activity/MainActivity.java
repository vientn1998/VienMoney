package com.tnv.moneymanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tnv.moneymanager.R;
import com.tnv.moneymanager.fragment.BeforeMonthFragment;
import com.tnv.moneymanager.fragment.ProfileFragment;
import com.tnv.moneymanager.fragment.AfterMonthFragment;
import com.tnv.moneymanager.fragment.CurrentMonthFragment;
import com.tnv.moneymanager.utils.Constance;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    protected Fragment mFragment;
    private Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    private FloatingActionButton actionButton;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setNewPage(new BeforeMonthFragment());
                    return true;
                case R.id.navigation_dashboard:
                    setNewPage(new CurrentMonthFragment());
                    return true;
                case R.id.navigation_notifications:
                    setNewPage(new AfterMonthFragment());
//                    toast("Đang phát triển", Constance.TYPE_TOAST.Info);
                    return true;
                case R.id.navigation_account:
                    setNewPage(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionButton = findViewById(R.id.fab);
        toolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setNewPage(new CurrentMonthFragment());
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,AddBillActivity.class), Constance.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constance.REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
//                Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setNewPage(Fragment fragment) {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_content, fragment, "currentFragment");
            transaction.commitAllowingStateLoss();
            if (mFragment != null)
                transaction.remove(mFragment);
            mFragment = fragment;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void toast(String message, Constance.TYPE_TOAST typeToast) {
        switch (typeToast) {
            case Success:
                Toasty.success(this, message, Toast.LENGTH_SHORT).show();
                break;
            case Error:
                Toasty.error(this, message, Toast.LENGTH_SHORT).show();
                break;
            case Warning:
                Toasty.warning(this, message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toasty.info(this, message, Toast.LENGTH_SHORT).show();

        }

    }
}
