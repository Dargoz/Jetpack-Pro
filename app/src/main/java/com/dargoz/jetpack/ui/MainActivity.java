package com.dargoz.jetpack.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.ui.favorite.movie.FavoriteMovieFragment;
import com.dargoz.jetpack.ui.movie.MovieFragment;
import com.dargoz.jetpack.ui.tvshow.TvShowFragment;
import com.dargoz.jetpack.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    TabLayout tabLayout;

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

        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
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
            menuItem.setIcon(R.drawable.baseline_collections_bookmark_white_24);
            bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.baseline_home_black_24);
//            bottomNavigationView.setItemTextColor();
            setTitle(getResources().getString(R.string.favorite_title_text));
            ViewPagerAdapter favoriteAdapter =
                    new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
            favoriteAdapter.addFragment(new FavoriteMovieFragment(), getString(R.string.movie_tab_title));
//                favoriteAdapter.addFragment(new TvShowFragment(), getString(R.string.tv_show_tab_title));
            viewPager.setAdapter(favoriteAdapter);
        }else {
            menuItem.setIcon(R.drawable.baseline_home_white_24);
            bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.baseline_collections_bookmark_black_24);
            setTitle(getResources().getString(R.string.app_name));
            ViewPagerAdapter homeAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout);
            homeAdapter.addFragment(new MovieFragment(), getString(R.string.movie_tab_title));
            homeAdapter.addFragment(new TvShowFragment(), getString(R.string.tv_show_tab_title));
            viewPager.setAdapter(homeAdapter);
        }
    }

}
