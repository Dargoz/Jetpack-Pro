package com.dargoz.jetpack.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;

import com.dargoz.jetpack.ui.utils.FakeData;
import com.dargoz.jetpack.utils.Constants;

import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailFilmActivityTest {
    private MovieEntity movieEntity = FakeData.getMovieEntity();

    @Rule
    public ActivityTestRule<DetailFilmActivity> activityTestRule = new ActivityTestRule<DetailFilmActivity>(DetailFilmActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intentData = new Intent(targetContext, DetailFilmActivity.class);
            intentData.putExtra(Constants.MOVIE_INTENT,movieEntity);
            return intentData;
        }
    };

    @Test
    public void checkFilmDataIsMatched(){
        onView(withId(R.id.desc_detail_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.desc_detail_text_view)).check(matches(withText(movieEntity.getDescription())));
        onView(withId(R.id.score_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.score_text_view)).check(matches(withText(String.valueOf(movieEntity.getScore()))));
    }
}