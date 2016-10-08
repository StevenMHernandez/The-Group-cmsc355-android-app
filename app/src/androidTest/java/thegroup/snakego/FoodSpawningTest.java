package thegroup.snakego;

import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Assert;

import thegroup.snakego.Entities.BaseEntity;
import thegroup.snakego.Services.EntitySpawner;

@RunWith(AndroidJUnit4.class)
public class FoodSpawningTest {

    @Before public void setup() {
        // required for EntitySpawner; ignore for now.
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    @Test public void randomFoodSpawnsInMyVicinity() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        // spawn some random entity
        BaseEntity entity = spawner.spawnEntity();

        // assert the entity is in the latitude-longitude bounds
        Assert.assertTrue(latLngBounds.contains(entity.getLatlng()));
    }

    @Test public void foodDissapearsWhenIWalkAway() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        // spawn some random entity
        BaseEntity entity = spawner.spawnEntity();

        // build our map latitude-longitude bounds
        LatLngBounds newLatLngBounds = new LatLngBounds(new LatLng(20, 20), new LatLng(30, 30));

        spawner.updateLocation(newLatLngBounds);

        // assert that our entity no longer exists
        Assert.assertTrue(!spawner.getCurrentEntities().contains(entity));
    }
}
