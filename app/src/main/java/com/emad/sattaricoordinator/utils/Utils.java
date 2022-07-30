package com.emad.sattaricoordinator.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import androidx.core.content.res.ResourcesCompat;

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.view.OneTimeAccessActivity;
import com.tapadoo.alerter.Alerter;

public class Utils {
    public static void showAlerter(Activity context, String title, String message){
        Alerter.create(context)
                .setDuration(3500)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorInt(Color.parseColor("#bb000000"))
                .setContentGravity(Gravity.RIGHT)
                .setTextTypeface(ResourcesCompat.getFont(context, R.font.vazir_regular))
                .setTitleTypeface(ResourcesCompat.getFont(context, R.font.vazir_regular))
                .show();
    }
}
