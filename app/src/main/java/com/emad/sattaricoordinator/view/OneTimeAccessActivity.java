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
import android.view.View;
import android.widget.EditText;
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
            tvSend.setText(show ? "لطفا منتظر بمانید" : "ارسال درخواست");
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
    }

    private void sendRequest() {
        String token = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE).getString("token", "");
        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setStartDate(String.valueOf(accessDate));
        requestModel.setEndDate(String.valueOf(accessDate));
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setName(etName.getText().toString().trim());
        employee.setNid(etNationalCode.getText().toString().trim());
        employeeList.add(employee);
        requestModel.setEmployees(employeeList);
        loading.postValue(true);
        RemoteDataManager.getKheyratiRepository()
                .createRequest("Bearer " + token, requestModel, new ApiCallback() {
                    @Override
                    public void apiFailed(Object o) {
                        loading.postValue(false);
                        Utils.showAlerter(
                                OneTimeAccessActivity.this,
                                "خطا در ارسال درخواست مجوز",
                                ((Throwable) o).getMessage());
                    }

                    @Override
                    public void apiSucceeded(Object o) {
                        loading.postValue(false);
                        Utils.showAlerter(
                                OneTimeAccessActivity.this,
                                "ثبت شد",
                                "درخواست مجوز به زودی بررسی خواهد شد");
                        etName.setText("");
                        etNationalCode.setText("");
                    }
                });
    }

    private ApiCallback apiWatcher() {
        return new ApiCallback() {
            @Override
            public void apiFailed(Object o) {
                loading.postValue(false);
                showAlerter(OneTimeAccessActivity.this, "در ارسال درخواست خطایی رخ داد", "از روشن بودن VPN مطمئن شوید!");
            }

            @Override
            public void apiSucceeded(Object o) {
                loading.postValue(false);
                showAlerter(OneTimeAccessActivity.this, "درخواست با موفقیت ارسال شد", "");
                onBackPressed();
            }
        };
    }

    private void showValidationError() {
        showAlerter(this, "خطایی رخ داد", "تاریخ و متن درخواست را پر کنید!");
    }

    private void findViews() {
        tvDate = findViewById(R.id.tvDate);
        tvSend = findViewById(R.id.tvSend);
        etName = findViewById(R.id.etName);
        etNationalCode = findViewById(R.id.etNationalCode);
    }

    private void showDatePicker() {
        new PersianDatePickerDialog(this)
                .setPositiveButtonString("ثبت")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
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