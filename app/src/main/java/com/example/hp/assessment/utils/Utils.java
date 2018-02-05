package com.example.hp.assessment.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Md. Sifat-Ul Haque on 1/30/2018.
 */

public class Utils {
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
