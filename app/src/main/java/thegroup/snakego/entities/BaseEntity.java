package thegroup.snakego.entities;

import com.google.android.gms.maps.model.LatLng;

public abstract class BaseEntity {
    public abstract void onCollision();

    public abstract int getImage();

    public abstract float getColor();

    protected LatLng latlng;

    BaseEntity(LatLng latlng) {
        this.latlng = latlng;
    }

    protected long createdAt = System.currentTimeMillis();

    public LatLng getPosition() {
        return latlng;
    }

    public void setPosition(double lat, double lng) {
        this.latlng = new LatLng(lat, lng);
    }

    public double getLong() {
        return this.getPosition().longitude;
    }

    public double getLat() {
        return this.getPosition().latitude;
    }
}
