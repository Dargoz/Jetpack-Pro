package com.dargoz.jetpack.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

@Dao
public interface FilmDao {

    @WorkerThread
    @Query("SELECT * FROM movie")
    DataSource.Factory<Integer,MovieEntity> getMovies();

    @WorkerThread
    @Query("SELECT * FROM tvShow")
    DataSource.Factory<Integer,TvShowEntity> getTvShows();

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movies);

    @WorkerThread
    @Query("SELECT * FROM movie WHERE id = :id")
    MovieEntity findMovieById(int id);

    @Delete
    void deleteMovie(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTv(TvShowEntity tvShowEntity);

    @WorkerThread
    @Query("SELECT * FROM tvShow WHERE id = :id")
    TvShowEntity findTvShowById(int id);

    @Delete
    void deleteTvShow(TvShowEntity tvShowEntity);
}
