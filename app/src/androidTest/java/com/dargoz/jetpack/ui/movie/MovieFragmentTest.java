package com.dargoz.jetpack.ui.movie;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.dargoz.jetpack.EspressoIdlingResource;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.testing.SingleFragmentActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MovieFragmentTest {
    @Rule
    public final ActivityTestRule<SingleFragmentActivity> activityTestRule =
            new ActivityTestRule<>(SingleFragmentActivity.class);
    private final MovieFragment movieFragment = new MovieFragment();
    @Before
    public void setUp() {
        activityTestRule.getActivity().setFragment(movieFragment);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void performClickMovieItem() {
        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }
}