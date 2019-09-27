package com.dargoz.jetpack.ui.movie;

import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.MovieEntity;
import com.dargoz.jetpack.utils.DummyData;

import java.util.List;

public class MovieViewModel extends ViewModel {

    public List<MovieEntity> getMovieList(){
        return DummyData.generateMovieData();
    }
}
