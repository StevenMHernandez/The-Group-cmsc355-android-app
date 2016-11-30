package thegroup.snakego;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import thegroup.snakego.entities.BaseEntity;
import thegroup.snakego.services.EntitySpawner;

@RunWith(MockitoJUnitRunner.class)

public class FoodSpawningTest2 {

    @Mock EntitySpawner spawner;

    @Test public void spawnedEntityIsEitherRedOrGreenApple() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        // spawn some random entity
        BaseEntity entity = spawner.spawnEntity();

        Assert.assertTrue(entity.getImage() == R.mipmap.green_apple || entity.getImage() == R.mipmap.red_apple);
    }

}