package com.example.hp.assessment.databinding.adapters;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import io.reactivex.annotations.Nullable;

/**
 * Created by HP on 10/17/2017.
 */

public class TextBindAdapter {

    private static final String TAG = "TextBindAdapter";

    @BindingAdapter("long_date")
    public static void StringToDate(TextView tvTime, @Nullable String time) {
        if (TextUtils.isEmpty(time)) return;
        try {
            long millisecond = Long.parseLong(time);
            String dateString = DateFormat.format("dd MMMM, yyyy", new Date(millisecond)).toString();
            tvTime.setText(dateString);
        } catch (NumberFormatException ex) {

        }
    }

}
