package com.dargoz.jetpack.ui;

import androidx.test.rule.ActivityTestRule;

import com.dargoz.jetpack.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkTabLayout() {
        onView(withId(R.id.main_tab_layout)).check(matches(isDisplayed()));

    }
    @Test
    public void swipePage() {
        onView(withId(R.id.main_view_pager))
                .check(matches(isDisplayed()));

        onView(withId(R.id.main_view_pager))
                .perform(swipeLeft());
    }
}