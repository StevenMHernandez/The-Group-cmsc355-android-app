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
public class MapsActivityTest {

    @Rule public ActivityTestRule<MapsActivity> mMapsActivity = new ActivityTestRule<>(MapsActivity.class);

    @Test public void onClickSnakeGoIcon1() {
        // given user is playing game
        String optionsActivityText = "Resume Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and Sees "Resume Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void onClickSnakeGoIcon2() {
        // given user is playing game
        String optionsActivityText = "High Scores";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "High Scores" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void onClickSnakeGoIcon3() {
        // given user is playing game
        String optionsActivityText = "Quit Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "Quit Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

}