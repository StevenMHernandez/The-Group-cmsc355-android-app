package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import thegroup.snakego.models.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class OptionsActivityTest {

    @Rule
    public ActivityTestRule<OptionsActivity> mOptionsActivity = new ActivityTestRule<>(OptionsActivity.class);


    @Test
    public void onOptionsPageClickHighScores() {
        // given user is on optionsActivity page

        // when user clicks "High Scores"
        onView(withId(R.id.high_scores_text)).perform(click());

        // then user is taken to page with High Scores
        onView(withId(R.id.high_scores_page)).check(matches(notNullValue()));
    }

    @Test
    public void statsPageReturnsToOptions() throws InterruptedException {
        onView(withId(R.id.user_name)).perform(click());
        onView(withId(R.id.user_stats_title)).check(matches(notNullValue()));
        onView(withId(R.id.return_from_stats)).perform(click());
        onView(withId(R.id.options_page_text)).check(matches(notNullValue()));
    }

    @Test
    public void optionsResumeIsClickable() {
        onView(withId(R.id.resume_game_text))
                .check(matches(withText("Resume Game")));
    }

    //this test needed to be re-worked to fit new implementations
    @Test
    public void userCanSetUserName() {
        // given user is on optionsActivity page

        // when user types in their username
        onView(withId(R.id.user_name))
            .check(matches(withText("Hey, " + User.get().getName())));

        // their username should be whatever they set
       // Assert.assertTrue("Username should be set to NEW_USERNAME", User.get().getName().equals("NEW_USERNAME"));
    }

    @Test
    public void userNameIsClickable() {
        onView(withId(R.id.user_name))
                .check(matches(isClickable()));
    }

    @Test
    public void optionsUserNameSendsToStats() {
        // given user is on optionsActivity page

        // when user clicks on name
        onView(withId(R.id.user_name)).perform(click());

        // then user is taken to page with stats
        onView(withId(R.id.user_stats_title)).check(matches(notNullValue()));
    }


}
