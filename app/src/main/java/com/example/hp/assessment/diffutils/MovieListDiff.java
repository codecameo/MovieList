package com.example.hp.assessment.diffutils;

import com.example.hp.assessment.databinding.viewmodels.MovieModel;

import java.util.List;

/**
 * Created by Md. Sifat-Ul Haque on 12/25/2017.
 */
public class MovieListDiff extends BaseDiff<MovieModel> {

    public MovieListDiff(List<MovieModel> oldMovieList, List<MovieModel> newMovieList) {
        super(oldMovieList, newMovieList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == mNewList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == mNewList.get(newItemPosition).getId();
    }
}
