package com.example.hp.assessment.di.components;

import android.app.Application;

import com.example.hp.assessment.ProjectApplication;
import com.example.hp.assessment.di.builder.ActivityBuilderModule;
import com.example.hp.assessment.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(ProjectApplication app);
}
