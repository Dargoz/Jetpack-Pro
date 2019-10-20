package com.dargoz.jetpack.data;


public interface DataSource {
    void getAllMovies(FilmRepository.RepositoryListener movieResponseListener);
}
