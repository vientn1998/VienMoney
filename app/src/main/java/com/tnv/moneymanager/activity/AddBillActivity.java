package com.tnv.moneymanager.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.BillDao;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.db.MenuItemDao;
import com.tnv.moneymanager.db.UserDao;
import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.SharedPreferenceHelper;
import com.tnv.moneymanager.utils.StringUtils;
import com.tnv.moneymanager.views.ImageUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AddBillActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvTitle, mTvMenu, mTvDate, mTvHour;
    private View mViewMenu, mViewDate, mViewHour, mViewEvent;
    private Menu mMenu;
    private MenuItem mMenuItem;
    private MaterialEditText mEdtMoney;
    private Calendar mCalendarBill = Calendar.getInstance();
    private Bill mBill;
    private Button mBtnAdd, mBtnDelete;
    private EditText mEdtNote;
    private ProgressDialog mProgressDialog;
    private AppDatabase db;
    private UserDao userDao;
    private BillDao billDao;
    private MenuDao menuDao;
    private MenuItemDao menuItemDao;
    private User mUser;
    private boolean isEdit;
    private DecimalFormat mDecimalFormat;
    private boolean hasFractionalPart;
    private DecimalFormat dfnd;
    private ImageView mImvIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        if (getIntent().hasExtra(Constance.EXTRA_BILL)) {
            mBill = (Bill) getIntent().getSerializableExtra(Constance.EXTRA_BILL);
            isEdit = true;
        }
        String strResult = SharedPreferenceHelper.getInstance(this).get(Constance.PREF_USER);
        if (strResult != null && !strResult.isEmpty()) {
            mUser = new Gson().fromJson(strResult, User.class);
        }
        db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        billDao = db.billDao();
        menuDao = db.menuDao();
        menuItemDao = db.menuItemDao();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        mDecimalFormat = new DecimalFormat("#,###.##", otherSymbols);
        mDecimalFormat.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###", otherSymbols);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        mEdtNote = findViewById(R.id.edt_note);
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnDelete = findViewById(R.id.btn_delete);
        mTvDate = findViewById(R.id.tv_date);
        mEdtMoney = findViewById(R.id.edt_money);
        mTvMenu = findViewById(R.id.tv_title);
        mTvHour = findViewById(R.id.tv_hour);
        mViewMenu = findViewById(R.id.view_menu);
        mViewDate = findViewById(R.id.view_date);
        mViewHour = findViewById(R.id.view_hour);
        mViewEvent = findViewById(R.id.view_event);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.toolbar_title);
        mImvIcon = findViewById(R.id.imv_icon);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_back_clear_da);
        tvTitle.setText("Thêm mục mới");
        if (mBill == null) {
            mBtnAdd.setVisibility(View.VISIBLE);
            mBill = new Bill();
            mBill.setDate(mCalendarBill.getTime());
            mTvHour.setText(StringUtils.formatHourTime(mCalendarBill.getTimeInMillis()));
        } else {
            tvTitle.setText("Chỉnh sửa danh mục");
            mEdtMoney.setText(mBill.getMoney() + "");
            mEdtMoney.setSelection(String.valueOf(mBill.getMoney()).length());
            mEdtNote.setText(mBill.getDescription());
            mTvDate.setText(StringUtils.formatDateTime(mBill.getDate().getTime()));
            mTvHour.setText(StringUtils.formatHourTime(mBill.getDate().getTime()));
            mTvMenu.setSelected(true);
            if (mBill.getItemId() != 0) {
                final MenuItem menuItem = menuItemDao.getMenuItem(mBill.getItemId());
                if (menuItem != null) {
                    mTvMenu.setText(menuItem.getName());
                    mImvIcon.post(new Runnable() {
                        @Override
                        public void run() {
                            mImvIcon.setImageDrawable(ImageUtils.getIcon(AddBillActivity.this,menuItem.getImage()));
                        }
                    });
                }
            } else {
                final Menu menu = menuDao.findById(mBill.getMenuId());
                if (menu != null) {
                    mTvMenu.setText(menu.getName());
                    mImvIcon.post(new Runnable() {
                        @Override
                        public void run() {
                            mImvIcon.setImageDrawable(ImageUtils.getIcon(AddBillActivity.this,menu.getImage()));
                        }
                    });
                }
            }
            mBtnAdd.setVisibility(View.VISIBLE);
            mBtnAdd.setText("Chỉnh sửa");
            mBtnDelete.setVisibility(View.VISIBLE);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddBillActivity.this, MenuActivity.class), Constance.REQUEST_CODE);
            }
        });


        mViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
        mViewHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHour();
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    String money = mEdtMoney.getText().toString().trim().replaceAll(",","");
                    mBill.setuId(mUser.getUid());
                    mBill.setMoney(Long.parseLong(money));
                    mBill.setDescription(mEdtNote.getText().toString().trim());
                    mBill.setEid(0);
                    mBill.setBid(mCalendarBill.getTimeInMillis() / 1000);
                    if (isEdit) {
                        toast("Đang phát triển", Constance.TYPE_TOAST.Info);
                    } else {
                        if (billDao.insert(mBill) > 0) {
                            toast("Thêm mục mới thành công", Constance.TYPE_TOAST.Success);
                            Intent in = new Intent(Constance.BROADCAST_ADD_BILL);
                            sendBroadcast(in);
                            finish();
                        } else {
                            toast("Thêm thất bại", Constance.TYPE_TOAST.Error);
                        }
                    }
                }
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(AddBillActivity.this)
                        .content("Bạn có chắc chắn muốn xoá danh mục ngày không ?")
                        .theme(Theme.LIGHT)
                        .title("Thông báo")
                        .positiveText("Đồng ý")
                        .negativeText("Không")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                toast("Đang phát triển", Constance.TYPE_TOAST.Info);
                            }
                        })
                        .cancelable(false)
                        .build();
                dialog.show();
            }
        });
        mViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Đang phát triển", Constance.TYPE_TOAST.Info);
            }
        });
        mEdtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(mDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator()))) {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEdtMoney.removeTextChangedListener(this);
                try {
                    int inilen, endlen;
                    inilen = mEdtMoney.getText().length();
                    String v = s.toString().replace(String.valueOf(mDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = mDecimalFormat.parse(v);
                    int cp = mEdtMoney.getSelectionStart();
                    if (hasFractionalPart) {
                        mEdtMoney.setText(mDecimalFormat.format(n));
                    } else {
                        mEdtMoney.setText(dfnd.format(n));
                    }
                    endlen = mEdtMoney.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= mEdtMoney.getText().length()) {
                        mEdtMoney.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        mEdtMoney.setSelection(mEdtMoney.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                    mEdtMoney.setText("");
                }

                mEdtMoney.addTextChangedListener(this);
            }
        });
    }

    private boolean valid() {
        String money = mEdtMoney.getText().toString().trim().replaceAll(",","");
        if ((money.isEmpty()) || Long.parseLong(money) == 0) {
            toast("Vui lòng nhập số tiền", Constance.TYPE_TOAST.Error);
            return false;
        }
        if (mBill.getMenuId() == 0 && mBill.getItemId() == 0) {
            toast("Vui lòng chọn danh mục", Constance.TYPE_TOAST.Error);
            return false;
        }
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constance.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.hasExtra("isMenu") && data.getBooleanExtra("isMenu", false)) {
                    mMenu = (Menu) data.getSerializableExtra("result");
                    mTvMenu.setText(mMenu.getName());
                    mBill.setMenuId(mMenu.getMenuId());
                    mBill.setItemId(0);
                    mImvIcon.post(new Runnable() {
                        @Override
                        public void run() {
                            mImvIcon.setImageDrawable(ImageUtils.getIcon(AddBillActivity.this,mMenu.getImage()));
                        }
                    });
                } else {
                    mMenuItem = (MenuItem) data.getSerializableExtra("result");
                    mTvMenu.setText(mMenuItem.getName());
                    mBill.setItemId(mMenuItem.getItemId());
                    mBill.setMenuId(mMenuItem.getMenuId());
                    mImvIcon.post(new Runnable() {
                        @Override
                        public void run() {
                            mImvIcon.setImageDrawable(ImageUtils.getIcon(AddBillActivity.this,mMenuItem.getImage()));
                        }
                    });
                }
                mTvMenu.setSelected(true);
            }
        }
    }

    private void showDate() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int monthOfYear,
                                          int dayOfMonth) {
                        mCalendarBill.set(Calendar.YEAR, year);
                        mCalendarBill.set(Calendar.MONTH, monthOfYear);
                        mCalendarBill.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mBill.setDate(mCalendarBill.getTime());
                        mTvDate.setText(StringUtils.formatDateTime(mCalendarBill.getTimeInMillis()));
                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showHour() {
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendarBill.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendarBill.set(Calendar.MINUTE, minute);
                mBill.setDate(mCalendarBill.getTime());
                mTvHour.setText(StringUtils.formatHourTime(mCalendarBill.getTimeInMillis()));
            }

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();


    }
}
