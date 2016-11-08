package thegroup.snakego;

import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import thegroup.snakego.entities.GreenApple;
import thegroup.snakego.models.User;
import thegroup.snakego.services.EntitySpawner;

// user story 14
@RunWith(AndroidJUnit4.class)
public class GreenAppleMoveTest {
    @Before
    public void setup() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    // Given I am walking When an apple populates Then I want to see it moving across the screen.
    @Test public void GreenAppleChangesLocation() throws InterruptedException {

        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLoc = new LatLng(2 , 2);
        LatLng greenStart = new LatLng(4, 4);
        User.get().setLatLng(userLoc);
        GreenApple apple = new GreenApple(greenStart);
        spawner.addEntity(apple);
        Thread.sleep(1000);

        Assert.assertNotEquals(greenStart, apple.getPosition());

    }

    // Given I get longer When more apples populate Then I see them moving faster
    @Test public void GreenAppleGetsFaster() throws InterruptedException {
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLoc = new LatLng(1.0 , 1.0);
        LatLng greenStart = new LatLng(4.0, 4.0);
        User.get().setLatLng(userLoc);
        GreenApple appleOne = new GreenApple(greenStart);
        spawner.addEntity(appleOne);
        // increases "speed" of apple movement
        User.get().addPoints(400);
        GreenApple appleTwo = new GreenApple(greenStart);
        spawner.addEntity(appleTwo);
        Thread.sleep(6000);

        Assert.assertNotEquals(appleOne.getPosition(), appleTwo.getPosition());
    }

    // Given a green apple populates When it starts moving Then I notice it chasing me!
    @Test public void GreenAppleGetsUser() throws InterruptedException {
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        final EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLoc = new LatLng(2 , 2);
        LatLng greenStart = new LatLng(1.999968, 1.999968);
        User.get().setLatLng(userLoc);
        final GreenApple apple = new GreenApple(greenStart);
        spawner.addEntity(apple);

        Runnable moveEntityRunnable =  new Runnable() {
            @Override
            public void run() {
                while (!spawner.collide) {
                    spawner.checkCollisions();
                    double oldLong = apple.getPosition().longitude;
                    double oldLat = apple.getPosition().latitude;
                    double newLong = oldLong;
                    double newLat = oldLat;
                    // move the apple
                    if (apple.getPosition() != User.get().getPosition()) {
                        if (oldLong < User.get().getPosition().longitude) {
                            newLong += .000001;
                        } else {
                            newLong -= .000001;
                        }
                        if (oldLat < User.get().getPosition().latitude) {
                            newLat += .000001;
                        } else {
                            newLat -= .000001;
                        }
                        apple.setPosition(newLat, newLong);
                    }
                }
            }
        };
        moveEntityRunnable.run();
        Assert.assertTrue(spawner.collide);
    }
}
