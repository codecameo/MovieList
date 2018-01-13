package com.example.hp.assessment.modules;

import android.content.Context;

import com.example.hp.assessment.di.ApplicationContext;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class SharedPreference extends TrayPreferences {

    public static final String MODULE_NAME = "SharedPreference";
    public static final int VERSION = 1;

    public SharedPreference(@ApplicationContext Context context, String moduleName, int version) {
        super(context, moduleName, version);
    }
}

