package com.emad.sattaricoordinator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.ChangeStatusModel;
import com.emad.sattaricoordinator.model.RequestItem;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.utils.Utils;
import com.emad.sattaricoordinator.view.adapter.RequestPersonAdapter;

import java.util.ArrayList;

import ir.hamsaa.persiandatepicker.date.PersianDateImpl;

public class RequestDetailsActivity extends AppCompatActivity {

    private TextView tvAccept;
    private TextView tvReject;
    private TextView tvDate;
    private TextView tvOwner;
    private RecyclerView recycler;
    private RequestPersonAdapter adapter;

    private RequestItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        tvDate = findViewById(R.id.tvDate);
        tvOwner = findViewById(R.id.tvOwner);
        recycler = findViewById(R.id.recycler);
        tvAccept = findViewById(R.id.tvAccept);
        tvReject = findViewById(R.id.tvReject);
        item = (RequestItem) getIntent().getSerializableExtra("item");
        adapter = new RequestPersonAdapter(new ArrayList<>(item.getEmployees()));
        recycler.setAdapter(adapter);
        PersianDateImpl impl = new PersianDateImpl();
        impl.setDate(Long.valueOf(item.getStartDate()));
        tvDate.setText("تاریخ: " + impl.getPersianLongDate());
        tvOwner.setText("درخواست کننده: " + item.getOwnerName());
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRequestStatus("accepted");
            }
        });
        tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRequestStatus("rejected");
            }
        });
    }

    private void changeRequestStatus(String status) {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
                .getString("token", "");
        ChangeStatusModel model = new ChangeStatusModel();
        model.setStatus(status);
        model.setRequestId(item.getId());
        RemoteDataManager.getKheyratiRepository()
                .changeRequestStatus("Bearer " + token, model, new ApiCallback() {
                    @Override
                    public void apiFailed(Object o) {
                        Utils.showAlerter(
                                RequestDetailsActivity.this,
                                "خطا در تغییر وضعیت درخواست",
                                ((Throwable) o).getMessage());
                    }

                    @Override
                    public void apiSucceeded(Object o) {
                        onBackPressed();
                    }
                });
    }
}