package com.example.hp.assessment.repositories;

import com.example.hp.assessment.data.DataManager;
import com.example.hp.assessment.network.interfaces.MovieApiService;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

public class BaseRepo {
    protected DataManager mDataManager;
    protected MovieApiService mApiService;

    public BaseRepo(DataManager dataManager, MovieApiService apiService){
        mDataManager = dataManager;
        mApiService = apiService;
    }

}
