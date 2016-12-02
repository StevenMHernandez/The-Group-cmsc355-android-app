package thegroup.snakego.entities;

import android.content.Intent;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import thegroup.snakego.MapsActivity;
import thegroup.snakego.OptionsActivity;
import thegroup.snakego.R;
import thegroup.snakego.interfaces.AnimateEntity;
import thegroup.snakego.models.User;

public class Ouroboros extends BaseEntity {

    private double latLngMove = .000001;

    public Ouroboros(LatLng latlng) {
        super(latlng);
    }

    public void onCollision() {
        User.get().addPoints(100);
    }

    public int getImage() {
        return R.mipmap.ic_ouroboros;
    }

    public float getColor() {
        return BitmapDescriptorFactory.HUE_GREEN;
    }

}


