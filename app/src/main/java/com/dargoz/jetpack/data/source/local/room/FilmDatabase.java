package com.dargoz.jetpack.data.source.local.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

@Database(entities = {MovieEntity.class, TvShowEntity.class},
        version = 1,
        exportSchema = false)
public abstract class FilmDatabase extends RoomDatabase {

    private static final Object syncLock = new Object();
    private static FilmDatabase INSTANCE;

    public static FilmDatabase getInstance(Context context){
        synchronized (syncLock){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        FilmDatabase.class,
                        "Film.db").build();
            }
            return INSTANCE;
        }
    }

    public abstract FilmDao filmDao();
}
