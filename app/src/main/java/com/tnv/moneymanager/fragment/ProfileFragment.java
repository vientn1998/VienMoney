package com.tnv.moneymanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.gson.Gson;
import com.tnv.moneymanager.R;
import com.tnv.moneymanager.activity.LoginActivity;
import com.tnv.moneymanager.activity.MainActivity;
import com.tnv.moneymanager.activity.RegisterActivity;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Constance;
import com.tnv.moneymanager.utils.SharedPreferenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view,mViewLogout;
    private TextView mTvName,mTvEmail;
    private User mUser;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mViewLogout = view.findViewById(R.id.view_logout);
        mTvName = view.findViewById(R.id.tv_name);
        mTvEmail = view.findViewById(R.id.tv_email);
        String strResult = SharedPreferenceHelper.getInstance(getActivity()).get(Constance.PREF_USER);
        if (strResult != null && !strResult.isEmpty()) {
            mUser = new Gson().fromJson(strResult, User.class);
            mTvName.setText("Tài khoản: "+mUser.getName());
            mTvEmail.setText("Email: "+mUser.getEmail());
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
            }
        });
    }

    private void showDialogLogout() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .content("Bạn có chắc chắn muốn đăng xuất")
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
                        SharedPreferenceHelper.getInstance(getActivity()).set(Constance.PREF_USER,"");
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                })
                .cancelable(false)
                .build();
        dialog.show();
    }
}
