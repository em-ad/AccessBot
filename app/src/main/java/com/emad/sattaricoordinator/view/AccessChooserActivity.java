package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.NotificationModel;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;

import java.util.List;

public class AccessChooserActivity extends AppCompatActivity {

    private TextView tvNormalAccess;
    private TextView tvTimedAccess;
    private TextView tvMessage;
    private TextView tvHistory;
    private AppCompatImageView ivNotification;
    private TextView tvNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_chooser);
        findViews();
        initListeners();
    }

    private void initListeners() {

        ivNotification.setOnClickListener(view ->
                startActivity(new Intent(AccessChooserActivity.this, NotificationListActivity.class)));

        tvNormalAccess.setOnClickListener(view ->
                startActivity(new Intent(AccessChooserActivity.this, OneTimeAccessActivity.class))
        );

        tvHistory.setOnClickListener(view ->
                startActivity(new Intent(AccessChooserActivity.this, MyRequestsActivity.class)));

        tvTimedAccess.setOnClickListener(view -> {

        });

        tvMessage.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForNotifications();
    }

    private void checkForNotifications() {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        RemoteDataManager.getKheyratiRepository().getNotifications(token, new ApiCallback() {
            @Override
            public void apiFailed(Object o) {
                tvNotification.setText("?");
            }

            @Override
            public void apiSucceeded(Object o) {
                tvNotification.setText(String.valueOf(((List<NotificationModel>) o).size()));
            }
        });
    }

    private void findViews() {
        tvNormalAccess = findViewById(R.id.tvNormalAccess);
        tvHistory = findViewById(R.id.tvHistory);
        tvTimedAccess = findViewById(R.id.tvTimedAccess);
        tvMessage = findViewById(R.id.tvMessage);
        ivNotification = findViewById(R.id.ivNotification);
        tvNotification = findViewById(R.id.tvNotification);
    }
}