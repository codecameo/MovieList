package com.example.hp.assessment.dbhelper.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hp.assessment.dbhelper.DbConstants;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity... movieEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieEntities(List<MovieEntity> movieEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long createMovieIfNotExists(MovieEntity subject);

    @Query("SELECT * FROM "+ DbConstants.MOVIE_TABLE_NAME+" ORDER BY vote_average DESC")
    Flowable<List<MovieEntity>> getAllMovies();

    /*@Query("SELECT * FROM "+ DbConstants.MOVIE_TABLE_NAME+" WHERE user_id = :id LIMIT 1")
    LiveData<MovieEntity> getSubject(int id);

    @Query("SELECT * FROM "+ DbConstants.MOVIE_TABLE_NAME+" WHERE user_id = :id LIMIT 1")
    MovieEntity getSubjectEntity(int id);*/

    @Update
    void updateMovie(MovieEntity... users);

}
