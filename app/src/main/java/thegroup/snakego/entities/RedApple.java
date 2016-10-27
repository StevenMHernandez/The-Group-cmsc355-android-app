package thegroup.snakego.entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.models.User;

public class RedApple extends BaseEntity {

    public RedApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().addPoints(10);
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_RED;
    }
}
