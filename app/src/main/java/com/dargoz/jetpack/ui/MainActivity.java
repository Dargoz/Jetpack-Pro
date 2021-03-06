package com.dargoz.jetpack.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.ui.favorite.movie.FavoriteMovieFragment;
import com.dargoz.jetpack.ui.favorite.tvshow.FavoriteTvShowFragment;
import com.dargoz.jetpack.ui.movie.MovieFragment;
import com.dargoz.jetpack.ui.tvshow.TvShowFragment;
import com.dargoz.jetpack.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(getApplication());
        tabLayout = findViewById(R.id.main_tab_layout);

        viewPager = findViewById(R.id.main_view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
        adapter.addFragment(new MovieFragment(), getString(R.string.movie_tab_title));
        adapter.addFragment(new TvShowFragment(), getString(R.string.tv_show_tab_title));
        viewPager.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            onBottomNavItemSelected(menuItem);
            return false;
        });
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

    private void onBottomNavItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.action_nav_favorite) {
            setTitle(getResources().getString(R.string.favorite_title_text));
            ViewPagerAdapter favoriteAdapter =
                    new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
            favoriteAdapter.addFragment(new FavoriteMovieFragment(), getString(R.string.movie_tab_title));
                favoriteAdapter.addFragment(new FavoriteTvShowFragment(), getString(R.string.tv_show_tab_title));
            viewPager.setAdapter(favoriteAdapter);
        }else {
            setTitle(getResources().getString(R.string.app_name));
            ViewPagerAdapter homeAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
            homeAdapter.addFragment(new MovieFragment(), getString(R.string.movie_tab_title));
            homeAdapter.addFragment(new TvShowFragment(), getString(R.string.tv_show_tab_title));
            viewPager.setAdapter(homeAdapter);
        }
    }

}
