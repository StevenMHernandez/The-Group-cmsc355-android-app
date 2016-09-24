package thegroup.snakego;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//need error checking
        mMap = googleMap;
        //
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        float zoom =  mMap.getMaxZoomLevel();

        BitmapDescriptor rvaMarker =
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        // Add a marker in VCU and move the camera
        LatLng rva = new LatLng(37.5490, -77.4534);
        mMap.addMarker(new MarkerOptions().position(rva).title("Marker in Richmond").icon(rvaMarker));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rva));

        //does a slow zoom in could be neat
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom), 10000, null);





    }
}
