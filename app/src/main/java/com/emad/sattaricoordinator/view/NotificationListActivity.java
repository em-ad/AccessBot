package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.NotificationModel;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.emad.sattaricoordinator.view.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        findViews();
    }

    private void findViews() {
        recycler = findViewById(R.id.recycler);
        adapter = new NotificationAdapter();
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGetNews();
    }

    private void callGetNews() {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        RemoteDataManager.getKheyratiRepository().getNotifications(token, new ApiCallback() {
            @Override
            public void apiFailed(Object o) {
                Utils.showAlerter(NotificationListActivity.this,
                        "خطا در دریافت اعلامیه ها",
                        ((Throwable) o).getMessage());
            }

            @Override
            public void apiSucceeded(Object o) {
                if(adapter == null) return;
                adapter.setDataSet(new ArrayList<>(((List<NotificationModel>) o)));
            }
        });
    }
}