package com.example.hp.assessment.providers;

import com.example.hp.assessment.data.PreferenceHelper;
import com.example.hp.assessment.modules.SharedPreference;

import javax.inject.Inject;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class PreferenceProvider implements PreferenceHelper {

    private static final String KEY_LAST_PAGE_NUMBER = "key_last_page_number";
    private final SharedPreference mSharedPreference;

    @Inject
    public PreferenceProvider(SharedPreference sharedPreference) {
        this.mSharedPreference = sharedPreference;
    }


    @Override
    public int getLastPageNumber() {
        return mSharedPreference.getInt(KEY_LAST_PAGE_NUMBER, 1);
    }

    @Override
    public void saveLastPageNumber(int page) {
        mSharedPreference.put(KEY_LAST_PAGE_NUMBER, page);
    }
}
