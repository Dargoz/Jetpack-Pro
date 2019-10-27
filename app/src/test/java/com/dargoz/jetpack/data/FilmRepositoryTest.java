package com.dargoz.jetpack.data;

import android.graphics.Bitmap;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.DummyData;
import com.dargoz.jetpack.utils.RemoteDBHelper;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FilmRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remote = mock(RemoteRepository.class);
    private FakeFilmRepository filmRepository = new FakeFilmRepository(remote);

    private ArrayList<MovieResponse> movieResponses = DummyData.generateMovieResponse();
    private ArrayList<TvShowResponse> tvShowResponses = DummyData.generateTvShowResponse();
    private MovieEntity movieEntity = mock(MovieEntity.class);
    private Bitmap imageBitmap = mock(Bitmap.class);
    private JSONObject response = mock(JSONObject.class);

    private FilmRepository.ImageRepositoryListener imageRepositoryListener =
            mock(FilmRepository.ImageRepositoryListener.class);
    private FilmRepository.DetailsRepositoryListener detailsRepositoryListener =
            mock(FilmRepository.DetailsRepositoryListener.class);
    private RemoteDBHelper.DetailsListener detailsListener =
            mock(RemoteDBHelper.DetailsListener.class);
    private RemoteDBHelper.ImageResponseListener imageResponseListener =
            mock(RemoteDBHelper.ImageResponseListener.class);


    @Before
    public void setUp() {
        filmRepository.setImageResponseListener(imageResponseListener);
        filmRepository.setDetailsListener(detailsListener);
    }

    @Test
    public void getAllMovies() {

        filmRepository.getAllMovies(any(FilmRepository.RepositoryListener.class));
        doAnswer(invocation -> {
            ((FakeFilmRepository) invocation.getArguments()[0])
                    .onResponse(movieResponses);
            return null;
        }).when(remote).getAllMovies(any(RemoteDBHelper.ResponseListener.class));

        verify(remote, times(1))
                .getAllMovies(any(RemoteDBHelper.ResponseListener.class));
    }

    @Test
    public void getAllTvShows() {
        filmRepository.getAllTvShows(any(FilmRepository.TvRepositoryListener.class));
        doAnswer(invocation -> {
            ((FakeFilmRepository) invocation.getArguments()[0])
                    .onTvResponse(tvShowResponses);
            return null;
        }).when(remote).getAllTvShows(any(RemoteDBHelper.TvResponseListener.class));
        verify(remote, times(1))
                .getAllTvShows(any(RemoteDBHelper.TvResponseListener.class));
    }

    @Test
    public void getMovieImage() {
        filmRepository.getMovieImage(movieEntity, imageRepositoryListener);
        doAnswer(invocation -> {
            ((FakeFilmRepository) invocation.getArguments()[0])
                    .onImageResponse(movieEntity, imageBitmap);
            return null;
        }).when(remote).getImage(movieEntity, imageResponseListener);
        verify(remote, times(1))
                .getImage(movieEntity,  imageResponseListener);
    }

    @Test
    public void getFilmDetails() {
        filmRepository.getFilmDetails(
                movieEntity,
                Constants.Category.URL_MOVIES,
                detailsRepositoryListener
        );
        doAnswer(invocation -> {
            ((FakeFilmRepository) invocation.getArguments()[0])
                    .onDetailResponse(movieEntity, Constants.Category.URL_MOVIES, response);
            return null;
        }).when(remote).getDetails(movieEntity, Constants.Category.URL_MOVIES, detailsListener);
        verify(remote, times(1))
                .getDetails(movieEntity, Constants.Category.URL_MOVIES, detailsListener);
    }

}