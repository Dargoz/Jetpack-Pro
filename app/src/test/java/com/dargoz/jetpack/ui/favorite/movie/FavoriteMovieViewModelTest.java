package com.dargoz.jetpack.ui.favorite.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteMovieViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FilmRepository filmRepository = Mockito.mock(FilmRepository.class);
    private FavoriteMovieViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new FavoriteMovieViewModel(filmRepository);
    }

    @Test
    public void getMoviePaged() {
        MutableLiveData<PagedList<MovieEntity>> dummyFavoriteMovie = new MutableLiveData<>();
        PagedList<MovieEntity> pagedList = mock(PagedList.class);

        dummyFavoriteMovie.setValue(pagedList);
        when(filmRepository.getFavoriteMovies()).thenReturn(dummyFavoriteMovie);

        Observer<PagedList<MovieEntity>> observer = mock(Observer.class);
        viewModel.getMoviePaged().observeForever(observer);
        verify(observer).onChanged(pagedList);
    }
}