package thegroup.snakego.Observers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thegroup.snakego.Entities.BaseEntity;
import thegroup.snakego.Services.EntitySpawner;

public class EntitySpawnerObserver implements PropertyChangeListener {

    GoogleMap map;

    HashMap<BaseEntity, Marker> markers = new HashMap<>();

    public EntitySpawnerObserver(EntitySpawner spawner, GoogleMap map) {
        this.map = map;

        spawner.addChangeListener(this);
    }

    public void addNewMarkers(ArrayList<BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            if (!this.markers.containsKey(entity)) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(entity.getLatlng())
                        .icon(BitmapDescriptorFactory.defaultMarker(entity.getColor()));

                Marker marker = this.map.addMarker(markerOptions);

                this.markers.put(entity, marker);
            }
        }
    }

    public void removeOldMarkers(ArrayList<BaseEntity> entities) {
        ArrayList<BaseEntity> markersToRemove = new ArrayList<>();

        for (Map.Entry<BaseEntity, Marker> entry : this.markers.entrySet()) {
            BaseEntity entity = entry.getKey();
            Marker marker = entry.getValue();

            if (!entities.contains(entity)) {
                markersToRemove.add(entity);
                marker.remove();
            }
        }

        for (BaseEntity entity : markersToRemove) {
            this.markers.remove(entity);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        ArrayList<BaseEntity> entities = (ArrayList) event.getNewValue();

        this.addNewMarkers(entities);

        this.removeOldMarkers(entities);
    }
}
