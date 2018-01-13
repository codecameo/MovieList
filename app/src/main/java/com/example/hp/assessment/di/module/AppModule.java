package com.example.hp.assessment.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.hp.assessment.data.DataManager;
import com.example.hp.assessment.data.DbHelper;
import com.example.hp.assessment.data.PreferenceHelper;
import com.example.hp.assessment.dbhelper.DbConstants;
import com.example.hp.assessment.dbhelper.database.ProjectDB;
import com.example.hp.assessment.di.ApplicationContext;
import com.example.hp.assessment.di.DatabaseInfo;
import com.example.hp.assessment.di.PreferenceInfo;
import com.example.hp.assessment.modules.SharedPreference;
import com.example.hp.assessment.network.ApiClient;
import com.example.hp.assessment.network.interfaces.MovieApiService;
import com.example.hp.assessment.providers.AppDataProvider;
import com.example.hp.assessment.providers.DbProvider;
import com.example.hp.assessment.providers.PreferenceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Module(includes = ViewModelModule.class)
public class AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return DbConstants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    String provideSharedPrefName(){return SharedPreference.MODULE_NAME;}

    @Provides
    @PreferenceInfo
    int provideSharedPrefVersion(){return SharedPreference.VERSION;}

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataProvider appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbProvider appDbProvider) {
        return appDbProvider;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferenceProvider appPreferencesProvider) {
        return appPreferencesProvider;
    }

    @Provides
    @Singleton
    MovieApiService apiServiceProvider(ApiClient apiClient){
        return apiClient.getRetrofitDataClient().create(MovieApiService.class);
    }

    @Provides
    @Singleton
    ProjectDB provideAppDatabase(@DatabaseInfo String dbName, @ApplicationContext Context context) {
        return Room.databaseBuilder(context, ProjectDB.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    SharedPreference provideSharedPref(@ApplicationContext Context context,
                                       @PreferenceInfo String moduleName,
                                       @PreferenceInfo int version){
        return new SharedPreference(context, moduleName, version);
    }
}
