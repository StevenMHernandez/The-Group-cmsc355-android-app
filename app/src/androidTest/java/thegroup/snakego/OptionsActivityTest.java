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
public class OptionsActivityTest {

    @Rule public ActivityTestRule<OptionsActivity> mOptionsActivity = new ActivityTestRule<>(OptionsActivity.class);

    @Test public void optionsPageClickResumeGame() {
        //  Display Menu user issue: Scenario 2: given user is on optionsActivity page

        // when user clicks "resume game"
        onView(withId(R.id.resume_game_text)).perform(click());

        // then user is taken back to game
        // this test will never work since there is no calling method
        onView(withId(R.id.icon_button)).check(matches(notNullValue()));
    }

    @Test public void onOptionsPageClickHighScores() {
        // given user is on optionsActivity page

        // when user clicks "High Scores"
        onView(withId(R.id.high_scores_text)).perform(click());

        // then user is taken to page with High Scores
        onView(withId(R.id.high_scores_page)).check(matches(notNullValue() ));
    }

    @Test public void optionsResumeIsClickable() {

        onView(withId(R.id.resume_game_text))
                .check(matches(withText("Resume Game")));
    }



}
