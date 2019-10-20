package com.dargoz.jetpack.data;


import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

public interface DataSource {
    void getAllMovies(FilmRepository.RepositoryListener movieResponseListener);
    void getMovieImage(MovieEntity movieEntity, FilmRepository.ImageRepositoryListener imageRepositoryListener);
}
