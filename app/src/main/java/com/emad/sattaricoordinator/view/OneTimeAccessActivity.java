package com.emad.sattaricoordinator.view;

import static com.emad.sattaricoordinator.utils.Utils.showAlerter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emad.sattaricoordinator.BuildConfig;
import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.CreateRequestModel;
import com.emad.sattaricoordinator.model.Employee;
import com.emad.sattaricoordinator.repository.remote.ApiCallback;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.repository.remote.TelegramRepository;
import com.emad.sattaricoordinator.utils.Utils;
import com.tapadoo.alerter.Alerter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;

public class OneTimeAccessActivity extends AppCompatActivity {

    private String accessDate;
    private LinearLayout ll;
    private TextView tvAddMore;
    private TextView tvDate;
    private TextView tvSend;
    private EditText etName;
    private EditText etNationalCode;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_access);
        findViews();
        initClicks();
        initObservers();
    }

    private void initObservers() {
        loading.observe(this, show -> {
            findViewById(R.id.loading).setVisibility(show ? View.VISIBLE : View.GONE);
            tvSend.setText(show ? "???????? ?????????? ????????????" : "?????????? ??????????????");
        });
    }

    private void initClicks() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        tvSend.setOnClickListener(view -> {
            if (accessDate != null && etNationalCode.getText().toString().trim().length() == 10 && etName.getText().toString().trim().length() > 1){
                sendRequest();
            } else showValidationError();
        });
        tvDate.setOnClickListener(view -> showDatePicker());
        tvAddMore.setOnClickListener(view -> addNewInputItem());
    }

    private void addNewInputItem() {
        LayoutInflater.from(this).inflate(R.layout.item_request_person, ll, true);
    }

    private void sendRequest() {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE).getString("token", "");
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(String.valueOf(accessDate));
        requestModel.setEndDate(String.valueOf(accessDate));
        List<Employee> employees = makeEmployeeListBundle();
        requestModel.setEmployees(employees);
        loading.postValue(true);
        RemoteDataManager.getKheyratiRepository()
                .createRequest("Bearer " + token, requestModel, new ApiCallback() {
                    @Override
                    public void apiFailed(Object o) {
                        loading.postValue(false);
                        Utils.showAlerter(
                                OneTimeAccessActivity.this,
                                "?????? ???? ?????????? ?????????????? ????????",
                                ((Throwable) o).getMessage());
                    }

                    @Override
                    public void apiSucceeded(Object o) {
                        loading.postValue(false);
                        Utils.showAlerter(
                                OneTimeAccessActivity.this,
                                "?????????????? ???????? " + (employees.size()) + " ?????? " + "?????? ????",
                                "?????????????? ???????? ???? ???????? ?????????? ?????????? ????");
                        etName.setText("");
                        etNationalCode.setText("");
                        ll.removeViews(1, ll.getChildCount() - 1);
                    }
                });
    }

    private List<Employee> makeEmployeeListBundle() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i < ll.getChildCount(); i++) {
            View parent = ll.getChildAt(i);
            TextView name = parent.findViewById(R.id.etName);
            TextView nationalCode = parent.findViewById(R.id.etNationalCode);
            if(name.getText().toString().trim().length() == 0) continue;
            if(nationalCode.getText().toString().trim().length() < 10) continue;
            Employee newEmployee = new Employee();
            newEmployee.setName(name.getText().toString().trim());
            newEmployee.setNid(nationalCode.getText().toString().trim());
            employees.add(newEmployee);
        }
        return employees;
    }

    private ApiCallback apiWatcher() {
        return new ApiCallback() {
            @Override
            public void apiFailed(Object o) {
                loading.postValue(false);
                showAlerter(OneTimeAccessActivity.this, "???? ?????????? ?????????????? ?????????? ???? ??????", "???? ???????? ???????? VPN ?????????? ????????!");
            }

            @Override
            public void apiSucceeded(Object o) {
                loading.postValue(false);
                showAlerter(OneTimeAccessActivity.this, "?????????????? ???? ???????????? ?????????? ????", "");
                onBackPressed();
            }
        };
    }

    private void showValidationError() {
        showAlerter(this, "?????????? ???? ??????", "?????????? ?? ?????? ?????????????? ???? ???? ????????!");
    }

    private void findViews() {
        tvAddMore = findViewById(R.id.tvAddMore);
        ll = findViewById(R.id.ll);
        tvDate = findViewById(R.id.tvDate);
        tvSend = findViewById(R.id.tvSend);
        etName = findViewById(R.id.person1).findViewById(R.id.etName);
        etNationalCode = findViewById(R.id.person1).findViewById(R.id.etNationalCode);
    }

    private void showDatePicker() {
        new PersianDatePickerDialog(this)
                .setPositiveButtonString("??????")
                .setNegativeButton("????????????")
                .setTodayButton("??????????")
                .setTodayButtonVisible(true)
                .setMinYear(1400)
                .setMaxYear(1403)
                .setBackgroundColor(Color.LTGRAY)
                .setPickerBackgroundColor(Color.DKGRAY)
                .setInitDate(1401, 4, 1)
                .setActionTextColor(Color.BLACK)
                .setTypeFace(ResourcesCompat.getFont(this, R.font.vazir_regular))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        accessDate = String.valueOf(persianPickerDate.getTimestamp());
                        tvDate.setText(persianPickerDate.getPersianLongDate());
                    }

                    @Override
                    public void onDismissed() {

                    }
                }).show();
    }
}