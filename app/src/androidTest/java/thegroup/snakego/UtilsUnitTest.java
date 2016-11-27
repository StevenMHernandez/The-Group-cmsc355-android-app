package thegroup.snakego;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import thegroup.snakego.utils.Utils;

public class UtilsUnitTest {

    @Test
    public void testComputeCentroid() {
        LatLng pointOne = new LatLng(37, -77);
        LatLng pointTwo = new LatLng(38, -78);

        LatLng expectedResult = new LatLng(37.5, -77.5);
        LatLng resultPoint = Utils.computeCentroid(pointOne, pointTwo);

        Assert.assertEquals(expectedResult, resultPoint);
    }

    @Test
    public void testComputeRectangleFromCenterPoint(){
        final double RECTANGLESIZE = 0.00001;
        LatLng point = new LatLng(1, 1);

        PolygonOptions expectedResult = new PolygonOptions().add(new LatLng((1-RECTANGLESIZE), (1-RECTANGLESIZE)),
                new LatLng((1-RECTANGLESIZE), (1+RECTANGLESIZE)),
                new LatLng((1+RECTANGLESIZE), (1+RECTANGLESIZE)),
                new LatLng((1+RECTANGLESIZE), (1-RECTANGLESIZE)));
        PolygonOptions actualResult = Utils.computeRectangleFromCenterPoint(point);

        List<LatLng> expectedResultsPoints = expectedResult.getPoints();
        List<LatLng> actualResultsPoints = actualResult.getPoints();

        Assert.assertEquals(expectedResultsPoints, actualResultsPoints);
    }

    @Test
    public void testGetRectanglesFromLineNotNull(){
        LatLng pointOne = new LatLng(37, -77);
        LatLng pointTwo = new LatLng(38, -78);
        Assert.assertNotNull(Utils.getRectanglesFromLine(pointOne, pointTwo));
    }

    @Test
    public void testGetRectanglesFromLine(){
        LatLng pointOne = new LatLng(37, -77);
        LatLng pointTwo = new LatLng(37.0002, -77.0002);

        Collection<PolygonOptions> results = Utils.getRectanglesFromLine(pointOne, pointTwo);

        Assert.assertEquals(16, results.size());
    }

}
