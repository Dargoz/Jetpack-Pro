package com.dargoz.jetpack.ui.tvshow;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.dargoz.jetpack.EspressoIdlingResource;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.testing.SingleFragmentActivity;
import com.dargoz.jetpack.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TvShowFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity > activityTestRule =
            new ActivityTestRule<>(SingleFragmentActivity .class);
    private TvShowFragment tvShowFragment = new TvShowFragment();

    @Before
    public void setUp() {
        activityTestRule.getActivity().setFragment(tvShowFragment);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void performClickTvShowItem() {
        onView(withId(R.id.tv_show_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_show_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }
}