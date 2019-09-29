package com.dargoz.jetpack.ui.movie;

import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.MovieEntity;
import com.dargoz.jetpack.utils.DummyData;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MovieViewModel extends ViewModel {
    private List<MovieEntity> movieEntities;

    void setMovieEntities(List<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }

    List<MovieEntity> getMovieList(){
        return movieEntities;
    }
}
