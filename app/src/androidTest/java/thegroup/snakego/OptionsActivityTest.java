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

    @Test public void optionsResumeIsClickable() {
        onView(withId(R.id.resume_game_text))
                .check(matches(withText("Resume Game")));
    }

    @Test public void optionsResumeToGamePage() {
        onView(withId(R.id.resume_game_text)).perform(click());
        onView(withId(R.id.icon_button)).check(matches(notNullValue() ));
    }

    @Test public void optionsTitleReadsRight() {
        onView(withId(R.id.options_page_text))
                .check(matches(withText("Options")));
    }

    @Test public void optionsQuitGameReadsRight() {
        onView(withId(R.id.quit_game_text))
                .check(matches(withText("Quit Game")));
    }

    @Test public void optionsHighScoreReadsRight() {
        onView(withId(R.id.high_scores_text))
                .check(matches(withText("High Scores")));
    }

}
