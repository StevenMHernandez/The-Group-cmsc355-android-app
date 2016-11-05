package thegroup.snakego.entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.R;
import thegroup.snakego.models.User;

public class GreenApple extends BaseEntity {

    public GreenApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().removePoints(5);
    }

    public int getImage() {
        return R.mipmap.ic_greenapple;
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_GREEN;
    }
}


