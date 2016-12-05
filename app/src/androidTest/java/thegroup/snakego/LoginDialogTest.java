package thegroup.snakego;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;


@RunWith(AndroidJUnit4.class)
public class LoginDialogTest {

    @Rule
    public ActivityTestRule<MapsActivity> mMapsActivity = new ActivityTestRule<>(MapsActivity.class);

    @Test
    public void onStartDialogAppears() throws InterruptedException {
        sleep(5000);
        onView(withId(R.id.login_name))
                .check(matches(hasFocus()));
    }

    @Test
    public void userNameIsSet() throws InterruptedException {
        sleep(5000);
        onView(withId(R.id.login_name))
                .perform(click(), replaceText("Ben"));
        onView(withId(R.id.login_name))
                .check(matches(withText("Ben")));
        onView(withText("OK"))
                .perform(click());
        sleep(1000);
        onView(withId(R.id.icon_button))
                .perform(click());
        // should be on option menu
        onView(withId(R.id.user_name))
                .check(matches(withText("Hey, Ben")));
    }

    @Test
    public void userDefaultNameWhenUnset() throws InterruptedException {
        sleep(5000);
        onView(withText("OK"))
                .perform(click());
        sleep(1000);
        onView(withId(R.id.icon_button))
                .perform(click());
        // should be on option menu
        onView(withId(R.id.user_name))
                .check(matches(withText("Hey, User1")));
    }
}
