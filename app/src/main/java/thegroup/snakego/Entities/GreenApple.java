package thegroup.snakego.Entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.Models.User;

public class GreenApple extends BaseEntity {

    public GreenApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().removePoints(50);
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_GREEN;
    }
}
