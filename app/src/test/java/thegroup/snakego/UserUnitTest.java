package thegroup.snakego;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import thegroup.snakego.Models.User;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserUnitTest {
    @Test
    public void testSingleton() throws Exception {
        User userOne = User.get();
        User userTwo = User.get();

        assertSame(userOne, userTwo);
    }

    @Test
    public void testSnakeLength() throws Exception {
        User user = User.get();
        for(int i = 0; i<100; ++i){
            user.onLocationUpdated(new LatLng(37.0, 77.0));
            System.out.println("snakeSize = " + user.getSnake().size());
            assertTrue(user.getSnake().size()<=11);
        }

    }

}