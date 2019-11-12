package com.dargoz.jetpack.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.viewmodel.ViewModelFactory;


public class FavoriteMovieFragment extends Fragment {
    private RecyclerView movieRecyclerView;
    private Button reloadButton;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadButton = view.findViewById(R.id.movie_reload_button);
        progressBar = view.findViewById(R.id.movie_progress_bar);
        movieRecyclerView = view.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            FavoriteMovieViewModel viewModel = obtainViewModel(getActivity());
            MoviePagedListAdapter adapter = new MoviePagedListAdapter();

            viewModel.getMoviePaged().observe(this, movieEntities -> {
                if(movieEntities != null) {
                    adapter.submitList(movieEntities);
                }
            });
        }
    }

    private static FavoriteMovieViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication(), Constants.Category.URL_MOVIES);
        return ViewModelProviders.of(activity, factory).get(FavoriteMovieViewModel.class);
    }
}
