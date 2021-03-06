package com.dargoz.jetpack.ui.movie;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.utils.LiveDataTestUtils;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;


import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MovieViewModel viewModel;
    private final FilmRepository  filmRepository = mock(FilmRepository.class);
    private final ArrayList<MovieEntity> dummyMovieList = new ArrayList<>();
    private final MutableLiveData<List<MovieEntity>> movieList = new MutableLiveData<>();

    @Before
    public void setUp() {
        viewModel = new MovieViewModel(filmRepository);
    }

    @Test
    public void getMovieList() {

        filmRepository.getAllMovies(new FilmRepository.RepositoryListener() {
            @Override
            public void onSuccess(ArrayList<MovieEntity> movieEntities) {
                dummyMovieList.addAll(movieEntities);
                verifyMovieList(movieEntities);
            }

            @Override
            public void onError() {
            }
        });

    }

    @SuppressWarnings("unchecked")
    private void verifyMovieList(ArrayList<MovieEntity> movieEntityArrayList){
        movieList.setValue(dummyMovieList);
        when(movieEntityArrayList).then((Answer<?>) movieList);
        Observer<List<MovieEntity>> observer = mock(Observer.class);
        viewModel.getMovieList().observeForever(observer);
        verify(observer).onChanged(dummyMovieList);
        List<MovieEntity> result = LiveDataTestUtils.getValue(viewModel.getMovieList());
        assertNotNull(result);
    }
}