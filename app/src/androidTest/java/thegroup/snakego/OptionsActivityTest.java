package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class OptionsActivityTest {

    @Rule public ActivityTestRule<OptionsActivity> mOptionsActivity = new ActivityTestRule<>(OptionsActivity.class);

    @Test public void optionsResumeIsClickable() {
        onView(withId(R.id.resume_game_text))
                .check(matches(withText("Resume Game")));
    }
}
