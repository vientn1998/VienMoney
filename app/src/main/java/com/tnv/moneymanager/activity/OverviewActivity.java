package com.tnv.moneymanager.activity;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.adapter.CateSumAdapter;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.BillDao;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.model.KeyValue;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.StringUtils;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class OverviewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvTitle;
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private CateSumAdapter adapter;
    private ScrollView scrollView;
    private View mViewChooseDate;
    private TextView mTvDate, mTvTotal, mTvMost, mTvLeast;
    private AppDatabase db;
    private BillDao billDao;
    private MenuDao menuDao;
    private Constance.TYPE_MONTH typeMonth;
    private Calendar calendar;
    private View mViewOne, mViewTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.toolbar_title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_back_clear_da);
        tvTitle.setText("Tổng quan");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pieChart = findViewById(R.id.chart1);
        recyclerView = findViewById(R.id.rc_list_data);
        mViewChooseDate = findViewById(R.id.view_choose_date);
        mTvDate = findViewById(R.id.tv_date);
        mTvTotal = findViewById(R.id.tv_total);
        mTvMost = findViewById(R.id.tv_money_most);
        mTvLeast = findViewById(R.id.tv_money_least);
        mViewOne = findViewById(R.id.view_1);
        mViewTwo = findViewById(R.id.view_2);
        scrollView = findViewById(R.id.scrollView);
        db = AppDatabase.getDatabase(this);
        billDao = db.billDao();
        menuDao = db.menuDao();
        calendar = Calendar.getInstance();
        typeMonth = (Constance.TYPE_MONTH) getIntent().getSerializableExtra(Constance.EXTRA_TYPE_MONTH);
        switch (typeMonth) {
            case First:
                loadData(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                break;
            case Current:
                loadData(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                break;
            default:
                loadData(calendar.get(Calendar.MONTH) + 2, calendar.get(Calendar.YEAR));
                break;
        }
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.smoothScrollTo(0, 0);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("VN", "vi");
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(OverviewActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                loadData(selectedMonth + 1, selectedYear);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                builder.setActivatedMonth(calendar.get(Calendar.MONTH))
                        .setMinYear(1990)
                        .setActivatedYear(calendar.get(Calendar.YEAR))
                        .setMaxYear(calendar.get(Calendar.YEAR) + 1)
                        .setTitle("Chọn tháng")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        .build().show();
            }
        });


    }

    private void loadData(int month, int year) {
        long sumMonth = billDao.getMoneySumByMonth(month, year, Constance.PREF_USER_ID);
        float sumMonthF = sumMonth;
        mTvTotal.setText(StringUtils.formatPrice(sumMonth));
        mTvDate.setText("Tháng " + (month) + "/" + year);
        List<Menu> listMenu = menuDao.getAll();
        List<KeyValue> listData = new ArrayList<>();
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        for (int i = 0; i < listMenu.size(); i++) {
            long sum = getMoneyByMonth(month, year, listMenu.get(i).getMenuId());
            if (sum == 0)
                continue;
            float sumF = sum;
            listData.add(new KeyValue(i, listMenu.get(i).getName(), sum));
            yvalues.add(new PieEntry((sumF / sumMonthF * 100), "", i));
        }
        if (listData.size() > 0) {
            long min = listData.get(0).getMoney();
            long max = listData.get(0).getMoney();
            for (int i = 1; i < listData.size(); i++) {
                if (listData.get(i).getMoney() < min) {
                    min = listData.get(i).getMoney();
                }
                if (listData.get(i).getMoney() > max) {
                    max = listData.get(i).getMoney();
                }
            }
            if (listData.size() == 0) {
                mViewOne.setVisibility(View.GONE);
                mViewTwo.setVisibility(View.GONE);
            } else if (listData.size() == 1) {
                mViewOne.setVisibility(View.VISIBLE);
                mTvMost.setText(StringUtils.formatPrice(max));
            } else {
                mViewOne.setVisibility(View.VISIBLE);
                mViewTwo.setVisibility(View.VISIBLE);
                mTvMost.setText(StringUtils.formatPrice(max));
                mTvLeast.setText(StringUtils.formatPrice(min));
            }

            adapter = new CateSumAdapter(this, listData);
            recyclerView.setAdapter(adapter);
            PieDataSet dataSet = new PieDataSet(yvalues, "Chart");
            dataSet.setSliceSpace(1.5f);
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(getResources().getColor(R.color.color1));
            colors.add(getResources().getColor(R.color.color2));
            colors.add(getResources().getColor(R.color.color3));
            colors.add(getResources().getColor(R.color.color4));
            colors.add(getResources().getColor(R.color.color5));
            colors.add(getResources().getColor(R.color.color6));
            colors.add(getResources().getColor(R.color.color7));
            colors.add(getResources().getColor(R.color.color8));
            colors.add(getResources().getColor(R.color.color9));
            colors.add(getResources().getColor(R.color.color10));
            dataSet.setColors(colors);
            pieChart.setUsePercentValues(false);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            pieChart.setData(data);
            Description description = new Description();
            description.setText("");
            pieChart.setDescription(description);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(40f);
            pieChart.setHoleRadius(40f);
            data.setValueTextSize(13f);
            data.setValueTextColor(getResources().getColor(R.color.colorWhite));
            pieChart.setTransparentCircleColor(getResources().getColor(R.color.colorPage));
            pieChart.setTransparentCircleAlpha(10);
            pieChart.setRotationAngle(0);
            pieChart.setRotationEnabled(true);
            pieChart.setHighlightPerTapEnabled(true);
            pieChart.animateXY(1200, 1200);
        }
    }

    private synchronized long getMoneyByMonth(int month, int year, int menuId) {
        return billDao.getMoneySumMenuByMonth(month, year, Constance.PREF_USER_ID, menuId);
    }
}

