package com.dargoz.jetpack.data;

import android.graphics.Bitmap;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.source.local.LocalRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.DummyData;
import com.dargoz.jetpack.utils.PagedListUtils;
import com.dargoz.jetpack.utils.RemoteDBHelper;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("SameReturnValue")
public class FilmRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private LocalRepository local = mock(LocalRepository.class);
    private final RemoteRepository remote = mock(RemoteRepository.class);
    private final FakeFilmRepository filmRepository = new FakeFilmRepository(local, remote);

    private final ArrayList<MovieResponse> movieResponses = DummyData.generateMovieResponse();
    private List<MovieEntity> movieEntities = DummyData.generateDummyMovieEntities();
    private final ArrayList<TvShowResponse> tvShowResponses = DummyData.generateTvShowResponse();
    private List<TvShowEntity> tvShowEntities = DummyData.generateDummyTvShowEntities();
    private final MovieEntity movieEntity = mock(MovieEntity.class);
    private final Bitmap imageBitmap = mock(Bitmap.class);
    private final JSONObject response = mock(JSONObject.class);

    private final FilmRepository.ImageRepositoryListener imageRepositoryListener =
            mock(FilmRepository.ImageRepositoryListener.class);
    private final FilmRepository.DetailsRepositoryListener detailsRepositoryListener =
            mock(FilmRepository.DetailsRepositoryListener.class);
    private final RemoteDBHelper.DetailsListener detailsListener =
            mock(RemoteDBHelper.DetailsListener.class);
    private final RemoteDBHelper.ImageResponseListener imageResponseListener =
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
    public void getFilmImage() {
        filmRepository.getFilmImage(movieEntity, imageRepositoryListener);
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

    @Test
    public void getFavoriteMovies() {
        androidx.paging.DataSource.Factory<Integer, MovieEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(local.getAllMovies()).thenReturn(dataSourceFactory);
        filmRepository.getFavoriteMovies();
        PagedList<MovieEntity>result = PagedListUtils.mockPagedList(movieEntities);

        verify(local).getAllMovies();
        assertNotNull(result);
        assertEquals(movieEntities.size(), result.size());
    }

    @Test
    public void insertMovie() {
    }

    @Test
    public void findMovieById() {
    }

    @Test
    public void deleteMovieFromFavorite() {
    }

    @Test
    public void getFavoriteTvShows() {
        androidx.paging.DataSource.Factory<Integer, TvShowEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(local.getAllTvShows()).thenReturn(dataSourceFactory);
        local.getAllTvShows();
        PagedList<TvShowEntity>result = PagedListUtils.mockPagedList(tvShowEntities);

        verify(local).getAllTvShows();
        assertNotNull(result);
        assertEquals(tvShowEntities.size(), result.size());
    }

    @Test
    public void insertTvShow() {
    }

    @Test
    public void findTvShowById() {
    }

    @Test
    public void deleteTvShowFromFavorite() {
    }
}