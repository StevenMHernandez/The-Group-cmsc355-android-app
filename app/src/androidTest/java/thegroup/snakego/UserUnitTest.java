package thegroup.snakego;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Test;

import thegroup.snakego.Models.User;

import static org.junit.Assert.*;

/**
 * This test class is for the MapDisplay user story.
 */

public class UserUnitTest {
    @Test
    public void testSingleton() throws Exception {
        //Assert that the created singletons have the same values at start
        User userOne = User.get();
        User userTwo = User.get();

        assertSame(userOne, userTwo);
    }

    @Test
    public void userAtStart() {
        //Assert that the length & score & highscore of the user("Snake") is zero at start
        Assert.assertEquals(0, User.get().getSnake().size());

        Assert.assertEquals(0, User.get().getScore());

        Assert.assertEquals(0, User.get().getHighScore());
    }

    @Test
    public void testSnakeLength() throws Exception {
        //Assert that the snake isnt longer than it should be depending on the score
        User user = User.get();
        for(int i = 0; i<100; ++i){
            user.onLocationUpdated(new LatLng(37.0, 77.0));
            System.out.println("snakeSize = " + user.getSnake().size());
            assertTrue(user.getSnake().size()<=11);
        }

    }

    @Test
    public void testAddPointToScore() {
        User.get().addPoints(1);
        Assert.assertEquals(1, User.get().getScore());
    }

    @Test
    public void testRemovePointFromScore(){
        User.get().addPoints(1);
        User.get().removePoints(1);
        Assert.assertEquals(0, User.get().getScore());
    }

    @Test
    public void testAddPointToSnake(){
        User.get().onLocationUpdated(new LatLng(33.0, -77.0));
        Assert.assertEquals(1, User.get().getSnake().size());
    }



}