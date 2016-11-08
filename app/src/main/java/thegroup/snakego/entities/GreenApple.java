package thegroup.snakego.entities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.R;
import thegroup.snakego.interfaces.AnimateEntity;
import thegroup.snakego.models.User;

public class GreenApple extends BaseEntity implements AnimateEntity {

    private double latLngMove = .000001;

    public GreenApple(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().addPoints(400);
    }

    public int getImage() {
        return R.mipmap.ic_greenapple;
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_GREEN;
    }

    @Override
    public void animate() {
        // added for test, better ways to do this
        if (User.get().getScore() > 200) {
            latLngMove = .000009;
        }

        double oldLong = this.getPosition().longitude;
        double oldLat = this.getPosition().latitude;
        double newLong = oldLong;
        double newLat = oldLat;
        // move the apple
        if (this.getPosition() != User.get().getPosition()) {
            if (oldLong < User.get().getPosition().longitude) {
                newLong += latLngMove;
            } else {
                newLong -= latLngMove;
            }
            if (oldLat < User.get().getPosition().latitude) {
                newLat += latLngMove;
            } else {
                newLat -= latLngMove;
            }
            this.setPosition(newLat, newLong);

        }
    }
}


