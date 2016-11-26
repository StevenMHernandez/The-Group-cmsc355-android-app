package thegroup.snakego;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Test;

import thegroup.snakego.models.User;

import static org.junit.Assert.*;

/**
 * This test class is for the MapDisplay user story.
 */
public class UserUnitTest {
    User userOne = new User();

    @Test
    public void userAtStart() {
        //Assert that the length & score & highscore of the user("Snake") is zero at start
        Assert.assertEquals(1, userOne.getMaxSnakeLength());

        Assert.assertEquals(0, userOne.getScore());

        Assert.assertEquals(0, userOne.getHighScore());
    }

    @Test
    public void testSnakeLength() throws Exception {
        //Assert that the snake isnt longer than it should be depending on the score
        //        User user = User.get();
        for (int i = 0; i < 100; ++i) {
            userOne.onLocationUpdated(new LatLng(37.0, 77.0));
            System.out.println("snakeSize = " + userOne.getUserLocationHistory().size());
            assertTrue(userOne.getUserLocationHistory().size() <= 11);
        }

    }

    @Test
    public void testAddPointToScore() {
        userOne.addPoints(1);
        Assert.assertEquals(1, userOne.getScore());
    }

    @Test
    public void testRemovePointFromScore() {
        User.get().addPoints(1);
        User.get().removePoints(1);
        Assert.assertEquals(0, userOne.getScore());
    }

    @Test
    public void testAddPointToSnake() {
        User.get().clearSnake();

        User.get().accelerometerChanged((float) 0.0, (float) 9.8, (float) -1.4, 1001);
        User.get().accelerometerChanged((float) 1.0, (float) 9.2, (float) -1.2, 1001);
        User.get().onLocationUpdated(new LatLng(33.0, -77.0));
        Assert.assertEquals(1, User.get().getUserLocationHistory().size());
    }

    @Test
    public void testAccelerometerMoving() {
        User.get().accelerometerChanged((float) 0.0, (float) 9.8, (float) -1.4, 1001);
        User.get().accelerometerChanged((float) 1.0, (float) 9.2, (float) -1.2, 1001);
        Assert.assertEquals(true, User.get().getMoving());
    }

    @Test
    public void testAccelerometerNotMoving() {
        User.get().accelerometerChanged((float) 1.0, (float) 9.2, (float) -1.2, 1001);
        User.get().accelerometerChanged((float) 1.0, (float) 9.2, (float) -1.2, 1001);
        Assert.assertEquals(false, User.get().getMoving());
    }

    @Test
    public void testSnakeUpdated() {
        User.get().accelerometerChanged((float) 0.0, (float) 9.8, (float) -1.4, 1001);
        User.get().onLocationUpdated(new LatLng(33.0, -77.5));
        Assert.assertEquals(1, User.get().getUserLocationHistory().size());
    }
}