package com.example.hp.assessment.converters;

import com.example.hp.assessment.databinding.viewmodels.MovieModel;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;
import com.example.hp.assessment.network.models.MovieApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md. Sifat-Ul Haque on 1/13/2018.
 */

public class MovieListConverter {

    public static List<MovieModel> getMovieModels(List<MovieEntity> movieEntities) {
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        for (MovieEntity movieModelEntity : movieEntities) {
            movieModels.add(getMovieModel(movieModelEntity));
        }
        return movieModels;
    }

    public static MovieModel getMovieModel(MovieEntity movieEntity) {
        MovieModel movieModel = new MovieModel();
        movieModel.setId(movieEntity.getId());
        movieModel.setVote_count(movieEntity.getVote_count());
        movieModel.setVote_average(movieEntity.getVote_average());
        movieModel.setPopularity(movieEntity.getPopularity());
        movieModel.setTitle(movieEntity.getTitle());
        movieModel.setOriginal_language(movieEntity.getOriginal_language());
        movieModel.setOriginal_title(movieEntity.getOriginal_title());
        movieModel.setOverview(movieEntity.getOverview());
        movieModel.setRelease_date(movieEntity.getRelease_date());
        movieModel.setPoster_path(movieEntity.getPoster_path());
        return movieModel;
    }

    public static List<MovieEntity> getMovieDbModels(ArrayList<MovieApiModel> movieApiModels){
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for (MovieApiModel movieApiModel : movieApiModels) {
            movieEntities.add(getMovieDbModel(movieApiModel));
        }
        return movieEntities;
    }

    private static MovieEntity getMovieDbModel(MovieApiModel movieApiModel) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieApiModel.getId());
        movieEntity.setAdult(movieApiModel.isAdult());
        movieEntity.setBackdrop_path(movieApiModel.getBackdrop_path());
        movieEntity.setOriginal_language(movieApiModel.getOriginal_language());
        movieEntity.setOverview(movieApiModel.getOverview());
        movieEntity.setPopularity(movieApiModel.getPopularity());
        movieEntity.setPoster_path(movieApiModel.getPoster_path());
        movieEntity.setTitle(movieApiModel.getTitle());
        movieEntity.setVideo(movieApiModel.isVideo());
        movieEntity.setVote_count(movieApiModel.getVote_count());
        movieEntity.setVote_average(movieApiModel.getVote_average());
        movieEntity.setOriginal_title(movieApiModel.getOriginal_title());
        movieEntity.setRelease_date(movieApiModel.getRelease_date());
        return movieEntity;
    }


}
