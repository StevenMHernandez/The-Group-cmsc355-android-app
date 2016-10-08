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

    @Test public void SnakeGoIconClickOpensOptionsPage() {
        //
        String optionsActivityText = "Options";
        onView(withId(R.id.icon_button)).perform(click());
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void mapFragmentIsOnPage() {
        onView(withId(R.id.map)).check(matches(notNullValue() ));
    }

    @Test public void iconButtonIsOnPage() {
        onView(withId(R.id.icon_button)).check(matches(notNullValue() ));
    }
}