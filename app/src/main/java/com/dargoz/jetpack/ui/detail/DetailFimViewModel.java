package com.dargoz.jetpack.ui.detail;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.utils.Constants;


public class DetailFimViewModel extends ViewModel implements FilmRepository.DetailsRepositoryListener {
    private final FilmRepository filmRepository;
    private final MutableLiveData<Object> filmObject = new MutableLiveData<>();
    private static boolean isMovieEntity;
    private static MovieEntity movieEntity;
    private static TvShowEntity tvShowEntity;

    public DetailFimViewModel(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    void setFilmDetails(MovieEntity movieEntity, Constants.Category category){
        filmRepository.getFilmDetails(movieEntity, category, this);
    }

    LiveData<Object> gerFilmDetails () {
        return filmObject;
    }

    static MovieEntity prepareData(Intent intent){
        if(intent.getParcelableExtra(Constants.MOVIE_INTENT) == null){
            tvShowEntity = intent.getParcelableExtra(Constants.TV_SHOW_INTENT);
            isMovieEntity = false;
            return  tvShowEntity;
        }else{
            movieEntity = intent.getParcelableExtra(Constants.MOVIE_INTENT);
            isMovieEntity = true;
            return movieEntity;
        }
    }

    void addToFavoriteMovieList(MovieEntity movieEntity) {
        filmRepository.insertMovie(movieEntity);
    }

    boolean isMovieInFavoriteList(MovieEntity movieEntity) {
        return filmRepository.findMovieById(movieEntity.getId()) != null;
    }

    void deleteMovieFromFavoriteList(MovieEntity movieEntity) {
        filmRepository.deleteMovieFromFavorite(movieEntity);
    }

    static boolean isMovieEntity() {
        return isMovieEntity;
    }

    MovieEntity getMovieEntity() {
        return movieEntity;
    }

    TvShowEntity getTvShowEntity() {
        return tvShowEntity;
    }

    @Override
    public void onDetailsDataResponse(Object object) {
        filmObject.setValue(object);
    }
}
