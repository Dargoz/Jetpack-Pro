package com.dargoz.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.di.Injection;
import com.dargoz.jetpack.ui.detail.DetailFimViewModel;
import com.dargoz.jetpack.ui.favorite.movie.FavoriteMovieViewModel;
import com.dargoz.jetpack.ui.favorite.tvshow.FavoriteTvShowViewModel;
import com.dargoz.jetpack.ui.movie.MovieViewModel;
import com.dargoz.jetpack.ui.tvshow.TvShowViewModel;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.RemoteDBHelper;

import static com.dargoz.jetpack.utils.Constants.Type.URL_TYPE_DETAIL;
import static com.dargoz.jetpack.utils.Constants.Type.URL_TYPE_DISCOVER;

@SuppressWarnings("unchecked")
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final FilmRepository filmRepository;

    private ViewModelFactory(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    public static ViewModelFactory getInstance(Application application, Constants.Category category){
        if(INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application,category));
                }
            }
        }else{
            INSTANCE.filmRepository.setRemoteRepository(
                    RemoteRepository.getInstance(
                            new RemoteDBHelper(Constants.getUrlOf(
                                    URL_TYPE_DISCOVER,
                                    category,
                                    "",
                                    application.getApplicationContext()
                            ))
                    )
            );
        }
        return INSTANCE;
    }

    public static ViewModelFactory getInstance(Application application, MovieEntity movieEntity,
                                               Constants.Category category){
        if(INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application,
                            movieEntity, category));
                }
            }
        }else{
            INSTANCE.filmRepository.setRemoteRepository(
                    RemoteRepository.getInstance(
                            new RemoteDBHelper(Constants.getUrlOf(
                                    URL_TYPE_DETAIL,
                                    category,
                                    String.valueOf(movieEntity.getId()),
                                    application.getApplicationContext()
                            ))
                    )
            );
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MovieViewModel.class)){
            //noinspection unchecked
            return (T) new MovieViewModel(filmRepository);
        }else if(modelClass.isAssignableFrom(TvShowViewModel.class)){
            //noinspection unchecked
            return (T) new TvShowViewModel(filmRepository);
        }else if (modelClass.isAssignableFrom(DetailFimViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailFimViewModel(filmRepository);
        }else if(modelClass.isAssignableFrom(FavoriteMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteMovieViewModel(filmRepository);
        }else if(modelClass.isAssignableFrom(FavoriteTvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteTvShowViewModel(filmRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
