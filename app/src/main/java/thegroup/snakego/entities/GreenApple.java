package thegroup.snakego.entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.R;
import thegroup.snakego.interfaces.AnimateEntity;
import thegroup.snakego.models.User;

public class GreenApple extends BaseEntity implements AnimateEntity {

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

    @Override
    public void animate() {
        double oldLong = GreenApple.this.getPosition().longitude;
        double oldLat = GreenApple.this.getPosition().latitude;
        double newLong = oldLong;
        double newLat = oldLat;
        // move the apple
        if (GreenApple.this.getPosition() != User.get().getPosition()) {
            if (oldLong < User.get().getPosition().longitude) {
                newLong += .000001;
            } else {
                newLong -= .000001;
            }
            if (oldLat < User.get().getPosition().latitude) {
                newLat += .000001;
            } else {
                newLat -= .000001;
            }
            GreenApple.this.setPosition(newLat, newLong);

        }
    }
}


