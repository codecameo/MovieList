package com.example.hp.assessment.providers;

import com.example.hp.assessment.data.DbHelper;
import com.example.hp.assessment.dbhelper.database.ProjectDB;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class DbProvider implements DbHelper {

    private final ProjectDB mAppDatabase;

    @Inject
    public DbProvider(ProjectDB appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Flowable<List<MovieEntity>> getMovieList() {
        return mAppDatabase.getMovieDao().getAllMovies();
    }

    @Override
    public void insertMovieListEntity(List<MovieEntity> movieEntities) {
        mAppDatabase.getMovieDao().insertMovieEntities(movieEntities);
    }
}
