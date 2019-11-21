package com.dargoz.jetpack.ui.favorite.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private TextView errorMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        errorMessage = view.findViewById(R.id.error_message_movie_fragment);
        ProgressBar progressBar = view.findViewById(R.id.movie_progress_bar);
        progressBar.setVisibility(View.GONE);
        movieRecyclerView = view.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            FavoriteMovieViewModel viewModel = obtainViewModel(getActivity());
            MoviePagedListAdapter adapter = new MoviePagedListAdapter();
            movieRecyclerView.setAdapter(adapter);
            viewModel.getMoviePaged().observe(this, movieEntities -> {
                Log.d("DRG","movie entity observe");
                if(movieEntities != null) {
                    Log.d("DRG","movie entity size : " + movieEntities.size());
                    errorMessage.setVisibility(movieEntities.isEmpty() ? View.VISIBLE : View.GONE);
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
