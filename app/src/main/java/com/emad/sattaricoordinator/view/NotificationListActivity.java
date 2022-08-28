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
import java.util.Collections;
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
                saveLastUnseenToPref(o);
            }
        });
    }

    private void saveLastUnseenToPref(Object o) {
        List<NotificationModel> notifs = (List<NotificationModel>) o;
        Collections.reverse(notifs);
        if(adapter != null)
            adapter.setDataSet(new ArrayList<>(notifs),
                    getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                            .getLong("last_seen", 0));

        long lastMessageDate = 0;
        for (int i = 0; i < notifs.size(); i++) {
            if (notifs.get(i).getCreateDate() > lastMessageDate)
                lastMessageDate = notifs.get(i).getCreateDate();
        }

        getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .edit()
                .putLong("last_seen", lastMessageDate)
                .apply();

    }
}