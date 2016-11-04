package thegroup.snakego.observers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import thegroup.snakego.R;
import thegroup.snakego.entities.BaseEntity;
import thegroup.snakego.services.EntitySpawner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntitySpawnerObserver implements PropertyChangeListener {

    private GoogleMap map;

    private HashMap<BaseEntity, Marker> markers = new HashMap<>();

    public EntitySpawnerObserver(EntitySpawner spawner, GoogleMap map) {
        this.map = map;

        spawner.addChangeListener(this);
    }

    public void addNewMarkers(ArrayList<BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            if (!this.markers.containsKey(entity)) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(entity.getLatlng());
                //.icon(BitmapDescriptorFactory.defaultMarker(entity.getColor()));

                markerOptions.icon(BitmapDescriptorFactory.fromResource(entity.getImage()));
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
