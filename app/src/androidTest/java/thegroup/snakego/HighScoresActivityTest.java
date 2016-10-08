package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;


@RunWith(AndroidJUnit4.class)
public class HighScoresActivityTest {

    @Rule
    public ActivityTestRule<HighScoresActivity> mHighScoresActivity = new ActivityTestRule<>(HighScoresActivity.class);

    @Test public void onOptionsPageClickHighScoresBackToOptions() {
        // given user is on High Scores Activity page
        onView(withId(R.layout.activity_high_scores));

        // when user clicks "Return to Options Page"
        onView(withId(R.id.return_to_options_page)).perform(click());

        // then user is taken to Options page
        onView(withId(R.id.options_page_text)).check(matches(notNullValue() ));
    }

}