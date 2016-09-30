package thegroup.snakego.Services;

import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import thegroup.snakego.Entities.BaseEntity;
import thegroup.snakego.Entities.GreenApple;
import thegroup.snakego.Entities.RedApple;
import thegroup.snakego.Interfaces.Listenable;
import thegroup.snakego.Utils.DistanceCalculator;

public class EntitySpawner implements Listenable {

    protected static final int SPAWN_FREQUENCY = 20000;

    protected static final int COLLISION_DISTANCE = 10;

    protected static final int MAX_ENTITIES = 10;

    protected int spawnRate = 2;

    protected LatLngBounds currentMapBounds;

    protected Class[] entityTypes = {GreenApple.class, RedApple.class};

    protected ArrayList<BaseEntity> currentEntities = new ArrayList<>();

    protected Handler handler = new Handler();

    protected List<PropertyChangeListener> listeners = new ArrayList<>();

    public EntitySpawner(LatLngBounds currentMapBounds) {
        this.currentMapBounds = currentMapBounds;
        this.handler.postDelayed(this.spawnEntitiesRunnable, SPAWN_FREQUENCY / spawnRate);
    }

    protected LatLng getRandomLocation() {
        double latitude = this.randomInRange(this.currentMapBounds.southwest.latitude, this.currentMapBounds.northeast.latitude);
        double longitude = this.randomInRange(this.currentMapBounds.northeast.longitude, this.currentMapBounds.southwest.longitude);

        return new LatLng(latitude, longitude);
    }

    protected double randomInRange(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    public void spawnEntity() {
        try {
            if (this.currentEntities.size() >= MAX_ENTITIES) {
                this.currentEntities.remove(0);
            }
            int index = new Random().nextInt(this.entityTypes.length);

            BaseEntity entity = (BaseEntity) this.entityTypes[index].getConstructor(LatLng.class).newInstance(this.getRandomLocation());

            this.currentEntities.add(entity);

            this.notifyListeners(this, "Entities", this.currentEntities, this.currentEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCollisions(LatLng latlng) {
        for (BaseEntity entity : this.currentEntities) {
            if (DistanceCalculator.distance(latlng, entity.getLatlng()) < COLLISION_DISTANCE) {
                entity.onCollision();
                this.currentEntities.remove(entity);
            }
        }
    }

    protected Runnable spawnEntitiesRunnable = new Runnable() {
        @Override
        public void run() {
            spawnEntity();

            handler.postDelayed(this, (long) randomInRange(SPAWN_FREQUENCY / spawnRate, 0));
        }
    };

    public void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listeners.add(newListener);
    }
}