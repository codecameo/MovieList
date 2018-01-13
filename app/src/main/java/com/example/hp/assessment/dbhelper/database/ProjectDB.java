package com.example.hp.assessment.dbhelper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.hp.assessment.dbhelper.dao.MovieDao;
import com.example.hp.assessment.dbhelper.entities.MovieEntity;

import javax.inject.Singleton;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Singleton
@Database(entities = {MovieEntity.class},
        version = 1, exportSchema = false)
public abstract class ProjectDB extends RoomDatabase {
    abstract public MovieDao getMovieDao();
}
