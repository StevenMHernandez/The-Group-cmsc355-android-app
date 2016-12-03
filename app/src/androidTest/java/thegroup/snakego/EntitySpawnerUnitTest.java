package thegroup.snakego;

import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import thegroup.snakego.entities.BaseEntity;
import thegroup.snakego.entities.GreenApple;
import thegroup.snakego.entities.Ouroboros;
import thegroup.snakego.entities.RedApple;
import thegroup.snakego.models.User;
import thegroup.snakego.services.EntitySpawner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EntitySpawnerUnitTest {

    @Before public void setup() {
        // required for EntitySpawner; ignore for now.
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    @Test public void test_EntitySpawner_checkCollisions() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));
        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLocation = new LatLng(1,1);

        // set our user to some location
        User.get().setLatLng(userLocation);

        int initialScore = User.get().getScore();

        // spawn good entity
        spawner.addEntity(new Ouroboros(userLocation));
        spawner.checkCollisions();

        int secondScore = User.get().getScore();

        Assert.assertTrue(secondScore != initialScore);
    }

    @Test public void test_EntitySpawner_addEntity() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));
        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        LatLng userLocation = new LatLng(1,1);
        LatLng userLocation2 = new LatLng(2,2);
        LatLng userLocation3 = new LatLng(3,3);
        LatLng userLocation4 = new LatLng(4,4);

        // set our user to some location
        User.get().setLatLng(userLocation);
        int initialSize = spawner.getCurrentEntities().size();
        Assert.assertEquals(0, initialSize);

        // spawn good entity
        spawner.addEntity(new Ouroboros(userLocation2));
        int subsequentSize = spawner.getCurrentEntities().size();
        Assert.assertEquals(1, subsequentSize);

        spawner.addEntity(new Ouroboros(userLocation3));
        subsequentSize = spawner.getCurrentEntities().size();
        Assert.assertEquals(2, subsequentSize);

        spawner.addEntity(new Ouroboros(userLocation4));
        subsequentSize = spawner.getCurrentEntities().size();
        Assert.assertEquals(3, subsequentSize);
    }
}
