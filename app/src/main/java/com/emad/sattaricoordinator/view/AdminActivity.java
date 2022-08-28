package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.ItemClickInterface;
import com.emad.sattaricoordinator.model.RequestItem;
import com.emad.sattaricoordinator.model.RequestResponseModel;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.emad.sattaricoordinator.view.adapter.RequestListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private TextView tvNotification;
    private RequestListAdapter adapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        findViews();
        token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        callGetRequests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGetRequests();
    }

    private void callGetRequests() {
        RemoteDataManager.getKheyratiRepository()
                .getRequests(token, new ApiCallback() {
                    @Override
                    public void apiFailed(Object o) {
                        Utils.showAlerter(
                                AdminActivity.this,
                                "خطا در دریافت لیست درخواست ها",
                                ((Throwable) o).getMessage());
                    }

                    @Override
                    public void apiSucceeded(Object o) {
                        List<RequestResponseModel> requests = ((List<RequestResponseModel>) o);
                        ArrayList<RequestItem> items = new ArrayList<>();
                        for (int i = 0; i < requests.size(); i++) {
                            for (int j = 0; j < requests.get(i).getRequests().size(); j++) {
                                if (!requests.get(i).getRequests().get(j).getStatus().equals("pending")) {
                                    continue;
                                }
                                RequestItem item = requests.get(i).getRequests().get(j);
                                item.setOwnerName(requests.get(i).getName());
                                items.add(item);
                            }
                        }
                        adapter.setDataSet(items);
                    }
                });
    }

    private void findViews() {
        recycler = findViewById(R.id.recycler);
        tvNotification = findViewById(R.id.tvNotification);
        adapter = new RequestListAdapter(item -> {
            Intent intent = new Intent(AdminActivity.this, RequestDetailsActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        });
        tvNotification.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, NotificationCreationActivity.class);
            startActivity(intent);
        });
        recycler.setAdapter(adapter);
    }
}