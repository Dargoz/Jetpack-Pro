package com.dargoz.jetpack.ui.detail;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailFimViewModelTest {
    private DetailFimViewModel viewModel;

    @Mock
    private Intent intent;

    private final FilmRepository filmRepository = mock(FilmRepository.class);
    private final MutableLiveData<Object> filmObject = new MutableLiveData<>();
    private MovieEntity movieEntity;
    private TvShowEntity tvShowEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new DetailFimViewModel(filmRepository);
        intent = mock(Intent.class);
        movieEntity = new MovieEntity(
                "Avengers: Infinity War",
                "super hero movie",
                "May 2018",
                "action, drama, super power",
                "135 minute",
                Double.parseDouble("8.5"),
                "Released",
                R.drawable.poster_avengerinfinity
        );
        tvShowEntity = new TvShowEntity(
                "Arrow",
                "super hero movie",
                "May 2018",
                "action, drama, super power",
                "35 minute",
                Double.parseDouble("8.5"),
                "Released",
                R.drawable.arrow    ,
                "22 Episodes"
        );
    }

    @Test
    public void isMovieEntity() {
        when(intent.getParcelableExtra(Constants.MOVIE_INTENT)).thenReturn(movieEntity);
        DetailFimViewModel.prepareData(intent);
        assertTrue(DetailFimViewModel.isMovieEntity());

        intent = mock(Intent.class);
        when(intent.getParcelableExtra(Constants.TV_SHOW_INTENT)).thenReturn(tvShowEntity);
        DetailFimViewModel.prepareData(intent);
        assertFalse(DetailFimViewModel.isMovieEntity());
    }

    @Test
    public void getMovieEntity() {
        when(intent.getParcelableExtra(Constants.MOVIE_INTENT)).thenReturn(movieEntity);
        DetailFimViewModel.prepareData(intent);
        assertEquals(movieEntity, viewModel.getMovieEntity());
    }

    @Test
    public void getTvShowEntity() {
        when(intent.getParcelableExtra(Constants.TV_SHOW_INTENT)).thenReturn(tvShowEntity);
        DetailFimViewModel.prepareData(intent);
        assertEquals(tvShowEntity, viewModel.getTvShowEntity());
    }

    @Test
    public void gerFilmDetails() {
        filmRepository.getFilmDetails(movieEntity, Constants.Category.URL_MOVIES, this::verifyData);
    }

    private void verifyData(Object object){
        filmObject.setValue(object);
        @SuppressWarnings("All")
        Observer<Object> observer = mock(Observer.class);
        viewModel.gerFilmDetails().observeForever(observer);
        verify(observer).onChanged(object);
    }
}