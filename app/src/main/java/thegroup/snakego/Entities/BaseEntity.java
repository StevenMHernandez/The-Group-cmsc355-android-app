package thegroup.snakego.entities;

import com.google.android.gms.maps.model.LatLng;

public abstract class BaseEntity {
    public abstract void onCollision();

    public abstract float getColor();

    protected LatLng latlng;

    public BaseEntity(LatLng latlng) {
        this.latlng = latlng;
    }

    protected long createdAt = System.currentTimeMillis();

    public LatLng getLatlng() {
        return latlng;
    }
}
