package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

//  Last part of End a Game User Story.
@RunWith(AndroidJUnit4.class)
public class QuitTest {

    @Rule
    public ActivityTestRule<OptionsActivity> mOptionsActivity = new ActivityTestRule<>(OptionsActivity.class);

    @Test
    public void testQuitWorks() {
        onView(withId(R.layout.options_layout));

        onView(withId(R.id.quit_game_text)).perform(click());

        Assert.assertTrue(mOptionsActivity.getActivity().isFinishing());
    }
}