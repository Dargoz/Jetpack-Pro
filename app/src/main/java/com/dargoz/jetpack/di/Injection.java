package com.dargoz.jetpack.di;

import android.app.Application;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.utils.Constants;
import static com.dargoz.jetpack.utils.Constants.Type.*;
import com.dargoz.jetpack.utils.RemoteDBHelper;

public class Injection {
    public static FilmRepository provideRepository(Application application,
                                                   Constants.Category category){
        RemoteRepository remoteRepository = RemoteRepository
                .getInstance(new RemoteDBHelper(
                        Constants.getUrlOf(
                                URL_TYPE_DISCOVER,
                                category,
                                "",
                                application.getApplicationContext()
                                )
                        )
                );
        return FilmRepository.getInstance(remoteRepository);
    }

    public static FilmRepository provideRepository(Application application, MovieEntity movieEntity,
                                                   Constants.Category category){

        RemoteRepository remoteRepository = RemoteRepository
                .getInstance(new RemoteDBHelper(
                        Constants.getUrlOf(
                                Constants.Type.URL_TYPE_DETAIL,
                                category,
                                String.valueOf(movieEntity.getId()),
                                application.getApplicationContext()
                            )
                        )
                );
        return FilmRepository.getInstance(remoteRepository);
    }
}
