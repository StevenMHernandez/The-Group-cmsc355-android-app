package thegroup.snakego.Observers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import thegroup.snakego.Entities.BaseEntity;
import thegroup.snakego.EntitySpawner;

public class EntitySpawnerObserver implements PropertyChangeListener {

    GoogleMap map;

    public EntitySpawnerObserver(EntitySpawner spawner, GoogleMap map) {
        this.map = map;

        spawner.addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        ArrayList<BaseEntity> entities = (ArrayList) event.getNewValue();

        BaseEntity entity = entities.get(entities.size() - 1);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(entity.getLatlng())
                .icon(BitmapDescriptorFactory.defaultMarker(entity.getColor()));

        this.map.addMarker(markerOptions);
    }
}
