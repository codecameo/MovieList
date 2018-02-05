package com.example.hp.assessment.repositories;

import android.util.Log;

import com.example.hp.assessment.converters.MovieListConverter;
import com.example.hp.assessment.data.DataManager;
import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;
import com.example.hp.assessment.network.ApiConstant;
import com.example.hp.assessment.network.interfaces.MovieApiService;
import com.example.hp.assessment.network.models.MovieListApiModel;
import com.example.hp.assessment.utils.ListUtils;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class MovieListRepo extends BaseRepo {

    private static final String TAG = "MovieListRepo";

    @Inject
    public MovieListRepo(DataManager dataManager, MovieApiService apiService) {
        super(dataManager, apiService);
    }

    public Flowable<List<MovieModel>> getInitMovieList() {
        Log.d(TAG, "getInitMovieList: "+Thread.currentThread().getName());
        return getDbMovieList()
                .doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                Log.d(TAG, "accept: "+Thread.currentThread().getName());
                getApiMovieList(1)
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }
        });
    }

    public Flowable<List<MovieModel>> getDbMovieList() {
        Log.d(TAG, "getDbMovieList: "+Thread.currentThread().getName());
        return mDataManager.getMovieList()
                .filter(new Predicate<List<MovieEntity>>() {
                    @Override
                    public boolean test(List<MovieEntity> movieEntities) throws Exception {
                        return !ListUtils.isEmpty(movieEntities);
                    }
                }).map(new Function<List<MovieEntity>, List<MovieModel>>() {
                    @Override
                    public List<MovieModel> apply(List<MovieEntity> movieEntities) throws Exception {
                        Log.d(TAG, "getDbMovieList: Map "+Thread.currentThread().getName());
                        return MovieListConverter.getMovieModels(movieEntities);
                    }
                });
    }

    public Completable getApiMovieList(int page) {
        Log.d(TAG, "getApiMovieList: Page -> "+page);
         return mApiService.getAllMovies(ApiConstant.API_KEY, ApiConstant.LANGUAGE, page)
                .map(new Function<MovieListApiModel, List<MovieModel>>() {
                    @Override
                    public List<MovieModel> apply(MovieListApiModel movieListApiModel) throws Exception {
                        List<MovieEntity> movieEntities = MovieListConverter.getMovieDbModels(movieListApiModel.getResults());
                        Log.d(TAG, "getApiMovieList apply: "+movieEntities.size());
                        mDataManager.insertMovieListEntity(movieEntities);
                        return MovieListConverter.getMovieModels(movieEntities);
                    }
                }).toCompletable();
    }

    public Observable<ArrayList<MovieModel>> searchMovie(String query) {
        return mApiService.searchMovies(ApiConstant.API_KEY, ApiConstant.LANGUAGE, query)
                .map(new Function<MovieListApiModel, ArrayList<MovieModel>>() {
                    @Override
                    public ArrayList<MovieModel> apply(MovieListApiModel movieListApiModel) throws Exception {
                        return (ArrayList<MovieModel>) MovieListConverter.getMovieModels(MovieListConverter.getMovieDbModels(movieListApiModel.getResults()));
                    }
                });
    }
}
