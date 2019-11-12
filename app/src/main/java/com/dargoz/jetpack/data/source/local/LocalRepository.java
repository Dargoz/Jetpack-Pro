package com.dargoz.jetpack.data.source.local;

import androidx.paging.DataSource;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.room.FilmDao;

import java.util.List;

public class LocalRepository {
    private static LocalRepository INSTANCE;
    private final FilmDao filmDao;

    private LocalRepository(FilmDao filmDao) {
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
        filmDao.insertMovie(movieEntity);
    }


}
