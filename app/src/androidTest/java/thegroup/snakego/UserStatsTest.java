package thegroup.snakego;

import android.os.Looper;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import thegroup.snakego.entities.GreenApple;
import thegroup.snakego.entities.RedApple;
import thegroup.snakego.models.User;
import thegroup.snakego.services.EntitySpawner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class UserStatsTest {

    @Rule
    public ActivityTestRule<UserStatsActivity> mUserStatsActivity = new ActivityTestRule<>(UserStatsActivity.class);

    @Before
    public void setup() {
        // required for EntitySpawner; ignore for now.
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    @After
    public void tearDown(){
        Looper.myLooper().quit();
    }

    @Test
    public void redAppleCollisionChangesCount() {
        User.get().setRedAppleCount(0);

        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLocation = new LatLng(1,1);

        // set our user to location
        User.get().setLatLng(userLocation);

        spawner.addEntity(new RedApple(userLocation));
        spawner.checkCollisions();

        Assert.assertEquals(User.get().getRedAppleCount(), 1);

    }

    @Test
    public void greenAppleCollisionChangesCount() {

        User.get().setGreenAppleCount(0);

        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLocation = new LatLng(1,1);

        // set our user to location
        User.get().setLatLng(userLocation);

        spawner.addEntity(new GreenApple(userLocation));
        spawner.checkCollisions();

        Assert.assertNotSame(User.get().getGreenAppleCount(), 0);
    }

    @Test
    public void timeUpChanges() throws InterruptedException {
        long init = User.get().getTimeUp();
        sleep(2000);
        long second = User.get().getTimeUp();

        Assert.assertNotSame(init, second);
    }

    @Test
    public void redAppleDisplayed() {
        onView(withId(R.id.red_apple_stat))
                .check(matches(withText("Reds:" + User.get().getRedAppleCount())));
    }
    @Test
    public void greenAppleDisplayed() {
        onView(withId(R.id.green_apple_stat))
                .check(matches(withText("Greens:" + User.get().getGreenAppleCount())));
    }

    @Test
    public void timeUpDisplayed() {
        onView(withId(R.id.time_up))
                .check(matches(notNullValue()));
    }

}

