package com.dargoz.jetpack.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;

import java.util.List;


public class MovieFragment extends Fragment {
    private View root;
    private MovieViewModel viewModel;
    private List<MovieEntity> movieEntities;
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
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieEntities = viewModel.getMovieList();
        RecyclerView movieRecyclerView = root.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }
}
