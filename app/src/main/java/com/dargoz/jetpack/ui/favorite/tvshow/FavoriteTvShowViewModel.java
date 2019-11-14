package com.dargoz.jetpack.ui.favorite.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;


public class FavoriteTvShowViewModel extends ViewModel {
    private FilmRepository filmRepository;

    public FavoriteTvShowViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }



    LiveData<PagedList<TvShowEntity>> getTvShowPaged() {
        return filmRepository.getFavoriteTvShows();
    }
}
