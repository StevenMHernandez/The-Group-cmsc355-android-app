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
        User.get().removePoints(50);
    }

    public int getColor() {
        return R.mipmap.ic_greenapple;
    }
}
