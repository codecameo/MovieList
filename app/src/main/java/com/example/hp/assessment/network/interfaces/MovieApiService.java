package com.example.hp.assessment.network.interfaces;

import com.example.hp.assessment.network.models.MovieApiModel;
import com.example.hp.assessment.network.models.MovieListApiModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Md. Sifat-Ul Haque on 12/23/2017.
 */

public interface MovieApiService {
    @GET("movie/top_rated")
    Single<MovieListApiModel> getAllMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("search/movie")
    Observable<MovieListApiModel> searchMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

}
