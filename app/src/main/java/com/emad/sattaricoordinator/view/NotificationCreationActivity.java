package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.KheyratiRepository;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import okhttp3.internal.Util;

public class NotificationCreationActivity extends AppCompatActivity {

    CircularProgressIndicator loading;
    EditText etMessage;
    TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creation);
        loading = findViewById(R.id.loading);
        tvSend = findViewById(R.id.tvSend);
        etMessage = findViewById(R.id.etMessage);
        tvSend.setOnClickListener(view -> {
            if(!checkValidation()) return;
            callSendNotification();
        });
    }

    private void callSendNotification() {
        loading.setVisibility(View.VISIBLE);
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        RemoteDataManager.getKheyratiRepository()
                .createNotification(token, etMessage.getText().toString().trim()
                        , new ApiCallback() {
                            @Override
                            public void apiFailed(Object o) {
                                loading.setVisibility(View.GONE);
                                Utils.showAlerter(NotificationCreationActivity.this, "خطا", "در ارسال اعلامیه خطایی رخ داد");
                            }

                            @Override
                            public void apiSucceeded(Object o) {
                                loading.setVisibility(View.GONE);
                                onBackPressed();
                            }
                        });

    }

    private boolean checkValidation() {
        if(etMessage.getText().toString().trim().length() < 3){
            Utils.showAlerter(this, "خطایی رخ داد", "متن اطلاعیه را کامل کنید!");
            return false;
        } else return true;
    }
}