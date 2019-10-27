package com.dargoz.jetpack.ui.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TvShowViewModel viewModel;
    private final FilmRepository filmRepository = mock(FilmRepository.class);
    private final ArrayList<TvShowEntity> dummyTvShowList = new ArrayList<>();
    private final MutableLiveData<List<TvShowEntity>> tvShowList = new MutableLiveData<>();
    @Before
    public void setUp() {
        viewModel = new TvShowViewModel(filmRepository);
    }

    @Test
    public void getTvShowList() {
        filmRepository.getAllTvShows(new FilmRepository.TvRepositoryListener() {
            @Override
            public void onSuccess(ArrayList<TvShowEntity> tvShowEntities) {
                dummyTvShowList.addAll(tvShowEntities);
                verifyData(tvShowEntities);
            }

            @Override
            public void onError() {

            }
        });
        viewModel.getTvShowList();
    }

    @SuppressWarnings("unchecked")
    private void verifyData(ArrayList<TvShowEntity> tvShowEntityArrayList){
        tvShowList.setValue(dummyTvShowList);
        when(tvShowEntityArrayList).then((Answer<?>) tvShowList);
        Observer<List<TvShowEntity>> observer = mock(Observer.class);
        viewModel.getTvShowList().observeForever(observer);
        verify(observer).onChanged(dummyTvShowList);
    }
}