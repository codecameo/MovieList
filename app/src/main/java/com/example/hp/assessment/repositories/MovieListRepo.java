package com.example.hp.assessment.repositories;

import com.example.hp.assessment.converters.MovieListConverter;
import com.example.hp.assessment.data.DataManager;
import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;
import com.example.hp.assessment.network.ApiConstant;
import com.example.hp.assessment.network.interfaces.MovieApiService;
import com.example.hp.assessment.network.models.MovieListApiModel;
import com.example.hp.assessment.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public Single<List<MovieModel>> getInitMovieList() {
        return Observable
                .concat(getDbMovieList(), getApiMovieList(1).toObservable())
                .subscribeOn(Schedulers.io())
                .first(Collections.<MovieModel>emptyList())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<MovieModel>> getDbMovieList() {
        return mDataManager.getMovieList()
                .filter(new Predicate<List<MovieEntity>>() {
                    @Override
                    public boolean test(List<MovieEntity> movieEntities) throws Exception {
                        return !ListUtils.isEmpty(movieEntities);
                    }
                }).map(new Function<List<MovieEntity>, List<MovieModel>>() {
                    @Override
                    public List<MovieModel> apply(List<MovieEntity> movieEntities) throws Exception {
                        return MovieListConverter.getMovieModels(movieEntities);
                    }
                }).toObservable();
    }

    public Single<List<MovieModel>> getApiMovieList(int page) {
        return mApiService.getAllMovies(ApiConstant.API_KEY, ApiConstant.LANGUAGE, page)
                .map(new Function<MovieListApiModel, List<MovieModel>>() {
                @Override
                public List<MovieModel> apply(MovieListApiModel movieListApiModel) throws Exception {
                        List<MovieEntity> movieEntities = MovieListConverter.getMovieDbModels(movieListApiModel.getResults());
                        mDataManager.insertMovieListEntity(movieEntities);
                        return MovieListConverter.getMovieModels(movieEntities);
                    }
                });
    }

    public Observable<ArrayList<MovieModel>> searchMovie(String query) {
        return mApiService.searchMovies(ApiConstant.API_KEY, ApiConstant.LANGUAGE, query).map(new Function<MovieListApiModel, ArrayList<MovieModel>>() {
            @Override
            public ArrayList<MovieModel> apply(MovieListApiModel movieListApiModel) throws Exception {
                return (ArrayList<MovieModel>) MovieListConverter.getMovieModels(MovieListConverter.getMovieDbModels(movieListApiModel.getResults()));
            }
        });
    }
}
