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
    User userTwo = new User();
//    @Test
//    public void testSingleton() throws Exception {
//        //Assert that the created singletons have the same values at start
//
//
//        assertSame(userOne, userTwo);
//    }

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
        for(int i = 0; i<100; ++i){
            userOne.onLocationUpdated(new LatLng(37.0, 77.0));
            System.out.println("snakeSize = " + userOne.getSnake().size());
            assertTrue(userOne.getSnake().size()<=11);
        }

    }

    @Test
    public void testAddPointToScore() {
        userOne.addPoints(1);
        Assert.assertEquals(1, userOne.getScore());
    }

    @Test
    public void testRemovePointFromScore(){
        User.get().addPoints(1);
        User.get().removePoints(1);
        Assert.assertEquals(0, userOne.getScore());
    }

    @Test
    public void testAddPointToSnake(){
        userOne.onLocationUpdated(new LatLng(33.0, -77.0));
        Assert.assertEquals(1, userOne.getSnake().size());
    }



}