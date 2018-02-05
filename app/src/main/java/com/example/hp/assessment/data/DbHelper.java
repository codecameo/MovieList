package com.example.hp.assessment.data;

import com.example.hp.assessment.dbhelper.entities.MovieEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public interface DbHelper {
    Flowable<List<MovieEntity>> getMovieList();
    void insertMovieListEntity(List<MovieEntity> subjectEntities);
}
