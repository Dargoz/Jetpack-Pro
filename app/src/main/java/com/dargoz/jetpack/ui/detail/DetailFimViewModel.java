package com.dargoz.jetpack.ui.detail;

import android.content.Intent;
import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.utils.Constants;


@SuppressWarnings("WeakerAccess")
public class DetailFimViewModel extends ViewModel {
    private boolean isMovieEntity;
    private MovieEntity movieEntity;
    private TvShowEntity tvShowEntity;

    void prepareData(Intent intent){
        if(intent.getParcelableExtra(Constants.MOVIE_INTENT) == null){
            tvShowEntity = intent.getParcelableExtra(Constants.TV_SHOW_INTENT);
            isMovieEntity = false;
        }else{
            movieEntity = intent.getParcelableExtra(Constants.MOVIE_INTENT);
            isMovieEntity = true;
        }
    }

    boolean isMovieEntity() {
        return isMovieEntity;
    }

    MovieEntity getMovieEntity() {
        return movieEntity;
    }

    TvShowEntity getTvShowEntity() {
        return tvShowEntity;
    }
}
