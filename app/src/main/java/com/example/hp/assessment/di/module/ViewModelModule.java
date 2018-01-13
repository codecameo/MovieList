package com.example.hp.assessment.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.hp.assessment.di.ViewModelKey;
import com.example.hp.assessment.viewmodels.MovieViewModel;
import com.example.hp.assessment.viewmodels.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    abstract ViewModel bindsQuestionSectionViewModel(MovieViewModel questionSectionViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
