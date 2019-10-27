package com.dargoz.jetpack.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dargoz.jetpack.EspressoIdlingResource;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

import static com.dargoz.jetpack.utils.Constants.Category.*;
import com.dargoz.jetpack.viewmodel.ViewModelFactory;

import java.util.List;


public class MovieFragment extends Fragment implements MovieViewModel.ErrorListener {
    private View root;
    private RecyclerView movieRecyclerView;
    private Button reloadButton;
    private ProgressBar progressBar;

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
        reloadButton = root.findViewById(R.id.movie_reload_button);
        progressBar = root.findViewById(R.id.movie_progress_bar);
        movieRecyclerView = root.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EspressoIdlingResource.increment();
        if(getActivity() != null){
            MovieViewModel viewModel = obtainViewModel(getActivity());
            viewModel.setErrorCallbackListener(this);
            viewModel.setMovieEntities();
            viewModel.getMovieList().observe(this, getMovies);
            reloadButton.setOnClickListener(viewButton -> {
                progressBar.setVisibility(View.VISIBLE);
                reloadButton.setVisibility(View.GONE);
                obtainViewModel(getActivity());
                viewModel.setMovieEntities();
            });
        }
    }

    private static MovieViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication(), URL_MOVIES);
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

    private final Observer<List<MovieEntity>> getMovies = new Observer<List<MovieEntity>>() {
        @Override
        public void onChanged(List<MovieEntity> movieEntities) {
            Log.i("DRG","get info");
            progressBar.setVisibility(View.GONE);
            MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter();
            adapter.setMovieEntities(movieEntities);
            movieRecyclerView.setAdapter(adapter);

        }
    };

    @Override
    public void onResponseError() {
        reloadButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Log.d("DRG","error get movie list");
    }
}
