package com.dargoz.jetpack.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

import java.util.List;

@Dao
public interface FilmDao {

    @WorkerThread
    @Query("SELECT * FROM movie")
    DataSource.Factory<Integer,MovieEntity> getMovies();

    @WorkerThread
    @Query("SELECT * FROM tvShow")
    LiveData<List<MovieEntity>> getTvShows();

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movies);

    @WorkerThread
    @Query("SELECT * FROM movie WHERE id = :id")
    MovieEntity findMovieById(int id);

    @Delete
    void deleteMovie(MovieEntity movieEntity);
}
