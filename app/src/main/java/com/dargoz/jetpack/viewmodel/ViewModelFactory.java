package com.dargoz.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.di.Injection;
import com.dargoz.jetpack.ui.movie.MovieViewModel;

@SuppressWarnings("unchecked")
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final FilmRepository filmRepository;

    private ViewModelFactory(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    public static ViewModelFactory getInstance(Application application){
        if(INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MovieViewModel.class)){
            //noinspection unchecked
            return (T) new MovieViewModel(filmRepository);
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
