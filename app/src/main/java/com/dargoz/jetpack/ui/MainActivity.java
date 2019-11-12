package com.dargoz.jetpack.ui;

import android.os.Bundle;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.ui.movie.MovieFragment;
import com.dargoz.jetpack.ui.tvshow.TvShowFragment;
import com.dargoz.jetpack.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(getApplication());
        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        final ViewPager viewPager = findViewById(R.id.main_view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
        adapter.addFragment(new MovieFragment(), getString(R.string.movie_tab_title));
        adapter.addFragment(new TvShowFragment(), getString(R.string.tv_show_tab_title));
        viewPager.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
