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

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.repository.remote.RemoteDataManager;
import com.emad.sattaricoordinator.repository.remote.TelegramRepository;
import com.tapadoo.alerter.Alerter;

import org.jetbrains.annotations.NotNull;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;

public class OneTimeAccessActivity extends AppCompatActivity {

    private String accessDate;

    private TextView tvDate;
    private TextView tvSend;
    private EditText etDescription;

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
            if (accessDate != null && etDescription.getText().toString().trim().length() > 0){
                loading.postValue(true);
                String text = "درخواست ورود یکروزه برای تاریخ: " + accessDate + "\n"
                        + etDescription.getText().toString().trim();
                RemoteDataManager.getTelegramRepository().sendMessage(text, apiWatcher());
            } else showValidationError();
        });
        tvDate.setOnClickListener(view -> showDatePicker());
    }

    private TelegramRepository.ApiCallback apiWatcher() {
        return new TelegramRepository.ApiCallback() {
            @Override
            public void apiFailed() {
                loading.postValue(false);
                showAlerter(OneTimeAccessActivity.this, "در ارسال درخواست خطایی رخ داد", "از روشن بودن VPN مطمئن شوید!");
            }

            @Override
            public void apiSucceeded() {
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
        etDescription = findViewById(R.id.etDescription);
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
                        accessDate = persianPickerDate.getPersianLongDate();
                        tvDate.setText(accessDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                }).show();
    }
}