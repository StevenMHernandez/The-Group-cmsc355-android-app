package thegroup.snakego.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import android.graphics.Color;
import android.location.Location;

import java.util.ArrayList;
import java.util.Collection;

public class Utils {

    private static final double RECT_SIZE = 0.00001;

    public static float distance(LatLng lat1, LatLng lat2) {
        Location location1 = new Location("1");
        location1.setLatitude(lat1.latitude);
        location1.setLongitude(lat1.longitude);
        Location location2 = new Location("2");
        location2.setLatitude(lat2.latitude);
        location2.setLongitude(lat2.longitude);
        return location1.distanceTo(location2);
    }

    public static PolygonOptions computeRectangleFromCenterPoint(LatLng center) {
        return new PolygonOptions()
                .add(new LatLng(center.latitude - RECT_SIZE, center.longitude - RECT_SIZE),
                        new LatLng(center.latitude - RECT_SIZE, center.longitude + RECT_SIZE),
                        new LatLng(center.latitude + RECT_SIZE, center.longitude + RECT_SIZE),
                        new LatLng(center.latitude + RECT_SIZE, center.longitude - RECT_SIZE)
                )
                .fillColor(Color.BLACK);
    }

    public static Collection<PolygonOptions> getRectanglesFromLine(LatLng p0, LatLng p1) {
        ArrayList<PolygonOptions> rectangles = new ArrayList<>();

        LatLng center = computeCentroid(p0, p1);

        if (Utils.distance(p0, p1) > 5) {
            rectangles.addAll(getRectanglesFromLine(p0, center));
            rectangles.addAll(getRectanglesFromLine(center, p1));
        } else {
            rectangles.add(computeRectangleFromCenterPoint(center));
            rectangles.add(computeRectangleFromCenterPoint(p1));
        }

        return rectangles;
    }

    public static LatLng computeCentroid(LatLng p0, LatLng p1) {
        return new LatLng((p0.latitude + p1.latitude) / 2, (p0.longitude + p1.longitude) / 2);
    }
}
