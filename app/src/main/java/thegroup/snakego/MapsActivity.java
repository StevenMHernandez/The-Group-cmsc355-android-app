package thegroup.snakego;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import thegroup.snakego.Observers.EntitySpawnerObserver;
import thegroup.snakego.Services.EntitySpawner;

import static thegroup.snakego.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMapLoadedCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_FINE_LOCATION = 0;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    //private Location mLastLocation;
    //protected TextView mLatitudeText;
    //protected TextView mLongitudeText;
    private TextView mCameraTextView;
    //private FusedLocationProviderApi mFusedLoc = LocationServices.FusedLocationApi;
    protected LocationManager mLocationManager;
    private double snakeLats;
    private double snakeLongs;
    private LatLng snakeLoc;
    private Marker snakeSpot;
    private Button iconButton;
    //private float mMinZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        iconButton = (Button) findViewById(R.id.icon_button);
        iconButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                pressIconButton();
            }
        });




        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();

            mGoogleApiClient.connect();
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000); //every 10 second
        mLocationRequest.setFastestInterval(3 * 1000); //checks other apps to see if we can get better location
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //burn the battery
        mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
        /*mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds*/




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    public void pressIconButton() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }



    /*private void stopAutoManage() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.stopAutoManage();
    }*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.

     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        // TODO: need error checking

        this.mMap = map;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // We check for correct API version usage
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Getting permission if not already granted!
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }

        map.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {
        EntitySpawner spawner = new EntitySpawner(this.mMap.getProjection().getVisibleRegion().latLngBounds);
        new EntitySpawnerObserver(spawner, this.mMap);


    }


    @Override
    public void onCameraIdle() {
        mCameraTextView.setText(mMap.getCameraPosition().toString());
    }


    //Apart of the GoogleApiClient
    @Override
    public void onConnected(Bundle connectionHint) {
        locationUpdateRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    //mGoogleApiClient);
            /*if (mLastLocation != null) {
                //place marker at current position
                mMap.clear();
                snakeLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(snakeLoc);
                markerOptions.title("Snake GO");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                snakeSpot = mMap.addMarker(markerOptions);
            }*/

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000); //5 seconds
            mLocationRequest.setFastestInterval(3000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else{
            // We check for correct API version usage
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Getting permission if not already granted!
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }

        /*try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }*/
    }

    private void locationUpdateRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else{
            // We check for correct API version usage
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Getting permission if not already granted!
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }


    }

    //Apart of the GoogleApiClient
    @Override
    public void onConnectionSuspended(int i) {

    }

    //Apart of the GoogleApiClient
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            String error = "Location services connection failed with code " + connectionResult.getErrorCode();

            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }


    //our location listener
    @Override
    public void onLocationChanged(Location location) {

        float maxZ = mMap.getMaxZoomLevel();
        //here we can draw our snake, right now it just throwing down markers
        snakeLats = location.getLatitude();
        snakeLongs = location.getLongitude();

        LatLng latLng = new LatLng(snakeLats, snakeLongs);

        mMap.addMarker(new MarkerOptions().position(new LatLng(snakeLats, snakeLongs)).title("Snake was here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMaxZoomPreference(maxZ);
        mMap.setMinZoomPreference(maxZ);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onStop() {

        //endLocationUpdates();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.disconnect();
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if map is lost we need to handle it here
        //mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            locationUpdateRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            //mGoogleApiClient.disconnect();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


/*
    @Override
    public PendingResult flushLocations(GoogleApiClient client)
    {

    }

    @Override
    public Location getLastLocation(GoogleApiClient client)
    {

    }

    @Override
    public PendingResult setMockLocation(GoogleApiClient client, Location mockLocation)
    {

    }

    @Override
    public PendingResult setMockMode(GoogleApiClient client, boolean isMockMode)
    {

    }

*/
    /*
private void loadPermissions(String perm,int requestCode) {
    if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
        }
    }
}*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Granted
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                }
                else{
                    // Not granted
                    Toast.makeText(getApplicationContext(), "No permission, no play", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }

        }

    }
}
