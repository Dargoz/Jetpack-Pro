package com.dargoz.jetpack.ui.favorite.tvshow;

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

public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView errorMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        errorMessage = view.findViewById(R.id.error_message_tv_show_fragment);
        ProgressBar progressBar = view.findViewById(R.id.tv_progress_bar);
        progressBar.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.tv_show_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            FavoriteTvShowViewModel viewModel = obtainViewModel(getActivity());
            TvShowPagedListAdapter adapter = new TvShowPagedListAdapter();
            recyclerView.setAdapter(adapter);
            viewModel.getTvShowPaged().observe(this, tvShowEntities -> {
                Log.d("DRG","movie entity observe");
                if(tvShowEntities != null) {
                    Log.d("DRG","movie entity size : " + tvShowEntities.size());
                    errorMessage.setVisibility(tvShowEntities.isEmpty() ? View.VISIBLE : View.GONE);
                    adapter.submitList(tvShowEntities);
                }
            });
        }
    }

    private static FavoriteTvShowViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication(), Constants.Category.URL_MOVIES);
        return ViewModelProviders.of(activity, factory).get(FavoriteTvShowViewModel.class);
    }
}
