package thegroup.snakego;

import android.graphics.Color;
import android.os.Looper;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import thegroup.snakego.Entities.BaseEntity;
import thegroup.snakego.Services.EntitySpawner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class FoodSpawningTest2 {

    @Mock EntitySpawner spawner;

    @Test public void foodColorGreenOrRed() {
        // build our map latitude-longitude bounds
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(10, 10));

        float color = BitmapDescriptorFactory.HUE_GREEN;
        float color2 = BitmapDescriptorFactory.HUE_RED;

        // build our random food entity spawner
        EntitySpawner spawner = new EntitySpawner(latLngBounds, false);

        // spawn some random entity
        BaseEntity entity = spawner.spawnEntity();

        Assert.assertTrue(entity.getColor() == color || entity.getColor() == color2);
    }

}