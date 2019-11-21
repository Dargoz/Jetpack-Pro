package com.dargoz.jetpack.ui;

import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.dargoz.jetpack.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

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

    @Test
    public void showFavoriteMenu() {
        onView(
                allOf(
                        withText("Favorite"),
                        isDescendantOfA(withId(R.id.main_bottom_navigation_view)),
                        isDisplayed()))
                .perform(click());
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Favorite List")));
    }
}