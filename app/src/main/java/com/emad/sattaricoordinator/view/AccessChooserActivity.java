package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;

public class AccessChooserActivity extends AppCompatActivity {

    private TextView tvNormalAccess;
    private TextView tvTimedAccess;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_chooser);
        findViews();
        initListeners();
//        RemoteDataManager.getTelegramRepository().sendMessage("salam baby");
    }

    private void initListeners() {
        tvNormalAccess.setOnClickListener(view ->
                startActivity(new Intent(AccessChooserActivity.this, OneTimeAccessActivity.class))
        );

        tvTimedAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void findViews() {
        tvNormalAccess = findViewById(R.id.tvNormalAccess);
        tvTimedAccess = findViewById(R.id.tvTimedAccess);
        tvMessage = findViewById(R.id.tvMessage);
    }
}