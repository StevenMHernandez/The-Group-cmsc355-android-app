package thegroup.snakego._services;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.os.Handler;

import thegroup.snakego._entities.BaseEntity;
import thegroup.snakego._entities.GreenApple;
import thegroup.snakego._entities.RedApple;
import thegroup.snakego._interfaces.Listenable;
import thegroup.snakego._models.User;
import thegroup.snakego._utils.DistanceCalculator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntitySpawner implements Listenable {

    private static final int SPAWN_FREQUENCY = 20000;

    private static final int COLLISION_DISTANCE = 10;

    private static final int MAX_ENTITIES = 10;

    private int spawnRate = 2;

    private LatLngBounds currentMapBounds;

    private Class[] entityTypes = {GreenApple.class, RedApple.class};

    private ArrayList<BaseEntity> currentEntities = new ArrayList<>();

    private Handler handler = new Handler();

    private List<PropertyChangeListener> listeners = new ArrayList<>();

    public EntitySpawner(){}

    public EntitySpawner(LatLngBounds currentMapBounds) {
        this(currentMapBounds, true);
    }

    public EntitySpawner(LatLngBounds currentMapBounds, boolean automaticSpawning) {
        this.currentMapBounds = currentMapBounds;

        if (automaticSpawning) {
            this.handler.postDelayed(this.spawnEntitiesRunnable, SPAWN_FREQUENCY / spawnRate);
        }
    }

    private LatLng getRandomLocation() {
        double latitude = this.randomInRange(this.currentMapBounds.southwest.latitude, this.currentMapBounds.northeast.latitude);
        double longitude = this.randomInRange(this.currentMapBounds.northeast.longitude, this.currentMapBounds.southwest.longitude);

        return new LatLng(latitude, longitude);
    }

    private double randomInRange(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    public BaseEntity spawnEntity() {
        try {
            if (this.currentEntities.size() >= MAX_ENTITIES) {
                this.currentEntities.remove(0);
            }
            int index = new Random().nextInt(this.entityTypes.length);

            BaseEntity entity = (BaseEntity) this.entityTypes[index].getConstructor(LatLng.class).newInstance(this.getRandomLocation());

            this.addEntity(entity);

            this.notifyListeners(this, "Entities", this.currentEntities, this.currentEntities);

            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void addEntity(BaseEntity entity) {
        this.currentEntities.add(entity);

        this.checkCollisions();
    }

    public void checkCollisions() {
        LatLng latlng = User.get().getLatLng();

        for (BaseEntity entity : this.currentEntities) {
            if (DistanceCalculator.distance(latlng, entity.getLatlng()) < COLLISION_DISTANCE) {
                entity.onCollision();
                this.currentEntities.remove(entity);
            }
        }
    }

    public ArrayList<BaseEntity> getCurrentEntities() {
        return currentEntities;
    }

    public void updateLocation(LatLngBounds newMapBounds) {
        this.currentMapBounds = newMapBounds;

        for (BaseEntity entity : this.currentEntities) {
            if (!this.currentMapBounds.contains(entity.getLatlng())) {
                this.currentEntities.remove(entity);
            }
        }

        this.checkCollisions();
    }

    private Runnable spawnEntitiesRunnable = new Runnable() {
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