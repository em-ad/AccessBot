package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.ItemClickInterface;
import com.emad.sattaricoordinator.model.RequestItem;
import com.emad.sattaricoordinator.model.RequestResponseModel;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.emad.sattaricoordinator.view.adapter.MyRequestListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyRequestsActivity extends AppCompatActivity {

    RecyclerView recycler;
    MyRequestListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        recycler = findViewById(R.id.recycler);
        adapter = new MyRequestListAdapter(item -> {});
        recycler.setAdapter(adapter);

        callGetRequests();
    }

    private void callGetRequests() {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        RemoteDataManager.getKheyratiRepository()
                .getMyRequests(token, new ApiCallback() {
                    @Override
                    public void apiFailed(Object o) {
                        Utils.showAlerter(
                                MyRequestsActivity.this,
                                "خطا در دریافت لیست درخواست ها",
                                ((Throwable) o).getMessage());
                    }

                    @Override
                    public void apiSucceeded(Object o) {
                        adapter.setDataSet(new ArrayList<>((List<RequestItem>) o));
                    }
                });
    }
}