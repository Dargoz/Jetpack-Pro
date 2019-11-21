package com.dargoz.jetpack.ui.favorite.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteTvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final FilmRepository filmRepository = Mockito.mock(FilmRepository.class);
    private FavoriteTvShowViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new FavoriteTvShowViewModel(filmRepository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTvShowPaged() {
        MutableLiveData<PagedList<TvShowEntity>> dummyFavoriteTvShow = new MutableLiveData<>();
        PagedList<TvShowEntity> pagedList = mock(PagedList.class);

        dummyFavoriteTvShow.setValue(pagedList);
        when(filmRepository.getFavoriteTvShows()).thenReturn(dummyFavoriteTvShow);

        Observer<PagedList<TvShowEntity>> observer = mock(Observer.class);
        viewModel.getTvShowPaged().observeForever(observer);
        verify(observer).onChanged(pagedList);
    }
}