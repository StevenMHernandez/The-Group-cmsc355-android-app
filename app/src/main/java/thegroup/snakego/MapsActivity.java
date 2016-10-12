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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

import thegroup.snakego.Models.User;
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
    protected LocationManager mLocationManager;
    private Button optionsButton;
    private Polyline polyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        if (getIntent().getBooleanExtra("quitclick", false)) {
            finish();
            return;
        }

        optionsButton = (Button) findViewById(R.id.icon_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsButtonPressed();
            }
        });

        if (this.mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();

            this.mGoogleApiClient.connect();
        }

        this.mLocationRequest = new LocationRequest()
                .setInterval(10 * 1000) //every 10 second
                .setFastestInterval(3 * 1000) //checks other apps to see if we can get better location
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //burn the battery
                .setSmallestDisplacement(0.5F); //1/2 meter


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    public void onOptionsButtonPressed() {
//        snakeOptions();  // comment this and uncomment bottom to switch back to activity view
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.mMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.mMap.setMyLocationEnabled(true);
        } else {
            this.requestLocationPermission();
        }

        float maxZoom = mMap.getMaxZoomLevel();
        this.mMap.setMaxZoomPreference(maxZoom - 1);
        this.mMap.setMinZoomPreference(maxZoom - 1);

        this.mMap.setOnMapLoadedCallback(this);
    }

    protected void requestLocationPermission() {
        // check for correct API version usage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onMapLoaded() {
        EntitySpawner spawner = new EntitySpawner(this.mMap.getProjection().getVisibleRegion().latLngBounds);
        new EntitySpawnerObserver(spawner, this.mMap);
    }

    @Override
    public void onCameraIdle() {

    }

    // part of the GoogleApiClient
    @Override
    public void onConnected(Bundle connectionHint) {
        this.locationUpdateRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationRequest = new LocationRequest()
                    .setInterval(10 * 1000) //every 10 second
                    .setFastestInterval(1000) //checks other apps to see if we can get better location
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //burn the battery
                    .setSmallestDisplacement(0.5F); // meter

            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, mLocationRequest, this);
        } else {
            this.requestLocationPermission();
        }
    }

    private void locationUpdateRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, mLocationRequest, this);
        } else {
            this.requestLocationPermission();
        }
    }

    // part of the GoogleApiClient
    @Override
    public void onConnectionSuspended(int i) {

    }

    // part of the GoogleApiClient
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                // Google Play services canceled the original PendingIntent
                e.printStackTrace();
            }
        } else {
            String error = "Location services connection failed with code " + connectionResult.getErrorCode();

            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    // location listener
    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //My addition to code here ZK
        User.get().onLocationUpdated(latLng);
        drawSnake();

//      mMap.addMarker(new MarkerOptions().position(latLng).title("Snake was here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void drawSnake() {
        LinkedList<LatLng> snake = User.get().getSnake();
        PolylineOptions polySnake = new PolylineOptions();
        for (LatLng l : snake) {
            polySnake.add(l);
        }
        if (polyline != null) {
            polyline.remove();
        }
        polyline = mMap.addPolyline(polySnake);
    }


    @Override
    protected void onStart() {  // BUG: Map doesn't OnLoad ever again from this point so spawner never reinitialized
        //TODO FIX SPAWNER BUG
        super.onStart();
        this.mGoogleApiClient.connect();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(this.mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.mGoogleApiClient.disconnect();
        AppIndex.AppIndexApi.end(this.mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if map is lost we need to handle it here
        // mGoogleApiClient.connect();
        if (this.mGoogleApiClient.isConnected()) {
            locationUpdateRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No permission, no play", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }

    public void snakeOptions() {
        DialogFragment newFragment = new OptionsDialogFragment();
        newFragment.show(getSupportFragmentManager(), "snakeOps");
    }

}
