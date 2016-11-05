package thegroup.snakego.observers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thegroup.snakego.entities.BaseEntity;
import thegroup.snakego.services.EntitySpawner;

public class EntitySpawnerObserver implements PropertyChangeListener {


    private GoogleMap map;

    private Marker marker = null;


    private HashMap<BaseEntity, Marker> markers = new HashMap<>();

    public EntitySpawnerObserver(EntitySpawner spawner, GoogleMap map) {
        this.map = map;

        spawner.addChangeListener(this);
    }

    public void addNewMarkers(ArrayList<BaseEntity> entities) {

        for (final BaseEntity entity : entities) {
            if (!this.markers.containsKey(entity)) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(entity.getPosition());

                markerOptions.icon(BitmapDescriptorFactory.fromResource(entity.getImage()));
                marker = this.map.addMarker(markerOptions);

                marker.setTag(entity);

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

    public void updateMarkerPosition() {

        for (Map.Entry<BaseEntity, Marker> entry : this.markers.entrySet()) {
            BaseEntity entity = entry.getKey();
            Marker marker = entry.getValue();
            if (entity.getPosition() != marker.getPosition()) {
                marker.setPosition(entity.getPosition());
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        ArrayList<BaseEntity> entities = (ArrayList) event.getNewValue();

        this.removeOldMarkers(entities);
        this.addNewMarkers(entities);
        this.updateMarkerPosition();


    }
}
