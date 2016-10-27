package thegroup.snakego._utils;

import com.google.android.gms.maps.model.LatLng;

import android.location.Location;

public class DistanceCalculator {
    public static float distance(LatLng lat1, LatLng lat2) {
        Location location1 = new Location("1");
        location1.setLatitude(lat1.latitude);
        location1.setLongitude(lat1.longitude);
        Location location2 = new Location("2");
        location2.setLatitude(lat2.latitude);
        location2.setLongitude(lat2.longitude);
        return location1.distanceTo(location2);
    }
}
