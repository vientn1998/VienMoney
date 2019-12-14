package com.tnv.moneymanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.MenuDao;
import com.tnv.moneymanager.db.MenuItemDao;
import com.tnv.moneymanager.db.UserDao;
import com.tnv.moneymanager.model.Event;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private Button mBtnLogin, mBtnRegister;
    private MaterialEditText mEditName, mEditPassWord;
    private AppDatabase db;
    private UserDao userDao;
    private MenuDao menuDao;
    private MenuItemDao menuItemDao;
    protected ProgressDialog mProgressDialog;


    private class TaskInsertMenuDB extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            //Insert DB
            final ArrayList<Menu> listMenu = new ArrayList<>();
            listMenu.add(new Menu(100, "Ăn uống", 100));
            listMenu.add(new Menu(101, "Dịch vụ sinh hoạt", 101));
            listMenu.add(new Menu(102, "Đi lại", 102));
            listMenu.add(new Menu(103, "Con cái", 103));
            listMenu.add(new Menu(104, "Trang phục", 104));
            listMenu.add(new Menu(105, "Tiền đám", 105));
            listMenu.add(new Menu(106, "Sức khoẻ", 106));
            listMenu.add(new Menu(107, "Bạn bè hoặc Người yêu", 107));
            listMenu.add(new Menu(108, "Du lịch", 108));
            listMenu.add(new Menu(109, "Bảo hiểm", 109));
            menuDao.insertAll(listMenu);

            User user = new User();
            user.setUid(Calendar.getInstance().getTimeInMillis()/1000);
            user.setName("vien");
            user.setPassword("123456");
            user.setEmail("vientn1998@gmail.com");
            userDao.insert(user);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
//            if (result) {
//                if (menuDao.getAll().size() > 0)
//                    toast("Insert menu thành công", Constance.TYPE_TOAST.Success);
//            }
        }
    }

    private class TaskInsertMenuItemDB extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            //Insert DB
            final ArrayList<MenuItem> listMenuItem = new ArrayList<>();
            /*Ăn uống*/
            listMenuItem.add(new MenuItem(1000, "Ăn quán/nhà hàng", 1000, 100));
            listMenuItem.add(new MenuItem(1001, "Cafe", 1001, 100));
            listMenuItem.add(new MenuItem(1002, "Đi chợ/siêu thị", 1002, 100));

            /*Dịch vụ sinh hoạt*/
            listMenuItem.add(new MenuItem(1003, "Điện", 1003, 101));
            listMenuItem.add(new MenuItem(1004, "Nước", 1004, 101));
            listMenuItem.add(new MenuItem(1005, "Điện thoại", 1005, 101));
            listMenuItem.add(new MenuItem(1006, "Internet", 1006, 101));
            listMenuItem.add(new MenuItem(1007, "Gas", 1007, 101));

            /*Đi lại*/
            listMenuItem.add(new MenuItem(1008, "Bảo hiểm xe", 1008, 102));
            listMenuItem.add(new MenuItem(1009, "Rửa xe", 1009, 102));
            listMenuItem.add(new MenuItem(1010, "Sửa xe", 1010, 102));
            listMenuItem.add(new MenuItem(1011, "Grap/Taxi", 1011, 102));
            listMenuItem.add(new MenuItem(1012, "Xăng xe", 1012, 102));

            /*Con cái*/
            listMenuItem.add(new MenuItem(1013, "Đồ chơi", 1013, 103));
            listMenuItem.add(new MenuItem(1014, "Học phí", 1014, 103));
            listMenuItem.add(new MenuItem(1015, "Sách vở", 1015, 103));
            listMenuItem.add(new MenuItem(1016, "Tiền tiêu vặt", 1016, 103));

            /*Trang phục*/
            listMenuItem.add(new MenuItem(1017, "Giày dép", 1017, 104));
            listMenuItem.add(new MenuItem(1018, "Quần áo", 1018, 104));
            listMenuItem.add(new MenuItem(1019, "Đồng hồ", 1019, 104));

            /*Đám đình*/
            listMenuItem.add(new MenuItem(1020, "Đám cưới", 1020, 105));
            listMenuItem.add(new MenuItem(1021, "Đám giỗ", 1021, 105));
            listMenuItem.add(new MenuItem(1022, "Đám tang", 1022, 105));

            /*Sức khoẻ*/
            listMenuItem.add(new MenuItem(1023, "Khám bệnh", 1023, 106));
            listMenuItem.add(new MenuItem(1024, "Thuốc", 1024, 106));
            menuItemDao.insertAll(listMenuItem);


            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
//            if (result) {
//                if (menuItemDao.getAll().size() > 0)
//                    toast("Insert menu item thành công", Constance.TYPE_TOAST.Success);
//            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        menuDao = db.menuDao();
        menuItemDao = db.menuItemDao();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        mBtnLogin = findViewById(R.id.btn_login);
        mEditName = findViewById(R.id.edt_name);
        mEditPassWord = findViewById(R.id.edt_password);
        mBtnRegister = findViewById(R.id.btn_register);
        if (!SharedPreferenceHelper.getInstance(this).getBool(Constance.PREF_FIRST_INSTALL)) {
            SharedPreferenceHelper.getInstance(this).setBool(Constance.PREF_FIRST_INSTALL, true);
            TaskInsertMenuDB taskInsertDB = new TaskInsertMenuDB();
            taskInsertDB.execute();
            TaskInsertMenuItemDB taskInsertMenuDB = new TaskInsertMenuItemDB();
            taskInsertMenuDB.execute();
        }

        String strResult = SharedPreferenceHelper.getInstance(this).get(Constance.PREF_USER);
        if (strResult != null && !strResult.isEmpty()) {
            showLoading(true);
            final User user = new Gson().fromJson(strResult, User.class);
            if (user != null) {
                Constance.PREF_USER_ID = user.getUid();
                mEditName.setText(user.getName());
                mEditName.setSelection(mEditName.getText().toString().length());
                mEditPassWord.setText(user.getPassword());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showLoading(false);
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, 1500);
            }
        }
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    showLoading(true);
                    User user = userDao.findByName(mEditName.getText().toString().trim().toLowerCase(), mEditPassWord.getText().toString().trim().toLowerCase());
                    if (user == null || user.getName() == null) {
                        showLoading(false);
                        toast("Tên tài khoản hoặc mật khẩu không chính xác !", Constance.TYPE_TOAST.Error);
                    } else {
                        SharedPreferenceHelper.getInstance(LoginActivity.this).set(Constance.PREF_USER, new Gson().toJson(user));
                        Constance.PREF_USER_ID = user.getUid();
                        showLoading(false);
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
//                finish();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private boolean valid() {
        if (mEditName.getText().toString().trim().length() < 2) {
            toast("Tên tài khoản phải lớn hơn 1 ký tự", Constance.TYPE_TOAST.Error);
            return false;
        }
        if (mEditPassWord.getText().toString().trim().length() < 6) {
            toast("Mật khẩu phải lớn hơn 5 ký tự", Constance.TYPE_TOAST.Error);
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
}
