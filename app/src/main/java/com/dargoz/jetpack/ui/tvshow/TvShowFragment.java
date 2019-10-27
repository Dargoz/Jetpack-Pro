package com.dargoz.jetpack.ui.tvshow;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.viewmodel.ViewModelFactory;

import java.util.List;

public class TvShowFragment extends Fragment {
    private View root;
    private RecyclerView recyclerView;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = root.findViewById(R.id.tv_show_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            TvShowViewModel viewModel = obtainViewModel(getActivity());
            viewModel.setTvShowEntities();
            viewModel.getTvShowList().observe(this, getTvShows);
        }
    }

    private static TvShowViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication(), Constants.Category.URL_TV);
        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
    }

    private Observer<List<TvShowEntity>> getTvShows = new Observer<List<TvShowEntity>>() {
        @Override
        public void onChanged(List<TvShowEntity> tvShowEntities) {
            TvShowRecyclerViewAdapter adapter = new TvShowRecyclerViewAdapter();
            adapter.setTvShowEntities(tvShowEntities);
            recyclerView.setAdapter(adapter);
        }
    };
}
