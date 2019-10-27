package com.dargoz.jetpack.data;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.data.FilmRepository.*;

@SuppressWarnings("unused")
interface DataSource {
    void getAllMovies(RepositoryListener movieResponseListener);
    void getAllTvShows(TvRepositoryListener tvRepositoryListener);
    void getMovieImage(MovieEntity movieEntity, ImageRepositoryListener imageRepositoryListener);
    void getFilmDetails(MovieEntity movieEntity, Constants.Category category, DetailsRepositoryListener listener);
}
