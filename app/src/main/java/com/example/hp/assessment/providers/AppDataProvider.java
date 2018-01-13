package com.example.hp.assessment.providers;

import android.content.Context;

import com.example.hp.assessment.data.DataManager;
import com.example.hp.assessment.data.DbHelper;
import com.example.hp.assessment.data.PreferenceHelper;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;
import com.example.hp.assessment.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class AppDataProvider implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferenceHelper mPreferencesHelper;

    @Inject
    public AppDataProvider(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferenceHelper preferencesHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Single<List<MovieEntity>> getMovieList() {
        return mDbHelper.getMovieList();
    }

    @Override
    public void insertMovieListEntity(List<MovieEntity> subjectEntities) {
        mDbHelper.insertMovieListEntity(subjectEntities);
    }

    @Override
    public int getLastPageNumber() {
        return mPreferencesHelper.getLastPageNumber();
    }

    @Override
    public void saveLastPageNumber(int page) {
        mPreferencesHelper.saveLastPageNumber(page);
    }
}
