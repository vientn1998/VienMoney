package com.tnv.moneymanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.db.AppDatabase;
import com.tnv.moneymanager.db.UserDao;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.SharedPreferenceHelper;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    private Button mBtnRegister;
    private MaterialEditText mEditName, mEditPassWord, mEditRetypePass, mEditEmail;
    private AppDatabase db;
    private UserDao userDao;
    protected ProgressDialog mProgressDialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        mBtnRegister = findViewById(R.id.btn_register);
        mEditName = findViewById(R.id.edt_name);
        mEditPassWord = findViewById(R.id.edt_password);
        mEditRetypePass = findViewById(R.id.edt_password_retype);
        mEditEmail = findViewById(R.id.edt_email);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    showLoading(true);
                    if(userDao.checkExistsName(mEditName.getText().toString().trim().toLowerCase()) == 0){
                        if(userDao.checkExistsEmail(mEditEmail.getText().toString().trim().toLowerCase()) == 0){
                            if (addUser() > 0) {
                                SharedPreferenceHelper.getInstance(RegisterActivity.this).set(Constance.PREF_USER, new Gson().toJson(user));
                                showLoading(false);
                                toast("Đăng ký tài khoản thành công !", Constance.TYPE_TOAST.Success);
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                showLoading(false);
                                toast("Đăng ký không thành công. Vui lòng thử lại !", Constance.TYPE_TOAST.Error);
                            }
                        }else{
                            showLoading(false);
                            toast("Email đã tồn tại", Constance.TYPE_TOAST.Error);
                        }
                    }else{
                        showLoading(false);
                        toast("Tên tài khoản đã tồn tại", Constance.TYPE_TOAST.Error);
                    }

                }
            }
        });
    }

    private synchronized long addUser() {
        user = new User();
        user.setUid(Calendar.getInstance().getTimeInMillis()/1000);
        user.setName(mEditName.getText().toString().trim().toLowerCase());
        user.setPassword(mEditPassWord.getText().toString().trim().toLowerCase());
        user.setEmail(mEditEmail.getText().toString().trim().toLowerCase());
        return userDao.insert(user);
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
        if (!mEditPassWord.getText().toString().toLowerCase().equals(mEditRetypePass.getText().toString().toLowerCase())) {
            toast("Mật khẩu không khớp", Constance.TYPE_TOAST.Error);
            return false;
        }
        if (!isValidEmail(mEditEmail.getText().toString().trim().toLowerCase())) {
            toast("Email không hợp lệ", Constance.TYPE_TOAST.Error);
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

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
