package thegroup.snakego.Entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class GreenApple extends BaseEntity {

    public GreenApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        // User loses a point
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_GREEN;
    }
}
