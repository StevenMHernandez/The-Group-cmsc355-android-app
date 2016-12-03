package thegroup.snakego.services;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.os.Handler;

import thegroup.snakego.entities.BaseEntity;
import thegroup.snakego.entities.GreenApple;
import thegroup.snakego.entities.Ouroboros;
import thegroup.snakego.entities.RedApple;
import thegroup.snakego.interfaces.AnimateEntity;
import thegroup.snakego.interfaces.Listenable;
import thegroup.snakego.models.User;
import thegroup.snakego.utils.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntitySpawner implements Listenable {

    private static final int SPAWN_FREQUENCY = 20000;

    public static final double COLLISION_DISTANCE = 3;

    private static final int MAX_ENTITIES = 10;

    private int spawnRate = 2;

    private int refreshMoveRate = 1000;

    public boolean collide = false;

    private LatLngBounds currentMapBounds;

    private Class[] entityTypes = {GreenApple.class, RedApple.class, Ouroboros.class};

    private ArrayList<BaseEntity> currentEntities = new ArrayList<>();

    private ArrayList<BaseEntity> entitiesToRemove = new ArrayList<>();

    private Handler handler = new Handler();

    private BaseEntity entity;

    private List<PropertyChangeListener> listeners = new ArrayList<>();

    private int ouroborosCount = 0;



    public EntitySpawner(LatLngBounds currentMapBounds) {
        this(currentMapBounds, true);
    }

    public EntitySpawner(LatLngBounds currentMapBounds, boolean automaticSpawning) {
        this.currentMapBounds = currentMapBounds;

        this.spawnEntity();
        this.spawnEntity();
        this.spawnEntity();

        if (automaticSpawning) {
            this.handler.postDelayed(this.spawnEntitiesRunnable, SPAWN_FREQUENCY / spawnRate);
        }
    }

    private LatLng getRandomLocation() {
        double slat = this.currentMapBounds.southwest.latitude;
        double nlat = this.currentMapBounds.northeast.latitude;
        double nlong = this.currentMapBounds.northeast.longitude;
        double slong = this.currentMapBounds.southwest.longitude;
        double latitude = this.randomInRange(slat, nlat);
        double longitude = this.randomInRange(nlong, slong);

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

            if (index == 2) {
                if (ouroborosCount < 10) {
                    ouroborosCount++;
                    index = 0;
                } else {
                    ouroborosCount = 0;
                }
            }

            entity = (BaseEntity) this.entityTypes[index]
                    .getConstructor(LatLng.class)
                    .newInstance(this.getRandomLocation());
            this.addEntity(entity);

            moveEntityRunnable.run();
            this.notifyListeners(this, "Entities", null, this.currentEntities);

            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void addEntity(BaseEntity entity) {
        if (User.get().getPosition() != null) {
            this.currentEntities.add(entity);
            this.checkCollisions();
            moveEntityRunnable.run();
            this.notifyListeners(this, "Entities", null, this.currentEntities);
        }
    }

    public void checkCollisions() {
        LatLng latlng = User.get().getPosition();

        for (BaseEntity entity : this.currentEntities) {
            if (Utils.distance(latlng, entity.getPosition()) < COLLISION_DISTANCE) {
                entity.onCollision();
                collide = true; // test dependency
                this.entitiesToRemove.add(entity);
                notifyListeners(this, "Entities", null, currentEntities);
            }

        }
    }

    public ArrayList<BaseEntity> getCurrentEntities() {
        return currentEntities;
    }

    public void updateLocation(LatLngBounds newMapBounds) throws NullPointerException {
        this.currentMapBounds = newMapBounds;

        ArrayList<BaseEntity> entitiesCopy = new ArrayList<>(this.currentEntities);

        for (BaseEntity entity : entitiesCopy) {
            if (!this.currentMapBounds.contains(entity.getPosition())) {
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

    private Runnable moveEntityRunnable = new Runnable() {
        @Override
        public void run() {
            if (User.get().getScore() > 20) {
                refreshMoveRate = 100; // this can be adjusted later
            }
            for (BaseEntity entity : currentEntities) {
                if (entity instanceof AnimateEntity) {
                    ((GreenApple) entity).animate();
                    checkCollisions();
                    handler.removeCallbacks(moveEntityRunnable);
                }
            }
            currentEntities.removeAll(entitiesToRemove);
            notifyListeners(this, "Entities", null, currentEntities);


            handler.postDelayed(this, refreshMoveRate);


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