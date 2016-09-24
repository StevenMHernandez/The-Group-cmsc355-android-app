package thegroup.snakego.Entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class RedApple extends BaseEntity {

    public RedApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        // User gains a point
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_RED;
    }
}
