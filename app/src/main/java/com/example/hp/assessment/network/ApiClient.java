package com.example.hp.assessment.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.hp.assessment.network.ApiConstant.MULTIPART_FORM_DATA;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */
@Singleton
public class ApiClient {

    public static final String BASE_DATA_URL = "https://api.themoviedb.org/";
    private static final String URL_EXTENSION = "3/";
    private Retrofit mRetrofitDataClient = null;
    private Gson mGson;

    @Inject
    public ApiClient() {
        mGson = setGson();
        mRetrofitDataClient = setDataClient();
    }

    private Gson setGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }


    public Retrofit setDataClient() {
        if (mRetrofitDataClient == null) {

            mRetrofitDataClient = new Retrofit.Builder()
                    .baseUrl(BASE_DATA_URL + URL_EXTENSION)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofitDataClient;
    }

    public Gson getGson() {
        return mGson;
    }

    public Retrofit getRetrofitDataClient() {
        return mRetrofitDataClient;
    }

    public static RequestBody createRequestBody(@NonNull String data) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), data);
    }
}
