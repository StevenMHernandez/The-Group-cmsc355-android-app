package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import thegroup.snakego.models.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class SnakeSpaceTest {

    @Rule
    public ActivityTestRule<MapsActivity> mMapsActivity = new ActivityTestRule<>(MapsActivity.class);

    @Test
    public void ouroborosTokenTakesUserToSnakeSpace() throws Throwable {
        final User user = User.get();

        this.mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.addPoints(215);
            }
        });

        onView(withId(R.id.gandalf)).check(matches(notNullValue()));

    }


//    @Test
//    public void clickOnPictureToReturnFromSnakeSpace() throws Throwable {
//        final User user = User.get();
//
//        this.mMapsActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                user.addPoints(215);
//            }
//        });
//
//        pressBack();
//        onView(withId(R.id.icon_button)).check(matches(notNullValue()));
//    }



}