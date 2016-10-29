package thegroup.snakego.entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.R;
import thegroup.snakego.models.User;

public class RedApple extends BaseEntity {

    public RedApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().addPoints(10);
    }

    public int getColor() {
        return R.mipmap.ic_redapple;
    }
}
