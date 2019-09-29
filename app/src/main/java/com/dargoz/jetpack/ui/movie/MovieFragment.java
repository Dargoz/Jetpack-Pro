package com.dargoz.jetpack.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;
import com.dargoz.jetpack.utils.DummyData;

import java.util.List;


public class MovieFragment extends Fragment {
    private View root;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        DummyData.prepareMovieData(view.getContext());
        viewModel.setMovieEntities(DummyData.generateMovieData());
        List<MovieEntity> movieEntities = viewModel.getMovieList();
        RecyclerView movieRecyclerView = root.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter();
        adapter.setMovieEntities(movieEntities);
        movieRecyclerView.setAdapter(adapter);
    }
}
