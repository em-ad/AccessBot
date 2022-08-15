package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.LoginRequestModel;
import com.emad.sattaricoordinator.model.LoginResponseModel;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etPhone;
    private EditText etNationalCode;
    private CircularProgressIndicator loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setClickListener();
    }

    private void setClickListener() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()){
                    Utils.showAlerter(LoginActivity.this, "خطا", "شماره موبایل و کد ملی را کامل وارد کنید!");
                } else {
                    callLogin();
                }
            }
        });
    }

    private void callLogin() {
        String nid = etNationalCode.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        loading.setVisibility(View.VISIBLE);
        RemoteDataManager.getKheyratiRepository().login(phone, nid, new ApiCallback() {
            @Override
            public void apiFailed(Object o) {
                loading.setVisibility(View.GONE);
                Utils.showAlerter(LoginActivity.this, "خطا در ورود", ((Throwable) o).getMessage());
            }

            @Override
            public void apiSucceeded(Object response) {
                loading.setVisibility(View.GONE);
                saveToken(((LoginResponseModel) response).getToken());
                if(((LoginResponseModel) response).getUser().isAdmin()){
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                } else
                    startActivity(new Intent(LoginActivity.this, AccessChooserActivity.class));
            }
        });
    }

    private void saveToken(String token) {
        getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE).edit().putString("token", token).apply();
    }

    private boolean validate() {
        return etPhone.getText().toString().trim().length() == 11 &&
                etNationalCode.getText().toString().trim().length() == 10;
    }

    private void findViews() {
        loading = findViewById(R.id.loading);
        tvLogin = findViewById(R.id.tvLogin);
        etPhone = findViewById(R.id.etPhone);
        etNationalCode = findViewById(R.id.etNationalCode);
    }
}