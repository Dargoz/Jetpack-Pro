package com.dargoz.jetpack.data.source.local;


import androidx.paging.DataSource;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.local.room.FilmDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LocalRepository {
    private static LocalRepository INSTANCE;
    private final FilmDao filmDao;
    private ExecutorService executorService;

    private LocalRepository(FilmDao filmDao) {
        executorService = Executors.newSingleThreadExecutor();
        this.filmDao = filmDao;
    }

    public static LocalRepository getInstance(FilmDao filmDao) {

        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(filmDao);
        }
        return INSTANCE;
    }

    public DataSource.Factory<Integer, MovieEntity> getAllMovies() {
        return filmDao.getMovies();
    }

    public void insertMovie(MovieEntity movieEntity) {
        executorService.execute(() -> filmDao.insertMovie(movieEntity));
    }

    public MovieEntity findMovieById(int id) {
        return filmDao.findMovieById(id);
    }

    public void deleteMovie(MovieEntity movieEntity) {
        executorService.execute(() -> filmDao.deleteMovie(movieEntity));
    }

    public DataSource.Factory<Integer, TvShowEntity> getAllTvShows() {
        return filmDao.getTvShows();
    }

    public void insertTvShow(TvShowEntity tvShowEntity) {
        executorService.execute(() -> filmDao.insertTv(tvShowEntity));
    }

    public TvShowEntity findTvShowById(int id) {
        return filmDao.findTvShowById(id);
    }

    public void deleteTvShow(TvShowEntity tvShowEntitiy) {
        executorService.execute(() -> filmDao.deleteTvShow(tvShowEntitiy));
    }
}
