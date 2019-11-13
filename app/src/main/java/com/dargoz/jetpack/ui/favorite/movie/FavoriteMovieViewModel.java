package com.dargoz.jetpack.ui.favorite.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;


public class FavoriteMovieViewModel extends ViewModel {
    private FilmRepository filmRepository;

    public FavoriteMovieViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }



    LiveData<PagedList<MovieEntity>> getMoviePaged() {
        return filmRepository.getFavoriteMovies();
    }
}
