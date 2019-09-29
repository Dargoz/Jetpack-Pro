package com.dargoz.jetpack.ui.tvshow;


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
import com.dargoz.jetpack.data.TvShowEntity;
import com.dargoz.jetpack.utils.DummyData;

import java.util.List;

public class TvShowFragment extends Fragment {
    private View root;

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
        DummyData.prepareTvShowData(view.getContext());
        TvShowViewModel viewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        viewModel.setTvShowEntities(DummyData.generateTvShowData());
        List<TvShowEntity> tvShowEntities = viewModel.getTvShowList();
        RecyclerView recyclerView = root.findViewById(R.id.tv_show_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        TvShowRecyclerViewAdapter adapter = new TvShowRecyclerViewAdapter();
        adapter.setTvShowEntities(tvShowEntities);
        recyclerView.setAdapter(adapter);
    }
}
