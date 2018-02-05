package com.example.hp.assessment.viewmodels;

import android.util.Log;

import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.repositories.MovieListRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class MovieViewModel extends BaseViewModel {
    private static final String TAG = "MovieViewModel";
    private MovieListRepo mMovieRepo;
    private int mCurrentPage = 1;

    @Inject
    public MovieViewModel(MovieListRepo movieListRepo) {
        Log.d(TAG, "MovieViewModel: Constructor");
        this.mMovieRepo = movieListRepo;
    }

    /*
    * Fetch Movie list from repo
    * */
    public Flowable<List<MovieModel>> getInitMovieList() {
        return mMovieRepo.getInitMovieList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable loadMoreMovieList() {
        return mMovieRepo.getApiMovieList(mCurrentPage);
    }

    public void incrementCurrentPage() {
        mCurrentPage++;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public Observable<ArrayList<MovieModel>> searchMovie(CharSequence query) {
        return mMovieRepo.searchMovie((String) query);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }
}
